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


public class Main extends Application {

  BTree<Integer, hrtoon> hrtoonsDB;
  ArrayList<Integer> toonsIdList;
  String csvPath;

  @Override
  public void start(Stage primaryStage) {
    try {

      // *** QUERY VIEW ***
      Label SearchLabel = new Label("Search on title: ");
      TextField searchField = new TextField();

      HBox titleBox = new HBox(SearchLabel, searchField, new Label("\t"));

      Label dowLabel = new Label("Day of Week: ");
      ComboBox<String> dowComboBox = new ComboBox<String>();
      dowComboBox.getItems().addAll("", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
          "Saturday", "Sunday");
      dowComboBox.setValue("");
      HBox dowBox = new HBox(dowLabel, dowComboBox, new Label("\t"));

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

      Button goButton = new Button("Go!");

      HBox dataFilterBox = new HBox(titleBox, dowBox, typeBox, goButton);

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

      toonGrid.getColumns().addAll(columnId, columnDate, columnDow, columnType, columnTitle,
          columnEasterEggs, runtime);
      toonGrid.setMinHeight(700);
      VBox queryvBox = new VBox(new Label(""), dataFilterBox, new Label(""), toonGrid);

      goButton.setOnAction(e -> {
        String searchTitle = searchField.getText();
        String searchDow = dowComboBox.getValue();
        String searchType = typeComboBox.getValue();

        ArrayList<hrtoon> toonView = new ArrayList<hrtoon>();

        for (Integer key : toonsIdList) {
          try {
            hrtoon testToon = hrtoonsDB.getValue(key);
            if (testToon.getTitle().contains(searchTitle) && testToon.getDow().contains(searchDow)
                && testToon.getType().contains(searchType)) {
              toonView.add(testToon);
            }
          } catch (KeyNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
          } catch (NullKeyException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
          }
        }
        
        toonGrid.getItems().clear();
        toonGrid.getItems().addAll(toonView);
      });
      // *** STATS VIEW ***

      Button runStatsButton = new Button("Run Stats");
      Label showStats = new Label(
          "Number of Toons: \nAverage Number of Easter Eggs: \nMost Popular Upload Date: ");
      VBox statBox = new VBox(new Label(""), runStatsButton, showStats);

      runStatsButton.setOnAction(e -> {
        showStats.setText(MainHelper.runStats(hrtoonsDB));
      });

      // *** INSERT VIEW ***

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
      Button insertButton = new Button("Insert");
      VBox insertBox = new VBox(new Label(""), insertName, insertReleaseDate, insertDow, insertType,
          insertEggs, insertRuntime, insertButton);

      insertButton.setOnAction(e -> {
        String name = insertNameField.getText();
        String releaseDate = insertReleaseDateField.getText();
        String dow = insertDowField.getText();
        String type = insertTypeField.getText();
        String eggs = insertEggsField.getText();
        Integer eggsInt = null;
        String runTime = insertRuntimeField.getText();

        if (name.isEmpty() || releaseDate.isEmpty() || dow.isEmpty() || type.isEmpty()
            || eggs.isEmpty() || runTime.isEmpty()) {
          new Alert(Alert.AlertType.ERROR, "Enter data for each field.").showAndWait();
        } else {
          try {
            eggsInt = Integer.parseInt(eggs);
          } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Enter a valid number for easter eggs.").showAndWait();
          }

          if (eggsInt != null) {
            try {
              int insertId = (int) Math.pow((new Random()).nextInt(), 2);
              while (toonsIdList.contains(insertId)) {
                insertId = (int) Math.pow((new Random()).nextInt(), 2);
              }
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

      Label deleteIdLabel = new Label("Enter ID to delete: ");
      TextField deleteIdField = new TextField();
      deleteIdField.setMaxWidth(300);
      Button deleteButton = new Button("Delete");
      VBox deleteBox = new VBox(new Label(""), deleteIdLabel, deleteIdField, deleteButton);

      deleteButton.setOnAction(e -> {
        String fieldText = deleteIdField.getText();
        Integer deleteInt = null;
        if (fieldText.isEmpty()) {
          new Alert(Alert.AlertType.ERROR, "Enter an ID to be deleted.").showAndWait();
        } else {
          try {
            deleteInt = Integer.parseInt(fieldText);
          } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Enter a valid ID.").showAndWait();
          }

          if (deleteInt != null) {
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
      TextField loadFileField = new TextField();
      Button loadButton = new Button("Load");
      HBox loadFile =
          new HBox(new Label("Enter path to load CSV file: "), loadFileField, loadButton);

      loadButton.setOnAction(e -> {
        String newPath = loadFileField.getText();
        if (newPath.isEmpty() || !newPath.contains(".csv")) {
          new Alert(Alert.AlertType.ERROR, "Please enter a valid CSV.").showAndWait();
        } else {
          try {
            hrtoonsDB = MainHelper.parseCSV(newPath);
            toonsIdList = hrtoonsDB.getAllKeys();
            for (Integer key : toonsIdList) {
              toonGrid.getItems().add(hrtoonsDB.getValue(key));
            }
            new Alert(Alert.AlertType.CONFIRMATION, "Load Complete.").showAndWait();
          } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid CSV.").showAndWait();
          }
        }
      });

      // FILE SAVE
      TextField saveFileField = new TextField();
      Button saveButton = new Button("Save");
      HBox saveFile =
          new HBox(new Label("Enter path to save CSV file: "), saveFileField, saveButton);

      saveButton.setOnAction(e -> {
        String newPath = saveFileField.getText();
        if (newPath.isEmpty() || !newPath.contains(".csv")) {
          new Alert(Alert.AlertType.ERROR, "Please enter a valid CSV.").showAndWait();
        } else {
          try {
            MainHelper.saveCSV(hrtoonsDB, newPath);
            new Alert(Alert.AlertType.CONFIRMATION, "Save Complete.").showAndWait();
          } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid CSV.").showAndWait();
          }
        }
      });


      VBox SettingsBox = new VBox(new Label(""), loadFile, saveFile);
      // *** TAB INIT ***

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

      TabPane tabPane1 = new TabPane(queryTab, statTab, insertTab, deleteTab, settingsTab);

      // *** SCENE INIT ***

      csvPath = "hrtoons.csv";
      try {
        hrtoonsDB = MainHelper.parseCSV(csvPath);
        toonsIdList = hrtoonsDB.getAllKeys();
        for (Integer key : toonsIdList) {
          toonGrid.getItems().add(hrtoonsDB.getValue(key));
        }
      } catch (Exception ex) {
        new Alert(Alert.AlertType.INFORMATION, "Select a file on the Settings tab.").showAndWait();
        csvPath = "";
      }

      Scene scene = new Scene(tabPane1, 1300, 800);
      scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      primaryStage.setScene(scene);
      primaryStage.setTitle("Homestar Runner Toons Database");
      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
