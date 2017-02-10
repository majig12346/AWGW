package majig12346.units.air;

import java.util.ArrayList;

import majig12346.Player;
import majig12346.units.Air;
import majig12346.units.Carry;
import majig12346.units.Unit;
import majig12346.weapons.WeaponType;

/**
 * The DropShip is a flying transport unit
 * Gets NONE (unarmed)
 * Costs 3000
 * Can carry 4 Infantry
 * Costs 5 fuel/turn to stay airborne
 */
public class DropShip extends Air implements Carry {

	/**
	 * Constructs a DropShip
	 * sets primary weapon to NONE (unarmed)
	 * @param owner owner of the unit
	 */
	public DropShip(Player owner) {
		super(owner);
		setWeapon(0, WeaponType.NONE); // unarmed
		carried = new ArrayList<Unit>();
	}

	private ArrayList<Unit> carried;
	@Override
	public ArrayList<Unit> getUnits() {
		return carried;
	}

	@Override
	public void resupply() {
		//cannot resupply

	}

	@Override
	public boolean canResupply() {
		return false;
	}

	@Override
	public boolean canCarry(Unit u) {
		switch(u.getMovementType()){
		case FOOT:
			return true;
		default:
			return false;
		}
	}

	@Override
	public int getBuildCost() {
		return 3000;
	}

	@Override
	public double getBaseArmorResistance() {
		return 1;
	}
	@Override
	public int getDailyCost() {
		return 5;
	}
	@Override
	public int getMaxCapacity() {
		return 4;
	}

}
