import java.awt.*;
import javax.swing.*;

public class Lobby {
	private JPanel p;
	private JPanel p1;
	private JPanel p2;
	
	public Lobby() {
		p = new JPanel();
		
		p1 = new JPanel();
		roomList rl = new roomList();
		playerList pl = new playerList();
		p1.add(rl, BorderLayout.WEST);
		p1.add(pl, BorderLayout.EAST);
		
		p2 = new JPanel();
		chatBox cb = new chatBox();
		SimpleInformation si = new SimpleInformation();
		p2.add(cb, BorderLayout.WEST);
		p2.add(si, BorderLayout.EAST);
		
		p.add(p1, BorderLayout.NORTH);
		p.add(p2, BorderLayout.SOUTH);
	}

}
