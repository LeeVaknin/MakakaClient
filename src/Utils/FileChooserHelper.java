package Utils;

import javafx.stage.FileChooser;

import java.io.File;

public class FileChooserHelper {

    private static FileChooser fileChooser = new FileChooser();

    public static File selectFileToSave() {
        fileChooser.setTitle("Save Level");
       return fileChooser.showSaveDialog(null);
    }

    public static File selectFileToLoad() {
        fileChooser.setTitle("Open Your Saved Games");
        fileChooser.setInitialDirectory(new File("./resources"));
        return fileChooser.showOpenDialog(null);
    }
}
