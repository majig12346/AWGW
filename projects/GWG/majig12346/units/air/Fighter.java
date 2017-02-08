package majig12346.units.air;

import majig12346.Player;
import majig12346.units.Air;
import majig12346.weapons.WeaponType;

/**
 * The fighter aircraft is an Air unit
 * Gets MISSILE (direct A2A)
 * Costs 2000
 * Costs 5 fuel/turn to stay airborne
 * Very strong vs enemy air units in direct combat -- cannot hit ground units
 */
public class Fighter extends Air {

	/**
	 * Constructs a fighter aircraft
	 * sets primary weapon to MISSILE
	 * @param owner owner of the unit
	 */
	public Fighter(Player owner) {
		super(owner);
		setWeapon(0, WeaponType.MISSILE);
	}

	@Override
	public int getBuildCost() {
		return 2000;
	}

	@Override
	public double getBaseArmorResistance() {
		return 1;
	}
	@Override
	public double resist(double damage, String source) {
		//50% resistance to small arms
		switch(source){
		case WeaponType.RIFLE:
			return damage*0.5;
		case WeaponType.MG:
			return damage*0.5;
		default:
			return damage;
		}
	}
	@Override
	public int getDailyCost() {
		return 5;
	}

}
