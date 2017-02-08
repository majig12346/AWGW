package majig12346.units.sea;

import majig12346.Player;
import majig12346.units.Stealth2;
import majig12346.weapons.WeaponType;

/**
 * A Sub is a stealth sea unit
 * Gets TORPEDO (hits naval units only)
 * Costs 2000
 * Costs 1 fuel/turn to stay afloat, 
 * costs extra 4 when hidden (5 total/turn)
 */
public class Sub extends Stealth2 {

	/**
	 * Constructs a submarine
	 * sets primary weapon to TORPEDO
	 * @param owner owner of the unit
	 */
	public Sub(Player owner) {
		super(owner);
		setWeapon(0, WeaponType.TORPEDO);
	}

	@Override
	public int getExtraDailyCost() {
		return 4;
	}

	@Override
	public int getBuildCost() {
		return 2000;
	}

	@Override
	public double getBaseArmorResistance() {
		return 1;
	}

}
