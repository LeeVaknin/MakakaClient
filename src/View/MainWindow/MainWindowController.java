package View.MainWindow;
import Model.PipeBoardModel;
import View.PipeGame.BoardDisplayer;
import ViewModels.PipeGameViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.util.Observer;
import java.util.ResourceBundle;


public class MainWindowController implements Observer, Initializable {

    PipeGameViewModel vm;

    @FXML
    public BoardDisplayer boardDisplayer;

    @FXML
    public StackPane boardStackPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (boardDisplayer != null) {
            boardDisplayer.widthProperty().bind(boardStackPane.widthProperty());
            boardDisplayer.heightProperty().bind(boardStackPane.heightProperty());
            boardDisplayer.setBoardData(vm.currentBoard.getBoard());
        }
    }

    public void start() {
        System.out.println("Start");
    }

    @FXML
    private void showSettings() {
    }

    @FXML
    private void showTop10() {

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
//        FileChooser.ExtensionFilter extentionFilter = new FileChooser.ExtensionFilter("XML Files", "*.xml");
//        fc.getExtensionFilters().add(extentionFilter);
        File chosen = fc.showOpenDialog(null);
        if (chosen != null) {
            System.out.println(chosen.getName());
            ObjectInputStream objInput = null;
            try {
                objInput = new ObjectInputStream(new FileInputStream(chosen));
                PipeBoardModel savedBoard = (PipeBoardModel) objInput.readObject();
                boardDisplayer.setBoardData(savedBoard.getBoard());

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
//            System.out.println(String.join(" ", "Loaded solution:\r\n", solution.toString()));
        }
    }

    @FXML
    private void saveLevel() {
        File selectedFile = SelectFile();
        if (selectedFile != null && !selectedFile.exists()) {
                try {
                    boolean created = selectedFile.createNewFile();
                    if (created) {
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                            new BufferedOutputStream(
                                    new FileOutputStream(selectedFile)
                            ));
                    objectOutputStream.writeObject(vm.currentBoard);
                    objectOutputStream.close();
                }}
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    @Override
    public void update (java.util.Observable o, Object arg){

    }

    private File SelectFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Level");
        File selectedFile = null;
        while (selectedFile == null) {
            selectedFile = fileChooser.showSaveDialog(null);
        }
        return selectedFile;
    }

}


