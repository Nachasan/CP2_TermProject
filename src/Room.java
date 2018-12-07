import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Room extends JPanel {
	ArrayList<Card> Deck = new ArrayList<>();
	Deck d = new Deck();
	JButton exit, call, plus, minus, die, help;
	JLabel p1CoinText, p2CoinText, dealCoin, nowCoinText;
	BufferedImage site, p1, p2, s1, s2;
	Random rand = new Random();
	int nowCard, p1Coin, p2Coin, nowCoin, deal, turn, dealedCoin, beforeDealedCoin;
	int p1CardNum, p2CardNum, s1CardNum, s2CardNum;
	Font f = new Font("", Font.BOLD, 20);
	Boolean canExit, booking, change, temp;

	public Room() throws IOException {
		setLayout(null);
		try {
			site = ImageIO.read(new File("site1.png"));
		} catch (Exception e) {
		}
		add(new image());

		exit = new JButton("방 나가기");
		canExit = true;
		exit.setSize(200, 40);
		exit.setLocation(1284, 0);
		exit.setFont(f);
		exit.addActionListener(new MyListener());
		add(exit);

		reload();
		game();
		setVisible(true);
	}

	class image extends JPanel {
		public image() {
			setSize(1500, 355);
			setLocation(0, 200);
		}

		public void paint(Graphics g) {
			g.drawImage(site, 150, 0, null);
			g.drawImage(p1, 155, 5, null);
			g.drawImage(p2, 495, 5, null);
			g.drawImage(s1, 770, 5, null);
			g.drawImage(s2, 1110, 5, null);
		}
	}

	public void reload() throws IOException {
		for (int i = 1; i < 11; i++) {
			for (int j = 0; j < 4; j++) {
				Deck.add(j + 4 * (i - 1), new Card(i));
			}
		}
		nowCard = 40;
	}

	public void game() throws IOException {
		exit.setText("방 나가기 예약");
		canExit = false;
		booking = false;
		p1Coin = 50;
		p2Coin = 50;
		deal = 1;
		turn = 1;
		setting();
		playing();

		// if (p1Coin == 0 || p2Coin == 0)
		// FLAG = false;

		// if (nowCard == 0)
		// reload();
	}

	public void setting() {
		change = false;

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

		// help = new JButton("?");
		// help.setSize(50, 50);
		// help.setLocation(1433, 910);
		// help.setFont(f);
		// help.addActionListener(new MyListener());
		// add(help);

		p1Coin -= 1;
		p2Coin -= 1;
		nowCoin = 2;

		p1CoinText = new JLabel("현재 코인: " + p1Coin);
		p1CoinText.setSize(200, 50);
		p1CoinText.setLocation(200, 550);
		p1CoinText.setFont(f);
		add(p1CoinText);

		p2CoinText = new JLabel("현재 코인: " + p2Coin);
		p2CoinText.setSize(200, 50);
		p2CoinText.setLocation(1175, 550);
		p2CoinText.setFont(f);
		add(p2CoinText);

		dealCoin = new JLabel("배팅할 코인: " + deal);
		dealCoin.setSize(200, 50);
		dealCoin.setLocation(690, 560);
		dealCoin.setFont(f);
		add(dealCoin);

		nowCoinText = new JLabel("배팅 코인: " + nowCoin);
		nowCoinText.setSize(200, 50);
		nowCoinText.setLocation(695, 150);
		nowCoinText.setFont(f);
		add(nowCoinText);
	}

	public void playing() {
		beforeDealedCoin = 0;
		temp = true;

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

	public void getWinner() {
		int winner = 0;
		if ((p1CardNum == s1CardNum) && (s1CardNum == s2CardNum)) {
			if ((p2CardNum == s1CardNum) && (s1CardNum == s2CardNum)) {
				if (p1CardNum < p2CardNum)
					winner = 2;
				else if (p1CardNum > p2CardNum)
					winner = 1;
				else if (p1CardNum == p2CardNum) {

				}
			}
		} else if (((p1CardNum == s1CardNum - 1) && (s1CardNum == s2CardNum - 1))
				|| ((p1CardNum == s1CardNum + 1) && (s1CardNum == s2CardNum + 1))
				|| ((p1CardNum == s1CardNum - 2) && (s1CardNum == s2CardNum + 1))
				|| ((p1CardNum == s1CardNum + 1) && (s1CardNum == s2CardNum - 1))
				|| ((p1CardNum == s1CardNum + 2) && (s1CardNum == s2CardNum - 1))
				|| ((p1CardNum == s1CardNum - 1) && (s1CardNum == s2CardNum + 1))) {
			if (((p2CardNum == s1CardNum - 1) && (s1CardNum == s2CardNum - 1))
					|| ((p2CardNum == s1CardNum + 1) && (s1CardNum == s2CardNum + 1))
					|| ((p2CardNum == s1CardNum - 2) && (s1CardNum == s2CardNum + 1))
					|| ((p2CardNum == s1CardNum + 1) && (s1CardNum == s2CardNum - 1))
					|| ((p2CardNum == s1CardNum + 2) && (s1CardNum == s2CardNum - 1))
					|| ((p2CardNum == s1CardNum - 1) && (s1CardNum == s2CardNum + 1))) {
				if (p1CardNum < p2CardNum)
					winner = 2;
				else if (p1CardNum > p2CardNum)
					winner = 1;
				else if (p1CardNum == p2CardNum) {
					playing();
					repaint();
				}
			}
		} else if ((p1CardNum == s1CardNum) || (p1CardNum == s2CardNum)) {
			if ((p2CardNum == s1CardNum) || (p2CardNum == s2CardNum)) {
				if (p1CardNum < p2CardNum)
					winner = 2;
				else if (p1CardNum > p2CardNum)
					winner = 1;
				else if (p1CardNum == p2CardNum) {
					playing();
					repaint();
				}
			}
		} else {
			if (p1CardNum < p2CardNum)
				winner = 2;
			else if (p1CardNum > p2CardNum)
				winner = 1;
			else if (p1CardNum == p2CardNum) {
				playing();
				repaint();
			}
		}
		if (winner == 1) {
			p1Coin += nowCoin;
			turn = 1;
			p1Coin -= 1;
			p2Coin -= 1;
			nowCoin = 2;
			deal = 1;
			nowCoinText.setText("배팅 코인: " + nowCoin);
			dealCoin.setText("배팅할 코인: " + deal);
			p1CoinText.setText("현재 코인: " + p1Coin);
			p2CoinText.setText("현재 코인: " + p2Coin);
			playing();
			repaint();
		} else if (winner == 2) {
			p2Coin += nowCoin;
			turn = 2;
			p1Coin -= 1;
			p2Coin -= 1;
			nowCoin = 2;
			deal = 1;
			nowCoinText.setText("배팅 코인: " + nowCoin);
			dealCoin.setText("배팅할 코인: " + deal);
			p1CoinText.setText("현재 코인: " + p1Coin);
			p2CoinText.setText("현재 코인: " + p2Coin);
			playing();
			repaint();
		}
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
				dealedCoin = deal - beforeDealedCoin;
				beforeDealedCoin = dealedCoin;
				nowCoinText.setText("배팅 코인: " + nowCoin);
				if (turn % 2 == 1) {
					p1Coin -= deal;
					p1CoinText.setText("현재 코인: " + p1Coin);
					if ((change) || (temp)) {
						dealCoin.setText("배팅할 코인: " + dealedCoin);
						change = false;
						deal = dealedCoin;
						temp = false;
					} else {
						getWinner();
					}
				} else if (turn % 2 == 0) {
					p2Coin -= deal;
					p2CoinText.setText("현재 코인: " + p2Coin);
					if ((change) || (temp)) {
						dealCoin.setText("배팅할 코인: " + deal);
						change = false;
						deal = dealedCoin;
						temp = false;
					} else {
						getWinner();
					}
				}
				turn += 1;
			}
			if (e.getSource() == plus) {
				if ((deal == p1Coin) || (deal == p2Coin)) {
				} else {
					change = true;
					deal += 1;
					dealCoin.setText("배팅할 코인: " + deal);
				}
			}
			if (e.getSource() == minus) {
				if (deal == dealedCoin) {
				} else {
					if (deal == dealedCoin - 1)
						change = false;
					deal -= 1;
					dealCoin.setText("배팅할 코인: " + deal);
				}
			}
			if (e.getSource() == die) {
				if (turn % 2 == 0) {
					p1Coin += nowCoin;
					turn = 1;
					p1Coin -= 1;
					p2Coin -= 1;
					nowCoin = 2;
					p1CoinText.setText("현재 코인: " + p1Coin);
					p2CoinText.setText("현재 코인: " + p2Coin);
					dealCoin.setText("배팅할 코인: " + deal);
					nowCoinText.setText("배팅 코인: " + nowCoin);
					playing();
					repaint();
				} else if (turn % 2 == 1) {
					p2Coin += nowCoin;
					turn = 2;
					p1Coin -= 1;
					p2Coin -= 1;
					nowCoin = 2;
					p1CoinText.setText("현재 코인: " + p1Coin);
					p2CoinText.setText("현재 코인: " + p2Coin);
					dealCoin.setText("배팅할 코인: " + deal);
					nowCoinText.setText("배팅 코인: " + nowCoin);
					playing();
					repaint();
				}
			}
		}
	}
}