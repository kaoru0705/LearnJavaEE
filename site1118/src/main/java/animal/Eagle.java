package animal;

public class Eagle extends Bird{
	String name = "나는 독수리";
	int age = 3;
	
	public void fly() {
		System.out.println("독수리가 날아요");
	}
	
	public static void main(String[] args) {
		Eagle e1 = new Eagle();
		System.out.println(e1.name);
		e1.fly();
	}
}

/*
 * 상속관계에서의 메모리 관계
 * class file로 존재할 때는 아직은 하드디스크
 * 1. 실행할 때 bird와 eagle이 method 영역에 실림
 * 2. Eagle의 인스턴스가 heap영역에 만들어진다. Eagle 멤버 변수가 복사됨
 * 3. Eagle의 영역이 확장되면서 부모가 갖고 있던 Bird 멤버 변수가 복사됨
 *  
 */