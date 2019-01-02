package viewModels;
import javafx.beans.property.StringProperty;

import java.util.Observable;
import java.util.Observer;

public class MainWindowViewModel extends Observable implements Observer {

    // User
    public StringProperty userName;

    public MainWindowViewModel() {
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
