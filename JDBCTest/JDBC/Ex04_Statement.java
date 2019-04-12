package com.test.jdbc;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;

public class Ex04_Statement {
	
	public static void main(String[] args) {
		
		//*** JDBC의 모든 작업은 기본적으로 자동커밋이 발생한다.(executeUpdate() 호출)
		// > 개발자가 트랜잭션을 직접 제어할 수 있다.
		// 1. 보통 : 그냥 둔다.(자동 커밋)
		// 2. 트랜잭션을 묶어야 하는 업무가 발생하면 : 수동 제어한다.
		
		//Ex04_Statement.java
		//고정값 insert > 입력값 insert : 동적 쿼리
		
		DBUtil util = new DBUtil();
		Connection conn = null;
		Statement stat = null;
		Scanner scan = new Scanner(System.in);
		
		try {
			
			conn = util.connect();
			stat = conn.createStatement();
			
//			System.out.print("이름: ");
//			String name = scan.nextLine();
//			
//			System.out.print("나이: ");
//			String age = scan.nextLine();
//			
//			System.out.print("전화: ");
//			String tel = scan.nextLine();
//			
//			System.out.print("주소: ");
//			String address = scan.nextLine();
			
			
			//자바 <-> 오라클
			//int <-> number //XXXX
			//String <-> varchar2 //XXXXX
			//호환성 0%
			//자바에서 사용하던 데이터를 오라클에게 넘기려면 > 오라클이 이해하는 표현으로 작성
			//검증 방법 > sql 출력 + 복사 > Developer에서 그대로 실행
			//String sql = String.format("insert into tblAddress (seq, name, age, tel, address, regdate) values (address_seq.nextval, '%s', %s, '%s', '%s', default)"
			//				, name, age, tel, address);
			
			//insert into tblAddress (seq, name, age, tel, address, regdate) values (address_seq.nextval, '호호호', 22, '010-9876-9876', 'ㅅtest', default)
			
			
			
			
			
			
//			System.out.print("번호: ");
//			String seq = scan.nextLine();//조건절
//			
//			System.out.print("전화: ");
//			String tel = scan.nextLine();//수정값
//			
//			String sql = String.format("update tblAddress set tel = '%s' where seq = %s"
//										, tel, seq);
			
			
			
			
//			System.out.print("번호: ");
//			String seq = scan.nextLine();//조건절
//			
//			String sql = String.format("delete from tblAddress where seq = %s"
//										, seq);
//			
			
			
			//카페 > 동호회마다 테이블 별도로 생성
			//String sql = "create table tblTest ()";
			String sql = "drop table tblAddress";
			
			
			System.out.println(sql);//권장(쿼리를 날리기전에 먼저 출력해볼 것)
			
			System.out.println(stat.executeUpdate(sql));
			
			conn.close();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	
	}

}
