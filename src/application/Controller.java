package application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXSpinner;

public class Controller {
	
	private static File audioFile = null;
	private static File imageFile = null;
	private static boolean userChoice = true; 
	
	@FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXButton convertButton;
    
    @FXML
    private JFXButton clearButton;

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
    private JFXButton okButton;
    
    @FXML
    private JFXButton cancelButton;
    
    @FXML
    private Label msgLabel;
    
    @FXML
    private Label titleLabel;

    @FXML
    void onConvertBtnClick(ActionEvent event) {
    	if (audioFile == null && imageFile == null) {
    		showDialog("请选择音频或图像文件", false);
		} else {
			clearButton.setDisable(true);
	    	convertSpinner.setVisible(true);
			if (audioFile != null) {
				// audioToImage
				Thread thread = new Thread() {
				    public void run(){
				    	Converter converter = new Converter();
						File newImage = converter.audioToImage(audioFile);
						convertSpinner.setVisible(false);
						Platform.runLater(() -> {
							if (saveFile(newImage, "jpg")) {
								imageButton.setDisable(false);
							}
							clearButton.setDisable(false);
						});
				    }
				};
				thread.start();
			}
			if (imageFile != null) {
				// imageToAudio
				Thread thread = new Thread() {
				    public void run(){
				    	Converter converter = new Converter();
						File newAudio = converter.imageToAudio(imageFile);
						convertSpinner.setVisible(false);
						Platform.runLater(() -> {
							if (saveFile(newAudio, "wav")) {
								audioButton.setDisable(false);
							}
							clearButton.setDisable(false);
						});
				    }
				};
				thread.start();	
			}
		}
    }
    
    @FXML
    void onClearBtnClick(ActionEvent event) {
    	if (clean()) {
    		showDialog("清除完成", false);
		} else {
			showDialog("清除时发生错误", false);
		}
    }
    
    @FXML
    void onAudioBtnClick(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("选择音频文件");
    	fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    	fileChooser.getExtensionFilters().addAll(
    			new FileChooser.ExtensionFilter("音频文件", "*.mp3", "*.wav", "*.mid", "*.flac")
                );
    	audioFile = fileChooser.showOpenDialog(audioButton.getScene().getWindow());
    	if (audioFile != null) {
    		titleLabel.setText("音频图像转换器" + " - " + audioFile.getName());
			imageButton.setDisable(true);
		}
    }
    
    @FXML
    void onImageBtnClick(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("选择图像文件");
    	fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    	fileChooser.getExtensionFilters().addAll(
    			new FileChooser.ExtensionFilter("图像文件", "*.jpg", "*.png", "*.bmp")
                );
    	imageFile = fileChooser.showOpenDialog(imageButton.getScene().getWindow());
    	if (imageFile != null) {
    		titleLabel.setText("音频图像转换器" + " - " + imageFile.getName());
			audioButton.setDisable(true);
		}
    }
    
    @FXML
    void onHbgClick(MouseEvent event) {
    	// TODO
    }
    
    private boolean showDialog(String msg, boolean isShowCancelBtn) {
		userChoice = true;
    	if (isShowCancelBtn) {
			cancelButton.setVisible(true);
		} else {
			cancelButton.setVisible(false);
		}
    	stackPane.setVisible(true);
    	msgLabel.setText(msg);
    	JFXAlert<?> alert = new JFXAlert<Object>((Stage)anchorPane.getScene().getWindow());
    	alert.setContent(stackPane);
    	okButton.setOnAction((action -> {
            userChoice = true;
		    stackPane.setVisible(false);
		    alert.close();
        }));
    	cancelButton.setOnAction((action -> {
            userChoice = false;
		    stackPane.setVisible(false);
		    alert.close();
        }));
    	alert.showAndWait();
		return userChoice;
    }
    
    // 选择路径保存 file，返回是否保存成功
    private boolean saveFile(File file, String Extension) {
    	boolean isGiveUpSaving = false;
    	boolean isSuccess = false;
    	if (file == null) {
    		showDialog("发生编码错误", false);
    	} else {
    		FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("保存文件");
			fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
			fileChooser.getExtensionFilters().addAll(
	    			new FileChooser.ExtensionFilter(Extension + "文件", "*." + Extension)
	                );
			fileChooser.setInitialFileName("*." + Extension);
			
			while (true) {
				File targetFile = null;
				targetFile = fileChooser.showSaveDialog(convertButton.getScene().getWindow());
				if (targetFile == null) {	// 用户直接关闭了保存界面
					isGiveUpSaving = showDialog("放弃保存？", true);
					if (!isGiveUpSaving) {	// 如果仍要保存
						continue;			// 重新打开保存界面
					} else {				// 否则
						break;				// 用户不想保存
					}
				} else {
					// Write file to targetFile, set isSuccess
					try {
						Files.copy(file.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
						isSuccess = true;
					} catch (IOException e) {
						isSuccess = false;
						e.printStackTrace();
					}
					break;
				}
			}
		}
    	return (!isGiveUpSaving && isSuccess);	// 返回是否保存成功
	}
    
    public boolean clean() {
		boolean result = true;
		userChoice = true;
    	audioFile = null;
    	imageFile = null;
    	try {
			Files.deleteIfExists(Paths.get(System.getProperty("java.io.tmpdir") + File.separator + "newAudio"));
			Files.deleteIfExists(Paths.get(System.getProperty("java.io.tmpdir") + File.separator + "newImage"));
		} catch (IOException e) {
			result = false;
			e.printStackTrace();
		}
    	audioButton.setDisable(false);
    	imageButton.setDisable(false);
    	titleLabel.setText("音频图像转换器");
		return result;
	}
}
