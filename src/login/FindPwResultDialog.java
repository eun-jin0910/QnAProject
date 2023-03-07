package login;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;


public class FindPwResultDialog extends JDialog {
	private JPanel contentPane;
	private JTextField tfPw;
	private JTextField tfMbti;
	private JButton btnOk;

	public JTextField getTfMbti() {
		return tfMbti;
	}

	public void setTfMbti(JTextField tfMbti) {
		this.tfMbti = tfMbti;
	}

	public FindPwResultDialog(String pw) {
		setModal(true);
		setBounds(720, 240, 350, 385);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTextPane textPane = new JTextPane();
		textPane.setFont(new Font("맑은 고딕", Font.BOLD, 24));
		textPane.setText("비밀번호 찾기");
		textPane.setEditable(false);
		textPane.setBounds(88, 60, 160, 39);
		contentPane.add(textPane);

		JLabel lblPw = new JLabel("비밀번호");
		lblPw.setBounds(55, 128, 100, 32);
		lblSetting(lblPw);
		tfPw = new JTextField(pw);
		tfPw.setEditable(false);
		tfPw.setBounds(55, 160, 230, 35);
		tfSetting(tfPw);
		
		btnOk = new JButton("확인");
		btnOk.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		btnOk.setBackground(new Color(96, 182, 230));
		btnOk.setBorderPainted(false);
		btnOk.setForeground(Color.white);
		btnOk.setBounds(55, 210, 230, 39);
		contentPane.add(btnOk);
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

	// 텍스트필드 설정
	private void tfSetting(JTextField tf) {
		tf.setFont(new Font("맑은 고딕", Font.BOLD, 13));
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