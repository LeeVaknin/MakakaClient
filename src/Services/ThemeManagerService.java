package Services;
import Model.ThemeModel;
import javafx.stage.Stage;
import java.util.Observable;
import java.util.Observer;


public class ThemeManagerService extends Observable implements ThemeManager, Observer {

    // TODO: Turn this service into a singleTon
    private ThemeModel currentTheme;
    private Stage currentstage;

    public ThemeManagerService() {
    }

    public void initializeThemeManager(Stage stage) {
        this.currentstage = stage;
        this.currentTheme = new ThemeModel(stage);
    }

    public void ChangeTheme(String newTheme) {
        this.currentTheme.SwitchTheme(newTheme);
        setChanged();
        notifyObservers();
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
