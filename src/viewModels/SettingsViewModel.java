package viewModels;


import model.Settings;
import services.FilesLoaderService;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

public class SettingsViewModel extends Observable implements Observer {

    private final String resources = "resources";
    private final String serverSettingsFile = "serverSettings";
    private Settings currentSettings;
    private final String delimiter = ":";

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
        String saveStr = String.join(delimiter, ip, port.toString());

        // Saving the settings object into a local file
        File selectedFile = new File(this.resources, serverSettingsFile);
        boolean result = FilesLoaderService.saveObjectToFile(saveStr, selectedFile);
        System.out.println("Result for saving ip and port: " + result);

    }

    public void loadSettings() {
        currentSettings = new Settings();
        File chosen = new File(this.resources, serverSettingsFile);
        String serverSettings = FilesLoaderService.loadFileToObject(chosen);
        if (serverSettings != null) {
            // loading settings from local file
            String[] str = serverSettings.split(delimiter);
            String ip = str[0];
            String port = str[1];
            currentSettings.setIp(ip);
            currentSettings.setPort(Integer.valueOf((port)));
        } else {
            // No local file - set default values
            currentSettings.setDefaultValues();
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
