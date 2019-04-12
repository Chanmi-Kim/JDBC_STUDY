//계층과 계층간의 데이터 전달용 객체
//주로 데이터베이스의 데이터를 나르는 경우가 많다．
//테이블의 레코드값을 나르는 역할
//ＤＴＯ == 레코드
//멤버변수 == 컬럼(필드）

//DTO의 자격
//1. 멤버 변수는 private을 가진다.
//2. getter/setter를 만든다.
//3. 되도록 추가 멤버(메소드)는 만들지 않는다.(이외의 기능은 추가하지 않는다.)
public class DTO {
	
	private String seq;
	private String name;
	private String memo;
	private String priority;
	private String regdate;
	
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
		
}
