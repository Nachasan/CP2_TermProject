import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.*;

class Card {
	int cardNumber;
	BufferedImage img = null;

	public Card(int cardNumber) {
		this.cardNumber = cardNumber;
		setImage(img,cardNumber);
	}

	public BufferedImage setImage(BufferedImage img, int cardNumber) {
		switch (cardNumber) {
		case 1:
			BufferedImage one = null;
			try {
				one = ImageIO.read(new File("one.jpg"));
			} catch (IOException e) {
				System.out.println(e.getMessage());
				System.exit(0);
			}
			img = one;
			break;
		case 2:
			BufferedImage two = null;
			try {
				two = ImageIO.read(new File("two.jpg"));
			} catch (IOException e) {
				System.out.println(e.getMessage());
				System.exit(0);
			}
			img = two;
			break;
		case 3:
			BufferedImage three = null;
			try {
				three = ImageIO.read(new File("three.jpg"));
			} catch (IOException e) {
				System.out.println(e.getMessage());
				System.exit(0);
			}
			img = three;
			break;
		case 4:
			BufferedImage four = null;
			try {
				four = ImageIO.read(new File("four.jpg"));
			} catch (IOException e) {
				System.out.println(e.getMessage());
				System.exit(0);
			}
			img = four;
			break;
		case 5:
			BufferedImage five = null;
			try {
				five = ImageIO.read(new File("five.jpg"));
			} catch (IOException e) {
				System.out.println(e.getMessage());
				System.exit(0);
			}
			img = five;
			break;
		case 6:
			BufferedImage six = null;
			try {
				six = ImageIO.read(new File("six.jpg"));
			} catch (IOException e) {
				System.out.println(e.getMessage());
				System.exit(0);
			}
			img = six;
			break;
		case 7:
			BufferedImage seven = null;
			try {
				six = ImageIO.read(new File("seven.jpg"));
			} catch (IOException e) {
				System.out.println(e.getMessage());
				System.exit(0);
			}
			img = seven;
			break;
		case 8:
			BufferedImage eight = null;
			try {
				six = ImageIO.read(new File("eight.jpg"));
			} catch (IOException e) {
				System.out.println(e.getMessage());
				System.exit(0);
			}
			img = eight;
			break;
		case 9:
			BufferedImage nine = null;
			try {
				six = ImageIO.read(new File("nine.jpg"));
			} catch (IOException e) {
				System.out.println(e.getMessage());
				System.exit(0);
			}
			img = nine;
			break;
		case 10:
			BufferedImage ten = null;
			try {
				six = ImageIO.read(new File("ten.jpg"));
			} catch (IOException e) {
				System.out.println(e.getMessage());
				System.exit(0);
			}
			img = ten;
			break;
		}
		return img;
	}

	public void paint(Graphics g) {

	}
}
