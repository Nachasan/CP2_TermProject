import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;

import javax.imageio.*;
import javax.swing.*;

import Server2.gameThread;

class Board {
	public static final int FIRST = 1, LAST = 2;
	private String info = "게임 중지";
	private boolean enable = false;
	private boolean running = false;
	private PrintWriter writer;
	private BufferedImage site;
	private Data d = new Data();

	Board() {
		try {
			site = ImageIO.read(new File("site1.png"));
		} catch (Exception e) {
		}
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public void startGame(String col) {
		running=true;
		if(col.equals("TRUE")) {
			enable = true; 
			info = "게임 시작... 배팅하세요.";
		} else {
			enable = false;
			info = "게임 시작... 기다리세요.";
		}
	}
	
	public void callOpponent() {
		info = "배킹하세요.";
	}
	
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
	public void setWriter(PrintWriter writer) {
		this.writer = writer;
	}
	
	public void paint(Graphics g) {
		drawSite(g);
	}
	
	public void drawCard(Graphics g) {
		g.drawImage(d.p1, 155, 5, null);
		g.drawImage(d.p2, 1110, 5, null);
		g.drawImage(d.s1, 495, 5, null);
		g.drawImage(d.s2, 770, 5, null);
	}
	
	synchronized private void drawSite(Graphics g) {
		g.drawImage(site, 150, 0, null);
	}
}

public class Client2 extends JFrame implements Runnable, ActionListener {
	private JTextArea msgView = new JTextArea("", 1, 1);

	private Board board = new Board();
	private BufferedReader reader;
	private PrintWriter writer;
	private Socket socket;
	private int roonNumber = -1;
	private String userName = null;

	public Client2() {
		super("Indian Hold'em");
		setLayout(null);
		
		JPanel p1 = new JPanel();
		p1.setLayout(null);
		JPanel p1_1 = new JPanel();
		p1_1.setLocation(0, 200);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

	}

	@Override
	public void run() {

	}

}
