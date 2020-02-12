package model;

public class PieceLocationModel {
	private int x, y;
	
	public PieceLocationModel(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public String toString() {
		return "Location x: " + x + "\nLocation y: " + y;
	}
}
