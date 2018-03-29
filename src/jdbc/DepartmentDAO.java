//***************************
 // 파일명: DeportmentDAO.java
 // 작성자: 마재희
 // 작성일: 2017-12-22
 // 내용: DepartmentDAO
 //***************************
package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DepartmentDAO {

	PreparedStatement pstmt;
	ResultSet rs;
	Connection con = DB.makeConnection();
	Scanner scan = new Scanner(System.in);

	public void departmentSimpleList(int n) throws SQLException {
		String departmentListSql = "SELECT * FROM departments order by id;";
		pstmt = con.prepareStatement(departmentListSql);
		rs = pstmt.executeQuery();
		if (!rs.next())
			System.out.println("학과가 존재하지 않습니다.");
		else {
			rs.previous();
			if (n == 2)
				System.out.println("0) 미정");
			while (rs.next()) {
				System.out.println(rs.getString("id") + ") " + rs.getString("department_name"));
			}
			System.out.print("번호 입력: ");
		}
	}

	public void departmentList() throws SQLException {
		System.out.println();

		String departmentListSql = "SELECT * FROM departments order by id;";
		pstmt = con.prepareStatement(departmentListSql);
		rs = pstmt.executeQuery();
		if (!rs.next())
			System.out.println("학과가 존재하지 않습니다.");
		else {
			rs.previous();
			while (rs.next()) {
				System.out.print("학과번호 : " + rs.getString("id") + "\t");
				System.out.println("학과이름 : " + rs.getString("department_name"));
			}
		}
		System.out.println();
	}

	public void departmentMenu() throws SQLException {

		System.out.println("다음 중 하나를 선택하시오.");
		int menu;

		do {
			System.out.print("1)학과 정보 입력   2)학과 삭제   3)메인 메뉴로 돌아가기 --->");
			menu = scan.nextInt();
			System.out.println();

			switch (menu) {// 선택 메뉴에 따른 스위치문
			case 1:
				System.out.println("학과 정보 입력");
				System.out.print("학과 이름: ");
				String department_name = scan.next();

				StringBuilder insertDepSql = new StringBuilder();

				insertDepSql.append("insert into departments ");
				insertDepSql.append("values (null, ?)");

				pstmt = con.prepareStatement(insertDepSql.toString());
				pstmt.setString(1, department_name);

				pstmt.execute();

				departmentList();
				break;
			case 2:
				System.out.println("학과 삭제");
				System.out.print("삭제할 학과이름: ");
				String dep_name = scan.next();

				String deleteDepSql = "delete FROM departments where department_name like '%" + dep_name + "%';";
				pstmt = con.prepareStatement(deleteDepSql);
				pstmt.execute();

				departmentList();
				break;
			case 3:
				System.out.println("메인 메뉴로 돌아갑니다.");
				System.out.println();

				return;
			default:
				System.out.println("메뉴 번호 오류: 메뉴를 다시 선택하세요.");
			}
		} while (menu != 3);
	}
}
