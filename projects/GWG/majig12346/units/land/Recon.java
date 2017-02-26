package majig12346.units.land;

import majig12346.PassiveFlag.MoveType;
import majig12346.terrain.Terrain;
import majig12346.Player;
import majig12346.units.Unit;
import majig12346.weapons.WeaponType;

/**
 * Gets NONE, MG (infinite ammo machine gun)
 * Costs 400
 *  MoveType TIRES
 *  Does well against infantry, poorly vs armor -- quite mobile
 */
public class Recon extends Unit{

	/**
	 * Constructs a Recon truck
	 * sets primary weapon to NONE, seconday to MG
	 * @param owner
	 */
	public Recon(Player owner) {
		super(owner);
		setWeapon(0, WeaponType.NONE); //has no primary -- seconday has infinite ammo
		setWeapon(1, WeaponType.MG);
	}

	@Override
	public int getBuildCost() {
		return 400;
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
		return MoveType.TIRES;
	}
	@Override
	public boolean couldTarget(Unit toCheck, Terrain hypothetical) { //cannot target jet fighters
		return super.couldTarget(toCheck, hypothetical)&&!toCheck.isJet();
	}
}
