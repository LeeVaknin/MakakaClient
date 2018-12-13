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

    int[][] mazeData = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1}, {1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1}, {1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 1},
            {1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1}};
    @FXML
    public BoardDisplayerControl boardDisplayer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        boardDisplayer.setBoardData(mazeData);
//        boardDisplayer.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> boardDisplayer.requestFocus());
//
//        boardDisplayer.setOnKeyPressed(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                int r = boardDisplayer.getcRow();
//                int c = boardDisplayer.getcCol();
//
//                if (event.getCode() == KeyCode.UP) {
//                    boardDisplayer.setCharecterPosition(r - 1, c);
//                }
//                if (event.getCode() == KeyCode.DOWN) {
//                    boardDisplayer.setCharecterPosition(r + 1, c);
//                }
//                if (event.getCode() == KeyCode.LEFT) {
//                    boardDisplayer.setCharecterPosition(r, c - 1);
//                }
//                if (event.getCode() == KeyCode.RIGHT) {
//                    boardDisplayer.setCharecterPosition(r, c + 1);
//                }
//            }
//        });
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


