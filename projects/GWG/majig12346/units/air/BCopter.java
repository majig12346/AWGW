package majig12346.units.air;

import majig12346.Player;
import majig12346.units.Air;
import majig12346.weapons.WeaponType;

/**
 * The battle helicopter is an air unit
 * Gets ROCKET_POD, MG
 * Costs 900
 * Costs 2 fuel/turn to stay airborne
 * Nicely harasses enemy land units
 */
public class BCopter extends Air{

	/**
	 * Constructs a battle helicopter
	 * sets primary weapon to ROCKET_POD,
	 * secondary to MG
	 * @param owner owner of the unit
	 */
	public BCopter(Player owner) {
		super(owner);
		setWeapon(0, WeaponType.ROCKET_POD);
		setWeapon(1, WeaponType.MG);
	}

	@Override
	public int getBuildCost() {
		return 900;
	}

	@Override
	public double getBaseArmorResistance() {
		return 1;
	}
	
	@Override
	public int getDailyCost() {
		return 2;
	}

}
