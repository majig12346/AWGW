package majig12346.units;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import majig12346.Player;
import majig12346.terrain.Terrain;
/**
 * abstract Stealth2 represents stealth sea units (currently just submarine)
 * Not an abstract class because I need something to switch the sprite to blank whitespace
 * when a stealth unit is hidden
 */
public abstract class Stealth2 extends Sea{
	/**
	 * calls super(Player) from child classes
	 * don't invoke this
	 * @param owner
	 */
	public Stealth2(Player p) {
		super(p);
	}
	private boolean hidden;
	/**
	 * Hides the stealth unit -- consumes extra daily fuel.
	 * hides sprite by replacing self in grid
	 * by version casted to Stealth2
	 */
	public void hide(){
		this.hiddenUnit = this;
		Grid<Actor> gr = getGrid();
		Terrain t = (Terrain) getLocation();
		this.removeSelfFromGrid();
		Stealth2 me = (Stealth2)(this);
		me.putSelfInGrid(gr, t);
		this.hidden = true;
	}
	/**
	 * reveals the hidden unit -- does opposite of hide()
	 */
	public void unHide(){
		Grid<Actor> gr = getGrid();
		Terrain t = (Terrain) getLocation();
		this.removeSelfFromGrid();
		this.hiddenUnit.putSelfInGrid(gr, t);
		this.hiddenUnit = null;
		this.hidden = false;
	}
	/**
	 * @return whether or not the unit is currently hidden
	 */
	public boolean isHidden(){
		return this.hidden;
	}
	/**
	 * @return extra amount of daily fuel the unit consumes when hidden
	 */
	public abstract int getExtraDailyCost();
	@Override
	public void deductDailyCost(){
		if(this.isHidden()){
			this.setFuel(getFuel()-getExtraDailyCost());
		}
		this.setFuel(getFuel()-this.getDailyCost());
		if(getFuel()<=0){
			setFuel(0);
		}
	}
	private Sea hiddenUnit;
}
