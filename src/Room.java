import java.io.File;
import java.util.ArrayList;

import javax.swing.JPanel;

import psd.model.Psd;

public class Room extends JPanel{
	ArrayList<Card> Deck = new ArrayList<>();
	Deck d = new Deck();
	JPanel p;
	Psd site = null;
	
	public Room() {
		
		try{
			File f = new File("site1.psd");
			site = new Psd(f);
		} catch(Exception e) {
		}
		
	}

	public void reload() {
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 4; j++) {
				Deck.add(j + 4 * i, new Card(i));
			}
		}
	}

}
