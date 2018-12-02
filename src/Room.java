import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JPanel;
import com.idrsolutions.image.psd.PsdDecoder;

public class Room extends JPanel {
	ArrayList<Card> Deck = new ArrayList<>();
	Deck d = new Deck();
	JPanel p;
	PsdDecoder pd = new PsdDecoder();
	BufferedImage site;

	public Room() {

		try {
			site = pd.read(new File("site1.psd"));
		} catch (Exception e) {
		}
	}

	public void paint(Graphics g) {
		g.drawImage(site, 150, 200, null);
	}

	public void reload() {
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 4; j++) {
				Deck.add(j + 4 * i, new Card(i));
			}
		}
	}
}