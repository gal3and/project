
public class Student { // DTO������ Ŭ������
	private String name;
	private int no;
	private int kor;
	private int eng;
	// ���� 4���� �Է� ���� �״ѱ� -> �����ڷ� �ϴ� �� ����

	private int tot;
	private int avg;
	private int rank;
	private char grade;
	// ������ ���� ������ �� private�� �ؾ���

	Student(String name, int no, int kor, int eng) {
		this.name = name; // �ν��Ͻ����� = �Ű�����;
		this.no=no; 
		this.kor=kor;
		this.eng=eng;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getKor() {
		return kor;
	}

	public void setKor(int kor) {
		this.kor = kor;
	}

	public int getEng() {
		return eng;
	}

	public void setEng(int eng) {
		this.eng = eng;
	}

	public int getTot() {
		return tot;
	}

	public void setTot(int tot) {
		this.tot = tot;
	}

	public int getAvg() {
		return avg;
	}

	public void setAvg(int avg) {
		this.avg = avg;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public char getGrade() {
		return grade;
	}

	public void setGrade(char grade) {
		this.grade = grade;
	}

}
