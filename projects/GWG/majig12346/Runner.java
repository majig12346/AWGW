package majig12346;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import info.gridworld.actor.Actor;
import info.gridworld.world.AVWorld;
import majig12346.CO.TestCO;
import majig12346.terrain.*;
import majig12346.terrain.properties.*;
import majig12346.units.land.*;
import majig12346.units.sea.*;
import majig12346.units.air.*;
import majig12346.units.*;

public class Runner {
	public static Player[] players;
	
	private enum VictoryCondition {
		CAPTURING_HQ, ELIMINATING_ALL_UNITS;
	}
	public final static int MONEY_PER_PROPERTY = 200;
	public static void main(String[] args) {
		System.out.println(
				"Currently 1337 PLANCK_TIMEs behind! Are we on " + "5nm or is Quantum tunneling somehow an issue "
						+ "on Skylake's 14nm? Wait a PLANCK_TIME," + " this is a Core 2 Duo!");
		AVWorld avw = new AVWorld();
		players = new Player[3];
		Player p1 = new Player(new TestCO(), 15000, new Color(255, 80, 45));
		Player p2 = new Player(new TestCO(), 90000, new Color(75, 150, 255));
		players[p1.id] = p1;
		players[p2.id] = p2;
		turnPlayer = players[2];
		TerrainGrid<Actor> g = new TerrainGrid<Actor>(10, 10);
		fillTerrainGrid(g);
		customFill(g, p1, p2);
		avw.setGrid(g);
		avw.show();
		cycleTurnPlayer();
		while (allPlayersCompeting(getCompetitivePlayers())) {
			avw.setMessage("Currently selected: none.\n\nUse your units to move. Click your factories to build. " +
			// "DO NOT use arrow keys or Enter"+
					"P1 money: " + players[1].getMoney() + "  P2 money: " + players[2].getMoney());
			avw.go();
		}
		avw.getWorldFrame().control.turnCycleButton.setEnabled(false);
		String victoryDiag = victoryDiag();
		avw.setMessage(victoryDiag);
		JOptionPane.showMessageDialog(avw.getWorldFrame(), (victoryDiag), "Game Over", 0,
				new ImageIcon(Runner.class.getClassLoader().getResource("resources/victory.png")));
	}

	private static void customFill(TerrainGrid<Actor> g, Player p1, Player p2) {
		Infantry inf1 = new Infantry(players[1]);
		inf1.putSelfInGrid(g, g.getLocationArray()[1][1]);
		Infantry inf2 = new Infantry(players[1]);
		inf2.putSelfInGrid(g, g.getLocationArray()[9][6]);
		Mech mech1 = new Mech(players[2]);
		mech1.putSelfInGrid(g, g.getLocationArray()[2][8]);
		MedTank medTank1 = new MedTank(players[1]);
		medTank1.putSelfInGrid(g, g.getLocationArray()[1][8]);
		APC apc1 = new APC(players[1]);
		apc1.putSelfInGrid(g, g.getLocationArray()[2][1]);
		Carrier carrier1 = new Carrier(players[1]);
		carrier1.putSelfInGrid(g, g.getLocationArray()[3][6]);
		BCopter copter1 = new BCopter(players[1]);
		copter1.putSelfInGrid(g, g.getLocationArray()[7][7]);
		TCopter copter2 = new TCopter(players[1]);
		copter2.putSelfInGrid(g, g.getLocationArray()[7][6]);
		AdvFighter avf1 = new AdvFighter(players[1]);
		avf1.setFuel(1.0);
		avf1.putSelfInGrid(g, g.getLocationArray()[5][5]);
	}

	/**
	 * A player is competitve if he/she controls Units and his/her HQ To knock
	 * out another Player, destroy all of their Units or capture his/her HQ
	 * 
	 * @return whether or not all players are competitive
	 */
	private static boolean allPlayersCompeting(ArrayList<Player> competitivePlayers) {
		// TODO more than 2 player support
		return competitivePlayers.size() == 2;
	}

	private static ArrayList<Player> getCompetitivePlayers() {
		ArrayList<Player> competitivePlayers = new ArrayList<>();
		for (Player p : players) {
			if (null != p && p.getUnitsControlled().size() > 0) {
				boolean hasHQ = p.hasHQ();
				if (hasHQ) {
					competitivePlayers.add(p);
				}
			}
		}
		return competitivePlayers;
	}

	/**
	 * Precondition: a Player has won
	 * 
	 * @return a description of the Player's victory
	 */
	private static String victoryDiag() {
		ArrayList<Player> allPlayers = new ArrayList<Player>(
				Arrays.asList(players).stream().filter(p -> null != p).collect(Collectors.toList())),
				compPlayers = getCompetitivePlayers();
		StringBuilder victoryMessage = new StringBuilder(
				"Congratulations to Player " + compPlayers.get(0).id + " for eliminating\n");
		allPlayers.remove(compPlayers.get(0));
		for (Player loser : allPlayers) {
			String loserName = "Player " + loser.id;
			victoryMessage.append("\t" + loserName + " by ");
			if (!loser.hasHQ()) {
				victoryMessage.append("capturing " + loserName + "'s HQ");
			}
			if (loser.getUnitsControlled().size() == 0) {
				victoryMessage.append("destroying all of " + loserName + "'s units");
			}
		}
		return victoryMessage.toString();
	}

	public static void fillTerrainGrid(TerrainGrid<Actor> g) {

		// String fileName = JOptionPane.showInputDialog("file name");
		String fileName = "resources/map.txt";
		try {
			Scanner sc = new Scanner(Runner.class.getClassLoader().getResourceAsStream(fileName));
			if (!(g.getNumRows() == sc.nextInt() && g.getNumCols() == sc.nextInt())) {
				throw new Exception("map size != grid size");
			}
			// hmm??? good
			sc.nextLine();
			for (int r = 0; r < g.getNumRows(); r++) {

				String[] rowStringForm = sc.nextLine().split(",");
				// System.out.println("good\n");
				// System.out.print(new
				// ArrayList<String>(Arrays.asList(rowStringForm)));
				for (int c = 0; c < g.getNumCols(); c++) {
					g.getLocationArray()[r][c] = makeTerrain(r, c, g, rowStringForm[c]);
				}
			}
		} catch (Exception e) {
			System.out.println("error reading file");
			e.printStackTrace();
		}
	}

	private static Player turnPlayer;

	public static Player getTurnPlayer() {
		return turnPlayer;
	}

	public static Player getNextTurnPlayer() {
		Player old = getTurnPlayer(), next;
		try {
			if (players[old.id + 1] == null) {
				next = players[1];
			} else {
				next = players[old.id + 1];
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			next = players[1];
		}
		return next;
	}

	public static Player cycleTurnPlayer() {
		Player old = turnPlayer;
		Player next;
		for (Unit u : old.getUnitsControlled()) {
			if (u.canMove()) {
				u.immobilize();
			}
		}
		try {
			if (players[old.id + 1] == null) {
				next = players[1];
			} else {
				next = players[old.id + 1];
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			next = players[1];
		}

		Iterator<Unit> i = next.getUnitsControlled().iterator();
		while (i.hasNext()) {
			Unit u = i.next();
			u.resetMovement();
		}

		// for(Unit u:next.getUnitsControlled()){
		// u.resetMovement();
		// }
		turnPlayer = next;
		for(Property p :turnPlayer.getPropertiesOwned()){
			turnPlayer.setMoney(turnPlayer.getMoney()+MONEY_PER_PROPERTY);
		}
		return turnPlayer;

	}

	public static Terrain makeTerrain(int r, int c, TerrainGrid<Actor> hostGrid, String terrainType) throws Exception {
		switch (terrainType) {
		case "Beach":
			return new Beach(r, c, hostGrid);
		case "Plains":
			return new Plains(r, c, hostGrid);
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
			if (propProp.length != 2) {
				throw new Exception("error reading file, bad property?");
			} else {
				Player ownerOfProp = players[Integer.parseInt(propProp[1])];
				switch (propProp[0]) {
				case "Property":
					return new Property(r, c, hostGrid, ownerOfProp);
				case "Barracks":
					return new Barracks(r, c, hostGrid, ownerOfProp);
				case "SeaPort":
					return new SeaPort(r, c, hostGrid, ownerOfProp);
				case "AirPort":
					return new AirPort(r, c, hostGrid, ownerOfProp);
				case "HQ":
					return new HQ(r, c, hostGrid, ownerOfProp);
				default:
					throw new Exception("error reading file, bad property?");
				}
			}
		}
	}
}
