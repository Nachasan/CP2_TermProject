
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;

class OmokBoard extends JPanel { // �������� �����ϴ� Ŭ����
	Card c = new Card();
	private String info = "���� ����";
	private boolean enable = false;
	private boolean running = false;
	private PrintWriter writer;

	private Data d = new Data();
	private JButton call, plus, minus, die;
	private JLabel p1CoinText, p2CoinText, dealCoin, nowCoinText, nowCardText, p1Name, p2Name;
	private int p1CardNum, p2CardNum, s1CardNum, s2CardNum, playerNum;
	private boolean change, temp;
	private Font f = new Font("", Font.BOLD, 20);
	private int turn = -1, nowCard = -1, p1Coin = -1, p2Coin = -1, nowCoin = -1, deal = -1, dealedCoin = -1,
			beforeDealedCoin = -1, winner = 0;
	private BufferedImage site, p1, p2, s1, s2;

	OmokBoard() {
		setSize(1400, 1000);
		setLocation(20, 0);
		setLayout(null);
		try {
			site = ImageIO.read(new File("site1.png"));
		} catch (Exception e) {
		}
		add(new image());

		call = new JButton("Call");
		call.setSize(70, 50);
		call.setLocation(510, 600);
		call.setFont(f);
		call.addActionListener(new MyListener());
		add(call);

		plus = new JButton("+");
		plus.setSize(70, 50);
		plus.setLocation(610, 600);
		plus.setFont(f);
		plus.addActionListener(new MyListener());
		add(plus);

		minus = new JButton("-");
		minus.setSize(70, 50);
		minus.setLocation(720, 600);
		minus.setFont(f);
		minus.addActionListener(new MyListener());
		add(minus);

		die = new JButton("Die");
		die.setSize(70, 50);
		die.setLocation(820, 600);
		die.setFont(f);
		die.addActionListener(new MyListener());
		add(die);

		nowCardText = new JLabel("���� ���: ");
		nowCardText.setSize(200, 50);
		nowCardText.setLocation(150, 150);
		nowCardText.setFont(f);
		add(nowCardText);

		p1CoinText = new JLabel("���� ����: ");
		p1CoinText.setSize(200, 50);
		p1CoinText.setLocation(170, 550);
		p1CoinText.setFont(f);
		add(p1CoinText);

		p2CoinText = new JLabel("���� ����: ");
		p2CoinText.setSize(200, 50);
		p2CoinText.setLocation(1135, 550);
		p2CoinText.setFont(f);
		add(p2CoinText);

		dealCoin = new JLabel("������ ����: ");
		dealCoin.setSize(200, 50);
		dealCoin.setLocation(650, 560);
		dealCoin.setFont(f);
		add(dealCoin);

		nowCoinText = new JLabel("���� ����: ");
		nowCoinText.setSize(200, 50);
		nowCoinText.setLocation(655, 150);
		nowCoinText.setFont(f);
		add(nowCoinText);
	}

	public void refresh() {
		nowCardText.setText("���� ���: " + (nowCard));
		p1CoinText.setText("���� ����: " + p1Coin);
		p2CoinText.setText("���� ����: " + p2Coin);
		dealCoin.setText("������ ����: " + deal);
		nowCoinText.setText("���� ����: " + nowCoin);
	}

	public boolean isRunning() { // ������ ���� ���¸� ��ȯ�Ѵ�.
		return running;
	}

	public void startGame() { // ������ �����Ѵ�.
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

	public void nextGame() {
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
		System.out.println("[SEND]" + p1Coin + "#" + p2Coin + "#" + nowCoin + "#" + deal + "#" + dealedCoin + "#"
				+ beforeDealedCoin + "#" + change + "#" + temp + "#" + turn + "#" + enable);
		refresh();
		repaint();
	}

	public void stopGame() { // ������ �����.
		reset(); // �������� �ʱ�ȭ�Ѵ�.
		writer.println("[STOPGAME]"); // ������� �޽����� ������.
		enable = false;
		running = false;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public void setWriter(PrintWriter writer) {
		this.writer = writer;
	}

	public void setCardNum(int p1cn, int p2cn, int s1cn, int s2cn) throws IOException {
		p1CardNum = p1cn;
		p2CardNum = p2cn;
		s1CardNum = s1cn;
		s2CardNum = s2cn;
		setImage();
	}

	public void setImage() throws IOException {
		p1 = c.setImage(p1CardNum);
		p2 = c.setImage(p2CardNum);
		s1 = c.setImage(s1CardNum);
		s2 = c.setImage(s2CardNum);
	}

	public void setNowCoin(int nc) {
		nowCoin = nc;
	}

	public void setp1Coin(int c1) {
		p1Coin = c1;
	}

	public void setp2Coin(int c2) {
		p2Coin = c2;
	}

	public void setTurn(int t) {
		turn = t;
	}

	public void setDeal(int d) {
		deal = d;
	}

	public void setDealedCoin(int dc) {
		dealedCoin = dc;
	}

	public void setBefDealedCoin(int bdc) {
		beforeDealedCoin = bdc;
	}

	public void setChange(String c) {
		change = Boolean.getBoolean(c);
	}

	public void setTemp(boolean t) {
		temp = t;
	}
	
	public void setPlayerNum(int i) {
		playerNum = i;
	}

	public void setWinner(int winner) {
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

	class MyListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!enable) {
				return;
			} else if (e.getSource() == call) {
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
						writer.println("[CALL]" + p1Coin + "#" + p2Coin + "#" + nowCoin + "#" + deal + "#" + dealedCoin + "#"
								+ beforeDealedCoin + "#" + change + "#" + temp + "#" + turn);
						enable = false;
					} else {
						writer.println("[CHECK]");
						try {
							Thread.sleep(1000);
						} catch (Exception ee) {
						}
						writer.println("[NEXT]");
						try {
							Thread.sleep(1000);
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
						writer.println("[CALL]" + p1Coin + "#" + p2Coin + "#" + nowCoin + "#" + deal + "#" + dealedCoin + "#"
								+ beforeDealedCoin + "#" + change + "#" + temp + "#" + turn);
						enable = false;
					} else {
						writer.println("[CHECK]");
						try {
							Thread.sleep(1000);
						} catch (Exception ee) {
						}
						writer.println("[NEXT]");
						try {
							Thread.sleep(1000);
						} catch (Exception ee) {
						}
						temp = true;
						nextGame();
					}
				}
				refresh();
			} else if (e.getSource() == plus) {
				if ((deal == p1Coin) || (deal == p2Coin)) {
					info = "�������ִ� ���κ��� �� ���� ������ ���� �� �� �����ϴ�.";
				} else {
					change = true;
					deal += 1;
					dealCoin.setText("������ ����: " + deal);
				}
				refresh();
			} else if (e.getSource() == minus) {
				if (deal == dealedCoin) {
					info = "�߰������� ���κ��� ���� ������ ������ �� �����ϴ�.";
				} else if (deal == 1) {
					info = "1�� �̻��� �����ؾ��մϴ�.";
				} else {
					if (deal == dealedCoin - 1)
						change = false;
					deal -= 1;
					dealCoin.setText("������ ����: " + deal);
				}
				refresh();
			} else if (e.getSource() == die) {
				if (turn % 2 == 0) {
					p1Coin += nowCoin;
					turn = 1;
					// gameEnd();
					// try {
					// set();
					// } catch (IOException e1) {
					// e1.printStackTrace();
					// }
				} else if (d.turn % 2 == 1) {
					p2Coin += nowCoin;
					turn = 2;
					// gameEnd();
					// try {
					// set();
					// } catch (IOException e1) {
					// e1.printStackTrace();
					// }
				}
				refresh();
			}
		}
	}

	class image extends JPanel {
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

	public void reset() {
		p1 = null;
		p1CardNum = -1;

		p2 = null;
		p2CardNum = -1;

		s1 = null;
		s1CardNum = -1;

		s2 = null;
		s2CardNum = -1;

		repaint();
	}
}

public class OmokClient extends JFrame implements Runnable, ActionListener {
	private JTextArea msgView = new JTextArea("", 1, 1); // �޽����� �����ִ� ����
	private JTextField sendBox = new JTextField(""); // ���� �޽����� ���� ����
	private JTextField nameBox = new JTextField(); // ����� �̸� ����
	private JTextField roomBox = new JTextField("0"); // �� ��ȣ ����

	private JLabel pInfo = new JLabel("����:  ��");
	private DefaultListModel<String> players = new DefaultListModel<>();
	private JList<String> pList = new JList<>(players);// ����� ����� �����ִ� ����Ʈ
	private JButton startButton = new JButton("���� ����"); // �뱹 ���� ��
	private JButton enterButton = new JButton("�����ϱ�"); // �����ϱ� ��ư
	private JButton exitButton = new JButton("���Ƿ�"); // ���Ƿ� ��ư

	private JLabel infoView = new JLabel("< �����ϴ� �ڹ� >");
	private OmokBoard board = new OmokBoard(); // ������ ��ü
	private BufferedReader reader; // �Է� ��Ʈ��
	private PrintWriter writer; // ��� ��Ʈ��
	private Socket socket; // ����
	private int roomNumber = -1; // �� ��ȣ
	private String userName = null; // ����� �̸�

	public OmokClient(String title) { // ������
		super(title);
		setLayout(null); // ���̾ƿ��� ������� �ʴ´�.

		msgView.setEditable(false);
		infoView.setBounds(10, 30, 480, 30);
		infoView.setBackground(new Color(200, 200, 255));
		board.setLocation(10, 70);

		Panel p1 = new Panel();
		p1.setLayout(new GridLayout(3, 3));
		p1.add(new Label("��     ��:", 2));
		p1.add(nameBox);
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
		add(board);
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
			String name = nameBox.getText().trim();
			if (name.length() <= 2 || name.length() > 10) {
				infoView.setText("�̸��� �߸��Ǿ����ϴ�. 3~10��");
				nameBox.requestFocus();
				return;
			}
			userName = name;
			writer.println("[NAME]" + userName);
			nameBox.setText(userName);
			nameBox.setEditable(false);
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
					if (board.isRunning()) { // ������ �������� �����̸�
						board.stopGame(); // ������ ������Ų��.
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
					board.startGame(); // ������ �����Ѵ�.
					if (first.equals("FIRST")) {
						infoView.setText("���÷��̾��Դϴ�.");
						board.setEnable(true);
						board.setPlayerNum(1);
						board.setTurn(1);
					} else {
						infoView.setText("���÷��̾��Դϴ�.");
						board.setEnable(false);
						board.setPlayerNum(2);
						board.setTurn(2);
					}
					board.refresh();
				}

				else if (msg.startsWith("[PLAY]")) {
					String temp = msg.substring(6);
					int p1cn = Integer.parseInt(temp.substring(0, 1));
					int p2cn = Integer.parseInt(temp.substring(2, 3));
					int s1cn = Integer.parseInt(temp.substring(4, 5));
					int s2cn = Integer.parseInt(temp.substring(6, 7));
					board.setCardNum(p1cn, p2cn, s1cn, s2cn);
				}

				else if (msg.startsWith("[CALL]")) {
					String temp = msg.substring(6);
					String[] data = temp.split("#");
					board.setp1Coin(Integer.parseInt(data[0]));
					board.setp2Coin(Integer.parseInt(data[1]));
					board.setNowCoin(Integer.parseInt(data[2]));
					board.setDeal(Integer.parseInt(data[3]));
					board.setDealedCoin(Integer.parseInt(data[4]));
					board.setBefDealedCoin(Integer.parseInt(data[5]));
					board.setChange(data[6]);
					board.setTemp(Boolean.getBoolean(data[7]));
					board.setTurn(Integer.parseInt(data[8]));
					board.setEnable(true);
					board.refresh();
				}

				else if (msg.startsWith("[SEND]")) {
					String temp = msg.substring(6);
					String[] data = temp.split("#");
					System.out.println(temp);
					boolean check;
					board.setp1Coin(Integer.parseInt(data[0]));
					board.setp2Coin(Integer.parseInt(data[1]));
					board.setNowCoin(Integer.parseInt(data[2]));
					board.setDeal(Integer.parseInt(data[3]));
					board.setDealedCoin(Integer.parseInt(data[4]));
					board.setBefDealedCoin(Integer.parseInt(data[5]));
					board.setChange(data[6]);
					board.setTemp(true);
					board.setTurn(Integer.parseInt(data[8]));
					check = Boolean.getBoolean(data[9]);
					board.setEnable(!check);
					board.refresh();
					board.repaint();
				}

				else if (msg.startsWith("[WINNER]"))
					board.setWinner(Integer.parseInt(msg.substring(8)));

				else if (msg.startsWith("[DROPGAME]")) // ��밡 ����ϸ�
					endGame("��밡 ����Ͽ����ϴ�.");

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

	public void setCardNum(String str, int p1cn, int p2cn, int s1cn, int s2cn) {

	}

	private void endGame(String msg) { // ������ �����Ű�� �޼ҵ�
		infoView.setText(msg);
		startButton.setEnabled(false);

		try {
			Thread.sleep(2000);
		} catch (Exception e) {
		} // 2�ʰ� ���

		if (board.isRunning())
			board.stopGame();

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
			board.setWriter(writer);
		} catch (Exception e) {
			msgView.append(e + "\n\n���� ����..\n");
		}
	}

	public static void main(String[] args) {
		OmokClient client = new OmokClient("��Ʈ��ũ ���� ����");
		client.setSize(1750, 1000);
		client.setVisible(true);
		client.connect();
	}
}