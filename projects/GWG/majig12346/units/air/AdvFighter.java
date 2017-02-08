package majig12346.units.air;

import majig12346.Player;
import majig12346.terrain.Terrain;
import majig12346.units.Stealth;
import majig12346.units.Unit;
import majig12346.weapons.WeaponType;

/**
 * the advanced fighter is a stealth aircraft
 *Gets MISSILE (direct A2A) , ROTARY (ranged A2A)
 * Costs 3000
 *  Costs 7 fuel/turn to stay airborne, costs 5 extra when hidden (12 total /turn)
 *  Highly mobile stealth air superiority fighter with fuel issues -- seems familiar
 */
public class AdvFighter extends Stealth {

	/**
	 * Constructs an advanced fighter.
	 * sets primary weapon to MISSILE, secondary to ROTARY
	 * @param owner owner of the unit
	 */
	public AdvFighter(Player p) {
		super(p);
		setWeapon(0, WeaponType.MISSILE);
		setWeapon(1, WeaponType.ROTARY);
	}
	
	
	//this aircraft has fuel issues, but fast
	@Override
	public int getExtraDailyCost() {
		return 5;
	}
	@Override
	public int getDailyCost() {
		return 7;
	}
	@Override
	public int getBuildCost() {
		return 3000;
	}

	@Override
	public double getBaseArmorResistance() {
		//10% of damage misses the aircraft
		return 0.90;
	}
	/**
     * 90% of small arms miss, HMG lacks net velocity -- 90% damage reduction
	 * missiles have limited effectiveness against stealth, 30% damage reduction
     */
	@Override
	public double resist(double damage, String source) {
		//
		switch(source){
		case WeaponType.RIFLE:
			return damage*0.1;
		case WeaponType.MG:
			return damage*0.1;
		case WeaponType.HMG:
			return damage*0.1;
		case WeaponType.MISSILE:
			return damage*0.7;
		case WeaponType.MISSILES:
			return damage*0.7;
		default:
			return damage;
		}
	}


}
