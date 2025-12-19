package com.ch.shop.controller.admin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ch.shop.dto.Color;
import com.ch.shop.dto.Product;
import com.ch.shop.dto.Size;
import com.ch.shop.model.topcategory.TopCategoryService;

import lombok.extern.slf4j.Slf4j;

/*쇼핑몰의 관리자에서 상품과 관련된 요청을 처리하는 하위 컨트롤러*/
@Controller
@Slf4j
public class ProductController {
	
	// 서비스 보유(느슨하게 보유)
	@Autowired
	private TopCategoryService topCategoryService;
	
	// 상품 등록폼 요청 처리
	@GetMapping("/product/registform")
	public String getRegistForm(Model model) {
		// 3단계: 상품페이지에 출력할 상위카테고리 가져오기
		//List topList = topCategoryService.getList();
		
		
		// 4단계: 결과저장(request 직접 해야 하지만, 스프링에서는 Model객체를 이용하면 간접적으로 저장이됨)
		// jsp까지 topList를 살려서 가야하므로, 포워딩 처리해야 함.. 스프링 개발자가 redirect를 명시하지 않으면 디폴트가 포워딩
		//model.addAttribute("topList", topList);	// request.setAttribute("topList", topList); 와 동일
		
		return "admin/product/regist";
	}
	
	// 상품 등록 요청 처리
	/* 
	 * 클라이언트가 전송한 데이터의 Contnet-Type이 multipart/form-data 즉 텍스트 뿐만 아니라, 바이너리가 포함된 경우
	 * 기존의 HttpServletRequest 객체로 바로 받지 못한다. 따라서 개발자가 스트림을 직접 제어하거나, 아니면 기존의 이미 개발되어진
	 * 파일 업로드 컴포넌트를 이용해야 하는데, 자바 분야에서는 apache에서 개발한 common fileupload 라이브러리를 많이 사용한다.
	 * 따라서 스프링 프레임웍도 apahe file commons fileupload를 내부적으로 사용한다..
	 * 
	 */
	@PostMapping("/product/regist")
	@ResponseBody
	// 파라미터 중 DTO와 일치하지 않아, 자동 매핑이 되지 않을 경우, 개발자가 수동으로 직접 나서면 된다..
	public String regist(Product product, int[] color, int[] size) {
		log.debug("선택하신 하위 카테고리는 " + product.getSubCategory().getSubcategory_id());
		log.debug("상품명 " + product.getProduct_name());
		log.debug("브랜드 " + product.getBrand());
		log.debug("가격 " + product.getPrice());
		log.debug("할인가격 " + product.getDiscount());
		
		List colorList = new ArrayList();
		
		// 색상에 대한 수동 처리
		for(int c: color) {
			log.debug("넘겨받은 색상은 " + c);
			Color dto = new Color();
			dto.setColor_id(c);
			colorList.add(dto);
			
		}
		
		List sizeList = new ArrayList();
		
		// 사이즈에 대한 수동 처리
		for(int s : size) {
			log.debug("넘겨받은 사이즈는 " + s);
			Size dto = new Size();
			dto.setSize_id(s);
			sizeList.add(dto);
		}
		product.setColorList(colorList);
		product.setSizeList(sizeList);
		
		log.debug("colorList " + product.getColorList());
		log.debug("sizeList " + product.getSizeList());
		
		log.debug("간단소개 " + product.getIntroduce());
		log.debug("상세설명 " + product.getDetail());
		
		// 이미지가 자동으로 채워졌는지 확인
		MultipartFile[] photo = product.getPhoto();
		for(MultipartFile p : photo) {
			log.debug("업로드된 파일명은 " + p.getOriginalFilename());
			
			// 메모리의 임시 파일을 실제 원하는 하드 경로에 저장하기
			try {
				p.transferTo(new File("C:/shopdata/product/" + p.getOriginalFilename()));
				log.debug("저장 성공");
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		
		return "ok";
	}
}
