package gametest;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import game.GameModeServiceImpl;
import game.GameModeServiceToolImpl;

class SelectAnswer {
	public SelectAnswer(String asga) {
		
	}
}

public class GameFrameTest extends JFrame {
	JLabel label;
	int currentImageIndex = 0;
	List<Image> images;
	int ooo = 0;
	// 퀴즈배열, 열릴페이지, X값, Y값, 가로길이, 세로길이 , Y값늘어나는값
	public void makingQuestion(JButton[] questionClickButton, int pageNum, int questionX, int questionY, int questionlength, int questionhight, int questionYPlus) {
		if (currentImageIndex == pageNum) {
			ooo = currentImageIndex;
			for (int questionArrNum1 = 0; questionArrNum1 < questionClickButton.length; questionArrNum1++) {
				label.add(questionClickButton[questionArrNum1]);
				questionClickButton[questionArrNum1].setBounds(questionX, questionY, questionlength, questionhight);
				questionY += questionYPlus;
				questionClickButton[questionArrNum1].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						currentImageIndex = ooo + 1;
						label.setIcon(new ImageIcon(images.get(currentImageIndex)));
						System.out.println(currentImageIndex);
						for (int removeNum = 0; removeNum < questionClickButton.length; removeNum++) {
							label.remove(questionClickButton[removeNum]);					
						}
						label.setFocusable(true);
					}
				});
			}	
		}
	}
	
	public GameFrameTest() {
		GameModeServiceImpl gms = new GameModeServiceImpl(new GameModeServiceToolImpl());
		Toolkit kit = Toolkit.getDefaultToolkit();
		ClassLoader classLoader = getClass().getClassLoader();

		File imagesDir = new File(classLoader.getResource("images").getFile());
		int countImages = imagesDir.listFiles().length;
		images = new ArrayList<>();
		for (int i = 0; i < countImages; i++) {
			images.add(kit.getImage(classLoader.getResource("images//집남자카톡" + i + ".jpg")));
		}
		
		label = new JLabel(new ImageIcon(images.get(currentImageIndex)));
		getContentPane().add(label, BorderLayout.CENTER);

		setSize(960, 740);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JButton option_1 = new JButton();
		option_1.setBorderPainted(false);
		option_1.setIcon(new ImageIcon(images.get(currentImageIndex)));
		
		JButton[] question1 = new JButton[5]; 
		question1[0] = new KyungtaeButton("SG워너비 - Timeless(발라드)");
		question1[1] = new KyungtaeButton("프리스타일 - Y(힙합)");
		question1[2] = new KyungtaeButton("뉴진스 - Hype Boy(댄스)");
		question1[3] = new KyungtaeButton("쿨시스터즈 - 왜그랬을까(트로트)");
		question1[4] = new KyungtaeButton("광야를 지나며(CCM)");
		
		JButton[] question2 = new JButton[2]; 
		question2[0] = new KyungtaeButton("저는 집돌이라 집에 처박혀있었네요 ㅋㅋ");
		question2[1] = new KyungtaeButton("어제 친구들이랑 나가서 술 퍼먹었어요 ㅋㅋ");
		
		JButton[] question3 = new JButton[4]; 
		question3[0] = new KyungtaeButton("모르는 고등학교 홈페이지가서 급식 메뉴 염탐하기");
		question3[1] = new KyungtaeButton("불법주차한 자동차 사진찍어서 국민신문고에 올리기");
		question3[2] = new KyungtaeButton("대학생 수준의 미분방정식 수학 문제 풀기");
		question3[3] = new KyungtaeButton("맨손으로 민물고기 잡으러 다니기");
		
		JButton[] question4 = new JButton[5]; 
		question4[0] = new KyungtaeButton("국밥 (한식)");
		question4[1] = new KyungtaeButton("피자 (양식)");
		question4[2] = new KyungtaeButton("짬뽕 (중식) ");
		question4[3] = new KyungtaeButton("초밥 (일식)");
		question4[4] = new KyungtaeButton("빵 (맛있음)");
		
		JButton[] question5 = new JButton[6]; 
		question5[0] = new KyungtaeButton("분위기가 좋은");
		question5[1] = new KyungtaeButton("캐쉬백 혜택이 있는");
		question5[2] = new KyungtaeButton(" 1+1 혜택이 있는");
		question5[3] = new KyungtaeButton("특별 할인 음식이 있는");
		question5[4] = new KyungtaeButton("무료 음식이 있는");
		question5[5] = new KyungtaeButton("예약시 무료 제공되는");
		
		MouseAdapter a = new MouseAdapter() {	
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println(currentImageIndex);
				if (currentImageIndex != 5 &&
						currentImageIndex != 10 &&
						currentImageIndex != 16 &&
						currentImageIndex != 25 &&
						currentImageIndex != 29
						) {
					currentImageIndex = (currentImageIndex + 1) % images.size();
					label.setIcon(new ImageIcon(images.get(currentImageIndex)));
				}
				// makingQuestion(퀴즈배열, 열릴페이지, X값, Y값, 가로길이, 세로길이 , Y값늘어나는값);
				makingQuestion(question1, 5, 300, 100, 400, 80, 100);
				makingQuestion(question2, 10, 150, 200, 650, 80, 150);
				makingQuestion(question3, 16, 300, 100, 400, 80, 130);
				makingQuestion(question4, 25, 300, 100, 400, 80, 100);
				makingQuestion(question5, 29, 300, 100, 400, 80, 100);
			}
		};
		label.addMouseListener(a);
	}

	public static void main(String[] args) {
		GameFrameTest gf = new GameFrameTest();
		gf.setVisible(true);
	}
}