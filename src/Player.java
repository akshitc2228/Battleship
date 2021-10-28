import java.util.ArrayList;

public class Player {
	
	public static ArrayList<Ship> FLEET_OF_SHIPS = new ArrayList<>();
	
	static {
		Ship CARRIER = new Ship(5);
		Ship DESTROYER = new Ship(4);
		Ship SUBMARINE = new Ship(3);
		Ship BATTLESHIP = new Ship(3);
		Ship BOAT = new Ship(2);
		FLEET_OF_SHIPS.add(CARRIER);
		FLEET_OF_SHIPS.add(DESTROYER);
		FLEET_OF_SHIPS.add(SUBMARINE);
		FLEET_OF_SHIPS.add(BATTLESHIP);
		FLEET_OF_SHIPS.add(BOAT);
		
	}
	
	private ArrayList<Ship> fleet;
 	
	public Player() {
		this.fleet = new ArrayList<>();
	}

	public void addShipToFleet(Ship ship) {
		this.fleet.add(ship);
	}
	
	public ArrayList<Ship> getFleet() {
		return new ArrayList<Ship>(fleet);
	}
	
	public Ship getCurrentShip() { //returns latest added ship
		if(this.fleet != null && this.fleet.size() > 0) {
			return this.fleet.get(this.fleet.size()-1);
		} else
			return null;
	}
	
	public Ship getAttackedShip(Cell c) {
		if(this.fleet != null && this.fleet.size() > 0) {
			for(Ship s: this.fleet) {
				boolean isOcc = s.checkCells(c); //if given cell is a point of a ship then that ship object is returned 
				if(isOcc) {
					return s;
				}
			}
		}
		return null;
	}
	
	public void removeShip(Ship s) {
		fleet.remove(s);
	}
	
	public void resetFleet() {
		this.fleet = new ArrayList<Ship>(); 
	}
 
}
