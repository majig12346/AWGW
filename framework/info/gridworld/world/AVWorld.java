package info.gridworld.world;

import info.gridworld.grid.Grid;
import info.gridworld.gui.WorldFrame;
import majig12346.Runner;
import majig12346.TerrainGrid;

public class AVWorld extends MouseWorld {
	public AVWorld(){
	}
	public AVWorld(Grid g) {
		super(g);
		// TODO Auto-generated constructor stub

	}
	@Override
	public void setGrid(Grid g) {
		super.setGrid(g);
		if(g instanceof TerrainGrid){
			((TerrainGrid) g).hostWorld = this;
		}
	}
	public void go(){
		System.out.println("starting go\n");
		getWorldFrame().control.display.avw = this;
		getWorldFrame().control.display.setCurrentLocation(getLocationWhenClicked());
		getWorldFrame().control.editLocation();
		resetClickedLocation();
		if(!getWorldFrame().control.display.shouldBeHighlighted.contains(clickedLocation)){
			getWorldFrame().control.display.shouldBeHighlighted.clear();
		}

	}
	public WorldFrame getWorldFrame() {
		if(null!=frame){
			return (WorldFrame) frame;
		}else{
			return null;
		}
	}

}
