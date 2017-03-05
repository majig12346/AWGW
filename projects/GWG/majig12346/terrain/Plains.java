package majig12346.terrain;

import info.gridworld.actor.Actor;
import majig12346.TerrainGrid;

public class Plains extends Terrain{


	public Plains(int r, int c, TerrainGrid<Actor> hostGrid) {
		super(r, c, hostGrid);
	}

	@Override
	public int getDefense() {
		return 1;
	}

	@Override
	protected double getMoveCostFoot() {
		return 1;
	}

	@Override
	protected double getMoveCostTires() {
		return 2;
	}

	@Override
	protected double getMoveCostTreads() {
		return 1;
	}

}
