package com.ch.mvcframework.food.model;

/*
 * JavaSE이건, JavaEE, JavaME 등과 상관없이 재사용이 가능한 중립적 코드를 정의하기 위함
 * */
 
public class FoodManager {
	/*
	 * 모든 플랫폼에서 재사용 가능한 객체 = Model 영역 정의
	 */
	public String getAdvice(String food) {
		// 각 영화에 대한 메시지 만들기
		String msg = "선택한 음식이 없음";
		
		if(food != null) {		// 파라미터가 있을 때만
			if(food.equals("경양식 돈까스")){
				msg="한국식 돈까스";
			}else if(food.equals("뼈해장국")){
				msg="살 뜯어 먹기";
			}else if(food.equals("오마카세")){
				msg="다채로운 스시 ";
			}else if(food.equals("수제버거")){
				msg="군침돋는 페티";
			}
		}
		
		return msg;
	}
}
