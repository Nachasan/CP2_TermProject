import java.net.*;

import java.io.*;

import java.util.*;

public class Server {
	private ServerSocket server;
	private Manager Man = new Manager(); // 메시지 방송자
	private Random rand = new Random(); // 선플레이어를 임의로 정하기 위한 변수

	private ArrayList<Card> Deck = new ArrayList<>();
	private int nowCard, p1CardNum, p2CardNum, s1CardNum, s2CardNum;

	public Server() {
	}

	void startServer() { // 서버를 실행한다.
		try {
			server = new ServerSocket(7777);
			System.out.println("서버소켓이 생성되었습니다.");
			while (true) {
				Socket socket = server.accept();
				GameThread gt = new GameThread(socket);
				gt.start();
				Man.add(gt);
				System.out.println("접속자 수: " + Man.size());
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void main(String[] args) {
		Server server = new Server();
		server.startServer();
	}

	class GameThread extends Thread {
		private int roomNumber = -1; // 방 번호
		private String userName = null; // 사용자 이름
		private Socket socket; // 소켓

		private boolean ready = false;
		private BufferedReader reader; // 입력 스트림
		private PrintWriter writer; // 출력 스트림

		GameThread(Socket socket) { // 생성자
			this.socket = socket;
		}

		Socket getSocket() { // 소켓을 반환한다.
			return socket;
		}

		int getRoomNumber() { // 방 번호를 반환한다.
			return roomNumber;
		}

		String getUserName() { // 사용자 이름을 반환한다.
			return userName;
		}

		boolean isReady() { // 준비 상태를 반환한다.
			return ready;
		}

		public void reload() throws IOException { // 덱을 갱신한다.
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 4; j++) {
					Deck.add(j + 4 * i, new Card(i));
				}
			}
		}

		public void cardSet() throws IOException { // 카드를 덱에서 랜덤으로 4개 뽑는다.
			if (nowCard == 0) {
				reload();
				nowCard = 40;
			}
			int p1Num = rand.nextInt(nowCard - 1);
			p1CardNum = Deck.get(p1Num).cardNumber;
			Deck.remove(p1Num);
			nowCard -= 1;

			int p2Num = rand.nextInt(nowCard - 1);
			p2CardNum = Deck.get(p2Num).cardNumber;
			Deck.remove(p2Num);
			nowCard -= 1;

			int s1Num = rand.nextInt(nowCard - 1);
			s1CardNum = Deck.get(s1Num).cardNumber;
			Deck.remove(s1Num);
			nowCard -= 1;

			int s2Num = 0;
			if (nowCard != 1)
				s2Num = rand.nextInt(nowCard - 1);
			s2CardNum = Deck.get(s2Num).cardNumber;
			Deck.remove(s2Num);
			nowCard -= 1;
		}
		
		public void getWinner() throws IOException { // 승자를 정한다.
			int winner = 0;
			if ((p1CardNum == s1CardNum) && (s1CardNum == s2CardNum)) {
				if ((p2CardNum == s1CardNum) && (s1CardNum == s2CardNum)) {
					if (p1CardNum < p2CardNum)
						winner = 2;
					else if (p1CardNum > p2CardNum)
						winner = 1;
					else if (p1CardNum == p2CardNum) {
						writer.println("[DRAW]");
					}
				} else {
					winner = 1;
				}
			} else if ((p2CardNum == s1CardNum) && (s1CardNum == s2CardNum)) {
				winner = 2;
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
						writer.println("[DRAW]");
					}
				} else {
					winner = 1;
				}
			} else if (((p2CardNum == s1CardNum - 1) && (s1CardNum == s2CardNum - 1))
					|| ((p2CardNum == s1CardNum + 1) && (s1CardNum == s2CardNum + 1))
					|| ((p2CardNum == s1CardNum - 2) && (s1CardNum == s2CardNum + 1))
					|| ((p2CardNum == s1CardNum + 1) && (s1CardNum == s2CardNum - 1))
					|| ((p2CardNum == s1CardNum + 2) && (s1CardNum == s2CardNum - 1))
					|| ((p2CardNum == s1CardNum - 1) && (s1CardNum == s2CardNum + 1))) {
				winner = 2;
			} else if ((p1CardNum == s1CardNum) || (p1CardNum == s2CardNum)) {
				if ((p2CardNum == s1CardNum) || (p2CardNum == s2CardNum)) {
					if (p1CardNum < p2CardNum)
						winner = 2;
					else if (p1CardNum > p2CardNum)
						winner = 1;
					else if (p1CardNum == p2CardNum) {
						writer.println("[DRAW]");
					}
				} else {
					winner = 1;
				}
			} else if ((p2CardNum == s1CardNum) || (p2CardNum == s2CardNum)) {
				winner = 2;
			} else {
				if (p1CardNum < p2CardNum)
					winner = 2;
				else if (p1CardNum > p2CardNum)
					winner = 1;
				else if (p1CardNum == p2CardNum) {
					writer.println("[DRAW]");
				}
			}
			if (winner == 1) {
				writer.println("[WINNER]1");
			} else if (winner == 2) {
				writer.println("[WINNER]2");
			}
		}

		public void run() { // 클라이언트와 연결되여 메세지를 주고받는다.
							// 메세지를 주고받음으로서 데이터를 주고받는다.
			try {
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer = new PrintWriter(socket.getOutputStream(), true);
				String msg; // 클라이언트의 메시지

				while ((msg = reader.readLine()) != null) {
					if (msg.startsWith("[NAME]")) {
						userName = msg.substring(6); // userName을 정한다.
					}

					else if (msg.startsWith("[ROOM]")) { // 방을 정한다.
						int roomNum = Integer.parseInt(msg.substring(6));
						if (!Man.isFull(roomNum)) {
							if (roomNumber != -1)
								Man.sendToOthers(this, "[EXIT]" + userName); // 방에서 나간다.
							roomNumber = roomNum;
							writer.println(msg);
							writer.println(Man.getNamesInRoom(roomNumber));
							Man.sendToOthers(this, "[ENTER]" + userName); // 방에 들어간다.
						} else
							writer.println("[FULL]"); // 사용자에 방이 찼음을 알린다.
					}

					else if (msg.startsWith("[MSG]")) // 채팅창에서 메세지를 주고받는다.
						Man.sendToRoom(roomNumber, "[" + userName + "]: " + msg.substring(5));

					else if (msg.startsWith("[START]")) { // 게임을 시작한다.
						ready = true; // 게임을 시작할 준비가 되었다.
						if (Man.isReady(roomNumber)) {
							int a = rand.nextInt(2);
							if (a == 0) { // 후에 게임시작을 누른 플레이어가 선플레이어가 된다.
								cardSet();
								writer.println(
										"[PLAY]" + p1CardNum + " " + p2CardNum + " " + s1CardNum + " " + s2CardNum);
								writer.println("[FIRST]FIRST");
								Man.sendToOthers(this,
										"[PLAY]" + p1CardNum + " " + p2CardNum + " " + s1CardNum + " " + s2CardNum);
								Man.sendToOthers(this, "[FIRST]SECOND");
							} else { // 후에 게임시작을 누른 플레이어가 후플레이어가 된다.
								cardSet();
								writer.println(
										"[PLAY]" + p1CardNum + " " + p2CardNum + " " + s1CardNum + " " + s2CardNum);
								writer.println("[FIRST]SECOND");

								Man.sendToOthers(this,
										"[PLAY]" + p1CardNum + " " + p2CardNum + " " + s1CardNum + " " + s2CardNum);
								Man.sendToOthers(this, "[FIRST]FIRST");
							}
						}
					}
					
					else if (msg.startsWith("[CHECK]")) // 한 판의 승자를 정한다.
						getWinner();
					
					else if (msg.startsWith("[CALL]")) // 플레이어가 call한 것을 다른 플레이어에게 전달한다.
						Man.sendToOthers(this, msg);
					
					else if (msg.startsWith("[NEXT]")) { // 다음 판을 시작한다.
						cardSet();
						writer.println(
								"[PLAY]" + p1CardNum + " " + p2CardNum + " " + s1CardNum + " " + s2CardNum);
						Man.sendToOthers(this,
								"[PLAY]" + p1CardNum + " " + p2CardNum + " " + s1CardNum + " " + s2CardNum);
					}
					
					else if (msg.startsWith("[SEND]")) // 다음 게임의 데이터값을 전달한다.
						Man.sendToOthers(this, msg);
					
					else if (msg.startsWith("[STOPGAME]")) // 게임을 중지한다. 상대가 나갈때도 적용된다.
						ready = false;

					else if (msg.startsWith("[WIN]")) { // 한 게임의 승패여부를 전달한다.
						ready = false;
						writer.println("[WIN]");
						Man.sendToOthers(this, "[LOSE]");
					}
				}
			} catch (Exception e) {
			} finally { // 전체 게임을 종료했을 때 실행된다.
				try {
					Man.remove(this); 

					if (reader != null)
						reader.close();

					if (writer != null)
						writer.close();

					if (socket != null)
						socket.close();

					reader = null;
					writer = null;
					socket = null;

					System.out.println(userName + "님이 접속을 끊었습니다.");
					System.out.println("접속자 수: " + Man.size());
					Man.sendToRoom(roomNumber, "[DISCONNECT]" + userName);
				} catch (Exception e) {
				}
			}
		}
	}

	class Manager extends Vector { // 같은 방에 있는 클라이언트에 메시지를 전달하는 클래스
		Manager() { // 생성자
		}

		void add(GameThread gt) { // 스레드를 추가한다.
			super.add(gt);
		}

		void remove(GameThread gt) { // 스레드를 제거한다.
			super.remove(gt);
		}

		GameThread getGT(int i) { // i번째 스레드를 반환한다.
			return (GameThread) elementAt(i);
		}

		Socket getSocket(int i) { // i번째 스레드의 소켓을 반환한다.
			return getGT(i).getSocket();
		}

		void sendTo(int i, String msg) { // i번째 스레드에 메세지를 전달한다.
			try {
				PrintWriter pw = new PrintWriter(getSocket(i).getOutputStream(), true);
				pw.println(msg);
			} catch (Exception e) {
			}
		}

		int getRoomNumber(int i) { // i번째 스레드의 방 번호를 반환한다.
			return getGT(i).getRoomNumber();
		}

		synchronized boolean isFull(int roomNum) { // 방이 찼는지 알아본다.
			if (roomNum == 0)
				return false;

			int count = 0;

			for (int i = 0; i < size(); i++)
				if (roomNum == getRoomNumber(i))
					count++;

			if (count >= 2)
				return true;

			return false;
		}

		void sendToRoom(int roomNum, String msg) { // 같은 방에 있는 플레이어에게 메세지를 전달한다.
			for (int i = 0; i < size(); i++)
				if (roomNum == getRoomNumber(i))
					sendTo(i, msg);
		}

		void sendToOthers(GameThread gt, String msg) { // 다른 플레이어에게 메세지를 전달한다.
			for (int i = 0; i < size(); i++)
				if (getRoomNumber(i) == gt.getRoomNumber() && getGT(i) != gt)
					sendTo(i, msg);
		}

		synchronized boolean isReady(int roomNum) { // ready의 값을 반환한다.
			int count = 0;

			for (int i = 0; i < size(); i++)
				if (roomNum == getRoomNumber(i) && getGT(i).isReady())
					count++;

			if (count == 2)
				return true;

			return false;
		}

		String getNamesInRoom(int roomNum) { // 방에 있는 플레이어의 이름을 반환한다.
			StringBuffer sb = new StringBuffer("[PLAYERS]");
			for (int i = 0; i < size(); i++)
				if (roomNum == getRoomNumber(i))
					sb.append(getGT(i).getUserName() + "\t");
			return sb.toString();
		}
	}
}