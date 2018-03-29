//***************************
 // 파일명: ProjectDAO.java
 // 작성자: 마재희
 // 작성일: 2017-12-22
 // 내용: ProjectDAO
 //***************************
package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ProjectDAO {

	private StudentDAO student = new StudentDAO();
	private ProfessorDAO professor = new ProfessorDAO();
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Connection con = DB.makeConnection();
	private Scanner scan = new Scanner(System.in);

	public void approveProject(int proNum) throws SQLException {

		String approveProjectSql = "update projects set approval=1 where id=" + proNum;
		pstmt = con.prepareStatement(approveProjectSql);
		pstmt.execute();

		System.out.println("해당 프로젝트가 승인되었습니다.");
	}

	public void approvalprojectList() throws SQLException {
		System.out.println();

		String projectListSql = "SELECT p.*, count(r.id) count FROM projects p left join reports r on p.id=r.rep_pro_id where approval=1 group by p.id;";
		pstmt = con.prepareStatement(projectListSql);
		rs = pstmt.executeQuery();
		if (!rs.next())
			System.out.println("현재 승인된 프로젝트가 존재하지 않습니다.");
		else {
			rs.previous();
			while (rs.next()) {
				System.out.print("프로젝트 번호 : " + rs.getString("id") + "\t");
				System.out.print("프로젝트 이름 : " + rs.getString("project_name") + "\t");
				System.out.print("프로젝트 내용 : " + rs.getString("project_content") + "\t");
				System.out.println("회의 횟수 : " + rs.getString("count"));
			}
		}
		System.out.println();
	}

	public void nonApprovalprojectList() throws SQLException {
		System.out.println();

		String projectListSql = "SELECT * FROM projects where approval=0;";
		pstmt = con.prepareStatement(projectListSql);
		rs = pstmt.executeQuery();
		if (!rs.next())
			System.out.println("현재 미승인 프로젝트가 존재하지 않습니다.");
		else {
			rs.previous();
			while (rs.next()) {
				System.out.print("프로젝트 번호 : " + rs.getString("id") + "\t");
				System.out.print("프로젝트 이름 : " + rs.getString("project_name") + "\t");
				System.out.println("프로젝트 내용 : " + rs.getString("project_content"));
			}
		}
		System.out.println();
	}

	public void approvalMenu() throws SQLException {
		System.out.println("다음 중 하나를 선택하시오.");
		int menu;

		do {
			System.out.print("1)프로젝트 승인하기  2)메인 메뉴로 돌아가기 --->");
			menu = scan.nextInt();
			System.out.println();

			switch (menu) {// 선택 메뉴에 따른 스위치문
			case 1:
				System.out.print("승인을 원하는 프로젝트 번호를 입력해 주세요: ");

				int pro_num = scan.nextInt();
				System.out.println();

				approveProject(pro_num);

				nonApprovalprojectList();
				break;
			case 2:
				System.out.println("메인 메뉴로 돌아갑니다.");
				return;
			default:
				System.out.println("메뉴 번호 오류: 메뉴를 다시 선택하세요.");
			}
		} while (true);
	}

	public void projectList() throws SQLException {
		System.out.println();

		String projectListSql = "SELECT * FROM projects;";
		pstmt = con.prepareStatement(projectListSql);
		rs = pstmt.executeQuery();
		if (!rs.next())
			System.out.println("현재 프로젝트가 존재하지 않습니다.");
		else {
			rs.previous();
			while (rs.next()) {
				System.out.print("프로젝트 번호 : " + rs.getString("id") + "\t");
				System.out.print("프로젝트 이름 : " + rs.getString("project_name") + "\t");
				System.out.print("프로젝트 내용 : " + rs.getString("project_content") + "\t");
				System.out.print("승인여부 : ");
				if (rs.getInt("approval") == 0)
					System.out.println("미승인");
				else
					System.out.println("승인");
			}
		}
		System.out.println();

	}

	public void projectInfo() throws SQLException {
		System.out.println();

		String projectListSql = "SELECT p.id, p.project_name, f.professor_name, count(r.id) count "
				+ "from projects p left join professors f on p.pro_f_id=f.id "
				+ "left join reports r on p.id=r.rep_pro_id " + "where p.approval=1 " + "group by r.rep_pro_id";
		pstmt = con.prepareStatement(projectListSql);
		rs = pstmt.executeQuery();
		if (!rs.next())
			System.out.println("현재 프로젝트가 존재하지 않습니다.");
		else {
			rs.previous();
			while (rs.next()) {
				System.out.print("프로젝트 번호 : " + rs.getString("p.id") + "\t");
				System.out.print("프로젝트 이름 : " + rs.getString("p.project_name") + "\t");
				System.out.print("교수 이름 : " + rs.getString("f.professor_name") + "\t");
				System.out.println("회의 횟수 : " + rs.getString("count"));
			}
		}
		System.out.println();

	}

	public void detail() throws SQLException {

		System.out.println("다음 중 하나를 선택하시오.");
		int menu;

		do {
			System.out.print("1)상세보기   2)메인 메뉴로 돌아가기 --->");
			menu = scan.nextInt();
			System.out.println();

			switch (menu) {// 선택 메뉴에 따른 스위치문
			case 1:
				System.out.print("상세보기를 원하는 프로젝트 번호를 입력해 주세요: ");
				int pro_num = scan.nextInt();
				System.out.println();

				String detailProjectNameSql = "select project_name from projects where id=" + pro_num;

				pstmt = con.prepareStatement(detailProjectNameSql);
				rs = pstmt.executeQuery();

				rs.next();
				System.out.println("프로젝트명: " + rs.getString("project_name"));

				String detailProfessorSql = "select f.professor_name from professors f inner join projects p on f.id=p.pro_f_id where p.id="
						+ pro_num;

				pstmt = con.prepareStatement(detailProfessorSql);
				rs = pstmt.executeQuery();

				rs.next();
				System.out.println("지도교수: " + rs.getString("f.professor_name"));

				String detailStudentsSql = "SELECT s.name from students s inner join student_project sp on s.id=sp.student_id where sp.project_id="
						+ pro_num;

				pstmt = con.prepareStatement(detailStudentsSql);
				rs = pstmt.executeQuery();

				System.out.print("참여학생: ");
				while (rs.next()) {
					System.out.print(rs.getString("s.name") + "   ");
				}

				System.out.println();
				String detailReportsSql = "select create_date, report_content from reports where rep_pro_id=" + pro_num;

				pstmt = con.prepareStatement(detailReportsSql);
				rs = pstmt.executeQuery();

				System.out.println("--------------회의정보-------------");
				while (rs.next()) {
					System.out.print("회의내용: " + rs.getString("report_content") + "\t");
					System.out.println("회의일시: " + rs.getString("create_date"));
				}
				System.out.println();
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

	public void projectMenu() throws SQLException {

		System.out.println("다음 중 하나를 선택하시오.");
		int menu;

		do {
			System.out.print("1)프로젝트 신청   2)프로젝트 삭제   3)메인 메뉴로 돌아가기 --->");
			menu = scan.nextInt();
			System.out.println();

			s: switch (menu) {// 선택 메뉴에 따른 스위치문
			case 1:
				System.out.println("프로젝트 신청");
				System.out.print("프로젝트 이름: ");
				String pro_name = scan.next();
				System.out.print("프로젝트 내용: ");
				scan.nextLine();
				String pro_content = scan.nextLine();
				System.out.println();
				student.studentSimpleList();
				ArrayList<Integer> list = new ArrayList<Integer>();
				String s = scan.nextLine();
				StringTokenizer st = new StringTokenizer(s, " ");
				while (st.hasMoreTokens()) {
					String s1 = st.nextToken();
					list.add(Integer.parseInt(s1));
				}
				System.out.println();
				professor.professorSimpleList();
				int professor = scan.nextInt();

				StringBuilder insertProSql = new StringBuilder();

				insertProSql.append("insert into projects ");
				insertProSql.append("values (null, ?, ?, ?, 0)");

				pstmt = con.prepareStatement(insertProSql.toString());
				pstmt.setString(1, pro_name);
				pstmt.setString(2, pro_content);
				pstmt.setInt(3, professor);

				pstmt.execute();

				String findProNumSql = "SELECT id from projects where project_name='" + pro_name + "'";

				pstmt = con.prepareStatement(findProNumSql);
				rs = pstmt.executeQuery();
				rs.next();

				int proNum = rs.getInt("id");

				for (int i = 0; i < list.size(); ++i) {
					insertProSql = new StringBuilder();
					insertProSql.append("insert into student_project ");
					insertProSql.append("values (null, " + list.get(i) + ", " + proNum + ")");

					pstmt = con.prepareStatement(insertProSql.toString());

					try {
						pstmt.execute();
					} catch (SQLException e) {
						String findStuProNoSql = "SELECT id from student_project where project_id=" + proNum
								+ " and student_id=" + list.get(i);

						pstmt = con.prepareStatement(findStuProNoSql);
						rs = pstmt.executeQuery();

						String rollBackSql = null;
						if (!rs.next()) {
							rollBackSql = "delete from projects where id=" + proNum;

							pstmt = con.prepareStatement(rollBackSql);
							pstmt.execute();
						}
						System.out.println();
						System.out.println("이미 프로젝트를 진행중인 학생이 존재합니다.");
						System.out.println();
						break s;
					}
				}

				System.out.println("프로젝트 신청이 완료되었습니다.");
				System.out.println();
				projectList();
				break;
			case 2:
				System.out.println("프로젝트 삭제");
				projectList();
				System.out.print("삭제할 프로젝트 번호: ");
				int proj_id = scan.nextInt();

				String deleteStuProSql = "delete FROM student_project where project_id=" + proj_id;
				pstmt = con.prepareStatement(deleteStuProSql);
				pstmt.execute();

				String deleteProSql = "delete FROM projects where id=" + proj_id;
				pstmt = con.prepareStatement(deleteProSql);
				pstmt.execute();

				projectList();
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
