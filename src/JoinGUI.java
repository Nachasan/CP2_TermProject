import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class JoinGUI extends JFrame implements ActionListener {
	JTextField nIDtx, nPWtx, rePWtx;
	JButton btJoin;
	JLabel idLabel, pwLabel, pw2Label;
	Font f = new Font("", Font.BOLD, 20);
	Font f1 = new Font("", Font.BOLD, 17);

	void Window() {
		setTitle("Join");
		setSize(330, 170);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);

		idLabel = new JLabel("ID");
		idLabel.setSize(60, 30);
		idLabel.setLocation(10, 10);
		idLabel.setFont(f);
		add(idLabel);

		nIDtx = new JTextField(10);
		nIDtx.setSize(150, 30);
		nIDtx.setLocation(80, 10);
		nIDtx.setFont(f);
		add(nIDtx);

		pwLabel = new JLabel("PW");
		pwLabel.setSize(60, 30);
		pwLabel.setLocation(10, 50);
		pwLabel.setFont(f);
		add(pwLabel);

		nPWtx = new JTextField(10);
		nPWtx.setSize(150, 30);
		nPWtx.setLocation(80, 50);
		nPWtx.setFont(f);
		add(nPWtx);

		pw2Label = new JLabel("RePW");
		pw2Label.setSize(60, 30);
		pw2Label.setLocation(10, 90);
		pw2Label.setFont(f);
		add(pw2Label);

		rePWtx = new JTextField(10);
		rePWtx.setSize(150, 30);
		rePWtx.setLocation(80, 90);
		rePWtx.setFont(f);
		add(rePWtx);

		btJoin = new JButton("Join");
		btJoin.setSize(70, 110);
		btJoin.setLocation(240,10);
		btJoin.setFont(f1);
		btJoin.addActionListener(this);
		add(btJoin);

		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btJoin) {
			try {
				doJoin();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public void doJoin() throws IOException {
		boolean FLAG = false;
		boolean FLAG2 = false;
		FileReader inID = null;
		FileReader inPW = null;
		FileWriter outID = null;
		FileWriter outPW = null;

		if (nIDtx.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "아이디를 입력해 주십시오.", "경고", JOptionPane.WARNING_MESSAGE);
			System.out.println("zz");
		} else {
			try {

				File file = new File("ID.txt");
				FileReader filereader = new FileReader(file);
				BufferedReader bufReader = new BufferedReader(filereader);
				String temp1 = "";
				while ((temp1 = bufReader.readLine()) != null) {
					if (temp1.equals(nIDtx.getText())) {
						JOptionPane.showMessageDialog(null, "이미 존재하는 아이디입니다.", "경고", JOptionPane.WARNING_MESSAGE);
						FLAG2 = true;
						break;
					}
				}
				bufReader.close();
			} finally {
				FLAG = true;
			}
		}
		if (FLAG && !FLAG2) {
			if (nPWtx.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "비밀번호를 입력하십시오.", "경고", JOptionPane.WARNING_MESSAGE);
			} else if (!nPWtx.getText().equals(rePWtx.getText())) {
				JOptionPane.showMessageDialog(null, "재입력 비밀번호를 다시 입력해 주십시오.", "경고", JOptionPane.WARNING_MESSAGE);
			} else {
				try {
					outID = new FileWriter("ID.txt", true);
					outPW = new FileWriter("PW.txt", true);
					outID.write(nIDtx.getText() + "\r\n");
					outPW.write(nPWtx.getText() + "\r\n");
				} finally {
					if (outID != null) {
						outID.close();
					}
					if (outPW != null) {
						outPW.close();
					}
					JOptionPane.showMessageDialog(null, "회원가입에 성공하셨습니다!");
					OpenLogin();
				}
			}
		}
	}

	public void OpenLogin() {
		LoginGUI.g.setVisible(true);
		this.dispose();
	}

	public JoinGUI() {
		Window();
	}

	public static void main(String args[]) {
		new JoinGUI();
	}
}