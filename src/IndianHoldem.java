import java.awt.*;
import java.io.IOException;

import javax.swing.JFrame;

public class IndianHoldem extends JFrame {

	public IndianHoldem() throws IOException{
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1500, 1000);
//		setBackground(Color.BLACK);
		add(new Room(), BorderLayout.CENTER);
		setVisible(true);
	}

	public static void main(String args[]) throws IOException{
		new IndianHoldem();
	}
}
