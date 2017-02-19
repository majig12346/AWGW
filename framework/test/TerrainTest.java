package test;

import info.gridworld.actor.Actor;

import org.junit.Assert;
import org.junit.Test;

import majig12346.PassiveFlag.MoveType;
import majig12346.Player;
import majig12346.TerrainGrid;
import majig12346.CO.TestCO;
import majig12346.terrain.*;
import majig12346.terrain.properties.*;

public class TerrainTest {

	@Test
	public void test() {
		TerrainGrid<Actor> tg = new TerrainGrid<Actor>(5, 5);
		Player p1 = new Player(new TestCO(), 0, null);
		Terrain plains1 = new Plains(0,0,tg);
		Terrain hq1 = new HQ(2,2,tg,p1);
		
		
		Assert.assertEquals("plains (0,0) to hq (2,2)",4, plains1.distanceTo(hq1));
		Assert.assertEquals("hq (2,2) to plains(0,0)",4, hq1.distanceTo(plains1));
		Assert.assertEquals("FOOT movecost",1, plains1.getMoveCost(MoveType.FOOT),0);
		
		
	}

}
