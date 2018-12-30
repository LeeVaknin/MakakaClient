package view.settings;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import viewModels.SettingsViewModel;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class SettingsController extends Observable implements Observer, Initializable{

    // Variables
    private SettingsViewModel vm;
    @FXML public TextField portField;
    @FXML public TextField ipField;


    public SettingsController(SettingsViewModel settingsVM) {
        this.vm = settingsVM;
        this.vm.addObserver(this);
    }

    @FXML
    protected void handleSubmitSettingsButtonAction() {
        this.vm.saveSettings(ipField.getText() , Integer.parseInt(portField.getText()));

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
}
