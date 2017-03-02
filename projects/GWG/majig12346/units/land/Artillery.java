package majig12346.units.land;

import majig12346.Player;
import majig12346.PassiveFlag.MoveType;
import majig12346.terrain.Terrain;
import majig12346.units.Unit;
import majig12346.weapons.WeaponType;

/**
 * An Artillery is a Tank, but is an indirect fire unit
 * Gets SHELL -- ranged ground-to-ground
 *  Costs 600
 *  Ranged attack, Manhattan radius 2-3 -- cannot counterattack
 *  Strong vs armor at range, very weak when defending
 */
public class Artillery extends Tank {

	/**
	 * Constructs an Artillery
	 * sets primary weapon to SHELL 
	 * @param owner owner of the Unit
	 */
	public Artillery(Player owner) {
		super(owner);
		setWeapon(1, WeaponType.SHELL);
	}
	@Override
	public boolean couldTarget(Unit toCheck, Terrain hypothetical){
		if(!getLocation().equals(hypothetical)){
			return false;//cannot move and fire
		}
		if(null==toCheck||MoveType.AIR.equals(toCheck.getMovementType())){
			return false; //can't target nothing, can target sea, land
		}
		int dist = hypothetical.distanceTo((Terrain) toCheck.getLocation());
		return (dist>=2&&dist<=3);
	}
	@Override
	public boolean canCounter(Unit u) {
		return false;
	}
	@Override
	public int getBuildCost() {
		return 600;
	}
	@Override
	public double getBaseArmorResistance() {
		return 1;
	}
}
