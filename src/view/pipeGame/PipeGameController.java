package view.pipeGame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Window;
import javafx.util.Duration;
import model.PipeGameSolution;
import services.CommonService;
import services.ThemeManagerService;
import viewModels.PipeGameViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import java.net.URL;
import java.util.*;


public class PipeGameController extends Observable implements Initializable, Observer {

    // Variables
    private PipeGameViewModel vm;
    private ThemeManagerService themeManager;
    private Timeline timeline;
    private CommonService commonService;

    // FXML Variables
    @FXML public Text stepsCounter;
    @FXML public BoardDisplayer boardDisplayer;
    @FXML public StackPane stackPane;
    @FXML public Text stopWatch;
    @FXML public GridPane pipeGameGrid;

    // C-TOR
    public PipeGameController(ThemeManagerService themeManager, CommonService commonService, PipeGameViewModel vm) {

        this.vm = vm;
        this.vm.addObserver(this);

        this.themeManager = themeManager;
        this.themeManager.addObserver(this);

        this.commonService = commonService;

    }

    // Overrides
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (boardDisplayer != null) {
            if (this.themeManager.getTheme() != null )
                boardDisplayer.setThemeModel(this.themeManager.getTheme());
            if (this.vm.currentBoard != null)
                boardDisplayer.setBoard(vm.currentBoard);
            boardDisplayer.widthProperty().bind(stackPane.widthProperty());
            boardDisplayer.heightProperty().bind(stackPane.heightProperty());
            boardDisplayer.setBoard(vm.currentBoard);

            stepsCounter.textProperty().bind(this.vm.stepsCounterProperty.asString());

            // timer.textProperty().bindBidirectional(this.vm.currentBoard.getGameTimer());
        }
        this.updateText(this.stopWatch);
        this.initStopwatch();


        commonService.isFailedLoadingNewLevel.addListener(event -> {
            if (commonService.isFailedLoadingNewLevel.getValue() == -1) {
                CommonService.showAlert(Alert.AlertType.ERROR, pipeGameGrid.getScene().getWindow(),
                        "Failed Loading new Level", "Something went wrong during board parsing.\nPlease validate your board format again.");
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == this.themeManager) {
            boardDisplayer.setThemeModel(this.themeManager.getTheme());
        }
        if (o == this.vm) {
            boardDisplayer.setBoard(vm.currentBoard);
            this.initStopwatch();
            stepsCounter.textProperty().bind(this.vm.stepsCounterProperty.asString());
        }
    }

    // FXMl methods

    @FXML protected void solveHandle() {
        this.timeline.stop();
        PipeGameSolution solution = this.vm.solve();
        if (solution == null || solution.getSteps().size() == 0) {
            CommonService.showAlert(Alert.AlertType.ERROR, stackPane.getScene().getWindow(),
                    "Something Went Wrong", "We couldn't reach the server." );
            return;
        }
        this.boardDisplayer.solve(solution);
    }

    @FXML protected void doneHandle() {
        this.timeline.stop();
        if (this.vm.submit() == 1) {
            CommonService.showAlert(Alert.AlertType.INFORMATION, stackPane.getScene().getWindow(),
                    "You made it!", "Your answer is correct. Good job!");
        } else if (this.vm.submit() == 0) {
            CommonService.showAlert(Alert.AlertType.ERROR, stackPane.getScene().getWindow(),
                               "OH NO!", "Don't be so hasty... Try again");
        } else
            CommonService.showAlert(Alert.AlertType.ERROR, stackPane.getScene().getWindow(),
                    "Something Went Wrong", "We couldn't reach the server." );
    }


    // stopwatch

    private void initStopwatch() {
        if (this.timeline != null) {
            this.timeline.stop();
            this.timeline = null;
        }
        this.timeline = new Timeline(new KeyFrame(Duration.millis(1), event -> changeTime(stopWatch)));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
        this.timeline.setAutoReverse(false);
        this.timeline.play();
    }

    private void changeTime(Text text) {
        // updates the text of the timer
        if(this.vm.currentBoard.millis == 1000) {
            this.vm.currentBoard.secs++;
            this.vm.currentBoard.millis = 0;
        }
        if(this.vm.currentBoard.secs == 60) {
            this.vm.currentBoard.mins++;
            this.vm.currentBoard.secs = 0;
        }
        this.vm.currentBoard.millis++;
        this.updateText(text);
    }

    private void updateText(Text text) {
        int mins = this.vm.currentBoard.mins;
        int secs = this.vm.currentBoard.secs;

        text.setText((((mins/10) == 0) ? "0" : "") + mins + ":"
                + (((secs/10) == 0) ? "0" : "") + secs);
    }

}
