package com.ch.tickethub.controller.admin.performance;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ch.tickethub.exception.UploadException;
import com.ch.tickethub.exception.WorkException;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class WorkController {
	
	@Autowired


	@GetMapping("/performance/work")
	public String person() {
		
		return "admin/performance/work/work";
	}
	
	@GetMapping("/performance/work/registform")
	public String getRegistForm() {
		
		return "admin/performance/work/regist";
	}
	
	@PostMapping("/performance/work/regist")
	@ResponseBody
	public Map<String, String> regist(){

		
		Map<String, String> body = new HashMap<>();
		body.put("message", "인물등록 성공");
		
		return body;
	}
	

	@GetMapping("/performance/work/listpage")
	public String getListPage() {
		
		return "admin/performance/work/list";
	}
	
	// MissingServletRequestParameterException.class 값을 제대로 입력 받지 못했을 때의 에러. 난 이것도 처리했다.
	@ExceptionHandler({WorkException.class, UploadException.class, MissingServletRequestParameterException.class})
	@ResponseBody
	public ResponseEntity<Map<String, String>> handle(Exception e){
		log.debug("인물 등록 시 예외가 발생하여, handler 메서드가 호출됨");
		
		Map<String, String> body = new HashMap<>();
		body.put("message", "인물 등록 실패");
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
	}
	
}