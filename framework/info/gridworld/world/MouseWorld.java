/**
	 * MouseWorld allows the location clicked on the grid to be returned.
	 * 
	 * @author Ben Eisner 2015
	 */
package info.gridworld.world;
import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class MouseWorld extends ActorWorld {
	private Location clickedLocation;

	public MouseWorld() {
		super();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MouseWorld(Grid g) {
		super(g);
	}

	public boolean locationClicked(Location loc) {
		clickedLocation = loc;
		return true;
	}

	public void resetClickedLocation() {
		clickedLocation = null;
	}

	public Location getLocationWhenClicked() {
		Location loc = null;
		//setMessage("Waiting for Click");
		while (loc == null) {
			loc = clickedLocation;
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//setMessage("Ready.");
		return loc;
	}

}
