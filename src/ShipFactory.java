
public final class ShipFactory {

	//Factory pattern, ship factory - handles all ship creation
	public static Ship createShip(String type, int xPos, int yPos) {
		Ship ship = null;
		
		if(type.equalsIgnoreCase("BattleStar")) {
			ship = new BattleStar();
			ship.setXPos(xPos);
			ship.setYPos(yPos);
		}
		if(type.equalsIgnoreCase("BattleCruiser")) {
			ship = new BattleCruiser();
			ship.setXPos(xPos);
			ship.setYPos(yPos);
		}
		if(type.equalsIgnoreCase("BattleShooter")) {
			ship = new BattleShooter();
			ship.setXPos(xPos);
			ship.setYPos(yPos);
		}
		if(type.equalsIgnoreCase("MasterShip")) {
			ship = new MasterShip();
			if (xPos == 0 && yPos == 0){
				ship.setXPos(1);
				ship.setYPos(1);
			}
			else {
				ship.setXPos(xPos);
				ship.setYPos(yPos);
			}
		}
		
		return ship;
	}
}
