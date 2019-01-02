package viewModels;


import model.Settings;
import services.CommonService;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

public class SettingsViewModel extends Observable implements Observer {

    private Settings currentSettings;

    public Settings getCurrentSettings() {
        return currentSettings;
    }

    public SettingsViewModel() {
        loadSettings();
        notifyObservers();
    }

    public void saveSettings(String ip, Integer port) {
        currentSettings.setPort(port);
        currentSettings.setIp(ip);
        // Saving the settings object into a local file
        CommonService.saveSettings(currentSettings);
    }

    public void loadSettings() {
        currentSettings = CommonService.loadSettings();
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
