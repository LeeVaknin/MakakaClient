package view.mainWindow;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.ThemeManagerService;
import utils.DIHelper;
import services.CommonService;
import viewModels.MainWindowViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Observer;
import java.util.ResourceBundle;


public class MainWindowController implements Observer, Initializable {

    // Variables
    private MainWindowViewModel vm;
    private ThemeManagerService themeManager;
    private CommonService commonService;
    private StringProperty theme;
    private StringProperty userNamePropery;

    // C-TORs and overrides
    public MainWindowController(ThemeManagerService themeManagerService, MainWindowViewModel mainWindowViewModel) {

        // Set the themeManager service and bind the theme type to it
        this.themeManager = themeManagerService;
        this.themeManager.addObserver(this);

        // Set the vm and bind the logged in user to the vm
        this.vm = mainWindowViewModel;
        this.vm.addObserver(this);

        this.commonService = new CommonService();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.loadMainWindow();
    }

    @Override
    public void update (java.util.Observable o, Object arg){
        if (o == this.themeManager) {
            theme = new SimpleStringProperty();
            theme.bindBidirectional(this.themeManager.themeName);
            themeToggle.textProperty().bindBidirectional(theme);

        }
    }

    // FXMl variables
    @FXML protected Button themeToggle;
    @FXML protected  BorderPane rootBorder;
    @FXML protected Text loggedInUser;

    @FXML protected void handleToggleAction(ActionEvent t) {
        if (theme.getValue().equals("Dark")) {
            theme.setValue("Bright");
        } else {
            theme.setValue("Dark");
        }
    }

    @FXML
    private void showSettings() {
        this.loadSettingsWindow();
    }

    @FXML
    private void loadLevel() {
        this.commonService.isGameLoadRequested.setValue(true);
    }

    @FXML
    private void saveLevel() {
        this.commonService.isGameSaveRequested.setValue(true);
    }

    @FXML
    private void showTimer() {
    }

    @FXML
    private void showStepper() {
    }

    private void handleLoginUpdate() {
        String newNickname = this.commonService.loggedInUser.getValue();
        this.loggedInUser.setText(newNickname);
        if (newNickname.equals(this.commonService.GUEST))
            this.loadLoginView();
        else
            this.loadBoard();
    }

    // load views

    private void loadMainWindow() {
        if (this.loggedInUser == null )
            this.loggedInUser = new Text();
        this.loggedInUser.setText(this.commonService.loggedInUser.getValue());

        // subscribe to change
        this.commonService.loggedInUser.addListener(change -> this.handleLoginUpdate());
        this.handleLoginUpdate();
    }

    private Parent createView(String vmName,String resourcePath, Object[] services) {
        URL resource = getClass().getClassLoader().getResource(resourcePath);
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        try {
            DIHelper.injectServiceAndVM(fxmlLoader, vmName, services);
            return fxmlLoader.load();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | InvocationTargetException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void loadBoard() {
        loadCentralView("PipeGameViewModel", "view/pipeGame/PipeGame.fxml", new Object[] { themeManager, this.commonService});
    }

    private void loadSettingsWindow() {
        Parent view = this.createView("SettingsViewModel", "view/settings/Settings.fxml", new Object[] {} );
        if (view == null) return;
        Scene scene = new Scene(view);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.toFront();
        stage.show();
    }

    private void loadCentralView(String vmName,String resourcePath, Object[] services) {
        Parent parent = createView(vmName, resourcePath, services);
        rootBorder.setCenter(null);
        rootBorder.centerProperty().setValue(parent);
    }

    private void loadLoginView() {
        loadCentralView("LoginViewModel", "view/login/Login.fxml", new Object[] { this.commonService});
    }
}


