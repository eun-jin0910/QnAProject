package login;

import exception.DataIOException;
import object.User;

public class Login {
	private LoginFrame loginFrame;
	public final int loginComplete = 0;
	public final int loginFailByPw = 1;
	public final int loginFailById = 2;
	private UserinfoRepositoryImpl repo = new UserinfoRepositoryImpl();
	
	public Login() {
		loginFrame = new LoginFrame(this);
		loginFrame.setVisible(true);
	}

	// 로그인 정보 -> db 연결
	public int checkLogin(String id, String pw) throws DataIOException {
		if (repo.countById(id) != 0) {
			if (repo.selectPw(id).equals(pw)) {
				return loginComplete;
			} else {
				return loginFailByPw;
			}
		} else {
			return loginFailById;
		}
	}
	
	public User loginUser(String id) {
		return repo.loginUser(id);
	}
}
