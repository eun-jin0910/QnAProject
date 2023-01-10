package exam;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;


import login.Login;
import login.UserinfoRepository;
import login.UserinfoRepositoryImpl;
import object.User;

public class ExamDialog extends JDialog implements ActionListener {

	private JPanel contentPane;
	private JButton signUpButton;
	private static JRadioButton radiobtn[] = new JRadioButton[41];
	private static JLabel lblquestion[] = new JLabel[10];
	private static JLabel number = new JLabel();
	private JLabel noSelectlbl;
	private List<Integer> examEachNum;
	private List<String> fixOptionList;
	static List<Integer> selectNum = new ArrayList<>();
	private List<String> QuestionList;
	private examServiceImpl esi;
	private User user;

//	public static void main(String[] args) {
//
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ExamFrame frame = new ExamFrame();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	public ExamDialog(User user) {
		setModal(true);
		this.user = user;
		esi = new examServiceImpl();

//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(560, 150, 549, 723);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// 질문지 제목
		JLabel questionTitle = new JLabel("질문지");
		questionTitle.setBounds(218, 10, 97, 54);
		questionTitle.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		contentPane.add(questionTitle);

		// 가입하기 버튼
		signUpButton = new JButton("가입하기");
		signUpButton.setBounds(198, 625, 135, 40);
		signUpButton.setBackground(new Color(96, 182, 230));
		signUpButton.setBorderPainted(false);
		signUpButton.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		signUpButton.setForeground(Color.white);
		contentPane.add(signUpButton);
		signUpButton.addActionListener(this);

		// 스크롤
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 80, 509, 514);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPane);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		scrollPane.setViewportView(panel);
		panel.setPreferredSize(new Dimension(405, 200 * 13 - 85));
		panel.setLayout(null);

		noSelectlbl = new JLabel("");
		noSelectlbl.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		noSelectlbl.setBounds(172, 604, 193, 15);
		noSelectlbl.setBorder(null);
		noSelectlbl.setForeground(Color.RED);
		contentPane.add(noSelectlbl);

		examEachNum = esi.readEachNum(); // 각 문제 문항 갯수 리스트 (5,2,4,5~)
		fixOptionList = esi.readFixOption(); // 각 답 문항 글자 리스트 (SG워너비~,)
		QuestionList = esi.readQuestion(); // 각 질문 문항 글자 리스트 (노래 선택지~,)

		int count = 0;
		for (int i = 1; i < 11; i++) {
			// 질문지 패널
			JPanel panel_i = new JPanel();
			panel_i.setBounds(12, 10 + (250 * (i - 1)), 470, 240);
			panel_i.setBackground(new Color(240, 248, 253));
			panel.add(panel_i);
			panel_i.setLayout(null);
			panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

			// 질문지 라벨
			number = new JLabel();
			number.setText(String.valueOf(i) + ".");
			number.setFont(new Font("맑은 고딕", Font.BOLD, 14));
			number.setBounds(28, 18, 295, 25);
			panel_i.add(number);

			lblquestion[i - 1] = new JLabel();
			lblquestion[i - 1].setText(QuestionList.get(i - 1));
			lblquestion[i - 1].setFont(new Font("맑은 고딕", Font.BOLD, 14));
			lblquestion[i - 1].setBounds(50, 18, 295, 25);
			panel_i.add(lblquestion[i - 1]);

			// 라디오버튼
			int z = 0;
			for (int j = count; j < count + examEachNum.get(i - 1); j++) {
				radiobtn[j] = new JRadioButton();
				radiobtn[j].setText(fixOptionList.get(j));
				radiobtn[j].setBounds(40, 53 + (30 * z), 400, 23);
				radiobtn[j].setFont(new Font("맑은 고딕", Font.PLAIN, 12));
				radiobtn[j].setOpaque(false);
				panel_i.add(radiobtn[j]);
				z++;
			}

			ButtonGroup group = new ButtonGroup();
			for (int j = count; j < count + examEachNum.get(i - 1); j++) {
				group.add(radiobtn[j]);
			}
			count += examEachNum.get(i - 1);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// 가입하기 버튼 눌렸을 때
		if (signUpButton.equals(e.getSource())) {
			selectNum.removeAll(selectNum);
			// 버튼 선택된거
			for (int i = 0; i < radiobtn.length; i++) {
				if (radiobtn[i].isSelected()) {
					selectNum.add(i);
				}
			}
//			System.out.println("가입하기 선택된번호" + selectNum);

			if (selectNum.size() == 10) {
				UserinfoRepository userinfoRep = new UserinfoRepositoryImpl();
				userinfoRep.userInsert(user);
				esi.signUp(selectNum, user);
				new Login();
				dispose();
			} else {
				noSelectlbl.setText("선택되지 않은 문항이 있습니다.");
			}
		}
	}
}
