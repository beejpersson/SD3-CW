

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JPanel;
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
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.Component;
import javax.swing.SwingConstants;

public class GameBoard extends JFrame{
	
	private JPanel contentPane;
	private JButton btnStart;
	private JButton btnMove;
	private JButton btnQuit;
	private JButton btnUndo;
	private JLabel boardSquare;
	private int board[][] = new int[4][4];
	private JLabel lblNewLabel;
	
	public GameBoard() {
	
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0,0,800,600);
		setTitle("Sky Wars");
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
        
        setLocationRelativeTo(null);
        
        for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				contentPane.add(getBoardSquare((i*135)+50, (j*110)+50, 135, 110));
			}
        }
		
		contentPane.add(getBtnStart());
		contentPane.add(getBtnMove());
		contentPane.add(getBtnQuit());
		contentPane.add(getBtnUndo());
		contentPane.add(getLblNewLabel());
	}
        
    private JButton getBtnStart() {
		if (btnStart == null) {
			btnStart = new JButton("Start");
			btnStart.setBounds(120, 525, 85, 30);
		}
		return btnStart;
    }
    
    private JButton getBtnMove() {
		if (btnMove == null) {
			btnMove = new JButton("Move");
			btnMove.setBounds(245, 525, 80, 30);
		}
		return btnMove;
    }
    
    private JButton getBtnQuit() {
  		if (btnQuit == null) {
  			btnQuit = new JButton("Quit");
  			btnQuit.setToolTipText("Quit the game and close the window.");
  			btnQuit.setBounds(495, 525, 80, 30);
  			btnQuit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
  		}
  		return btnQuit;
     }
    
    private JButton getBtnUndo() {
  		if (btnUndo == null) {
  			btnUndo = new JButton("Undo");
  			btnUndo.setBounds(370, 525, 80, 30);
  		}
  		return btnUndo;
     }
    
    private JLabel getBoardSquare(int i, int j, int k, int l) {
		boardSquare = new JLabel("Master Ship");
		boardSquare.setHorizontalAlignment(SwingConstants.CENTER);
		boardSquare.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		boardSquare.setBounds(i, j, k, l);
  		return boardSquare;
     }
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("New label");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setBounds(349, 54, 127, 65);
		}
		return lblNewLabel;
	}
}
