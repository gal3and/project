/*
public class CallByTest2 { //값에 의한 전달
	void swap(int num1, int num2) {

		int num3 = num1;
		num1 = num2;
		num2 = num3;

	}

	public static void main(String[] args) {
		// TODO 반드시 참조에 의한 전달방식을 써야만 하는 경우

		int num1 = 10, num2 = 5, num3;

		System.out.println("두 수의 교환");

		CallByTest2 cbt = new CallByTest2();

		cbt.swap(num1, num2);

		System.out.println(num1 + ", " + num2);
	}

}
*/

/*
public class CallByTest2 { //참조에 의한 전달
	void swap(int num[]) {

		int num3 = num[0];
		num[0] = num[1];
		num[1] = num3; //

	}//주소를 넘기닌깐 main에 있는거 바꾸는거 (100번지에 가서 바꾸는 거임)

	public static void main(String[] args) {
		// TODO 반드시 참조에 의한 전달방식을 써야만 하는 경우

		int num[] = { 10, 5 };
		
		System.out.println("두 수의 교환");
		CallByTest2 cbt = new CallByTest2();
		
		cbt.swap(num); // 주소를 알려줌 : 시작점만알면되잖아
		System.out.println(num[0] + ", " + num[1]);
	}

}

*/

public class CallByTest2 { //참조에 의한 전달
	int num1 = 10, num2 = 5;//인스턴스 변수로 생성
	
	void swap() { //this가 주소를 받음
		int num3 = this.num1;
		this.num1 = this.num2;
		this.num2 = num3;
	}

	public static void main(String[] args) {
		// TODO 반드시 참조에 의한 전달방식을 써야만 하는 경우

		System.out.println("두 수의 교환");

		CallByTest2 cbt = new CallByTest2(); //변수가 이때 생성되면서 , cbt가 주소값을 가짐

		cbt.swap(); //이렇게해도 cbt주소가 생략

		System.out.println(cbt.num1 + ", " + cbt.num2);
	}

}
