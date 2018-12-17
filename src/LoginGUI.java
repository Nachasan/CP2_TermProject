import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class LoginGUI extends JFrame implements ActionListener {
	JTextField IDtx;
	JTextField PWtx;
	JButton btLogin;
	JButton btJoin;
	JLabel idLabel, pwLabel;
	Font f = new Font("", Font.BOLD, 20);
	Font f1 = new Font("", Font.BOLD, 12);
	public static JFrame g;
	int ans;
	String ID;
	

	void Window() {
		setTitle("Login");
		setSize(380, 130);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);

		idLabel = new JLabel("ID");
		idLabel.setSize(40, 30);
		idLabel.setLocation(15, 10);
		idLabel.setFont(f);
		add(idLabel);

		IDtx = new JTextField(10);
		IDtx.setSize(150, 30);
		IDtx.setLocation(50, 10);
		IDtx.setFont(f);
		add(IDtx);

		pwLabel = new JLabel("PW");
		pwLabel.setSize(40, 30);
		pwLabel.setLocation(10, 50);
		pwLabel.setFont(f);
		add(pwLabel);

		PWtx = new JTextField(10);
		PWtx.setSize(150, 30);
		PWtx.setLocation(50, 50);
		PWtx.setFont(f);
		add(PWtx);

		btLogin = new JButton("Login");
		btLogin.setSize(70, 70);
		btLogin.setLocation(210, 10);
		btLogin.setFont(f1);
		btLogin.addActionListener(this);
		add(btLogin);

		btJoin = new JButton("Join");
		btJoin.setSize(70, 70);
		btJoin.setLocation(290, 10);
		btJoin.setFont(f1);
		btJoin.addActionListener(this);
		add(btJoin);

		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btLogin) {
			try {
				doLogin();
			} catch (IOException e1) {
			}
		} else if (e.getSource() == btJoin) {
			OpenJoin();
		}
	}

	public void OpenJoin() {
		JFrame w = new JoinGUI();
		setVisible(false);
	}

	@SuppressWarnings("deprecation")
	public void doLogin() throws IOException {
		int temp = 0;
		boolean FLAG1 = false;
		boolean FLAG2 = true;
		String temp1 = "";
		ArrayList<String> checkPW = new ArrayList<>();
		try {
			File file = new File("ID.txt");
			FileReader filereader = new FileReader(file);
			BufferedReader bufReader = new BufferedReader(filereader);

			while ((temp1 = bufReader.readLine()) != null) {
				temp++;
				if (temp1.equals(IDtx.getText())) {
					FLAG1 = true;
					break;
				}
			}
			bufReader.close();
		} finally {
			if (!FLAG1) {
				JOptionPane.showMessageDialog(null, "ID를 잘못입력하셨습니다.", "오류", JOptionPane.ERROR_MESSAGE);
				FLAG2 = false;
			}
		}
		if (FLAG2) {
			try {
				File file = new File("PW.txt");
				FileReader filereader = new FileReader(file);
				BufferedReader bufReader = new BufferedReader(filereader);
				String l;
				while ((l = bufReader.readLine()) != null) {
					checkPW.add(l);
				}
				bufReader.close();
			} finally {
			}
			if (!(PWtx.getText().equals(checkPW.get(temp - 1)))) {
				JOptionPane.showMessageDialog(null, "암호를 잘못입력하셨습니다.", "오류", JOptionPane.ERROR_MESSAGE);
			} else {
				String[] answer = {"확인"};
				ans = JOptionPane.showOptionDialog(null, "로그인 성공!", "Login", JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE, null, answer, null);
				if (ans == 0)
					setVisible(false);
				ID = temp1;
			}
		}
	}
	
	public String getID() {
		return ID;
	}

	public LoginGUI() {
		Window();
	}
	
	public int getAns() {
		return ans;
	}
	
	class MyListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
		}
		
	}

	public static void main(String args[]) {
		g = new LoginGUI();
	}

}