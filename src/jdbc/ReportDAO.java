//***************************
 // 파일명: ReportDAO.java
 // 작성자: 마재희
 // 작성일: 2017-12-22
 // 내용: ReportDAO
 //***************************
package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ReportDAO {
	PreparedStatement pstmt;
	ResultSet rs;
	Connection con = DB.makeConnection();
	Scanner scan = new Scanner(System.in);
	ProjectDAO project = new ProjectDAO();

	public void report() throws SQLException {

		System.out.println("다음 중 하나를 선택하시오.");
		int menu;

		do {
			System.out.print("1)프로젝트 회의   2)메인 메뉴로 돌아가기 --->");
			menu = scan.nextInt();
			System.out.println();

			switch (menu) {// 선택 메뉴에 따른 스위치문
			case 1:
				System.out.print("회의 내용을 작성할 프로젝트 번호를 입력해 주세요: ");
				int pro_no = scan.nextInt();
				System.out.println();
				System.out.print("회의 내용: ");
				scan.nextLine();
				String rep_content = scan.nextLine();

				StringBuilder insertRepSql = new StringBuilder();

				insertRepSql.append("insert into reports ");
				insertRepSql.append("values (null, now(), ?, ?)");

				pstmt = con.prepareStatement(insertRepSql.toString());
				pstmt.setString(1, rep_content);
				pstmt.setInt(2, pro_no);

				pstmt.execute();
				System.out.println();
				System.out.println("회의 내용이 등록되었습니다.");

				project.approvalprojectList();
				break;
			case 2:
				System.out.println("메인 메뉴로 돌아갑니다.");
				System.out.println();

				return;
			default:
				System.out.println("메뉴 번호 오류: 메뉴를 다시 선택하세요.");
			}
		} while (true);
	}
}
