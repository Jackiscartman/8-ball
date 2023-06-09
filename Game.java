import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.event.*; 
import javax.sound.sampled.*;
import java.io.*;

public class Game  extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener {
	private BufferedImage back; 
	private int key, score1, score2, winning, playerTurn;  
	private ImageIcon background;	
	private Stick stick;
	private Ball cueBall;
	private ArrayList<Ball> gameBalls;	
	private Table table;
	private boolean gameOver;

	public Game() {
		new Thread(this).start();	
		this.addKeyListener(this);
		key = -1; 
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		playerTurn = 1;
		winning = 7;
		score1 = 0;
		score2 = 0;
		background = new ImageIcon("img\\pool table 2.jpg");
		//need to change to fit monitor resolution
		table = new Table(new Point(110, 120), new Point(1550, 900));
		//table = new Table(new Point(100, 100), new Point(1440, 730));
		stick = new Stick();
		cueBall = new Ball(table);
		gameBalls = setGameBalls();
		gameOver = false;
	}

	private ArrayList<Ball> setGameBalls() {
		ArrayList <Ball> temp = new ArrayList();
		int size = 40, cols = 5, k = 0;
		int x0 = table.getXL()+2*(table.getXU()-table.getXL())/3;
		int y0 = (table.getYL()+table.getYU())/2;

		for (int j = 0; j < cols; j++) {
			for (int i = 0; i <= j; i++) {
				temp.add(new Ball(x0+j*size, y0+i*size-j*size/2, k++, table));
			}
		}
		return temp;		
	}
		
	public void run() {
		try {
			while(true) {
				Thread.currentThread().sleep(5);
				repaint();
			}
		}
		catch(Exception e) {}
	}

	public void playmusic(String musicfile) {
		File soundFile = new File(musicfile);
		try {
			Clip clip = AudioSystem.getClip();
			AudioInputStream inputStream= AudioSystem.getAudioInputStream(soundFile);
			clip.open(inputStream);
			//clip.loop(clip.LOOP_CONTINUOUSLY);
			clip.start();
		}
		catch(Exception e) { System.out.println(e); }
	}

	public void paint(Graphics g) {	
		Graphics2D twoDgraph = (Graphics2D) g; 
		if( back == null)
			back =(BufferedImage)( (createImage(getWidth(), getHeight()))); 
		
		Graphics g2d = back.createGraphics();
		g2d.clearRect(0,0,getSize().width, getSize().height);
		g2d.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
		
		g2d.setFont( new Font("Broadway", Font.BOLD, 25));
		
		g2d.drawImage(cueBall.getBallImg().getImage(), cueBall.getX(), cueBall.getY(), cueBall.getW(), cueBall.getH(), this);

		g2d.drawString("player "+Integer.toString(playerTurn)+"'s turn", 500, 50);
		g2d.drawString("player 1 (solid): "+Integer.toString(score1), 200, 50);
		g2d.drawString("player 2 (stripes): "+Integer.toString(score2), 1000, 50);

		g2d.drawLine(table.getXL(), table.getYL(), table.getXU(), table.getYL());
		g2d.drawLine(table.getXU(), table.getYL(), table.getXU(), table.getYU());
		g2d.drawLine(table.getXU(), table.getYU(), table.getXL(), table.getYU());
		g2d.drawLine(table.getXL(), table.getYU(), table.getXL(), table.getYL());

		if (!gameOver) {
			if (stick.firstPtSet) {
				g2d.drawString("first point set", 300, 100);
				if (stick.secondPtSet) g2d.drawString("second point set", 900, 100);
			}
			drawThickLine(g2d, stick, cueBall);
			
			if (stick.ballCollision(cueBall) || cueBall.isMoving()) {
				cueBall.moveXY(5, 5);		
			}
			if (cueBall.IsSunk()) {
				g2d.drawString("cue ball sunk", 500, 50);
				ResetGame();
				cueBall.PutInCenter();
			}
			
			for (Ball b : gameBalls) {
				if (b.ballCollision(cueBall, gameBalls) || b.isMoving()) {
					b.moveXY(5, 5);
					
					if (b.IsSunk()) {
						b.Erase();
						if (b.getIndex() == 8) gameOver = true;
						else if (b.getIndex() <= 7) score1++;
						else score2++;
					}
				}
				g2d.drawImage(b.getBallImg().getImage(), b.getX(), b.getY(), b.getW(), b.getH(), this);
			}		

			if (score1 >= winning || score2 >= winning) {
				if (score1 >= winning) {
					g2d.drawString("Player 1 Wins, Player 2 Loses!", 400, 500);						
				}
				else {
					g2d.drawString("Player 2 Wins, Player 1 Loses!", 400, 500);						
				}
				ResetGame();
			}
		}
		else {
			SwitchPlayer();
			g2d.drawString("eight ball sunk, game over, player "+Integer.toString(playerTurn)+" loses", 400, 500);
			gameOver = true;
		}
		
		twoDgraph.drawImage(back, null, 0, 0);
	}

	public static int gcd(int a, int b) //from stackoverflow.com
	{
		return b == 0 ? a : gcd(b, a % b);
	}
	
	public static void drawThickLine(Graphics g, Stick stick, Ball cue) {
		if (!stick.firstPtSet || !stick.secondPtSet) return;
		
		if (stick.IsMoving()) {
			int dx = 0, dy = 0;
			
			if (!stick.ballCollision(cue)) {
				int a = gcd(10, (int)(10*Math.abs(stick.slope())+0.5));
				dx = 10/a;
				dy = (int)(10*Math.abs(stick.slope())+0.5)/a;
				
				if (stick.x2 < stick.x1) dx = -dx;
				if (stick.y2 < stick.y1) dy = -dy;
			}
			stick.setDir(dx, dy);
			stick.x1 += dx;
			stick.x2 += dx;
			stick.y1 += dy;
			stick.y2 += dy;
		}

		float length = (float) Math.sqrt((stick.x2-stick.x1)*(stick.x2-stick.x1)+(stick.y2-stick.y1)*(stick.y2-stick.y1));
        float dx = (stick.x2-stick.x1)/length;
        float dy = (stick.y2-stick.y1)/length;
        float xpos = stick.x1;
        float ypos = stick.y1;
        
        for (int i = 0; i < length; i++) {
            drawDot(g, (int) xpos, (int) ypos);
            xpos += dx;
            ypos += dy;
        }
        drawDot(g, stick.x2, stick.y2);
    }

    public static void drawDot(Graphics g, int x, int y) {
        g.drawLine(x-2, y-2, x, y-2);
        g.drawLine(x-2, y, x, y);
    }

    public void ResetGame() {
		stick.firstPtSet = false;
		stick.secondPtSet = false;			
		stick.MakeMove(false);
		cueBall.setMove(false);    	
    }
    
    public void SwitchPlayer()
    {
    	if (gameOver) return;
    	
    	if (playerTurn == 1) playerTurn = 2;
    	else playerTurn = 1;
    }
    
	//DO NOT DELETE
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

//DO NOT DELETE
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		key= e.getKeyCode();
		System.out.println(key);
		
		if (key == 83) { //s = shoot 
			if (stick.firstPtSet && stick.secondPtSet) {
				stick.MakeMove(true);
				playmusic("img\\poolballhit.wav");
				SwitchPlayer();
			}
		}
		if (key == 88) { //x = reset
			ResetGame();
		}
		if (key == 80) { //p = put cue ball back
			cueBall.PutInCenter();
		}
		if (key == 65) { //a = play again
			cueBall.PutInCenter();
			score1 = 0;
			score2 = 0;
			gameOver = false;
			gameBalls.clear();
			gameBalls = setGameBalls();
		}
	}


	//DO NOT DELETE
	@Override
	public void keyReleased(KeyEvent e) {
	}
		
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		int y = e.getY();
		int x = e.getX();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		int y = e.getY();
		int x = e.getX();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (!stick.firstPtSet) { 
			stick.SetFirst(e.getX(), e.getY());
		}
		else {
			if (!stick.secondPtSet) { 
				stick.SetSecond(e.getX(), e.getY());
			}					
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}	
}