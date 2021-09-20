//name: Adam SHeeres-Paulicpulle
//Student ID: 1036569
//email: asheeres@uoguelph.ca
package dungeon;

import dnd.models.ChamberContents;
import dnd.models.ChamberShape;
import dnd.models.DnDElement;
import dnd.models.Monster;
import dnd.models.Exit;
import dnd.models.Trap;
import dnd.models.Treasure;
import dnd.die.D20;
import dnd.die.Die;
import java.util.ArrayList;
import java.io.Serializable;

public class Chamber extends Space implements java.io.Serializable {

	private ChamberContents myContents;
	private ChamberShape mySize;
	private Door intoDoor;
	private int length;
	private int width;
	private int numExits;
	private ArrayList<Monster> 	monsterList;
	private ArrayList<Treasure> treasureList;
	private ArrayList<Door> doorList;
	private ArrayList<Exit>	exitList;
	private ArrayList<Chamber> connectsToList;
	private String description;
	private String name;



	/******************************
	 Required Methods for that we will test during grading
	*******************************/
	/* note:  Some of these methods would normally be protected or private, but because we
	don't want to dictate how you set up your packages we need them to be public
	for the purposes of running an automated test suite (junit) on your code.  */

/**
	Constructor for Chamber Class
*/
public Chamber() {

	/*set all the needed class variables*/

	description = new String();
	monsterList = new ArrayList<Monster>();
	treasureList = new ArrayList<Treasure>();
	doorList = new ArrayList<Door>();
	exitList = new ArrayList<Exit>();
	connectsToList = new ArrayList<Chamber>();

	myContents = new ChamberContents();
	myContents.chooseContents(Die.d20());

	mySize = ChamberShape.selectChamberShape(Die.d20()); /*set the shape*/

	boolean monster = myContents.getDescription().contains("monster");
	boolean treasure = myContents.getDescription().contains("treasure");

	if (monster) {
		Monster newMonster = new Monster();
		newMonster.setType(Die.d20());
		addMonster(newMonster);
	}

	if (treasure) {
		Treasure chamberTreasure = new Treasure();
		chamberTreasure.chooseTreasure(Die.d20());
		addTreasure(chamberTreasure);
	}
	setExits();
	ensureDoors();
}

/**
	Constructor for Chamber Class with specified shape and contents
*/
public Chamber(ChamberShape theShape, ChamberContents theContents) {
	mySize = theShape;
	myContents = theContents;

	description = new String();
	monsterList = new ArrayList<Monster>();
	treasureList = new ArrayList<Treasure>();
	doorList = new ArrayList<Door>();
	exitList = new ArrayList<Exit>();

	boolean monster = myContents.getDescription().contains("monster");
	boolean treasure = myContents.getDescription().contains("treasure");

	if (monster) {
		Monster newMonster = new Monster();
		newMonster.setType(Die.d20());
		addMonster(newMonster);
	}

	if (treasure) {
		System.out.println("treasure");
		Treasure chamberTreasure = new Treasure();
	}
	setExits();
	ensureDoors();
}

/**
	This method makes sure that the chamber doesn't generate 5 doors.
*/

public void ensureDoors() {
	if (doorList.size() >= 5) {
		doorList.remove(4);
	}
}

//setter and getter for connections
public void addConnections(Chamber toAdd) {
	if (!connectsToList.contains(toAdd)) { //if it isn't already in
		connectsToList.add(toAdd);
	}
}

public String getConnections() {
	String connections = new String();
	for (int i = 0; i < connectsToList.size(); i++) {
		connections += connectsToList.get(i).getName() + ", ";
	}

	return connections;
}

public ArrayList<Chamber> getConnection() {
	if (connectsToList.isEmpty()) {
		return null;
	} else {
	return connectsToList;
	}
}

public Space getPassageFromDoor(int index) {
	Space toReturn = null;
	try {
		toReturn = doorList.get(index).getSpaces().get(1);
	} catch (Exception e) {
		return null;
	}
	return toReturn;
}


/**
	Sets the doors based on exits
*/
private void setExits() {
	numExits = mySize.getNumExits();
	for (int i = 0; i < numExits; i++) {
		Door newDoor = new Door();
		setDoor(newDoor);
	}
}
/**
	set shape of chamber
*/
public void setShape(ChamberShape theShape) { //set shape of class inputted
	mySize = theShape;
}

/**
	Sets the name of the chamber.
*/
@Override
public void setName(String newName) {
	super.setName(newName);
}

/**
	Gets the name of the chamber.
*/
@Override
public String getName() {
	return super.getName();
}

/**
	Returns list of doors
*/
public ArrayList<Door> getDoors(){
	if (doorList.isEmpty()) {
		return null;
	} else {
		return doorList;
	}
}

public String getDoorDescription() {
	String doorDescriptoin = new String();

	doorDescriptoin = getName() + " doors:\n";

	for (int i = 0; i < doorList.size(); i++) {
		doorDescriptoin += Integer.toString(i+1) + ": " + doorList.get(i).getDescription() + "\n";
	}

	return doorDescriptoin;
}
/**
	Adds a monster
*/
public void addMonster(Monster toAdd){
	//add a monster to the monster list
	monsterList.add(toAdd);
}

/**
	Removes the last monster
*/
public void removeMonster(int ... index) {
	try {
		monsterList.remove(index[0] - 1);
	} catch (Exception e) {
		if (monsterList.size() > 0) {
			monsterList.remove(monsterList.size() - 1);
		}
	}
}

/**
	Removes the last piece of treasure
*/
public void removeTreasure(int ... index) {
	try {
		treasureList.remove(index[0] - 1);
	} catch (Exception e) {
		if (treasureList.size() > 0) {
			treasureList.remove(treasureList.size() - 1);
		}
	}
}

/**
	Returns a list of monsters in the chamber
*/
public ArrayList<Monster> getMonsters(){
	//return all monsters
	if (monsterList.isEmpty()) {
		return null;
	} else {
		return monsterList;
	}
}

/**
	Adds a random monster
*/
public void addRandMonster(int ... roll) {
  Integer[] type = {1, 3, 5, 15, 16, 18, 19, 20};
	Monster newMonster = new Monster();
	newMonster.setType(Die.d20());
	try {
		newMonster.setType(type[roll[0]]);
	} catch (Exception e) {

	}
	monsterList.add(newMonster);
}

/**
	Adds random treasure
*/
public void addRandTreasure(int ... roll) {
	Treasure newTreasure = new Treasure();
	Integer[] type = {15, 40, 55, 70, 85, 92, 96, 98};
	newTreasure.chooseTreasure(Die.d10()*10 + Die.d10());
	newTreasure.setContainer(Die.d20());
	try {
		newTreasure.chooseTreasure(type[roll[0]]);
	} catch (Exception e) {

	}
	treasureList.add(newTreasure);
}

/**
	Adds treasure to the chamber
*/
public void addTreasure(Treasure theTreasure) {
	//add to treasure list
	treasureList.add(theTreasure);
}

/**
	Returns list of treasure
*/
public ArrayList<Treasure> getTreasureList() {
	//return entire treasure list
	if (treasureList.isEmpty()) {
		return null;
	} else {
		return treasureList;
	}
}

/**
	Checks if all the doors are set
*/
public boolean allDoorsSet () {
	for (int i = 0; i < doorList.size(); i++) { //loops through all doors
		if (doorList.get(i).getSpaces() == null) {  //if any space is null, it hasn 't been set.
			return false;
		}
	}
	return true; //if every space has been set (i.e. it is not null) then all doors have been set.
}

/**
	Gets the number of mosnters
*/
public int monsterCount() {
	return monsterList.size();
}

public int getWidth() {
	int width = 0;
	try {
		width = mySize.getWidth();
	} catch (Exception e) {
		width = 0;
	}
	return width;
}

public int getLength() {
	int length = 0;
	try {
		length = mySize.getLength();
	} catch (Exception e) {
		length = 0;
	}
	return length;
}

public int getArea() {
	return mySize.getArea();
}

/**
	Appends everything to do with treasure to the description string
*/

private void getTreasureDescription() {
	if(treasureList.isEmpty()) {
	} else {
		if (treasureList.size() > 1) { //if there's more than one treasure
			for (int i = 0; i < treasureList.size(); i++) {
				description += "\nTreasure: " + treasureList.get(i).getDescription() + " | Container: " + treasureList.get(i).getContainer() + " | ";
				try {
					description += "Protection: " + treasureList.get(i).getProtection() + "\n";
				} catch (Exception e) {
					description += "Not protected.\n";
				}
			}
		} else if (treasureList.size() > 0) { //if there is treasure
			description += "\nTreasure: " + treasureList.get(0).getDescription() + " | Container: " + treasureList.get(0).getContainer() + " | ";
			try {
				description += "Protection: " + treasureList.get(0).getProtection() + "\n";
			} catch (Exception e) {
				description += "Not protected.\n";
			}
		}
		description += "\n";
	}
}

/**
	Appends everything to do with monsters to the description string.
*/
private void getMonsterDescription() {
	if(monsterList.size()>0) { //print out details of monster(s)
			if(monsterList.size()>1) {
				description += "\nThere are " + Integer.toString(monsterList.size()) + " monsters.\n";
				for(int i = 0; i < monsterList.size(); i++) { //more than one moster
					description += "Monster " + Integer.toString(i + 1) + ": " + monsterList.get(i).getDescription() +" | ";
					description += "Max: " + monsterList.get(i).getMaxNum() + " Min: " + monsterList.get(i).getMinNum() + "\n";
				}
				description += "\n";
			} else { //just one monster
				description += "\nMonster: " + monsterList.get(0).getDescription() + " | ";
				description += "Max: " + monsterList.get(0).getMaxNum() + " Min: " + monsterList.get(0).getMinNum() + "\n\n";
			}
		}
}

/**
	Returns a string that is the entire description of the chamber.
*/
@Override
public String getDescription() {
	description = getName();
	description += ":\nChamber is " + mySize.getDescription() + ".\n\n";
	getTreasureDescription();
	getMonsterDescription();

	if (myContents.getDescription().contains("trap")) {
		Trap newTrap = new Trap();
		description += "Trap:";
		description += newTrap.getDescription();
		description += ".\n";
	}
	return description;
}

/**
	Adds a door to the chamber.
*/
@Override
public void setDoor(Door newDoor){
	//should add a door connection to this room
	doorList.add(newDoor);
}


/***********
You can write your own methods too, you aren't limited to the required ones
*************/

//how to use ant:      /Users/microwave/apache-ant-1.10.7/bin/ant
//how to test program: java -cp lib/dnd-20190914.jar:build asheeres.Test
}
