package com.ch.galleryspring.controller.admin.performance;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ch.galleryspring.exception.PersonException;
import com.ch.galleryspring.exception.UploadException;
import com.ch.galleryspring.model.person.PersonService;

import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
public class PersonController {
	
	@Autowired
	PersonService personService;

	@GetMapping("/admin/performance/person")
	public String person() {
		
		return "admin/performance/person/person";
	}
	
	@GetMapping("/admin/performance/person/registform")
	public String getRegistForm() {
		
		return "admin/performance/person/regist";
	}
	
	@PostMapping("/admin/performance/person/regist")
	@ResponseBody
	/* 내 코드에 문제점 db에 column이 더 늘어난다면? 결국 객체를 file을 저장할 dto를 따로 만들고 regist.jsp에서
	 *  formData.append(`persons[${index}].person_name`, name); 이런식으로 저장해야 한다.
	 *  */
	public Map<String, String> regist(
			@RequestParam("person_name") List<String> nameList,
			@RequestParam("profile_img") List<MultipartFile> imgList) {
		
		for(String name : nameList) {
			log.debug("이름 " + name);
		}
		
		for(MultipartFile img : imgList) {
			log.debug("프로필 이미지명은 " + img.getOriginalFilename());	
		}
		
		try {
			personService.regist(nameList, imgList);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		Map<String, String> body = new HashMap<>();
		body.put("message", "인물등록 성공");
		
		return body;
	}
	

	@GetMapping("/admin/performance/person/list")
	public String getListForm() {
		
		return "admin/performance/person/list";
	}
	
	@ExceptionHandler({PersonException.class, UploadException.class})
	@ResponseBody
	public ResponseEntity<Map<String, String>> handle(Exception e){
		log.debug("인물 등록 시 예외가 발생하여, handler 메서드가 호출됨");
		
		Map<String, String> body = new HashMap<>();
		body.put("message", "인물 등록 실패");
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
	}
	
}
