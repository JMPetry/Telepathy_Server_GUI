package gui;

import java.io.File;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import model.Management;

/**
 *Scene which shows the sending and receiving perspective of files
 */
public class SendReceiveFile implements SwitchableScene {

	private static final double SPACE_TITLE_CHOOSE = 10.0;
	
	private double xOffset = 0;
	private double yOffset = 0;
	private Scene scene;

	public SendReceiveFile() {

		BorderPane root = new BorderPane();
		root.setId("bpMain");
		
		VBox vBTopMenu = new VBox();
		
		//Contains left and right side(send and receive)
		HBox hBContent = new HBox();
		hBContent.setSpacing(10);
		hBContent.setPadding(new Insets(10));
		
		//Left side, sending to mobile device
		VBox vBSend = new VBox();
		vBSend.setSpacing(20);
		vBSend.setMinWidth((WindowSettings.WINDOW_WIDTH/2.0)-50);
		
		Label lSend = new Label();
		lSend.setId("lSend");
		lSend.setText("Datei senden");
		
		//path and choose button next to each other
		AnchorPane aPChooseFile = new AnchorPane();
		
		Label lPathToFileSend = new Label();
		lPathToFileSend.setId("lPathToFileSend");
		lPathToFileSend.setText("C:/default");
		lPathToFileSend.setMaxWidth(400);
		
		Button bChooseFileToSend = new Button();
		bChooseFileToSend.setId("bChooseFileToSend");
		bChooseFileToSend.setText("Datei auswählen");
		
		HBox hBSendFileContainer = new HBox();
		hBSendFileContainer.setAlignment(Pos.CENTER);
		
		Button bSendFile = new Button();
		bSendFile.setId("bSendFile");
		bSendFile.setText("Datei zum Handy senden");
		
		//Separator between sending and recieving
		Separator sVLSeparateSendReceive = new Separator();
		sVLSeparateSendReceive.setId("sLineVertical");
		sVLSeparateSendReceive.setOrientation(Orientation.VERTICAL);
		
		//Right side, receiving files from device
		VBox vBReceive = new VBox();
		vBReceive.setSpacing(20);
		vBReceive.setMinWidth(WindowSettings.WINDOW_WIDTH/2.0);
		
		Label lReceive = new Label();
		lReceive.setId("lReceive");
		lReceive.setText("Datei empfangen");
		
		AnchorPane aPChooseDirectory = new AnchorPane();
		
		Label lPathToDirectoryReceive = new Label();
		lPathToDirectoryReceive.setId("lPathToDirectoryReceive");
		lPathToDirectoryReceive.setText("C:/default");
		lPathToDirectoryReceive.setMaxWidth(400);
		
		Button bChooseSavePathReceive = new Button();
		bChooseSavePathReceive.setId("bChooseSavePathReceive");
		bChooseSavePathReceive.setText("Ordner auswählen");
		
		AnchorPane.setRightAnchor(bChooseSavePathReceive, 0.0);
		AnchorPane.setTopAnchor(bChooseSavePathReceive, SPACE_TITLE_CHOOSE);
		AnchorPane.setRightAnchor(bChooseFileToSend, 0.0);
		AnchorPane.setTopAnchor(bChooseFileToSend, SPACE_TITLE_CHOOSE);
		AnchorPane.setTopAnchor(lPathToFileSend, SPACE_TITLE_CHOOSE);
		AnchorPane.setTopAnchor(lPathToDirectoryReceive, SPACE_TITLE_CHOOSE);
		
		aPChooseDirectory.getChildren().addAll(lPathToDirectoryReceive, bChooseSavePathReceive);
		vBReceive.getChildren().addAll(lReceive, aPChooseDirectory);
		aPChooseFile.getChildren().addAll(lPathToFileSend, bChooseFileToSend);
		hBSendFileContainer.getChildren().add(bSendFile);
		vBSend.getChildren().addAll(lSend, aPChooseFile, hBSendFileContainer);
		hBContent.getChildren().addAll(vBSend, sVLSeparateSendReceive, vBReceive);
		vBTopMenu.getChildren().addAll(new TopBar(), new NavigationBar());
		root.setTop(vBTopMenu);
		root.setCenter(hBContent);
		
		
		bChooseFileToSend.setOnMouseClicked(e -> {
			FileChooser fileChooser = new FileChooser();
			File selectedFile = fileChooser.showOpenDialog(null);
			
			if(selectedFile != null){
				lPathToFileSend.setText(selectedFile.getAbsolutePath());
			}
		});
		
		
		bSendFile.setOnMouseClicked(e -> {
			System.out.println("TODO: Sending file");
		});
		
		
		bChooseSavePathReceive.setOnMouseClicked(e -> {
			DirectoryChooser directoryChooser = new DirectoryChooser();
			File selectedDirectory = directoryChooser.showDialog(SceneManager.getInstance().getPrimaryStage());
			
			if(selectedDirectory != null){
            	lPathToDirectoryReceive.setText(selectedDirectory.getAbsolutePath());
            	Management.getInstance().setFilePathSendReceiveFile(selectedDirectory.getAbsolutePath());
            }
			
		});
		
		// Make undecorated window dragable
		root.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});

		root.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				SceneManager.getInstance().getPrimaryStage().setX(event.getScreenX() - xOffset);
				SceneManager.getInstance().getPrimaryStage().setY(event.getScreenY() - yOffset);
			}
		});

		scene = new Scene(root, WindowSettings.WINDOW_WIDTH, WindowSettings.WINDOW_HEIGHT);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	}

	@Override
	public Scene getScene() {
		return scene;
	}

}
