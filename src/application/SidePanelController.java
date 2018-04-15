package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;

public class SidePanelController implements Initializable {
    @FXML
    private JFXSlider timeSlider;

    @FXML
    private JFXButton aboutButton;

    @FXML
    private JFXButton exitButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
    }

    @FXML
    void onAboutBtnClick(ActionEvent event) {
        // TODO
    }

    @FXML
    void onExitBtnClick(ActionEvent event) {
        Platform.exit();
    }
}
