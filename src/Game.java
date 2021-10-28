import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Game implements ActionListener {

	private static JFrame frame;
	private static JButton start;

	public Game() {
		frame = new JFrame();
		frame.setTitle("BATTLESHIP");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(800, 600));
		
		BufferedImage img = null;
		try {
			String filePath = new File(getClass().getResource("bShip.jpg").getPath()).getAbsolutePath();
			filePath = filePath.replace("%20", " "); //replacement done to read correct file path
			img = ImageIO.read(new File(filePath));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image dimg = img.getScaledInstance(800, 600, Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(dimg);
		JLabel imgLabel = new JLabel(imageIcon);
		frame.setContentPane(imgLabel);
		
		start = new JButton("START A NEW GAME");
		start.setBounds(300, 200, 200, 20);		
		start.addActionListener(this);
		
		frame.add(start);
		frame.setLayout(null);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
	}
	
	public static void winningScreen() {
		JFrame win = new JFrame();
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.setPreferredSize(new Dimension(300, 300));
		
		JPanel winPanel = new JPanel();
		JPanel choicePanel = new JPanel(new GridLayout(0, 2));
		
		JLabel winMessage = new JLabel("Congratulations! You won! would you like to play again?");
		JButton yes = new JButton("yes");
		JButton no = new JButton("no");
		
		yes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				win.dispose();
				Board.eDestroyCount = 0;
				Board.uDestroyCount = 0;
				new Game();
			}
		});
		
		no.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				win.dispose();
				frame.dispose();
				System.exit(0);
			}
		});
		
		winPanel.add(winMessage);
		choicePanel.add(yes);
		choicePanel.add(no);
		win.add(winPanel, BorderLayout.CENTER);
		win.add(choicePanel, BorderLayout.SOUTH);
		win.pack();
		win.setVisible(true);
	}
	
	public static void losingScreen() {
		JFrame lose = new JFrame();
		lose.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		lose.setPreferredSize(new Dimension(300, 300));
		
		JPanel losePanel = new JPanel();
		JPanel choicePanel = new JPanel(new GridLayout(0, 2));
		
		JLabel defeatMessage = new JLabel("You have lost. Continue?");
		JButton yes = new JButton("yes");
		JButton no = new JButton("no");
		
		yes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				lose.dispose();
				Board.eDestroyCount = 0;
				Board.uDestroyCount = 0;
				new Game();
			}
		});
		
		no.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				lose.dispose();
				frame.dispose();
				System.exit(0);
			}
		});
		
		losePanel.add(defeatMessage);
		choicePanel.add(yes);
		choicePanel.add(no);
		lose.add(losePanel, BorderLayout.CENTER);
		lose.add(choicePanel, BorderLayout.SOUTH);
		lose.pack();
		lose.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		frame.setVisible(false);
		Board gameBoard = new Board();
		gameBoard.startButton();
		
	}
	
	public static void main(String args[]) {
		new Game();
	}
	
	public static void finishGame() {
		if (Board.uDestroyCount == 5)
			winningScreen();
		if (Board.eDestroyCount == 5)
			losingScreen();
	}

}
