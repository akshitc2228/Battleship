class Cell {
	
	private int x, y;
	private boolean occupied;
	private char direction = 'O'; // N,S,E,W, O-> for none
	
	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Cell(int x, int y, char dir) {
		this.x = x;
		this.y = y;
		this.direction = dir;
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
	public boolean isOccupied() {
		return occupied;
	}
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

	public char getDirection() {
		return direction;
	}

	public void setDirection(char direction) {
		this.direction = direction;
	}
	
}
