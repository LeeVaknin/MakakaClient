package View.PipeGame;
import Services.ThemeManager;
import ViewModels.PipeGameViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class PipeGameController extends Observable implements Initializable {

    // attributes
    PipeGameViewModel vm;
    ThemeManager themeManager;

    @FXML
    public BoardDisplayer boardDisplayer;

    @FXML
    public AnchorPane rootPane;

    // C-TOR
    public PipeGameController(ThemeManager themeManager, PipeGameViewModel vm) {
        this.vm = vm;
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
}
