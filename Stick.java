import java.awt.Rectangle;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

public class Stick {
	private int x, y, w, h;
	private int dx, dy;
	public int x1, y1, x2, y2;
	private ImageIcon stickImg;
	public boolean setFirst, setSecond;
	
	public Stick() {
		x = 200;
		y = 250;
		w = 300;
		h = 200;
		stickImg = new ImageIcon("C:\\Users\\S1757642\\pool stick.png");
		setFirst = false;
		setSecond = false;
	}
	
//use for the player stick
	public Stick(int xV, int yV, ImageIcon s) {
		x = xV;
		y = yV;
		w = 50;
		h = 50;
		stickImg = s;
		dx = 0;
		dy = 0;
	}
	
	public void SetFirst(int xx, int yy)
	{
		x1 = xx;
		y1 = yy;
	}

	public void SetSecond(int xx, int yy)
	{
		x2 = xx;
		y2 = yy;
	}

	public double angle() { return Math.atan2(y2-y1, x2-x1); }
	
	public void setX(int xV) { x += xV;	}
	
	public void setY(int yV) { y += yV;	}
	
	public void setW(int wV) { w += wV;	}
	
	public void setH(int hV) { h += hV; }
	
	public void setXdos(int xV) { x = xV; }
	
	public void setYdos(int yV) { y = yV; }

	public int getX() { return x; }

	public int getY() { return y; }	

	public int getH() { return h; }
	 
	public int getW() { return w; }	
	
	public ImageIcon getStickImg() { return stickImg; }
}
