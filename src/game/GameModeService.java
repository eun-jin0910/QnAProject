package game;

import java.awt.Image;
import java.util.List;

import javax.swing.JLabel;

import object.User;

public interface GameModeService {
	public List<Image> makeFirstHalfList(User user);
	public List<Image> makeSecondHalfList(User user, int score);
	public void decideSecondHalf(User attacker, List<Image> resultImages, int score);
	public void gameComplete(User user, User defender);
	public int selectByOption(String option);
	public void dataTransferToDB(User user, User defender ,GameFrame gameFrame);
	public boolean isUserPlayedGameBefore(User user, User defender);
	public JLabel setTextByImageIndex(GameFrame gameFrame);
	public JLabel setTextByImageIndex2(GameFrame gameFrame);
}
