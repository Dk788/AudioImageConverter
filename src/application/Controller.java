package application;

import java.io.File;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXSpinner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;

public class Controller {
	
	private static File audioFile = null;
	private static File imageFile = null;
	
	@FXML
    private AnchorPane anchorPane;

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
    private StackPane stackPane;

    @FXML
    private JFXDialog dialog;

    @FXML
    private JFXButton okButton;
    
    @FXML
    private Label msgLable;

    @FXML
    void onConvertBtnClick(ActionEvent event) {
    	if (audioFile == null && imageFile == null) {
    		showDialog("请选择音频或图像文件");
		} else {
			convertSpinner.setVisible(true);
			if (audioFile != null) {
				// TODO: audioFile to image
				showDialog(audioFile.toPath().toString());
				convertSpinner.setVisible(false);
			}
			if (imageFile != null) {
				// TODO: imageFile to audio
				showDialog(imageFile.toPath().toString());
				convertSpinner.setVisible(false);
			}
		}
    }
    
    @FXML
    void onAudioBtnClick(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("选择音频文件");
    	fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    	audioFile = fileChooser.showOpenDialog(audioButton.getScene().getWindow());
//    	try {
//			Files.copy(audio.toPath(), Paths.get("file/audio.bin"), StandardCopyOption.REPLACE_EXISTING);
//		} catch (IOException e) {
//			showDialog("保存时发生错误");
//		}
    }
    
    @FXML
    void onImageBtnClick(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("选择图像文件");
    	fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    	imageFile = fileChooser.showOpenDialog(imageButton.getScene().getWindow());
//    	try {
//			Files.copy(image.toPath(), Paths.get("file/image.bin"), StandardCopyOption.REPLACE_EXISTING);
//		} catch (IOException e) {
//			showDialog("保存时发生错误");
//		}
    }
    
    @FXML
    void onHbgClick(MouseEvent event) {

    }
    
    @FXML
    void onOkBtnClick(ActionEvent event) {
    	closeDialog();
    }
    
    private void showDialog(String msg) {
    	stackPane.setVisible(true);
		audioButton.setDisable(true);
		imageButton.setDisable(true);
		convertButton.setDisable(true);
		msgLable.setText(msg);
		dialog.setDialogContainer(stackPane);
		dialog.show();
    }
    
    private void closeDialog() {
    	audioButton.setDisable(false);
		imageButton.setDisable(false);
		convertButton.setDisable(false);
    	dialog.close();
    	stackPane.setVisible(false);
	}
}
