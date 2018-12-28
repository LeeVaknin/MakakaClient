package viewModels;

import model.User;
import javafx.beans.property.StringProperty;

import java.util.Observable;
import java.util.Observer;

public class MainWindowViewModel extends Observable implements Observer {

    // User
    private User currentUser;
    public StringProperty userName;

    public MainWindowViewModel() {
        currentUser = new User();
    }

    public void addNewUser(){
        // In case we are adding new user.
        currentUser.setNickname(userName.get());
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
