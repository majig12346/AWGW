package majig12346.terrain;
import java.util.ArrayList;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Location;
import majig12346.PassiveFlag.MoveType;
import majig12346.TerrainGrid;

/**
 * A unit is a Location. This is the base class for all the other Terrains to extend
 */
public abstract class Terrain extends Location{
	
	 /**
     * Constructs a Terrain with given row and column coordinates.
     * @param r the row
     * @param c the column
     */
	public Terrain(int r, int c, TerrainGrid<Actor> hostGrid) {
		super(r, c);
		this.hostGrid = hostGrid;
	}
	/**
	 * @return the defense points of this Terrain
	 * non-air units occupying the terrain gain a 10% defense bonus per defense point
	 */
	public abstract int getDefense();
	/**
	 * @return the cost of mobility points that a unit of movementType would take 
	 * to traverse this terrain
	 */
	public double getMoveCost(MoveType movementType){
		switch(movementType){
		case AIR:return getMoveCostAir();
		case SEA:return getMoveCostBoat();
		case FOOT:return getMoveCostFoot();
		case TIRES:return getMoveCostTires();
		case TREADS:return getMoveCostTreads();
		default:throw new IllegalArgumentException();
		}
	}
	/**
	 * @return the cost of mobility points that a unit of movementType.FOOT would take 
	 * to traverse this terrain -- override to set
	 */
	protected abstract double getMoveCostFoot();
	
	/**
	 * @return the cost of mobility points that a unit of movementType.TIRES would take 
	 * to traverse this terrain -- override to set
	 */
	protected abstract double getMoveCostTires();
	
	/**
	 * @return the cost of mobility points that a unit of movementType.TREADS would take 
	 * to traverse this terrain -- override to set
	 */
	protected abstract double getMoveCostTreads();
	
	/**
	 * @return the cost of mobility points that a unit of movementType.AIR would take 
	 * to traverse this terrain -- override to set
	 */
	protected double getMoveCostAir(){
		return 1;
	}
	/**
	 * @return the cost of mobility points that a unit of movementType.SEA would take 
	 * to traverse this terrain -- override to set
	 */
	protected double getMoveCostBoat(){
		return 999;
	}
	
	/**
	 * @return Manhattan distance to other terrain t
	 */
	public int distanceTo(Terrain t){
		return Math.abs(this.getRow()-t.getRow())+Math.abs(this.getCol()-t.getCol());
	}
	
	@Override
	public Location getAdjacentLocation(int direction) {
		int adjustedDirection = (direction + HALF_RIGHT / 2) % FULL_CIRCLE;
        if (adjustedDirection < 0)
            adjustedDirection += FULL_CIRCLE;

        adjustedDirection = (adjustedDirection / HALF_RIGHT) * HALF_RIGHT;
        int dc = 0;
        int dr = 0;
        if (adjustedDirection == EAST)
            dc = 1;
        else if (adjustedDirection == SOUTHEAST)
        {
            dc = 1;
            dr = 1;
        }
        else if (adjustedDirection == SOUTH)
            dr = 1;
        else if (adjustedDirection == SOUTHWEST)
        {
            dc = -1;
            dr = 1;
        }
        else if (adjustedDirection == WEST)
            dc = -1;
        else if (adjustedDirection == NORTHWEST)
        {
            dc = -1;
            dr = -1;
        }
        else if (adjustedDirection == NORTH)
            dr = -1;
        else if (adjustedDirection == NORTHEAST)
        {
            dc = 1;
            dr = -1;
        }
        try{
		return hostGrid.getLocationArray()[getRow()+dr][getCol()+dc];
        }catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
    }
	public ArrayList<Terrain> getAllAdjacentTerrains(){
		System.out.println("a");
		ArrayList<Terrain> adjacent = new ArrayList<Terrain>();
		for(int dirs = 0;dirs<360;dirs+=45){
			Terrain t = (Terrain) getAdjacentLocation(dirs);
			if(t != null){
				adjacent.add(t);
			}
		}
		return adjacent;
	}
	protected TerrainGrid hostGrid;
}
