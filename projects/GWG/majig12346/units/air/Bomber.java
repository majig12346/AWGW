package majig12346.units.air;

import majig12346.PassiveFlag.MoveType;
import majig12346.Player;
import majig12346.terrain.Terrain;
import majig12346.units.Air;
import majig12346.units.Unit;
import majig12346.weapons.WeaponType;

/**
 * The bomber is an air unit
 * Gets HEAT (direct A2G)
 * Costs 2200
 * Costs 5 fuel/turn to stay airborne
 * Good overall against ground units -- cannot hit air units
 */
public class Bomber extends Air {

	/**
	 * Constructs a bomber
	 * sets primary weapon to HEAT,
	 * @param owner owner of the unit
	 */
	public Bomber(Player owner) {
		super(owner);
		setWeapon(0, WeaponType.HEAT);
	}

	@Override
	public int getBuildCost() {
		return 2200;
	}

	@Override
	public double getBaseArmorResistance() {
		//5% general resistance
		return 0.95;
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
	@Override
	public boolean couldTarget(Unit toCheck, Terrain hypothetical) { //cannot target Air units
		return super.couldTarget(toCheck, hypothetical)&&!MoveType.AIR.equals(toCheck.getMovementType());
	}
}
