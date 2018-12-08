import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Indian_Holdem extends JFrame {
	Data d = new Data();
	JButton exit, call, plus, minus, die, help;
	JLabel p1CoinText, p2CoinText, dealCoin, nowCoinText, nowCardText;
	BufferedImage site;
	int p1CardNum, p2CardNum, s1CardNum, s2CardNum;
	Font f = new Font("", Font.BOLD, 20);

	public Indian_Holdem() throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1500, 1000);
		setLayout(null);
		try {
			site = ImageIO.read(new File("site1.png"));
		} catch (Exception e) {
		}
		add(new image());

		exit = new JButton("방 나가기");
		d.setCanEx(true);
		exit.setSize(200, 40);
		exit.setLocation(1284, 0);
		exit.setFont(f);
		exit.addActionListener(new MyListener());
		add(exit);

		d.reload();
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
			g.drawImage(d.p1, 155, 5, null);
			g.drawImage(d.p2, 1110, 5, null);
			g.drawImage(d.s1, 495, 5, null);
			g.drawImage(d.s2, 770, 5, null);
		}
	}

	public void game() throws IOException {
		exit.setText("방 나가기 예약");
		d.setCanEx(false);
		d.setBook(false);
		d.set1Coin(20);
		d.set2Coin(20);
		d.setDeal(1);
		d.setTurn(1);
		setting();
		d.playing();
	}

	public void setting() {
		d.setChange(false);

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

		d.set1Coin(d.p1Coin - 1);
		d.set2Coin(d.p2Coin - 1);
		d.setNowCoin(2);

		nowCardText = new JLabel("남은 장수: " + (d.nowCard - 4));
		nowCardText.setSize(200, 50);
		nowCardText.setLocation(300, 150);
		nowCardText.setFont(f);
		add(nowCardText);

		p1CoinText = new JLabel("현재 코인: " + d.p1Coin);
		p1CoinText.setSize(200, 50);
		p1CoinText.setLocation(200, 550);
		p1CoinText.setFont(f);
		add(p1CoinText);

		p2CoinText = new JLabel("현재 코인: " + d.p2Coin);
		p2CoinText.setSize(200, 50);
		p2CoinText.setLocation(1175, 550);
		p2CoinText.setFont(f);
		add(p2CoinText);

		dealCoin = new JLabel("배팅할 코인: " + d.deal);
		dealCoin.setSize(200, 50);
		dealCoin.setLocation(690, 560);
		dealCoin.setFont(f);
		add(dealCoin);

		nowCoinText = new JLabel("배팅 코인: " + d.nowCoin);
		nowCoinText.setSize(200, 50);
		nowCoinText.setLocation(695, 150);
		nowCoinText.setFont(f);
		add(nowCoinText);
	}

	public void getWinner() throws IOException {
		int winner = 0;
		if ((d.p1CardNum == d.s1CardNum) && (d.s1CardNum == d.s2CardNum)) {
			if ((d.p2CardNum == d.s1CardNum) && (d.s1CardNum == d.s2CardNum)) {
				if (d.p1CardNum < d.p2CardNum)
					winner = 2;
				else if (d.p1CardNum > d.p2CardNum)
					winner = 1;
				else if (d.p1CardNum == d.p2CardNum) {
					d.playing();
					repaint();
				}
			} else {
				winner = 1;
			}
		} else if ((d.p2CardNum == d.s1CardNum) && (d.s1CardNum == d.s2CardNum)) {
			winner = 2;
		} else if (((d.p1CardNum == d.s1CardNum - 1) && (d.s1CardNum == d.s2CardNum - 1))
				|| ((d.p1CardNum == d.s1CardNum + 1) && (d.s1CardNum == d.s2CardNum + 1))
				|| ((d.p1CardNum == d.s1CardNum - 2) && (d.s1CardNum == d.s2CardNum + 1))
				|| ((d.p1CardNum == d.s1CardNum + 1) && (d.s1CardNum == d.s2CardNum - 1))
				|| ((d.p1CardNum == d.s1CardNum + 2) && (d.s1CardNum == d.s2CardNum - 1))
				|| ((d.p1CardNum == d.s1CardNum - 1) && (d.s1CardNum == d.s2CardNum + 1))) {
			if (((d.p2CardNum == d.s1CardNum - 1) && (d.s1CardNum == d.s2CardNum - 1))
					|| ((d.p2CardNum == d.s1CardNum + 1) && (d.s1CardNum == d.s2CardNum + 1))
					|| ((d.p2CardNum == d.s1CardNum - 2) && (d.s1CardNum == d.s2CardNum + 1))
					|| ((d.p2CardNum == d.s1CardNum + 1) && (d.s1CardNum == d.s2CardNum - 1))
					|| ((d.p2CardNum == d.s1CardNum + 2) && (d.s1CardNum == d.s2CardNum - 1))
					|| ((d.p2CardNum == d.s1CardNum - 1) && (d.s1CardNum == d.s2CardNum + 1))) {
				if (d.p1CardNum < d.p2CardNum)
					winner = 2;
				else if (d.p1CardNum > d.p2CardNum)
					winner = 1;
				else if (d.p1CardNum == d.p2CardNum) {
					d.playing();
					repaint();
				}
			} else {
				winner = 1;
			}
		} else if (((d.p2CardNum == d.s1CardNum - 1) && (d.s1CardNum == d.s2CardNum - 1))
				|| ((d.p2CardNum == d.s1CardNum + 1) && (d.s1CardNum == d.s2CardNum + 1))
				|| ((d.p2CardNum == d.s1CardNum - 2) && (d.s1CardNum == d.s2CardNum + 1))
				|| ((d.p2CardNum == d.s1CardNum + 1) && (d.s1CardNum == d.s2CardNum - 1))
				|| ((d.p2CardNum == d.s1CardNum + 2) && (d.s1CardNum == d.s2CardNum - 1))
				|| ((d.p2CardNum == d.s1CardNum - 1) && (d.s1CardNum == d.s2CardNum + 1))) {
			winner = 2;
		} else if ((d.p1CardNum == d.s1CardNum) || (d.p1CardNum == d.s2CardNum)) {
			if ((d.p2CardNum == d.s1CardNum) || (d.p2CardNum == d.s2CardNum)) {
				if (d.p1CardNum < d.p2CardNum)
					winner = 2;
				else if (d.p1CardNum > d.p2CardNum)
					winner = 1;
				else if (d.p1CardNum == d.p2CardNum) {
					d.playing();
					repaint();
				}
			} else {
				winner = 1;
			}
		} else if ((d.p2CardNum == d.s1CardNum) || (d.p2CardNum == d.s2CardNum)) {
			winner = 2;
		} else {
			if (d.p1CardNum < d.p2CardNum)
				winner = 2;
			else if (d.p1CardNum > d.p2CardNum)
				winner = 1;
			else if (d.p1CardNum == d.p2CardNum) {
				d.playing();
				repaint();
			}
		}
		if (winner == 1) {
			d.set1Coin(d.p1Coin + d.nowCoin);
			d.setTurn(1);
			d.set1Coin(d.p1Coin - 1);
			d.set2Coin(d.p2Coin - 1);
			d.setNowCoin(2);
			d.setDeal(1);
			nowCoinText.setText("배팅 코인: " + d.nowCoin);
			dealCoin.setText("배팅할 코인: " + d.deal);
			p1CoinText.setText("현재 코인: " + d.p1Coin);
			p2CoinText.setText("현재 코인: " + d.p2Coin);
			d.playing();
			repaint();
		} else if (winner == 2) {
			d.set2Coin(d.p2Coin + d.nowCoin);
			d.setTurn(2);
			d.set1Coin(d.p1Coin - 1);
			d.set2Coin(d.p2Coin - 1);
			d.setNowCoin(2);
			d.setDeal(1);
			nowCoinText.setText("배팅 코인: " + d.nowCoin);
			dealCoin.setText("배팅할 코인: " + d.deal);
			p1CoinText.setText("현재 코인: " + d.p1Coin);
			p2CoinText.setText("현재 코인: " + d.p2Coin);
			d.playing();
			repaint();
		}

		nowCardText.setText("남은 장수: " + d.nowCard);
	}

	class MyListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == exit) {
				if (d.canExit) {
				} else if (!d.booking) {
					exit.setText("예약 완료");
					d.booking = true;
				} else if (d.booking) {
					exit.setText("방 나가기 예약");
					d.booking = false;
				}
			}
			if (e.getSource() == call) {
				d.setNowCoin(d.nowCoin + d.deal);
				d.setDealedCoin(d.deal - d.beforeDealedCoin);
				d.setBefDealedCoin(d.dealedCoin);
				nowCoinText.setText("배팅 코인: " + d.nowCoin);
				if (d.turn % 2 == 1) {
					d.set1Coin(d.p1Coin - d.deal);
					p1CoinText.setText("현재 코인: " + d.p1Coin);
					if ((d.change) || (d.temp)) {
						dealCoin.setText("배팅할 코인: " + d.dealedCoin);
						d.setChange(false);
						d.setDeal(d.dealedCoin);
						d.setTemp(false);
					} else {
						try {
							getWinner();
						} catch (IOException e1) {
						}
					}
				} else if (d.turn % 2 == 0) {
					d.set2Coin(d.p2Coin - d.deal);
					p2CoinText.setText("현재 코인: " + d.p2Coin);
					if ((d.change) || (d.temp)) {
						dealCoin.setText("배팅할 코인: " + d.dealedCoin);
						d.setChange(false);
						d.setDeal(d.dealedCoin);
						d.setTemp(false);
					} else {
						try {
							getWinner();
						} catch (IOException e1) {
						}
					}
				}
				d.setTurn(d.turn + 1);
			}
			if (e.getSource() == plus) {
				if ((d.deal == d.p1Coin) || (d.deal == d.p2Coin)) {
				} else {
					d.setChange(true);
					d.setDeal(d.deal + 1);
					dealCoin.setText("배팅할 코인: " + d.deal);
				}
			}
			if (e.getSource() == minus) {
				if ((d.deal == d.dealedCoin) || (d.deal == 1)) {
				} else {
					if (d.deal == d.dealedCoin - 1)
						d.setChange(false);
					d.setDeal(d.deal - 1);
					dealCoin.setText("배팅할 코인: " + d.deal);
				}
			}
			if (e.getSource() == die) {
				if (d.turn % 2 == 0) {
					d.set1Coin(d.p1Coin + d.nowCoin);
					d.setTurn(1);
					d.set1Coin(d.p1Coin - 1);
					d.set2Coin(d.p2Coin - 1);
					d.setNowCoin(2);
					p1CoinText.setText("현재 코인: " + d.p1Coin);
					p2CoinText.setText("현재 코인: " + d.p2Coin);
					dealCoin.setText("배팅할 코인: " + d.deal);
					nowCoinText.setText("배팅 코인: " + d.nowCoin);
					try {
						d.playing();
					} catch (IOException e1) {
					}
					repaint();
				} else if (d.turn % 2 == 1) {
					d.set2Coin(d.p2Coin + d.nowCoin);
					d.setTurn(2);
					d.set1Coin(d.p1Coin - 1);
					d.set2Coin(d.p2Coin - 1);
					d.setNowCoin(2);
					p1CoinText.setText("현재 코인: " + d.p1Coin);
					p2CoinText.setText("현재 코인: " + d.p2Coin);
					dealCoin.setText("배팅할 코인: " + d.deal);
					nowCoinText.setText("배팅 코인: " + d.nowCoin);
					try {
						d.playing();
					} catch (IOException e1) {
					}
					repaint();
				}
			}
		}
	}

	public static void main(String args[]) {
		try {
			new Indian_Holdem();
		} catch (Exception e) {
		}
	}
}