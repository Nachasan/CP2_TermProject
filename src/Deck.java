import java.util.ArrayList;

public class Deck {
	ArrayList<Card> deck = new ArrayList<>();
	
	public Deck() {	
		for(int i=1;i<=10;i++) {
			for(int j=0;j<10;j++) {
				deck.add(new Card(i));
			}
		}
	}
	
	public void reroad() {
		for(int i=1;i<=10;i++) {
			for(int j=0;j<10;j++) {
				deck.add(new Card(i));
			}
		}
	}
}
