import java.net.*;
import java.io.*;
import java.util.*;

public class Server2 {
	private ServerSocket serverSocket;
	private Manager Man = new Manager();
	private Random rnd = new Random();

	public Server2() {
	}

	void StartServer() {
		try {
			serverSocket = new ServerSocket(7777);
			System.out.println("서버소켓 생성");
			while (true) {
				Socket socket = serverSocket.accept();

				gameThread gt = new gameThread(socket);
				gt.start();

			}
		} catch (Exception e) {
		}
	}

	public static void main(String args[]) {
		Server2 s2 = new Server2();
		s2.StartServer();
	}

	class gameThread extends Thread {
		private Socket socket;
		private int roomNumber = -1;
		private String userName = null;
		private boolean ready = false;

		private BufferedReader reader;
		private PrintWriter writer;

		gameThread(Socket socket) {
			this.socket = socket;
		}

		Socket getSocket() {
			return socket;
		}

		int getRoomNumber() {
			return roomNumber;
		}

		String getUserName() {
			return userName;
		}

		boolean isReady() {
			return ready;
		}

		public void run() {
			try {
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer = new PrintWriter(socket.getOutputStream(), true);

				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

				String str;

				while ((str = reader.readLine()) != null) {
					if (str.startsWith("[NAME]"))
						userName = str.substring(6);

					else if (str.startsWith("[ROOM]")) {
						int roomNum = Integer.parseInt(str.substring(6));
						if (Man.isFull(roomNum)) {
							if (roomNumber != -1)
								Man.sendToOthers(this, "[EXIT]" + userName);
							roomNumber = roomNum;
							writer.println(str);
							writer.println(Man.getNameInRoom(roomNumber));
							Man.sendToOthers(this, "[ENTER]" + userName);
						} else
							writer.println("[FULL}");
					}

					else if (str.startsWith("[START]")) {
						ready = true;

						if (Man.isReady(roomNumber)) {
							int a = rnd.nextInt(2);
							if (a == 0) {
								writer.println("[FIRST]TRUE");
								Man.sendToOthers(this, "[FIRST]TRUE");
							} else {
								writer.println("[FIRST]FALSE");
								Man.sendToOthers(this, "[FIRST]FALSE");
							}
						}
					}

					else if (str.startsWith("[WIN]")) {
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
					System.out.println(userName + "님이 접속을 끊었습니다.");
					System.out.println("접속자 수: " + Man.size());
					Man.sendToRoom(roomNumber, "[DISCONNECT]" + userName);
				} catch (Exception e) {

				}
			}
		}
	}

	class Manager extends Vector {
		Manager() {
		}

		void add(gameThread gt) {
			super.add(gt);
		}

		void remove(gameThread gt) {
			super.remove(gt);
		}

		gameThread getGT(int i) {
			return (gameThread) elementAt(i);
		}

		Socket getSocket(int i) {
			return getGT(i).getSocket();
		}

		void sendTo(int i, String str) {
			try {
				PrintWriter pw = new PrintWriter(getSocket(i).getOutputStream(), true);
				pw.println(str);
			} catch (Exception e) {
			}
		}

		int getRoomNumber(int i) {
			return getGT(i).getRoomNumber();
		}

		synchronized boolean isFull(int roomNum) {
			if (roomNum == 0)
				return false;
			int count = 0;
			for (int i = 0; i < size(); i++) {
				if (roomNum == getRoomNumber(i))
					count++;
			}
			if (count >= 2)
				return true;
			return false;
		}

		void sendToRoom(int roomNum, String str) {
			for (int i = 0; i < size(); i++) {
				if (roomNum == getRoomNumber(i))
					sendTo(i, str);
			}
		}

		void sendToOthers(gameThread gt, String str) {
			for (int i = 0; i < size(); i++) {
				if (getRoomNumber(i) == gt.getRoomNumber() && getGT(i) != gt)
					sendTo(i, str);
			}
		}

		synchronized boolean isReady(int roomNum) {
			int count = 0;
			for (int i = 0; i < size(); i++)
				if (roomNum == getRoomNumber(i) && getGT(i).isReady())
					count++;
			if (count == 2)
				return true;
			return false;
		}

		String getNameInRoom(int roomNum) {
			StringBuffer sb = new StringBuffer("[PLAYERS]");
			for (int i = 0; i < size(); i++)
				if (roomNum == getRoomNumber(i))
					sb.append(getGT(i).getUserName() + "\t");
			return sb.toString();
		}
	}
}
