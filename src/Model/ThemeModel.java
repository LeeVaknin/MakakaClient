package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.scene.media.AudioClip;
import java.util.ArrayList;
import java.util.Observable;
import static javafx.scene.media.AudioClip.INDEFINITE;


public class ThemeModel extends Observable {

    public final static String STYLESHEETBASEPATH = "Styles/";
    public final static ArrayList<String> THEMES = new ArrayList<String>() {{
        add("Bright");
        add("Dark");
    }};
    private javafx.stage.Stage stage;

    // C-TOR
    public ThemeModel(javafx.stage.Stage stage) {
//        selectedTheme.addListener(event => {});
        this.stage = stage;
        this.selectedTheme = new SimpleStringProperty();
        // Every time the theme changes, do the following
        selectedTheme.addListener(event -> {
            this.setTheme();
        });
        SwitchTheme("DARK");

    }

    // Variables
    private String backgroundMusicPath;
    private String styleSheetPath;
    private String startPipeImage;
    private String goalPipeImage;
    private String verticalPipeImage;
    private String horizontalPipeImage;
    private String cornerLPipeImage;
    private String cornerFPipeImage;
    private String cornerJPipeImage;
    private String corner7PipeImage;
    private StringProperty selectedTheme;

    // Methods
    public Task loadBackgroundMusic() {
        Task task = new Task() {

            protected Object call() {
                AudioClip audio = new AudioClip(getClass().getResource(backgroundMusicPath).toExternalForm());
                audio.setVolume(0.5f);
                audio.setCycleCount(INDEFINITE);
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

    public void loadStyleSheet() {
        this.styleSheetPath = STYLESHEETBASEPATH + selectedTheme;
        this.stage.getScene().getStylesheets().add(this.styleSheetPath + "/menuBar.css");
        this.stage.getScene().getStylesheets().add(this.styleSheetPath + "/mainStyles.css");
        setChanged();
        notifyObservers();
    }

    private void loadGameChildImages() {
        startPipeImage = "startPipe.png";
        goalPipeImage = "goalPipe.png";
        verticalPipeImage = "verticalPipe.png";
        horizontalPipeImage = "horizontalPipe.png";
        cornerLPipeImage = "LPipe.png";
        cornerFPipeImage = "FPipe.png";
        cornerJPipeImage = "JPipe.png";
        corner7PipeImage = "7Pipe.png";
    }

    public String getImagePath(ImageType imageType) {
        switch (imageType) {
            case START:
                return  startPipeImage;
            case GOAL:
                return goalPipeImage;
            case VERTICAL:
                return verticalPipeImage;
            case HORIZONTAL:
                return horizontalPipeImage;
            case CORNERL:
                return cornerLPipeImage;
            case CORNERF:
                return cornerFPipeImage;
            case CORNERJ:
                return cornerJPipeImage;
            case CORNER7:
                return corner7PipeImage;
        }

        return null;
    }
    
    public void SwitchTheme(String newThemeName){
        if (THEMES.contains(newThemeName)) {
            selectedTheme.setValue(newThemeName);
            setChanged();
            notifyObservers(newThemeName);
        }
    }

    public void setTheme() {
        if (this.stage.getScene() != null) {
            this.loadBackgroundMusic();
            this.loadGameChildImages();
            this.loadStyleSheet();
        }
    }
}
