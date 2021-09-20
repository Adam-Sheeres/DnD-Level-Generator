//name: Adam SHeeres-Paulicpulle
//Student ID: 1036569
//email: asheeres@uoguelph.ca
package dungeon;
import java.util.ArrayList;
import dnd.models.Monster;
import java.io.Serializable;
import dnd.models.Treasure;
import dnd.models.Monster;

public abstract class Space implements java.io.Serializable {
	private String name;

	public abstract  String getDescription();

	public abstract void setDoor(Door theDoor);

	public abstract ArrayList<Door> getDoors();

	public abstract void addMonster(Monster toAdd);

	public abstract void addRandMonster(int ... roll);

	public abstract void removeMonster(int ... index);

	public abstract void addRandTreasure(int ... roll);

	public abstract void removeTreasure(int ... index);

	public abstract  ArrayList<Treasure> getTreasureList();

	public abstract ArrayList<Monster> getMonsters();

	public void setName(String toSet) {
		name = toSet;
	}

	public String getName() {
		return name;
	}
}
//added comments
