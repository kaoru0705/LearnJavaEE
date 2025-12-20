package com.ch.galleryspring.controller.admin.performance;


import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
public class PersonController {

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
	public String regist(
			@RequestParam("person_name") List<String> nameList,
		    @RequestParam("profile_img") List<MultipartFile> imgList
		    ) {
		for(String name : nameList) {
			log.debug("이름 " + name);
		}
		for(MultipartFile img : imgList) {
			log.debug("프로필 이미지명은 " + img.getOriginalFilename());
			
		}
		
		return "ok";
	}
	

	@GetMapping("/admin/performance/person/list")
	public String getListForm() {
		
		return "admin/performance/person/list";
	}
	
}
