package animal;

public class Duck {
	String name ="오리";
	int age = 3;
	static int wing = 2;
	
	public static void main(String[] args) {
		int x = 6;
		Duck d1 = new Duck();
		Duck d2 = new Duck();
		d1.age = 5;
		System.out.println(d1.wing);
		Duck.wing = 4;
		System.out.println(d2.wing);
	}
}

/*
 * JVM 메모리 구조
 * 1. 실행할 때 class load가 이뤄져 Duck이 method영역에 실림
 * 2. main의 파라미터 String[] args가 stack 영역에 실림 args = null; 맨 밑에 쌓인다.
 * 3. x = 6; 바로 그위에 쌓임
 * 4. d1은 stack에 쌓이고 힙 영역에 duck이 들어가고(hash값을 가짐, name, age) 그걸 참조한다.
 * 5. d2도 마찬가지
 * 6. d1.age = 5;
 * 7. d1.wing은 일단 힙영역을 가서 찾아보고 없으니 클래스 원본 코드 영역인 method 영역을 간다.(모든 인스턴스는 클래스 원본 코드 영역을 볼 수 있다.) 
 * main thread 영역이 끝날 때 스택영역은 사라지고 그다음 가비지 컬렉터가 알아서 지움
 * 
 * method 영역 - 실행할 때 class load가 이뤄진다.
 * stack 영역
 * heap 영역
 */