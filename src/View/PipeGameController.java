package View;

import Services.ThemeManagerService;
import ViewModels.PipeGameViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class PipeGameController extends Observable implements Initializable, Observer {

    // Variables
    private PipeGameViewModel vm;
    private ThemeManagerService themeManager;

    @FXML
    public BoardDisplayer boardDisplayer;

    @FXML
    public StackPane stackPane;

    // C-TOR
    public PipeGameController(ThemeManagerService themeManager, PipeGameViewModel vm) {

        this.vm = vm;
        this.vm.addObserver(this);

        this.themeManager = themeManager;
        this.themeManager.addObserver(this);
    }

    // Implementations
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (boardDisplayer != null) {
            boardDisplayer.widthProperty().bind(stackPane.widthProperty());
            boardDisplayer.heightProperty().bind(stackPane.heightProperty());
            boardDisplayer.setBoard(vm.currentBoard);
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
}
