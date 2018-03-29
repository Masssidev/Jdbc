//***************************
 // 파일명: Main.java
 // 작성자: 마재희
 // 작성일: 2017-12-22
 // 내용: Main, Menu
 //***************************
package jdbc;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub

		DepartmentDAO department = new DepartmentDAO();
		StudentDAO student = new StudentDAO();
		ProfessorDAO professor = new ProfessorDAO();
		ProjectDAO project = new ProjectDAO();
		ReportDAO report = new ReportDAO();

		Scanner scan = new Scanner(System.in);

		System.out.println("다음 중 하나를 선택하시오.");
		String menu;
		do {
			System.out.print(
					"a)학과 관리   b)학생 관리   c)교수 추가   d)프로젝트 관리   e)프로젝트 승인   f)프로젝트 회의   g)프로젝트 정보 표시   h)종료 --->");
			menu = scan.next();
			switch (menu) {// 선택 메뉴에 따른 스위치문
			case "a":
				department.departmentList();
				department.departmentMenu();
				break;
			case "b":
				student.studentList();
				student.studentMenu();
				break;
			case "c":
				professor.professorList();
				professor.professorMenu();
				break;
			case "d":
				project.projectList();
				project.projectMenu();
				break;
			case "e":
				project.nonApprovalprojectList();
				project.approvalMenu();
				break;
			case "f":
				project.approvalprojectList();
				report.report();
				break;
			case "g":
				project.projectInfo();
				project.detail();
				break;
			case "h":
				System.out.println();
				System.out.println("프로그램을 종료합니다.");
				DB.unConnection();
				break;
			default:
				System.out.println();
				System.out.println("메뉴 번호 오류: 메뉴를 다시 선택하세요.");
				System.out.println();
			}
		} while (!menu.equals("h"));

		scan.close();
	}
}
