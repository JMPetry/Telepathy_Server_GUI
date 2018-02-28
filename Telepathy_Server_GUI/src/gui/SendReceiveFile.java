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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

/**
 *Scene which shows the sending and receiving perspective of files
 */
public class SendReceiveFile implements SwitchableScene {

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
		//hBContent.setAlignment(Pos.CENTER);
		
		//Left side, sending to mobile device
		VBox vBSend = new VBox();
		vBSend.setSpacing(20);
		vBSend.setAlignment(Pos.TOP_LEFT);
		
		Label lSend = new Label();
		lSend.setId("lSend");
		lSend.setText("Datei senden");
		
		//path and choose button next to each other
		HBox hBChooseFile = new HBox();
		hBChooseFile.setSpacing(20);
		hBChooseFile.setPadding(new Insets(30,0,0,0));
		
		Label lPathToFileSend = new Label();
		lPathToFileSend.setId("lPathToFileSend");
		lPathToFileSend.setText("C:/default");
		lPathToFileSend.setMaxWidth(400);
		
		Button bChooseFileToSend = new Button();
		bChooseFileToSend.setId("bChooseFileToSend");
		bChooseFileToSend.setText("Datei auswählen");
		
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
		//vBReceive.setStyle("-fx-background-color: red;");
		vBReceive.setMinWidth(500);
		
		Label lReceive = new Label();
		lReceive.setId("lReceive");
		lReceive.setText("Datei empfangen");
		
		HBox hBChooseDirectory = new HBox();
		hBChooseDirectory.setSpacing(20);
		hBChooseDirectory.setPadding(new Insets(30,0,0,0));
		
		Label lPathToDirectoryReceive = new Label();
		lPathToDirectoryReceive.setId("lPathToDirectoryReceive");
		lPathToDirectoryReceive.setText("C:/default");
		lPathToDirectoryReceive.setMaxWidth(400);
		
		Button bChooseSavePathReceive = new Button();
		bChooseSavePathReceive.setId("bChooseSavePathReceive");
		bChooseSavePathReceive.setText("Ordner auswählen");	
		
		
		hBChooseDirectory.getChildren().addAll(lPathToDirectoryReceive, bChooseSavePathReceive);
		vBReceive.getChildren().addAll(lReceive, hBChooseDirectory);
		hBChooseFile.getChildren().addAll(lPathToFileSend, bChooseFileToSend);
		vBSend.getChildren().addAll(lSend, hBChooseFile, bSendFile);
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
			System.out.println("Sending file");
		});
		
		
		bChooseSavePathReceive.setOnMouseClicked(e -> {
			DirectoryChooser directoryChooser = new DirectoryChooser();
			File selectedDirectory = directoryChooser.showDialog(SceneManager.getInstance().getPrimaryStage());
			
			if(selectedDirectory != null){
            	lPathToDirectoryReceive.setText(selectedDirectory.getAbsolutePath());
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
