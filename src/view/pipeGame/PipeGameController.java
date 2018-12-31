package view.pipeGame;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import javafx.stage.Window;
import model.PipeGameSolution;
import services.ThemeManagerService;
import viewModels.PipeGameViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;


public class PipeGameController extends Observable implements Initializable, Observer {

    // Variables
    private PipeGameViewModel vm;
    private ThemeManagerService themeManager;

    // FXML Variables
    @FXML public BoardDisplayer boardDisplayer;
    @FXML public StackPane stackPane;


   /* @FXML public Text timer;
    private SimpleDateFormat sdf = new SimpleDateFormat("mm:ss:S");
    private String[] split;
    private SimpleStringProperty sspTime;
    private long time;
    private Timer t = new Timer("Metronome", true);
    private TimerTask tt;
    boolean timing = false;
*/

    // C-TOR
    public PipeGameController(ThemeManagerService themeManager, PipeGameViewModel vm) {

        this.vm = vm;
        this.vm.addObserver(this);

        this.themeManager = themeManager;
        this.themeManager.addObserver(this);
    }

    // Overrides
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (boardDisplayer != null) {
            boardDisplayer.widthProperty().bind(stackPane.widthProperty());
            boardDisplayer.heightProperty().bind(stackPane.heightProperty());
            boardDisplayer.setBoard(vm.currentBoard);
            // timer.textProperty().bindBidirectional(this.vm.currentBoard.getGameTimer());
        }
    }


    @Override
    public void update(Observable o, Object arg) {
        if (o == this.themeManager) {
            boardDisplayer.setThemeModel(this.themeManager.getTheme());
        }
        if (o == this.vm) {
            boardDisplayer.setBoard(vm.currentBoard);
        }

    }

    // FXMl methods

    @FXML protected void solveHandle() {
        PipeGameSolution solution = this.vm.solve();
        this.boardDisplayer.solve(solution);
    }

    @FXML protected void doneHandle() {
        if (this.vm.submit()) {
            showAlert(Alert.AlertType.ERROR, stackPane.getScene().getWindow(),
                               "OH NO!", "Don't be so hasty... Try again");
        } else {
            showAlert(Alert.AlertType.INFORMATION, stackPane.getScene().getWindow(),
                    "You made it!", "Your answer is correct. Good job!");
        }
    }

    public void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

}
