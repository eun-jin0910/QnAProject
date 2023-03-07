package object;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import game.GameFrame;
import game.GameModeServiceImpl;
import game.GameModeServiceToolImpl;
import style.ImageService;
import style.ImageServiceImpl;
import style.ImageServiceToolImpl;

public class OptionButtonListener implements ActionListener {
	private GameFrame gameFrame;

	public OptionButtonListener(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		GameModeServiceImpl gameModeService = new GameModeServiceImpl(new GameModeServiceToolImpl());
		ImageService imageService = new ImageServiceImpl(new ImageServiceToolImpl());
		gameFrame.getOptionPanel().setVisible(false);
		gameFrame.setSelectedMode(false);
		gameFrame.setSelectOption(e.getActionCommand());
		gameFrame.setOptionAnswer();
		gameFrame.setTextLbl(gameModeService.setTextByImageIndex(gameFrame));
		gameFrame.setTextLbl2(gameModeService.setTextByImageIndex2(gameFrame));
		int choice = gameModeService.selectByOption(e.getActionCommand());
		gameFrame.getChoiceList().add(choice);
		boolean answer = gameFrame.getMissonList().contains(choice);
		if (answer) {
			gameFrame.setScore(gameFrame.getScore() + 10);
		}
		if (gameFrame.getCurrentImageIndex() == 48) {
			for (Image date : imageService.theDayWeMeet1(answer, gameFrame.getDefender())) {
				gameFrame.getResultImages().add(date);
			}
		}
		if (gameFrame.getCurrentImageIndex() == 57) {
			for (Image date : imageService.theDayWeMeet2(answer, gameFrame.getDefender())) {
				gameFrame.getResultImages().add(date);
			}
		}
		if (gameFrame.getCurrentImageIndex() == 69) {
			for (Image date : imageService.theDayWeMeet3(answer, gameFrame.getDefender())) {
				gameFrame.getResultImages().add(date);
			}
		}
		if (gameFrame.getCurrentImageIndex() == 74) {
			for (Image date : imageService.theDayWeMeet4(answer, gameFrame.getDefender())) {
				gameFrame.getResultImages().add(date);
			}
		}
		gameFrame.nextImage();
	}
}
