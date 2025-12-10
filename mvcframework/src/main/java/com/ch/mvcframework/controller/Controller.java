package com.ch.mvcframework.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * 일반
 * 추상: 자식에게 추상메서드를 구현 강제할 수 있다는 장점은 있으나, 자식 클래스가 이미 누군가를
 * 			상속받았을 경우, 다중상속이 적용될 수 없으므로, 현재에는 많이 사용되지 않음.
 * 인터페이스: 클래스가 아닌 오직 추상메서드와 상수만을 보유할 수 있는  대상이기 때문에
 * 				자바의 다중상속 문제를 피해갈 수 있음
 */
public interface Controller {
	// 앞으로 이 인터페이스를 구현하는 모든 자식 객체가 반드시 아래의 메서드명을 구현한 것을 강제할 수 있으므로
	// 메서드 명을 통일할 수 있다는 장점이 있음
	// 또한 자식마다, 구현 내용이 다르므로, 이 시점에 아래의 메서드의 내용을 채울 수도 없고, 채워서도 안 된다.
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
	
	// 모든 하위 컨트롤러가 구현해야 할 메서드 추가
	// 그리고 메서드의 몸체는 두지 않는다. 즉, 불완전하게 해야, 추상메서드가 되고, 이 메서드에 대한 완성은 자식들에게 맡김(구현 강제)
	public String getViewName();
	
	// 하위 컨트롤러가 jsp까지 데이터를 살려서 유지할 일이 있을 경우엔 요청에 대한 응답을 하면 안되며
	// 반드시 포워딩으로 처리가 되어야 한다. 따라서 하위 컨트롤러는 DispatcherServlet에게 해당 요청이 포워딩 대상인지 아닌지에 대한
	// 판단을 위한 논리값을 반환하는 메서드를 제공해주어야 한다..
	
	public boolean isForward();
}
