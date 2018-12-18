import Services.ThemeManagerService;
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
        //Size
        SetDesiredSceneSized();

        // Load resource and inject the themes service
        ThemeManagerService service = new ThemeManagerService();
        URL resource = getClass().getResource("View/MainWindow/MainWindow.fxml");

        FXMLLoader fxmlLoader = new FXMLLoader(resource);

        DIHelper.injectServiceAndVM(fxmlLoader, "MainWindowViewModel", service);
        BorderPane root = fxmlLoader.load();
        Scene scene = new Scene(root, desiredSceneWidth, desiredSceneHeight, Color.TRANSPARENT);

        stage.setScene(scene);
        stage.toFront();
        stage.setMaximized(true);

        service.initializeThemeManager(stage);
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
