package gui;

import java.util.HashMap;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Switching between scenes and getting the primaryStage globally
 */
public class SceneManager {
	
	private Stage primaryStage;
	private HashMap<SceneNames, Scene> scenes = new HashMap<>();
	private static SceneManager sceneManager;
	
	private SceneManager(Stage primaryStage, Scene scene){
		this.primaryStage = primaryStage;
		
		registerScene(SceneNames.START_WINDOW, scene);
		registerScene(SceneNames.OVERVIEW, new Overview().getScene());
		registerScene(SceneNames.SEND_RECEIVE_FILE, new SendReceiveFile().getScene());
		registerScene(SceneNames.OPEN_URL, new OpenURL().getScene());
		registerScene(SceneNames.INFORMATION, new Information().getScene());
	}
	
	public static SceneManager getInstance(Stage primaryStage, Scene scene){
		if(sceneManager == null){
			sceneManager = new SceneManager(primaryStage, scene);
		}
		return sceneManager;
	}
	
	public static SceneManager getInstance(){
		return sceneManager;
	}
	
	public void registerScene(SceneNames name, Scene scene){
		scenes.put(name, scene);
	}
	
	public void switchScene(SceneNames sceneName){
		primaryStage.setScene(scenes.get(sceneName));
		primaryStage.show();
	}
	
	public Stage getPrimaryStage(){
		return primaryStage;
	}
}
