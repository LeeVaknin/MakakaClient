package view.login;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import viewModels.LoginViewModel;


import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class LoginController extends Observable implements Initializable {

    @FXML protected TextField userName;
    private LoginViewModel vm;

    public LoginController(LoginViewModel vm) {
        this.vm = vm;
    }

    @FXML
    protected void handleLogin() {
        String usernmae = this.userName.getText();
        if (!usernmae.equals("")) {
            // save the username and load the board game
            this.vm.Login(usernmae);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
