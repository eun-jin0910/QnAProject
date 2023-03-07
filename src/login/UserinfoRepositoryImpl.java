package login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dbutil.ConnectionProvider;
import exception.DataIOException;
import object.User;

public class UserinfoRepositoryImpl implements UserinfoRepository {
	@Override
	public int userInsert(String id, String pw, String name, String mbti, String gender) {
		String sql = "INSERT INTO userinfo (id, pw, name, mbti, gender) VALUES (?, ?, ?, ?, ?)";
		try (Connection conn = ConnectionProvider.makeConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
			stmt.setString(1, id);
			stmt.setString(2, pw);
			stmt.setString(3, name);
			stmt.setString(4, mbti);
			stmt.setString(5, gender);

			return stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("예외");
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int userInsert(User user) {
		return userInsert(user.getId(), user.getPw(), user.getName(), user.getMbti(), user.getGender());
	}

	@Override
	public int countById(String id) {
		String sql = "SELECT count(*) FROM userinfo WHERE id = ?";
		try (Connection conn = ConnectionProvider.makeConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("count(*)");
				}
			}
		} catch (SQLException e) {
			throw new DataIOException(e);
		}
		return 0;
	}

	@Override
	public String selectPw(String id) {
		String sql = "SELECT * FROM userinfo WHERE id = ?";
		try (Connection conn = ConnectionProvider.makeConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getString("pw");
				}
			}
		} catch (SQLException e) {
			throw new DataIOException(e);
		}
		return null;
	}

	@Override
	public int userUpdate(User user) {
		String sql = "UPDATE userinfo SET pw = ?, name = ?, mbti = ?, gender = ? WHERE id = ?";
		try (Connection conn = ConnectionProvider.makeConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
			stmt.setString(1, user.getPw());
			stmt.setString(2, user.getName());
			stmt.setString(3, user.getMbti());
			stmt.setString(4, user.getGender());
			stmt.setString(5, user.getId());

			return stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("예외");
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public User loginUser(String id) {
		String sql = "SELECT * FROM userinfo WHERE id = ?";
		try (Connection conn = ConnectionProvider.makeConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					String pw = rs.getString("pw");
					String name = rs.getString("name");
					String mbti = rs.getString("mbti");
					String gender = rs.getString("gender");
					return new User(id, pw, name, mbti, gender);
				}
			}
		} catch (SQLException e) {
			return null;
		}
		return null;
	}

	@Override
	public void userDelete(User user) {

		String sql1 = "delete from answer where defender = ? or attacker = ?";
		String sql2 = "delete from mission where id = ?";
		String sql3 = "delete from userinfo where id= ?";
		try (Connection conn = ConnectionProvider.makeConnection();
				PreparedStatement stmt1 = conn.prepareStatement(sql1);
				PreparedStatement stmt2 = conn.prepareStatement(sql2);
				PreparedStatement stmt3 = conn.prepareStatement(sql3);

		) {
			stmt1.setString(1, user.getId());
			stmt1.setString(2, user.getId());
			stmt2.setString(1, user.getId());
			stmt3.setString(1, user.getId());

			stmt1.executeUpdate();
			stmt2.executeUpdate();
			stmt3.executeUpdate();

			System.out.println("탈퇴완료");

		} catch (SQLException e) {
			e.printStackTrace();
		}


	}
}
