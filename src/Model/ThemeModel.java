package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.scene.media.AudioClip;
import java.util.ArrayList;
import java.util.Observable;
import static javafx.scene.media.AudioClip.INDEFINITE;


public class ThemeModel extends Observable {

    public final static String RESOURCES = "./resources/";
    public final static String STYLESHEETBASEPATH = "Styles/";
    public final static ArrayList<String> THEMES = new ArrayList<String>() {{
        add("Bright");
        add("Dark");
    }};

    private javafx.stage.Stage stage;

    // C-TOR
    public ThemeModel(javafx.stage.Stage stage) {
        this.stage = stage;
        this.selectedTheme = new SimpleStringProperty();
        // Every time the theme changes, do the following
        selectedTheme.addListener(event -> {
            this.setTheme();
        });
        SwitchTheme("Dark");
    }

    // Variables
    private String backgroundMusicPath;
    private String styleSheetPath;
    private String fullResoucesPath;
    private String startPipeImage;
    private String goalPipeImage;
    private String verticalPipeImage;
    private String horizontalPipeImage;
    private String cornerLPipeImage;
    private String cornerFPipeImage;
    private String cornerJPipeImage;
    private String corner7PipeImage;
    public StringProperty selectedTheme;

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
        this.styleSheetPath = STYLESHEETBASEPATH + selectedTheme.getValue();
        if (this.stage.getScene().getStylesheets().size() > 0) this.stage.getScene().getStylesheets().clear();
        this.stage.getScene().getStylesheets().add(this.styleSheetPath + "/menuBar.css");
        this.stage.getScene().getStylesheets().add(this.styleSheetPath + "/mainStyles.css");
        this.stage.getScene().getStylesheets().add(this.styleSheetPath + "/textField.css");
        this.stage.getScene().getStylesheets().add(this.styleSheetPath + "/button.css");
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
        fullResoucesPath = RESOURCES + this.selectedTheme.getValue() + "/";
        switch (imageType) {
            case START:
                return  fullResoucesPath + startPipeImage;
            case GOAL:
                return fullResoucesPath +goalPipeImage;
            case VERTICAL:
                return fullResoucesPath + verticalPipeImage;
            case HORIZONTAL:
                return fullResoucesPath + horizontalPipeImage;
            case CORNERL:
                return fullResoucesPath + cornerLPipeImage;
            case CORNERF:
                return fullResoucesPath + cornerFPipeImage;
            case CORNERJ:
                return fullResoucesPath + cornerJPipeImage;
            case CORNER7:
                return fullResoucesPath + corner7PipeImage;
        }

        return null;
    }
    
    public void SwitchTheme(String newThemeName){
        if (THEMES.contains(newThemeName)) {
            selectedTheme.setValue(newThemeName);
        }
    }

    public void setTheme() {
        if (this.stage.getScene() != null) {
            this.loadStyleSheet();
            this.loadBackgroundMusic();
            this.loadGameChildImages();
        }
    }
}
