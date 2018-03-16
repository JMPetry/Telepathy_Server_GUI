package gui;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Management;

public class Overview implements SwitchableScene{
	
	private double xOffset = 0;
    private double yOffset = 0;
	private Scene scene;
	
	public Overview(){
		
		BorderPane root = new BorderPane();
		root.setId("bpMain");
		
		//Contains topbar and navigartionbar
		VBox vBTopMenu = new VBox();
		
		
		//Contains name of the connected device and an info text
		VBox vBConnectedDevice = new VBox();
		vBConnectedDevice.setSpacing(10);
		vBConnectedDevice.setAlignment(Pos.CENTER);
		
		Label lConnectedDeviceInfo = new Label();
		lConnectedDeviceInfo.setId("lConnectedDeviceInfo");
		lConnectedDeviceInfo.setText("Verbundenes Handy:");
		
		Label lDeviceName = new Label();
		lDeviceName.setId("lDeviceName");
		lDeviceName.setText("BeispielName");
		
		//HBox hBDisconnectContainer
		HBox hBDisconnectContainer = new HBox();
		hBDisconnectContainer.setAlignment(Pos.CENTER);
		hBDisconnectContainer.setPadding(new Insets(0,0,35,0));
		
		//Disconnect Button
		Button bDisconnectDevice = new Button();
		bDisconnectDevice.setId("bDisconnectDevice");
		bDisconnectDevice.setMinWidth(200);
		bDisconnectDevice.setText("Trennen");
		
		
		hBDisconnectContainer.getChildren().add(bDisconnectDevice);
		vBConnectedDevice.getChildren().addAll(lConnectedDeviceInfo, lDeviceName);
		vBTopMenu.getChildren().addAll(new TopBar(), new NavigationBar());
		root.setTop(vBTopMenu);
		root.setCenter(vBConnectedDevice);
		root.setBottom(hBDisconnectContainer);
		
		bDisconnectDevice.setOnMouseClicked(e ->{
			System.out.println("Disconnect");
		});
		
		//Make undecorated window dragable
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
		
		lDeviceName.textProperty().bind(Management.getInstance().getPhoneNameProperty());
		
		scene = new Scene(root,WindowSettings.WINDOW_WIDTH, WindowSettings.WINDOW_HEIGHT);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	}

	@Override
	public Scene getScene() {
		return scene;
	}

}
