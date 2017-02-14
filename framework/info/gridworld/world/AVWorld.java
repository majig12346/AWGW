package info.gridworld.world;

import info.gridworld.grid.Grid;
import info.gridworld.gui.WorldFrame;
import majig12346.TerrainGrid;

public class AVWorld extends MouseWorld {

	public AVWorld(){

	}
	public AVWorld(Grid g) {
		super(g);
		// TODO Auto-generated constructor stub
		
	}
	@Override
	public void show() {
		super.show();
	}
	public void go(){
		System.out.println("starting go\n");
		WorldFrame wf = (WorldFrame) frame;
		//wf.control.display.originalLocation = getLocationWhenClicked();
		wf.control.display.avw = this;
		wf.control.display.setCurrentLocation(getLocationWhenClicked());
		wf.control.editLocation();
		resetClickedLocation();
		setMessage("Use mouse or arrowkeys+enter+esc");
	}

}
