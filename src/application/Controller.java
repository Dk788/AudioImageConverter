package application;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXSpinner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class Controller {

	@FXML
    private JFXButton convertButton;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXSpinner convertSpinner;

    @FXML
    private JFXButton audioButton;

    @FXML
    private JFXButton imageButton;

    @FXML
    private ImageView audioImageView;

    @FXML
    private ImageView imageImageView;

    @FXML
    void onClick(ActionEvent event) {
    	convertSpinner.setVisible(true);
    }

}
