package majig12346;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JOptionPane;

import info.gridworld.actor.Actor;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;
import info.gridworld.gui.WorldFrame;
import info.gridworld.world.AVWorld;
import info.gridworld.world.MouseWorld;
import majig12346.CO.TestCO;
import majig12346.terrain.*;
import majig12346.terrain.properties.*;
import majig12346.units.Unit;
import majig12346.units.land.Infantry;

public class Runner {
	public static void main(String[] args){
		AVWorld avw = new AVWorld();
		Player p1 = new Player(new TestCO(),9999,null);
		Player p2 = new Player(new TestCO(), 9999, Color.green);
		TerrainGrid<Actor> g = new TerrainGrid<Actor>(10,10);
		customFill(g,p1,p2);
		avw.setGrid(g);
		avw.show();
		//I will fix this
		while(true){
			avw.go();
		}
	}

	
	
	
	private static void customFill(TerrainGrid g, Player p1, Player p2){
		Infantry inf1 = new Infantry(p1);
		fillTerrainGrid(g);
		Property prop1 = (Property) g.getLocationArray()[1][1];
		prop1.setOwner(p2);
		inf1.putSelfInGrid(g, g.getLocationArray()[1][1]);
		System.out.println(g.get(g.getLocationArray()[1][1]));
		System.out.println("no crashes yet");
		
		Infantry inf2 = new Infantry(p2);
		inf2.putSelfInGrid(g, g.getLocationArray()[2][2]);
		System.out.println(g.getLocationArray()[2][2].getClass().getName());
		System.out.println(g.getLocationArray()[1][1].getClass().getName());
	}
	
	
	public static void fillTerrainGrid(TerrainGrid g){

		//String fileName = JOptionPane.showInputDialog("file name");
		String fileName = "projects/GWG/resources/map.txt";
		try{
			Scanner sc = new Scanner(new File(fileName));
			if(!(g.getNumRows()==sc.nextInt()&&g.getNumCols()==sc.nextInt())){
				throw new Exception("map size != grid size");
			}
			//hmm??? good
			sc.nextLine();
			for(int r = 0;r<g.getNumRows();r++){
				
				String[] rowStringForm = sc.nextLine().split(",");
				//System.out.println("good\n");
				//System.out.print(new ArrayList<String>(Arrays.asList(rowStringForm)));
				for(int c = 0;c<g.getNumCols();c++){
					g.getLocationArray()[r][c] = makeTerrain(r, c, g, rowStringForm[c]);
				}
			}
		}catch(Exception e){
			System.out.println("error reading file");
			e.printStackTrace();
		}
	}
	public static Terrain makeTerrain(int r, int c, TerrainGrid<Actor> hostGrid, String terrainType) throws NoSuchMethodException, SecurityException{
		switch (terrainType) {
		case "Beach":
			return new Beach(r, c, hostGrid);
		case "Plains":
			return new Plains(r,c,hostGrid);
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
			return new AirPort(r, c, hostGrid,null);
		case "SeaPort":
			return new SeaPort(r, c, hostGrid,null);
		case "Barracks":
			return new Barracks(r, c, hostGrid,null);
		case "Property":
			return new Property(r, c, hostGrid,null);
		case "HQ":
			return new HQ(r,c,hostGrid,null);
		default:
			return new TestTerrain(r, c, hostGrid);
		}
	}
}
