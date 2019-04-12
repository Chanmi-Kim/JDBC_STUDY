package com.test.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class Ex05_select {
	
	public static void main(String[] args) {
		
		//Ex05_select.java
		//m1();
		//m2();
		//m3();
		//m4();
		//m5();
		//m6();
		//m7();
		m8();
		
	}

	private static void m8() {
		
		DBUtil util = new DBUtil();
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;

		try {

			conn = util.connect();
			stat = conn.createStatement();

			String sql = "select * from tblinsa where num = 1001";
			rs = stat.executeQuery(sql);
			
			
			//컬럼 이야기
			if (rs.next()) {
				//1. 컬럼값 얻기(**********************)
				System.out.println(rs.getString(1)); //num
				System.out.println(rs.getString("name"));
				
				//2. 레코드(컬럼) 정보 얻기
				ResultSetMetaData meta = rs.getMetaData();
				System.out.println(meta.getColumnCount()); //결과셋의 컬럼 수
				
				for (int i=1; i<=meta.getColumnCount(); i++) {
					System.out.println(meta.getColumnLabel(i));
					System.out.println(meta.getColumnTypeName(i));
					System.out.println(meta.getColumnDisplaySize(i));
					System.out.println(rs.getString(i));//모든 컬럼 접근
					System.out.println();
				}
				
				
			}
			

			rs.close();
			stat.close();
			conn.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
	}

	private static void m7() {
		
		try {
			
			//1. 기획부 직원 명단 출력
			//2. 인원수 출력
			
			DBUtil util = new DBUtil();
			Connection conn = util.connect();
			Statement stat = conn.createStatement();
			
			String sql = "select * from tblinsa where buseo = '기획부'";
			
			//** ResultSet은 본인 참조하고 있는 결과 테이블의 행 개수를 모른다.(알 수 있는 방법이 없다.)
			ResultSet rs = stat.executeQuery(sql);
			
			//레코드 1개 : if (rs.next())
			//레코드 N개 : while (rs.next())
			
			int cnt = 0;
			
			
			while (rs.next()) {
				cnt++;
			}
			System.out.println("인원수 : " + cnt);
			
			
			String sql2 = "select count(*) as cnt from tblinsa where buseo = '기획부'";
			//다른 쿼리를 날려야 하는 상황에서 Connection, Statement, ResultSet 재사용성 문제?
			//1. Connection : 재사용 가능
			//2. Statement : 재사용 가능(SQL 종속적이지 않다. SQL 교체 가능)
			//3. ResultSet : 재사용 가능. 해당 결과를 다 소비하기 전에 교체하면 안된다. > 쿼리 1개당 ResultSet 1개를 따로 만드는 것을 추천
			ResultSet rs2 = stat.executeQuery(sql2);//별도의 질의 결과
			
			if (rs2.next()) {
				System.out.println("총 직원수 : " + rs2.getString("cnt"));
			}
			
			rs = stat.executeQuery(sql);//다시 쿼리 실행(커서의 위치를 다시 BOF 이동)
			while (rs.next()) {
				//1바퀴 > 레코드 1개 > rs가 가르키고 있음
				System.out.printf("[%s] %s - %s\n"
									, rs.getString("buseo")
									, rs.getString("name")
									, rs.getString("jikwi"));
				//cnt++;
			}
			//System.out.println("인원수 : " + cnt);
			
			rs.close();
			stat.close();
			conn.close();
			
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
	}

	private static void m6() {
		
		//다중 레코드 반환받기(다중 행)
		DBUtil util = new DBUtil();
		Connection conn = null; //변수
		Statement stat = null;
		ResultSet rs = null;

		try {

			conn = util.connect();//접속
			
			stat = conn.createStatement();

			String sql = String.format("select name, buseo, jikwi from tblinsa order by name asc");
			rs = stat.executeQuery(sql);
			
//			rs.next();
//			System.out.println(rs.getString("name"));
//
//			rs.next();
//			System.out.println(rs.getString("name"));
			
			//boolean loop = true;
			
			while (rs.next()) {
				System.out.print(rs.getString("name") + ", ");
				System.out.print(rs.getString("buseo") + ", ");
				System.out.println(rs.getString("jikwi"));
			}

			rs.close();
			stat.close();
			conn.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		
		
		
	}

	private static void m5() {
		
		DBUtil util = new DBUtil();
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;

		try {

			conn = util.connect();
			stat = conn.createStatement();

			//Text Query : DB 클라언트툴에서 작성해서 복붙
			//조인할 때와 뷰 작성 시에 자주 발생 : 모호한 열이름 > 별칭으로 구분시킬것
			String sql = String.format("select m.name as mname, w.name as wname from tblMen m inner join tblWomen w on m.name = w.couple where rownum = 1");
			rs = stat.executeQuery(sql);
			
			if (rs.next()) {
				//남자 이름 출력
				System.out.println(rs.getString("mname"));
				//여자 이름 출력
				System.out.println(rs.getString("wname"));
			}

			rs.close();
			stat.close();
			conn.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
	}

	private static void m4() {
		
		//seq 입력 > 1개 레코드의 여러 컬럼 출력하기
		//select * from tblinsa where num = 1001;
		String num = "1001";
		
		DBUtil util = new DBUtil();
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		
		try {
			
			conn = util.connect();
			stat = conn.createStatement();
			
			String sql = "select a.*, basicpay + sudang as total from tblinsa a where num = " + num;
			rs = stat.executeQuery(sql);
			
			if (rs.next()) {
				
				//컬럼 여러개
				String name = rs.getString("name");
				String buseo = rs.getString("buseo");
				String jikwi = rs.getString("jikwi");
				int basicpay = rs.getInt("basicpay");
				int sudang = rs.getInt("sudang");
				//int total = rs.getInt("basicpay + sudang");
				int total = rs.getInt("total");
				
				System.out.println(name);
				System.out.println(buseo);
				System.out.println(jikwi);
				System.out.println(basicpay + sudang);
				System.out.println(total);
				
				rs.close();
				stat.close();
				conn.close();
				
			} else {
				System.out.printf("직원 번호 %s인 직원은 존재하지 않습니다.\n", num);
			}
			
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
	}

	private static void m3() {
		
		//날짜시간형 반환
		// > select > 1행 1열 반환
		DBUtil util = new DBUtil();
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		
		try {
			
			conn = util.connect();
			stat = conn.createStatement();
			
			String sql = "select min(completedate) as cdate from tblTodo";
			rs = stat.executeQuery(sql);
			
			if (rs.next()) {
				
				//오라클의 date형 > 자바의 어떤 자료형? : String(권장)
				//Date cdate = rs.getDate("cdate");
				//Date cdate = rs.getDate("cdate");
				//System.out.printf("%tF %tT\n", cdate, cdate);
				
				String cdate = rs.getString("cdate"); //사용 빈도 높음(대부분출력용도사용)
				System.out.println(cdate);
				System.out.println(cdate.split(" ")[0]);
				System.out.println(cdate.split(" ")[1]);
				
				//[오라클]		->		[자바]
				//number		->		getInt() : 추가 산술 연산 필요 O
				//number		-> 		getDouble() : 추가 산술 연산 필요 O
				//number		-> 		getString() : 추가 산술 연산 필요 X + 출력
				//varchar2		->		getString()
				//date			-> 		getString()
				
				
			}
			
			rs.close();
			stat.close();
			conn.close();			
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
	}

	private static void m2() {
		
		//단일값 반환
		// > select > 1행 1열 반환(문자열)
		//23번 주소록의 이름 가져오기
		DBUtil util = new DBUtil();
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		
		try {
			
			conn = util.connect();
			stat = conn.createStatement();
			
			
			//java.sql.SQLSyntaxErrorException: ORA-00923: FROM keyword not found where expected
			String sql = "select name from tblAddress where seq = 23";
			rs = stat.executeQuery(sql);
			
			
			//rs.next();
			if (rs.next()) {
				//java.sql.SQLException: 결과 집합을 모두 소모했음
				String name = rs.getString("name");
				System.out.println(name);
			}
			
			rs.close();
			stat.close();
			conn.close();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		
		
	}

	private static void m1() {
		
		//단일값 반환
		// > select > 1행 1열 반환(숫자)
		DBUtil util = new DBUtil();
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null; //select 결과 테이블 참조 객체(구조가 cursor와 유사함)
		
		try {
			
			conn = util.connect();
			
			if (!conn.isClosed()) {
				
				stat = conn.createStatement();
				String sql = "select count(*) as cnt from tblAddress";
				
				//반환값 없는 쿼리
				//int result = stat.executeUpdate(sql);
				
				//반환값 있는 쿼리
				rs = stat.executeQuery(sql);
				
				//rs : select 결과값
				rs.next(); //커서를 아래로(다음 레코드로) 한칸 전진
				
				//rs.getXXX(특정컬럼)
				//1. rs.getInt(index) : 결과셋의 컬럼 위치(index) 1부터 시작(****)
				//2. rs.getInt("컬럼명") : 결과셋의 컬럼명
				
				//int cnt = rs.getInt(1); //***
				//int cnt = rs.getInt("count(*)"); //절대 사용 금지 > Alias 사용
				//int cnt = rs.getInt("cnt"); //************************************
				String cnt = rs.getString("cnt");
				
				//많이 사용
				//rs.getInt()
				//rs.getDouble()
				//rs.getString()				
				
				System.out.println("주소록 행 개수 : " + cnt);
				
				
				//자원해제
				rs.close();
				stat.close();
				conn.close();
				
				
			} else {
				System.out.println("관리자에게 연락하세요.");
			}
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		
	}

}
