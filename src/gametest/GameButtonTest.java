package gametest;

import javax.swing.JButton;

class KyungtaeButton extends JButton {
	public KyungtaeButton(String setText) {
		this.setText(setText);
		this.revalidate();
		this.repaint();
	}
}

