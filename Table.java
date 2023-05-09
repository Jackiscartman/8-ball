
public class Table {
	private int xL, xU, yL, yU;
	
	public Table(int x1, int x2, int y1, int y2) {
		xL = x1;
		xU = x2;
		yL = y1;
		yU = y2;
	}
	
	public int getXL() { return xL; }
	public int getXU() { return xU; }
	public int getYL() { return yL; }
	public int getYU() { return yU; }
}
