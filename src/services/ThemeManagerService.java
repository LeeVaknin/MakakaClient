package services;
import model.ThemeModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.stage.Stage;
import java.util.Observable;
import java.util.Observer;


public class ThemeManagerService extends Observable implements Observer {

    // TODO: Turn this service into a singleTon
    private ThemeModel currentTheme;
    public StringProperty themeName;

    public void initializeThemeManager(Stage stage) {
        this.currentTheme = new ThemeModel(stage);
        themeName = new SimpleStringProperty();
        themeName.bindBidirectional(this.currentTheme.selectedTheme);
        this.currentTheme.addObserver(this);
        setChanged();
        notifyObservers();
    }

    public ThemeModel getTheme() {
        return this.currentTheme;
    }

    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers();
    }
}
