package majig12346.units.air;

import majig12346.Player;
import majig12346.terrain.Terrain;
import majig12346.units.Air;
import majig12346.units.Unit;
import majig12346.weapons.WeaponType;

/**
 * The Close Air Support is an air unit
 *Gets SHELL (ranged A2G)
 *Costs 2800
 *Costs 5 fuel/turn to stay airborne
 *Very potent against enemy ground units
 */
public class CAS extends Air {

	/**
	 * Constructs a Close Air Support
	 * sets primary weapon to SHELL (ranged A2G)
	 * @param owner owner of the unit
	 */
	public CAS(Player owner) {
		super(owner);
		setWeapon(0, WeaponType.SHELL);
	}

	@Override
	public int getBuildCost() {
		return 2800;
	}

	@Override
	public double getBaseArmorResistance() {
		//20% general resistance
		return 0.80;
	}
	@Override
	public double resist(double damage, String source) {
		//70% resistance to small arms, 30% to heavy machine gun fire
		switch(source){
		case WeaponType.RIFLE:
			return damage*0.3;
		case WeaponType.MG:
			return damage*0.3;
		case WeaponType.HMG:
			return damage*0.7;
		default:
			return damage;
		}
	}
	@Override
	public boolean canTarget(Unit u){
		Terrain home = (Terrain) getLocation();
		int dist = home.distanceTo((Terrain) u.getLocation());
		return (dist>=1&&dist<=4);
	}
	@Override
	public boolean canCounter(Unit u) {
		return false;
	}
	@Override
	public int getDailyCost() {
		return 5;
	}

}
