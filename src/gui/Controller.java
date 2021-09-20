//name: Adam SHeeres-Paulicpulle
//Student ID: 1036569
//email: asheeres@uoguelph.ca
package gui;

import dungeon.*;
import java.util.ArrayList;
import javafx.scene.control.*;
import javafx.scene.Group;
import javafx.scene.Node;

public class Controller {
    private Interface myGui;
    private Door currentDoor;

    public Controller(Interface theGui){
        myGui = theGui;
    }

    public void setDoor(Door toSet) {
      currentDoor = toSet;
    }

    public Door getDoor() {
      return currentDoor;
    }
}
