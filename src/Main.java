
import java.awt.EventQueue;
import javax.swing.JFrame;
	
public class Main extends JFrame {

public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
	        @Override
	        public void run() {
	        	try {
		            GameBoard frame = new GameBoard();
		            frame.setVisible(true);
		        } catch (Exception e) {
					e.printStackTrace();
				}
	        }
	    });
	}
}
