
import javax.swing.JOptionPane;

public class OutputUpdater implements Observer {
	
	String outputText;
	GameBoard gameBoard;
	
	public void updateOutput(String output) {
		this.outputText = output;
		display();
		
	}
	
	public void display() {
		//gameBoard.setOutput(outputText);
		JOptionPane.showMessageDialog(null, this.outputText, "", JOptionPane.INFORMATION_MESSAGE);
	}

}
