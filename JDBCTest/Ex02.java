package com.test.jdbc;

import java.sql.Connection;

public class Ex02 {
	
	public static void main(String[] args) {
		
		DBUtil util = new DBUtil();
		Connection conn = null;
		
		try {

			conn = util.connect();
			//conn = util.connect("211.63.89.xx", "hr", "java1234");
			
			System.out.println(conn.isClosed());//false
			
			conn.close();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
	}

}

