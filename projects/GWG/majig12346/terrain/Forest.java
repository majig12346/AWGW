package majig12346.terrain;

import info.gridworld.actor.Actor;
import majig12346.TerrainGrid;

/**
 * A Forest is a Terrain. 
 * Units of MoveType FOOT, TIRES, TREADS costs 1.5 mobility to traverse. SEA costs 999.
 * Provides bonus 20% defense to occupying land Units
 */
public class Forest extends Terrain {

	 /**
	  * Constructs a Forest with given row and column coordinates.
	  * @param r the row
	  * @param c the column
	  */
	public Forest(int r, int c, TerrainGrid<Actor> hostGrid) {
		super(r, c, hostGrid);
	}

	@Override
	public int getDefense() {
		return 0;
	}

	@Override
	protected double getMoveCostFoot() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected double getMoveCostTires() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected double getMoveCostTreads() {
		// TODO Auto-generated method stub
		return 0;
	}

}
