package View.MainWindow;
import Services.ThemeManager;
import Services.ThemeManagerService;
import ViewModels.MainWindowViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.util.Observer;
import java.util.ResourceBundle;


public class MainWindowController implements Observer,  Initializable {

    MainWindowViewModel vm;
    ThemeManagerService themeManager;

    private StringProperty theme;

    // DI for poor
    public MainWindowController(ThemeManagerService themeManagerService, MainWindowViewModel mainWindowViewModel) {

        // Set the themeManager service and bind the theme type to it
        this.themeManager = themeManagerService;
        this.themeManager.addObserver(this);

        // Set the vm and bind the logged in user to the vm
        this.vm = mainWindowViewModel;
        this.vm.addObserver(this);

    }

    @FXML protected TextField userName;

    @FXML protected Button themeToggle;

    @FXML protected void handleToggleAction(ActionEvent t) {
        if (theme.getValue().equals("Dark")) {
            theme.setValue("Bright");
        }
        else {
            theme.setValue("Dark");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void start() {
        System.out.println("Starting our pipe game");
    }

    @FXML
    private void showSettings() {
    }

    @FXML
    private void showTop10() {

    }

    @FXML
    private void showTimer() {
    }

    @FXML
    private void showStepper() {
    }

    @FXML
    private void loadLevel() {
//        TODO: Move the logic into the board logic or a service maybe?
//        FileChooser fc = new FileChooser();
//        fc.setTitle("Open Saved Pipes Board");
//        fc.setInitialDirectory(new File("./resources"));
//        // Set extension filter
////        FileChooser.ExtensionFilter extentionFilter = new FileChooser.ExtensionFilter("XML Files", "*.xml");
////        fc.getExtensionFilters().add(extentionFilter);
//        File chosen = fc.showOpenDialog(null);
//        if (chosen != null) {
//            System.out.println(chosen.getName());
//            ObjectInputStream objInput = null;
//            try {
//                objInput = new ObjectInputStream(new FileInputStream(chosen));
//                PipeBoardModel savedBoard = (PipeBoardModel) objInput.readObject();
//                boardDisplayer.setBoardData(savedBoard.getBoard());
//
//            } catch (IOException | ClassNotFoundException e) {
//                e.printStackTrace();
//            }
////            System.out.println(String.join(" ", "Loaded solution:\r\n", solution.toString()));
//        }
    }

    @FXML
    private void saveLevel() {
//        TODO: Move this logic to the
//        File selectedFile = SelectFile();
//        if (selectedFile != null && !selectedFile.exists()) {
//                try {
//                    boolean created = selectedFile.createNewFile();
//                    if (created) {
//                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
//                            new BufferedOutputStream(
//                                    new FileOutputStream(selectedFile)
//                            ));
//                    objectOutputStream.writeObject(vm.currentBoard);
//                    objectOutputStream.close();
//                }}
//                catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
        }

    @Override
    public void update (java.util.Observable o, Object arg){
        if (o == this.themeManager) {
            theme = new SimpleStringProperty();
            theme.bindBidirectional(this.themeManager.themeName);
            themeToggle.textProperty().bindBidirectional(theme);

        }
        if (o == this.vm){
            this.vm.userName.bindBidirectional(userName.textProperty());
        }

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


