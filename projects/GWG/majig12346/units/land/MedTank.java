package majig12346.units.land;

import majig12346.Player;
import majig12346.weapons.WeaponType;

/*
 * A MedTank is a Tank
 * Gets HEAT and MG
 * Costs 700
 * MoveType TREADS
 * Medium tank is all-around medium strong unit
 */
public class MedTank extends Tank {

	/**
	 * Constructs a Medium Tank
	 * sets primary weapon to HEAT, secondary to MG
	 * @param owner owner of the Unit
	 */
	public MedTank(Player owner) {
		super(owner);
		setWeapon(0, WeaponType.HEAT);
		setWeapon(1, WeaponType.MG);
	}
	@Override
	public int getBuildCost() {
		return 1400;
	}

	@Override
	public double getBaseArmorResistance() {
		//10% resistance
		return 0.90;
	}
}
