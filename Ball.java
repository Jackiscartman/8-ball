import java.awt.Rectangle;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

public class Ball {
	private int x, y, w, h;
	private int dx, dy;
	private ImageIcon ballImg;
	
	public Ball() {
		x = 600;
		y = 325;
		w = 40;
		h = 40;
		ballImg = new ImageIcon("C:\\Users\\S1757642\\cue ball.png");
	}

	public Ball(int xx, int yy, int i) {
		x = xx;
		y = yy;
		w = 40;
		h = 40;
		ballImg = new ImageIcon("C:\\Users\\S1757642\\ball"+(i+1)+".png");
	}

	public int getX() { return x; }

	public int getY() { return y; }	

	public int getH() { return h; }
	 
	public int getW() { return w; }	
	
	public ImageIcon getBallImg() { return ballImg; }
}
