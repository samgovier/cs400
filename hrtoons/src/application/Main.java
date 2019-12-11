package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.image.*;


public class Main extends Application {
  @Override
  public void start(Stage primaryStage) {
    try {
      // TODO MAKE DARK MODE, or just colorful. HR colors?
      
      Label SearchLabel = new Label("Search: ");
      TextField searchField = new TextField();
      Button searchButton = new Button("Go!");
      HBox searchBox = new HBox(SearchLabel, searchField, searchButton);
      
      Label SortLabel = new Label("Sort by: ");
      ComboBox<String> SortComboBox = new ComboBox();
      SortComboBox.getItems().addAll("Release Date","Release DOW","Type","Title","Easter Eggs","Runtime");
      HBox SortBox = new HBox(SortLabel, SortComboBox);
            
      GridPane toonGrid = new GridPane();
      toonGrid.add(new TextArea("Release DOW"), 0, 0);
      toonGrid.add(new TextArea("Release Date"), 1, 0);
      toonGrid.add(new TextArea("Type"), 2, 0);
      toonGrid.add(new TextArea("Title"), 3, 0);
      toonGrid.add(new TextArea("Easter Eggs"), 4, 0);
      toonGrid.add(new TextArea("Runtime"), 5, 0);
      toonGrid.add(new TextArea("test3"), 0, 1);
      toonGrid.add(new TextArea("test4"), 1, 1);

      VBox vBox1 = new VBox(searchBox, SortBox, toonGrid);

      BorderPane root = new BorderPane();
      root.setCenter(vBox1);
      
      Tab queryTab = new Tab("Query");
      queryTab.setContent(vBox1);
      queryTab.setClosable(false);
      Tab insertTab = new Tab("Insert");
      insertTab.setClosable(false);
      Tab deleteTab = new Tab("Delete");
      deleteTab.setClosable(false);
      Tab settingsTab = new Tab("Settings");
      settingsTab.setClosable(false);
      
      TabPane tabPane1 = new TabPane(queryTab, insertTab, deleteTab, settingsTab);
      
      Scene scene = new Scene(tabPane1, 400, 400);
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
