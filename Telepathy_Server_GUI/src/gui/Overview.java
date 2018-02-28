package gui;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Overview implements SwitchableScene{
	
	private double xOffset = 0;
    private double yOffset = 0;
	private Scene scene;
	
	public Overview(){
		
		BorderPane root = new BorderPane();
		root.setId("bpMain");
		
		VBox vBTopMenu = new VBox();
		
		
		
		VBox vBConnectedDevice = new VBox();
		
		
		Label lConnectedDeviceInfo = new Label();
		lConnectedDeviceInfo.setId("lConnectedDeviceInfo");
		lConnectedDeviceInfo.setText("Verbundenes Handy:");
		
		Label lDeviceName = new Label();
		lDeviceName.setId("lDeviceName");
		lDeviceName.setText("BeispielName");
		
		vBConnectedDevice.getChildren().addAll(lConnectedDeviceInfo, lDeviceName);
		vBTopMenu.getChildren().addAll(new TopBar(), new NavigationBar());
		root.setTop(vBTopMenu);
		root.setCenter(vBConnectedDevice);
		
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
		
		scene = new Scene(root,WindowSettings.WINDOW_WIDTH, WindowSettings.WINDOW_HEIGHT);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	}

	@Override
	public Scene getScene() {
		return scene;
	}

}
