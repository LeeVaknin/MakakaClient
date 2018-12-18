import View.MainWindow.MainWindowController;
import ViewModels.PipeGameViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    private double desiredSceneWidth;
    private double desiredSceneHeight;

    @Override
    public void start(Stage stage) throws Exception {
        URL a = getClass().getResource("View/MainWindow/MainWindow.fxml");
//        stage.initStyle(StageStyle.DECORATED);

        SetDesiredSceneSized();

//        scene.getStylesheets().add("Styles/Dark/menuBar.css");
//        scene.getStylesheets().add("Styles/Dark/mainStyles.css");
        PipeGameViewModel vm = new PipeGameViewModel(scene);
        FXMLLoader fxl = new FXMLLoader();
        BorderPane root = fxl.load(getClass().getResource("MainWindow.fxml").openStream());
        MainWindowController mwc=fxl.getController(); // View
        mwc.setViewModel(vm);
        vm.addObserver(mwc);

        Scene scene = new Scene(root,desiredSceneWidth, desiredSceneHeight, Color.TRANSPARENT);

        stage.setScene(scene);
        stage.toFront();
        stage.setMaximized(true);
        stage.show();
    }

    // Create relative scene sized based on the screen resolution
    private void SetDesiredSceneSized() {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        desiredSceneHeight = primaryScreenBounds.getHeight() * 0.6;
        desiredSceneWidth = primaryScreenBounds.getWidth() * 0.6;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
