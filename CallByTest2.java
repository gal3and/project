/*
public class CallByTest2 { //���� ���� ����
	void swap(int num1, int num2) {

		int num3 = num1;
		num1 = num2;
		num2 = num3;

	}

	public static void main(String[] args) {
		// TODO �ݵ�� ������ ���� ���޹���� ��߸� �ϴ� ���

		int num1 = 10, num2 = 5, num3;

		System.out.println("�� ���� ��ȯ");

		CallByTest2 cbt = new CallByTest2();

		cbt.swap(num1, num2);

		System.out.println(num1 + ", " + num2);
	}

}
*/

/*
public class CallByTest2 { //������ ���� ����
	void swap(int num[]) {

		int num3 = num[0];
		num[0] = num[1];
		num[1] = num3; //

	}//�ּҸ� �ѱ�ѱ� main�� �ִ°� �ٲٴ°� (100������ ���� �ٲٴ� ����)

	public static void main(String[] args) {
		// TODO �ݵ�� ������ ���� ���޹���� ��߸� �ϴ� ���

		int num[] = { 10, 5 };
		
		System.out.println("�� ���� ��ȯ");
		CallByTest2 cbt = new CallByTest2();
		
		cbt.swap(num); // �ּҸ� �˷��� : ���������˸���ݾ�
		System.out.println(num[0] + ", " + num[1]);
	}

}

*/

public class CallByTest2 { //������ ���� ����
	int num1 = 10, num2 = 5;//�ν��Ͻ� ������ ����
	
	void swap() { //this�� �ּҸ� ����
		int num3 = this.num1;
		this.num1 = this.num2;
		this.num2 = num3;
	}

	public static void main(String[] args) {
		// TODO �ݵ�� ������ ���� ���޹���� ��߸� �ϴ� ���

		System.out.println("�� ���� ��ȯ");

		CallByTest2 cbt = new CallByTest2(); //������ �̶� �����Ǹ鼭 , cbt�� �ּҰ��� ����

		cbt.swap(); //�̷����ص� cbt�ּҰ� ����

		System.out.println(cbt.num1 + ", " + cbt.num2);
	}

}
