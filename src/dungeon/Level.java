//name: Adam SHeeres-Paulicpulle
//Student ID: 1036569
//email: asheeres@uoguelph.ca
package dungeon;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;


public class Level implements java.io.Serializable {
  private ArrayList<Chamber>  chamberList;
  private ArrayList<Chamber>  sortedList;
  private ArrayList<Door>     doorList;
  private HashMap<Door, Chamber> doorMap;
  private Chamber mostDoorChamber;
  private int currentPassageNumber;

  /**
    Constructor for level class.
  */
  public Level() {
    currentPassageNumber = 0;
    chamberList = new ArrayList<Chamber>();
    doorList = new ArrayList<Door>();
    doorMap = new HashMap<Door, Chamber>();
    fillChambers();
    fillDoorList();
    setMapping();
    sortedList = sortChambers();
    setChamberNames();
    connectChambers(0);
    makeConnections();
  }

  /**
    Fill the 5 chambers with new chamber
  */
  private void fillChambers() {
    for (int i = 0; i < 5; i++) {
      Chamber newChamber = new Chamber();
      chamberList.add(newChamber);
    }
  }

  /**
    Return list of chambers
  */
  public ArrayList<Chamber> getChambers() {
    if (chamberList.isEmpty()) {
      return null;
    } else {
      return chamberList;
    }
  }

  /**
    Generate a single passage section
  */
  private void genSegments(Passage addTo) {
    addTo.addPassageSection(new PassageSection("Goes straight for 10ft"));
    addTo.addPassageSection(new PassageSection("Goes straight for 10ft"));
  }

  /**
    Used to set the doors of a single chamber to new, empty passages
  */
  private void setDoors(Chamber toSet) {
    for (int i = 0; i < toSet.getDoors().size(); i++) {
      if(toSet.getDoors().get(i).getSpaces() == null) { //if the spaces haven't been set
        Passage newPassage = new Passage();

        genSegments(newPassage);
        newPassage.setDoor(toSet.getDoors().get(i));
        newPassage.setName("Passage " + Integer.toString(currentPassageNumber+1));
        currentPassageNumber++;
        toSet.getDoors().get(i).setSpaces(toSet, newPassage); //[CHAMBER] [PASSAGE]
      }
    }
  }

  /**
    Used to set the hashmap connecting the doors to the chamber it is a part of.
  */
  private void setMapping() {
    for (int i = 0; i < chamberList.size(); i++) { //number of chambers (5)
      for (int j = 0; j < chamberList.get(i).getDoors().size(); j++) //chamber's door list
      doorMap.put(chamberList.get(i).getDoors().get(j), chamberList.get(i));
    }
  }

  /**
    Used to get all of the doors into doorList for ease of use.
  */
  private void fillDoorList() {
    for (int i = 0; i < chamberList.size(); i++) {
      for (int j = 0; j < chamberList.get(i).getDoors().size(); j++) {
        doorList.add(chamberList.get(i).getDoors().get(j));
      }
    }
  }
  /**
    Sorts the chambers in descending order of amount of doors
  */
  private ArrayList<Chamber> sortChambers() {
    ArrayList<Chamber> sortedList = new ArrayList<Chamber>();
    Chamber max;
    int a, b;

    //get largest
    max = chamberList.get(0);
    for (int i = 1; i < chamberList.size(); i++) { //5 times
      if (chamberList.get(i).getDoors().size() > max.getDoors().size()) { //if the next one is greater than first
        max = chamberList.get(i); //set max
      }
    }
    sortedList.add(max);
    chamberList.remove(max);
    max = chamberList.get(0);

    //second biggest number
    for (int i = 1; i < chamberList.size(); i++) { //5 times
      if (chamberList.get(i).getDoors().size() > max.getDoors().size()) { //if the next one is greater than first
        max = chamberList.get(i); //set max
      }
    }
    sortedList.add(max);
    chamberList.remove(max);

    //third biggest number
    max = chamberList.get(0);
    for (int i = 1; i < chamberList.size(); i++) { //5 times
      if (chamberList.get(i).getDoors().size() > max.getDoors().size()) { //if the next one is greater than first
        max = chamberList.get(i); //set max
      }
    }
    sortedList.add(max);
    chamberList.remove(max);

    ///fourth biggest number
    max = chamberList.get(0);
    for (int i = 1; i < chamberList.size(); i++) { //5 times
      if (chamberList.get(i).getDoors().size() > max.getDoors().size()) { //if the next one is greater than first
        max = chamberList.get(i); //set max
      }
    }
    sortedList.add(max);
    chamberList.remove(max);

    //fifth biggest number
    max = chamberList.get(0);
    for (int i = 1; i < chamberList.size(); i++) { //5 times
      if (chamberList.get(i).getDoors().size() > max.getDoors().size()) { //if the next one is greater than first
        max = chamberList.get(i); //set max
      }
    }
    sortedList.add(max);
    chamberList.remove(max);
    return sortedList;
  }

  /**
    Sets the name for all chambers
  */
  private void setChamberNames() {
    String name = new String();
    for (int i = 0; i < 5; i++) {
      name = "Chamber " + Integer.toString(i+1) + " "; //make name
      sortedList.get(i).setName(name); //set the name of the chamber
    }
  }

  /**
    This method is used to connect the chambers together
  */
  private void connectChambers(int start) {
    if (start < 4)  { //if at total number of chamber
      setDoors(sortedList.get(start)); //add passages to the first one
      if (sortedList.get(start+1).allDoorsSet()) { //if all the doors are set in the next chamber.
        return;
      }
      for (int i = start; i < sortedList.get(start).getDoors().size(); i++) { //loop for the number of doors in the chamber
        if (sortedList.get(i+1).allDoorsSet()) { //if all the doors are set, then break
          break;
        } else {
          Door connectingDoor = sortedList.get(i+1).getDoors().get(start); //get first door from chamber
          Space connectingPassage = sortedList.get(start).getDoors().get(i).getSpaces().get(1);
          connectingDoor.setSpaces(sortedList.get(i+1), connectingPassage); //setSpaces([CHAMBER], [PASSAGE])
          connectingPassage.setDoor(connectingDoor);
        }
      }
      connectChambers(start+1);
    } else {
      return;
    }
  }


  /**
    make connections for all the chamber
  */
  private void makeConnections() {
    for (int i = 0; i < sortedList.size(); i++) {
      whatConnects(sortedList.get(i));
    }
  }

  /**
    Connects the chambers to each other in their classes.
  */
  private void whatConnects(Chamber fromHere) {
    ArrayList<Door> tempDoorList = fromHere.getDoors();
    ArrayList<Door> compareDoorList = new ArrayList<Door>();

    for (int i = 0; i < tempDoorList.size(); i++) { //go over all the doors in the current chamber
        Chamber examined = sortedList.get(i);
        if (examined != fromHere) {  //chamber isn't the same one
          for (int k = 0; k < examined.getDoors().size(); k++) { //loop over all doors for specific chamber
            if (tempDoorList.get(i).getPassage() == examined.getDoors().get(k).getPassage());
            //if the passage for the examined door is the same as the passage for the origional door, they connect
            fromHere.addConnections(examined);
            sortedList.get(i).addConnections(fromHere);
          }
        }
    }
  }
  /**
    gets the description for a single chamber based on index
  */
  public String getThisDescription(int index) {
    if (sortedList.isEmpty()) {
      return null;
    } else {
      return sortedList.get(index).getDescription();
    }
  }

  /**
    returns a chamber from a certain index
  */
  public Chamber getThisChamber(int index) {
    return sortedList.get(index);
  }

  /**
    Returns description of entire level.
  */
  public String getDescription() {
    String description = new String();
    description = "\nLevel:";
    for (int i = 0; i < 5; i++) {
      description += "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n";
      description += sortedList.get(i).getDescription(); //get descrip of chamber
    }
    return description;
  }
}

//to compile: /Users/microwave/apache-ant-1.10.7/bin/ant
//to run:     java -cp lib/dnd-A3.jar:build dungeon/Level
