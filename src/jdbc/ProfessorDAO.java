//***************************
 // 파일명: ProfessorDAO.java
 // 작성자: 마재희
 // 작성일: 2017-12-22
 // 내용: ProfessorDAO
 //***************************
package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ProfessorDAO {

	DepartmentDAO department = new DepartmentDAO();
	PreparedStatement pstmt;
	ResultSet rs;
	Connection con = DB.makeConnection();
	Scanner scan = new Scanner(System.in);

	public void professorSimpleList() throws SQLException {
		String professorListSql = "SELECT * FROM professors order by id;";
		pstmt = con.prepareStatement(professorListSql);
		rs = pstmt.executeQuery();
		if (!rs.next())
			System.out.println("교수가 존재하지 않습니다.");
		else {
			rs.previous();
			while (rs.next()) {
				System.out.println("교수번호: " + rs.getString("id") + ") " + rs.getString("professor_name"));
			}
			System.out.print("지도 교수 번호 입력: ");
		}
	}

	public void professorList() throws SQLException {
		System.out.println();

		String professorListSql = "SELECT p.*, d.department_name FROM professors p left join departments d on p.pro_dep_id=d.id;";
		pstmt = con.prepareStatement(professorListSql);
		rs = pstmt.executeQuery();
		if (!rs.next())
			System.out.println("교수가 존재하지 않습니다.");
		else {
			rs.previous();
			while (rs.next()) {
				System.out.print("교수번호 : " + rs.getString("p.id") + "\t");
				System.out.print("이름 : " + rs.getString("p.professor_name") + "\t");
				System.out.print("소속학과 : ");
				if (rs.getString("d.department_name") == null)
					System.out.print("미정" + "\t");
				else
					System.out.print(rs.getString("d.department_name") + "\t");
				System.out.println("연구실번호 : " + rs.getString("p.office_no"));
			}
		}
		System.out.println();

	}

	public void professorMenu() throws SQLException {

		System.out.println("다음 중 하나를 선택하시오.");
		int menu;
		do {
			System.out.print("1)교수 정보 입력   2)교수 삭제   3)메인 메뉴로 돌아가기 --->");
			menu = scan.nextInt();
			System.out.println();

			switch (menu) {// 선택 메뉴에 따른 스위치문
			case 1:
				System.out.println("교수 정보 입력");
				System.out.print("이름: ");
				String professor_name = scan.next();
				System.out.print("연구실번호: ");
				String office_no = scan.next();
				System.out.println("소속학과선택");
				department.departmentSimpleList(2);
				int department = scan.nextInt();

				StringBuilder insertProSql = new StringBuilder();

				insertProSql.append("insert into professors ");

				if (department == 0) {
					insertProSql.append("values (null, ?, null, ?)");
					pstmt = con.prepareStatement(insertProSql.toString());
					pstmt.setString(1, professor_name);
					pstmt.setString(2, office_no);
				} else {
					insertProSql.append("values (null, ?, ?, ?)");
					pstmt = con.prepareStatement(insertProSql.toString());
					pstmt.setString(1, professor_name);
					pstmt.setInt(2, department);
					pstmt.setString(3, office_no);

				}

				pstmt.execute();

				professorList();
				break;
			case 2:
				System.out.println("교수 삭제");
				System.out.print("삭제할 교수이름: ");
				String pro_name = scan.next();

				String deleteProSql = "delete FROM professors where professor_name like '%" + pro_name + "%';";
				pstmt = con.prepareStatement(deleteProSql);
				pstmt.execute();

				professorList();
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
