package game;

import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import lobby.LobbyFrame;
import lobby.LobbyService;
import lobby.LobbyServiceImpl;
import lobby.LobbyServiceToolImpl;
import object.OptionButtonListener;
import object.OptionPanel;
import object.User;
import object.Vibration;

public class GameFrame extends JFrame {
	private JLabel label;
	private int currentImageIndex = 0;
	private List<Image> resultImages;
	private List<Integer> examNo = new ArrayList<>(Arrays.asList(17, 22, 28, 37, 41, 48, 57, 65, 69, 74));
	private OptionPanel optionPanel;
	private boolean selectedMode = false;
	private OptionButtonListener listener = new OptionButtonListener(this);
	private List<Integer> missonList = new ArrayList<>();
	private List<Integer> choiceList = new ArrayList<>();
	private User defender;
	private User user;
	private int score = 0;
	private JLabel textLbl = new JLabel();
	private JLabel textLbl2 = new JLabel();
	private String selectOption = "";
	private String optionSong;
	private String optionIE;
	private String optionHobby;
	private String optionMeal;
	private String optionBenefit;
	private Clip clip = null;

	public GameFrame(User user, User defender) {
		GameModeService gameModeService = new GameModeServiceImpl(new GameModeServiceToolImpl());
		resultImages = gameModeService.makeFirstHalfList(defender);
		LobbyService lobbyService = new LobbyServiceImpl(new LobbyServiceToolImpl());
		missonList = lobbyService.makeMissonList(defender);
		this.defender = defender;
		this.user = user;
		JPanel pnl = new JPanel();
		pnl.setLayout(null);
		label = new JLabel(new ImageIcon(resultImages.get(currentImageIndex)));
		label.setBounds(0, 0, 944, 701);
		pnl.add(label);
		label.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// 게임 정상적으로 완료 시 동작
				gameOn(user, defender, gameModeService);
			}
		});

		label.setFocusable(true);
		label.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					gameOn(user, defender, gameModeService);
				}
			}
		});

		this.addWindowListener(new WindowAdapter() {
			// 게임을 완료하지않고 닫기창 누를 때 발생
			@Override
			public void windowClosed(WindowEvent e) {
				int lastImageNum = resultImages.size() - 1;
				if (currentImageIndex != lastImageNum) {
					LobbyFrame lf = new LobbyFrame(user);
					lf.setVisible(true);
				}
			}
		});

		getContentPane().add(pnl);

		setSize(960, 740);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void setOptionAnswer() {
		if (currentImageIndex >= 15 && currentImageIndex < 20) {
			String[] str = selectOption.split("-");
			String option = "";
			for (int i = 0; i < str.length; i++) {
				option += str[i] + "<br>";
			}
			optionSong = "<HTML><body>" + option + "</body></HTML>";
		}
		if (currentImageIndex >= 21 && currentImageIndex < 23) {
			String[] str = selectOption.split(" 처");
			String option = "";
			if (str.length > 1) {
				option += str[0] + "<br>처";
				for (int i = 1; i < str.length; i++) {
					option += str[i];
				}
			}
			str = selectOption.split(" 나");
			if (str.length > 1) {
				option += str[0] + "<br>나";
				for (int i = 1; i < str.length; i++) {
					option += str[i];
				}
			}
			optionIE = "<HTML><body>" + option + "</body></HTML>";
		}
		if (currentImageIndex >= 27 && currentImageIndex < 30) {
			String str = selectOption;
			if (str.length() >= 23) { 
				str = str.substring(0, 15) + "<br>" + str.substring(15, str.length());
			} else if (str.length() > 10 && str.length() < 23) {
				str = str.substring(0, 14) + "<br>" + str.substring(14, str.length());
			}
			optionHobby = "<HTML><body>" + str + "</body></HTML>";
		}
		if (currentImageIndex >= 36 && currentImageIndex < 38) {
			optionMeal = selectOption;
		}
		if (currentImageIndex >= 40 && currentImageIndex < 42) {
			optionBenefit = selectOption;
		}
	}

	public void nextImage() {
		currentImageIndex += 1;
		label.setIcon(new ImageIcon(resultImages.get(currentImageIndex)));
	}

	public String getOptionSong() {
		return optionSong;
	}

	public void setOptionSong(String optionSong) {
		this.optionSong = optionSong;
	}

	public String getOptionIE() {
		return optionIE;
	}

	public void setOptionIE(String optionIE) {
		this.optionIE = optionIE;
	}

	public String getOptionHobby() {
		return optionHobby;
	}

	public void setOptionHobby(String optionHobby) {
		this.optionHobby = optionHobby;
	}

	public String getOptionMeal() {
		return optionMeal;
	}

	public void setOptionMeal(String optionMeal) {
		this.optionMeal = optionMeal;
	}

	public String getOptionBenefit() {
		return optionBenefit;
	}

	public void setOptionBenefit(String optionBenefit) {
		this.optionBenefit = optionBenefit;
	}

	public boolean isSelectedMode() {
		return selectedMode;
	}

	public void setSelectedMode(boolean selectedMode) {
		this.selectedMode = selectedMode;
	}

	public OptionPanel getOptionPanel() {
		return optionPanel;
	}

	public void setOptionPanel(OptionPanel optionPanel) {
		this.optionPanel = optionPanel;
	}

	public List<Integer> getMissonList() {
		return missonList;
	}

	public void setMissonList(List<Integer> missonList) {
		this.missonList = missonList;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public List<Integer> getChoiceList() {
		return choiceList;
	}

	public void setChoiceList(List<Integer> choiceList) {
		this.choiceList = choiceList;
	}

	public int getCurrentImageIndex() {
		return currentImageIndex;
	}

	public void setCurrentImageIndex(int currentImageIndex) {
		this.currentImageIndex = currentImageIndex;
	}

	public List<Image> getResultImages() {
		return resultImages;
	}

	public void setResultImages(List<Image> resultImages) {
		this.resultImages = resultImages;
	}

	public User getDefender() {
		return defender;
	}

	public void setDefender(User defender) {
		this.defender = defender;
	}

	public JLabel getLabel() {
		return label;
	}

	public void setLabel(JLabel label) {
		this.label = label;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public JLabel getTextLbl() {
		return textLbl;
	}

	public void setTextLbl(JLabel textLbl) {
		this.textLbl = textLbl;
	}

	public JLabel getTextLbl2() {
		return textLbl2;
	}

	public void setTextLbl2(JLabel textLbl2) {
		this.textLbl2 = textLbl2;
	}

	public String getSelectOption() {
		return selectOption;
	}

	public void setSelectOption(String selectOption) {
		this.selectOption = selectOption;
	}

	public void gameOn(User user, User defender, GameModeService gameModeService) {
		if (selectedMode == false) {
			if (textLbl.isVisible() || textLbl2.isVisible()) {
				textLbl.setVisible(false);
				textLbl2.setVisible(false);
			}
			textLbl = gameModeService.setTextByImageIndex(this);
			textLbl2 = gameModeService.setTextByImageIndex2(this);
			int lastImageNum = resultImages.size() - 1;
			if (currentImageIndex == lastImageNum) {
				dispose();
				gameModeService.dataTransferToDB(user, defender, GameFrame.this);
				gameModeService.gameComplete(user, defender);
			}

			// 맞춘 개수에 따른 2부 결정
			if (currentImageIndex != lastImageNum) {
				nextImage();
				boolean startSecondHalf = currentImageIndex == 44;
				if (startSecondHalf) {
					gameModeService.decideSecondHalf(defender, resultImages, score);
				}
			}

			// 선택지 발생 필요시 동작
			if (examNo.contains(currentImageIndex)) {
				int optionSceneNum = examNo.indexOf(currentImageIndex) + 1;
				optionPanel = new OptionPanel(optionSceneNum);
				optionPanel.setBounds(0, 0, 960, 720);
				for (JButton btn : optionPanel.getBtns()) {
					btn.addActionListener(listener);
				}
				label.add(optionPanel);
				selectedMode = true;
			}
			// 8페이지 진동효과 동작
			boolean phoneScene = currentImageIndex == 8;
			if (phoneScene) {
				Vibration vibe = new Vibration(label);
				vibe.getTimer().start();
				try {
					// 음원 파일 경로
					File file = new File("phoneVibeSound.wav");
					clip = AudioSystem.getClip();
					clip.open(AudioSystem.getAudioInputStream(file));
					clip.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
