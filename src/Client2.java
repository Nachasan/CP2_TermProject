import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.imageio.*;
import javax.swing.*;

class Board extends JPanel {
	public static final int FIRST = 1, LAST = 2;
	private String info = "게임 중지";
	private boolean enable = false;
	private boolean running = false;
	private PrintWriter writer;
	private BufferedImage site;

	private Data d = new Data();
	JButton exit, call, plus, minus, die, help;
	JLabel p1CoinText, p2CoinText, dealCoin, nowCoinText, nowCardText;
	Font f = new Font("", Font.BOLD, 20);

	Board() {
		setSize(1400, 1000);
		setLocation(0, 0);
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

	}

	class MyListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!enable) {
				return;
			} else if (e.getSource() == plus) {
				if ((d.deal == d.p1Coin) || (d.deal == d.p2Coin)) {
				} else {
					d.setChange(true);
					d.setDeal(d.deal + 1);
					dealCoin.setText("배팅할 코인: " + d.deal);
				}
			} else if (e.getSource() == minus) {
				if ((d.deal == d.dealedCoin) || (d.deal == 1)) {
				} else {
					if (d.deal == d.dealedCoin - 1)
						d.setChange(false);
					d.setDeal(d.deal - 1);
					dealCoin.setText("배팅할 코인: " + d.deal);
				}
			}
		}

	}

	public boolean isRunning() {
		return running;
	}

	public void startGame(String col) {
		running = true;
		if (col.equals("TRUE")) {
			enable = true;
			info = "게임 시작... 배팅하세요.";
		} else {
			enable = false;
			info = "게임 시작... 기다리세요.";
		}
	}

	public void stopGame() {

		writer.println("[STOPGAME]");

		enable = false;

		running = false;

	}

	public void callOpponent() {
		info = "배팅하세요.";
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public void setWriter(PrintWriter writer) {
		this.writer = writer;
	}

	class image extends JPanel {
		public image() {
			setSize(1500, 355);
			setLocation(0, 200);
		}

		public void paint(Graphics g) {
			g.drawImage(site, 100, 0, null);
			drawCard(g);

		}

		synchronized public void drawCard(Graphics g) {
			g.drawImage(d.p1, 105, 5, null);
			g.drawImage(d.p2, 1060, 5, null);
			g.drawImage(d.s1, 445, 5, null);
			g.drawImage(d.s2, 720, 5, null);
		}
	}
}

public class Client2 extends JFrame implements Runnable, ActionListener {
	private JTextArea msgView = new JTextArea("", 1, 1);
	private JTextField sendBox = new JTextField("");
	private JTextField nameBox = new JTextField();
	private JTextField roomBox = new JTextField("0");

	private JLabel pInfo = new JLabel("대기실:  명");
	private Vector<String> players = new Vector<>();

	private Button startButton = new Button("게임 시작");
	private Button enterButton = new Button("입장하기");
	private Button exitButton = new Button("대기실로");

	private JLabel infoView = new JLabel("Indian Hold'em");
	private Board board = new Board();
	private BufferedReader reader;
	private PrintWriter writer;
	private Socket socket;
	private int roomNumber = -1;
	private String userName = null;
	private Font f = new Font("", Font.PLAIN, 15);
	private Data d = new Data();

	public Client2() {
		super("Indian Hold'em");
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		pInfo.setFont(f);
		msgView.setEditable(false);
		startButton.setFont(f);
		enterButton.setFont(f);
		exitButton.setFont(f);

		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout(3, 3));
		p1.add(new Label("이     름:", 2));
		p1.add(nameBox);
		p1.add(new Label("방 번호:", 2));
		p1.add(roomBox);
		p1.add(enterButton);
		p1.add(exitButton);
		enterButton.setEnabled(false);
		p1.setBackground(Color.white);
		p1.setLocation(1420, 10);
		p1.setSize(300, 70);

		JPanel p2 = new JPanel();
		p2.setLayout(new BorderLayout());
		p2.add(pInfo, "North");
		p2.add(startButton, "South");
		startButton.setEnabled(false);
		p2.setLocation(1420, 100);
		p2.setSize(300, 70);

		JPanel p3 = new JPanel();
		p3.setLayout(new BorderLayout());
		p3.add(msgView, "Center");
		p3.add(sendBox, "South");
		p3.setLocation(1420, 190);
		p3.setSize(300, 760);

		add(board);
		add(p1);
		add(p2);
		add(p3);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == sendBox) { // 메시지 입력 상자이면
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
		} else if (e.getSource() == enterButton) { // 입장하기 버튼이면
			try {
				if (Integer.parseInt(roomBox.getText()) < 1) {
					infoView.setText("방번호가 잘못되었습니다. 1이상");
					return;
				}
				writer.println("[ROOM]" + Integer.parseInt(roomBox.getText()));
				msgView.setText("");
			} catch (Exception ie) {
				infoView.setText("입력하신 사항에 오류가 았습니다.");
			}
		} else if (e.getSource() == exitButton) { // 대기실로 버튼이면
			try {
				goToWaitRoom();
				startButton.setEnabled(false);
			} catch (Exception ie) {
			}
		} else if (e.getSource() == startButton) { // 대국 시작 버튼이면
			try {
				writer.println("[START]");
				infoView.setText("상대의 결정을 기다립니다.");
				startButton.setEnabled(false);
			} catch (Exception ie) {
			}
		}
	}

	void goToWaitRoom() { // 대기실로 버튼을 누르면 호출된다.
		if (userName == null) {
			String name = nameBox.getText().trim();
			if (name.length() <= 2 || name.length() > 10) {
				infoView.setText("이름이 잘못되었습니다. 3~10자");
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
		infoView.setText("대기실에 입장하셨습니다.");
		roomBox.setText("0");
		enterButton.setEnabled(true);
		exitButton.setEnabled(false);
	}

	private void nameList(String msg) {

	}

	@Override
	public void run() {
		String str;
		try {
			while ((str = reader.readLine()) != null) {
				if (str.startsWith("[ROOM]")) {
					if (!str.equals("[ROOM]0")) {
						enterButton.setEnabled(false);
						exitButton.setEnabled(true);
						infoView.setText(str.substring(6) + "번 방에 입장하셨습니다.");
					} else
						infoView.setText("대기실에 입장하셨습니다.");
					roomNumber = Integer.parseInt(str.substring(6));
				} else if (str.startsWith("[FULL]")) {
					infoView.setText("방이 차서 입장할 수 없습니다.");
				} else if (str.startsWith("[PLAYERS]")) {
					nameList(str.substring(9));
				} else if (str.startsWith("[ENTER]")) {
					players.add(str.substring(7));
					playersInfo();
					msgView.append("[" + str.substring(7) + "]님이 입장하였습니다.\n");
				} else if (str.startsWith("[EXIT]")) {
					players.remove(str.substring(6));
					playersInfo();
					msgView.append("[" + str.substring(6) + "]님이 다른 방으로 입장하였습니다.\n");
					if (roomNumber != 0)
						endGame("상대가 나갔습니다.");
				}
			}

		} catch (Exception e) {

		}
	}

	private void endGame(String msg) { // 게임의 종료시키는 메소드

		infoView.setText(msg);

		startButton.setEnabled(false);

		try {
			Thread.sleep(2000);
		} catch (Exception e) {
		} // 2초간 대기

		if (board.isRunning())
			board.stopGame();

		if (players.size() == 2)
			startButton.setEnabled(true);

	}

	private void playersInfo() { // 방에 있는 접속자의 수를 보여준다.
		int count = players.size();
		if (roomNumber == 0)
			pInfo.setText("대기실: " + count + "명");
		else
			pInfo.setText(roomNumber + " 번 방: " + count + "명");

		// 대국 시작 버튼의 활성화 상태를 점검한다.

		if (count == 2 && roomNumber != 0)
			startButton.setEnabled(true);
		else
			startButton.setEnabled(false);

	}

	private void connect() {
		try {
			msgView.append("서버에 연결을 요청합니다.\n");
			socket = new Socket("127.0.0.1", 7777);
			msgView.append("---연결 성공--.\n");

			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream(), true);
			new Thread(this).start();
			board.setWriter(writer);
		} catch (Exception e) {
		}
	}

	public static void main(String args[]) {
		Client2 c = new Client2();
		c.setSize(1750, 1000);
		c.setVisible(true);
		c.connect();
	}

}
