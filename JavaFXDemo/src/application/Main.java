package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.image.*;


public class Main extends Application {
  private static Integer clickCount = 0;

  @Override
  public void start(Stage primaryStage) {
    try {
      BorderPane root = new BorderPane();

      Label label1 = new Label("Places I've lived for > 1 month");
      TextArea textArea1 = new TextArea("nowhere");
      textArea1.setText("Monona\nMadison");
      textArea1.setMaxHeight(10);
      textArea1.setFont(new Font("Courier", 15));

      TextField textField1 = new TextField();
      PasswordField pf1 = new PasswordField();

//      ImageView iv1 = new ImageView(new Image("example.jpg"));
//      iv1.setPreserveRatio(true);

      ComboBox<String> combo1 = new ComboBox();
      combo1.getItems().addAll("option1", "option2");

      HBox hbox1 = new HBox(pf1, combo1);

      Label labelClickCount = new Label("0");
      Button button1 = new Button("click counter");
      button1.setOnAction(e -> {
        clickCount++;
        System.out.println(clickCount);
        labelClickCount.setText(clickCount.toString());
      });

      VBox vBox1 = new VBox(label1, textArea1, labelClickCount, button1);
      vBox1.getChildren().addAll(hbox1);

      root.setCenter(vBox1);
      Scene scene = new Scene(root, 400, 400);
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
