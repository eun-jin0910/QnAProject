package mypage;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import exam.examServiceImpl;
import login.UserinfoRepository;
import login.UserinfoRepositoryImpl;
import mbti.MbtiImageManager;
import object.User;

public class SecessionDialog extends JDialog {
	private JPanel contentPane;
	private JTextField tfId;
	private JTextField pfPw;
	private JButton btnSecession;
	private JLabel noPw;
	private UserinfoRepositoryImpl uri = new UserinfoRepositoryImpl();
	public boolean sucessionDispose = false;

	public boolean isSucessionDispose() {
		return sucessionDispose;
	}

	public void setSucessionDispose(boolean sucessionDispose) {
		this.sucessionDispose = sucessionDispose;
	}

	// 라벨 설정
	private void lblSetting(JLabel lbl) {
		lbl.setFont(new Font("맑은 고딕", Font.BOLD, 11));
		lbl.setBackground(Color.white);
		lbl.setForeground(Color.DARK_GRAY);
		contentPane.add(lbl);
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

	public SecessionDialog(User user) {
		setModal(true);
		setBounds(700, 180, 378, 450);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTextPane textPane = new JTextPane();
		textPane.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		textPane.setText("회원탈퇴");
		textPane.setEditable(false);
		textPane.setBounds(123, 60, 131, 39);
		contentPane.add(textPane);

		JLabel lblId = new JLabel("아이디");
		lblId.setBounds(66, 130, 100, 32);
		lblSetting(lblId);
		tfId = new JTextField(user.getId());
		tfId.setBounds(63, 159, 234, 33);
		tfId.setEditable(false);
		tfSetting(tfId);

		JLabel lblPw = new JLabel("비밀번호를 입력해주세요");
		lblPw.setBounds(66, 195, 150, 32);
		lblSetting(lblPw);

		pfPw = new JTextField();
		pfPw.setBounds(63, 224, 234, 33);
		tfSetting(pfPw);

		noPw = new JLabel("");
		noPw.setBounds(66, 270, 170, 32);
		noPw.setFont(new Font("맑은 고딕", Font.BOLD, 11));
		noPw.setBackground(Color.white);
		noPw.setForeground(Color.RED);
		contentPane.add(noPw);

		btnSecession = new JButton("탈퇴하기");
		btnSecession.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		btnSecession.setBackground(new Color(96, 182, 230));
		btnSecession.setBorderPainted(false);
		btnSecession.setForeground(Color.white);
		btnSecession.setBounds(62, 300, 236, 39);
		contentPane.add(btnSecession);
		btnSecession.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String a = user.getPw();

				if (a.equals(pfPw.getText())) {
					uri.userDelete(user);
					new CheckDialog().setVisible(true);
					sucessionDispose = true;
					dispose();
				} else {
					noPw.setText("비밀번호를 잘못 입력하였습니다.");
				}
			}
		});
	}
}
