import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

public class Board implements ActionListener, MouseListener {

	private static JFrame window;
	private static JFrame dbox;
	private static JPanel P1_container;
	private static JPanel main1;
	private static JPanel main2;
	private static JPanel compContainer;
	private static JPanel mainPanel;
	private static JPanel panel;
	private static JPanel[][] grid;
	private static JPanel[][] enemyGrid;
	private static JLabel player, opponent;
	private static JPanel pPoints, ePoints;
	private static JLabel Ppts, Epts;
	
	private static int FRAME_OFFSET = 5;
	private static int FRAME_STEP = 34;
	private static boolean[][] occupied;
	private static boolean[][] enemyOcc;
	
	private static boolean flagInvalid[][];
	private static boolean flagInvalidE[][];
	private static int i;
	private static boolean startAttack = false;

	public static int clickCount;
	public static int shipLength;
	public static int eDestroyCount = 0;
	public static int uDestroyCount = 0;
	public int playerScore = 0;
	public int enemyScore = 0;
	public boolean turn;

	public static ArrayList<Cell> validCells = new ArrayList<>();
	
	public Player p1, comp;
	
	private Cell kraken, cetus;
	
	public void startButton() {
		//Displays a dialogue box that shows the rules of ship placement
		dbox = new JFrame();
		dbox.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dbox.setPreferredSize(new Dimension(300, 300));

		JPanel command = new JPanel();
		JPanel options = new JPanel(new GridBagLayout());
		GridBagConstraints cell = new GridBagConstraints();
		cell.fill = GridBagConstraints.HORIZONTAL;
		cell.gridx = 2;
		cell.gridy = 0;
		cell.insets = new Insets(3,3,3,3);
		
		JLabel message = new JLabel("PLACE YOUR SHIPS IN THE FOLLOWING ORDER:-");
		JLabel airCarrier = new JLabel("5 squares: Aircraft Carrier");
		JLabel destroyer = new JLabel("4 squares: Destroyer");
		JLabel sub = new JLabel("3 squares: Submarine");
		JLabel bship = new JLabel("3 squares: Battleship");
		JLabel boat = new JLabel("2 squares: Patrol Boat");

		command.add(message, BorderLayout.CENTER);
		options.add(airCarrier, cell);
		cell.gridy = 1;
		
		options.add(destroyer, cell);
		cell.gridy = 2;
		
		options.add(sub, cell);
		cell.gridy = 3;
		
		options.add(bship, cell);
		cell.gridy = 4;
		
		options.add(boat, cell);
		cell.gridy = 5;
		
		JButton ok = new JButton("ok");
		ok.setPreferredSize(new Dimension(20, 20));
		options.add(ok, cell);
		cell.gridy = 8;
		
		command.add(options, BorderLayout.SOUTH);

		ok.addActionListener(this);

		dbox.add(command, BorderLayout.CENTER);
		dbox.pack();
		dbox.setVisible(true);
		
		p1 = new Player();
		comp = new Player();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		dbox.setVisible(false);
		createGrid();
	}
	
	public void createGrid() {
		//Sets the game board containing the player and the enemy grids
		i = clickCount = 0;
		window = new JFrame();
		window.setTitle("Battleship.exe");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setPreferredSize(new Dimension(900, 500));

		P1_container = new JPanel(new GridLayout(10, 10));
		P1_container.setPreferredSize(new Dimension(350, 350));
		P1_container.setBorder(BorderFactory.createLineBorder(Color.black, 5));

		compContainer = new JPanel(new GridLayout(10, 10));
		compContainer.setPreferredSize(new Dimension(350, 350));
		compContainer.setBorder(BorderFactory.createLineBorder(Color.black, 5));

		grid = new JPanel[10][10];
		occupied = new boolean[10][10];
		flagInvalid = new boolean[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				grid[i][j] = new JPanel();
				grid[i][j].setBackground(Color.white);
				grid[i][j].setBorder(BorderFactory.createLineBorder(Color.blue, 2));
				grid[i][j].setPreferredSize(new Dimension(25, 25));
				grid[i][j].addMouseListener(this);
				P1_container.add(grid[i][j]);
			}
		}

		enemyGrid = new JPanel[10][10];
		enemyOcc = new boolean[10][10];  
		flagInvalidE = new boolean[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				enemyGrid[i][j] = new JPanel();
				enemyGrid[i][j].setBackground(Color.lightGray);
				enemyGrid[i][j].setBorder(BorderFactory.createLineBorder(Color.red, 2));
				enemyGrid[i][j].setPreferredSize(new Dimension(25, 25));
				enemyGrid[i][j].addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						//Enables the user to attack and sink enemy ships only by clicking on the enemy grid 
						if (startAttack) {
							turn = true;
							if (turn == true) {
								JPanel pt2 = (JPanel) e.getSource(); //clicked cell is saved as a JPanel object
								Cell eCell = new Cell((pt2.getX() - FRAME_OFFSET) / FRAME_STEP, (pt2.getY() - FRAME_OFFSET) / FRAME_STEP); 
								
								// check for Kraken hit
								if (eCell.getX()==kraken.getX() && eCell.getY()==kraken.getY() && !flagInvalid[eCell.getY()][eCell.getX()]) {
									pt2.setBackground(Color.MAGENTA);
									System.out.println("YOU HAVE AWAKENED THE KRAKEN! DEDUCTING POINTS");
									if(playerScore > 0) 
										playerScore = 0;
									else 
										playerScore -= 20;
									flagInvalid[eCell.getY()][eCell.getX()] = true;
								}
								
								//check for Cetus hit
								if (eCell.getX()==cetus.getX() && eCell.getY()==cetus.getY() && !flagInvalid[eCell.getY()][eCell.getX()]) {
									pt2.setBackground(Color.ORANGE);
									System.out.println("YOU HAVE AWAKENED CETUS! REPLACING ENEMY SHIPS");
									ArrayList<Ship> currShips = comp.getFleet();
									for(Ship cetusShip: currShips) {
										//if ship is not destroyed, its cells are marked as vacant; color and flagInvalid are reset to default
 										if (!cetusShip.isDestroyed()) {
											ArrayList<Cell> checkedPoints = cetusShip.getPoints();
											for(Cell c: checkedPoints) {
												enemyGrid[c.getY()][c.getX()].setBackground(Color.lightGray);
												enemyOcc[c.getY()][c.getX()] = false;
												flagInvalid[c.getY()][c.getX()] = false;
											}
										}
									}
									setEnemyships(true);
								}
								
								// check for ship hit
								if (enemyOcc[eCell.getY()][eCell.getX()] && !flagInvalid[eCell.getY()][eCell.getX()]) {
									Ship curShip;
									if((curShip = comp.getAttackedShip(eCell)) != null) {
										pt2.setBackground(Color.RED);
										playerScore++;
										curShip.damaged();
										flagInvalid[eCell.getY()][eCell.getX()] = true;
										if(curShip.isDestroyed()) {
											int s = curShip.getSize();
											Ship.shipType(s);
											playerScore += 2*s;
											uDestroyCount++; 
											comp.removeShip(curShip); //destroyed ship is removed from fleet 
											if(uDestroyCount == 5) {
												window.setVisible(false); //goes to user winning screen
												Game.finishGame();
											}
										}
									}
								}
								else if(!flagInvalid[eCell.getY()][eCell.getX()]) {
									pt2.setBackground(Color.BLACK);
									playerScore--;
									flagInvalid[eCell.getY()][eCell.getX()] = true;
									turn = false;
									attackUser(); //enemy turn begins
								}
							}
							Ppts.setText("PLAYER POINTS: " + playerScore);
						}
					}
				});
				compContainer.add(enemyGrid[i][j]);
			}
		}

		GridLayout layout = new GridLayout(1, 2);
		layout.setHgap(150);
		mainPanel = new JPanel(layout);

		player = new JLabel("PLAYER:");

		opponent = new JLabel("COMPUTER:");

		BorderLayout p1grid = new BorderLayout();
		p1grid.setVgap(7);
		BorderLayout compgrid = new BorderLayout();
		compgrid.setVgap(7);
		
		pPoints = new JPanel();
		Ppts = new JLabel("PLAYER POINTS: " + playerScore);
		pPoints.add(Ppts, BorderLayout.NORTH);
		
		ePoints = new JPanel();
		Epts = new JLabel("ENEMY POINTS: " + enemyScore);
		ePoints.add(Epts, BorderLayout.NORTH);
		
		main1 = new JPanel(p1grid);
		main1.add(P1_container, BorderLayout.CENTER);
		main1.add(player, BorderLayout.SOUTH);

		main2 = new JPanel(compgrid);
		main2.add(compContainer, BorderLayout.CENTER);
		main2.add(opponent, BorderLayout.SOUTH);

		mainPanel.add(main1, BorderLayout.CENTER);
		mainPanel.add(main2, BorderLayout.CENTER);

		panel = new JPanel();
		panel.setPreferredSize(new Dimension(100, 100));
		panel.add(mainPanel, BorderLayout.CENTER);
		panel.add(pPoints, BorderLayout.WEST);
		panel.add(ePoints, BorderLayout.EAST);

		window.add(panel, BorderLayout.CENTER);
		window.pack();
		window.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//Allows the user to place their ships by clicking on the user grid 
		int size = Player.FLEET_OF_SHIPS.size();
		if (i < size) {
			JPanel point = (JPanel) e.getSource();
			//FRAME_STEP and FRAME_OFFSET have been used to get coordinates in the form of {(0, 1), (1, 1)} instead of observed results like {(0, 29), (39, 29)}
			Cell c = new Cell((point.getX() - FRAME_OFFSET) / FRAME_STEP, (point.getY() - FRAME_OFFSET) / FRAME_STEP);			
			if (occupied[c.getY()][c.getX()]) {
				System.out.println("A SHIP OCCUPIES THIS AREA");
			} else {
				boolean validCell = false;
				if (clickCount > 0) {
					validCell = isValidCell(c);
				} else {
					// is first click so valid cell by default
					validCell = true;
					shipLength = Player.FLEET_OF_SHIPS.get(i).getSize();
				}

				if (validCell) {
					validCells = createValidCells(c, shipLength-clickCount-1, false); //clickCounts are subtracted to limit the number of permissible clicks while the user is placing a ship so as to not exceed the size of the ship
					if (validCells.size() > 0) {
						
						if (clickCount < shipLength) {
							point.setBackground(Color.CYAN);
							occupied[c.getY()][c.getX()] = true;
							Ship curShip;
							if(clickCount == 0) {
								Ship curShipN = new Ship(shipLength);
								curShipN.addPoint(c);
								p1.addShipToFleet(curShipN);
							}
							else if(clickCount >0) {
								//Adds the consecutive cells to the same ship object that is being placed
								if((curShip = p1.getCurrentShip()) != null) {
									curShip.addPoint(c);
								}
							}
							clickCount++;
						}
						if (clickCount == shipLength) {
							//if clickCount reaches the ship count we move to the next ship object in the list and reset the clickCount for the new ship
							i++;
							clickCount = 0;
						}
					}
				} 
			}
		}
		if (i == size) {
			//when all the ships are placed a message will be displayed asking the user if they are happy with their layout
			JFrame ready = new JFrame();
			JLabel done = new JLabel("Your ships have been placed. Confirm Layout?");
			JPanel choices = new JPanel(new GridBagLayout());
			GridBagConstraints pos = new GridBagConstraints();
			pos.fill = GridBagConstraints.HORIZONTAL;
			pos.gridx = 2;
			pos.gridy = 0;
			pos.insets = new Insets(3,3,3,3);
			
			JButton yes = new JButton("yes");
			JButton no = new JButton("no");
			
			no.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					window.setVisible(false);
					ready.setVisible(false);
					createGrid();					
				}
			});
			
			yes.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					validCells = new ArrayList<>();
					//Kraken and Cetus are placed before placing the computer's ships
					placeKraken();
					placeCetus();
					setEnemyships(false); // false to indicate that this method is not being called upon hitting Cetus
					ready.setVisible(false);
					JFrame attack = new JFrame();
					attack.setPreferredSize(new Dimension(200, 200));
					attack.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
					JLabel begin = new JLabel("ATTACK!");
					
					attack.add(begin, BorderLayout.CENTER);
					attack.pack();
					attack.setVisible(true);
					startAttack = true;
				}
			});
			
			choices.add(yes, pos);
			pos.gridx = 2;
			pos.gridy = 2;
			
			choices.add(no, pos);
			pos.gridx = 3;
			pos.gridy = 2;
			
			ready.add(done, BorderLayout.CENTER);
			ready.add(choices, BorderLayout.SOUTH);
			ready.pack();
			ready.setVisible(true);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) { //Not used in the main code
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) { //Not used in the main code
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) { //Not used in the main code
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) { //Not used in the main code
		// TODO Auto-generated method stub

	}

	public ArrayList<Cell> createValidCells(Cell c, int size, boolean enemy) {
		//assign local variable depending on the player
		boolean[][] occupiedInternal = (enemy) ? enemyOcc : occupied; 
		boolean[][] flagInvalidLocal = (enemy) ? flagInvalid : flagInvalidE;
		boolean invalid;
		ArrayList<Cell> curValidCells = new ArrayList<Cell>();
		int x = c.getX();
		int y = c.getY();
		if(c.getDirection() == 'O') {
			// LEFT CHECK
			if ((x - size) >= 0) {
				invalid = false;
				for (int i = 1; i <= size; i++) {
					if (occupiedInternal[y][x - i] || flagInvalidLocal[y][x - i]) {
						invalid = true;
						break;
					}
				}
				if (!invalid) {
					curValidCells.add(new Cell(x - 1, y, 'W'));
				}
			}
			// RIGHT CHECK
			if ((x + size) <= 9) {
				invalid = false;
				for (int i = 1; i <= size; i++) {
					if (occupiedInternal[y][x + i] || flagInvalidLocal[y][x + i]) {
						invalid = true;
						break;
					}
				}
				if (!invalid) {
					curValidCells.add(new Cell(x + 1, y, 'E'));
				}
			}
			// TOP CHECK
			if ((y - size) >= 0) {
				invalid = false;
				for (int i = 1; i <= size; i++) {
					if (occupiedInternal[y - i][x] || flagInvalidLocal[y - i][x]) {
						invalid = true;
						break;
					}
				}
				if (!invalid) {
					curValidCells.add(new Cell(x, y - 1, 'N'));
				}
			}
			// BOTTOM CHECK
			if ((y + size) <= 9) {
				invalid = false;
				for (int i = 1; i <= size; i++) {
					if (occupiedInternal[y + i][x] || flagInvalidLocal[y + i][x]) {
						invalid = true;
						break;
					}
				}
				if (!invalid) {
					curValidCells.add(new Cell(x, y + 1, 'S'));
				}
			}
		} else {
			//add consecutive cell in the direction of the current cell
			if (c.getDirection() == 'N') {
				curValidCells.add(new Cell(x, y-1, 'N'));
			}
			if (c.getDirection() == 'S') {
				curValidCells.add(new Cell(x, y+1, 'S'));
			}
			if (c.getDirection() == 'E') {
				curValidCells.add(new Cell(x+1, y, 'E'));
			}
			if (c.getDirection() == 'W') {
				curValidCells.add(new Cell(x-1, y, 'W'));
			}		
		}
		
		return curValidCells;
	}
	
	public boolean isValidCell(Cell c) {
		boolean isValid = false;
		for(int i=0; i<validCells.size(); i++) {
			if(validCells.get(i).getX() == c.getX() && validCells.get(i).getY() == c.getY()) {
				isValid = true;
				c.setDirection(validCells.get(i).getDirection());
				break;
			}
		}
		return isValid;
	}

	public void setEnemyships(boolean cetusHit) {
		clickCount = i = 0;
		ArrayList<Ship> fleetToPlace; 
		if(cetusHit) {
			fleetToPlace = new ArrayList<Ship>(comp.getFleet()); //new fleet is created containing un-sunk ships if Cetus is hit
			comp.resetFleet();
		} else {
			fleetToPlace = Player.FLEET_OF_SHIPS; //if Cetus is not attacked default fleet is selected 
		}
		int size = fleetToPlace.size();
		while (i<size) {
			Cell enemyC;
			Random X = new Random();
			if(clickCount == 0) {
				//first cell is selected randomly
				int x = Math.abs(X.nextInt()%10);
				int y = Math.abs(X.nextInt()%10);
				enemyC = new Cell(x, y);
			} else {
				int randomIndex = X.nextInt(validCells.size()); //a random index is selected ; removes ambiguity between coordinates of the next cell from the list validCells
				Cell randomCell = validCells.get(randomIndex); //a random object is selected from validCells
				enemyC = new Cell(randomCell.getX(), randomCell.getY(), randomCell.getDirection()); //coordinates of the same object from validCells are selected 
			}
			if (!enemyOcc[enemyC.getY()][enemyC.getX()] && !flagInvalid[enemyC.getY()][enemyC.getX()]) {
				shipLength = fleetToPlace.get(i).getSize();
				validCells = createValidCells(enemyC, shipLength-clickCount-1, true);
				if (validCells.size() > 0) {
					if (clickCount < fleetToPlace.get(i).getSize()) {
						enemyOcc[enemyC.getY()][enemyC.getX()] = true;
						Ship curShip;
						if(clickCount == 0) {
							Ship curShipN = new Ship(shipLength); //new ship object is created for the first click
							curShipN.addPoint(enemyC);
							comp.addShipToFleet(curShipN);
						}
						else if(clickCount> 0) {
							if((curShip = comp.getCurrentShip()) != null) {
								curShip.addPoint(enemyC); //subsequent cells are added to the latest ship being added
							}
						}
						clickCount++;
					}
					if (clickCount == fleetToPlace.get(i).getSize()) {
						i++;
						clickCount = 0;
					}
				}
			}
		}
	}
	
	public void attackUser () {
		if (startAttack) {
			while (turn == false) {
				Random X = new Random();
				int x = Math.abs(X.nextInt()%10);
				
				Random Y = new Random();
				int y = Math.abs(Y.nextInt()%10);
								
				Cell attackedCell = new Cell(x, y);
				if (occupied[y][x] && !flagInvalidE[y][x]) {				
					Ship curShip;
					if((curShip = p1.getAttackedShip(attackedCell)) != null)  { //returns the ship whose cell is attacked for distinguishing between different ship objects
						grid[y][x].setBackground(Color.RED);
						flagInvalidE[y][x] = true;
						enemyScore++;
						curShip.damaged();
						if(curShip.isDestroyed()) {
							int s = curShip.getSize();
							enemyScore += 2*s;
							eDestroyCount++;
							if(eDestroyCount == 5) {
								window.setVisible(false);
								Game.finishGame();
							}
						}
					}					
				}
				if (!occupied[y][x] && !flagInvalidE[y][x] ) {
					grid[y][x].setBackground(Color.BLACK);
					flagInvalidE[y][x] = true;
					enemyScore--;
					turn = true;
				}
			}
			Epts.setText("ENEMY POINTS: " + enemyScore);
		}
	}
	
	public void placeKraken() {
		// A random cell is selected to place Kraken
		Random X = new Random();
		int x = Math.abs(X.nextInt()%10);
		
		Random Y = new Random();
		int y = Math.abs(Y.nextInt()%10);

		
		kraken = new Cell(x, y);
		enemyOcc[y][x] = true;
	}
	
	public void placeCetus() {
		while(cetus == null) {
			//Cetus will not be placed until vacant independent coordinates are found i.e., the cell must not be occupied by Kraken
			Random X = new Random();
			int x = Math.abs(X.nextInt()%10);
			
			Random Y = new Random();
			int y = Math.abs(Y.nextInt()%10);
			
			if(kraken.getX()!=x && kraken.getY()!=y) {
				cetus = new Cell(x, y);
				enemyOcc[y][x] = true;
			}
		}
	}
}


