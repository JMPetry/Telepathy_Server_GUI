package gui;
	
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import model.Management;
import model.QRCodeGenerator;

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
			
			Management.getInstance().run();
			
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
			lInfoForCode.setText("Bitte folgenden QR-Code auf dem Handy einscannen:");
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
			
			ImageView iVQRCode = new ImageView(QRCodeGenerator.generateQRCode(Management.getInstance().getQRCodeString(), 350, 350));
			iVQRCode.setPreserveRatio(true);
			iVQRCode.setVisible(false);
			iVQRCode.setFitWidth(350);
			
			sPCircle.getChildren().addAll(circleConnect,lConnect);
			vBContent.getChildren().addAll(lInfoForCode, sPCircle);
			spConnect.getChildren().addAll(vBContent);
			root.setTop(new TopBar());
			root.setCenter(spConnect);
			
			//Switch to next scene
			circleConnect.setOnMouseClicked(e ->{
				
				vBContent.getChildren().remove(sPCircle);
				vBContent.getChildren().add(iVQRCode);
				lInfoForCode.setVisible(true);
				iVQRCode.setVisible(true);
				
				FadeTransition fadeQRCodeIn = new FadeTransition(Duration.millis(2000), iVQRCode);
		        fadeQRCodeIn.setFromValue(0.0f);
		        fadeQRCodeIn.setToValue(1.0f);
		        fadeQRCodeIn.play();
				
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
			
			Management.getInstance().getAuthorizedProperty().addListener(new ChangeListener<Boolean>(){
				
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					
					if(newValue){
						SceneManager.getInstance().switchScene(SceneNames.OVERVIEW);
					}
				}
			});
			
			SceneManager.getInstance(primaryStage, scene);
			
			scene = new Scene(root,WindowSettings.WINDOW_WIDTH,WindowSettings.WINDOW_HEIGHT);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setTitle("Telepathy");
			primaryStage.getIcons().add(new Image("file:images/iconTelepathy.png"));
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
