package Model;

import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;

import java.util.Observable;

import static javafx.scene.media.AudioClip.INDEFINITE;


public class PipeGameTheme extends Observable implements ThemeModel {

    private String backgroundMusicPath;
    private String styleSheetPath;
    private String gameChildImagePath;

    public String getBackgroundMusicPath() {
        return backgroundMusicPath;
    }

    public void setBackgroundMusicPath(String backgroundMusicPath) {
        this.backgroundMusicPath = backgroundMusicPath;
    }

    public Task loadBackgroundMusic() {
        Task task = new Task() {

            protected Object call() throws Exception {
                int s = INDEFINITE;
                AudioClip audio = new AudioClip(getClass().getResource(backgroundMusicPath).toExternalForm());
                audio.setVolume(0.5f);
                audio.setCycleCount(s);
                audio.play();
                setChanged();
                notifyObservers();
                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.start();
        return task;
    }

    @Override
    public void loadStyleSheet(Scene scene) {
        scene.getStylesheets().add(styleSheetPath);
        setChanged();
        notifyObservers();
    }

    public String getStyleSheetPath() {
        return styleSheetPath;
    }

    public void setStyleSheetPath(String styleSheetPath) {
        this.styleSheetPath = styleSheetPath;
    }

    public String getGameChildImagePath() {
        return gameChildImagePath;
    }

    public void setGameChildImagePath(String gameChildImagePath) {
        this.gameChildImagePath = gameChildImagePath;
    }

//    public String getGameChildComponentImage() {
//        return gameChildComponentImage.get();
//    }
//
//    public void setGameChildComponentImage(String gameChildComponentImage) {
//        this.gameChildComponentImage.set(gameChildComponentImage);
//    }
}
