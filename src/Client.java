import java.io.*;
import java.net.*;

public class Client {
	Data d = new Data();
	Socket socket;
	
	InputStream is;
	OutputStream os;
	ObjectInputStream ois;
	ObjectOutputStream oos;
	
	public void connect() {
		try {
			System.out.println("立加 矫档");
			socket = new Socket("127.0.0.1", 8080);
			System.out.println("立加 肯丰");
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
			socket.close();
			ois.close();
			is.close();
			oos.close();
			os.close();
		} catch (Exception e) {
		}
	}
	
	public Client() {
		
		connect();
		streamSetting();
		dataSend();
		dataRecv();
	}
	
	public static void main(String[] args) {
		new Client();
	}
}