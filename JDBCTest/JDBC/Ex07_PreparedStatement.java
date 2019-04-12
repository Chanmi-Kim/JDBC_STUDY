package com.test.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Ex07_PreparedStatement {
	
	public static void main(String[] args) {
		
		//Ex07_PreparedStatement.java
		
		//tblinsa. 이름 + 부서 명단 출력
		
		DBUtil util = new DBUtil();
		Connection conn = null;
		Statement stat = null;
		PreparedStatement pstat = null;
		
		try {
			
			conn = util.connect();
			stat = conn.createStatement();
			
			String sql = "select buseo, jikwi, (select name from tblinsa where buseo = a.buseo and jikwi = '부장' and rownum=1) as name from tblinsa a where jikwi = '부장' group by buseo, jikwi order by buseo, jikwi";
			
			ResultSet rs = stat.executeQuery(sql);
			
			//하위 쿼리 객체
			sql = "select * from tblinsa where buseo = ? and jikwi <> '부장'";
			pstat = conn.prepareStatement(sql);
			
			
			while (rs.next()) {
				System.out.printf("[%s]\n부장: %s\n"
									, rs.getString("buseo")
									, rs.getString("name"));
				
				//또 다른 하위 쿼리 실행
				// - 현재 부서에 속한 나머지 직원 명단 가져오기
				//pstat = conn.prepareStatement(sql);
				pstat.setString(1, rs.getString("buseo"));
				
				
				ResultSet subrs = pstat.executeQuery();
				
				while (subrs.next()) {
					System.out.println("  - " + subrs.getString("name"));
				}
				
				subrs.close();
				//pstat.close();
			}
			
			pstat.close();
			rs.close();
			stat.close();
			conn.close();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		
		
	}

}
