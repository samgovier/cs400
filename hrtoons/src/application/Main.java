package application;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tree.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
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

      Label SearchLabel = new Label("Search: ");
      TextField searchField = new TextField();
      Button searchButton = new Button("Go!");
      HBox searchBox = new HBox(SearchLabel, searchField, searchButton);

      Label SortLabel = new Label("Sort by: ");
      ComboBox<String> SortComboBox = new ComboBox<String>();
      SortComboBox.getItems().addAll("Release Date", "Release DOW", "Type", "Title", "Easter Eggs",
          "Runtime");
      HBox SortBox = new HBox(SortLabel, SortComboBox);

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
      VBox queryvBox = new VBox(searchBox, SortBox, toonGrid);

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
      HBox insertRuntime = new HBox(new Label("Runtime (mm:ss): \t"), insertRuntimeField);
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
              hrtoonsDB.insert(toonsIdList.size(),
                  new hrtoon(toonsIdList.size(), releaseDate, dow, type, name, eggsInt, runTime));
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
              hrtoonsDB.remove(deleteInt);
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

      TextField loadFile = new TextField();

      // https://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Open Resource File");
      // fileChooser.showOpenDialog(stage);

      // *** TAB INIT ***

      Tab queryTab = new Tab("Query");
      queryTab.setContent(queryvBox);
      queryTab.setClosable(false);
      Tab insertTab = new Tab("Insert");
      insertTab.setContent(insertBox);
      insertTab.setClosable(false);
      Tab deleteTab = new Tab("Delete");
      deleteTab.setContent(deleteBox);
      deleteTab.setClosable(false);
      Tab settingsTab = new Tab("Settings");
      settingsTab.setClosable(false);

      TabPane tabPane1 = new TabPane(queryTab, insertTab, deleteTab, settingsTab);

      // *** SCENE INIT ***

      csvPath = "hrtoons.csv";
      hrtoonsDB = MainHelper.parseCSV(csvPath);
      toonsIdList = hrtoonsDB.getAllKeys();

      for (Integer key : toonsIdList) {
        toonGrid.getItems().add(hrtoonsDB.getValue(key));
      }

      Scene scene = new Scene(tabPane1, 1300, 800);
      scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      primaryStage.setScene(scene);
      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
