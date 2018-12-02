import java.util.*;

import javax.swing.*;

public class IndianHoldem extends JFrame {

	public IndianHoldem() {
		setSize(1500, 1000);
		add(new Room());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String args[]) {
		new IndianHoldem();
	}
}
