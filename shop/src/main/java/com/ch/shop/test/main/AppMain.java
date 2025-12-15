package com.ch.shop.test.main;

import com.ch.shop.test.food.Cook;

public class AppMain {
	
	public static void main(String args[]) {
		Cook cook = new Cook();	// 요리사 생성
		cook.makeFood();
	}
}
