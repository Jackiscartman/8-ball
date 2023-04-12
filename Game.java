import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.event.*; 
import javax.sound.sampled.*;
import java.io.*;

public class Game  extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener{

	
	private BufferedImage back; 
	private int key; 
	private ImageIcon background;	
	private Stick stick;
	private Ball cueBall;
	private ArrayList<Ball> gameBalls;	
	
	public Game() {
		new Thread(this).start();	
		this.addKeyListener(this);
		key =-1; 
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		background = new ImageIcon("C:\\Users\\S1757642\\pool table.jpg");		
		stick = new Stick();;
		cueBall = new Ball();
		gameBalls = setGameBalls();
	}

	private ArrayList<Ball> setGameBalls()
	{
		ArrayList <Ball> temp = new ArrayList();
		int size = 40, cols = 5, k = 0;

		for (int j = 0; j < cols; j++) {
			for (int i = 0; i <= j; i++) {
				temp.add(new Ball(900+j*size, 325+i*size-j*size/2, k++));
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
	

	
	
	
	public void paint(Graphics g){
		
		Graphics2D twoDgraph = (Graphics2D) g; 
		if( back == null)
			back=(BufferedImage)( (createImage(getWidth(), getHeight()))); 
		
		Graphics g2d = back.createGraphics();
		g2d.clearRect(0,0,getSize().width, getSize().height);
		g2d.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
		
		g2d.setFont( new Font("Broadway", Font.BOLD, 50));
		
		//g2d.drawString("key " + key, 340, 100);
		//g2d.drawImage(stick.getStickImg().getImage(), stick.getX(), stick.getY(), stick.getW(), stick.getH(), this);
		g2d.drawImage(cueBall.getBallImg().getImage(), cueBall.getX(), cueBall.getY(), cueBall.getW(), cueBall.getH(), this);
		
		if (stick.setFirst && stick.setSecond) {
			//g2d.drawLine(stick.x1, stick.y1, stick.x2, stick.y2);
			drawThickLine(g2d, stick.x1, stick.y1, stick.x2, stick.y2);
		}
		
		for (Ball b : gameBalls) {
			g2d.drawImage(b.getBallImg().getImage(), b.getX(), b.getY(), b.getW(), b.getH(), this);
		}		
		
		twoDgraph.drawImage(back, null, 0, 0);
		//double angle = Math.atan2((cueBall.getY()+cueBall.getH()/2)-(stick.getY()+stick.getH()/2), cueBall.getX()-stick.getX());
		//if (stick.setFirst && stick.setSecond) twoDgraph.rotate(stick.angle());
		//twoDgraph.rotate(angle);
		//twoDgraph.drawImage(stick.getStickImg().getImage(), stick.getX(), stick.getY(), stick.getW(), stick.getH(), this);
	}

	
	public static void drawThickLine(Graphics g, int x1, int y1, int x2, int y2) {
        float length = (float) Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
        float dx = (x2-x1)/length;
        float dy = (y2-y1)/length;
        float xpos = x1;
        float ypos = y1;
        
        for (int i = 0; i < length; i++) {
            drawDot(g, (int) xpos, (int) ypos);
            xpos += dx;
            ypos += dy;
        }
        drawDot(g, x2, y2);
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
		
		//stick.setYdos(y-stick.getH()/2);
		//stick.setXdos(x);
	}



	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		int y = e.getY();
		int x = e.getX();
		
		//stick.setYdos(y);
		//stick.setXdos(x);
	}



	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (!stick.setFirst) { 
			stick.SetFirst(e.getX(), e.getY());
			stick.setFirst = true;
			return;
		}
		if (!stick.setSecond) { 
			stick.SetSecond(e.getX(), e.getY());
			stick.setSecond = true;
			return;
		}
		
		if (stick.setFirst && stick.setSecond) {
			stick.setFirst = false;
			stick.setSecond = false;
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