import java.io.*;
import java.net.*;
import java.util.*;

public class Client {

	public static void main(String[] args) {
		try {
			Socket s = new Socket("localhost", 9999);  // 9999��Ʈ�� ����
			System.out.println("Connected....");

			Scanner sc = new Scanner(System.in);

			while(true) {
				String myMsg = sc.nextLine();

				OutputStream os = s.getOutputStream();
				DataOutputStream dos = new DataOutputStream(os);

				dos.writeUTF(myMsg);

				InputStream is = s.getInputStream();
				DataInputStream dis = new DataInputStream(is);
				String rcvMsg = dis.readUTF();

				System.out.println("Echo : " + rcvMsg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}