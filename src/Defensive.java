import javax.swing.JOptionPane;

public class Defensive implements OperationalMode {
	
	//Strategy pattern method to indicate mastership's operational mode
	public void inform() {
		JOptionPane.showMessageDialog(null, "Defensive Mode");
	}
}
