import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

//데이터 처리 담당자
//주로 Service 객체가 DAO를 호출하는 경우가 많다. > 메소드(업무) 단위 호출
public class DAO {
	
	private Connection conn;
	private Statement stat;
	private PreparedStatement pstat;

	//초기화(선행 작업)
	// - DB 접속
	public DAO() {
		
		try {
			
			DBUtil util = new DBUtil();
			this.conn = util.connect();
			this.stat = conn.createStatement();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("DAO.Constructor");
		}
		
	}
	
	public boolean isConnected() {
		try {
			return !this.conn.isClosed();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void close() {
		try {
			
			this.conn.close();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	//컨트롤러 > 서비스 > DAO
	public int add(DTO dto) {
		
		//1. stat or pstat : 매개변수 유무
		String sql = "insert into tblMemo (seq, name, memo, priority, regdate) values (memo_seq.nextval, ?, ?, ?, default)";
		
		try {
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, dto.getName());
			pstat.setString(2, dto.getMemo());
			pstat.setString(3, dto.getPriority());
			
			return pstat.executeUpdate();			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return 0;
	}

	public ArrayList<DTO> list() {
		
		try {
			
			String sql = "select * from vwMemo";
			
			ResultSet rs = stat.executeQuery(sql);
			
			//ResultSet을 다른 객체에게 전달하기 위해 그에 준하는 자료형으로 변경 > ArrayList<DTO>
			//ResultSet == 테이블 == ArrayList<DTO>
			//레코드 == 행 == DTO
			//컬럼 = 값 == DTO의 멤버변수
			
						
			ArrayList<DTO> list = new ArrayList<DTO>();
			
			//rs -> (복사) -> list
			while (rs.next()) {
				//레코드 1개 -> DTO 1개
				DTO dto = new DTO();
				
				dto.setSeq(rs.getString("seq")); //컬럼값 -> DTO 멤버변수
				dto.setName(rs.getString("name"));
				dto.setMemo(rs.getString("memo"));
				dto.setPriority(rs.getString("priorityLabel"));
				dto.setRegdate(rs.getString("regtime"));
				
				list.add(dto);				r
			}
			
			return list;//DTO
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return null;
		
	}

	public ArrayList<DTO> elist() {
		try {
			
			String sql = "select seq, name, memo from vwMemo";
			
			ResultSet rs = stat.executeQuery(sql);
				
			ArrayList<DTO> list = new ArrayList<DTO>();
			
			while (rs.next()) {
				//레코드 1개 -> DTO 1개
				DTO dto = new DTO();
				
				dto.setSeq(rs.getString("seq"));
				dto.setName(rs.getString("name"));
				dto.setMemo(rs.getString("memo"));
				
				list.add(dto);				
			}
			
			return list;//DTO
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return null;
	}

	public DTO get(String seq) {
		
		try {
			
			//sql = String.format("select * from tblMemo where seq = %s", seq);
			String sql = "select * from tblMemo where seq = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			
			ResultSet rs = pstat.executeQuery();
			
			DTO dto = new DTO();//복사용
			
			if (rs.next()) {
				dto.setSeq(rs.getString("seq"));
				dto.setName(rs.getString("name"));
				dto.setMemo(rs.getString("memo"));
				dto.setPriority(rs.getString("priority"));
				dto.setRegdate(rs.getString("regdate"));
				
				return dto;//return 빼먹지 말것!!!!!!!!!!!!!!!!!!!!!
			}
			
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return null;
	}

	public int edit(DTO dto) {
		
		try {
			
			String sql = "update tblMemo set name = ?, memo = ?, priority = ?"
							+ " where seq = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, dto.getName());
			pstat.setString(2, dto.getMemo());
			pstat.setString(3, dto.getPriority());
			pstat.setString(4, dto.getSeq());
			
			return pstat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return 0;
	}

	
	public int del(String seq) {
		
		try {
			
			String sql = "delete from tblMemo where seq = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			
			return pstat.executeUpdate();			
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return 0;
	}

	public ArrayList<DTO> list(String word) {
		
		try {
			
			String sql = "select * from vwMemo where memo like '%' || ? || '%'";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, word);
			
			ResultSet rs = pstat.executeQuery();			
						
			ArrayList<DTO> list = new ArrayList<DTO>();
			
			while (rs.next()) {
				DTO dto = new DTO();
				
				dto.setSeq(rs.getString("seq"));
				dto.setName(rs.getString("name"));
				dto.setMemo(rs.getString("memo"));
				dto.setPriority(rs.getString("priorityLabel"));
				dto.setRegdate(rs.getString("regtime"));
				
				list.add(dto);				
			}
			
			return list;//DTO
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return null;
	}

	public ArrayList<DTO> aaa() {
		
try {
			
			String sql = "select * from vwMemo where memo like '%' || ? || '%'";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, word);
			
			ResultSet rs = pstat.executeQuery();			
						
			ArrayList<DTO> list = new ArrayList<DTO>();
			
			while (rs.next()) {
				DTO dto = new DTO();
				
				dto.setSeq(rs.getString("seq"));
				dto.setName(rs.getString("name"));
				dto.setMemo(rs.getString("memo"));
				dto.setPriority(rs.getString("priorityLabel"));
				dto.setRegdate(rs.getString("regtime"));
				
				list.add(dto);				
			}
			
			
			return list;
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
	}
	
}
