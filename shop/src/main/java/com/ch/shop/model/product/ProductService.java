package com.ch.shop.model.product;

import java.util.List;

import com.ch.shop.dto.Product;

public interface ProductService {
	public void regist(Product product);
	public List getList();	// 모든 목록 가져오기
	public List selectBySubCategoryId(int subcategory_id);		// 하위 카테고리에 소속된 상품
	
	// 우리의 경우, 이미지들을 상품의 pk 값을 이용하여 "p23" 형식으로 만들었기 때문에, pk 값만 알면 이미지를 모두 제거할 수 있다.
	public void cancelUpload(Product product);
}
