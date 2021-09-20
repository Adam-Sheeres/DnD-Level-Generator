//name: Adam SHeeres-Paulicpulle
//Student ID: 1036569
//email: asheeres@uoguelph.ca
package dungeon;

import dnd.models.Monster;
import dnd.models.Treasure;
import java.util.ArrayList;
import java.util.HashMap;
import dnd.die.Die;
import java.io.Serializable;
/*
A passage begins at a door and ends at a door.  It may have many other doors along
the way

You will need to keep track of which door is the "beginning" of the passage
so that you know how to
*/

public class Passage extends Space implements java.io.Serializable {
	//these instance variables are suggestions only
	//you can change them if you wish.
	private Chamber newChamber									 = new Chamber();
	private PassageSection currentSection 			 = new PassageSection();
	private String finalDescription 						 = new String();
	private ArrayList<Monster> monsterList 			 = new ArrayList<>();
	private ArrayList<Door> doorList 						 = new ArrayList<>();
	private ArrayList<PassageSection> thePassage = new ArrayList<>();
	private ArrayList<Treasure> treasureList 		 = new ArrayList<>();
	private HashMap<Door,PassageSection> doorMap = new HashMap<Door,PassageSection>(); //door is the key, passagesection is the value
	private String name;
	/******************************
	 Required Methods for that we will test during grading
	*******************************/
	/* note:  Some of these methods would normally be protected or private, but because we
	don't want to dictate how you set up your packages we need them to be public
	for the purposes of running an automated test suite (junit) on your code.  */

	/**
		Constructor
	*/
	public void Passage() {

	}

	/**
		Returns all doors.
	*/
	public ArrayList getDoors() {
	//gets all of the doors in the entire passage
		if (doorList.isEmpty()) {
			return null;
		} else {
		return doorList;
		}
	}

	public ArrayList<Monster> getMonsters(){
		//return all monsters
		if (monsterList.isEmpty()) {
			return null;
		} else {
			return monsterList;
		}
	}

	/**
		Returns a specific door if it exists.
	*/
	public Door getDoor(int i){
		//returns the door in section 'i'. If there is no door, returns null
		if (doorList.isEmpty()) {
			return null;
		} else {
			return doorList.get(i);
		}
	}

	public void addMonster(Monster theMonster, int i){
		// adds a monster to section 'i' of the passage
		monsterList.add(i, theMonster);
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
		Returns all monsters.
	*/
	public Monster getMonster(int i){
		//returns Monster door in section 'i'. If there is no Monster, returns null
		if(monsterList.isEmpty()) {
			return null;
		} else {
			return monsterList.get(i);
		}
	}

	/**
		Adds a monster to the monster list
	*/
	public void addMonster(Monster toAdd) {
		monsterList.add(toAdd);
	}

	/**
		Removes the last monster
	*/
	public void removeMonster(int ... index) {
		try {
			monsterList.remove(index);
		} catch (Exception e) {
			if (monsterList.size() > 0) {
				monsterList.remove(monsterList.size() - 1);
			}
		}
	}

	public ArrayList<Treasure> getTreasureList() {
		if (treasureList.isEmpty()) {
			return null;
		} else {
			return treasureList;
		}
	}

	/**
		Adds random treasure to the passage. Can be specified with roll
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
		Removes the last piece of treasure
	*/
	public void removeTreasure(int ... index) {
		try {
			treasureList.remove(index);
		} catch (Exception e) {
			if (treasureList.size() > 0) {
				treasureList.remove(treasureList.size() - 1);
			}
		}
	}
	/**
		Adds a section to this passage and also changes the status of 'current passage'.
	*/
	public void addPassageSection(PassageSection toAdd){
		//adds the passage section to the passageway
		thePassage.add(toAdd);
		if (toAdd.getDescription().contains("monster")) {
			monsterList.add(toAdd.getMonster());
		}
		currentSection = toAdd;
	}

	/**
		Returns all the passage segments
	*/
	public ArrayList<PassageSection> getPassages() {
		if (thePassage.isEmpty()) {
			return null;
		} else {
			return thePassage;
		}
	}

	/**
		Sets a chamber for this specific passage
	*/
	public void setChamber(Chamber toSet) {
		newChamber = toSet;
	}

	public Chamber getChamber() {
		if (newChamber != null) {
			return newChamber;
		} else {
			return null;
		}
	}

	public String getDoorDescription() {
		String doorDescriptoin = new String();
		doorDescriptoin = getName() + " doors:\n";

		for (int i = 0; i < doorList.size(); i++) {
			doorDescriptoin += "Door " + Integer.toString(i+1) + " " + doorList.get(i).getDescription() + "\n";
		}

		return doorDescriptoin;
	}

	@Override
	public void setName(String toSet) {
		name = toSet;
	}

	@Override
	public String getName() {
		return name;
	}


	@Override
	public void setDoor(Door newDoor){
		//should add a door connection to the current Passage Section
		doorList.add(newDoor);
	}

	/**
		Returns a string that is all of the sections's descriptions.
	*/
	@Override
	public String getDescription(){
		finalDescription = name + "\n";
		for (int i = 0; i < thePassage.size(); i++) {
	 		finalDescription += Integer.toString(i+1) + ". " + thePassage.get(i).getDescription();
		}
		if (monsterList.size() == 1) finalDescription += "\n\nMonster:\n";
		else if (monsterList.size() > 1) finalDescription += "\n\nMonsters:\n";
		for (int i = 0; i < monsterList.size(); i++) {
			finalDescription += monsterList.get(i).getDescription() + ".\n";
		}

		if (treasureList.size() == 1) finalDescription += "\n\nTreasure:\n";
		for (int i = 0; i < treasureList.size(); i++) {
			finalDescription += treasureList.get(i).getDescription() + " | Container: " + treasureList.get(i).getContainer();
			try {
				finalDescription += " | Protection: " + treasureList.get(i).getProtection() + "\n";
			} catch (Exception e) {
				finalDescription += " | Not protected\n";
			}
		}
	return finalDescription;
	}
	/***********
	You can write your own methods too, you aren't limited to the required ones
	*************/
}
