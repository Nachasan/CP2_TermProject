
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Data {
	ArrayList<Card> Deck = new ArrayList<>();
	BufferedImage p1, p2, s1, s2;
	int nowCard, p1Coin, p2Coin, nowCoin, deal, turn, dealedCoin, beforeDealedCoin;
	int p1CardNum, p2CardNum, s1CardNum, s2CardNum;
	Boolean canExit, booking, change, temp;
	Random rand = new Random();

	public void playing() throws IOException {
		if (nowCard == 0) {
			reload();
			nowCard = 40;
		}
		beforeDealedCoin = 0;
		temp = true;

		int p1Num = rand.nextInt(nowCard - 1);
		p1 = Deck.get(p1Num).img;
		p1CardNum = Deck.get(p1Num).cardNumber;
		Deck.remove(p1Num);
		nowCard -= 1;

		int p2Num = rand.nextInt(nowCard - 1);
		p2 = Deck.get(p2Num).img;
		p2CardNum = Deck.get(p2Num).cardNumber;
		Deck.remove(p2Num);
		nowCard -= 1;

		int s1Num = rand.nextInt(nowCard - 1);
		s1 = Deck.get(s1Num).img;
		s1CardNum = Deck.get(s1Num).cardNumber;
		Deck.remove(s1Num);
		nowCard -= 1;

		int s2Num = 0;
		if (nowCard != 1)
			s2Num = rand.nextInt(nowCard - 1);
		s2 = Deck.get(s2Num).img;
		s2CardNum = Deck.get(s2Num).cardNumber;
		Deck.remove(s2Num);
		nowCard -= 1;
		
		
	}

	public void reload() throws IOException {
		for (int i = 1; i < 11; i++) {
			for (int j = 0; j < 4; j++) {
				Deck.add(j + 4 * (i - 1), new Card(i));
			}
		}
		nowCard = 40;
	}

	public void setNowCoin(int nc) {
		nowCoin = nc;
	}

	public void set1Coin(int c1) {
		p1Coin = c1;
	}

	public void set2Coin(int c2) {
		p2Coin = c2;
	}

	public void setDeal(int d) {
		deal = d;
	}

	public void setTurn(int t) {
		turn = t;
	}

	public void setDealedCoin(int dc) {
		dealedCoin = dc;
	}

	public void setBefDealedCoin(int bdc) {
		beforeDealedCoin = bdc;
	}

	public void setCanEx(boolean ce) {
		canExit = ce;
	}

	public void setBook(boolean b) {
		booking = b;
	}

	public void setChange(boolean c) {
		change = c;
	}

	public void setTemp(boolean t) {
		temp = t;
	}
}
