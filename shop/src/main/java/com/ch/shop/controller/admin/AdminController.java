package com.ch.shop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// 이 컨트롤러는 관리자 메인과 관련된 요청을 처리하는 하위 컨트롤러임
@Controller	// ComponentScan에 의해 자동으로 인스턴스를 생성해달라!!
					// 그리거 위해서는 개발자가 이 클래스가 검색될 수 있도록 제대로 된 패키지명을 등록해야 함..
public class AdminController {

	// 관리자 모드의 메인인 대시보드 요청을 처리
	@GetMapping("/admin/main")
	public String main() {
		// 3단계 -무
		// 4단계 - 무
		return "admin/index";
	}
}