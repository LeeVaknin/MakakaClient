package services;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.*;


public class CommonService {

    public final String GUEST = "Guest";
    private String USERPATH = "./resources/user";

    public BooleanProperty isGameSaveRequested;
    public BooleanProperty isGameLoadRequested;
    public StringProperty loggedInUser;

    private static boolean created = false;

    public CommonService() {
        isGameSaveRequested = new SimpleBooleanProperty(false);
        isGameLoadRequested = new SimpleBooleanProperty(false);

        String username = loadUser();

        loggedInUser = new SimpleStringProperty(username != null ? username : this.GUEST);
    }

    public void saveUser(String username) {
        File file = new File(this.USERPATH);
        saveObjectToFile(username, file);
    }

    public String loadUser() {
        File file = new File(this.USERPATH);
        return loadFileToObject(file);
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
}
