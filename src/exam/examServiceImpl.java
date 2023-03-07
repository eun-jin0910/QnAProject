package exam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import dbutil.ConnectionProvider;
import object.User;

public class examServiceImpl implements examService {
	private static JLabel lblTest[] = new JLabel[10];
	static List<String> QuestionList = new ArrayList<>();
	static List<String> fixOptionList = new ArrayList<>();
	static List<Integer> examEachNum = new ArrayList<>();
	static List<Integer> missionIn = new ArrayList<>();
	static List<Integer> missionOut = new ArrayList<>();
	static List<Integer> selectNum = new ArrayList<>();

	// 인덱스 값 mission값으로 바꾸기
	public List<Integer> indexChange(List<Integer> e) {
		int a = e.get(0) + 11;
		missionIn.add(a);

		int count = 0;
		for (int i = 1; i < 10; i++) {
			int b = examEachNum.get(i - 1);
			count += b;
			int c = e.get(i) - count + 1 + (i + 1) * 10;
			missionIn.add(c);
		}
		return missionIn;
	}

	@Override
	public List<String> readQuestion() {
		try (Connection conn = ConnectionProvider.makeConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM exam")) {

			for (int j = 0; j < 10; j++) {
				lblTest[j] = new JLabel();
			}

			int k = 0;
			while (rs.next()) {
				String question = rs.getString("question");
				lblTest[k].setText(question);
				QuestionList.add(question);
				k++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return QuestionList;
	}

	@Override
	public List<String> readFixOption() {
		try (Connection conn = ConnectionProvider.makeConnection();
				Statement stmt1 = conn.createStatement();
				ResultSet rs1 = stmt1.executeQuery("SELECT * FROM exam_option;");) {
			while (rs1.next()) {
				String option = rs1.getString("option");
				fixOptionList.add(option);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fixOptionList;

	}

	@Override
	public List<Integer> readEachNum() {
		try (Connection conn = ConnectionProvider.makeConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT count(*) FROM exam_option group by examNo;");) {

			while (rs.next()) {
				int examNo = rs.getInt("count(*)");
				examEachNum.add(examNo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return examEachNum;
	}

	@Override
	public void signUp(List<Integer> e, User user) {
		// 받아온 리스트로 값 변환해서 다른 리스트에 넣어서 DB에 넣기
		indexChange(e);
		String userId = user.getId();
		String sql = "INSERT INTO mission (id,choice) VALUES ('" + userId + "', ?)";
		try (Connection conn = ConnectionProvider.makeConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			for (int i = 0; i < 10; i++) {
				pstmt.setInt(1, missionIn.get(i));
				pstmt.executeUpdate();
			}

		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}

	@Override
	public List<Integer> readMissionNum(String n) {
		missionOut.removeAll(missionOut);
		selectNum.removeAll(selectNum);
		String sql = "SELECT * FROM mission WHERE id = ?";
		try (Connection conn = ConnectionProvider.makeConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, n);

			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					int choice = rs.getInt("choice");

					missionOut.add(choice);
				}
				int a = missionOut.get(0) - 11;
				selectNum.add(a);

				int count = 0;
				for (int i = 1; i < 10; i++) {
					int b = examEachNum.get(i - 1);
					count += b;
					int c = missionOut.get(i) + count - 1 - (i + 1) * 10;
					selectNum.add(c);
				}
			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return selectNum;
	}

	@Override
	public List<String> readOptionByExamNo(int examNo) {
		String sql = "SELECT `option` FROM exam_option WHERE examNo = ?";
		List<String> optionList = new ArrayList<>();
		try (Connection conn = ConnectionProvider.makeConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, examNo);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					String option = rs.getString(1);
					optionList.add(option);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return optionList;
	}

	@Override
	public int countOptionByExamNo(int examNo) {
		String sql = "SELECT count(*) AS 'optionNum' FROM exam_option group by examNo HAVING examNo = ?";
		int count = 0;
		try (Connection conn = ConnectionProvider.makeConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, examNo);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					count = rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public void editUp(List<Integer> e, User user) {
		missionIn.removeAll(missionIn);
		// 삭제하고
		String sql = "DELETE FROM mission WHERE id = ?";
		try (Connection conn = ConnectionProvider.makeConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, user.getId());
			pstmt.executeUpdate();
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		// 다시 추가
		signUp(e, user);
	}
}
