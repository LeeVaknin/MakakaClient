import View.Controls.BoardDisplayerControl;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    char[][] boardData = {
            {'s','7','-'},
            {'|','|','F'},
            {'L','7','|'},
            {'-','|','-'},
            {'F','J','g'},
    };
    @FXML
    public BoardDisplayerControl boardDisplayer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        boardDisplayer.setBoardData(boardData);
        boardDisplayer.setOnMouseClicked(event -> {



        });

    }

    public void start() {
        System.out.println("Start");
    }

    @FXML
    private void showSettings() throws IOException {

    }
    @FXML
    private void showTop10() throws IOException {

    }
    @FXML
    private void showThemeDialog() throws IOException {

    }
    @FXML
    private void showTimer() throws IOException {

    }

}


