package mypage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import exam.examServiceImpl;
import object.User;

import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ScrollPaneConstants;
import javax.swing.BorderFactory;

public class ChangeExamDialog extends JDialog implements ActionListener {

	private JPanel contentPane;
	private static JRadioButton radiobtn[] = new JRadioButton[41];
	private static JLabel lblTest[] = new JLabel[10];
	private static JLabel number = new JLabel();
	private JButton editButton;
	private examServiceImpl esi;
	private List<Integer> examEachNum;
	private List<String> fixOptionList;
	static List<Integer> selectNum = new ArrayList<>();
	private List<String> QuestionList;
	private JLabel noSelectlbl;
	private List<Integer> missionOut;
	private User user;

	// 리스트 값 질문지 표시
	public void selectBtn(List<Integer> e) {
		for (int i = 0; i < 10; i++) {
			int a = e.get(i);
			radiobtn[a].setSelected(true);
		}
	}

	public ChangeExamDialog(User user) {
		this.user = user;
		esi = new examServiceImpl();
		setModal(true);
		setBounds(650, 170, 549, 723);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// 질문지 제목
		JLabel questionTitle = new JLabel("질문지 수정");
		questionTitle.setBounds(190, 10, 180, 54);
		questionTitle.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		contentPane.add(questionTitle);

		// 수정하기 버튼
		editButton = new JButton("수정하기");
		editButton.setBounds(198, 625, 135, 40);
		editButton.setBackground(new Color(96, 182, 230));
		editButton.setBorderPainted(false);
		editButton.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		editButton.setForeground(Color.white);
		contentPane.add(editButton);
		editButton.addActionListener(this);

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

		int count = 0;
		examEachNum = esi.readEachNum();
		fixOptionList = esi.readFixOption();
		QuestionList = esi.readQuestion();

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

			lblTest[i - 1] = new JLabel();
			lblTest[i - 1].setText(QuestionList.get(i - 1));
			lblTest[i - 1].setFont(new Font("맑은 고딕", Font.BOLD, 14));
			lblTest[i - 1].setBounds(50, 18, 295, 25);
			panel_i.add(lblTest[i - 1]);

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

		// 이름으로 질문지 답변 가져오기 (수정)
		String id = user.getId();
		missionOut = esi.readMissionNum(id);

		// mission에서 가져온 인덱스 질문지 표시
		selectBtn(missionOut);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// 수정하기 버튼 눌렸을 때
		if (editButton.equals(e.getSource())) {

			// 버튼 선택된거
			selectNum.removeAll(selectNum);
			for (int i = 0; i < radiobtn.length; i++) {
				if (radiobtn[i].isSelected()) {
					selectNum.add(i);
				}
			}
			if (selectNum.size() == 10) {
				esi.editUp(selectNum, user);
				dispose();
			} else {
				noSelectlbl.setText("선택되지 않은 문항이 있습니다.");
			}
		}
	}
}
