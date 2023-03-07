package object;

import java.awt.Font;
import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import exam.examServiceImpl;

public class OptionPanel extends JPanel {
	private List<JButton> btns;
	private List<JLabel> lbls;
	private List<JLabel> txtlbls;

	public OptionPanel(int examNo) {
		ClassLoader loader = OptionPanel.this.getClass().getClassLoader();
		URL url = loader.getResource("버튼0.png");
		ImageIcon img = new ImageIcon(url);

		Image stiImg = img.getImage();
		Image stickerImg = stiImg.getScaledInstance(600, 80, Image.SCALE_SMOOTH);
		ImageIcon stickerImgIcon = new ImageIcon(stickerImg);
		setLayout(null);
		setOpaque(false);
		examServiceImpl esi = new examServiceImpl();
		int optionNum = esi.countOptionByExamNo(examNo);
		List<String> optionList = esi.readOptionByExamNo(examNo);
		btns = new ArrayList<>();
		lbls = new ArrayList<>();
		txtlbls = new ArrayList<>();
		Font font = new Font("맑은 고딕", Font.BOLD, 19);
		int[] y = { 250, 340, 160, 430, 70, 520 };

		for (int i = 0; i < optionNum; i++) {
			btns.add(new JButton());
			btns.get(i).setBounds(150, y[i], 600, 80);
			btns.get(i).setOpaque(true);
			btns.get(i).setLayout(null);
			lbls.add(new JLabel(stickerImgIcon));
			lbls.get(i).setBounds(0, 0, 600, 80);
			btns.get(i).setText(optionList.get(i));
			txtlbls.add(new JLabel());
			txtlbls.get(i).setText(optionList.get(i));
			txtlbls.get(i).setFont(font);
			txtlbls.get(i).setBounds(0, 0, 600, 80);
			txtlbls.get(i).setHorizontalAlignment(JLabel.CENTER);
			
			lbls.get(i).add(txtlbls.get(i));
			btns.get(i).add(lbls.get(i));
			add(btns.get(i));
		}
	}

	public List<JButton> getBtns() {
		return btns;
	}

	public void setBtns(List<JButton> btns) {
		this.btns = btns;
	}
}
