package mypage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import lobby.LobbyFrame;
import login.Login;

public class CheckDialog extends JDialog {
	private final JPanel contentPane = new JPanel();
	public static void main(String[] args) {
		try {
			CheckDialog dialog = new CheckDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public CheckDialog() {
		
		setModal(true);
		setBounds(800, 300, 292, 193);
		getContentPane().setLayout(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.WHITE);
		getContentPane().add(contentPane, BorderLayout.CENTER);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("탈퇴 완료되었습니다.");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		label.setBounds(75, 30, 210, 39);
		contentPane.add(label);
		
		JButton OkButton = new JButton("확인");
		OkButton.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		OkButton.setBackground(new Color(96,182,230));
		OkButton.setBorderPainted(false);
		OkButton.setForeground(Color.white);
		OkButton.setBounds(74, 83, 134, 39);
		contentPane.add(OkButton);
		OkButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				new Login();
			}
		});
	}
}