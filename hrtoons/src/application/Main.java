//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: Final Project
// Files: BTree.java, BtreeADT.java, BTreeTests.java,
// DuplicateKeyException.java, KeyNotFoundException.java, NullKeyException.java,
// Main.java, MainHelper.java, hrtoon.java
// Course: Computer Science 400
//
// Author: Sam Govier
// Email: sgovier@wisc.edu
// Lecturer's Name: Andrew Kuemmel
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here. Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates,
// strangers, and others do. If you received no outside help from either type
// of source, then please explicitly indicate NONE.
//
// Persons: NONE
// Online Sources: NONE
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
package application;

import java.util.ArrayList;
import java.util.Random;
import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tree.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.image.*;

/**
 * Main is the javafx application for hrtoons. It has 4 tabs: one for file management, one for
 * insert, one for remove, and one for data queries.
 */
public class Main extends Application {

  // hrtoonsDB is the hrtoons database object
  BTree<Integer, hrtoon> hrtoonsDB;

  // toonsIdList is an arraylist of all the keys in the database
  ArrayList<Integer> toonsIdList;

  // csvPath is the path to the csv file
  String csvPath;

  /**
   * start launches the javafx application and defines interaction
   */
  @Override
  public void start(Stage primaryStage) {
    try {

      // *** QUERY VIEW ***

      // construct the search field
      Label SearchLabel = new Label("Search on title: ");
      TextField searchField = new TextField();
      HBox titleBox = new HBox(SearchLabel, searchField, new Label("\t"));

      // construct the day of week combo selection
      Label dowLabel = new Label("Day of Week: ");
      ComboBox<String> dowComboBox = new ComboBox<String>();
      dowComboBox.getItems().addAll("", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
          "Saturday", "Sunday");
      dowComboBox.setValue("");
      HBox dowBox = new HBox(dowLabel, dowComboBox, new Label("\t"));

      // construct the type of toon combo selection
      Label typeLabel = new Label("Type: ");
      ComboBox<String> typeComboBox = new ComboBox<String>();
      typeComboBox.getItems().addAll("", "Book", "Uncategorized", "Toon", "Intro", "Game", "Short",
          "Main Page", "Menu", "Marzipan's Answering Machine", "Holiday Toon", "Email",
          "Powered By The Cheat", "(N/A)", "Blog Entry", "Teen Girl Squad", "Character Video",
          "Puppet Stuff", "DVD Exclusive", "YouTube", "New Stuff", "Game Trailer", "Endorsement",
          "SBCG4AP", "Hremail", "Email/Hremail", "Email/Holiday Toon",
          "Marzipan's Answering Machine/Holiday Toon", "Skills of an Artist",
          "Holiday Toon/Puppet Stuff", "The Deleteheads Download", "Kickstarter", "Six-Sadded Die");
      typeComboBox.setValue("");
      HBox typeBox = new HBox(typeLabel, typeComboBox, new Label("\t"));

      // define the query button and HBox for the query materials
      Button goButton = new Button("Go!");
      HBox dataFilterBox = new HBox(titleBox, dowBox, typeBox, goButton);

      // construct the grid of data
      TableView toonGrid = new TableView();
      TableColumn<String, hrtoon> columnId = new TableColumn<>("ID");
      columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
      TableColumn<String, hrtoon> columnDate = new TableColumn<>("Date");
      columnDate.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
      TableColumn<String, hrtoon> columnDow = new TableColumn<>("Day Of Week");
      columnDow.setCellValueFactory(new PropertyValueFactory<>("dow"));
      TableColumn<String, hrtoon> columnType = new TableColumn<>("Type");
      columnType.setCellValueFactory(new PropertyValueFactory<>("type"));
      TableColumn<String, hrtoon> columnTitle = new TableColumn<>("Title");
      columnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
      TableColumn<String, hrtoon> columnEasterEggs = new TableColumn<>("Easter Eggs");
      columnEasterEggs.setCellValueFactory(new PropertyValueFactory<>("easterEggs"));
      TableColumn<String, hrtoon> runtime = new TableColumn<>("Runtime");
      runtime.setCellValueFactory(new PropertyValueFactory<>("runtime"));

      // add the columns
      toonGrid.getColumns().addAll(columnId, columnDate, columnDow, columnType, columnTitle,
          columnEasterEggs, runtime);
      toonGrid.setMinHeight(700);

      // construct the tab
      VBox queryvBox = new VBox(new Label(""), dataFilterBox, new Label(""), toonGrid);

      // when the query button is hit
      goButton.setOnAction(e -> {

        // collect query details
        String searchTitle = searchField.getText();
        String searchDow = dowComboBox.getValue();
        String searchType = typeComboBox.getValue();

        // toonView defines what will be displayed
        ArrayList<hrtoon> toonView = new ArrayList<hrtoon>();

        // for each toon in the DB, if it matches the search add it to the display
        for (Integer key : toonsIdList) {
          try {
            hrtoon testToon = hrtoonsDB.getValue(key);
            if (testToon.getTitle().contains(searchTitle) && testToon.getDow().contains(searchDow)
                && testToon.getType().contains(searchType)) {
              toonView.add(testToon);
            }
          } catch (KeyNotFoundException e1) {
            new Alert(Alert.AlertType.ERROR, "Bad ID entered. Please restart.").showAndWait();
          } catch (NullKeyException e1) {
            new Alert(Alert.AlertType.ERROR, "Bad ID entered. Please restart.").showAndWait();
          }
        }

        // clear the grid and reset the data
        toonGrid.getItems().clear();
        toonGrid.getItems().addAll(toonView);
      });

      // *** STATS VIEW ***

      // define the stats tab
      Button runStatsButton = new Button("Run Stats");
      Label showStats = new Label(
          "Number of Toons: \nAverage Number of Easter Eggs: \nMost Popular Upload Date: ");
      VBox statBox = new VBox(new Label(""), runStatsButton, showStats);

      // if the stats button is pressed, run the stats
      runStatsButton.setOnAction(e -> {
        showStats.setText(MainHelper.runStats(hrtoonsDB));
      });

      // *** INSERT VIEW ***

      // add a field for each field of the hrtoons object
      TextField insertNameField = new TextField();
      HBox insertName = new HBox(new Label("Title: \t\t\t"), insertNameField);
      TextField insertReleaseDateField = new TextField();
      HBox insertReleaseDate = new HBox(new Label("Release Date: \t\t"), insertReleaseDateField);
      TextField insertDowField = new TextField();
      HBox insertDow = new HBox(new Label("Day Of Week: \t\t"), insertDowField);
      TextField insertTypeField = new TextField();
      HBox insertType = new HBox(new Label("Type: \t\t\t"), insertTypeField);
      TextField insertEggsField = new TextField();
      HBox insertEggs = new HBox(new Label("# of Easter Eggs: \t"), insertEggsField);
      TextField insertRuntimeField = new TextField();
      HBox insertRuntime = new HBox(new Label("Runtime: \t\t\t"), insertRuntimeField);

      // define the button for insert
      Button insertButton = new Button("Insert");
      VBox insertBox = new VBox(new Label(""), insertName, insertReleaseDate, insertDow, insertType,
          insertEggs, insertRuntime, insertButton);

      // when the insert button is hit
      insertButton.setOnAction(e -> {

        // get all the field data
        String name = insertNameField.getText();
        String releaseDate = insertReleaseDateField.getText();
        String dow = insertDowField.getText();
        String type = insertTypeField.getText();
        String eggs = insertEggsField.getText();
        Integer eggsInt = null;
        String runTime = insertRuntimeField.getText();

        // if anything is empty, show an error and exit, otherwise continue
        if (name.isEmpty() || releaseDate.isEmpty() || dow.isEmpty() || type.isEmpty()
            || eggs.isEmpty() || runTime.isEmpty()) {
          new Alert(Alert.AlertType.ERROR, "Enter data for each field.").showAndWait();
        } else {

          // try to parse easter eggs
          try {
            eggsInt = Integer.parseInt(eggs);
          } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Enter a valid number for easter eggs.").showAndWait();
          }

          // continue if easter eggs was parsed
          if (eggsInt != null) {

            try {

              // create an insert id by getting a square, to assure a positive number
              int insertId = (int) Math.pow((new Random()).nextInt(), 2);
              while (toonsIdList.contains(insertId)) {
                insertId = (int) Math.pow((new Random()).nextInt(), 2);
              }

              // insert into the database and confirm insertion. Otherwise display error
              hrtoon toonToAdd =
                  new hrtoon(insertId, releaseDate, dow, type, name, eggsInt, runTime);
              hrtoonsDB.insert(insertId, toonToAdd);
              toonsIdList.add(insertId);
              toonGrid.getItems().add(toonToAdd);

              new Alert(Alert.AlertType.CONFIRMATION, "Insertion Complete.").showAndWait();
            } catch (DuplicateKeyException e1) {
              new Alert(Alert.AlertType.ERROR, "Duplicate ID entered.").showAndWait();
            } catch (NullKeyException e1) {
              new Alert(Alert.AlertType.ERROR, "ID was null.").showAndWait();
            }
          }
        }
      });

      // *** DELETE VIEW ***

      // create the delete field and button
      Label deleteIdLabel = new Label("Enter ID to delete: ");
      TextField deleteIdField = new TextField();
      deleteIdField.setMaxWidth(300);
      Button deleteButton = new Button("Delete");
      VBox deleteBox = new VBox(new Label(""), deleteIdLabel, deleteIdField, deleteButton);

      // when the delete button is hit
      deleteButton.setOnAction(e -> {

        // get the id for deletion
        String fieldText = deleteIdField.getText();
        Integer deleteInt = null;

        // if nothing was entered or it wasn't an integer, exit, otherwise continue
        if (fieldText.isEmpty()) {
          new Alert(Alert.AlertType.ERROR, "Enter an ID to be deleted.").showAndWait();
        } else {
          try {
            deleteInt = Integer.parseInt(fieldText);
          } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Enter a valid ID.").showAndWait();
          }
          if (deleteInt != null) {

            // try to remove: if it fails, show an error, otherwise show confirmation
            try {
              toonGrid.getItems().remove(hrtoonsDB.getValue(deleteInt));
              hrtoonsDB.remove(deleteInt);
              toonsIdList.remove(deleteInt);
              new Alert(Alert.AlertType.CONFIRMATION, "Deletion Complete.").showAndWait();
            } catch (KeyNotFoundException knfEx) {
              new Alert(Alert.AlertType.ERROR, "ID not found.").showAndWait();
            } catch (NullKeyException nullEx) {
              new Alert(Alert.AlertType.ERROR, "ID not found.").showAndWait();
            }
          }
        }
      });

      // *** SETTINGS VIEW ***

      // FILE LOAD

      // create file load field and button
      TextField loadFileField = new TextField();
      Button loadButton = new Button("Load");
      HBox loadFile =
          new HBox(new Label("Enter path to load CSV file: "), loadFileField, loadButton);

      // when load button is hit
      loadButton.setOnAction(e -> {

        // check that the path is valid
        String newPath = loadFileField.getText();
        if (newPath.isEmpty() || !newPath.contains(".csv")) {
          new Alert(Alert.AlertType.ERROR, "Please enter a valid CSV.").showAndWait();
        } else {

          // try to parse the csv and put it in the query grid
          try {
            hrtoonsDB = MainHelper.parseCSV(newPath);
            toonsIdList = hrtoonsDB.getAllKeys();
            for (Integer key : toonsIdList) {
              toonGrid.getItems().add(hrtoonsDB.getValue(key));
            }

            // show confirmation if successful: otherwise show error
            new Alert(Alert.AlertType.CONFIRMATION, "Load Complete.").showAndWait();
          } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid CSV.").showAndWait();
          }
        }
      });

      // FILE SAVE

      // create file save field and button
      TextField saveFileField = new TextField();
      Button saveButton = new Button("Save");
      HBox saveFile =
          new HBox(new Label("Enter path to save CSV file: "), saveFileField, saveButton);

      // when the save button is hit
      saveButton.setOnAction(e -> {

        // check that the path is valid
        String newPath = saveFileField.getText();
        if (newPath.isEmpty() || !newPath.contains(".csv")) {
          new Alert(Alert.AlertType.ERROR, "Please enter a valid CSV.").showAndWait();
        } else {

          // try to save the csv. show confirmation or error
          try {
            MainHelper.saveCSV(hrtoonsDB, newPath);
            new Alert(Alert.AlertType.CONFIRMATION, "Save Complete.").showAndWait();
          } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid CSV.").showAndWait();
          }
        }
      });

      // create the settings view vertical box
      VBox SettingsBox = new VBox(new Label(""), loadFile, saveFile);

      // *** TAB INIT ***

      // initialize all tabs and make them un-closable
      Tab queryTab = new Tab("Query");
      queryTab.setContent(queryvBox);
      queryTab.setClosable(false);
      Tab statTab = new Tab("Stats");
      statTab.setContent(statBox);
      statTab.setClosable(false);
      Tab insertTab = new Tab("Insert");
      insertTab.setContent(insertBox);
      insertTab.setClosable(false);
      Tab deleteTab = new Tab("Delete");
      deleteTab.setContent(deleteBox);
      deleteTab.setClosable(false);
      Tab settingsTab = new Tab("Settings");
      settingsTab.setContent(SettingsBox);
      settingsTab.setClosable(false);

      // initialize the full tab pane
      TabPane tabPane1 = new TabPane(queryTab, statTab, insertTab, deleteTab, settingsTab);

      // *** STARTUP INIT ***

      // initialize the database
      csvPath = "hrtoons.csv";
      try {

        // try to parse hrtoons.csv, if it's there
        hrtoonsDB = MainHelper.parseCSV(csvPath);
        toonsIdList = hrtoonsDB.getAllKeys();
        for (Integer key : toonsIdList) {
          toonGrid.getItems().add(hrtoonsDB.getValue(key));
        }

        // if it fails, ask the user to select a file in settings and unset csvPath
      } catch (Exception ex) {
        new Alert(Alert.AlertType.INFORMATION, "Select a file on the Settings tab.").showAndWait();
        csvPath = "";
      }

      // initialize the full javafx scene and title
      Scene scene = new Scene(tabPane1, 1300, 800);
      scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      primaryStage.setScene(scene);
      primaryStage.setTitle("Homestar Runner Toons Database");
      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * main launches the javafx application
   * 
   * @param args
   */
  public static void main(String[] args) {
    launch(args);
  }
}
