package com.test.memo2;

import java.util.Scanner;

public class UserScanner {
	
	private Scanner scan = new Scanner(System.in);
	
	public int nextInt() {
		
		int n = scan.nextInt();
		scan.skip("\r\n");
		return n;
	}
	
	public int nextInt(String label) {
		
		System.out.print(label + ": ");
		int n = scan.nextInt();
		scan.skip("\r\n");
		return n;
	}
	
	public String nextLine() {
		return scan.nextLine();
	}
	
	public String nextLine(String label) {
		System.out.print(label + ": ");
		return scan.nextLine();
	}
	
	public static void main(String[] args) {
		
		UserScanner ss = new UserScanner();
		int n = ss.nextInt();
		
		int m = ss.nextInt("나이");
		
		String name = ss.nextLine("이름");
		
	}

}
