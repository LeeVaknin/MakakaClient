package View;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class MainWindowController implements Initializable {

    private char[][] boardData = {
            {'s','7','-', 'L'},
            {'|','|','F', '|'},
            {'L','7',' ', '-'},
            {'-','|','-', '|'},
            {'F','J',' ', 'g'},
    };
    @FXML
    public BoardDisplayer boardDisplayer;

    @FXML
    public StackPane boardStackPane;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (boardDisplayer != null) {
            boardDisplayer.widthProperty().bind(boardStackPane.widthProperty());
            boardDisplayer.heightProperty().bind(boardStackPane.heightProperty());
            boardDisplayer.setBoardData(boardData);
        }
    }

    public void start() {
        System.out.println("Start");
    }

    @FXML
    private void showSettings() {

    }
    @FXML
    private void showTop10()  {

    }
    @FXML
    private void showThemeDialog() {

    }
    @FXML
    private void showTimer() {

    }
    @FXML
    private void showStepper() {

    }
    @FXML
    private void loadLevel() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open Saved Pipes Board");
        fc.setInitialDirectory(new File("./resources"));
        // Set extension filter
        FileChooser.ExtensionFilter extentionFilter = new FileChooser.ExtensionFilter("XML Files", "*.xml");
        fc.getExtensionFilters().add(extentionFilter);
        File choosen = fc.showOpenDialog(null);
        if (choosen != null) {
            System.out.println(choosen.getName());
        }
    }

    @FXML
    private void saveLevel()  {

    }


}


