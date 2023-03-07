package lobby;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import game.GameFrame;
import login.Login;
import login.UserinfoRepositoryImpl;
import mypage.MypageInfo;
import object.Attacker;
import object.User;
import ranking.UserRankDialog;

public class LobbyFrame extends JFrame implements ActionListener {
	private JPanel contentPane;
	private JTextField inputInfo;
	private JTable table;
	private Object str = "ALL";
	private DefaultTableModel model;
	private LobbyServiceImpl lsi;
	private TableRowSorter<TableModel> sorter;
	private UserinfoRepositoryImpl repo = new UserinfoRepositoryImpl();

	public LobbyFrame(User user) {
		lsi = new LobbyServiceImpl(new LobbyServiceToolImpl());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 500);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton startBtn = new JButton("공략 시작");
		startBtn.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		startBtn.setForeground(Color.WHITE);
		startBtn.setBorderPainted(false);
		startBtn.setBackground(new Color(92, 180, 229));
		startBtn.setBounds(38, 44, 101, 200);
		contentPane.add(startBtn);
		startBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (lsi.isRowSelected(table)) {
					int seletedRow = table.getSelectedRow();
					Object userId = table.getValueAt(seletedRow, 2);
					User defender = repo.loginUser((String) userId);
					GameFrame gf = new GameFrame(user, defender);
					gf.setVisible(true);
					dispose();
				}
			}
		});

		JPanel panel = new JPanel();
		panel.setBackground(new Color(240, 248, 253));
		panel.setBounds(151, 44, 350, 380);
		contentPane.add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 37, 326, 300);
		panel.add(scrollPane);
		String[] header = { "이름", "성별", "아이디", "MBTI" };
		model = new DefaultTableModel(header, 0);
		table = new JTable(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setDefaultEditor(Object.class, null);
		table.setBackground(Color.WHITE);
		scrollPane.setViewportView(table);

		JLabel listLabel = new JLabel("공략 리스트");
		listLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		listLabel.setHorizontalAlignment(SwingConstants.CENTER);
		listLabel.setBounds(136, 12, 80, 15);
		panel.add(listLabel);

		inputInfo = new JTextField();
		inputInfo.setBounds(114, 344, 116, 21);
		panel.add(inputInfo);
		inputInfo.setColumns(10);
		inputInfo.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					transferMethod();
				}
			}
		});

		JButton searchBtn = new JButton("검색");
		searchBtn.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		searchBtn.setBackground(SystemColor.control);
		searchBtn.setBounds(242, 343, 74, 23);
		searchBtn.addActionListener(this);
		panel.add(searchBtn);

		String[] comboName = { "ALL", "이름", "성별", "ID", "MBTI" };
		JComboBox combo = new JComboBox(comboName);
		combo.setBackground(SystemColor.control);
		combo.setBounds(28, 343, 74, 23);
		combo.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				str = combo.getSelectedItem();
			}
		});
		panel.add(combo);

		JButton endBtn = new JButton("로그아웃");
		endBtn.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		endBtn.setForeground(Color.WHITE);
		endBtn.setBorderPainted(false);
		endBtn.setBackground(new Color(92, 180, 229));
		endBtn.setBounds(39, 374, 100, 50);
		endBtn.addActionListener(this);
		contentPane.add(endBtn);

		JButton mypageBtn = new JButton("마이페이지");
		mypageBtn.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		mypageBtn.setForeground(Color.WHITE);
		mypageBtn.setBorderPainted(false);
		mypageBtn.setBackground(new Color(92, 180, 229));
		mypageBtn.setBounds(39, 314, 100, 50);
		mypageBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MypageInfo mi = new MypageInfo(user);
				if (mi.mypageDispose()) {
					dispose();
				}
			}
		});
		contentPane.add(mypageBtn);

		JButton rankBtn = new JButton("랭킹 조회");
		rankBtn.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		rankBtn.setForeground(Color.WHITE);
		rankBtn.setBorderPainted(false);
		rankBtn.setBackground(new Color(92, 180, 229));
		rankBtn.setBounds(38, 254, 100, 50);
		rankBtn.addActionListener(this);
		contentPane.add(rankBtn);

		sorter = new TableRowSorter<TableModel>(model);
		table.setRowSorter(sorter);

		lsi.readUserinfo(model, user);
		setResizable(false);
		setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("로그아웃")) {
			Login login = new Login();
			dispose();
		}
		if (command.equals("랭킹 조회")) {
			if (lsi.isRowSelected(table)) {
				int seletedRow = table.getSelectedRow();
				String userId = (String) table.getValueAt(seletedRow, 2);
				User user = repo.loginUser((String) userId);
				UserRankDialog urd = new UserRankDialog(user);
				List<Attacker> attackerList = lsi.makeAttackerList(user);
				lsi.setUserRanking(urd, attackerList);
				urd.setVisible(true);
			}
		}
		if (command.equals("검색")) {
			if (str.equals("이름")) {
				sorter.setRowFilter(RowFilter.regexFilter("(?i)" + inputInfo.getText(), 0));
			} else if (str.equals("성별")) {
				sorter.setRowFilter(RowFilter.regexFilter("(?i)" + inputInfo.getText(), 1));
			} else if (str.equals("ID")) {
				sorter.setRowFilter(RowFilter.regexFilter("(?i)" + inputInfo.getText(), 2));
			} else if (str.equals("MBTI")) {
				sorter.setRowFilter(RowFilter.regexFilter("(?i)" + inputInfo.getText(), 3));
			} else {
				lsi.infomationFiltering(this);
			}
			inputInfo.setText("");
		}
	}

	private void transferMethod() {
		lsi.infomationFiltering(this);
	}

	public JTextField getInputInfo() {
		return inputInfo;
	}

	public void setInputInfo(JTextField inputInfo) {
		this.inputInfo = inputInfo;
	}

	public TableRowSorter<TableModel> getSorter() {
		return sorter;
	}

	public void setSorter(TableRowSorter<TableModel> sorter) {
		this.sorter = sorter;
	}
}
