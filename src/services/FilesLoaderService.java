package services;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import java.io.*;

public class FilesLoaderService<T> {

    public BooleanProperty isGameSaveRequested;

    public BooleanProperty isGameLoadRequested;

    public FilesLoaderService() {
        isGameSaveRequested = new SimpleBooleanProperty(false);
        isGameLoadRequested = new SimpleBooleanProperty(false);
    }

    public boolean saveObjectToFile(T object, File selectedFile) {
        try {
            boolean created = selectedFile.createNewFile();
            if (created) {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                        new BufferedOutputStream(
                                new FileOutputStream(selectedFile)
                        ));
                objectOutputStream.writeObject(object);
                objectOutputStream.close();
                return true;
            }}
        catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public T loadFileToObject(File chosenFile) {
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
