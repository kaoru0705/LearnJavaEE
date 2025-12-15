package com.ch.shop.test.food;

/* 현실의 요리사를 정의 */
public class Cook {
	
	private FriPan pan;	// has-a 관계
	
	public Cook() {
		pan = new FriPan();
	}
	
	public void makeFood() {
		pan.boil();
	}
}
