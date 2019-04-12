import java.util.Scanner;

public class MainController {

	//메인 객체가 업무를 분산시키기 위해 사용하는 위임 객체
	//1. View 객체
	//2. Service 객체
	private static View view;
	private static IService service;
	private static Scanner scan;
	
	static {
		view = new View();
		service = new Service();
		scan = new Scanner(System.in);
	}
	
	public static void main(String[] args) {
		
		view.begin();
		
		boolean loop = true;
		
		while (loop) {
			
			view.menu();
			
			String sel = scan.nextLine();
			
			if (sel.equals("1")) service.add();
			else if (sel.equals("2")) service.list();
			else if (sel.equals("3")) service.edit();
			else if (sel.equals("4")) service.del();
			else if (sel.equals("5")) service.search();
			else loop = false;		
			
		}
		
		view.end();
		
	}
	
}
