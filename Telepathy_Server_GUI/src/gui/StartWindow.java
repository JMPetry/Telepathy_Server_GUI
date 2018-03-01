package gui;
	
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * Window which is shown up when the application is started
 */
public class StartWindow extends Application implements SwitchableScene{
	
	private static final double CIRCLE_CONNECT_RADIUS = 130;
	private static final double FONT_SIZE_LABEL_CONNECT = 36;
	private static final String FONT_NAME = "Segoe UI";
	
	private double xOffset = 0;
    private double yOffset = 0;
    private Scene scene;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			
			BorderPane root = new BorderPane();
			root.setId("bpMain");
			
			//StackPane in BorderPane body which holds the circle and the Label on top of it
			StackPane spConnect = new StackPane();
			
			//DropShadow effect for the circle to make it more "clickable"
			DropShadow dsCircle = new DropShadow();
			dsCircle.setRadius(20);
			
			//VBox which contains the circlebutton, and both labels
			VBox vBContent = new VBox();
			vBContent.setSpacing(60);
			vBContent.setAlignment(Pos.CENTER);
			
			Label lInfoForCode = new Label();
			lInfoForCode.setId("lInfoForCode");
			lInfoForCode.setText("Bitte folgenden Code auf dem Handy eingeben:");
			lInfoForCode.setFont(Font.font(FONT_NAME, FontWeight.BOLD, FONT_SIZE_LABEL_CONNECT));
			lInfoForCode.setVisible(false);
			
			StackPane sPCircle = new StackPane();
			sPCircle.setTranslateY(-80);
			
			//Circle which is used to trigger the change of the scene
			Circle circleConnect = new Circle();
			circleConnect.setId("circleConnect");
			circleConnect.setEffect(dsCircle);
			circleConnect.setRadius(CIRCLE_CONNECT_RADIUS);
			circleConnect.setFill(Color.rgb(6, 132, 54));
			
			Label lConnect = new Label();
			lConnect.setId("lConnect");
			lConnect.setText("Koppeln");
			lConnect.setFont(Font.font(FONT_NAME, FontWeight.BOLD, FONT_SIZE_LABEL_CONNECT));
			lConnect.setMouseTransparent(true);
			
			
			sPCircle.getChildren().addAll(circleConnect,lConnect);
			vBContent.getChildren().addAll(lInfoForCode, sPCircle);
			spConnect.getChildren().addAll(vBContent);
			root.setTop(new TopBar());
			root.setCenter(spConnect);
			
			//Switch to next scene
			circleConnect.setOnMouseClicked(e ->{
				
				final Timeline tmSPCircleYTranslation = new Timeline();
				final KeyValue kvYTranslate = new KeyValue(sPCircle.translateYProperty(), 30);
				final KeyFrame kfYTranslate = new KeyFrame(Duration.millis(250), kvYTranslate);
				tmSPCircleYTranslation.getKeyFrames().add(kfYTranslate);
				tmSPCircleYTranslation.play();
				
				lInfoForCode.setVisible(true);
				
				
				final Timeline tmCircleConnectColor = new Timeline();
				final KeyValue kvColorCirlce = new KeyValue(circleConnect.fillProperty(), Color.rgb(148, 163, 35));
				final KeyFrame kfColorCircle = new KeyFrame(Duration.millis(250), kvColorCirlce);
				tmCircleConnectColor.getKeyFrames().add(kfColorCircle);
				tmCircleConnectColor.play();
				
				System.out.println("click");
				
				SceneManager.getInstance().switchScene(SceneNames.OVERVIEW);
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
					primaryStage.setX(event.getScreenX() - xOffset);
	                primaryStage.setY(event.getScreenY() - yOffset);
				}
			});
			
			SceneManager.getInstance(primaryStage, scene);
			
			scene = new Scene(root,WindowSettings.WINDOW_WIDTH,WindowSettings.WINDOW_HEIGHT);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setTitle("Telepathy");
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public Scene getScene() {
		return scene;
	}
}
