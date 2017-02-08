package majig12346;

import java.awt.Color;
import java.io.File;
import java.util.Scanner;

import javax.swing.JOptionPane;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import majig12346.CO.TestCO;
import majig12346.terrain.*;
import majig12346.terrain.properties.*;
import majig12346.units.Unit;

public class Runner {
	public static void main(String[] args){
		Player a = new Player(new TestCO(), 1000, Color.GREEN);
		Player b = new Player(new TestCO(), 1000, Color.RED);
		Grid<Unit> g = new TerrainGrid<Unit>(10,10);
		
	}
	
	 public static void fillTerrainGrid(TerrainGrid g){
		
		 String fileName = JOptionPane.showInputDialog("file name");
		 try{
			 Scanner sc = new Scanner(new File(fileName));
			 if(!(g.getNumRows()==sc.nextInt()&&g.getNumCols()==sc.nextInt())){
				 throw new Exception("map size != grid size");
			 }
			 for(int r = 0;r<g.getNumRows();r++){
				 String[] rowStringForm = sc.nextLine().split(",");
				 for(int c = 0;c<g.getNumCols();c++){
					 g.getLocationArray()[r][c] = makeTerrain(r, c, g, rowStringForm[c]);
				 }
			 }
		 }catch(Exception e){
			 System.out.println(e.getStackTrace());
		 }
	 }
	 public static Terrain makeTerrain(int r, int c, TerrainGrid<Actor> hostGrid, String terrainType) throws NoSuchMethodException, SecurityException{
		 switch (terrainType) {
		 case "Beach":
			return new Beach(r, c, hostGrid);
		 case "Bridge":
				return new Bridge(r, c, hostGrid);
		 case "Forest":
				return new Forest(r, c, hostGrid);
		 case "Mountain":
				return new Mountain(r, c, hostGrid);
		 case "Ocean":
				return new Ocean(r, c, hostGrid);
		 case "River":
				return new River(r, c, hostGrid);
		 case "Road":
				return new Road(r, c, hostGrid);
		 case "AirPort":
				return new AirPort(r, c, hostGrid);
		 case "SeaPort":
				return new SeaPort(r, c, hostGrid);
		 case "Barracks":
				return new Barracks(r, c, hostGrid);
		 case "Property":
				return new Property(r, c, hostGrid);
		default:
			return new TestTerrain(r, c, hostGrid);
		}
	 }
}
