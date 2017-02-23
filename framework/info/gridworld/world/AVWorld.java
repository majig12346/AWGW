package info.gridworld.world;

import info.gridworld.grid.Grid;
import info.gridworld.gui.WorldFrame;
import majig12346.TerrainGrid;

public class AVWorld extends MouseWorld {

	public AVWorld(){}
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
		wf.control.display.avw = this;
		wf.control.display.setCurrentLocation(getLocationWhenClicked());
		wf.control.editLocation();
		resetClickedLocation();
		if(!wf.control.display.shouldBeHighlighted.contains(clickedLocation)){
			wf.control.display.shouldBeHighlighted.clear();
		}
		setMessage("Currently selected: none.\n\nUse mouse to click things. DO NOT use arrow keys or Enter");
	}

}
