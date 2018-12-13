package Model;

import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;

import java.util.HashMap;
import java.util.Observable;

import static javafx.scene.media.AudioClip.INDEFINITE;


public class PipeGameTheme extends Observable implements ThemeModel {

    // C-TOR
    public PipeGameTheme() {
        this.loadBackgroundMusic();
        this.loadGameChildImages();
        //this.loadStyleSheet();
    }

    // Variables
    private String backgroundMusicPath;
    private String styleSheetPath;
    private String startPipeImage;
    private String goalPipeImage;
    private String verticalPipeImage;
    private String cornerPipeImage;


    // Methods
    @Override
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

    @Override
    public void loadGameChildImages() {
        startPipeImage = "./resources/startPipe.png";
        goalPipeImage = "./resources/goalPipe.png";
        verticalPipeImage = "./resources/verticalPipe.png";
        cornerPipeImage = "./resources/cornerPipe.png";
    }

    @Override
    public String getImagePath(ImageType imageType) {
        switch (imageType) {
            case START:
                return startPipeImage;
            case GOAL:
                return goalPipeImage;
            case VERTICAL:
                return verticalPipeImage;
            case CORNER:
                return cornerPipeImage;
        }
        return null;
    }

}
