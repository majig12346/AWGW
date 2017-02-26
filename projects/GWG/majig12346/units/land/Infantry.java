package majig12346.units.land;

import majig12346.PassiveFlag.MoveType;
import majig12346.Player;
import majig12346.terrain.Terrain;
import majig12346.terrain.properties.Property;
import majig12346.units.Unit;
import majig12346.weapons.Suit;
import majig12346.weapons.WeaponType;

/**
 * An Infantry is a Unit
 *Gets NONE, RIFLE (infinite ammo rifle)
 *	Costs 100
 *	Can capture properties
 *	MoveType FOOT
 *	Staple expendable foot soldier
 */
public class Infantry extends Unit {
	
	/**
	 *Constructs an Infantry owned by owner
	 *also sets the correct weapons
	*/
	public Infantry(Player p) {
		super(p);
		setWeapon(0, WeaponType.NONE); //no primary -- secondary has infinite ammo
		setWeapon(1, WeaponType.RIFLE);
	}
	public Infantry(Player p, Suit suit) {
		this(p);
		this.setSuit(suit);
	}

	@Override
	public int getBuildCost() {
		return 100;
	}

	@Override
	public double getBaseArmorResistance() {
		//0% resistance
		return 1;
	}

	@Override
	public void outOfFuel() {
		//do nothing
	}

	@Override
	public MoveType getMovementType() {
		return MoveType.FOOT;
	}
	
	/**
     * Precondition: Infantry is on an enemy
     * owned or neutral Property
     * Ticks the cap timer of the Property with tickCapTimer()
     */
	public void capture(){
		System.out.println("capturing city");
		Terrain t = (Terrain) this.getLocation();
		if(t instanceof Property){
			Property p = (Property)t;
			System.out.println("capTimer = "+p.getCapTimer());
			p.tickCapTimer(this);
			System.out.println("property's capture timer down to "+p.getCapTimer());
		}
		this.immobilize();
	}
	@Override
	public boolean couldTarget(Unit toCheck, Terrain hypothetical) { //cannot target jet fighters, boats
		return super.couldTarget(toCheck, hypothetical)&&!toCheck.isJet()&&!MoveType.SEA.equals(toCheck.getMovementType());
	}

}
