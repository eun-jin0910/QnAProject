package login;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import com.sun.corba.se.spi.orbutil.fsm.Action;

import javax.swing.JTextPane;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame implements FocusListener {
	private JPanel contentPane;
	private JTextField tfId;
	private JTextField tfPw;
	private JPasswordField pfPw;
	private JLabel lblResult;
	private JButton btnFind;
	private JButton btnLogin;
	private JButton btnJoin;
	private JoinDialog joinDialog;

	public LoginFrame(Login login) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(700, 250, 350, 455);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextPane textPane = new JTextPane();
		textPane.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		textPane.setText("로그인");
		textPane.setEditable(false);
		textPane.setBounds(118, 64, 115, 39);
		contentPane.add(textPane);
		
		tfId = new JTextField("아이디");
		tfId.setBounds(58, 141, 220, 39);
		tfSetting(tfId);
		
		tfPw = new JTextField("비밀번호");
		tfPw.setBounds(58, 190, 220, 39);
		tfSetting(tfPw);
		
//		pfPw = new JPasswordField("비밀번호");
//		pfPw.setBounds(58, 190, 220, 39);
//		tfSetting(pfPw); // 암호화 되지만 어떤값을 입력해야하는지 알려주지 못함
		
		lblResult = new JLabel("");
		lblResult.setBounds(59, 228, 220, 39);
		lblResult.setFont(new Font("맑은 고딕", Font.BOLD, 11));
		lblResult.setBorder(null);
		lblResult.setForeground(Color.RED);
		contentPane.add(lblResult);
		
		btnLogin = new JButton("로그인");
		btnLogin.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		btnLogin.setBackground(new Color(96,182,230));
		btnLogin.setBorderPainted(false);
		btnLogin.setForeground(Color.white);
		btnLogin.setBounds(58, 260, 220, 39);
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = login.checkLogin(tfId.getText(), tfPw.getText());
				if (result == login.loginComplete) {
					// 로그인 완료
					lblResult.setText("");
				} else if (result == login.loginFailByPw) {
					lblResult.setText("비밀번호가 일치하지 않습니다.");
				} else if (result == login.loginFailById) {
					lblResult.setText("존재하지 않는 아이디입니다.");
				}
			}
		});
		
		contentPane.add(btnLogin);
		
		btnJoin = new JButton("회원가입");
		btnJoin.setFont(new Font("맑은 고딕", Font.BOLD, 11));
		btnJoin.setBackground(Color.WHITE);
		btnJoin.setBorderPainted(false);
		btnJoin.setBounds(79, 308, 81, 23);
		btnJoin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				joinDialog = new JoinDialog();
				joinDialog.setVisible(true);
			}
		});
		contentPane.add(btnJoin);
		
		btnFind = new JButton("ID/PW 찾기");
		btnFind.setFont(new Font("맑은 고딕", Font.BOLD, 11));
		btnFind.setBorderPainted(false);
		btnFind.setBackground(Color.WHITE);
		btnFind.setBounds(155, 308, 95, 23);
		contentPane.add(btnFind);
	}
	
	// 텍스트필드 설정
	public void tfSetting(JTextField j) {
		j.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		j.setBackground(new Color(240,241,242));
		j.setBorder(null);
		j.setForeground(new Color(88,89,91));
		j.setBorder(new EmptyBorder(0, 7, 0, 7));
		j.setColumns(20);
		j.addFocusListener(this);
		contentPane.add(j);
	}

	@Override
	public void focusGained(FocusEvent e) {
		if (e.getSource() == tfId && tfId.getText().equals("아이디")) {
			tfId.setText("");
		} else if (e.getSource() == tfPw && tfPw.getText().equals("비밀번호")) {
			tfPw.setText("");
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		if (e.getSource() == tfId && tfId.getText().equals("")) {
			tfId.setText("아이디");
		} else if (e.getSource() == tfPw && tfPw.getText().equals("")) {
			tfPw.setText("비밀번호");
		}
	}
	
	// 비밀번호 암호화
	public void hidePw() {
	}
}