package login;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import exam.ExamDialog;
import mbti.MbtiDialog;
import mbti.MbtiImageManager;
import mypage.ChangeExamDialog;

import javax.swing.JTextPane;
import javax.swing.JToggleButton;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

public class JoinDialog extends JDialog implements FocusListener, MouseListener, KeyListener {
	private JPanel contentPane;
	private JTextField tfName;
	private JTextField tfId;
	private JTextField tfMbti;
	private JPasswordField pfPw;
	private JLabel lblResult;
	private JButton btnNext;
	private JButton btnMbti;
	private Join join = new Join();
	private JLabel checkId;
	private JLabel checkPw;
	private JLabel checkName;
	private JLabel checkMbti;
	private MbtiImageManager im = new MbtiImageManager();

	public JTextField getTfMbti() {
		return tfMbti;
	}
 
	public void setTfMbti(JTextField tfMbti) {
		this.tfMbti = tfMbti;
	}

	public JoinDialog() {
		setModal(true);
		setBounds(700, 180, 378, 635);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTextPane textPane = new JTextPane();
		textPane.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		textPane.setText("회원가입");
		textPane.setEditable(false);
		textPane.setBounds(123, 60, 131, 39);
		contentPane.add(textPane);

		JLabel lblId = new JLabel("아이디");
		lblId.setBounds(66, 130, 100, 32);
		lblSetting(lblId);
		tfId = new JTextField("아이디");
		tfId.setBounds(63, 159, 234, 33);
		tfSetting(tfId);
		checkId = checkImage(lblId, 38);

		JLabel lblPw = new JLabel("비밀번호");
		lblPw.setBounds(66, 195, 100, 32);
		lblSetting(lblPw);
		pfPw = new JPasswordField("비밀번호");
		pfPw.setBounds(63, 224, 234, 33);
		tfSetting(pfPw);
		checkPw = checkImage(lblPw, 48);

		JLabel lblName = new JLabel("이름");
		lblName.setBounds(66, 260, 100, 32);
		lblSetting(lblName);
		tfName = new JTextField("이름");
		tfName.setBounds(63, 289, 234, 33);
		tfSetting(tfName);
		checkName = checkImage(lblName, 26);

		JLabel lblMbti = new JLabel("MBTI");
		lblMbti.setBounds(66, 325, 100, 32);
		lblSetting(lblMbti);

		tfMbti = new JTextField("MBTI");
		tfMbti.setBounds(63, 354, 234, 33);
		tfSetting(tfMbti);
		checkMbti = checkImage(lblMbti, 34);

		btnMbti = new JButton(); // mbti 물음표버튼
		btnMbti.setIcon(new ImageIcon(im.getqImage()));
		btnMbti.setBounds(201, 1, 30, 30);
		btnMbti.setBorderPainted(false);
		btnMbti.setBackground(null);
		tfMbti.add(btnMbti);
		btnMbti.addMouseListener(this);
		btnMbti.addFocusListener(this);
		btnMbti.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new MbtiDialog(tfMbti).setVisible(true);
			}
		});

		JLabel lblGender = new JLabel("성별");
		lblGender.setBounds(66, 390, 100, 32);
		lblSetting(lblGender);

		JToggleButton btnMan = new JToggleButton("남자");
		btnMan.setBounds(63, 419, 117, 31);
		btnSetting(btnMan);
		btnMan.setSelected(true);

		JToggleButton btnWoman = new JToggleButton("여자");
		btnWoman.setBounds(180, 419, 117, 31);
		btnSetting(btnWoman);

		ButtonGroup group = new ButtonGroup();
		group.add(btnMan);
		group.add(btnWoman);

		lblResult = new JLabel("");
		lblResult.setBounds(64, 455, 220, 39);
		lblResult.setFont(new Font("맑은 고딕", Font.BOLD, 11));
		lblResult.setBorder(null);
		lblResult.setForeground(Color.RED);
		contentPane.add(lblResult);

		btnNext = new JButton("다음");
		btnNext.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		btnNext.setBackground(new Color(96, 182, 230));
		btnNext.setBorderPainted(false);
		btnNext.setForeground(Color.white);
		btnNext.setBounds(62, 488, 236, 39);
		contentPane.add(btnNext);
		btnNext.addKeyListener(this);
		btnNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = tfId.getText();
				String pw = getPassword(pfPw);
				String name = tfName.getText();
				String mbti = tfMbti.getText().toUpperCase();
				String gender = "";
				if (btnMan.isSelected()) {
					gender = "남";
				} else if (btnWoman.isSelected()) {
					gender = "여";
				}
				if (checkNotInput(id, pw, name, mbti, gender)) {
					checkInput(id, pw, name, mbti, gender);
				}
			}
		});
	}

	// 텍스트필드 설정
	private void tfSetting(JTextField tf) {
		tf.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		tf.setBackground(new Color(240, 241, 242));
		tf.setForeground(Color.gray);
		tf.setBorder(new EmptyBorder(0, 7, 0, 7));
		tf.setColumns(20);
		tf.addFocusListener(this);
		tf.addKeyListener(this);
		tf.addMouseListener(this);
		contentPane.add(tf);
	}

	// 라벨 설정
	private void lblSetting(JLabel lbl) {
		lbl.setFont(new Font("맑은 고딕", Font.BOLD, 11));
		lbl.setBackground(Color.white);
		lbl.setForeground(Color.DARK_GRAY);
		contentPane.add(lbl);
	}

	// 성별 토글버튼 설정
	private void btnSetting(JToggleButton btn) {
		btn.setFont(new Font("맑은 고딕", Font.BOLD, 11));
		btn.setBackground(Color.WHITE);
		btn.setBorder(new LineBorder(Color.lightGray));
		btn.addKeyListener(this);
		contentPane.add(btn);
	}

	// 비밀번호값 구하기
	private String getPassword(JPasswordField pfPw) {
		char[] c = pfPw.getPassword();
		String pw = "";
		for (int i = 0; i < c.length; i++) {
			pw += c[i];
		}
		return pw;
	}

	// 체크이미지(입력값 확인)
	private JLabel checkImage(JLabel lbl, int i) {
		JLabel lblCheck = new JLabel();
		lblCheck.setIcon(new ImageIcon(im.getUnchecked()));
		lblCheck.setBounds(i, 10, 15, 15);
		lblCheck.setBackground(null);
		lbl.add(lblCheck);
		return lblCheck;
	}

	// 입력값 확인 후 체크이미지 변경
	private void checkInputImage(KeyEvent e) {
		if (e.getSource() == tfId) {
			if (join.checkId(tfId.getText())) {
				checkId.setIcon(new ImageIcon(im.getChecked()));
			} else {
				checkId.setIcon(new ImageIcon(im.getUnchecked()));
			}
		} else if (e.getSource() == pfPw) {
			if (join.checkPw(getPassword(pfPw))) {
				checkPw.setIcon(new ImageIcon(im.getChecked()));
			} else {
				checkPw.setIcon(new ImageIcon(im.getUnchecked()));
			}
		} else if (e.getSource() == tfName) {
			if (join.checkName(tfName.getText())) {
				checkName.setIcon(new ImageIcon(im.getChecked()));
			} else {
				checkName.setIcon(new ImageIcon(im.getUnchecked()));
			}
		} else if (e.getSource() == tfMbti) {
			if (join.checkMbti(tfMbti.getText())) {
				checkMbti.setIcon(new ImageIcon(im.getChecked()));
			} else {
				checkMbti.setIcon(new ImageIcon(im.getUnchecked()));
			}
		}
	}

	// 입력값 없는지 확인
	public boolean checkNotInput(String id, String pw, String name, String mbti, String gender) {
		if (name.equals("이름")) {
			lblResult.setText("이름를 입력해주세요.");
			return false;
		} else if (id.equals("아이디")) {
			lblResult.setText("아이디를 입력해주세요.");
			return false;
		} else if (mbti.equals("MBTI")) {
			lblResult.setText("MBTI를 입력해주세요.");
			return false;
		} else if (pw.equals("비밀번호")) {
			lblResult.setText("비밀번호를 입력해주세요.");
			return false;
		} else {
			return true;
		}
	}

	public void checkInput(String id, String pw, String name, String mbti, String gender) {
		int result = join.checkInput(id, pw, name, mbti, gender);
		if (result == join.joinComplete) {
			System.out.println("회원가입 완료");
			lblResult.setText("");
			new ExamDialog(join.getUser()).setVisible(true);
			dispose();

		} else if (result == join.joinFailByName) {
			lblResult.setText("이름 입력이 잘못되었습니다.(1~4자)");
		} else if (result == join.joinFailByDuplicate) {
			lblResult.setText("이미 가입된 아이디입니다.");
		} else if (result == join.joinFailById) {
			lblResult.setText("아이디 입력이 잘못되었습니다.(4~20자)");
		} else if (result == join.joinFailByMbti) {
			lblResult.setText("MBTI 입력이 잘못되었습니다.(ex.INFP)");
		} else if (result == join.joinFailByPw) {
			lblResult.setText("비밀번호 입력이 잘못되었습니다.(4~20자)");
		}
	}

	@Override
	public void focusGained(FocusEvent e) { // 입력시 초기값 지우기
		if (e.getSource() == tfName && tfName.getText().equals("이름")) {
			tfName.setText("");
		} else if (e.getSource() == tfId && tfId.getText().equals("아이디")) {
			tfId.setText("");
		} else if (e.getSource() == tfMbti && tfMbti.getText().equals("MBTI")) {
			tfMbti.setText("");
		} else if (e.getSource() == pfPw && getPassword(pfPw).equals("비밀번호")) {
			pfPw.setText("");
		}
		if (e.getSource() == btnMbti) {
			if (join.checkMbti(tfMbti.getText())) {
				checkMbti.setIcon(new ImageIcon(im.getChecked()));
			} else {
				checkMbti.setIcon(new ImageIcon(im.getUnchecked()));
			}
		}
	}

	@Override
	public void focusLost(FocusEvent e) { // 내용을 지우면 초기값 설정
		if (e.getSource() == tfName && tfName.getText().equals("")) {
			tfName.setText("이름");
		} else if (e.getSource() == tfId && tfId.getText().equals("")) {
			tfId.setText("아이디");
		} else if (e.getSource() == tfMbti && tfMbti.getText().equals("")) {
			tfMbti.setText("MBTI");
		} else if (e.getSource() == pfPw && getPassword(pfPw).equals("")) {
			pfPw.setText("비밀번호");
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource() == btnMbti) {
			btnMbti.setIcon(new ImageIcon(im.getqImage2()));
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource() == btnMbti) {
			btnMbti.setIcon(new ImageIcon(im.getqImage()));
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			btnNext.doClick();
		}
		checkInputImage(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		checkInputImage(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		checkInputImage(e);
	}
}