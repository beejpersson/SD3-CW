
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public abstract class Ship extends Thread {
	
	//Initialise ship attributes
	protected String type;
	protected int xPos;
	protected int yPos;
	protected OperationalMode mode;
	private ArrayList<Point> possibleMoves = new ArrayList<Point>();
	private Random rn = new Random();
	
	//getters and setters for ship attributes
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public int getXPos() {
		return this.xPos;
	}

	public void setXPos(int xPos) {
		this.xPos = xPos;
	}
	
	public int getYPos() {
		return this.yPos;
	}

	public void setYPos(int yPos) {
		this.yPos = yPos;
	}
	
	public OperationalMode getMode() {
		return this.mode;
	}
	public void setMode(OperationalMode mode) {
		this.mode = mode;
	}
	
	//Strategy pattern, changes master ship mode
	public void informPlayer() {
		this.mode.inform();
	}
	
	//Movement limitations
	public void move(){
		if (!possibleMoves.isEmpty()){
			possibleMoves.clear();
		}
		//North
		if (this.yPos - 1 >= 0) {
			possibleMoves.add(new Point(this.xPos, yPos - 1));
		}
		//North East
		if (this.xPos + 1 <= 3 && this.yPos - 1 >= 0) {
			possibleMoves.add(new Point(this.xPos + 1, this.yPos - 1));
		}
		//East
		if (this.xPos + 1 <= 3) {
			possibleMoves.add(new Point(this.xPos + 1, this.yPos));
		}
		//South East
		if (this.xPos + 1 <= 3 && this.yPos + 1 <= 3) {
			possibleMoves.add(new Point(this.xPos + 1, this.yPos + 1));
		}
		//South
		if (this.yPos + 1 <= 3) {
			possibleMoves.add(new Point(this.xPos, this.yPos + 1));
		}
		//South West
		if (this.xPos - 1 >= 0 && this.yPos + 1 <= 3) {
			possibleMoves.add(new Point(this.xPos - 1, this.yPos + 1));
		}
		//West
		if (this.xPos - 1 >= 0) {
			possibleMoves.add(new Point(this.xPos - 1, this.yPos));
		}
		//North West
		if (this.xPos - 1 >= 0 && this.yPos - 1 >= 0) {
			possibleMoves.add(new Point(this.xPos - 1, this.yPos - 1));
		}
		int random = rn.nextInt(possibleMoves.size());
		setXPos(possibleMoves.get(random).x);
		setYPos(possibleMoves.get(random).y);
	}
	
	//Run each ship instance in a thread
	public void run() { 
		try {
			Thread.sleep(50);
			System.out.println(this.toString());
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		move();
	}
}
