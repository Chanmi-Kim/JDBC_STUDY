import java.util.Scanner;

public class View {
	
	public final static int ADD = 1;
	public final static int LIST = 2;
	public final static int EDIT = 3;
	public final static int DEL = 4;
	public final static int SEARCH = 5;

	public void begin() {
		System.out.println("메모장을 시작합니다.");
	}

	public void end() {
		System.out.println("메모장을 종료합니다.");
	}

	public void menu() {
		
		System.out.println("-------------------");
		System.out.println("[메모장]");
		System.out.println("1. 메모 쓰기");
		System.out.println("2. 메모 읽기");
		System.out.println("3. 메모 수정하기");
		System.out.println("4. 메모 삭제하기");
		System.out.println("5. 메모 검색하기");
		System.out.println("0. 종료");
		System.out.println("-------------------");
		System.out.print("선택: ");
		
	}
	
	//각업무별 제목 출력
	public void title(int n) {
		
		
		
		if (n == View.ADD) { System.out.println("[메모 입력하기]");
		} else if (n == View.LIST) { System.out.println("[메모 목록보기]");
		} else if (n == View.EDIT) { System.out.println("[메모 수정하기]");
		} else if (n == View.DEL) { System.out.println("[메모 삭제하기]");
		} else if (n == View.SEARCH) { System.out.println("[메모 검색하기]");
		}	
	}
	
	public void pause() {
		System.out.print("계속하려면 엔터를 입력하세요.");
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
	}

}
