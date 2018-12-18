package View.PipeGame;
import Services.ThemeManagerService;
import ViewModels.PipeGameViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class PipeGameController extends Observable implements Initializable, Observer {

    // attributes
    PipeGameViewModel vm;
    ThemeManagerService themeManager;

    @FXML
    public BoardDisplayer boardDisplayer;

    @FXML
    public AnchorPane rootPane;

    // C-TOR
    public PipeGameController(ThemeManagerService themeManager, PipeGameViewModel vm) {

        this.vm = vm;
        this.vm.addObserver(this);

        this.themeManager = themeManager;
        addObserver(this.themeManager);
    }

    // Implementations

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (boardDisplayer != null) {
            boardDisplayer.widthProperty().bind(rootPane.widthProperty());
            boardDisplayer.heightProperty().bind(rootPane.heightProperty());
            boardDisplayer.setBoardData(vm.currentBoard.getBoard());
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
