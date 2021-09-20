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


public class Interface<toReturn> extends Application {
    /* Even if it is a GUI it is useful to have instance variables
    so that you can break the processing up into smaller methods that have
    one responsibility.
     */
    private ArrayList<Chamber> chamberList;
    private int currentDisplayChamber;
    private Controller theController;
    private BorderPane root;  //the root element of this GUI
    private Popup descriptionPane;
    private Text mainTextArea;
    private Stage primaryStage;  //The stage that is passed in on initialization
    private Level currentLevel;
    private Passage currentPassage;
    private Node rightDoorSection;
    private Node centreArea;
    private ChoiceBox doorList;
    private Space currentSelection;
    private GridPane chamberDisplay;

    /*a call to start replaces a call to the constructor for a JavaFX GUI*/
    @Override
    public void start(Stage assignedStage) {
        /*Initializing instance variables */
        currentLevel = new Level();
        chamberList = new ArrayList<Chamber>();

        chamberList = currentLevel.getChambers();
        theController = new Controller(this);
        primaryStage = assignedStage;
        /*Border Panes have  top, left, right, center and bottom sections */
        root = setUpRoot();
        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setTitle("D&D Level Generator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private BorderPane setUpRoot() { //add all the buttons and stuff here
        BorderPane temp = new BorderPane();
        temp.setTop(addSaveButton());

        //buttons
        Node left = setChamberButtons();
        temp.setLeft(left);

        //edit button
        Node bottom = editButton();
        temp.setBottom(bottom);

        //save/load
        Node save = addSaveButton();
        temp.setTop(save);

        //fillDoorList
        currentSelection = currentLevel.getThisChamber(0);
        Node right = displayDoorList(currentSelection);
        temp.setRight(right);

        //this is where everything will be displayed
        centreArea = genCenterArea();
        temp.setCenter(centreArea);
        return temp;
    }


    private Node setChamberButtons() {
        /*this method should be broken down into even smaller methods, maybe one per button*/
        VBox temp = new VBox();
        GridPane grid = setUpGrid();

        temp.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;" +
                "-fx-background-color: white;");

        for (int i = 0; i < 5; i++) {
          final int chamberNumber = i;
          Button chamber = createButton("Chamber " + Integer.toString(i+1), "-fx-background-color: blue; ");
          chamber.setOnAction((ActionEvent event) -> {
            currentDisplayChamber = chamberNumber;
            currentSelection = currentLevel.getThisChamber(chamberNumber);
            updateMidBit(currentLevel.getThisChamber(chamberNumber).getDescription());
            root.setRight(displayDoorList(currentLevel.getThisChamber(chamberNumber)));
          });
          grid.getChildren().add(chamber);
          GridPane.setConstraints(chamber, 0, i);
          addPassageButtons(grid, currentLevel.getThisChamber(chamberNumber), i);
        }
        temp.getChildren().add(grid);
        return temp;
    }

    /**
      Adds the buttons for the chamber to the left side
    */
    private void addPassageButtons(GridPane temp, Chamber chamber, int row) {
      for (int i = 0; i < chamber.getDoors().size(); i++) { //loop over all the doors
        Space passage = chamber.getPassageFromDoor(i);
        Button passageButton = createButton(passage.getName(), "-fx-text-fill: #242d35;");
        passageButton.setOnAction((ActionEvent event) -> {
          currentSelection = passage;
          updateMidBit(passage.getDescription());
          //update the door list
          root.setRight(displayDoorList(passage));
        });
        temp.getChildren().add(passageButton);
        GridPane.setConstraints(passageButton, i+1, row);
      }
    }

    /**
      Shows the edit button
    */
    private Node editButton() {
      VBox temp = new VBox();
      Button editButton = new Button("Edit");
      GridPane grid = setUpGrid();

      temp.setStyle("-fx-paddding: 10;" +
                    "-fx-border-style: solid inside;" +
                    "-fx-border-width: 2;" +
                    "-fx-border-color: red;");

      editButton.setOnAction((ActionEvent event) -> {
        EditWindow.showEditWindow("Edit", currentSelection);
        updateMidBit(currentSelection.getDescription());
      });
      grid.getChildren().add(editButton);
      GridPane.setConstraints(editButton, 0, 0);
      temp.getChildren().add(grid);
      return temp;
    }

    /**
      Displays the door list on the right side
    */
    private Node displayDoorList(Space useThis) {
      VBox temp = new VBox();
      Text descrip = new Text();
      GridPane grid = setUpGrid();
      Button goThrough = new Button("Go through");

      temp.setPadding(new Insets(10, 10, 10, 10));

      temp.setStyle("-fx-background-color: white");
      int doorCount = currentSelection.getDoors().size();

      doorList = new ChoiceBox();
      for (int i = 0; i < doorCount; i++) {
          doorList.getItems().add("Door " + Integer.toString(i+1));
      }

      goThrough.setOnAction((e) -> {
        //change current node to the repective one
        Door currentDoor = theController.getDoor();
        if (currentDoor != null) {
          if (currentSelection != currentDoor.getSpaces().get(0)) {
            currentSelection = currentDoor.getSpaces().get(0);
            root.setRight(displayDoorList(currentSelection));
            updateMidBit(currentDoor.getSpaces().get(0).getDescription());
          } else {
            currentSelection = currentDoor.getSpaces().get(1);
            root.setRight(displayDoorList(currentSelection));
            updateMidBit(currentDoor.getSpaces().get(1).getDescription());
          }
        }
      });

      //when you select a new element
      doorList.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
        final String newNewVal = newValue.toString();
        for (int i = 0; i < doorCount; i++) {
          if (newNewVal.contains(Integer.toString(i+1))) { //go to the correct door
            Door thisDoor = currentSelection.getDoors().get(i);
            theController.setDoor(thisDoor);
            String doorConnections = new String();
            Space current;
            if (currentSelection != thisDoor.getSpaces().get(0)) {
              doorConnections += thisDoor.getSpaces().get(0).getName();
            } else {
              doorConnections += thisDoor.getSpaces().get(1).getName();
            }
            descrip.setText(thisDoor.getDescription() + "\n" +
                            "Connects to " + doorConnections);
          }
        }
      });
      grid.getChildren().add(descrip);
      GridPane.setConstraints(descrip, 0, 1);

      grid.getChildren().add(goThrough);
      GridPane.setConstraints(goThrough, 0, 0);

      grid.getChildren().add(doorList);
      GridPane.setConstraints(doorList, 1, 0);

      temp.getChildren().add(grid);
      return temp;
    }

    /* an example of a popup area that can be set to nearly any
    type of node
     */
    private Popup createPopUp(int x, int y, String text) {
        Popup popup = new Popup();
        popup.setX(x);
        popup.setY(y);
        TextArea textA = new TextArea(text);
        popup.getContent().addAll(textA);
        textA.setStyle("-fx-paddding: 10;" +
                      "-fx-border-style: solid inside;" +
                      "-fx-border-width: 10;" +
                      "-fx-border-color: red;");
        textA.setMinWidth(80);
        textA.setMinHeight(50);
        return popup;
    }

    private Node addSaveButton() {
      VBox temp = new VBox();
      Button save = new Button("Save");
      Button load = new Button("Load");
      Button newLevel = new Button("New");
      GridPane grid = new GridPane();

      grid.setPadding(new Insets(10, 10, 10, 10));
      grid.setVgap(8);
      grid.setHgap(10);

      //LOADING
      load.setOnAction((ActionEvent event) ->  {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Save Files (*.sav)", "*.sav");
        fileChooser.getExtensionFilters().add(extFilter);

        try {
          final File selectedFile = fileChooser.showOpenDialog(primaryStage);
          if (selectedFile != null) {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(selectedFile));
            currentLevel = (Level)in.readObject();
            root = setUpRoot();
            primaryStage.close();
            Scene scene = new Scene(root, 1200, 800);
            primaryStage.setScene(scene);
            primaryStage.show();
          }
        } catch (IOException e) {
          System.out.println("IO Exception");
        } catch (ClassNotFoundException ex) {
          System.out.println("ClassNotFoundException");
        }
      });

      //SAVING
      save.setOnAction((ActionEvent event) ->  {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Save Files (*.sav)", "*.sav");
        fileChooser.getExtensionFilters().add(extFilter);

        try {
          final File selectedFile = fileChooser.showSaveDialog(primaryStage);
          if (selectedFile != null) {
            ObjectOutputStream outPutDest = new ObjectOutputStream(new FileOutputStream(selectedFile));
            outPutDest.writeObject(currentLevel);
            outPutDest.close();
          }
        } catch (IOException e) {
          System.out.println("UNABLE TO SAVE");
        }
      });

      newLevel.setOnAction((ActionEvent event) -> {
        currentLevel = new Level();
        root = setUpRoot();
        primaryStage.close();
        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
      });

      grid.getChildren().add(save);
      GridPane.setConstraints(save, 0, 0);
      grid.getChildren().add(load);
      GridPane.setConstraints(load, 1, 0);
      grid.getChildren().add(newLevel);
      GridPane.setConstraints(newLevel, 2, 0);


      temp.getChildren().add(grid);
      return temp;
    }

    /*generic button creation method ensure that all buttons will have a
    similar style and means that the style only need to be in one place
     */
    private Button createButton(String text, String format) {
        Button btn = new Button();
        btn.setText(text);
        btn.setStyle("");
        return btn;
    }

    public void changeDescriptionText(String text) {
      //updates the main text area
      mainTextArea.setText(text);
    }

    private Node genCenterArea() {
      //Generates the center area for the UI
      GridPane grid = setUpGrid();
      mainTextArea = new Text(currentSelection.getDescription());
      mainTextArea.setStyle("-fx-background-color: white");

      if (currentSelection.getName().contains("Chamber")) { //if the current selected thing is a chamber
        chamberDisplay = new ChamberView(currentLevel.getThisChamber(currentDisplayChamber));
      } else {
        chamberDisplay = new PassageView(currentSelection);
      }

      grid.getChildren().add(chamberDisplay);
      GridPane.setConstraints(chamberDisplay, 0, 1);

      grid.getChildren().add(mainTextArea);
      GridPane.setConstraints(mainTextArea, 0, 0);
      return grid;
    }

    public void updateMidBit(String text) {
      //Updates the middle section (description + visual)
      changeDescriptionText(text);
      centreArea = genCenterArea();
      root.setCenter(centreArea);
    }

    //Default grid setup
    private GridPane setUpGrid() {
      GridPane grid = new GridPane();
      grid.setPadding(new Insets(10, 10, 10, 10));
      grid.setVgap(8);
      grid.setHgap(10);
      return grid;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
