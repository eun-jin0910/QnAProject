package ranking;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import object.User;


public class UserRankDialog extends JDialog implements ActionListener {
	private DefaultTableModel model;
	private JTable table;
	
	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public DefaultTableModel getModel() {
		return model;
	}

	public void setModel(DefaultTableModel model) {
		this.model = model;
	}

	public UserRankDialog(User user) {
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		setModal(true);
		String resultText = "<HTML><body style='text-align:center;'>" + user.getId() + "님 <br>공략 랭킹</body></HTML>";
		JLabel rankingLabel = new JLabel(resultText);
		rankingLabel.setBackground(Color.BLACK);
		rankingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		rankingLabel.setForeground(Color.BLACK);
		rankingLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		rankingLabel.setBounds(21, 10, 261, 50);
		getContentPane().add(rankingLabel);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		String[] header = { "랭킹", "ID", "점수" };
		model = new DefaultTableModel(header, 0);
		table = new JTable(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setDefaultEditor(Object.class, null);
		table.setPreferredSize(new Dimension(270, 450));
		
		mainPanel.add(table);
		JScrollPane scrollPane = new JScrollPane(table);
		mainPanel.add(scrollPane, "Center");
		mainPanel.setBounds(12, 70, 270, 250);
		getContentPane().add(mainPanel);

		JButton closeBtn = new JButton("확인");
		closeBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		closeBtn.setForeground(new Color(255, 255, 255));
		closeBtn.setBounds(92, 330, 97, 23);
		closeBtn.setBackground(new Color(92, 180, 229));
		closeBtn.addActionListener(this);
		getContentPane().add(closeBtn);
		
		setSize(300, 400);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		dispose();
	}
}
