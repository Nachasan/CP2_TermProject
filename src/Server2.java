import java.net.*;
import java.io.*;
import java.util.*;

public class Server2 {
	private ServerSocket serverSocket;

	public Server2() {
	}

	void StartServer() {
		try {
			serverSocket = new ServerSocket(7777);
			System.out.println("辑滚家南 积己");
			while (true) {
				Socket socket = serverSocket.accept();
				
				gameThread gt= new gameThread(socket);
				gt.start();
			
			}
		} catch (Exception e) {
		}
	}

	public static void main(String args[]) {
		Server2 s2 = new Server2();
		s2.StartServer();
	}
}

class gameThread extends Thread {
	Socket socket;
	Boolean isThread;
	gameThread(Socket socket){
		this.socket = socket;
	}
	
	public void run() {
		try {
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			
			String str;
			
			while(isThread) {
				
			}
		} catch (Exception e) {
		}
	}
}