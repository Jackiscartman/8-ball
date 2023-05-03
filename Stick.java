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
	private float length;
	private ImageIcon stickImg;
	public boolean firstPtSet, secondPtSet;
	private boolean move;
	
	public Stick() {
		length = 200;
		x = 200;
		y = 250;
		w = 1;
		h = 1;
		stickImg = new ImageIcon("C:\\Users\\S1757642\\pool stick.png");
		firstPtSet = false;
		secondPtSet = false;
		move = false;
	}
	
//use for the player stick
	public Stick(int xV, int yV, ImageIcon s) {
		x = xV;
		y = yV;
		w = 1;
		h = 1;
		stickImg = s;
		dx = 0;
		dy = 0;
	}
	
	public void SetFirst(int xx, int yy) {
		x1 = xx;
		y1 = yy;
		firstPtSet = true;
	}

	public void SetSecond(int xx, int yy) {
		if (xx == x1) {
			x2 = x1;
			
			if (yy > y1) y2 = y1+(int)length;
			else y2 = y1-(int)length;
			
			return;
		}
		float slope = (float)(yy-y1)/(float)(xx-x1);
		float adjX = length/(float)Math.sqrt(1+slope*slope);
		float adjY = adjX*slope;
		
		if (xx < x1) {
			adjX *= -1;
			adjY *= -1;
		}
		x2 = x1+(int)(adjX); 
		y2 = y1+(int)adjY;
		//x2 = xx;
		//y2 = yy;
		secondPtSet = true;
	}

	public void MakeMove(boolean x) { move = x; }
	public boolean IsMoving() { return move; }
	
	public double slope() { return (double)(y2-y1)/(double)(x2-x1); }
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
	
	public boolean ballCollision(Ball b) {
		Rectangle bb = new Rectangle(b.getX(), b.getY(), b.getW(), b.getH());
		Rectangle stk  = new Rectangle(x2, y2, getW(), getH());
		
		if (bb.intersects(stk)) {
			b.setMove(true);
			b.SetDirX(x2 > x1 ? 1 : -1);
			b.SetDirY(y2 > y1 ? 1 : -1);
			move = false;
			return true;
		}
		return false;
	}		
}
