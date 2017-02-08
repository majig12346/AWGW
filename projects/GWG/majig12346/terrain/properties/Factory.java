package majig12346.terrain.properties;

import info.gridworld.actor.Actor;

import java.lang.reflect.Constructor;
import java.util.Set;

import majig12346.TerrainGrid;
import majig12346.PassiveFlag.UnitType;
import majig12346.units.Unit;

/**
 * A Factory is a capturable Terrain -- a Property
 * Factories build Units. Different kinds build different types of Units. 
 */
public abstract class Factory extends Property {

	/**
	  * Constructs a Factory with given row and column coordinates.
	  * @param r the row
	  * @param c the column
	  */
	public Factory(int r, int c, TerrainGrid<Actor> hostGrid) {
		super(r, c, hostGrid);
	}
	protected Set<Constructor<? extends Unit>> buildableUnits;
	/**
	  * @return a set of constructors for units that 
	  * can be built on this factory
	  */
	public Set<Constructor<? extends Unit>> getBuildableUnits() {
		return this.buildableUnits;
	}
}
