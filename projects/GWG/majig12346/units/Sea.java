package majig12346.units;

import majig12346.Player;
import majig12346.PassiveFlag.MoveType;

/**
 * abstract Sea represents all sea units
 * Sea units use some fuel every day to stay afloat and 
 * will sink without fuel.
 * These get MoveType.SEA
 */
public abstract class Sea extends Unit{

	/**
	 * calls super(Player) from child classes
	 * don't invoke this
	 * @param owner
	 */
	public Sea(Player owner) {
		super(owner);
	}
	@Override
	public MoveType getMovementType() {
		return MoveType.SEA;
	}
	
	@Override
	public void outOfFuel() {
		//TODO sinking animation
		this.selfDestruct();
	}


}
