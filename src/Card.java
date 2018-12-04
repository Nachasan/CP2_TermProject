import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import com.idrsolutions.image.psd.PsdDecoder;

// 카드 크기 234*323

class Card {
	int cardNumber;
	BufferedImage img = null;
	PsdDecoder pd = new PsdDecoder();

	public Card(int cardNumber) {
		this.cardNumber = cardNumber;
		setImage(cardNumber);
	}

	public void setImage(int cardNumber) {
		switch (cardNumber) {
		case 1:
			try {
				img = pd.read(new File("one.psd"));
			} catch (Exception e) {
			}
			break;
		case 2:
			try {
				img = pd.read(new File("two.psd"));
			} catch (Exception e) {
			}
			break;
		case 3:
			try {
				img =  pd.read(new File("three.psd"));
			} catch (Exception e) {
			}
			break;
		case 4:
			try {
				img =  pd.read(new File("four.psd"));
			} catch (Exception e) {
			}
			break;
		case 5:
			try {
				img =  pd.read(new File("five.psd"));
			} catch (Exception e) {
			}
			break;
		case 6:
			try {
				img =  pd.read(new File("six.psd"));
			} catch (Exception e) {
			}
			break;
		case 7:
			try {
				img = pd.read(new File("seven.psd"));
			} catch (Exception e) {
			}
			break;
		case 8:
			try {
				img = pd.read(new File("eight.psd"));
			} catch (Exception e) {
			}
			break;
		case 9:
			try {
				img = pd.read(new File("nine.psd"));
			} catch (Exception e) {
			}
			break;
		case 10:
			try {
				img = pd.read(new File("ten.psd"));
			} catch (Exception e) {
			}
			break;
		}
	}
}
