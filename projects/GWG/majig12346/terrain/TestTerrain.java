package majig12346.terrain;

import info.gridworld.actor.Actor;
import majig12346.TerrainGrid;

public class TestTerrain extends Terrain {

	public TestTerrain(int r, int c, TerrainGrid<Actor> hostGrid) {
		super(r, c, hostGrid);
	}

	@Override
	protected double getMoveCostFoot() {
		return 1;
	}

	@Override
	protected double getMoveCostTires() {
		return 1;
	}

	@Override
	protected double getMoveCostTreads() {
		return 1;
	}
	@Override
	protected double getMoveCostBoat() {
		return 1;
	}

	@Override
	public int getDefense() {
		// TODO Auto-generated method stub
		return 0;
	}

}
