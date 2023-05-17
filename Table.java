import java.util.ArrayList;

public class Table {
	private int xL, xU, yL, yU;
	private ArrayList<Point> pockets;	

	
	public Table(Point pL, Point pU) {
		xL = pL.x;
		xU = pU.x;
		yL = pL.y;
		yU = pU.y;

		pockets = new ArrayList<Point>();
		pockets.add(new Point(xL, yL));
		pockets.add(new Point((xL+xU)/2, yL));
		pockets.add(new Point(xU, yL));
		pockets.add(new Point(xL, yU));
		pockets.add(new Point((xL+xU)/2, yU));
		pockets.add(new Point(xU, yU));
	}
	
	public int getXL() { return xL; }
	public int getXU() { return xU; }
	public int getYL() { return yL; }
	public int getYU() { return yU; }
	
	public Point getPocket(int i) { return pockets.get(i); }
	public int numPockets() { return pockets.size(); }
}
