import java.net.*;

import java.io.*;

import java.util.*;

public class OmokServer {
	private ServerSocket server;
	private Manager Man = new Manager(); // �޽��� �����
	private Random rand = new Random(); // ��� ���� ���Ƿ� ���ϱ� ���� ����

	private ArrayList<Card> Deck = new ArrayList<>();
	private int nowCard, p1CardNum, p2CardNum, s1CardNum, s2CardNum;

	public OmokServer() {
	}

	void startServer() { // ������ �����Ѵ�.
		try {
			server = new ServerSocket(7777);
			System.out.println("���������� �����Ǿ����ϴ�.");
			while (true) {
				Socket socket = server.accept();
				Omok_Thread ot = new Omok_Thread(socket);
				ot.start();
				Man.add(ot);
				System.out.println("������ ��: " + Man.size());
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void main(String[] args) {
		OmokServer server = new OmokServer();
		server.startServer();
	}

	class Omok_Thread extends Thread {
		private int roomNumber = -1; // �� ��ȣ
		private String userName = null; // ����� �̸�
		private Socket socket; // ����

		private boolean ready = false;
		private BufferedReader reader; // �Է� ��Ʈ��
		private PrintWriter writer; // ��� ��Ʈ��

		Omok_Thread(Socket socket) { // ������
			this.socket = socket;
		}

		Socket getSocket() { // ������ ��ȯ�Ѵ�.
			return socket;
		}

		int getRoomNumber() { // �� ��ȣ�� ��ȯ�Ѵ�.
			return roomNumber;
		}

		String getUserName() { // ����� �̸��� ��ȯ�Ѵ�.
			return userName;
		}

		boolean isReady() { // �غ� ���¸� ��ȯ�Ѵ�.
			return ready;
		}

		public void reload() throws IOException {
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 4; j++) {
					Deck.add(j + 4 * i, new Card(i));
				}
			}
		}

		public void cardSet() throws IOException {
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

		public void run() {
			try {
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer = new PrintWriter(socket.getOutputStream(), true);
				String msg; // Ŭ���̾�Ʈ�� �޽���

				while ((msg = reader.readLine()) != null) {
					if (msg.startsWith("[NAME]")) {
						userName = msg.substring(6); // userName�� ���Ѵ�.
					}

					else if (msg.startsWith("[ROOM]")) {
						int roomNum = Integer.parseInt(msg.substring(6));
						if (!Man.isFull(roomNum)) {
							if (roomNumber != -1)
								Man.sendToOthers(this, "[EXIT]" + userName);
							roomNumber = roomNum;
							writer.println(msg);
							writer.println(Man.getNamesInRoom(roomNumber));
							Man.sendToOthers(this, "[ENTER]" + userName);
						} else
							writer.println("[FULL]"); // ����ڿ� ���� á���� �˸���.
					}

					else if (roomNumber >= 1 && msg.startsWith("[STONE]"))
						Man.sendToOthers(this, msg);

					else if (msg.startsWith("[MSG]"))
						Man.sendToRoom(roomNumber, "[" + userName + "]: " + msg.substring(5));

					else if (msg.startsWith("[START]")) {
						ready = true; // ������ ������ �غ� �Ǿ���.
						if (Man.isReady(roomNumber)) {
							int a = rand.nextInt(2);
							if (a == 0) {
								writer.println("[FIRST]FIRST");
								cardSet();
								writer.println(
										"[PLAY]" + p1CardNum + " " + p2CardNum + " " + s1CardNum + " " + s2CardNum);
								Man.sendToOthers(this, "[FIRST]SECOND");
								Man.sendToOthers(this,
										"[PLAY]" + p1CardNum + " " + p2CardNum + " " + s1CardNum + " " + s2CardNum);
							} else {
								writer.println("[FIRST]SECOND");
								cardSet();
								writer.println(
										"[PLAY]" + p1CardNum + " " + p2CardNum + " " + s1CardNum + " " + s2CardNum);
								Man.sendToOthers(this, "[FIRST]FIRST");
								Man.sendToOthers(this,
										"[PLAY]" + p1CardNum + " " + p2CardNum + " " + s1CardNum + " " + s2CardNum);
							}
						}
					}

					else if (msg.startsWith("[STOPGAME]"))
						ready = false;

					else if (msg.startsWith("[DROPGAME]")) {
						ready = false;
						Man.sendToOthers(this, "[DROPGAME]");
					}

					else if (msg.startsWith("[WIN]")) {
						ready = false;
						writer.println("[WIN]");
						Man.sendToOthers(this, "[LOSE]");
					}
				}
			} catch (Exception e) {
			} finally {
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

					System.out.println(userName + "���� ������ �������ϴ�.");
					System.out.println("������ ��: " + Man.size());
					Man.sendToRoom(roomNumber, "[DISCONNECT]" + userName);
				} catch (Exception e) {
				}
			}
		}
	}

	class Manager extends Vector { // �޽����� �����ϴ� Ŭ����
		Manager() {
		}

		void add(Omok_Thread ot) { // �����带 �߰��Ѵ�.
			super.add(ot);
		}

		void remove(Omok_Thread ot) { // �����带 �����Ѵ�.
			super.remove(ot);
		}

		Omok_Thread getOT(int i) { // i��° �����带 ��ȯ�Ѵ�.
			return (Omok_Thread) elementAt(i);
		}

		Socket getSocket(int i) { // i��° �������� ������ ��ȯ�Ѵ�.
			return getOT(i).getSocket();
		}

		void sendTo(int i, String msg) {
			try {
				PrintWriter pw = new PrintWriter(getSocket(i).getOutputStream(), true);
				pw.println(msg);
			} catch (Exception e) {
			}
		}

		int getRoomNumber(int i) { // i��° �������� �� ��ȣ�� ��ȯ�Ѵ�.
			return getOT(i).getRoomNumber();
		}

		synchronized boolean isFull(int roomNum) { // ���� á���� �˾ƺ���.
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

		void sendToRoom(int roomNum, String msg) {
			for (int i = 0; i < size(); i++)
				if (roomNum == getRoomNumber(i))
					sendTo(i, msg);
		}

		void sendToOthers(Omok_Thread ot, String msg) {
			for (int i = 0; i < size(); i++)
				if (getRoomNumber(i) == ot.getRoomNumber() && getOT(i) != ot)
					sendTo(i, msg);
		}

		synchronized boolean isReady(int roomNum) {
			int count = 0;

			for (int i = 0; i < size(); i++)
				if (roomNum == getRoomNumber(i) && getOT(i).isReady())
					count++;

			if (count == 2)
				return true;

			return false;
		}

		String getNamesInRoom(int roomNum) {
			StringBuffer sb = new StringBuffer("[PLAYERS]");
			for (int i = 0; i < size(); i++)
				if (roomNum == getRoomNumber(i))
					sb.append(getOT(i).getUserName() + "\t");
			return sb.toString();
		}
	}
}