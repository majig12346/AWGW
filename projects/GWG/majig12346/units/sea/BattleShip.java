package majig12346.units.sea;

import majig12346.Player;
import majig12346.terrain.Terrain;
import majig12346.units.Sea;
import majig12346.units.Unit;
import majig12346.weapons.WeaponType;

/**
 * A BattleShip is a Sea unit
 * Gets SHELL (ranged surface-to-surface), 
 * range Manhattan 2-6,
 * cannot counterattack
 * Costs 2800
 * Costs 1 fuel/turn to stay afloat
 */
public class BattleShip extends Sea {

	/**
	 * Constructs a BattleShip
	 * sets primary to SHELL
	 * @param owner owner of the unit
	 */
	public BattleShip(Player owner) {
		super(owner);
		setWeapon(0, WeaponType.SHELL);
	}

	@Override
	public int getBuildCost() {
		return 2800;
	}

	@Override
	public double getBaseArmorResistance() {
		//10% overall resistance
		return 0.90;
	}
	@Override
	public boolean canTarget(Unit u){
		Terrain home = (Terrain) getLocation();
		int dist = home.distanceTo((Terrain) u.getLocation());
		return (dist>=2&&dist<=6);
	}
	@Override
	public boolean canCounter(Unit u) {
		return false;
	}
}
