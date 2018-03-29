//***************************
 // 파일명: StudentDAO.java
 // 작성자: 마재희
 // 작성일: 2017-12-22
 // 내용: StudentDAO
 //***************************
package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class StudentDAO {

	DepartmentDAO department = new DepartmentDAO();
	PreparedStatement pstmt;
	ResultSet rs;
	Connection con = DB.makeConnection();
	Scanner scan = new Scanner(System.in);

	public void studentSimpleList() throws SQLException {
		String studentListSql = "SELECT * FROM students order by id;";
		pstmt = con.prepareStatement(studentListSql);
		rs = pstmt.executeQuery();
		if (!rs.next())
			System.out.println("학생이 존재하지 않습니다.");
		else {
			rs.previous();
			while (rs.next()) {
				System.out.println("학번: " + rs.getString("id") + ") " + rs.getString("name"));
			}
			System.out.print("신청 학번 입력: ");
		}
	}

	public void studentList() throws SQLException {
		System.out.println();

		String studentListSql = "SELECT s.*, d.department_name FROM students s left join departments d on s.stu_dep_id=d.id";
		pstmt = con.prepareStatement(studentListSql);
		rs = pstmt.executeQuery();
		if (!rs.next())
			System.out.println("학생이 존재하지 않습니다.");
		else {
			rs.previous();
			while (rs.next()) {
				System.out.print("학번 : " + rs.getString("id") + "\t");
				System.out.print("이름 : " + rs.getString("name") + "\t");
				System.out.print("소속학과 : " + rs.getString("d.department_name") + "\t");
				System.out.println("휴대전화번호 : " + rs.getString("phone"));
			}
		}
		System.out.println();

	}

	public void studentMenu() throws SQLException {

		System.out.println("다음 중 하나를 선택하시오.");
		int menu;
		do {
			System.out.print("1)학생 정보 입력   2)학생 삭제   3)메인 메뉴로 돌아가기 --->");
			menu = scan.nextInt();
			System.out.println();

			switch (menu) {// 선택 메뉴에 따른 스위치문
			case 1:
				System.out.println("학생 정보 입력");
				System.out.print("이름: ");
				String name = scan.next();
				System.out.print("휴대전화번호: ");
				String phone = scan.next();
				System.out.println("전공선택");
				department.departmentSimpleList(1);
				int department = scan.nextInt();

				StringBuilder insertStuSql = new StringBuilder();

				insertStuSql.append("insert into students ");
				insertStuSql.append("values (null, ?, ?, ?)");

				pstmt = con.prepareStatement(insertStuSql.toString());
				pstmt.setString(1, name);
				pstmt.setString(2, phone);
				pstmt.setInt(3, department);

				pstmt.execute();

				studentList();
				break;
			case 2:
				System.out.println("학생 삭제");
				System.out.print("삭제할 학생이름: ");
				String stu_name = scan.next();

				String deleteStuSql = "delete FROM students where name like '%" + stu_name + "%';";
				pstmt = con.prepareStatement(deleteStuSql);
				pstmt.execute();

				studentList();
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
