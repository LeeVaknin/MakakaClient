package Services;

import Model.ThemeModel;
import javafx.stage.Stage;

import java.util.Observable;

public class ThemeManagerService extends Observable implements ThemeManager {

    // TODO: Turn this service into a singleTon
    private ThemeModel currentTheme;
    private Stage currentstage;

    public ThemeManagerService(Stage stage) {
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
