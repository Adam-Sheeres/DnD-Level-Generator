//name: Adam SHeeres-Paulicpulle
//Student ID: 1036569
//email: asheeres@uoguelph.ca
package dungeon;
import dnd.models.Exit;
import dnd.models.Trap;
import java.util.ArrayList;
import dnd.die.Die;
import java.lang.Integer;
import java.io.Serializable;

public class Door implements java.io.Serializable {
	private Trap trap;
	private boolean open;
	private boolean archway;
	private boolean trapped;
	private ArrayList<Space> spaces;
	private String description = new String();
	private Passage currentPassage;

/**
	Constructor for door. Each door has a chance of being locked, an archway, or trapped.
*/
	public Door() {
		spaces = new ArrayList<Space>();
		if (Die.d20() == 20) { //1/20 chamce it is trapped
			trapped = true;
			trap = new Trap();
		} else {
			trapped = false;
		}

		if (Die.d6() == 3) { // 1/6 chance its locked
			open = false;
		} else {
			open = true;
			archway = true;
		}

		if (Die.d10() == 10) {
			archway = true;
			open = true;
		} else {
			archway = false;
		}
		//needs to set defaults
	}

	public Door(Exit theExit){
		//sets up the door based on the Exit from the tables

	}

	/******************************
	 Required Methods for that we will test during grading
	*******************************/
	/* note:  Some of these methods would normally be protected or private, but because we
	don't want to dictate how you set up your packages we need them to be public
	for the purposes of running an automated test suite (junit) on your code.  */

/**
	Sets trap based on input
*/
	public void setTrapped(boolean flag, int roll) {
		// true == trapped.  Trap must be rolled if no integer is given
		if (flag) {
			trapped = true;
			if (trap == null) {
				trap = new Trap();
			}
			trap.setDescription(roll);
		}
	}

	/**
		Sets the door's open status to the input
	*/
	public void setOpen(boolean flag){
		open = flag;
	}

	/**
		Sets the door's archway status to the input
	*/
	public void setArchway(boolean flag){
		archway = flag;
		if (flag) { //if it is an archway, then it is open
			open = true;
		}
	}

	public boolean isTrapped(){
		return trapped;
	}
	public boolean isOpen(){
		return open;
	}
	public boolean isArchway(){
		return archway;
	}
	public String getTrapDescription() {
		if (trap == null) {
			return null;
		} else {
			return trap.getDescription();
		}
	}

	/**
			Returns the two spaces from the door. Space one is always a Chamber and space two is always a Passage
	*/
	public ArrayList<Space> getSpaces(){
		//returns the two spaces that are connected by the door
		if (spaces.isEmpty()) {
			return null;
		} else {
			return spaces;
		}
	}

	public Space getPassage() {
		if (spaces.isEmpty()) {
			return null;
 		} else {
			return spaces.get(1);
		}
	}


	/**
		Used to set the two spaces the door connects
	*/
	public void setSpaces(Space spaceOne, Space spaceTwo){
		//identifies the two spaces with the door
		//this method should also call the addDoor method from Space
		spaces.add(0, spaceOne);
		spaces.add(1, spaceTwo);
	}
	/**
		Used to get the description of the two spaces door connects
	*/
	public String spacesDescription() {
		description =  spaces.get(0).getDescription();
		description += spaces.get(1).getDescription();
		return description;
	}

	/**
		Returns the entire description of the door.
	*/
	public String getDescription() {
		//open
		if (open) { description = "Open, "; }
			else { description = "Locked, "; }

		//archway
		if (archway) { description += "archway, "; }
			else { description += "not an archway, "; }

	//trapped
		if (trapped) { description += "trapped, "; }
			else { description += "not trapped, "; }

		return description;
	}
/***********
You can write your own methods too, you aren't limited to the required ones
*************/
}
