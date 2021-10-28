import java.util.ArrayList;

public class Ship {
	
	private static boolean FIRST_SHIP = true;
	
	private int size;
	private int health;
	private boolean destroyed;
	private ArrayList<Cell> points;
	
	public Ship(int size) {
		this.size = size;
		this.health = size;
		this.destroyed = false;
		this.points = new ArrayList<Cell>();
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public void damaged() {
		this.health--;
		if(this.health == 0)
			this.destroyed = true;
	}
	
	public void addPoint(Cell c) {
		this.points.add(c);
	}
	
	public ArrayList<Cell> getPoints() {
		return points;
	}
	
	public boolean isDestroyed() {
		return this.destroyed;
	}

	public boolean checkCells(Cell c) { //checks if a cell belongs to a ship object
		if(this.points != null && this.points.size() > 0) {
			for(Cell point: this.points) {
				if(point.getX() == c.getX() && point.getY() == c.getY()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static void shipType(int size) {
		System.out.print("You sunk ");
		switch(size) {
			case 5: System.out.println("CARRIER");
					break;
			case 4: System.out.println("DESTROYER");
					break;
			case 3: if(FIRST_SHIP) {
						System.out.println("SUBMARINE");
						FIRST_SHIP = false;
					} else {
						System.out.println("BATTLESHIP");
						FIRST_SHIP = true;
					}
					break;
			case 2: System.out.println("PATROL BOAT");
					break;
		}
		
	}
	
}
