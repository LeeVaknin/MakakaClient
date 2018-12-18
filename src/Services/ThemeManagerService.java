package Services;

import View.Themes.ThemesController;

import java.util.Observable;
import java.util.Observer;

public class ThemeManagerService extends Observable implements ThemeManager, Observer {
    @Override
    public void update(Observable o, Object arg) {

    }
}
