package com.ch.shop.controller.shop;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.ch.shop.dto.GoogleUser;
import com.ch.shop.dto.Member;
import com.ch.shop.dto.NaverUser;
import com.ch.shop.dto.NaverUserResponse;
import com.ch.shop.dto.OAuthClient;
import com.ch.shop.dto.OAuthTokenResponse;
import com.ch.shop.dto.Provider;
import com.ch.shop.model.member.MemberService;
import com.ch.shop.model.topcategory.TopCategoryService;

import lombok.extern.slf4j.Slf4j;

//일반 유저가 사용하게 될 쇼핑몰 쪽의 회원관련 요청을 처리하는 컨트롤러

@Slf4j
@Controller
public class MemberController {
	@Autowired
	private TopCategoryService topCategoryService;

	@Autowired
	private Map<String, OAuthClient> oauthClients;

	@Autowired
	private RestTemplate restTemplate; // HTTP 요청 능력 + 응답 결과인 json을 자동으로 java 객체로 매핑(마치 jackson처럼)

	@Autowired
	private MemberService memberService;

	@Autowired
	private ProviderService providerService;

	// 회원 로그인폼 요청 처리
	@GetMapping("/member/loginform")
	public String getLoginForm(Model model) {
		List topList = topCategoryService.getList();// 3단계

		// 4단계 결과 페이지로 가져갈것이 있따.
		model.addAttribute("topList", topList);
		return "shop/member/login";
	}

	// sns 로그인을 희망하는 유저들의 로그인 인증 요청 url 주소를 알려주는 컨트롤러 메서드
	// {} 변수 표시 value 는 @PathVariable("provider") 는 url의 일부를 파라미터화 시키는 기법 REST API 에
	// 사용됨
	@GetMapping("/oauth2/authorize/{provider}")
	@ResponseBody // 이 어노테이션을 설정하면, DispatcherServlet은 jsp 와의 매핑을 시도하지 않고 반환값 그대로 응답정보로 보낸다.
	public String getAuthUrl(@PathVariable("provider") String provider) throws Exception {
		OAuthClient oAuthClient = oauthClients.get(provider);

		log.debug(provider + "의 로그인 요청 url은 {}", oAuthClient.getAuthorizeUrl());

		// 이 주소를 이용하여 , 브라우저 사용자는 프로바이더에게 로그인을 요청해야 하는데 이때 요청 파라미터를 갖추어야
		// 로그인 절차가 성공할수 있다.
		// 요청시 지참할 파라미터에는 clientId, callback url, scope....
		StringBuffer sb = new StringBuffer();
		sb.append(oAuthClient.getAuthorizeUrl()).append("?").append("response_type=code") // 이 요청에 의해 인가 code 를 받을것임을
																							// 알린다.
				.append("&client_id=").append(urlEncode(oAuthClient.getClientId())) // 클라이언트 아이디 추가
				.append("&redirect_uri=").append(urlEncode(oAuthClient.getRedirectUri())) // 콜백 받을 주소 추가
				.append("&scope=").append(urlEncode(oAuthClient.getScope())); // 사용자 정보의 접근 범위 추가

		return sb.toString();
	}

	// 웹을통해 파라미터 전송시 문자열이 깨지지 않도록 인코딩 처리를 해주는 메서드
	private String urlEncode(String s) throws Exception {
		return URLEncoder.encode(s, "UTF-8");
	}

	/*----------------------------------------------------------
	 클라이언트가 동의하면(최초사용자) 또는 로그인(기존) 요청이 들어오게 되고, Provider가 이를 처리하는 과정에서
	 개발자가 등록해놓은 callback 주소로 임시코드(Authorize code)를 발급한다.
	  -----------------------------------------------------------*/
	@GetMapping("/login/callback/google")
	public String handleGoogleCallback(String code, HttpSession session) {
		/*----------------------------------------------------------
		 구글이 보내온 인증 코드를 이용하여, 나의 clientId, client Secret을 조합하여, token을 요청하자!
		 결국 개발자가 원하는 것은 사용자의 정보이므로, 이 정보를 얻기 위해서는 토큰이 필요하므로...
		  -----------------------------------------------------------*/
		log.debug("구글이 발급한 임시코드는 {}", code);

		OAuthClient google = oauthClients.get("google");

		// 구글로부터 받은 임시코드와 나의 정보(client id, client secret) 를 조합하여 구글에게 보내자... (토큰 받으려고)
		// 이때, 구글과 같은 프로바이더와 데이터를 주고 받기 위해서는 HTTP 통신규약을 지켜서 말을 걸 때는 머리, 몸을 구성하여 요청을 시도해야
		// 함
		MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>(); // 몸체
		param.add("grant_type", "authorization_code"); // 임시 코드를 이용하여 토큰을 요청하겠다는 것을 명시
		param.add("code", code); // 구글로부터 발급받은 임시코드를 그대로 추
		param.add("client_id", google.getClientId()); // 클라이언트 아이디 추가
		param.add("client_secret", google.getClientSecret()); // 클라이언트 시크릿 추가
		param.add("redirect_uri", google.getRedirectUri()); // callback uri

		HttpHeaders headers = new HttpHeaders(); // 머리
		// 아래와 같이 전송파라미터에 대한 contentType을 명시하면, key=value&key=value 방식의 데이터 쌍으로 자동으로
		// 변환...
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		// 머리와 본문(몸)을 합쳐서 하나의 HTTP 요청 엔티티로 결합
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(param, headers);

		// 구글에 요청 시작!!!, 스프링에서는 Http요청 후 그 응답 정보를 java 객체와 자동으로 매핑해주는 편리한 객체를 지원해 주는데,
		// 그 객체가 바로 RestTemplate (Http 요청 능력 + jackson 능력)
		// restTemplate.postForEntity("구글의 토큰 발급 주소", "머리와 몸을 합친 요청 객체", "결과를 받을 클래스");
		ResponseEntity<OAuthTokenResponse> response = restTemplate.postForEntity(google.getTokenUrl(), request,
				OAuthTokenResponse.class);
		log.debug("구글로부터 받은 응답 정보는 {}", response.getBody());

		// 얻어진 토큰으로 구글에 회원정보를 요청해보기
		OAuthTokenResponse responseBody = response.getBody();
		String access_token = responseBody.getAccess_token();

		log.debug("구글로부터 받은 엑세스 토큰은 {}", access_token);

		// 회원정보 가져오기
		// 구글에 요청을 시도하려면 역시나 이번에도 Http 프로토콜의 형식을 갖추어야 함
		HttpHeaders userInfoHeaders = new HttpHeaders();
		// 내가 바로 토큰을 가진 자임을 알리는 헤더 속성 값을 넣어야 함
		userInfoHeaders.add("Authorization", "Bearer " + access_token);
		HttpEntity<String> userInfoRequest = new HttpEntity<>("", userInfoHeaders);
		ResponseEntity<GoogleUser> userInfoResponse = restTemplate.exchange(google.getUserInfoUrl(), HttpMethod.GET,
				userInfoRequest, GoogleUser.class); // 서버로부터 데이터를 가져오기이므로, exchange() 사용

		log.debug("사용자 정보는 {}", userInfoResponse);

		GoogleUser user = userInfoResponse.getBody();

		/*----------------------------------------------------------
		 얻어진 유저 정보를 이용하여 할일
		 
		 1) 얻어진 회원이 우리를 mysql 존재하는지 따져서,
		 	있다면 로그인 세션만 부여하고 홈페이지 메인으로 보내기
		 	없다면? member 테이블에 insert하고 세션부여하고 홈페이지 메인으로 보내기
		  -----------------------------------------------------------*/
		Member member = new Member(); // empty
		member.setProvider_userid(user.getId());
		member.setName(user.getName());
		member.setEmail(user.getEmail());

		// select * from provider where provider_name='google'
		Provider provider = providerService.selectByName("google");
		member.setProvider(provider);
		memberService.registOrUpdate(member);

		// List topList=topCategoryService.getList();
		// model.addAttribute("topList", topList);
		// return "shop/index"; // 뻘 짓이다.

		// 로그인에 성공하면, 브라우저를 종료할 때까지는 자신의 정보를 접근할 수 있는 혜택을 부여해야 하므로,
		// 세션의 회원 정보를 담아둬야 한다...
		// jsp의 내장객체 중 세션을 담당하는 내장객체명은 session이고, 서블릿에서 자료형은 HttpSession이다.
		// jsp의 내장객체 중 요청정보를 담당하는 내장객체명은 request이고, 서블릿에서 자료형은 HttpServletRequest이다.

		session.setAttribute("member", member);

		return "redirect:/"; // 회원 로그인이 처리되면, 쇼핑몰의 메인으로 보내기
	}

	// 네이버 로그인 요청 처리(콜백 함수 처리)
	@GetMapping("/login/callback/naver")
	public String handleNaverLogin(String code, HttpSession session) {
		log.debug("네이버에서 발급한 임시코드는 {}", code);

		/*------------------------------------------------
		  1) code, client id, client secret 을 구성하여 토큰 발급을 요청
		 ------------------------------------------------*/
		OAuthClient client = oauthClients.get("naver");

		// 몸체 구성
		MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>(); // 몸체
		param.add("grant_type", "authorization_code"); // 임시 코드를 이용하여 토큰을 요청하겠다는 것을 명시
		param.add("code", code); // 구글로부터 발급받은 임시코드를 그대로 추
		param.add("client_id", client.getClientId()); // 클라이언트 아이디 추가
		param.add("client_secret", client.getClientSecret()); // 클라이언트 시크릿 추가
		param.add("redirect_uri", client.getRedirectUri()); // callback uri

		// 머리 만들기
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		// 몸 + 머리
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(param, headers);

		// 토큰 달라고 요청하기 (구글, 네이버, 카카오, 메타 등 모든 프로바이더가 토큰을 포함한 응답 정보의 내용이 같다..)
		ResponseEntity<OAuthTokenResponse> response = restTemplate.postForEntity(client.getTokenUrl(), request,
				OAuthTokenResponse.class);
		log.debug("네이버가 응답한 토큰 포함 정보는 {}", response);

		OAuthTokenResponse responseBody = response.getBody();

		/*------------------------------------------------
		  2) 발급된 토큰을 이용하여 회원 정보 조회하기
		  
		  
		 ------------------------------------------------*/
		String access_token = responseBody.getAccess_token();
		log.debug("네이버의 토큰은 {}", access_token);

		HttpHeaders userInfoHeaders = new HttpHeaders();
		userInfoHeaders.add("Authorization", "Bearer " + access_token);

		HttpEntity<String> userInfoRequest = new HttpEntity<>("", userInfoHeaders); // 몸은 비워넣고, 몸과 머리 합쳐 요청 보내기
		// GET 방식으로 사용자 정보 요청하기
		// restTemplate.exchange(client.getUserInfoUrl(), HttpMethod.GET,
		// userInfoRequest, 매핑될 클래스);
		ResponseEntity<NaverUserResponse> userInfoResponse = restTemplate.exchange(client.getUserInfoUrl(),
				HttpMethod.GET, userInfoRequest, NaverUserResponse.class);

		NaverUserResponse naverUserResponse = userInfoResponse.getBody();
		NaverUser naverUser = naverUserResponse.getResponse();

		log.debug("고유 id = {}", naverUser.getId());
		log.debug("이름 = {}", naverUser.getName());
		log.debug("email = {}", naverUser.getEmail());

		Member member = new Member(); // empty
		member.setProvider_userid(naverUser.getId());
		member.setName(naverUser.getName());
		member.setEmail(naverUser.getEmail());

		// select * from provider where provider_name='naver'
		Provider provider = providerService.selectByName(client.getProvider());
		member.setProvider(provider);
		memberService.registOrUpdate(member);

		/*------------------------------------------------
		  3) 로그인 처리
		  		- 최초의 로그인 시도자는 회원가입을 처리
		  		- 기존 가입자는, 로그인만 처리 (회원정보 업데이트)
		  		- 세션에 회원정보 저장
		 ------------------------------------------------*/

		session.setAttribute("member", member);

		return "redirect:/";
	}

	// 네이버 로그인 요청 처리(콜백 함수 처리)
	@GetMapping("/login/callback/kakao")
	public String handleKakaoLogin(String code, HttpSession session) {
		log.debug("카카오에서 발급한 임시코드는 {}", code);

		/*------------------------------------------------
		  1) code, client id, client secret 을 구성하여 토큰 발급을 요청
		 ------------------------------------------------*/
		OAuthClient client = oauthClients.get("kakao");

		// 몸체 구성
		MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>(); // 몸체
		param.add("grant_type", "authorization_code"); // 임시 코드를 이용하여 토큰을 요청하겠다는 것을 명시
		param.add("code", code); // 구글로부터 발급받은 임시코드를 그대로 추
		param.add("client_id", client.getClientId()); // 클라이언트 아이디 추가
		param.add("client_secret", client.getClientSecret()); // 클라이언트 시크릿 추가
		param.add("redirect_uri", client.getRedirectUri()); // callback uri

		// 머리 만들기
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		// 몸 + 머리
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(param, headers);

		// 토큰 달라고 요청하기 (구글, 네이버, 카카오, 메타 등 모든 프로바이더가 토큰을 포함한 응답 정보의 내용이 같다..)
		ResponseEntity<OAuthTokenResponse> response = restTemplate.postForEntity(client.getTokenUrl(), request,
				OAuthTokenResponse.class);
		log.debug("카카오가 응답한 토큰 포함 정보는 {}", response);

		OAuthTokenResponse responseBody = response.getBody();

		/*------------------------------------------------
		  2) 발급된 토큰을 이용하여 회원 정보 조회하기
		  
		  
		 ------------------------------------------------*/
		String access_token = responseBody.getAccess_token();
		log.debug("카카오의 토큰은 {}", access_token);

		/*
		 * HttpHeaders userInfoHeaders = new HttpHeaders();
		 * userInfoHeaders.add("Authorization", "Bearer " + access_token);
		 * 
		 * HttpEntity<String> userInfoRequest = new HttpEntity<>("", userInfoHeaders);
		 * // 몸은 비워넣고, 몸과 머리 합쳐 요청 보내기 // GET 방식으로 사용자 정보 요청하기
		 * //restTemplate.exchange(client.getUserInfoUrl(), HttpMethod.GET,
		 * userInfoRequest, 매핑될 클래스); ResponseEntity<NaverUserResponse>
		 * userInfoResponse= restTemplate.exchange(client.getUserInfoUrl(),
		 * HttpMethod.GET, userInfoRequest, NaverUserResponse.class);
		 * 
		 * NaverUserResponse naverUserResponse = userInfoResponse.getBody(); NaverUser
		 * naverUser = naverUserResponse.getResponse();
		 * 
		 * log.debug("고유 id = {}", naverUser.getId()); log.debug("이름 = {}",
		 * naverUser.getName()); log.debug("email = {}", naverUser.getEmail());
		 * 
		 * Member member = new Member(); // empty
		 * member.setProvider_userid(naverUser.getId());
		 * member.setName(naverUser.getName()); member.setEmail(naverUser.getEmail());
		 * 
		 * // select * from provider where provider_name='naver' Provider provider =
		 * providerService.selectByName(client.getProvider());
		 * member.setProvider(provider); memberService.registOrUpdate(member);
		 * 
		 * ------------------------------------------------ 3) 로그인 처리 - 최초의 로그인 시도자는
		 * 회원가입을 처리 - 기존 가입자는, 로그인만 처리 (회원정보 업데이트) - 세션에 회원정보 저장
		 * ------------------------------------------------
		 * 
		 * session.setAttribute("member", member);
		 */

		return "redirect:/";
	}
}
