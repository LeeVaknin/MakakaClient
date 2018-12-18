package ViewModels;
import Model.PipeBoardModel;
import Model.ThemeModel;
import Model.User;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;

import java.util.Observable;
import java.util.Observer;

public class PipeGameViewModel extends Observable implements Observer {

    // User
    private User currentUser;
    public StringProperty userName;

    // GameBoard theme
    private ThemeModel currentTheme;
    public StringProperty theme;
    public Scene currentScene;

    // GameBoard
    public PipeBoardModel currentBoard;

    // C-TOR
    public PipeGameViewModel(Scene scene) {
        currentUser = new User();
        currentTheme = new ThemeModel(scene);
        currentBoard = new PipeBoardModel();
        char[][] boardData = {
                {'s','7','-', 'L'},
                {'|','|','F', '|'},
                {'L','7',' ', '-'},
                {'-','|','-', '|'},
                {'F','J',' ', 'g'},
        };
        currentBoard.setBoard(boardData);
    }

    public void addNewUser(){
        // In case we are adding new user.
        currentUser.setNickname(userName.get());
    }

    public void ChangeTheme() {
        this.currentTheme.SwitchTheme(theme.get());
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == currentUser) {
            // Save the new user to the database here
            // save the username locally
        }
    }
}

//
//public class PipeGameViewModel extends Observable implements BoardModel<MatrixBoard> {
//
//    @Override
//    public MatrixBoard getBoard() {
//        return null;
//    }
//}
