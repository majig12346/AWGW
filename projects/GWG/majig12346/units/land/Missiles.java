package majig12346.units.land;

import majig12346.PassiveFlag.MoveType;
import majig12346.Player;
import majig12346.terrain.Terrain;
import majig12346.units.Unit;
import majig12346.weapons.WeaponType;

/**
 * An Missiles truck is a Unit
 * Gets MISSILES (ground-to-air, ranged)
 * Costs 1200
 * MoveType TIRES
 * Ranged attack vs air only, Manhattan radius 3-5
 * cannot counterattack
 */
public class Missiles extends Unit {

	/**
	 * Constructs a Missiles truck
	 * sets primary weapon to MISSILES
	 * @param owner the owner of this unit
	 */
	public Missiles(Player owner) {
		super(owner);
		setWeapon(0, WeaponType.MISSILES);
	}
	@Override
	public boolean canTarget(Unit u){
		Terrain home = (Terrain) getLocation();
		int dist = home.distanceTo((Terrain) u.getLocation());
		return (dist>=3&&dist<=5);
	}
	@Override
	public boolean canCounter(Unit u) {
		return false;
	}
	@Override
	public int getBuildCost() {
		return 1200;
	}
	@Override
	public double getBaseArmorResistance() {
		return 1;
	}
	@Override
	public void outOfFuel() {
		//do nothing
		
	}
	@Override
	public MoveType getMovementType() {
		return MoveType.TIRES;
	}
}

