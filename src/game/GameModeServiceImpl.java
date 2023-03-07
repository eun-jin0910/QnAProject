package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;

import dbutil.ConnectionProvider;
import lobby.LobbyFrame;
import lobby.LobbyServiceImpl;
import lobby.LobbyServiceToolImpl;
import object.Attacker;
import object.User;
import ranking.UserRankDialog;
import style.ImageServiceImpl;
import style.ImageServiceToolImpl;

public class GameModeServiceImpl implements GameModeService {
	private GameModeServiceTool gmst;
	private int[] attackerIndex = { 0, 3, 8, 51, 54, 60, 63, 65, 71 };
	private int[] defenderIndex = { 49, 50, 53, 55, 57, 59, 66, 67, 69, 70, 72, 74, 75 };
	private Font font = new Font("맑은 고딕", Font.BOLD, 18);
	private Font font2 = new Font("맑은 고딕", Font.BOLD, 27);
	private Font font3 = new Font("맑은 고딕", Font.BOLD, 33);
	private boolean secondHalfGo = true;
	
	public GameModeServiceImpl(GameModeServiceTool gmst) {
		super();
		this.gmst = gmst;
	}

	@Override
	public List<Image> makeFirstHalfList(User user) {
		List<Image> firstHalfImages = new ArrayList<>();
		ImageServiceImpl isi = new ImageServiceImpl(new ImageServiceToolImpl());
		List<Image> introList = isi.readIntro();
		List<Image> firstHalfList = user.getGender().equals("남") ? isi.readKatalkMan() : isi.readKatalkWoman();
		for (Image intro : introList) {
			firstHalfImages.add(intro);
		}

		for (Image firstHalf : firstHalfList) {
			firstHalfImages.add(firstHalf);
		}
		return firstHalfImages;
	}

	@Override
	public List<Image> makeSecondHalfList(User user, int score) {
		List<Image> secondHalfImages = new ArrayList<>();
		List<Image> secondHalfList = new ArrayList<>();
		int passScore = 20;
		ImageServiceImpl isi = new ImageServiceImpl(new ImageServiceToolImpl());
		if (user.getGender().equals("남")) {
			secondHalfList = score >= passScore ? isi.getLikeAbilityMan() : isi.failedLikeAbilityMan();
		} else {
			secondHalfList = score >= passScore ? isi.getLikeAbilityWoman() : isi.failedLikeAbilityWoman();
		}
		for (Image secondHalf : secondHalfList) {
			secondHalfImages.add(secondHalf);
		}
		if (score >= passScore) {
			for (Image date : isi.theDayWeMeet()) {
				secondHalfImages.add(date);
			}
		} else {
			secondHalfGo = false;
			for (Image gameOver : isi.gameOver()) {
				secondHalfImages.add(gameOver);
			}
		}
		return secondHalfImages;
	}

	@Override
	public void decideSecondHalf(User attacker, List<Image> resultImages, int score) {
		List<Image> secondHalfList = makeSecondHalfList(attacker, score);
		for (Image secondHalf : secondHalfList) {
			resultImages.add(secondHalf);
		}
	}

	@Override
	public void gameComplete(User user, User defender) {
		UserRankDialog urd = new UserRankDialog(defender);
		LobbyServiceImpl lsi = new LobbyServiceImpl(new LobbyServiceToolImpl());
		List<Attacker> attackerList = lsi.makeAttackerList(defender);
		lsi.setUserRanking(urd, attackerList);
		for (int i = 0; i < urd.getModel().getRowCount(); i++) {
			String attackerId = (String) urd.getModel().getValueAt(i, 1);
			if (user.getId().equals(attackerId)) {
				// 게임이 끝난 후 랭킹 화면에서 자신의 행이 하이라이트 되는 기능 구현 예정
			}
		}
		urd.setVisible(true);
		LobbyFrame lf = new LobbyFrame(user);
		lf.setVisible(true);
	}

	@Override
	public int selectByOption(String option) {
		String sql = "SELECT choice FROM exam_option WHERE `option` = ?";
		try (Connection conn = ConnectionProvider.makeConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, option);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					int choice = rs.getInt(1);
					return choice;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public void dataTransferToDB(User user, User defender, GameFrame gameFrame) {
		if (isUserPlayedGameBefore(user, defender)) {
			gmst.deleteAnswer(user, defender);
		}
		List<Integer> choiceList = gameFrame.getChoiceList();
		String sql = "INSERT INTO answer VALUES (?, ?, ?);";
		try (Connection conn = ConnectionProvider.makeConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);) {
			for (Integer option : choiceList) {
				stmt.setString(1, defender.getId());
				stmt.setString(2, user.getId());
				stmt.setInt(3, option);
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isUserPlayedGameBefore(User user, User defender) {
		String sql = "SELECT EXISTS "
				+ "(SELECT choice from answer WHERE defender = ? AND attacker = ?)"
				+ " AS `isNOTNULL`;";
		try (Connection conn = ConnectionProvider.makeConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, defender.getId());
			stmt.setString(2, user.getId());
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					int answer = rs.getInt(1);
					if (answer == 1) {
						return true;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public JLabel setTextByImageIndex(GameFrame gameFrame) {
		JLabel lbl = new JLabel();
		List<Integer> nameList = new ArrayList<>(Arrays.asList(11, 12, 13, 14, 15, 17, 18, 19));
		int currentImageIndex = gameFrame.getCurrentImageIndex();
		System.out.println(currentImageIndex);
		for (int i = 0; i < attackerIndex.length; i++) { // 사용자 이름 고정 라벨
			if (currentImageIndex == attackerIndex[i]) {
				String name = gameFrame.getUser().getName();
				int x = 160;
				if (name.length() > 3) {
					x = 147;
				} 
				lbl.setText(name);
				lbl.setBounds(x, 287, 400, 300);
				lbl.setFont(font2);
				gameFrame.getLabel().add(lbl);
			}
		}
		for (int i = 0; i < defenderIndex.length; i++) { // defender 이름 고정 라벨
			if (currentImageIndex == defenderIndex[i]) {
				String name = gameFrame.getDefender().getName();
				int x = 160;
				if (name.length() > 3) {
					x = 147;
				} 
				lbl.setText(name);
				lbl.setBounds(x, 287, 400, 300);
				lbl.setFont(font2);
				gameFrame.getLabel().add(lbl);
			}
		}
		if (currentImageIndex == 46 && secondHalfGo) {
			lbl.setText(gameFrame.getDefender().getName() + "은(는) 언제오려나...?");
			lbl.setBounds(150, 380, 400, 300);
			lbl.setFont(font3);
			gameFrame.getLabel().add(lbl);
		}
		// 카톡 화면 라벨
		if (nameList.contains(currentImageIndex)) {
			int y = 53;
			if (currentImageIndex == 19) {
				y -= 85;
			}
			String name = gameFrame.getDefender().getName();
			int x = 370;
			if (name.length() > 3) {
				x = 358;
			} 
			lbl.setText(name);
			lbl.setBounds(x, y, 400, 300);
			lbl.setFont(font);
			gameFrame.getLabel().add(lbl);
		}
		// 외향성내향성 선택
		List<Integer> ieList = new ArrayList<>(Arrays.asList(22, 23, 24, 25, 26, 28, 29, 30, 31));
		if (ieList.contains(currentImageIndex)) {
			int y = 260;
			lbl.setText(gameFrame.getOptionIE());
			if (currentImageIndex == 22) {
			} else if (currentImageIndex == 23) {
				y = 150;
			} else if (currentImageIndex == 24) {
				y = 90;
			} else if (currentImageIndex == 25 || currentImageIndex == 26) {
				y = 10;
			} else if (currentImageIndex == 28) {
				y = -80;
			} else if (currentImageIndex == 29) {
				y = -130;
			} else if (currentImageIndex == 30) {
				y = -200;
			} else if (currentImageIndex == 31) {
				y = -250;
			}
			lbl.setBounds(370, y, 400, 700);
			lbl.setFont(font);
			gameFrame.getLabel().add(lbl);
		}
		// 음식선택
		List<Integer> bread = new ArrayList<>(Arrays.asList(37, 38, 39, 41, 42, 43, 44, 45));
		if (bread.contains(currentImageIndex)) {
			int y = 481;
			if (currentImageIndex == 38) {
				y -= 54;
			} else if (currentImageIndex == 39) {
				y -= 124;
			} else if (currentImageIndex == 41) {
				y = 295;
			} else if (currentImageIndex == 42) {
				y = 255;
			} else if (currentImageIndex == 43) {
				y = 163;
			} else if (currentImageIndex == 44) {
				y = 105;
			} else if (currentImageIndex == 45 && !secondHalfGo) { 
				y = 75;
			} else if (currentImageIndex == 45 && secondHalfGo) {
				lbl.setVisible(false);
			}
			lbl.setText(gameFrame.getOptionMeal());
			lbl.setForeground(new Color(48, 56, 72));
			lbl.setFont(font);
			lbl.setBounds(475, y, 400, 300);
			gameFrame.getLabel().add(lbl);
		}
		return lbl;
	}

	@Override
	public JLabel setTextByImageIndex2(GameFrame gameFrame) { // 라벨 두개필요할때 사용
		JLabel lbl = new JLabel();
		int currentImageIndex = gameFrame.getCurrentImageIndex();
		// 노래선택
		List<Integer> choiceList = new ArrayList<>(Arrays.asList(17, 18, 19, 20, 22, 23, 24));
		if (choiceList.contains(currentImageIndex)) {
			int y = 175;
			if (currentImageIndex == 19) {
				y = 90; // 90
			} else if (currentImageIndex == 20) {
				y = 10; // 10
			} else if (currentImageIndex == 22) {
				y = -77; // 10
			} else if (currentImageIndex == 23) {
				y = -185; 
			} else if (currentImageIndex == 24) {
				y = -253; 
			}
			lbl.setText(gameFrame.getOptionSong());
			lbl.setBounds(355, y, 400, 700);
			lbl.setFont(font);
			gameFrame.getLabel().add(lbl);
		}
		// 혜택선택
		List<Integer> benefit = new ArrayList<>(Arrays.asList(41, 42, 43, 44, 45));
		if (benefit.contains(currentImageIndex)) {
			int y = 470;
			if (currentImageIndex == 42) {
				y = 430;
			} else if (currentImageIndex == 43) {
				y = 337;
			} else if (currentImageIndex == 44) {
				y = 281;
			} else if (currentImageIndex == 45 && !secondHalfGo) { 
				y = 231;
			} else if (currentImageIndex == 45 && secondHalfGo) {
				lbl.setVisible(false);
			}
			lbl.setText(gameFrame.getOptionBenefit());
			lbl.setForeground(new Color(48, 56, 72));
			lbl.setFont(font);
			lbl.setBounds(465, y, 400, 300);
			gameFrame.getLabel().add(lbl);
		}
		List<Integer> hobby = new ArrayList<>(Arrays.asList(28, 29, 30, 31, 32, 33, 34, 35, 37, 38));
		if (hobby.contains(currentImageIndex)) {
			int y = 457;
			if (currentImageIndex == 29) {
				y = 410;
			} else if (currentImageIndex == 30) {
				y = 344;
			} else if (currentImageIndex == 31) {
				y = 290;
			} else if (currentImageIndex == 32 || currentImageIndex == 33) {
				y = 209;
			} else if (currentImageIndex == 34) {
				y = 121;
			} else if (currentImageIndex == 35) {
				y = 77;
			} else if (currentImageIndex == 37) {
				y = 26;
			} else if (currentImageIndex == 38) {
				y = -27;
			} 
			lbl.setText(gameFrame.getOptionHobby());
			lbl.setForeground(new Color(48, 56, 72));
			lbl.setFont(font);
			lbl.setBounds(372, y, 400, 300);
			gameFrame.getLabel().add(lbl);
		}
		return lbl;
	}
}
