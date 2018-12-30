package services;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.io.*;


public class FilesLoaderService {

    public BooleanProperty isGameSaveRequested;
    public BooleanProperty isGameLoadRequested;
    private static boolean created = false;

    public FilesLoaderService() {
        isGameSaveRequested = new SimpleBooleanProperty(false);
        isGameLoadRequested = new SimpleBooleanProperty(false);
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
            e.printStackTrace();
        }
        return null;
    }


}
