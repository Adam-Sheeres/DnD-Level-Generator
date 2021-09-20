//name: Adam SHeeres-Paulicpulle
//Student ID: 1036569
//email: asheeres@uoguelph.ca
package dungeon;

import dnd.models.Monster;
import dnd.models.Trap;
import java.util.ArrayList;
import dnd.die.Die;
import dnd.models.Stairs;
import java.io.Serializable;

/* Represents a 10 ft section of passageway */

public class PassageSection implements java.io.Serializable {

	private Monster monster;
	private Door door;
	private String finalDescription = new String();



	/******************************
	 Required Methods for that we will test during grading
	*******************************/
	/* note:  Some of these methods would normally be protected or private, but because we
	don't want to dictate how you set up your packages we need them to be public
	for the purposes of running an automated test suite (junit) on your code.  */


/**
	Sets default passage section. Rolls each time.
*/
	public PassageSection(){
		//sets up the 10 foot section with default settings
		int roll = Die.d20();
		if (roll == 1 || roll == 2) {
			finalDescription = "Passage continues straight for 10ft.";

		} else if (roll >= 3 && roll <= 5) {
			finalDescription = "Passage ends in a door to a chamber.";
			door = new Door();
			//chamber

		} else if (roll == 6 || roll == 7) {
			finalDescription = "Archway to left, main passage continues straight for 10ft.";
			door = new Door();
			door.setArchway(true);

		} else if (roll == 8 || roll == 9) {
			finalDescription += "Archway to right, main passage continues straight for 10ft";

		} else if (roll == 10 || roll == 11) {
			finalDescription += "Passage turns to left and continues for 10ft.";

		} else if (roll == 12 || roll == 13) {
			finalDescription += "Passage turns to right and continues for 10ft.";

		} else if (roll >= 14 && roll <= 16) {
			finalDescription += "Passage ends in archway to chamber.";
			door = new Door();
			door.setOpen(true);

		} else if (roll == 17) {
			finalDescription += "Stairs, passage continues straight for 10ft.";

		} else {
			finalDescription += "Wandering monster: ";
			addRandMonster();
			finalDescription += monster.getDescription();
			finalDescription += "\n    Passage continues straight for 10ft.";
		}
		finalDescription += "\n";
	}

/**
	Sets up the specific passage based on string input.
*/
	public PassageSection(String description) {
		//sets up a specific passage based on the values sent in from
		//modified table 1
		boolean monster = description.contains("monster");
		boolean archway = description.contains("archway");
		boolean doors		= description.contains("door");
		finalDescription += description;
		finalDescription += "\n";

		if (monster) addRandMonster();
		if (doors) {
			door = new Door();
			if (archway) {
				door.setArchway(true);
			}
		}

	}

	public Door getDoor(){
		//returns the door that is in the passage section, if there is one
		if (door != null) {
			return door;
		} else {
			return null;
		}
	}

	/**
	Sets the door in this section to a specific door, overriding the other door
	*/

	public void setDoor(Door newDoor) {
		if (door != null) {
			door = newDoor;
		} else {
			door = new Door();
			door = newDoor;
		}
	}

	public void addMonster(Monster newMonster) {
		monster = newMonster;
	}

	/**
		Allows addition of a random monster.
	*/
	private void addRandMonster() {
		Monster newMonster = new Monster();
		newMonster.setType(Die.d20());
		monster = newMonster;
	}

	public Monster getMonster(){
		//returns the monster that is in the passage section, if there is one
		if (monster != null) {
			return monster;
		} else {
			return null;
		}
	}
	/**
	Simply returns the string that is the description of the passage section.
	*/
	public String getDescription(){
		return finalDescription;
	}
}
