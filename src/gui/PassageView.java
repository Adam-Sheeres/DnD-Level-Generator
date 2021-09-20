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

public class PassageView extends GridPane {
  private String door = "/res/doorTile.png";
  private String floor = "/res/floor.png";
  private String treasure = "/res/treasure.png";
  private String monster = "/res/monster2.png";

  public PassageView (Space passage) {
    int monsterCount = 0;
    int treasureCount = 0;

    if (passage.getTreasureList() != null) {
      treasureCount = passage.getTreasureList().size();
    }
    if (passage.getMonsters() != null) {
      monsterCount = passage.getMonsters().size();
    }

    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 10; j++) {
        if (i == 2 && j == 0) {
          add(floorFactory("/res/doorLeft.png"), j, i, 1, 1);
        } else if (i == 2 && j == 9) {
          add(floorFactory("/res/doorRight.png"), j, i, 1, 1);
        } else if (monsterCount > 0 && i > 1) {
          add(floorFactory(monster), j, i, 1, 1);
          monsterCount--;
        } else if (treasureCount > 0 && i != 2) {
          add(floorFactory(treasure), j, i, 1, 1);
          treasureCount--;
        } else {
          add(floorFactory(floor), j, i, 1, 1);
        }
      }
    }
  }


  private Node floorFactory(String image) {
    Image floor = new Image(getClass().getResourceAsStream(image));
    Label toReturn = new Label();
    ImageView imageView = new ImageView(floor);
    imageView.setFitWidth(50);
    imageView.setFitHeight(50);
    toReturn.setGraphic(imageView);
    return toReturn;
  }
}
