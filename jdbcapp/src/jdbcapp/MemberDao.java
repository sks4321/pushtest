package jdbcapp;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
// 주석을 작성해서 test 해보는 부분
public class MemberDao {
	private String url;//외부파일에 둘겂이다.
	private String dbid;//외부파일에 둘겂이다.
	private String dbpw;//외부파일에 둘겂이다.
	private void dbpropertiesInit() throws IOException{
		FileInputStream fis = new FileInputStream("d:\\db.properties");
		Properties pro = new Properties(); // ? = ? 이런식으로 만들어져 있는 파일을 읽기 쉽다.
		pro.load(fis);// 어트리뷰트로  바뀜
		this.url = pro.getProperty("url");
		this.dbid = pro.getProperty("dbid");
		this.dbpw = pro.getProperty("dbpw");
	}
	public Member selectMemberbyID(String id) throws ClassNotFoundException, SQLException, IOException{
		this.dbpropertiesInit();
		System.out.println(this.dbid);
		System.out.println(this.dbpw);
		//Properties DB정보 가져옵니다(내부적으로 input...
		Class.forName("oracle.jdbc.OracleDriver");
		Connection conn = DriverManager.getConnection(this.url, this.dbid, this.dbpw);
		String sql = "SELECT * FROM oracle_member WHERE ora_id = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, id);
		ResultSet rs = pstmt.executeQuery();
		Member member = null;
		if(rs.next()){
			member = new Member();
			member.setOra_id(rs.getString("ora_id"));
			member.setOra_pw(rs.getString("ora_pw"));
			member.setOra_level(rs.getString("ora_level"));
			member.setOra_name(rs.getString("ora_name"));
			member.setOra_email(rs.getString("ora_email"));
		}
		return member;
	}
	//유니테스트 함.
	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException{
		MemberDao mda = new MemberDao();
		Member m = mda.selectMemberbyID("id001");
		System.out.println(m.getOra_id().equals("id001"));
		System.out.println(m.getOra_pw());
		System.out.println(m.getOra_level());
		System.out.println(m.getOra_name());
		System.out.println(m.getOra_email());

	}
}
