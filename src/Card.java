import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

// 카드 크기 234*323

class Card {
	int cardNumber;
	BufferedImage img = null;

	public Card(int cardNumber) throws IOException {
		this.cardNumber = cardNumber;
		setImage(cardNumber);
	}

	public void setImage(int cardNumber) throws IOException {
		switch (cardNumber) {
		case 1:
			img = ImageIO.read(new File("one.png"));
			break;
		case 2:
			img = ImageIO.read(new File("two.png"));
			break;
		case 3:
			img = ImageIO.read(new File("three.png"));
			break;
		case 4:
			img = ImageIO.read(new File("four.png"));
			break;
		case 5:
			img = ImageIO.read(new File("five.png"));
			break;
		case 6:
			img = ImageIO.read(new File("six.png"));
			break;
		case 7:
			img = ImageIO.read(new File("seven.png"));
			break;
		case 8:
			img = ImageIO.read(new File("eight.png"));
			break;
		case 9:
			img = ImageIO.read(new File("nine.png"));
			break;
		case 10:
			img = ImageIO.read(new File("ten.png"));
			break;
		}
	}
}
