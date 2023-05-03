import java.awt.Rectangle;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

public class Ball {
	private int x, y, w, h;
	private int dx, dy;
	private int dirX, dirY;
	private ImageIcon ballImg;
	private boolean move;
	
	public Ball() {
		x = 600;
		y = 325;
		w = 40;
		h = 40;
		ballImg = new ImageIcon("C:\\Users\\S1757642\\cue ball.png");
		move = false;
	}

	public Ball(int xx, int yy, int i) {
		x = xx;
		y = yy;
		w = 40;
		h = 40;
		ballImg = new ImageIcon("C:\\Users\\S1757642\\ball"+(i+1)+".png");
		move = false;
	}

	public void SetDirX(int xx) { dirX = xx; }
	public void SetDirY(int yy) { dirY = yy; }
	
	public void setMove(boolean x) { move = x;}
	public boolean isMoving() { return move; }
	
	public int getX() { return x; }
	public int getY() { return y; }	
	
	public void moveXY(int xx, int yy) {
		if (x+xx < 100 || x+xx > 1260) {
			dirX = -dirX;
		}
		if (y+yy < 130 || y+yy > 530) {
			dirY = -dirY;
		}
		x += dirX*xx;
		y += dirY*yy;
	}
	
	public int getH() { return h; }	 
	public int getW() { return w; }	
	
	public ImageIcon getBallImg() { return ballImg; }
}
