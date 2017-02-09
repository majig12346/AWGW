package majig12346.terrain.properties;

import info.gridworld.actor.Actor;

import javax.swing.JOptionPane;

import majig12346.PassiveFlag.COFlag;
import majig12346.Player;
import majig12346.TerrainGrid;
import majig12346.terrain.Terrain;
import majig12346.units.Unit;
import majig12346.units.land.Infantry;

/**
 * Property represents all capturable Terrains
 * Instantiated, it is a plain city. 
 * Provides 30% defense boost to occupying land units. 
 * Units of MoveType FOOT, TIRES, TREADS costs 1 mobility to traverse. SEA costs 999.
 */
public class Property extends Terrain{

	/**
	  * Constructs a Property with given row and column coordinates.
	  * @param r the row
	  * @param c the column
	  * @param owner owner of property, null if neutral
	  */
	public Property(int r, int c, TerrainGrid<Actor> hostGrid, Player owner) {
		super(r, c, hostGrid);
		this.setOwner(owner);
	}

	//cap timer
	public static final int FULL_CAP_TIMER= 200;
	private int capTimer;
	/**
	 * @return the current capture timer
	 */
	public int getCapTimer() {
		return this.capTimer;
	}
	/**
	 * resets the capture timer to the FULL_CAP_TIMER
	 */
	public void resetCapTimer() {
		this.capTimer = Property.FULL_CAP_TIMER;
	}

	//capture mechanics
	private Player owner;
	/**
	 * @return the current owner of the property
	 * If the property is neutral, it returns null.
	 */
	public Player getOwner() {
		return this.owner;
	}
	
	/**
	 * Sets the owner of the property. 
	 * Should only be invoked internally by tickCapTimer()
	 * Precondition: Player p is not null
	 */
	private void setOwner(Player p){
		this.owner = p;
		if(null!=owner){
			p.getPropertiesOwned().add(this);
		}
	}
	
	/**
	 * Decrements the capture timer by the health of the
	 *  occupying infantry unit
	 * If capTimer falls below or equal to 0, the property is captured.
	 */
	public void tickCapTimer(Unit u){
		//currently, only infantry can cap. CO powers will probably allow other units to cap later.
		if(!(u instanceof Infantry)){
			JOptionPane.showMessageDialog(null, "not sure how you invoked this method without "
					+ "an infantry unit...see line 39 of Property");
			System.exit(-1);
		}
		//actual code
		//checks for extra capturing points, to be implemented in CO
		int tickBy = (int) u.getOwner().CO.passive(u.getHealth(), COFlag.CAPTURE, u.getUnitType()); 
		this.capTimer-=tickBy;
		if(capTimer<=0){
			getOwner().getPropertiesOwned().remove(this);
			this.setOwner(u.getOwner());
		}
	}
	
	@Override
	protected double getMoveCostFoot() {
		return 1;
	}
	@Override
	protected double getMoveCostTreads() {
		return 1;
	}
	@Override
	protected double getMoveCostTires() {
		return 1;
	}
	@Override
	public int getDefense() {
		return 3;
	}
}
