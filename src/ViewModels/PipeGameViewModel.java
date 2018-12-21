package ViewModels;
import Model.PipeBoardModel;
import Services.FilesLoaderService;
import Utils.FileChooserHelper;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

public class PipeGameViewModel extends Observable implements Observer {

    // GameBoard
    public PipeBoardModel currentBoard;
    private FilesLoaderService<PipeBoardModel> filesLoaderService;

    // C-TOR
    public PipeGameViewModel(FilesLoaderService<PipeBoardModel> flservice) {
        this.currentBoard = new PipeBoardModel();
        char[][] boardData = {
                {'s','7','-', 'L'},
                {'|','|','F', '|'},
                {'L','7',' ', '-'},
                {'-','|','-', '|'},
                {'F','J',' ', 'g'},
        };
        this.currentBoard.setBoard(boardData);
        this.currentBoard.addObserver(this);

        // Listen to the requests to save/load games
        this.filesLoaderService = flservice;
        this.filesLoaderService.isGameLoadRequested.addListener( event -> {
            if (this.filesLoaderService.isGameLoadRequested.getValue()) {
                this.filesLoaderService.isGameLoadRequested.setValue(false);
                this.loadLevelFromDisk();
            }
        });

        filesLoaderService.isGameSaveRequested.addListener(event -> {
            if (filesLoaderService.isGameSaveRequested.getValue()) {
                filesLoaderService.isGameSaveRequested.setValue(false);
                this.saveCurrentLevel();
            }
        });
    }

    // Observer Implementation
    @Override
    public void update(Observable o, Object arg) {
        if (o == this.currentBoard) {
            System.out.println("The board has changed and the Pipe game view model knows about it");
            setChanged();
            notifyObservers();
        }
    }

    // Save and load levels
    private void saveCurrentLevel() {
        File selectedFile = FileChooserHelper.selectFileToSave();
        if (selectedFile != null && !selectedFile.exists()) {
            boolean result =  this.filesLoaderService.saveObjectToFile(currentBoard, selectedFile);
            System.out.println("Result for saving stage: " + result);
        }
    }

    private void loadLevelFromDisk() {
        File chosen = FileChooserHelper.selectFileToLoad();
        if (chosen != null) {
            System.out.println(chosen.getName());
            PipeBoardModel boardModel = this.filesLoaderService.loadFileToObject(chosen);
            if (boardModel != null) {
                this.currentBoard.setBoard(boardModel.getBoard());
            }
        }
    }
}


