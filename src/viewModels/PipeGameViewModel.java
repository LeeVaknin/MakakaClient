package viewModels;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Alert;
import model.PipeBoardModel;
import model.PipeGameSolution;
import services.CommonService;
import services.PipeGameSolverService;
import utils.FileChooserHelper;

import java.awt.*;
import java.io.File;
import java.time.Duration;
import java.util.Observable;
import java.util.Observer;

public class PipeGameViewModel extends Observable implements Observer {

    // GameBoard
    public PipeBoardModel currentBoard;
    private CommonService commonService;
    public IntegerProperty stepsCounterProperty;

    // C-TOR
    public PipeGameViewModel(CommonService flservice) {

        this.stepsCounterProperty = new SimpleIntegerProperty();

        this.initBoard();
        // Listen to the requests to save/load games
        this.commonService = flservice;
        this.commonService.isGameLoadRequested.addListener(event -> {
            if (this.commonService.isGameLoadRequested.getValue()) {
                this.commonService.isGameLoadRequested.setValue(false);
                this.loadLevelFromDisk();
            }
        });

        commonService.isGameSaveRequested.addListener(event -> {
            if (commonService.isGameSaveRequested.getValue()) {
                commonService.isGameSaveRequested.setValue(false);
                this.saveCurrentLevel();
            }
        });

        commonService.isNewLevelRequested.addListener(event -> {
            if (commonService.isNewLevelRequested.getValue()) {
                commonService.isNewLevelRequested.setValue(false);
                commonService.isFailedLoadingNewLevel.setValue(this.loadNewLevel());
            }
        });
    }

    private void initBoard() {
        PipeBoardModel theBoard = new PipeBoardModel();
        char[][] boardData = {
                {'s','7','-', 'L'},
                {'|','|','F', '|'},
                {'L','7',' ', '-'},
                {'-','|','-', '|'},
                {'F','J',' ', 'g'},
        };


        theBoard.setBoard(boardData);
        this.setCurrentBoard(theBoard);
    }

    // Observer Implementation
    @Override
    public void update(Observable o, Object arg) {
        if (o == this.currentBoard) {
            if (arg != null) {
              this.stepsCounterProperty.setValue(this.currentBoard.getStepsCounter());
            }
            setChanged();
            notifyObservers();
        }
    }

    public void setCurrentBoard(PipeBoardModel currentBoard) {
        if (this.currentBoard != null && this.currentBoard.countObservers() > 0)
            this.currentBoard.deleteObservers();
        this.currentBoard = currentBoard;
        this.currentBoard.addObserver(this);
        this.stepsCounterProperty.setValue(currentBoard.getStepsCounter());
        this.setChanged();
        this.notifyObservers(currentBoard);
    }

    // Save and load levels
    private void saveCurrentLevel() {
        File selectedFile = FileChooserHelper.selectFileToSave();
        if (selectedFile != null && !selectedFile.exists()) {
            boolean result =  CommonService.saveObjectToFile(currentBoard, selectedFile);
            System.out.println("Result for saving stage: " + result);
        }
    }

    private void loadLevelFromDisk() {
        File chosen = FileChooserHelper.selectFileToLoad();
        if (chosen != null) {
            System.out.println(chosen.getName());
            PipeBoardModel boardModel = CommonService.loadFileToObject(chosen);
            if (boardModel != null) {
                this.setCurrentBoard(boardModel);
            }
        }
    }

    // -1 - failed loading board
    // 0 - canceled loading board
    // 1 - succeeded
    private int loadNewLevel() {
        File chosen = FileChooserHelper.selectStringLevelToLoad();
        if (chosen != null) {
            System.out.println(chosen.getName());
            String strBoard = CommonService.loadFileToString(chosen);
            if (strBoard != null && !strBoard.isEmpty()) {
                PipeBoardModel newLevel = new PipeBoardModel();
                newLevel.setBoard(strBoard);
                if (newLevel.getBoard() == null
                        || newLevel.getBoard().length <= 0
                        || newLevel.getBoard()[0].length <= 0 ) {
                    return -1;
                }
                this.setCurrentBoard(newLevel);
                return 1;
            }
            return -1;
        }
        return 0;
    }

    // Solutions logic

    public PipeGameSolution solve() {
        String strBoard = this.currentBoard.toString();
        return PipeGameSolverService.solveBoard(strBoard);
    }

    public int submit() {
        String strBoard = this.currentBoard.toString();
        Point goalPos = this.currentBoard.findGoalPosition();
        return PipeGameSolverService.submitBoard(strBoard, goalPos);

    }
}


