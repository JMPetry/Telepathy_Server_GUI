package gui;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Information implements SwitchableScene {

	private double xOffset = 0;
	private double yOffset = 0;
	private Scene scene;

	public Information() {

		BorderPane root = new BorderPane();
		root.setId("bpMain");
		
		VBox vBTopMenu = new VBox();
		
		
		VBox vBInfoContainer = new VBox();
		vBInfoContainer.setSpacing(10);
		vBInfoContainer.setAlignment(Pos.CENTER);
		
		HBox hBVersionContainer = new HBox();
		hBVersionContainer.setSpacing(10);
		hBVersionContainer.setAlignment(Pos.CENTER);
		
		Label lVersion = new Label();
		lVersion.setId("InfoFirstValue");
		lVersion.setText("Version: ");
		
		Label lVersionNumber = new Label();
		lVersionNumber.setId("lVersionNumber");
		lVersionNumber.setText(WindowSettings.VERSION_NUMBER);
		
		HBox hBCreatorContainer = new HBox();
		hBCreatorContainer.setSpacing(10);
		hBCreatorContainer.setAlignment(Pos.CENTER);
		
		Label lCreator = new Label();
		lCreator.setId("InfoFirstValue");
		lCreator.setText("Creator: ");
		
		Label lCreatorName = new Label();
		lCreatorName.setId("lCreatorName");
		lCreatorName.setText("Jean Petry");
		
		hBCreatorContainer.getChildren().addAll(lCreator, lCreatorName);
		hBVersionContainer.getChildren().addAll(lVersion, lVersionNumber);
		vBInfoContainer.getChildren().addAll(hBVersionContainer, hBCreatorContainer);
		vBTopMenu.getChildren().addAll(new TopBar(), new NavigationBar());
		root.setTop(vBTopMenu);
		root.setCenter(vBInfoContainer);
		
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
