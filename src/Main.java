import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        URL a = getClass().getResource("MainWindow.fxml");
        BorderPane root = FXMLLoader.load(a);
//        root.setStyle("-fx-background-color:rgb(186,153,122,0.7); -fx-background-radius:30;");

        stage.setMaximized(true);
        stage.toFront();
        Scene scene = new Scene(root, Color.TRANSPARENT);
//        scene.getStylesheets().add(getClass().getResource("/Styles/brightTheme.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
