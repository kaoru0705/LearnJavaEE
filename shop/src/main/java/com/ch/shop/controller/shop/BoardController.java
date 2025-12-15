package com.ch.shop.controller.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/*
 * 우리가 제작한 MVC 프레임웍에서는 모든 요청마다 1:1 대응하는 컨트롤러를 매핑하는 방식이었으나,
 * 스프링 MVC는 예를 들어 게시판 1개에 대한 목록, 쓰기, 상세보기, 수정, 삭제에 대해 하나의 컨트롤러로 처리가 가능함
 * 왜? 모든 요청마다 1:1 대응하는 클래스 기반이 아니라, 메서드 기반이기 때문...
 * @Controller 만 붙여도 @Bean에 자동으로 등록 다만 BoardController가 어디사는지는 등록해야 한다. @ComponentScan
 * */
@Controller
public class BoardController {
	
	// 글 목록 페이지 요청 처리
	// 이 URL 요청이 들어오면 이 메서드를 실행하라고 스프링에게 알려주는 주소표
	@RequestMapping("/board/list")
	public ModelAndView getList() {
		System.out.println("클라이언트의 목록 요청 감지");
		return null;
	}
	
	// 글 쓰기 요청 처리
	
	// 글 상세보기 요청 처리
	
	// 글 수정 요청 처리
	
	// 글 삭제 요청 처리
	
}
