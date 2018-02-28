package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * HBox which holds the minimize and the X buttons to close or minimize the window
 */
public class TopBar extends HBox{
	
	private static final double WINDOW_BUTTONS_SIZE = 15;
	private static final double WINDOW_BUTTONS_SPACING = 5;
	
	public TopBar(){
		
		setId("hbWindowOperations");
		
		ImageView iVMinimize = new ImageView(new Image("minimize.png"));
		iVMinimize.setPreserveRatio(true);
		iVMinimize.setPickOnBounds(true);
		iVMinimize.setFitWidth(WINDOW_BUTTONS_SIZE);
		
		ImageView iVCloseWindow = new ImageView(new Image("close.png"));
		iVCloseWindow.setPreserveRatio(true);
		iVCloseWindow.setPickOnBounds(true);
		iVCloseWindow.setFitWidth(WINDOW_BUTTONS_SIZE);
		
		setAlignment(Pos.CENTER_RIGHT);
		setPadding(new Insets(5,5,5,0));
		setSpacing(WINDOW_BUTTONS_SPACING);
		getChildren().addAll(iVMinimize,iVCloseWindow);
		
		//Minimize window
		iVMinimize.setOnMouseClicked(e ->{
			SceneManager.getInstance().getPrimaryStage().setIconified(true);
		});
		
		//Close window
		iVCloseWindow.setOnMouseClicked(e ->{
			SceneManager.getInstance().getPrimaryStage().close();
		});
		
	}
	
}
