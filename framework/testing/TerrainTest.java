package testing;

import majig12346.TerrainGrid;
import majig12346.terrain.TestTerrain;
import info.gridworld.actor.Actor;
import info.gridworld.world.AVWorld;

public class TerrainTest {
	public static void main(String[] args){
		AVWorld avw = new AVWorld();
		TerrainGrid<Actor> tg = new TerrainGrid<>(10, 10);
		TestTerrain tt1 = new TestTerrain(0, 0, tg);
		TestTerrain tt2 = new TestTerrain(2, 2, tg);
		tg.getLocationArray()[0][0] = tt1;
		tg.getLocationArray()[2][2] = tt2;
		System.out.println(tt1.distanceTo(tt2));
		
	}
}
