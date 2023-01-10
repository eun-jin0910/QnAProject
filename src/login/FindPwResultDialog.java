package login;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import mbti.MbtiImageManager;

public class FindPwResultDialog extends JDialog {
	private JPanel contentPane;
	private JTextField tfPw;
	private JTextField tfMbti;

	public JTextField getTfMbti() {
		return tfMbti;
	}

	public void setTfMbti(JTextField tfMbti) {
		this.tfMbti = tfMbti;
	}

//	public static void main(String[] args) {
//		new FindPwResultDialog("1234").setVisible(true);
//	}

	public FindPwResultDialog(String pw) {
		setModal(true);
		setBounds(700, 180, 378, 465);
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

		JLabel lblPw = new JLabel("비밀번호");
		lblPw.setBounds(66, 180, 100, 32);
		lblSetting(lblPw);
		tfPw = new JTextField(pw);
		tfPw.setBounds(63, 209, 234, 33);
		tfSetting(tfPw);

	}

	// 텍스트필드 설정
	private void tfSetting(JTextField tf) {
		tf.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		tf.setBackground(new Color(240, 241, 242));
		tf.setForeground(Color.gray);
		tf.setBorder(new EmptyBorder(0, 7, 0, 7));
		tf.setColumns(20);
		contentPane.add(tf);
	}

	// 라벨 설정
	private void lblSetting(JLabel lbl) {
		lbl.setFont(new Font("맑은 고딕", Font.BOLD, 11));
		lbl.setBackground(Color.white);
		lbl.setForeground(Color.DARK_GRAY);
		contentPane.add(lbl);
	}

}