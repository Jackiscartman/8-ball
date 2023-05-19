import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

public class Ball {
	private int x, y, w, h, index;
	private int dx, dy;
	private int dirX, dirY;
	private long moveTime, maxTime;
	private ImageIcon ballImg;
	private boolean move, isSunk;
	private Table table;
	
	public Ball(Table tab) {
		table = tab;
		PutInCenter();
		w = 40;
		h = 40;
		ballImg = new ImageIcon("img\\cue ball.png");
		move = false;
		moveTime = 0;
		maxTime = 1000;
	}

	public Ball(int xx, int yy, int i, Table tab) {
		x = xx;
		y = yy;
		w = 40;
		h = 40;
		index = i+1;
		ballImg = new ImageIcon("img\\ball"+(i+1)+".png");
		move = false;
		isSunk = false;
		table = tab;
		moveTime = 0;
		maxTime = 1000;
	}

	public int getIndex() { return index; }
	
	public void PutInCenter() {
		x = table.getXL()+(table.getXU()-table.getXL())/3;
		y = (table.getYL()+table.getYU())/2;		
		isSunk = false;
	}
	
	public void Erase()
	{
		w = 0;
		h = 0;
	}
	
	public void SetDirX(int xx) { dirX = xx; }
	public void SetDirY(int yy) { dirY = yy; }
	public int GetDirX() { return dirX; }
	public int GetDirY() { return dirY; }
	
	public void SetMoveTime( ) { moveTime = System.currentTimeMillis(); }
	public void setMove(boolean x) { move = x; }
	public boolean isMoving() { return move; }
	public boolean IsSunk() { return isSunk; }
	public void SetSunk(boolean x) { isSunk = x; }
	
	public int getX() { return x; }
	public int getY() { return y; }	
	
	private boolean CheckSunk(int xx, int yy)
	{
		int deadZone = 30;

		for (int i = 0; i < table.numPockets(); i++) {
			if (table.getPocket(i).x-deadZone < xx && xx < table.getPocket(i).x+deadZone && 
					table.getPocket(i).y-deadZone < yy && yy < table.getPocket(i).y+deadZone)
				return true;
		}
		return false;
	}
	
	public void moveXY(int xx, int yy) {
		if (x+dirX*xx < table.getXL() || x+dirX*xx+w > table.getXU()) {
			dirX = -dirX;
		}
		if (y+dirY*yy < table.getYL() || y+dirY*yy+h > table.getYU()) {
			if (CheckSunk(x+xx, y+yy) || CheckSunk(x+xx+w, y+yy) || CheckSunk(x+xx, y+yy+h) || CheckSunk(x+xx+w, y+yy+h)) {
				move = false;
				isSunk = true;
				return;
			}
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
	
	private int randomDir(double ran)
	{
		if (ran < 0.2) { return -1; }
		else if (ran < 0.8) { return 0; }
		else { return 1; }
	}
	
	public boolean ballCollision(Ball b) {
		Rectangle ball = new Rectangle(b.getX(), b.getY(), b.getW(), b.getH());
		Rectangle me  = new Rectangle(x, y, getW(), getH());
		
		if (ball.intersects(me)) {
			move = true;
			SetMoveTime();
			dirX = b.GetDirX()+randomDir(Math.random());
			dirY = b.GetDirY()+randomDir(Math.random());
			return true;
		}
		move = false;
		return false;
	}		

	public boolean ballCollision(Ball cue_ball, ArrayList<Ball> gameBalls) {
		Rectangle ball = new Rectangle(cue_ball.getX(), cue_ball.getY(), cue_ball.getW(), cue_ball.getH());
		Rectangle me  = new Rectangle(x, y, getW(), getH());
		
		if (ball.intersects(me)) {
			move = true;
			SetMoveTime();
			dirX = cue_ball.GetDirX();
			dirY = cue_ball.GetDirY();
			return true;
		}
		for (Ball b : gameBalls) {
			if (b != this) {
				if (ballCollision(b)) return true;
			}
		}
		move = false;
		return false;
	}		
}
