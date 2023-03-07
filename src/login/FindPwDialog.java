package login;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import mbti.MbtiImageManager;

public class FindPwDialog extends JDialog implements FocusListener, KeyListener {
	private JPanel contentPane;
	private JTextField tfName;
	private JTextField tfId;
	private JTextField tfMbti;
	private JLabel lblResult;
	private JButton btnNext;
	private MbtiImageManager im = new MbtiImageManager();
	private UserinfoRepository rep = new UserinfoRepositoryImpl();

	public JTextField getTfMbti() {
		return tfMbti;
	}

	public void setTfMbti(JTextField tfMbti) {
		this.tfMbti = tfMbti;
	}

	public FindPwDialog() {
		setModal(true);
		setBounds(700, 220, 378, 465);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTextPane textPane = new JTextPane();
		textPane.setFont(new Font("맑은 고딕", Font.BOLD, 24));
		textPane.setText("비밀번호 찾기");
		textPane.setEditable(false);
		textPane.setBounds(100, 60, 160, 39);
		contentPane.add(textPane);

		JLabel lblId = new JLabel("아이디");
		lblId.setBounds(66, 130, 100, 32);
		lblSetting(lblId);
		tfId = new JTextField("");
		tfId.setBounds(63, 159, 234, 33);
		tfSetting(tfId);

		JLabel lblName = new JLabel("이름");
		lblName.setBounds(66, 195, 100, 32);
		lblSetting(lblName);
		tfName = new JTextField("");
		tfName.setBounds(63, 224, 234, 33);
		tfSetting(tfName);

		lblResult = new JLabel("");
		lblResult.setBounds(64, 266, 220, 39);
		lblResult.setFont(new Font("맑은 고딕", Font.BOLD, 11));
		lblResult.setBorder(null);
		lblResult.setForeground(Color.RED);
		contentPane.add(lblResult);

		btnNext = new JButton("다음");
		btnNext.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		btnNext.setBackground(new Color(96, 182, 230));
		btnNext.setBorderPainted(false);
		btnNext.setForeground(Color.white);
		btnNext.setBounds(62, 300, 236, 39);
		contentPane.add(btnNext);
		btnNext.addKeyListener(this);
		btnNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = tfId.getText();
				String name = tfName.getText();
				if (checkNotInput(id, name)) {
					String pw = checkInput(id, name);
					System.out.println(pw);
					new FindPwResultDialog(pw).setVisible(true);
					dispose();
				} else {
					System.out.println("실행안됨");
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
		contentPane.add(tf);
	}

	// 라벨 설정
	private void lblSetting(JLabel lbl) {
		lbl.setFont(new Font("맑은 고딕", Font.BOLD, 11));
		lbl.setBackground(Color.white);
		lbl.setForeground(Color.DARK_GRAY);
		contentPane.add(lbl);
	}

	// 패스워드 아이디,이름 확인
	public boolean checkNotInput(String id, String name) {
		if (name.equals("") || id.equals("")) {
			lblResult.setText("다시 입력해주세요.");
			return false;
		}
		int a = rep.countById(id);
		if (a == 0) {
			lblResult.setText("아이디를 잘못입력하셨습니다.");
			return false;
		}
		String name1 = rep.loginUser(id).getName();
		if (!name.equals(name1)) {
			lblResult.setText("이름을 잘못입력하셨습니다.");
			return false;
		} else {
			return true;
		}
	}

	public String checkInput(String id, String name) {
		String pw = "";
		pw = rep.loginUser(id).getPw();
		return pw;
	}

	@Override
	public void focusGained(FocusEvent e) { // 입력시 초기값 지우기
		if (e.getSource() == tfName && tfName.getText().equals("이름")) {
			tfName.setText("");
		} else if (e.getSource() == tfId && tfId.getText().equals("아이디")) {
			tfId.setText("");
		}
	}

	@Override
	public void focusLost(FocusEvent e) { 
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			btnNext.doClick();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
