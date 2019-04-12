package com.test.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class Ex01 {
	
	
	public static void main(String[] args) {
		
		/*
		
		JDBC, Java Database Connectivity
		- 자바 응용 프로그램(콘솔, 웹, 모바일 등)과 데이터베이스 시스템(오라클, MySQL, MS-SQL 등)을 연결 시켜주는 중간 계층
		- JDBC 계층, 중간 계층
		
		
		JDBC 사용 환경 세팅하기
		- ojdbc6.jar or ojdbc14.jar 등이 필요하다. //JDBC 드라이버 파일(= JDBC 계층)
		- 오라클 버전에 맞는 ojdbc.jar를 사용한다.
		- C:\oraclexe\app\oracle\product\11.2.0\server\jdbc\lib
		1. 전역
			- C:\Program Files\Java\jre1.8.0_201\lib\ext\ojdbc6.jar 복사
			- ext 폴더내의 모든 jar 파일은 현재 컴퓨터에서 동작하는 모든 자바 프로그램이 자동으로 참조한다.
			- 프로젝트를 집으로 이동 > jar 복사X
			
		2. 지역(권장)
			- 프로젝트 > lib 폴더 생성 > ojdbc6.jar 복사 
				> Build Path > Configure Build Path > Libraries > Add jar
			- 프로젝트 집으로 이동 > jar 복사O
		
		
		[SQL Developer]
		1. 프로그램 실행
		2. 접속
			2.1 접속 정보 입력
				a. 호스트명 : 서버IP or 도메인주소
				b. 포트번호 : 1521
				c. SID : xe
				d. 드라이버 : thin(oci)
				e. 사용자명 : hr
				f. 비밀번호 : java1234
		3. 질의(주업무)
			3.1 SQL 사용(PL/SQL)
			3.2 반환값이 없는 쿼리
				- SELECT를 제외한 나머지 쿼리
			3.3 반환값이 있는 쿼리
				- SELECT
				- 결과셋을 반환하는 쿼리
				- 결과셋 > 업무에 사용하는 데이터
		4. 접속 종료
			4.1 COMMIT or ROLLBACK
			4.2 접속 종료
		
		[JAVA]
		1. 프로그램 실행
		2. 접속 //Connection 클래스
			2.1 접속 정보 입력
				a. 호스트명 : 서버IP or 도메인주소
				b. 포트번호 : 1521
				c. SID : xe
				d. 드라이버 : thin(oci)
				e. 사용자명 : hr
				f. 비밀번호 : java1234
		3. 질의(주업무) //Statement 클래스(3종류)
			3.1 SQL 사용(PL/SQL)
			3.2 반환값이 없는 쿼리 //executeUpdate() 메소드 사용
				- SELECT를 제외한 나머지 쿼리
			3.3 반환값이 있는 쿼리 //ResultSet executeQuery() 메소드 사용
				- SELECT
				- 결과셋을 반환하는 쿼리
				- 결과셋 > 업무에 사용하는 데이터 //ResultSet 클래스
		4. 접속 종료 //Connection 클래스
			4.1 COMMIT or ROLLBACK
			4.2 접속 종료
		*/
		
		//1. DB 접속하고 종료하기
		// - Connection 클래스
		Connection conn = null;
		
		//2. 연결 문자열 생성
		// - 접속 정보 제공
		// - jdbc:oracle:thin //드라이버 종류
		// - @localhost //서버
		// - 1521 //포트번호
		// - xe //SID
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "hr";
		String password = "java1234";
		
		//DB 작업 > 프로그램의 외부 입출력 환경 > 예외 처리 필수
		try {
			
			//3. jar 설치 > 설치한 JDBC 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//4. 접속
			// - Connection 객체 생성 > DB 연결 정보를 가지고 있는 상태
			// - DB 접속
			conn = DriverManager.getConnection(url, id, password);
			
			System.out.println(conn.isClosed()); //false
			
			//5. 질의(업무)
			System.out.println("업무 완료");
			
			//6. 접속 종료(자원 해제 > Clean Up Code)
			conn.close();
			
			System.out.println(conn.isClosed()); //true
			
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		
		
	}//main

}


















