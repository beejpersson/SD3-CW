
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.Component;
import javax.swing.SwingConstants;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import java.awt.Scrollbar;

public class GameBoard extends JFrame implements Observable{
	
	private JPanel contentPane;
	private JButton btnStart;
	private JButton btnMove;
	private JButton btnQuit;
	private JButton btnUndo;
	private JTextPane output;
	private JScrollPane scrollPane;
	private JTextPane boardSquare;
	private int boardSquareWidth = 135;
	private int boardSquareHeight = 110;
	private JTextPane[][] board = new JTextPane[4][4];
	private Random rn = new Random();
	private Ship masterShip;
	private ArrayList<Ship> EnemyShips = new ArrayList<Ship>();
	private ArrayList<OutputUpdater> outputUpdaterList = new ArrayList<OutputUpdater>();
	
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
		contentPane.add(getBtnQuit());
		contentPane.add(getBtnUndo());
		contentPane.add(getOutput());
		
	}
        
    private JButton getBtnStart() {
		if (btnStart == null) {
			btnStart = new JButton("Start");
			btnStart.setBounds(60, 505, 85, 30);
			btnStart.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int randomX = rn.nextInt(4);
					int randomY = rn.nextInt(4);
					masterShip = ShipFactory.createShip("MasterShip", randomX, randomY);
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
    
    private JButton getBtnMove() {
		if (btnMove == null) {
			btnMove = new JButton("Move");
			btnMove.setBounds(185, 505, 80, 30);
			btnMove.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (masterShip == null){
						JOptionPane.showMessageDialog(null, "You can only move once the game has started.");
						return;
					}
					output.setText(output.getText() + "\n\nNEXT MOVE");
					if (!EnemyShips.isEmpty()) {
						for (Ship enemyShip : EnemyShips) {
							enemyShip.move();
						}
					}
					int random = rn.nextInt(8);
					if (random == 0){
						EnemyShips.add(ShipFactory.createShip("BattleStar", 0, 0));
					}
					else if (random == 3){
						EnemyShips.add(ShipFactory.createShip("BattleCruiser", 0, 0));
					}
					else if (random == 6){
						EnemyShips.add(ShipFactory.createShip("BattleShooter", 0, 0));
					}
					masterShip.move();	
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
							if (!EnemyShips.isEmpty()) {
								for (Ship enemyShip : EnemyShips) {
									if (i == enemyShip.getXPos() && j == enemyShip.getYPos()) {
										boardSquare.setText(boardSquare.getText() + "\n" + enemyShip.getType());
									}
								}
							}
							contentPane.add(board[i][j]);
						}
					}
					for (Ship enemyShip : EnemyShips){
						output.setText(output.getText() + "\n" + enemyShip.getType() + " moved to square (" + enemyShip.getXPos() + ", " + enemyShip.getYPos() + ").");
					}
					output.setText(output.getText() + "\n" + masterShip.getType() + " moved to square (" + masterShip.getXPos() + ", " + masterShip.getYPos() + ").");
				}
			});
		}
		return btnMove;
    }
    
    private JButton getBtnUndo() {
  		if (btnUndo == null) {
  			btnUndo = new JButton("Undo");
  			btnUndo.setBounds(310, 505, 80, 30);
  		}
  		return btnUndo;
     }
    
    private JButton getBtnQuit() {
  		if (btnQuit == null) {
  			btnQuit = new JButton("Quit");
  			btnQuit.setToolTipText("Quit the game and close the window.");
  			btnQuit.setBounds(435, 505, 80, 30);
  			btnQuit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
  		}
  		return btnQuit;
     }
    
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
		this.outputUpdaterList.add(o);
	}

	public void notifyObservers() {
		for (OutputUpdater o : this.outputUpdaterList)
		{
			o.updateOutput(this.output.getText());
		}
	}
}
