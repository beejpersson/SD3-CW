
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.border.EtchedBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JTextPane;

public class GameBoard extends JFrame implements Observable {
	
	private JPanel contentPane;
	private JButton btnStart;
	private JButton btnMove;
	private JButton btnMode;
	private JButton btnUndo;
	private JButton btnQuit;
	private JTextPane output;
	private JScrollPane scrollPane;
	private JTextPane boardSquare;
	private int boardSquareWidth = 135;
	private int boardSquareHeight = 110;
	private JTextPane[][] board = new JTextPane[4][4];
	private Random rn = new Random();
	private Ship masterShip;
	private ArrayList<Ship> enemyShips = new ArrayList<Ship>();
	private ArrayList<OutputUpdater> outputUpdaterList = new ArrayList<OutputUpdater>();
	private OutputUpdater outputUpdater;
	
	public GameBoard() {
	
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0,0,900,600);
		setTitle("Sky Wars");
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);     
        setLocationRelativeTo(null);
		
		contentPane.add(getBtnStart());
		contentPane.add(getBtnMove());
		contentPane.add(getBtnMode());
		contentPane.add(getBtnUndo());
		contentPane.add(getBtnQuit());
		contentPane.add(getOutput());
		
	}
        
    private JButton getBtnStart() {
		if (btnStart == null) {
			btnStart = new JButton("Start");
			btnStart.setToolTipText("Start a new game");
			btnStart.setBounds(20, 505, 85, 30);
			btnStart.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Audio.playSound("audio.wav");
					int randomX = rn.nextInt(4);
					int randomY = rn.nextInt(4);
					masterShip = ShipFactory.createShip("MasterShip", randomX, randomY);
					enemyShips.clear();
					registerObserver(outputUpdater);
					for (int i = 0; i < 4; i++) {
						for (int j = 0; j < 4; j++) {
							if (board[i][j] != null)
							{
								contentPane.remove(board[i][j]);
							}
							boardSquare = new JTextPane();
							SimpleAttributeSet center = new SimpleAttributeSet();
							StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
							boardSquare.setParagraphAttributes(center, false);
							boardSquare.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.WHITE, null));
							boardSquare.setForeground(Color.BLACK);
							boardSquare.setBackground(new Color(135, 206, 235));
							boardSquare.setBounds((i*boardSquareWidth)+15, (j*boardSquareHeight)+20, boardSquareWidth,  boardSquareHeight);
							boardSquare.setText("(" + i + ", " + j + ")");
							board[i][j] = boardSquare;
							if (i == masterShip.getXPos() && j == masterShip.getYPos()) {
								boardSquare.setText(boardSquare.getText() + "\n" + masterShip.getType());
							}
							if (i == 0 && j == 0) {
								boardSquare.setText(boardSquare.getText() + "\n(Portal)");
							}
							contentPane.add(board[i][j]);
						}
			        }
					output.setText(masterShip.getType() + " spawned in square (" + masterShip.getXPos() + ", " + masterShip.getYPos() + ").");
				}
			});
		}
		return btnStart;
    }
    
    //Make a move
    private JButton getBtnMove() {
		if (btnMove == null) {
			btnMove = new JButton("Move");
			btnMove.setToolTipText("Make a move.");
			btnMove.setBounds(145, 505, 80, 30);
			btnMove.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//No movement when game hasn't started
					if (masterShip == null){
						JOptionPane.showMessageDialog(null, "You can only move once the game has started.");
						return;
					}
					//Move enemy ships
					output.setText(output.getText() + "\n\nNEXT MOVE");
					if (!enemyShips.isEmpty()) {
						for (Ship enemyShip : enemyShips) {
							enemyShip.run();
						}
					}
					//Spawn enemy ships
					int random = rn.nextInt(8);
					if (random == 0){
						enemyShips.add(ShipFactory.createShip("BattleStar", 0, 0));
					}
					else if (random == 3){
						enemyShips.add(ShipFactory.createShip("BattleCruiser", 0, 0));
					}
					else if (random == 6){
						enemyShips.add(ShipFactory.createShip("BattleShooter", 0, 0));
					}
					//Move master ship
					masterShip.run();
					//Build board with ships in correct squares
					for (int i = 0; i < 4; i++) {
						for (int j = 0; j < 4; j++) {
							if (board[i][j] != null)
							{
								contentPane.remove(board[i][j]);
							}
							boardSquare = new JTextPane();
							SimpleAttributeSet center = new SimpleAttributeSet();
							StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
							boardSquare.setParagraphAttributes(center, false);
							boardSquare.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.WHITE, null));
							boardSquare.setForeground(Color.BLACK);
							boardSquare.setBackground(new Color(135, 206, 235));
							boardSquare.setBounds((i*boardSquareWidth)+15, (j*boardSquareHeight)+20, boardSquareWidth,  boardSquareHeight);
							boardSquare.setText("(" + i + ", " + j + ")");
							board[i][j] = boardSquare;
							if (i == 0 && j == 0) {
								boardSquare.setText(boardSquare.getText() + "\n(Portal)");
							}
							if (i == masterShip.getXPos() && j == masterShip.getYPos()) {
								boardSquare.setText(boardSquare.getText() + "\n" + masterShip.getType());
							}
							if (!enemyShips.isEmpty()) {
								for (Ship enemyShip : enemyShips) {
									if (i == enemyShip.getXPos() && j == enemyShip.getYPos()) {
										boardSquare.setText(boardSquare.getText() + "\n" + enemyShip.getType());
									}
								}
							}
							contentPane.add(board[i][j]);
						}
					}
					//Output ships new locations
					for (Ship enemyShip : enemyShips){
						output.setText(output.getText() + "\n" + enemyShip.getType() + " moved to square (" + enemyShip.getXPos() + ", " + enemyShip.getYPos() + ").");
					}
					output.setText(output.getText() + "\n" + masterShip.getType() + " moved to square (" + masterShip.getXPos() + ", " + masterShip.getYPos() + ").");
					
					//Handling killing of enemy ships and master ship, game over
					ArrayList<Ship> deadEnemyShips = new ArrayList<Ship>();
					for (Ship enemyShip : enemyShips){
						//In offensive mode kill 3, more than 3 then master ship destroyed
						if (masterShip.getMode().getClass() == Offensive.class){
							if (deadEnemyShips.size() < 3){
								if (enemyShip.getXPos() == masterShip.getXPos() && enemyShip.getYPos() == masterShip.getYPos()){
									deadEnemyShips.add(enemyShip);
									output.setText(output.getText() + "\n" + masterShip.getType() + " destroyed " + enemyShip.getType() + " in square (" + masterShip.getXPos()+ ", " + masterShip.getYPos() + ").");
								}
							} else {
								if (enemyShip.getXPos() == masterShip.getXPos() && enemyShip.getYPos() == masterShip.getYPos()){
									output.setText(output.getText() + "\n" + masterShip.getType() + " destroyed. \n\nGAME OVER");
									masterShip = null;
									Audio.playSound("gameover.wav");
									return;
								}
							}
						//In defensive mode only kill 1, more than 1 then master ship destroyed
						} else {
							if (deadEnemyShips.size() < 1){
								if (enemyShip.getXPos() == masterShip.getXPos() && enemyShip.getYPos() == masterShip.getYPos()){
									deadEnemyShips.add(enemyShip);
									output.setText(output.getText() + "\n" + masterShip.getType() + " destroyed " + enemyShip.getType() + " in square (" + masterShip.getXPos()+ ", " + masterShip.getYPos() + ").");
								}
							} else {
								if (enemyShip.getXPos() == masterShip.getXPos() && enemyShip.getYPos() == masterShip.getYPos()){
									output.setText(output.getText() + "\n" + masterShip.getType() + " destroyed. \n\nGAME OVER");
									masterShip = null;
									Audio.playSound("gameover.wav");
									return;
								}
							}
						}
					}
					enemyShips.removeAll(deadEnemyShips);
					deadEnemyShips.clear();
					//notifyObservers();
				}
			});
		}
		return btnMove;
    }
    
    //Change the mastership's operational mode
    private JButton getBtnMode() {
  		if (btnMode == null) {
  			btnMode = new JButton("Mode");
  			btnMode.setToolTipText("Change the Operational Mode of the MasterShip.");
  			btnMode.setBounds(270, 505, 80, 30);
  			btnMode.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//Can't change mode when there's no ship
					if (masterShip == null){
						JOptionPane.showMessageDialog(null, "You can only change mode once the game has started.");
						return;
					}
					
					//Change the mode
					if (masterShip.getMode().getClass() == Defensive.class){
						masterShip.setMode(new Offensive());
						masterShip.informPlayer();
					}
					else {
						masterShip.setMode(new Defensive());
						masterShip.informPlayer();
					}
				}
			});
  		}
  		return btnMode;
     }
    
    private JButton getBtnUndo() {
  		if (btnUndo == null) {
  			btnUndo = new JButton("Undo");
  			btnUndo.setToolTipText("Undo the previous move.");
  			btnUndo.setBounds(395, 505, 80, 30);
  		}
  		return btnUndo;
     }
    
    //Quit the program
    private JButton getBtnQuit() {
  		if (btnQuit == null) {
  			btnQuit = new JButton("Quit");
  			btnQuit.setToolTipText("Quit the game and close the window.");
  			btnQuit.setBounds(515, 505, 80, 30);
  			btnQuit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
  		}
  		return btnQuit;
     }
    
    //Output text box
    private JScrollPane getOutput() {
  		if (output == null) {
  			output = new JTextPane();
  			output.setEditable(false);
  			output.setBackground(Color.WHITE);
  			output.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
  			scrollPane = new JScrollPane(output);
  			scrollPane.setBounds(600, 20, 270, 440);
  		}
  		return scrollPane;
     }
    
	public void setOutput(String text) {
		this.output.setText(text);
	}
	

	public void registerObserver(OutputUpdater o) {
		outputUpdaterList.add(o);
	}

	public void notifyObservers() {
		for (OutputUpdater o : outputUpdaterList)
		{
			//JOptionPane.showMessageDialog(null, output.getText());
			o.updateOutput(output.getText());
		}
	}
}
