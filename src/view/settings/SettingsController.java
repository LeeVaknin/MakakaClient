package view.settings;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import viewModels.SettingsViewModel;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class SettingsController extends Observable implements Observer, Initializable {

    // Variables
    private SettingsViewModel vm;
    @FXML public TextField portField;
    @FXML public TextField ipField;
    @FXML public GridPane settingsPane;


    public SettingsController(SettingsViewModel settingsVM) {
        this.vm = settingsVM;
        this.vm.addObserver(this);
    }

    @FXML
    protected void handleSubmitSettingsButtonAction() {
        if(ipField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, settingsPane.getScene().getWindow(), "Error!", "Please enter ip");
            return;
        }
        if(portField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, settingsPane.getScene().getWindow(), "Error!", "Please enter port");
            return;
        }
        this.vm.saveSettings(ipField.getText() , Integer.parseInt(portField.getText()));
        final Stage stage = (Stage) settingsPane.getScene().getWindow();
        stage.close();
        showAlert(Alert.AlertType.INFORMATION, settingsPane.getScene().getWindow(), "Settings were set Successfully!", "Ip: " + ipField.getText() + "\nPort: " + portField.getText());
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == this.vm) {
           this.setFields();
        }
    }

    private void setFields() {
        if (this.vm != null && this.vm.getCurrentSettings() != null) {
            Integer port = this.vm.getCurrentSettings().getPort() == null ? 8080 : this.vm.getCurrentSettings().getPort();
            String ip = this.vm.getCurrentSettings().getIp() == null ? "127.0.0.1" : this.vm.getCurrentSettings().getIp();
            this.portField = this.portField == null ? new TextField() : this.portField;
            this.ipField = this.ipField == null ? new TextField() : this.ipField;
            this.portField.setText(port.toString());
            this.ipField.setText(ip);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setFields();
    }

    public void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}
