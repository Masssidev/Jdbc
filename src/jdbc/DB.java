//***************************
 // 파일명: DB.java
 // 작성자: 마재희
 // 작성일: 2017-12-22
 // 내용: Connection
 //***************************
package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
	private static Connection con = null;

	public static Connection makeConnection() {

		if (con != null)
			return con;
		else {
			String url = "jdbc:mysql://localhost/projects";
			try {
				Class.forName("com.mysql.jdbc.Driver");
				System.out.println("데이터베이스 연결중 ...");
				con = DriverManager.getConnection(url, "puser", "1234");
				System.out.println("데이터베이스 연결 성공");
			} catch (ClassNotFoundException e) {
				System.out.println("JDBC 드라이버를 찾지 못했습니다...");
			} catch (SQLException e) {
				System.out.println("데이터베이스 연결 실패");
			}
			return con;
		}
	}

	public static void unConnection() throws SQLException {
		con.close();
		System.out.println("데이터베이스 연결 해제");
	}

}
