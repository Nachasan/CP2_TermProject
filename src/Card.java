import java.awt.Graphics;
import java.io.File;

import psd.model.Psd;

// 카드 크기 234*323

class Card {
	int cardNumber;
	Psd img = null;

	public Card(int cardNumber) {
		this.cardNumber = cardNumber;
		setImage(img, cardNumber);
	}

	public Psd setImage(Psd img, int cardNumber) {
		switch (cardNumber) {
		case 1:
			try {
				img = new Psd(new File("one.psd"));
			} catch (Exception e) {
			}
			break;
		case 2:
			try {
				img = new Psd(new File("two.psd"));
			} catch (Exception e) {
			}
			break;
		case 3:
			try {
				img = new Psd(new File("three.psd"));
			} catch (Exception e) {
			}
			break;
		case 4:
			try {
				img = new Psd(new File("four.psd"));
			} catch (Exception e) {
			}
			break;
		case 5:
			try {
				img = new Psd(new File("five.psd"));
			} catch (Exception e) {
			}
			break;
		case 6:
			try {
				img = new Psd(new File("six.psd"));
			} catch (Exception e) {
			}
			break;
		case 7:
			try {
				img = new Psd(new File("seven.psd"));
			} catch (Exception e) {
			}
			break;
		case 8:
			try {
				img = new Psd(new File("eight.psd"));
			} catch (Exception e) {
			}
			break;
		case 9:
			try {
				img = new Psd(new File("nine.psd"));
			} catch (Exception e) {
			}
			break;
		case 10:
			try {
				img = new Psd(new File("ten.psd"));
			} catch (Exception e) {
			}
			break;
		}
		return img;
	}

	public void paint(Graphics g) {

	}
}
