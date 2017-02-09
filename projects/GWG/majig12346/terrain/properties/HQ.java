/**
 * 
 */
package majig12346.terrain.properties;

import info.gridworld.actor.Actor;
import majig12346.Player;
import majig12346.TerrainGrid;

/**
 * @author George
 *
 */
public class HQ extends Property {

	/**
	 * @param r row
	 * @param c column
	 * @param hostGrid hostGrid
	 * @param owner owner of this HQ
	 */
	public HQ(int r, int c, TerrainGrid<Actor> hostGrid, Player owner) {
		super(r, c, hostGrid, owner);
	}

}
