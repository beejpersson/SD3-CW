import javax.swing.JOptionPane;

public class Offensive implements OperationalMode {
	
	//Strategy pattern method to indicate mastership's operational mode
	public void inform() {
		JOptionPane.showMessageDialog(null, "Offensive Mode");
	}
}
