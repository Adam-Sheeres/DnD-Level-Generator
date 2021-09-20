//name: Adam SHeeres-Paulicpulle
//Student ID: 1036569
//email: asheeres@uoguelph.ca
package gui;

import dnd.models.Treasure;
import dnd.models.Monster;
import javafx.scene.*;
import javafx.stage.*;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import dungeon.*;
import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;
import javafx.scene.control.TextField;
import java.text.*;

public class EditWindow {

  public static void showEditWindow(String title, Space updateThis) {
      Label addMonster =     new Label("Add Monster");
      Label removeMonster =  new Label("Remove Monster");
      Label addTreasure =    new Label("Add Treasure");
      Label removeTreasure = new Label("Remove Treasure");
      Button close =          new Button("Save");
      Button cancel = new Button("Cancel");
      Stage window = setUpKeepWindow(title);
      ArrayList<Monster> monsterList = new ArrayList<>();
      ArrayList<Treasure> treasureList = new ArrayList<>();
      GridPane grid = new GridPane();

      if (updateThis.getMonsters() != null) {
        monsterList = updateThis.getMonsters();
      }

      if (updateThis.getTreasureList() != null) {
        treasureList = updateThis.getTreasureList();
      }

      ChoiceBox monsterBox = genMonsterBox(monsterList);
      ChoiceBox treasureBox = genTreasureBox(treasureList);
      ChoiceBox monsterFullBox = genMonsterFullBox();
      ChoiceBox treasureFullBox = getnTreasureFullBox();

      close.setOnAction((ActionEvent event) -> { //save saves all changes
        try {
          updateThis.removeTreasure(((Number)NumberFormat.getInstance().parse(treasureBox.getValue().toString())).intValue());
        } catch (Exception e) {}
        try {
          updateThis.removeMonster(((Number)NumberFormat.getInstance().parse(monsterBox.getValue().toString())).intValue());
        } catch (Exception e) {}
        try {
          updateThis.addRandMonster(((Number)NumberFormat.getInstance().parse(monsterFullBox.getValue().toString())).intValue() - 1);
        } catch (Exception e) {}
        try {
          updateThis.addRandTreasure(((Number)NumberFormat.getInstance().parse(treasureFullBox.getValue().toString())).intValue() - 1);
        } catch (Exception e) {}
        window.close();
      });

      cancel.setOnAction((ActionEvent event) -> window.close());

      //setup the grid
      grid.setPadding(new Insets(10, 10, 10, 10));
      grid.setVgap(8);
      grid.setHgap(10);

      //addd everything
      grid.getChildren().add(addMonster);
      grid.getChildren().add(monsterFullBox);
      GridPane.setConstraints(addMonster, 0, 0);
      GridPane.setConstraints(monsterFullBox, 1, 0);

      grid.getChildren().add(removeMonster);
      GridPane.setConstraints(removeMonster, 0, 1);
      grid.getChildren().add(monsterBox);
      GridPane.setConstraints(monsterBox, 1, 1);

      grid.getChildren().add(addTreasure);
      grid.getChildren().add(treasureFullBox);
      GridPane.setConstraints(addTreasure, 0, 2);
      GridPane.setConstraints(treasureFullBox, 1, 2);

      grid.getChildren().add(removeTreasure);
      GridPane.setConstraints(removeTreasure, 0, 3);
      grid.getChildren().add(treasureBox);
      GridPane.setConstraints(treasureBox, 1, 3);

      grid.getChildren().add(close);
      GridPane.setConstraints(close, 1, 4);

      grid.getChildren().add(cancel);
      GridPane.setConstraints(cancel, 0, 4);

      Scene scene = new Scene(grid);
      window.setScene(scene);
      window.showAndWait();
  }

  public static TextField loadWindow(String title) {
    String toReturn = new String();
    Button cancel = new Button("Cancel");
    Button ok = new Button("Load");
    TextField input = new TextField();
    VBox layout = new VBox(1);
    Stage window = new Stage();
    GridPane grid = new GridPane();

    //Grid layout
    grid.setPadding(new Insets(10, 10, 10, 10));
    grid.setVgap(8);
    grid.setHgap(10);

    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle(title);
    window.setMinWidth(250);

    cancel.setOnAction((ActionEvent event) -> window.close());
    ok.setOnAction((ActionEvent event) -> {
      window.close();
    });

    input.setPromptText("File name");

    grid.getChildren().add(cancel);
    GridPane.setConstraints(cancel, 0, 1);
    grid.getChildren().add(ok);
    GridPane.setConstraints(ok, 0, 0);
    grid.getChildren().add(input);
    GridPane.setConstraints(input, 1, 0);

    Scene scene = new Scene(grid);
    window.setScene(scene);
    window.showAndWait();
    return input;
  }

  public static TextField saveWindow(String title) {
    String toReturn = new String();
    Button cancel = new Button("Cancel");
    Button ok = new Button("Save");
    TextField input = new TextField();
    GridPane grid = new GridPane();
    Stage window = new Stage();

    grid.setPadding(new Insets(10, 10, 10, 10));
    grid.setVgap(8);
    grid.setHgap(10);

    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle(title);
    window.setMinWidth(250);

    cancel.setOnAction((ActionEvent event) -> window.close());
    ok.setOnAction((ActionEvent event) -> {
      window.close();
    });

    input.setPromptText("File name");

    grid.getChildren().add(cancel);
    GridPane.setConstraints(cancel, 0, 1);
    grid.getChildren().add(ok);
    GridPane.setConstraints(ok, 0, 0);
    grid.getChildren().add(input);
    GridPane.setConstraints(input, 1, 0);

    Scene scene = new Scene(grid);
    window.setScene(scene);
    window.showAndWait();
    return input;
  }

  private static String getTextFromText(TextField input) {
    return "sav/" + input.getText() + ".sav";
  }

  private static ChoiceBox genTreasureBox(ArrayList<Treasure> treasureList) {
    ChoiceBox temp = new ChoiceBox();

    for (int i = 0; i < treasureList.size(); i++) {
      temp.getItems().add(Integer.toString(i+1) + "| " + treasureList.get(i).getDescription());
    }
    return temp;
  }

  private static ChoiceBox genMonsterBox(ArrayList<Monster> monsterList) {
    ChoiceBox temp = new ChoiceBox();

    for (int i = 0; i < monsterList.size(); i++) {
      temp.getItems().add(Integer.toString(i+1) + "| " + monsterList.get(i).getDescription());
    }
    return temp;
  }

  private static ChoiceBox genMonsterFullBox() {
    ChoiceBox box = new ChoiceBox();
    Integer[] type = {1, 3, 5, 15, 16, 18, 19, 20};

    for (int i = 0; i < 8; i++) {
      Monster newMonster = new Monster();
      newMonster.setType(type[i]);
      box.getItems().add(Integer.toString(i+1) + "| " + newMonster.getDescription());
    }
    return box;
  }

  private static ChoiceBox getnTreasureFullBox() {
    ChoiceBox box = new ChoiceBox();
    Integer[] type = {15, 40, 55, 70, 85, 92, 96, 98};

    for (int i = 0; i < 8; i++) {
      Treasure newTreasure = new Treasure();
      newTreasure.chooseTreasure(type[i]);
      box.getItems().add(Integer.toString(i+1) + "| " + newTreasure.getDescription());
    }
    return box;
  }

  private static Stage setUpKeepWindow(String title) {
    Stage temp = new Stage();
    temp.initModality(Modality.APPLICATION_MODAL);
    temp.setTitle(title);
    temp.setMinWidth(250);
    return temp;
  }

}
