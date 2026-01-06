package com.ch.shop.controller.shop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/*
	로그인 한 회원에게만 제공되는 서비스를 판단하여, 해당 유저가 로그인하지 않았을 경우,
	로그인 폼을 강제로 보여주는 처리를 위해서는, 세션 체크 코드를 작성해야 한다.
	하지만, 회원에게만 제공되는 요청을 처리하는 모든 컨트롤러마다 세션 체크 코드를 넣으면 코드 중복이 발생하므로,
	유지보수성을 위해서는 스프링에서 제공하는 인터셉터를 이용하면 된다. 
 */

public class LoginCheckInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		// 현재 요청에 연계된 세션 얻기
		HttpSession session = request.getSession();
		
		// 로그인 하지 않았을 경우, 가던 길 가는 게 아니라, 로그인 폼으로 강제 전환
		if(session == null || session.getAttribute("member") == null) {
			
			String asyncHeader = request.getHeader("X-Requested-With");
			
			if(asyncHeader != null && asyncHeader.equals("XMLHttpRequest")) {		// 비동기로 요청이 들어온 경우... 응답 메시지로 처리...(JSON)
				response.setContentType("application/json; charset=UTF-8");
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);		// 서버의 응답 상태코드	401 권한없음
				/*
				 {
				 	"msg":"로그인이 필요한 서비스입니다 "
				 }
				 */
				
				response.getWriter().write("{ \"msg\" : \"로그인이 필요한 서비스입니다\" }");
				
			}else {	// 동기로 요청이 들어온 경우, 응답페이지로 처리 ...
				response.sendRedirect("/member/loginform");		// 동기로 들어왔을 때의 처리				
			}
			
			return false;
		}
		
		// 원래 요청을 그대로 진행하고 싶다면 true, 진행을 막으려면 false
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
