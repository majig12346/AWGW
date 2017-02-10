package majig12346.units.sea;

import java.util.ArrayList;

import majig12346.Player;
import majig12346.units.Carry;
import majig12346.units.Sea;
import majig12346.units.Unit;
import majig12346.weapons.WeaponType;

/**
 * A Lander is an unarmed transport sea unit
 * Costs 1200
 * Can carry 2 land units (eg 1 tank and 1 APC carrying 1 infantry), crosses ocean
 * Costs 1 fuel/turn to stay afloat
 * Is the only sea unit that can go on beaches
 * Does not have resupply capabilities
 */
public class Lander extends Sea implements Carry {

	/**
	 * Constructs a Lander
	 * Sets primary weapon to NONE (unarmed)
	 * @param owner owner of the unit
	 */
	public Lander(Player owner) {
		super(owner);
		setWeapon(0, WeaponType.NONE);
		carried = new ArrayList<Unit>();
	}

	private ArrayList<Unit> carried;
	@Override
	public ArrayList<Unit> getUnits() {
		return carried;
	}
	@Override
	public int getMaxCapacity() {
		return 2;
	}
	@Override
	public void resupply() {
		//nope
	}

	@Override
	public boolean canCarry(Unit u) {
		//can carry 2 land units
		switch(u.getMovementType()){
		case TREADS:
			return true;
		case TIRES:
			return true;
		case FOOT:
			return true;
		default:
			return false;
		}
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
	public boolean canResupply() {
		return false;
	}

}
