package majig12346.units.land;

import majig12346.PassiveFlag.MoveType;
import majig12346.terrain.Terrain;
import majig12346.Player;
import majig12346.units.Unit;
import majig12346.weapons.WeaponType;

/**
 * An Rockets truck is a Unit
 * Gets ROCKET -- ranged ground-to-ground
 *  Costs 1500
 *  RMoveType TIRES
 * Ranged attack vs ground only, Manhattan radius 3-5 
 */
public class Rockets extends Unit{

	/**
	 * Constructs a Rockets truck
	 * sets primary weapon to ROCKET
	 * @param owner the owner of this unit
	 */
	public Rockets(Player owner) {
		super(owner);
		setWeapon(0, WeaponType.ROCKET);
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
		return 1500;
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
