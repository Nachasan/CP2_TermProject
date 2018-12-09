import java.io.*;
import java.net.*;

public class Server {
	Data d = new Data();
	ServerSocket serverSocket;
	Socket socket;
	
	InputStream is;
	OutputStream os;
	ObjectInputStream ois;
	ObjectOutputStream oos;
	
	public void serverSetting() {
		try {
			serverSocket = new ServerSocket(8080);
			System.out.println("서버 생성");
			socket = serverSocket.accept();
			System.out.println("클라이언트 소켓 연결");
		} catch (Exception e) {
		}
	}
	
	public void dataRecv() {
		new Thread(new Runnable() {
			boolean isThread = true;
			
			@Override
			public void run() {
				while (isThread) {
					try {
						d = (Data) ois.readObject();
					} catch (Exception e) {
					} 
				}
			}
		}).start();
	}
	
	public void dataSend() {
		new Thread(new Runnable() {
			boolean isThread = true;
			
			@Override
			public void run() {
				while (isThread) {
					try {
						oos.writeObject(d);
					} catch (Exception e) {
					}
				}
			}
		}).start();
	}
	
	public void streamSetting() {
		InputStream is;
		try {
			is = socket.getInputStream();
			ois = new ObjectInputStream(is);
			os = socket.getOutputStream();
			oos = new ObjectOutputStream(os);
		} catch (Exception e) {
		}
	}
	
	public void closeAll() {
		try {
			serverSocket.close();
			socket.close();
			ois.close();
			is.close();
			oos.close();
			os.close();
		} catch (Exception e) {
		}
	}
	
	public Server() {
		
		serverSetting();
		streamSetting();
		dataRecv();
		dataSend();
	}
	
	public static void main(String[] args) {
		new Server();
	}
}