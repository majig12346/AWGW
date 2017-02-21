package majig12346;

import java.awt.Color;
import java.io.File;
import java.util.Scanner;

import info.gridworld.actor.Actor;
import info.gridworld.world.AVWorld;
import majig12346.CO.TestCO;
import majig12346.terrain.*;
import majig12346.terrain.properties.*;
import majig12346.units.air.BCopter;
import majig12346.units.air.CAS;
import majig12346.units.land.Infantry;
import majig12346.units.land.Tank;
import majig12346.units.sea.Carrier;


public class Runner {
	public static Player[] players;
	public static void main(String[] args){
		//System.out.println("Currently 6151355 PLANCK_TIMEs behind! Are we on "
		//		+ "5nm or is Quantum tunneling somehow an issue "
		//		+ "on Skylake's 14nm? Wait a PLANCK_TIME,"
		//		+ " this is a Core 2 Duo!");
		AVWorld avw = new AVWorld();
		players = new Player[4];
		Player p1 = new Player(new TestCO(),9999,null);
		Player p2 = new Player(new TestCO(), 9999, new Color(100, 200, 255));
		players[p1.ID] = p1;
		players[p2.ID] = p2;
		TerrainGrid<Actor> g = new TerrainGrid<Actor>(10,10);
		fillTerrainGrid(g);
		customFill(g,p1,p2);
		avw.setGrid(g);
		avw.setMessage("Use mouse to click things\nDO NOT us arrow keys");
		avw.show();
		//I will fix this
		while(true){
			avw.go();
		}
	}




	private static void customFill(TerrainGrid g, Player p1, Player p2){
		Infantry inf1 = new Infantry(players[1]);
		inf1.putSelfInGrid(g,g.getLocationArray()[1][1]);
		Infantry inf2 = new Infantry(players[1]);
		inf2.putSelfInGrid(g,g.getLocationArray()[9][6]);
		Tank tank1 = new Tank(players[1]);
		tank1.putSelfInGrid(g,g.getLocationArray()[2][1]);
		Carrier carrier1 = new Carrier(players[1]);
		carrier1.putSelfInGrid(g,g.getLocationArray()[6][4]);
		BCopter copter1 = new BCopter(players[1]);
		copter1.putSelfInGrid(g,g.getLocationArray()[7][7]);
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
	public static Terrain makeTerrain(int r, int c, TerrainGrid<Actor> hostGrid, String terrainType) throws Exception{
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
		case "Test":
			return new TestTerrain(r, c, hostGrid);
		default:
			String[] propProp = terrainType.split("_");
			if(propProp.length!=2){
				throw new Exception("error reading file, bad property?");
			}else{
				Player ownerOfProp = players[Integer.parseInt(propProp[1])];
				switch(propProp[0]){
				case "Property":
					return new Property(r,c,hostGrid,ownerOfProp);
				case "Barracks":
					return new Barracks(r,c,hostGrid,ownerOfProp);
				case "SeaPort":
					return new SeaPort(r,c,hostGrid,ownerOfProp);
				case "AirPort":
					return new AirPort(r,c,hostGrid,ownerOfProp);
				case "HQ":
					return new HQ(r,c,hostGrid,ownerOfProp);
				default:
					throw new Exception("error reading file, bad property?");
				}
			}
		}
	}
}
