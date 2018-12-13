package Model;

import javafx.concurrent.Task;
import javafx.scene.Scene;

public interface ThemeModel {

    Task loadBackgroundMusic();

    void loadStyleSheet(Scene scene);
}
