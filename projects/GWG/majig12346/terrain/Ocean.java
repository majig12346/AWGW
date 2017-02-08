package majig12346.terrain;

import info.gridworld.actor.Actor;
import majig12346.TerrainGrid;

/**
 *Units of MoveType SEA expend 1 mobility to traverse.
 * FOOT, TREADS, TIRES take 999.
 *Provides NO defensive cover.
 */
public class Ocean extends Terrain{

	/**
	 * Constructs an Ocean with given row and column coordinates.
	 * @param r the row
	 * @param c the column
	 */
	public Ocean(int r, int c, TerrainGrid<Actor> hostGrid) {
		super(r, c, hostGrid);
	}

	@Override
	public int getDefense() {
		return 0;
	}

	@Override
	protected double getMoveCostFoot() {
		return 999;
	}

	@Override
	protected double getMoveCostTires() {
		return 999;
	}

	@Override
	protected double getMoveCostTreads() {
		return 999;
	}

}
