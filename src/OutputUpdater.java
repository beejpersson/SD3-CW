
import javax.swing.JOptionPane;

public class OutputUpdater implements Observer {
	
	String outputText;
	GameBoard gameBoard;
	
	public void updateOutput(String output) {
		this.outputText = output;
		JOptionPane.showMessageDialog(null, this.outputText);
		
	}
	
	public void display() {
		//gameBoard.setOutput(outputText);
		System.out.println("hello");
		JOptionPane.showMessageDialog(null, outputText);
	}

}
