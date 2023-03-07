package mypage;


import javax.swing.JTable;

import object.User;

public class MypageInfo {
	private MypageDialog mypageDialog;
	
	public MypageInfo(User user) {
		mypageDialog = new MypageDialog(user);
		mypageDialog.setVisible(true);
	}
	
	// 테이블 정보 얻어오기
	public void getRankingTable(JTable tableAttack) {
	}
	
	// 로비창 끄기
	public boolean mypageDispose() {
		return mypageDialog.isMypageDispose();
	}
}
