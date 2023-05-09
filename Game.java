import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.event.*; 
import javax.sound.sampled.*;
import java.io.*;

public class Game  extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener {
	private BufferedImage back; 
	private int key; 
	private ImageIcon background;	
	private Stick stick;
	private Ball cueBall;
	private ArrayList<Ball> gameBalls;	
	private Table table;
	
	public Game() {
		new Thread(this).start();	
		this.addKeyListener(this);
		key = -1; 
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		background = new ImageIcon("img\\pool table 2.jpg");		
		table = new Table(110, 1550, 130, 950);
		stick = new Stick();
		cueBall = new Ball(table);
		gameBalls = setGameBalls();
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
		
	public void paint(Graphics g) {	
		Graphics2D twoDgraph = (Graphics2D) g; 
		if( back == null)
			back=(BufferedImage)( (createImage(getWidth(), getHeight()))); 
		
		Graphics g2d = back.createGraphics();
		g2d.clearRect(0,0,getSize().width, getSize().height);
		g2d.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
		
		g2d.setFont( new Font("Broadway", Font.BOLD, 50));
		
		g2d.drawImage(cueBall.getBallImg().getImage(), cueBall.getX(), cueBall.getY(), cueBall.getW(), cueBall.getH(), this);

		if (stick.firstPtSet) {
			g2d.drawString("first point set", 100, 50);
			if (stick.secondPtSet) g2d.drawString("second point set", 1100, 50);
		}
		
		//g2d.drawLine(table.getXL(), table.getYL(), table.getXU(), table.getYL());
		//g2d.drawLine(table.getXU(), table.getYL(), table.getXU(), table.getYU());
		//g2d.drawLine(table.getXU(), table.getYU(), table.getXL(), table.getYU());
		//g2d.drawLine(table.getXL(), table.getYU(), table.getXL(), table.getYL());

		drawThickLine(g2d, stick, cueBall);
		
		if (stick.ballCollision(cueBall) || cueBall.isMoving()) {
			cueBall.moveXY(10, 10);			
		}
		
		for (Ball b : gameBalls) {
			if (b.ballCollision(cueBall)) {
				b.moveXY(10, 10);
			}
			g2d.drawImage(b.getBallImg().getImage(), b.getX(), b.getY(), b.getW(), b.getH(), this);
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
				dy = (int)(10*Math.abs(stick.slope()))/a;
				
				if (stick.x2 < stick.x1) dx = -dx;
				if (stick.y2 < stick.y1) dy = -dy;
			}
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
			}
		}
		if (key == 88) { //x = reset
			stick.firstPtSet = false;
			stick.secondPtSet = false;			
			stick.MakeMove(false);
			cueBall.setMove(false);
		}
		if(key == 37) {
			stick.setX(-25);
		}
		
		if(key == 39) {
			stick.setX(25);
		}		
		if(key==40) {
			stick.setY(25);
		}
		if(key==38) {
			stick.setY(-25);
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