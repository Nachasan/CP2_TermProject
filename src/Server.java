import java.io.*;
import java.net.*;

public class Server {

	public static void main(String[] args) {
		ServerSocket a;
		try {
			a = new ServerSocket(9999); // 9999�� ��Ʈ�� ���� ��� ����
			System.out.println("Multi-Server Start!!!...");
			
			Socket b = a.accept();   // client ���� ��� ��
			System.out.println("Client Connected......");
			
			while(true) {
				InputStream is = b.getInputStream();
				DataInputStream ds = new DataInputStream(is);
				
				String rcvBuff = ds.readUTF();
				System.out.println("���� ���� : " + rcvBuff);
				
				OutputStream os = b.getOutputStream();
				DataOutputStream dos = new DataOutputStream(os);

				dos.writeUTF(rcvBuff);
				
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}