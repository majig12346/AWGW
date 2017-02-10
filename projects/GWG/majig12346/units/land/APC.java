package majig12346.units.land;

import java.util.ArrayList;

import majig12346.PassiveFlag.MoveType;
import majig12346.Player;
import majig12346.units.Carry;
import majig12346.units.Unit;
import majig12346.weapons.WeaponType;

/**
 * The armored personnel carrier is an unarmed support unit
 * Gets NONE (unarmed)
 * Costs 500
 *Can carry 1 Infantry, can drop off into any valid (for infantry) Manhattan 
 *adjacent tile (not in same turn)
 *Can resupply units (Manhattan adjacent allied units only)
 */
public class APC extends Unit implements Carry {

	/**
	 * Constructs an armored personnel carrier
	 * sets primary weapon to NONE (unarmed)
	 * @param owner owner of the unit
	 */
	public APC(Player owner) {
		super(owner);
		setWeapon(0, WeaponType.NONE); //unarmed
		carried = new ArrayList<Unit>();
	}

	private ArrayList<Unit> carried;
	@Override
	public ArrayList<Unit> getUnits() {
		return carried;
	}

	@Override
	public void resupply() {
		ArrayList<Unit> targetUnits = new ArrayList<Unit>();
		try{
			for(int i=0;i<360;i+=90){
				targetUnits.add((Unit) getGrid().get(getLocation().getAdjacentLocation(i)));
			}
		}catch(ClassCastException e){
			System.out.println("class cast error resupplying from APC at "
					+ "(r,c) : ("+getLocation().getRow()+", "+getLocation().getCol()+")");
		}
		for(Unit u:targetUnits){
			if(this.getOwner()==u.getOwner()){
				u.setAmmo(u.getWeapons()[0].getMaxAmmo());
				u.setFuel(99);
			}
		}

	}

	@Override
	public double resist(double damage, String source) {
		//40% small arms resistance
		switch(source){
		case WeaponType.RIFLE:
			return damage*0.6;
		case WeaponType.MG:
			return damage*0.6;
		default:
			return damage;
		}
	}

	@Override
	public boolean canCarry(Unit u) {
		//APC can carry 1 infantry or mechanized infantry
		switch(u.getUnitType()){
		case INFANTRY:
			return true;
		case MECH:
			return true;
		default:
			return false;

		}
	}

	@Override
	public int getBuildCost() {
		return 500;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canResupply() {
		return true;
	}

}
