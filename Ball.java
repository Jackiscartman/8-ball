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
	private long moveTime, maxTime;
	private ImageIcon ballImg;
	private boolean move;
	private Table table;
	
	public Ball(Table tab) {
		table = tab;
		x = table.getXL()+(table.getXU()-table.getXL())/3;
		y = (table.getYL()+table.getYU())/2;
		w = 40;
		h = 40;
		ballImg = new ImageIcon("img\\cue ball.png");
		move = false;
		moveTime = 0;
		maxTime = 2000;
	}

	public Ball(int xx, int yy, int i, Table tab) {
		x = xx;
		y = yy;
		w = 40;
		h = 40;
		ballImg = new ImageIcon("img\\ball"+(i+1)+".png");
		move = false;
		table = tab;
		moveTime = 0;
		maxTime = 2000;
	}

	public void SetDirX(int xx) { dirX = xx; }
	public void SetDirY(int yy) { dirY = yy; }
	public int GetDirX() { return dirX; }
	public int GetDirY() { return dirY; }
	
	public void SetMoveTime( ) { moveTime = System.currentTimeMillis(); }
	public void setMove(boolean x) { move = x; }
	public boolean isMoving() { return move; }
	
	public int getX() { return x; }
	public int getY() { return y; }	
	
	public void moveXY(int xx, int yy) {
		if (x+xx < table.getXL() || x+xx > table.getXU()) {
			dirX = -dirX;
		}
		if (y+yy < table.getYL() || y+yy > table.getYU()) {
			dirY = -dirY;
		}
		x += dirX*xx;
		y += dirY*yy;
		if (System.currentTimeMillis()-moveTime > maxTime) {
			moveTime = 0;
			move = false;
		}
	}
	
	public int getH() { return h; }	 
	public int getW() { return w; }	
	
	public ImageIcon getBallImg() { return ballImg; }
	
	public boolean ballCollision(Ball cue) {
		Rectangle cue_ball = new Rectangle(cue.getX(), cue.getY(), cue.getW(), cue.getH());
		Rectangle me  = new Rectangle(x, y, getW(), getH());
		
		if (cue_ball.intersects(me)) {
			move = true;
			SetMoveTime();
			dirX = cue.GetDirX();
			dirY = cue.GetDirY();
			return true;
		}
		return false;
	}		
}
