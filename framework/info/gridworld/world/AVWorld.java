package info.gridworld.world;

import info.gridworld.grid.Grid;
import info.gridworld.gui.WorldFrame;

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
		WorldFrame wf = (WorldFrame) frame;
		//wf.control.display.originalLocation = getLocationWhenClicked();
		wf.control.display.avw = this;
		wf.control.display.setCurrentLocation(getLocationWhenClicked());
		wf.control.editLocation();
		resetClickedLocation();
	}

}
