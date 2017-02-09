package majig12346.terrain.properties;

import info.gridworld.actor.Actor;

import java.lang.reflect.Constructor;
import java.util.HashSet;

import majig12346.Player;
import majig12346.TerrainGrid;
import majig12346.units.Unit;
import majig12346.units.air.*;

/**
 * A Barracks is a factory
 * Barracks builds land Units. 
 */
public class AirPort extends Factory{

	/**
	  * Constructs a Barracks with given row and column coordinates.
	  * all land Unit constructors are added to buildableUnits
	  * @param r the row
	  * @param c the column
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	  */
	public AirPort(int r, int c, TerrainGrid<Actor> hostGrid,Player owner)throws NoSuchMethodException, SecurityException {
		super(r, c, hostGrid,owner);
		this.buildableUnits = new HashSet<Constructor<? extends Unit>>();
		buildableUnits.add(TCopter.class.getConstructor(Player.class));
		buildableUnits.add(BCopter.class.getConstructor(Player.class));
		buildableUnits.add(SCopter.class.getConstructor(Player.class));
		buildableUnits.add(Fighter.class.getConstructor(Player.class));
		buildableUnits.add(Bomber.class.getConstructor(Player.class));
		buildableUnits.add(StealthBomber.class.getConstructor(Player.class));
		buildableUnits.add(AdvFighter.class.getConstructor(Player.class));
		buildableUnits.add(CAS.class.getConstructor(Player.class));
		buildableUnits.add(DropShip.class.getConstructor(Player.class));
		buildableUnits.add(JSF.class.getConstructor(Player.class));
	}

}
