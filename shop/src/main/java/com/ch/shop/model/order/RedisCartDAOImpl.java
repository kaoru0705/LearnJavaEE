package com.ch.shop.model.order;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.ch.shop.dto.Cart;
import com.ch.shop.exception.CartException;

import lombok.extern.slf4j.Slf4j;

@Slf4j                                                                                                                                                                                                                                                                                                                
@Repository
public class RedisCartDAOImpl implements RedisCartDAO{
	
	private static final String CART_KEY_PREFIX = "cart:";		// 장바구니의 키로 사용될 접두어
	
	/*
	 	Redis는 테이블을 지원하지 않는 Key - Value 값만을 저장해놓는 저장소라서 장바구니와 관련된 데이터를 넣으려면
	 	장바구니임을 표시해야 한다..
	 	계획/설계 ) cart:member_id(누가?) product_id(어떤 상품을?) ea(몇 개나?)
	 	
	*/
	
	@Autowired               
	private RedisTemplate<String, String> redisTemplate;
	
	// 이 메서드를 호출하면 cart:23, cart:34 의 키값을 만들어주고 반환해줌
	private String getCartKey(int member_id) {
		return CART_KEY_PREFIX + member_id;
	}
	
	@Override
	public void addItem(Cart cart) throws CartException{
		// 장바구니에서 넘어온 값이 갯수가 0이라면.. 작업 중단
		if(cart.getEa() <= 0) {
			throw new CartException("수량은 1개 이상이어야 합니다.");
		}
		
		/* HSET, HGET 등의 명령을 수행하는 객체 생성 */
								/* key	field 	value */
		HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
		
		String key = getCartKey(cart.getMember_id());	// 장바구니에 사용될 키
		
		try {
			Long qnt = hashOps.increment(key, Integer.toString(cart.getProduct_id()), (long)cart.getEa());
			
			if(qnt <= 0) {
				throw new CartException("장바구니 수량이 유효하지 않습니다.");
			}
		}catch(CartException e) {	// 비즈니스 업무적 예외..(예 - 제대로 들어갔다, 안 들어 갔다..)
			throw e;
		}catch (Exception e) {		// 시스템 관련된 비즈니스 업무 이외의 예외
			e.printStackTrace();
			throw new CartException("장바구니 상품 등록 과정에 오류 발생", e);
		}
		
	}

	@Override
	public Map<Integer, Integer> getCart() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAll() {
		// TODO Auto-generated method stub
		
	}

}
