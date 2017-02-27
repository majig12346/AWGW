package majig12346.units.land;

import majig12346.Player;
import majig12346.weapons.WeaponType;

/**
 * A HeavyTank is a Tank
 * Gets AP, HMG
 * Costs 2200
 * Armor Piercing rounds are strong against  armor and trucks
 */
public class HeavyTank extends Tank {

	/**
	 * Constructs a Heavy Tank
	 * sets primary weapon to AP, 
	 *secondary to HMG
	 * @param owner owner of the Unit
	 */
	public HeavyTank(Player owner) {
		super(owner);
		setWeapon(0, WeaponType.AP);
		setWeapon(1, WeaponType.HMG);
	}
	@Override
	public int getBuildCost() {
		return 2200;
	}
	@Override
	public double getBaseArmorResistance() {
		//30% resistance
		return 0.7;
	}

}
