package Model;

import javafx.concurrent.Task;
import javafx.scene.Scene;

import java.util.HashMap;

public interface ThemeModel {

    Task loadBackgroundMusic();

    void loadStyleSheet(Scene scene);

    void loadGameChildImages();

    String getImagePath(ImageType imageType);
}
