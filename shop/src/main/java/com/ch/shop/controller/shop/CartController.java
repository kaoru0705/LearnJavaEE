package com.ch.shop.controller.shop;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.ch.shop.dto.Member;

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
}
