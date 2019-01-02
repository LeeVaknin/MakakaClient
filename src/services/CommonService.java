package services;

import javafx.beans.property.*;
import javafx.scene.control.Alert;
import javafx.stage.Window;
import model.Settings;

import java.io.*;
import java.util.Scanner;


public class CommonService {

    public final String GUEST = "Guest";
    private static String USERPATH = "./resources/settings/user";
    private static String SETTINGSPATH = "./resources/settings/settings";

    public BooleanProperty isGameSaveRequested;
    public BooleanProperty isGameLoadRequested;
    public BooleanProperty isNewLevelRequested;
    public IntegerProperty isFailedLoadingNewLevel;
    public StringProperty loggedInUser;

    private static boolean created = false;

    public CommonService() {
        isGameSaveRequested = new SimpleBooleanProperty(false);
        isGameLoadRequested = new SimpleBooleanProperty(false);
        isNewLevelRequested = new SimpleBooleanProperty(false);
        isFailedLoadingNewLevel = new SimpleIntegerProperty(0);

        String username = loadUser();

        loggedInUser = new SimpleStringProperty(username != null ? username : this.GUEST);
    }

    public void saveUser(String username) {
        File file = new File(USERPATH);
        saveObjectToFile(username, file);
    }

    public String loadUser() {
        File file = new File(USERPATH);
        return loadFileToObject(file);
    }

    public static Settings loadSettings() {
        File file = new File(SETTINGSPATH);
        Settings currentSettings = loadFileToObject(file);
        if (currentSettings == null) {
            currentSettings = new Settings();
            currentSettings.setDefaultValues();
        }
        return currentSettings;
    }

    public static void saveSettings(Settings settings) {
        File file = new File(SETTINGSPATH);
        saveObjectToFile(settings, file);
    }

    public static <T> boolean saveObjectToFile(T object, File selectedFile) {
        try {
            if (!selectedFile.exists())
                created = selectedFile.createNewFile();
            if (selectedFile.exists() || created) {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                        new BufferedOutputStream(
                                new FileOutputStream(selectedFile)
                        ));
                objectOutputStream.writeObject(object);
                objectOutputStream.close();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static <T> T loadFileToObject(File chosenFile) {
        ObjectInputStream objInput = null;
        try {
            objInput = new ObjectInputStream(new FileInputStream(chosenFile));
            return (T) objInput.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("The file couldn't be loaded.");
        }
        return null;
    }

    public static String loadFileToString(File chosenFile) {
        StringBuilder fileContents = new StringBuilder((int)chosenFile.length());

        try (Scanner scanner = new Scanner(chosenFile)) {
                while(scanner.hasNextLine()) {
                    fileContents.append(scanner.nextLine()).append(System.lineSeparator());
                }
                return fileContents.toString();
            } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
    public static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

}
