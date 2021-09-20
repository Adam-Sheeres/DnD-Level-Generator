//name: Adam SHeeres-Paulicpulle
//Student ID: 1036569
//email: asheeres@uoguelph.ca
package gui;

import javafx.application.Application;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.geometry.*;
import java.io.*;
import dungeon.*;

public class ChamberView extends GridPane {
  private String floor = "/res/floor.png";
  private String treasure = "/res/treasure.png";
  private String monster = "/res/monster.png";
  private String door = "/res/doorTile.png";
  private Chamber thisChamber;
  private int doorCount = 0;
  private int monsterCount = 0;
  private int treasureCount = 0;

  public ChamberView (Chamber here) {
    thisChamber = here;
    if (thisChamber.getMonsters() != null) {
      monsterCount = thisChamber.getMonsters().size();
    }

    if (thisChamber.getTreasureList() != null) {
      treasureCount = thisChamber.getTreasureList().size();
    }
    doorCount = thisChamber.getDoors().size();

    if (thisChamber.getDescription().contains("Rectangle") || thisChamber.getDescription().contains("Square")) {
      int length = thisChamber.getLength()/2;
      int width = thisChamber.getWidth()/2;

      for (int i = 0; i < length; i++) {
        for (int j = 0; j < width; j++) {
          if (doorCount > 0) {
            add(floorFactory(door), j, i, 1, 1);
            doorCount--;
          } else if (monsterCount > 0) {
            add(floorFactory(monster), j, i, 1, 1);
            monsterCount--;
          } else if (treasureCount > 0) {
            add(floorFactory(treasure), j, i, 1, 1);
            treasureCount--;
          } else {
            add(floorFactory(floor), j, i, 1, 1);
          }
        }
      }
    } else if (thisChamber.getDescription().contains("Oval") || thisChamber.getDescription().contains("Cave")) {
      makeOvalRoom();
    } else if (thisChamber.getDescription().contains("Circular") || thisChamber.getDescription().contains("Hexagonal") || thisChamber.getDescription().contains("Octagonal")) {
      makeCircleRoom();
    } else if (thisChamber.getDescription().contains("Triangular")) {
      makeTriangle();
    } else {
      makeTrapezoid();
    }
  }

  private Node floorFactory(String image) {
    Image floor = new Image(getClass().getResourceAsStream(image));
    Label toReturn = new Label();
    ImageView imageView = new ImageView(floor);
    imageView.setFitWidth(25);
    imageView.setFitHeight(25);
    toReturn.setGraphic(imageView);
    return toReturn;
  }

  public void makeOvalRoom() {
    add(floorFactory(floor), 0, 5, 1, 1);
    add(floorFactory(floor), 0, 6, 1, 1);


    for (int i = 1; i < 4; i++) {
      for (int j = 4; j < 8; j++) {
        if (doorCount > 0 && j == 4) {
          add(floorFactory(door), i, j, 1, 1);
          doorCount--;
        } else if (monsterCount > 0) {
          add(floorFactory(monster), i, j, 1, 1);
          monsterCount--;
        } else {
          add(floorFactory(floor), i, j, 1, 1);
        }
      }
    }

    for (int i = 4; i < 7; i++) {
      for (int j = 3; j < 9; j++) {
        if (doorCount > 0) {
          add(floorFactory(door), i, j, 1, 1);
          doorCount--;
        } else if (monsterCount > 0) {
          add(floorFactory(monster), i, j, 1, 1);
          monsterCount--;
        } else {
          add(floorFactory(floor), i, j, 1, 1);
        }
      }
    }

    for (int i = 7; i < 10; i++) {
      for (int j = 4; j < 8; j++) {
        if (doorCount > 0) {
          add(floorFactory(door), i, j, 1, 1);
          doorCount--;
        } else if (treasureCount > 0) {
          add(floorFactory(treasure), i, j, 1, 1);
          treasureCount--;
        } else {
          add(floorFactory(floor), i, j, 1, 1);
        }
      }
    }

    add(floorFactory(floor), 10, 5, 1, 1);
    add(floorFactory(floor), 10, 6, 1, 1);

  }

  private void makeCircleRoom()  {

    //main beef area
    for (int j = 4; j < 8; j++) {
      for (int i = 0; i < 12; i++) {
        if (doorCount > 0 && i == 0) {
          add(floorFactory(door), j, i, 1, 1);
          doorCount--;
        } else if (monsterCount > 0 && ((j == 4) || (j == 5))) {
          add(floorFactory(monster), j, i, 1, 1);
          monsterCount--;
        } else if (treasureCount > 0 && ((j == 6) || (j == 7))) {
          add(floorFactory(treasure), j, i, 1, 1);
          treasureCount--;
        } else {
          add(floorFactory(floor), j, i, 1, 1);
        }
      }
    }

    //rest of the stuff
    for (int k = 0; k < 12; k++) {
      if (k == 1 || k == 10) {
        for (int i = 3; i < 9; i++) {
          if (doorCount > 0 && i == 3) {
            add(floorFactory(door), k, i, 1, 1);
            doorCount--;
          } else {
            add(floorFactory(floor), k, i, 1, 1);
          }
        }
      } else if (k == 2 || k == 9) {
        for (int i = 2; i < 10;i++) {
          if (doorCount > 0 && i == 2) {
            add(floorFactory(door), k, i, 1, 1);
            doorCount--;
          } else {
            add(floorFactory(floor), k, i, 1, 1);
          }
        }
      } else if (k == 3 || k == 8) {
        for (int i = 1; i < 11; i++) {
          if (doorCount > 0 && i == 1) {
            add(floorFactory(door), k, i, 1, 1);
            doorCount--;
          } else {
            add(floorFactory(floor), k, i, 1, 1);
          }
        }
      } else if (k == 0 || k == 11) {
        for (int i = 4; i < 8; i++) {
          add(floorFactory(floor), k, i, 1, 1);
        }
      }
    }
  }

  private void makeTriangle() {

    //makes a triangle shape
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < i; j++) {
        if (doorCount > 0 && j == 0) {
          add(floorFactory(door), i, j, 1, 1);
          doorCount--;
        } else if (monsterCount > 0) {
          add(floorFactory(monster), i, j, 1, 1);
          monsterCount--;
        } else if (treasureCount > 0) {
          add(floorFactory(treasure), i, j, 1, 1);
          treasureCount--;
        } else {
          add(floorFactory(floor), i, j, 1, 1);
        }
      }
    }
  }

  private void makeTrapezoid() {
    //left pyramid
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < i; j++) {
        if (doorCount > 0 && j == 0) {
          add(floorFactory(door), i, j, 1, 1);
          doorCount--;
        } else {
          add(floorFactory(floor), i, j, 1, 1);
        }
      }
    }

    //mid section
    for (int i = 5; i < 10; i++) {
      for (int j = 0; j < 5; j++) {
        if (monsterCount > 0) {
          add(floorFactory(monster), i, j, 1, 1);
          monsterCount--;
        } else if (treasureCount > 0) {
          add(floorFactory(treasure), i, j, 1, 1);
          treasureCount--;
        } else {
          add(floorFactory(floor), i, j, 1, 1);
        }
      }
    }

    //right pyramid
    for (int i = 10; i < 15; i++) {
      for (int j = 14-i; j >= 0; j--) {
        if (doorCount > 0 && j == 0) {
          add(floorFactory(door), i, j, 1, 1);
          doorCount--;
        } else {
          add(floorFactory(floor), i, j, 1, 1);
        }
      }
    }

  }
}
