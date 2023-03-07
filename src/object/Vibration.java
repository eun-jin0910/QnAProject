package object;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

public class Vibration {
	private Timer timer;

	public Vibration(JLabel lbl) {
		
		timer = new Timer(1, new ActionListener() {
			int dx = 5; 
			int x = 0; 
			int movesCount = 0;

			@Override
			public void actionPerformed(ActionEvent e) {
				lbl.setLocation(x, 0);
				lbl.repaint();

				x += dx;
				
				if (x > 10 || x < 0) {
					dx = -dx;
					movesCount++;
				}
				if (movesCount == 10) {
					timer.stop();
				}
			}
		});
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}
}
