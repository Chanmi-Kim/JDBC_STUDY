package com.test.memo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
	
	private static Scanner scan;
	private static Connection conn;
	private static Statement stat;
		
	static {
		scan = new Scanner(System.in);
		
		try {
			
			DBUtil util = new DBUtil();
			conn = util.connect(); //프로그램 시작 시 DB 접속
			stat = conn.createStatement();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		
		boolean loop = true;
		
		System.out.println("메모장을 시작합니다.");
		
		while (loop) {
			System.out.println("-------------------");
			try {
				System.out.printf("[메모장] - %s\n", conn.isClosed() ? "Not Connected" : "Connected");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("1. 메모 쓰기");
			System.out.println("2. 메모 읽기");
			System.out.println("3. 메모 수정하기");
			System.out.println("4. 메모 삭제하기");
			System.out.println("5. 메모 검색하기");
			System.out.println("0. 종료");
			System.out.println("-------------------");
			System.out.print("선택: ");
			
			String sel = scan.nextLine();
			
			if (sel.equals("1")) add();
			else if (sel.equals("2")) list();
			else if (sel.equals("3")) edit();
			else if (sel.equals("4")) del();
			else if (sel.equals("5")) search();
			else loop = false;			
			
		}
		
		System.out.println("메모장을 종료합니다.");
		
		
		try {
			stat.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	private static void add() {
		
		System.out.println("[메모 쓰기]");
		
		System.out.print("작성자: ");
		String name = scan.nextLine();
		name = name.replace("'", "''");
		
		System.out.print("메모: ");
		String memo = scan.nextLine();
		memo = memo.replace("'", "''");
		
		System.out.print("중요도(1.높음, 2.보통, 3.낮음): ");
		String priority = scan.nextLine();
		
		String sql = String.format("insert into tblMemo (seq, name, memo, priority, regdate) values (memo_seq.nextval, '%s', '%s', %s, default)", name, memo, priority);
		
		try {
			
			int result = stat.executeUpdate(sql);
			
			if (result == 1) {
				System.out.println("메모를 작성했습니다.");
			} else {
				System.out.println("메모를 작성하지 못했습니다.");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		scan.nextLine();//pause();
		
	}

	private static void list() {
		
		System.out.println("[메모 읽기]");
		
		System.out.println("[번호]\t[작성자]\t[중요도]\t[날짜]\t\t[메모내용]");
		
		//String sql = "select * from tblMemo order by seq desc";
		//String sql = "select seq, name, memo, to_char(regdate, 'hh24:mi:ss') as \"regtime\", case when priority = 1 then '높음' when priority = 2 then '보통' when priority = 3 then '낮음' end as \"priorityLabel\" from tblMemo m order by seq desc";
		
		//String sql = "select seq, name, memo, case when to_char(regdate, 'yyyymmdd') = to_char(sysdate, 'yyyymmdd') then to_char(regdate, 'hh24:mi:ss') else to_char(regdate, 'yyyy-mm-dd') end as \"regtime\", case when priority = 1 then '높음' when priority = 2 then '보통' when priority = 3 then '낮음' end as \"priorityLabel\" from tblMemo m order by seq desc";
		String sql = "select * from vwMemo";
		
		try {
			
			ResultSet rs = stat.executeQuery(sql);
			
			while (rs.next()) {
				//레코드 1개 > 메모 1건 > 출력
				//System.out.println(rs.getString("memo"));
				System.out.printf("%s\t%s\t\t%s\t\t%s\t%s\n"
									, rs.getString("seq")
									, rs.getString("name")
									, rs.getString("priorityLabel")
									, rs.getString("regtime")
									, rs.getString("memo"));
			}
			
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		scan.nextLine();
		
	}//list()

	private static void edit() {
	
		//목록 출력 > 선택 > 메모 내용 출력 > 수정 입력 > 수정
		System.out.println("[메모 수정하기]");
		
		System.out.println("[번호]\t[작성자]\t[메모 내용]");
		
		String sql = "select seq, name, memo from vwMemo";
		
		try {
			
			ResultSet rs = stat.executeQuery(sql);
			
			while (rs.next()) {
				System.out.printf("%s\t%s\t\t%s\n"
								, rs.getString("seq")
								, rs.getString("name")
								, rs.getString("memo"));
			}
			
			rs.close();
			
			System.out.print("수정할 번호: ");
			String seq = scan.nextLine();
			
			
			sql = String.format("select * from tblMemo where seq = %s", seq);
			//System.out.println(sql);
			
			rs = stat.executeQuery(sql);
			
			String name = "";
			String memo = "";
			String priority = "";
			
			if (rs.next()) {
				name = rs.getString("name");
				memo = rs.getString("memo");
				priority = rs.getString("priority");
				System.out.println(name);
			}
			
			rs.close();
			
			System.out.println("기존 작성자: " + name);
			System.out.print("새로운 작성자: ");
			String temp = scan.nextLine();
			
			if (!temp.equals("")) { //수정
				name = temp;//사용자가 입력한 이름으로 교환
			}
			
			
			System.out.println("기존 메모: " + memo);
			System.out.print("새로운 메모: ");
			temp = scan.nextLine();
			
			if (!temp.equals("")) {
				memo = temp;//사용자가 입력한 메모로 교환
			}
			
			
			
			System.out.println("기존 중요도: " + priority);
			System.out.print("새로운 중요도: ");
			temp = scan.nextLine();
			
			if (!temp.equals("")) {
				priority = temp;//사용자가 입력한 메모로 교환
			}
			
			
			
			//변수 3개 > 사용자 의도 반영 > 기존값 or 새값
			sql = String.format("update tblMemo set name = '%s', memo = '%s'"
					+ ", priority = %s where seq = %s", name, memo, priority, seq);
			System.out.println(sql);
			
			if (stat.executeUpdate(sql) == 1) {
				System.out.println("메모를 수정했습니다.");
			} else {
				System.out.println("메모를 수정하지 못했습니다.");
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		scan.nextLine();
		
	}

	private static void del() {
		
		//목록 출력 > 선택 > 삭제
		System.out.println("[메모 삭제하기]");
		
		System.out.println("[번호]\t[작성자]\t[메모 내용]");
		
		String sql = "select seq, name, memo from vwMemo";
		
		try {
			
			ResultSet rs = stat.executeQuery(sql);
			
			while (rs.next()) {
				System.out.printf("%s\t%s\t\t%s\n"
								, rs.getString("seq")
								, rs.getString("name")
								, rs.getString("memo"));
			}
			
			rs.close();
			
			System.out.print("삭제할 번호: ");
			String seq = scan.nextLine();
			
			sql = String.format("delete from tblMemo where seq = %s", seq);
			
			if (stat.executeUpdate(sql) == 1) {
				System.out.println("메모를 삭제했습니다.");
			} else {
				System.out.println("메모를 삭제하지 못했습니다.");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		scan.nextLine();
		
	}

	private static void search() {
		
		//검색어 입력 > 목록 출력(where)
		System.out.println("[메모 검색하기]");
		
		System.out.print("검색어 입력: ");//메모 내용 검색
		String word = scan.nextLine();
		
		System.out.println("[번호]\t[작성자]\t[중요도]\t[날짜]\t\t[메모내용]");
		
		String sql = String.format("select * from vwMemo where memo like '%%%s%%'", word);
		
		try {
			
			ResultSet rs = stat.executeQuery(sql);
			
			while (rs.next()) {
				System.out.printf("%s\t%s\t\t%s\t\t%s\t%s\n"
									, rs.getString("seq")
									, rs.getString("name")
									, rs.getString("priorityLabel")
									, rs.getString("regtime")
									, rs.getString("memo"));
			}
			
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		scan.nextLine();
		
	}
	
}
