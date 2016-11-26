
public class ShipFactory {

	public Ship createShip(String type) {
		Ship ship = null;
		
		if(type.equalsIgnoreCase("BattleStar")) {
			ship = new BattleStar();
		}
		if(type.equalsIgnoreCase("BattleCruiser")) {
			ship = new BattleCruiser();
		}
		if(type.equalsIgnoreCase("BattleShooter")) {
			ship = new BattleShooter();
		}
		if(type.equalsIgnoreCase("MasterShip")) {
			ship = new MasterShip();
		}
		
		return ship;
	}
}
