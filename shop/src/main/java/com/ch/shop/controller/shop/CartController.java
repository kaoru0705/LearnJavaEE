package com.ch.shop.controller.shop;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ch.shop.dto.Cart;
import com.ch.shop.dto.Member;
import com.ch.shop.dto.ResponseMessage;

@Controller
public class CartController {
	
	// 장바구니 메인 요청 처리
	@GetMapping("/cart/main")
	public String getMain(HttpSession session) {
		
		String viewName = "";
		
		// 로그인 세션 처리
		Member member = (Member)session.getAttribute("member");
		
		if(member == null) {	// 로그인하지 않은 경우...
			viewName = "shop/member/login";	// 로그인 폼을 보여줌
		}else {	// 로그인 한 경우..
			viewName = "shop/cart/list";
		}
		
		return viewName;
	}
	
	/*
	 * 장바구니는 임시 저장 기능이므로, 다음의 3가지 기술로 구현이 가능하다
	 * 1) session 
	 * 					단점 - 만일 이 쇼핑몰을 분산환경으로 구현하면, 하나의 쇼핑몰을 여러 대의 서버가 구동하여
	 * 							운영되므로, 세션이 공유될 수 없다.. 사용자가 많을 경우 메모리가 너무 많이 쓰임..
	 * 							소규모나 테스트, 연구분야에 사용.. 실 서버 운영 시 사용되지 않음
	 * 					장점 - 별도의 db가 필요 없으며, 메모리 상에서 구현 가능
	 * 							개발자가 별도로 장바구니를 지우지 않아도, 세션 소멸 시 자동으로 처리됨..
	 * 
	 * 2) DB에 저장 
	 * 					장점 - 원하는 기간만큼 제한없이 저장해놓을 수 있음
	 * 					단점 - 개발자가 주문이 완료되었을 때, 삭제하는 처리를 별도로 해야 함..
	 * 							따라서 사용자가 많을 경우 데이터베이스 용량이 커짐..
	 * 
	 * 3) Redis 데이터베이스 - 가벼운 경량의 메모리 db이다. 기존의 전통적인 RDBM와는 달리, 테이블 등의
	 * 								스키마가 존재하지 않음(테이블, 컬럼 등이 없음)
	 * 								Map 구조로 저장됨..
	 * 								장점 - 메모리 상의 데이터베이스이므로, 속도가 무지 빠름..
	 * 										데이터의 유효기간을 명시할 수 있으므로, 
	 * 										개발자가 별도의 삭제 작업을 하지 않아도 됨(마치 쿠키처럼)
	 * 								단점 - 메모리 용량이 많이 차지 
	 */
	
	/*
	 	만일 @ResponseBody가 붙어 있지 않으면? DispatcherServlet은 InternalResourceViewResolver에게 리턴된 스트링값을 이용하여 
	 	실제 jsp를 얻어오려고 할 것이다 ex) WEB-INF/views/등록성공.jsp
	 */
	@GetMapping("/cart/add")
	@ResponseBody 
	public ResponseEntity<ResponseMessage> addCart(@RequestParam(defaultValue = "0") int product_id, HttpSession session) {
		
		// 클라이언트가 전송한 상품의 product_id, 갯수를 이용하여 Cart 생성하고 보관...
		// 그리고 이 생성된 Cart 인스턴스를 세션에 저장...
		Cart cart = new Cart();
		
		cart.setProduct_id(product_id);
		
		cart.setEa(product_id);				// 넘겨 받을 예정
		cart.setProduct_name(null);		// 상품명을 넘겨 받아야 함
		cart.setFilename(null);
		cart.setPrice(0);
		
		Map map = new HashMap<Integer, Cart>();
		
		map.put(product_id, cart);
		
		session.setAttribute("cart", map);
		
		ResponseMessage msg = new ResponseMessage();
		msg.setMsg("장바구니에 상품이 담겼습니다.");
		
		// 이 시점에 jackson라이브러리를 개발자가 직접 사용하는 것이 아니라, @ResponseBody에 의해 내부적으로 작동함
		
		// 스프링에서 지원하는 HTTP 응답 전용 객체(head + body 구성해줌)
		// ResponseEntity<ResponseMessage>
		// header에 200인 성공과 body로 msg를 갖고가는 ok method 
		return ResponseEntity.ok(msg);
	}
}
