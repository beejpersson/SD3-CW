
public abstract class Ship {
	
	protected String type;
	protected int xPos;
	protected int yPos;
	
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
	
	public void move(){
		
	}
}
