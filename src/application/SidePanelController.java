package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
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
        timeSlider.valueProperty()
                .addListener((ChangeListener) -> Converter.audioSeconds = (int) timeSlider.getValue());
    }

    @FXML
    void onAboutBtnClick(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("AboutView.fxml"));
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setAlwaysOnTop(true);
            stage.setTitle("关于");
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onExitBtnClick(ActionEvent event) {
        Platform.exit();
    }
}
