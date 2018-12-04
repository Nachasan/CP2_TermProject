import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

import com.idrsolutions.image.psd.PsdDecoder;

public class Room extends JPanel {
	ArrayList<Card> Deck = new ArrayList<>();
	Deck d = new Deck();
	JPanel p;
	PsdDecoder pd = new PsdDecoder();
	BufferedImage site, p1, p2, s1, s2;
	Random rand = new Random();
	int nowCard;
	public Room() {
		try {
			site = pd.read(new File("site1.psd"));
		} catch (Exception e) {
		}

		for (int i = 1; i < 11; i++) {
			for (int j = 0; j < 4; j++) {
				Deck.add(j + 4 * (i-1), new Card(i));
			}
		}
		
		nowCard = 40;
		int p1Num = rand.nextInt(nowCard-1);
		p1 = Deck.get(p1Num).img;
		Deck.remove(p1Num);
		nowCard -= 1;

		int p2Num = rand.nextInt(nowCard-1);
		p2 = Deck.get(p2Num).img;
		Deck.remove(p2Num);
		nowCard -= 1;

		int s1Num = rand.nextInt(nowCard-1);
		s1 = Deck.get(s1Num).img;
		Deck.remove(s1Num);
		nowCard -= 1;

		int s2Num = rand.nextInt(nowCard-1);
		s2 = Deck.get(s2Num).img;
		Deck.remove(s2Num);
		nowCard -= 1;

	}

	public void paint(Graphics g) {
		g.drawImage(site, 150, 200, null);
		g.drawImage(p1, 155, 205, null);
		g.drawImage(p2, 495, 205, null);
		g.drawImage(s1, 770, 205, null);
		g.drawImage(s2, 1110, 205, null);
		
	}

	public void reload() {
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 4; j++) {
				Deck.add(j + 4 * i, new Card(i));
			}
		}
		nowCard = 40;
	}
}