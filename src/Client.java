
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;

class Site extends JPanel { // ������ ����Ǵ� �⺻ ��
	Card c = new Card();
	private String info = "���� ����";
	private boolean enable = false;
	private boolean running = false;
	private PrintWriter writer;

	private Data d = new Data();
	private JButton call, plus, minus, die;
	private JLabel p1CoinText, p2CoinText, dealCoin, nowCoinText, nowCardText, p1Name, p2Name;
	private int p1CardNum, p2CardNum, s1CardNum, s2CardNum, playerNum;
	private boolean change, temp, draw;
	private Font f = new Font("", Font.BOLD, 20);
	private int turn = -1, nowCard = -1, p1Coin = -1, p2Coin = -1, nowCoin = -1, deal = -1, dealedCoin = -1,
			beforeDealedCoin = -1, winner = 0;
	private BufferedImage site, p1, p2, s1, s2;

	Site() { // ������
		setSize(1400, 1000);
		setLocation(20, 0);
		setLayout(null);
		try {
			site = ImageIO.read(new File("site1.png"));
		} catch (Exception e) {
		}
		add(new image());

		call = new JButton("Call"); // ���ù�ư
		call.setSize(70, 50);
		call.setLocation(510, 600);
		call.setFont(f);
		call.addActionListener(new MyListener());
		add(call);

		plus = new JButton("+"); // ������ ���� ����
		plus.setSize(70, 50);
		plus.setLocation(610, 600);
		plus.setFont(f);
		plus.addActionListener(new MyListener());
		add(plus);

		minus = new JButton("-"); // ������ ���� ����
		minus.setSize(70, 50);
		minus.setLocation(720, 600);
		minus.setFont(f);
		minus.addActionListener(new MyListener());
		add(minus);

		die = new JButton("Die"); // ����
		die.setSize(70, 50);
		die.setLocation(820, 600);
		die.setFont(f);
		die.addActionListener(new MyListener());
		add(die);

		nowCardText = new JLabel("���� ���: "); // ���� ���� ��� ǥ��
		nowCardText.setSize(200, 50);
		nowCardText.setLocation(150, 150);
		nowCardText.setFont(f);
		add(nowCardText);

		p1CoinText = new JLabel("���� ����: "); // ���÷��̾��� ���� ���� ǥ��
		p1CoinText.setSize(200, 50);
		p1CoinText.setLocation(170, 550);
		p1CoinText.setFont(f);
		add(p1CoinText);

		p2CoinText = new JLabel("���� ����: "); // ���÷��̾��� ���� ���� ǥ��
		p2CoinText.setSize(200, 50);
		p2CoinText.setLocation(1135, 550);
		p2CoinText.setFont(f);
		add(p2CoinText);

		dealCoin = new JLabel("������ ����: "); // ������ ���� ���� ǥ��
		dealCoin.setSize(200, 50);
		dealCoin.setLocation(650, 560);
		dealCoin.setFont(f);
		add(dealCoin);

		nowCoinText = new JLabel("���� ����: "); // ���� ���õ� ���� ǥ��
		nowCoinText.setSize(200, 50);
		nowCoinText.setLocation(655, 150);
		nowCoinText.setFont(f);
		add(nowCoinText);
	}

	public void refresh() { // �󺧵��� �ؽ�Ʈ�� �����Ѵ�.
		nowCardText.setText("���� ���: " + nowCard);
		p1CoinText.setText("���� ����: " + p1Coin);
		p2CoinText.setText("���� ����: " + p2Coin);
		dealCoin.setText("������ ����: " + deal);
		nowCoinText.setText("���� ����: " + nowCoin);
	}

	public boolean isRunning() { // ������ ���� ���¸� ��ȯ�Ѵ�.
		return running;
	}

	public void startGame() { // ������ �����Ѵ�.
		System.out.println(turn);
		playerNum = turn;
		System.out.println(playerNum);
		running = true;
		p1Coin = 19;
		p2Coin = 19;
		nowCoin = 2;
		deal = 1;
		nowCard = 40;
		beforeDealedCoin = 0;
		change = false;
		temp = true;
		try {
			setImage();
		} catch (IOException e) {
		}
		repaint();
	}

	public void nextGame() { // �� ���� ���� �� ���� ������ �����Ѵ�.
		p1Coin -= 1;
		p2Coin -= 1;
		nowCoin = 2;
		deal = 1;
		beforeDealedCoin = 0;
		change = false;
		temp = true;
		turn = winner;
		writer.println("[SEND]" + p1Coin + "#" + p2Coin + "#" + nowCoin + "#" + deal + "#" + dealedCoin + "#"
				+ beforeDealedCoin + "#" + change + "#" + temp + "#" + turn + "#" + enable);
		refresh();
		repaint();
	}

	public void stopGame() { // ������ �����.
		reset();
		writer.println("[STOPGAME]");
		enable = false;
		running = false;
	}

	public void setEnable(boolean enable) { // enable�� �����Ѵ�.
		this.enable = enable;
	}

	public void setWriter(PrintWriter writer) { // ������ �޼����� ������ writer�� �����Ѵ�.
		this.writer = writer;
	}

	public void setCardNum(int p1cn, int p2cn, int s1cn, int s2cn) throws IOException {
		// ī���� ���ڸ� �����Ѵ�.
		p1CardNum = p1cn;
		p2CardNum = p2cn;
		s1CardNum = s1cn;
		s2CardNum = s2cn;
	}

	public void setImage() throws IOException { // ī���� �̹����� �����Ѵ�. �� �÷��̾�� ���� �����߿� �ڽ��� ī�带 �� �� ����.
		if (playerNum == 1) // ���÷��̾��� ī�带 ������.
			p1 = c.setImage(100);
		else
			p1 = c.setImage(p1CardNum);
		if (playerNum == 2) // �ĕ����̾��� ī�带 ������.
			p2 = c.setImage(100);
		else
			p2 = c.setImage(p2CardNum);
		s1 = c.setImage(s1CardNum);
		s2 = c.setImage(s2CardNum);
	}

	public void viewAll() throws IOException { // ������ �Ϸ�ǰ� ���� ����Ǳ� ���� �ڽ��� ī�带 �����ش�.
		p1 = c.setImage(p1CardNum);
		p2 = c.setImage(p2CardNum);
		s1 = c.setImage(s1CardNum);
		s2 = c.setImage(s2CardNum);
	}

	public void setNowCoin(int nowCoin) { // nowCoin�� �����Ѵ�.
		this.nowCoin = nowCoin;
	}

	public void setp1Coin(int p1Coin) { // p1Coin�� �����Ѵ�.
		this.p1Coin = p1Coin;
	}

	public void setp2Coin(int p2Coin) { // p2Coin�� �����Ѵ�.
		this.p2Coin = p2Coin;
	}

	public void setTurn(int turn) { // turn�� �����Ѵ�.
		this.turn = turn;
	}

	public void setDeal(int deal) { // deal(���� �� ����)�� �����Ѵ�.
		this.deal = deal;
	}

	public void setDealedCoin(int dealedCoin) { // dealedCoin(���� �÷��̾ �߰������� ����)�� ��ȯ�Ѵ�.
		this.dealedCoin = dealedCoin;
	}

	public void setBefDealedCoin(int beforeDealedCoin) { // beforeDealedCoin(�� �Ͽ� �ڽ��� �߰������� ����)�� ��ȯ�Ѵ�.	
													     // dealedCoin��꿡 ���ȴ�.
		this.beforeDealedCoin = beforeDealedCoin;
	}

	public void setChange(String change) { // change(�߰����� ����)�� �����Ѵ�.
		this.change = Boolean.getBoolean(change);
	}

	public void setTemp(boolean temp) { // temp(���÷��̾ 1�� ���ý� ������ ������ ���� ����)�� �����Ѵ�.
		this.temp = temp;
	}

	public void setPlayerNum(int playerNum) { // playerNum(�÷��̾��� ���Ŀ���)�� �����Ѵ�.
		this.playerNum = playerNum;
	}

	public void setDraw(boolean draw) { // regame(������ �Ϸ�� �� ����� ������ ������ �� ������ �����ִ� ����)�� �����Ѵ�.
		this.draw = draw;
	}

	public void setWinner(int winner) { // winner(���� ����)�� �����Ѵ�.
		this.winner = winner;
		if (winner == 1) {
			p1Coin += nowCoin;
			if (playerNum == 1)
				enable = true;
			else
				enable = false;
		} else if (winner == 2) {
			p2Coin += nowCoin;
			if (playerNum == 2)
				enable = true;
			else
				enable = false;
		}
	}

	public void endGame() { // p1Coin�̳� p2Coin�� 0���� �Ǿ��� �� ������ ����ȴ�.
		if (p1Coin <= 0) {
			if (playerNum == 1) {
				running = false;
			} else if (playerNum == 2) {
				writer.println("[WIN]");
				running = false;
			}
		} else if (p2Coin <= 0) {
			if (playerNum == 2) {
				running = false;
			} else if (playerNum == 1) {
				writer.println("[WIN]");
				running = false;
			}
		}
	}

	class MyListener implements ActionListener { // �׼� ������

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!enable) { // enable�� false�� ��ư�� �������� �ʴ´�.
				return;
			} else if (e.getSource() == call) { // call�� ������ ��
				nowCoin += deal;
				dealedCoin = deal - beforeDealedCoin;
				beforeDealedCoin = dealedCoin;
				if (turn % 2 == 1) {
					p1Coin -= deal;
					if ((change) || (temp)) {
						change = false;
						deal = dealedCoin;
						temp = false;
						turn += 1;
						writer.println("[CALL]" + p1Coin + "#" + p2Coin + "#" + nowCoin + "#" + deal + "#" + dealedCoin
								+ "#" + beforeDealedCoin + "#" + change + "#" + temp + "#" + turn);
						enable = false;
					} else {
						try {
							viewAll();
						} catch (IOException e1) {
						}
						repaint();
						writer.println("[CHECK]");
						try {
							viewAll();
						} catch (IOException e1) {
						}
						try {
							Thread.sleep(500);
						} catch (Exception ee) {
						}
						if (draw) {
							p1Coin += (nowCoin / 2);
							p2Coin += (nowCoin / 2);
							draw = false;
						}
						writer.println("[NEXT]");
						try {
							Thread.sleep(500);
						} catch (Exception ee) {
						}
						temp = true;
						nextGame();
					}
				} else if (turn % 2 == 0) {
					p2Coin -= deal;
					if ((change) || (temp)) {
						change = false;
						deal = dealedCoin;
						temp = false;
						turn += 1;
						writer.println("[CALL]" + p1Coin + "#" + p2Coin + "#" + nowCoin + "#" + deal + "#" + dealedCoin
								+ "#" + beforeDealedCoin + "#" + change + "#" + temp + "#" + turn);
						enable = false;
					} else {
						writer.println("[CHECK]");
						try {
							viewAll();
						} catch (IOException e1) {
						}
						try {
							Thread.sleep(500);
						} catch (Exception ee) {
						}
						if (draw) {
							p1Coin += (nowCoin / 2);
							p2Coin += (nowCoin / 2);
							draw = false;
						}
						writer.println("[NEXT]");
						try {
							Thread.sleep(500);
						} catch (Exception ee) {
						}
						temp = true;
						if (running)
							nextGame();
					}
				}
				refresh();
			} else if (e.getSource() == plus) { // ������ ������ �߰��Ѵ�.
				if ((deal == p1Coin) || (deal == p2Coin)) { // p1Coin�̳� p2Coin���� �� ���� ������ �����Ϸ� �� ��
					info = "�������ִ� ���κ��� �� ���� ������ ���� �� �� �����ϴ�.";
				} else {
					change = true;
					deal += 1;
					dealCoin.setText("������ ����: " + deal);
				}
				refresh();
			} else if (e.getSource() == minus) { // ������ ������ ����.
				if (deal == dealedCoin) { // �߰������� ���κ��� ���� ������ �����Ϸ� �� ��
					info = "�߰������� ���κ��� ���� ������ ������ �� �����ϴ�.";
				} else if (deal == 1) { // 1������ ���� ������ �����Ϸ� �� ��
					info = "1�� �̻��� �����ؾ��մϴ�.";
				} else {
					if (deal == dealedCoin - 1)
						change = false;
					deal -= 1;
					dealCoin.setText("������ ����: " + deal);
				}
				refresh();
			} else if (e.getSource() == die) { // ���� ������ ��
				if (turn % 2 == 0) {
					p1Coin += nowCoin;
					turn = 1;
					enable = false;
					writer.println("[NEXT]");
					try {
						Thread.sleep(500);
					} catch (Exception ee) {
					}
					temp = true;
					nextGame();

				} else if (turn % 2 == 1) {
					p2Coin += nowCoin;
					turn = 2;
					enable = false;
					writer.println("[NEXT]");
					try {
						Thread.sleep(500);
					} catch (Exception ee) {
					}
					temp = true;
					endGame();
					nextGame();

				}
				refresh();
			}
		}
	}

	class image extends JPanel { // ī�带 ����Ѵ�.
		public image() {
			setSize(1500, 355);
			setLocation(0, 200);
		}

		synchronized public void paint(Graphics g) {
			g.drawImage(site, 100, 0, null);
			g.drawImage(p1, 105, 5, null);
			g.drawImage(p2, 1060, 5, null);
			g.drawImage(s1, 445, 5, null);
			g.drawImage(s2, 720, 5, null);
		}
	}

	public void reset() { // ������ �����Ѵ�.
		p1 = null;
		p1CardNum = -1;

		p2 = null;
		p2CardNum = -1;

		s1 = null;
		s1CardNum = -1;

		s2 = null;
		s2CardNum = -1;

		repaint();

		nowCardText.setText("���� ���: ");
		p1CoinText.setText("���� ����: ");
		p2CoinText.setText("���� ����: ");
		dealCoin.setText("������ ����: ");
		nowCoinText.setText("���� ����: ");
	}
}

public class Client extends JFrame implements Runnable, ActionListener { // Ŭ���̾�Ʈ
	private LoginGUI login;
	private JTextArea msgView = new JTextArea("", 1, 1); // ä��â �α׺κ�
	private JTextField sendBox = new JTextField(""); // ä��â �Էºκ�
	private JTextField nameBox = new JTextField(); // �ڽ��� �̸� ����
	private JTextField roomBox = new JTextField("0"); // ���� ��ȣ ����

	private JLabel pInfo = new JLabel("����:  ��"); // ���ǰ� ���� �ο��� ���
	private DefaultListModel<String> players = new DefaultListModel<>(); // �濡 �ִ� �÷��̾��� �̸��� ����Ʈȭ�ϴµ� ������ ��
	private JList<String> pList = new JList<>(players); // ���ǰ� �濡 �ִ� �÷��̾��� �̸� ���
	private JButton startButton = new JButton("���� ����");
	private JButton enterButton = new JButton("�����ϱ�");
	private JButton exitButton = new JButton("���Ƿ�");

	private JLabel infoView = new JLabel("< Indian Hold'em >"); // ���¸޼��� ���
	private Site site = new Site();
	private BufferedReader reader; // �Է� ��Ʈ��
	private PrintWriter writer; // ��� ��Ʈ��
	private Socket socket; // ����
	private int roomNumber = -1; // �� ��ȣ
	private String userName = null; // ����� �̸�

	public Client(String title) { // ������
		super(title);
		setLayout(null); // ���̾ƿ��� ������� �ʴ´�.

		setVisible(true);
		login = new LoginGUI(); // �α���

		msgView.setEditable(false);
		infoView.setBounds(10, 30, 480, 30);
		infoView.setBackground(new Color(200, 200, 255));
		site.setLocation(10, 70);

		Panel p1 = new Panel();
		p1.setLayout(new GridLayout(3, 2));
		p1.add(new Label("�� ��ȣ:", 2));
		p1.add(roomBox);
		p1.add(enterButton);
		p1.add(exitButton);
		enterButton.setEnabled(false);
		p1.setLocation(1420, 10);
		p1.setSize(300, 70);

		Panel p2 = new Panel();
		p2.setLayout(new BorderLayout());
		p2.add(pInfo, BorderLayout.NORTH);
		p2.add(pList, BorderLayout.CENTER);
		p2.add(startButton, BorderLayout.SOUTH);
		startButton.setEnabled(false);
		p2.setLocation(1420, 100);
		p2.setSize(300, 370);

		Panel p3 = new Panel();
		p3.setLayout(new BorderLayout());
		p3.add(msgView, "Center");
		p3.add(sendBox, "South");
		p3.setLocation(1420, 490);
		p3.setSize(300, 460);

		add(infoView);
		add(site);
		add(p1);
		add(p2);
		add(p3);

		sendBox.addActionListener(this);
		enterButton.addActionListener(this);
		exitButton.addActionListener(this);
		startButton.addActionListener(this);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == sendBox) { // �޽��� �Է� �����̸�
			String msg = sendBox.getText();
			if (msg.length() == 0)
				return;
			if (msg.length() >= 30)
				msg = msg.substring(0, 30);
			try {
				writer.println("[MSG]" + msg);
				sendBox.setText("");
			} catch (Exception ie) {
			}
		}

		else if (ae.getSource() == enterButton) { // �����ϱ� ��ư�̸�
			try {
				if (Integer.parseInt(roomBox.getText()) < 1) {
					infoView.setText("���ȣ�� �߸��Ǿ����ϴ�. 1�̻�");
					return;
				}
				writer.println("[ROOM]" + Integer.parseInt(roomBox.getText()));
				msgView.setText("");
			} catch (Exception ie) {
				infoView.setText("�Է��Ͻ� ���׿� ������ �ҽ��ϴ�.");
			}
		}

		else if (ae.getSource() == exitButton) { // ���Ƿ� ��ư�̸�
			try {
				goToWaitRoom();
				startButton.setEnabled(false);
			} catch (Exception e) {
			}
		}

		else if (ae.getSource() == startButton) { // �뱹 ���� ��ư�̸�
			try {
				writer.println("[START]");
				infoView.setText("����� ������ ��ٸ��ϴ�.");
				startButton.setEnabled(false);
			} catch (Exception e) {
			}
		}
	}

	void goToWaitRoom() { // ���Ƿ� ��ư�� ������ ȣ��ȴ�.
		if (userName == null) {
			String name = login.getID();
			userName = name;
			writer.println("[NAME]" + userName);
		}
		msgView.setText("");
		writer.println("[ROOM]0");
		infoView.setText("���ǿ� �����ϼ̽��ϴ�.");
		roomBox.setText("0");
		enterButton.setEnabled(true);
	}

	public void run() {
		String msg; // �����κ����� �޽���
		try {
			while ((msg = reader.readLine()) != null) {
				if (msg.startsWith("[ROOM]")) { // �濡 ����
					if (!msg.equals("[ROOM]0")) { // ������ �ƴ� ���̸�
						enterButton.setEnabled(false);
						infoView.setText(msg.substring(6) + "�� �濡 �����ϼ̽��ϴ�.");
					} else
						infoView.setText("���ǿ� �����ϼ̽��ϴ�.");
					roomNumber = Integer.parseInt(msg.substring(6)); // �� ��ȣ ����
					if (site.isRunning()) { // ������ �������� �����̸�
						site.stopGame(); // ������ ������Ų��.
					}
				}

				else if (msg.startsWith("[FULL]")) { // ���� �� �����̸�
					infoView.setText("���� ���� ������ �� �����ϴ�.");
				}

				else if (msg.startsWith("[PLAYERS]")) { // �濡 �ִ� ����� ���
					nameList(msg.substring(9));
				}

				else if (msg.startsWith("[ENTER]")) { // �մ� ����
					players.addElement(msg.substring(7));
					playersInfo();
					msgView.append("[" + msg.substring(7) + "]���� �����Ͽ����ϴ�.\n");
				}

				else if (msg.startsWith("[EXIT]")) { // �մ� ����
					players.removeElement(msg.substring(6)); // ����Ʈ���� ����
					playersInfo(); // �ο����� �ٽ� ����Ͽ� �����ش�.
					msgView.append("[" + msg.substring(6) + "]���� �ٸ� ������ �����Ͽ����ϴ�.\n");
					if (roomNumber != 0)
						endGame("��밡 �������ϴ�.");
				}

				else if (msg.startsWith("[DISCONNECT]")) { // �մ� ���� ����
					players.removeElement(msg.substring(12));
					playersInfo();
					msgView.append("[" + msg.substring(12) + "]���� ������ �������ϴ�.\n");
					if (roomNumber != 0)
						endGame("��밡 �������ϴ�.");
				}

				else if (msg.startsWith("[FIRST]")) {
					String first = msg.substring(7);
					site.startGame(); // ������ �����Ѵ�.
					if (first.equals("FIRST")) {
						site.setTurn(1);
						infoView.setText("���÷��̾��Դϴ�.");
						site.setEnable(true);
					} else {
						site.setTurn(2);
						infoView.setText("���÷��̾��Դϴ�.");
						site.setEnable(false);
					}
					site.refresh();
				}

				else if (msg.startsWith("[PLAY]")) {
					String temp = msg.substring(6);
					int p1cn = Integer.parseInt(temp.substring(0, 1));
					int p2cn = Integer.parseInt(temp.substring(2, 3));
					int s1cn = Integer.parseInt(temp.substring(4, 5));
					int s2cn = Integer.parseInt(temp.substring(6, 7));
					site.setCardNum(p1cn, p2cn, s1cn, s2cn);
					site.setImage();
				}

				else if (msg.startsWith("[CALL]")) {
					String temp = msg.substring(6);
					String[] data = temp.split("#");
					site.setp1Coin(Integer.parseInt(data[0]));
					site.setp2Coin(Integer.parseInt(data[1]));
					site.setNowCoin(Integer.parseInt(data[2]));
					site.setDeal(Integer.parseInt(data[3]));
					site.setDealedCoin(Integer.parseInt(data[4]));
					site.setBefDealedCoin(Integer.parseInt(data[5]));
					site.setChange(data[6]);
					site.setTemp(Boolean.getBoolean(data[7]));
					site.setTurn(Integer.parseInt(data[8]));
					site.setEnable(true);
					site.refresh();
				}

				else if (msg.startsWith("[SEND]")) {
					String temp = msg.substring(6);
					String[] data = temp.split("#");
					boolean check;
					site.setp1Coin(Integer.parseInt(data[0]));
					site.setp2Coin(Integer.parseInt(data[1]));
					site.setNowCoin(Integer.parseInt(data[2]));
					site.setDeal(Integer.parseInt(data[3]));
					site.setDealedCoin(Integer.parseInt(data[4]));
					site.setBefDealedCoin(Integer.parseInt(data[5]));
					site.setChange(data[6]);
					site.setTemp(true);
					site.setTurn(Integer.parseInt(data[8]));
					check = Boolean.getBoolean(data[9]);
					site.setEnable(!check);
					site.refresh();
					site.repaint();
				}

				else if (msg.startsWith("[DRAW]"))
					site.setDraw(true);

				else if (msg.startsWith("[WINNER]"))
					site.setWinner(Integer.parseInt(msg.substring(8)));

				else if (msg.startsWith("[WIN]")) // �̰�����
					endGame("�̰���ϴ�.");

				else if (msg.startsWith("[LOSE]")) // ������
					endGame("�����ϴ�.");

				else
					msgView.append(msg + "\n");
			}
		} catch (IOException ie) {
			msgView.append(ie + "\n");
		}
		msgView.append("������ ������ϴ�.");
	}

	private void endGame(String msg) { // ������ �����Ű�� �޼ҵ�
		infoView.setText(msg);
		startButton.setEnabled(false);

		try {
			Thread.sleep(2000);
		} catch (Exception e) {
		} // 2�ʰ� ���

		if (site.isRunning())
			site.stopGame();

		if (players.size() == 2)
			startButton.setEnabled(true);
	}

	private void playersInfo() { // �濡 �ִ� �������� ���� �����ش�.
		int count = players.size();
		if (roomNumber == 0)
			pInfo.setText("����: " + count + "��");
		else
			pInfo.setText(roomNumber + " �� ��: " + count + "��");

		if (count == 2 && roomNumber != 0)
			startButton.setEnabled(true);
		else
			startButton.setEnabled(false);
	}

	private void nameList(String msg) {
		players.removeAllElements();
		StringTokenizer st = new StringTokenizer(msg, "\t");
		while (st.hasMoreElements())
			players.addElement(st.nextToken());
		playersInfo();
	}

	private void connect() { // ����
		try {
			msgView.append("������ ������ ��û�մϴ�.\n");
			socket = new Socket("127.0.0.1", 7777);
			msgView.append("---���� ����--.\n");
			msgView.append("�̸��� �Է��ϰ� ���Ƿ� �����ϼ���.\n");

			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			writer = new PrintWriter(socket.getOutputStream(), true);

			new Thread(this).start();
			site.setWriter(writer);
		} catch (Exception e) {
			msgView.append(e + "\n\n���� ����..\n");
		}
	}

	public static void main(String[] args) {
		Client client = new Client("Indian Hold'em");
		client.setSize(1750, 1000);
		client.connect();
	}
}