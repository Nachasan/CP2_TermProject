import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

import javax.swing.*;

import com.idrsolutions.image.psd.PsdDecoder;

public class Room extends JPanel {
	ArrayList<Card> Deck = new ArrayList<>();
	Deck d = new Deck();
	JButton exit, call, plus, minus, die, help;
	PsdDecoder pd = new PsdDecoder();
	BufferedImage site, p1, p2, s1, s2;
	Random rand = new Random();
	int nowCard, p1Coin, p2Coin, nowCoin, deal, turn;
	int p1CardNum, p2CardNum, s1CardNum, s2CardNum;
	Font f = new Font("", Font.BOLD, 20);
	Boolean canExit, booking;
	
	

	public Room() {
		setLayout(null);
		try {
			site = pd.read(new File("site1.psd"));
		} catch (Exception e) {
		}
		exit = new JButton("방 나가기");
		canExit = true;
		exit.setSize(200, 40);
		exit.setLocation(1284, 0);
		exit.setFont(f);
		exit.addActionListener(new MyListener());
		add(exit);

		call = new JButton("Call");
		call.setSize(70, 50);
		call.setLocation(570, 600);
		call.setFont(f);
		call.addActionListener(new MyListener());
		add(call);

		plus = new JButton("+");
		plus.setSize(70, 50);
		plus.setLocation(670, 600);
		plus.setFont(f);
		plus.addActionListener(new MyListener());
		add(plus);

		minus = new JButton("-");
		minus.setSize(70, 50);
		minus.setLocation(770, 600);
		minus.setFont(f);
		minus.addActionListener(new MyListener());
		add(minus);

		die = new JButton("Die");
		die.setSize(70, 50);
		die.setLocation(870, 600);
		die.setFont(f);
		die.addActionListener(new MyListener());
		add(die);

//		help = new JButton("?");
//		help.setSize(50, 50);
//		help.setLocation(1433, 910);
//		help.setFont(f);
//		help.addActionListener(new MyListener());
//		add(help);

		reload();
		game();
	}

	public void paint(Graphics g) {
		g.drawImage(site, 150, 200, null);
		g.drawImage(p1, 155, 205, null);
		g.drawImage(p2, 495, 205, null);
		g.drawImage(s1, 770, 205, null);
		g.drawImage(s2, 1110, 205, null);

	}

	public void reload() {
		for (int i = 1; i < 11; i++) {
			for (int j = 0; j < 4; j++) {
				Deck.add(j + 4 * (i - 1), new Card(i));
			}
		}
		nowCard = 40;
	}

	public void game() {
		exit.setText("방 나가기 예약");
		canExit = false;
		booking = false;
		boolean FLAG = true;
		p1Coin = 50;
		p2Coin = 50;
		deal = 1;
		turn = 1;

//		while (FLAG) {
		setting();

		if (p1Coin == 0 || p2Coin == 0)
			FLAG = false;

		else if (nowCard == 0)
			reload();
//		}

	}

	public void setting() {
		p1Coin -= 1;
		p2Coin -= 1;
		nowCoin = 2;

		int p1Num = rand.nextInt(nowCard - 1);
		p1 = Deck.get(p1Num).img;
		p1CardNum = Deck.get(p1Num).cardNumber;
		Deck.remove(p1Num);
		nowCard -= 1;

		int p2Num = rand.nextInt(nowCard - 1);
		p2 = Deck.get(p2Num).img;
		p2CardNum = Deck.get(p2Num).cardNumber;
		Deck.remove(p2Num);
		nowCard -= 1;

		int s1Num = rand.nextInt(nowCard - 1);
		s1 = Deck.get(s1Num).img;
		s1CardNum = Deck.get(s1Num).cardNumber;
		Deck.remove(s1Num);
		nowCard -= 1;

		int s2Num = rand.nextInt(nowCard - 1);
		s2 = Deck.get(s2Num).img;
		s2CardNum = Deck.get(s2Num).cardNumber;
		Deck.remove(s2Num);
		nowCard -= 1;
	}

	class MyListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == exit) {
				if (canExit) {

				} else if (!booking) {
					exit.setText("예약 완료");
					booking = true;
				} else if (booking) {
					exit.setText("방 나가기 예약");
					booking = false;
				}
			}
			if (e.getSource() == call) {
				nowCoin += deal;
				turn += 1;
			}
			if (e.getSource() == plus) {
				if (deal == p1Coin || deal == p2Coin) {
				} else {
					deal += 1;
				}
			}
			if (e.getSource() == minus) {
				if (deal == 1) {
				} else {
					deal -= 1;
				}

			}
			if (e.getSource() == die) {
				if (turn % 2 == 0) {
					p1Coin += nowCoin;
					turn = 1;
				} else if (turn % 2 == 1) {
					p2Coin += nowCoin;
					turn = 2;
				}
			}
//			if (e.getSource() == help) {
//
//			}
		}
	}
}