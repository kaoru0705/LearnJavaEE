package com.ch.shop.controller.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ch.shop.model.product.ProductService;

@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	// 상품 목록 요청 처리
	@GetMapping("/product/list")
	public String getProductList(Model model, @RequestParam(name="subcategory_id", defaultValue="0") int subcategory_id) {
		// 파라미터 값이 0인 경우, 사용자는 모든 상품을 보고 싶은 것임.. 0이 아니면 해당 하위 카테고리에 소속된 상품만 나열...
		List productList = productService.selectBySubCategoryId(subcategory_id);			// 3단계: 일 시키기
		
		model.addAttribute("productList", productList);	// 4단계: jsp에서 보여질 결과 저장
		
		return "shop/product/list";
	}
}
