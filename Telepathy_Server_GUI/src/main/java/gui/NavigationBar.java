package gui;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NavigationBar extends VBox{
	
	public NavigationBar(){
		
		HBox hBMenuPoints = new HBox();
		hBMenuPoints.setSpacing(10);
		hBMenuPoints.setPadding(new Insets(0,10,10,10));
		
		//Start
		Button bStart = new Button();
		bStart.setId("MenuButtonGrey");
		bStart.setText("Start");
		
		//sLV = separator line vertical
		Separator sLVBetweenMenuPoints = new Separator();
		sLVBetweenMenuPoints.setId("sLVBetweenMenuPoints");
		sLVBetweenMenuPoints.setOrientation(Orientation.VERTICAL);
		
		//Send recieve File
		Button bSendFile = new Button();
		bSendFile.setId("MenuButtonGrey");
		bSendFile.setText("Datei senden/empfangen");
		
		Separator sLVBetweenMenuPoints_1 = new Separator();
		sLVBetweenMenuPoints_1.setId("sLVBetweenMenuPoints");
		sLVBetweenMenuPoints_1.setOrientation(Orientation.VERTICAL);
		
		//Open Url
		Button bOpenURL = new Button();
		bOpenURL.setId("MenuButtonGrey");
		bOpenURL.setText("Link öffnen");
		
		Separator sLVBetweenMenuPoints_2 = new Separator();
		sLVBetweenMenuPoints_2.setId("sLVBetweenMenuPoints");
		sLVBetweenMenuPoints_2.setOrientation(Orientation.VERTICAL);
		
		//Start program
		Button bStartProgram = new Button();
		bStartProgram.setId("MenuButtonGrey");
		bStartProgram.setText("Programm starten");
		
		Separator sLVBetweenMenuPoints_3 = new Separator();
		sLVBetweenMenuPoints_3.setId("sLVBetweenMenuPoints");
		sLVBetweenMenuPoints_3.setOrientation(Orientation.VERTICAL);
		
		//Makro
		Button bMakros = new Button();
		bMakros.setId("MenuButtonGrey");
		bMakros.setText("Makros");
		
		Separator sLVBetweenMenuPoints_4 = new Separator();
		sLVBetweenMenuPoints_4.setId("sLVBetweenMenuPoints");
		sLVBetweenMenuPoints_4.setOrientation(Orientation.VERTICAL);
		
		//Infos
		Button bInfos = new Button();
		bInfos.setId("MenuButtonGrey");
		bInfos.setText("Infos");
		
		Separator sLHUnderNB = new Separator();
		sLHUnderNB.setId("sLineHorizontal");
		sLHUnderNB.setOrientation(Orientation.HORIZONTAL);
		
		
		hBMenuPoints.getChildren().addAll(bStart, sLVBetweenMenuPoints, bSendFile, sLVBetweenMenuPoints_1, bOpenURL, sLVBetweenMenuPoints_2, bStartProgram, sLVBetweenMenuPoints_3, bMakros, sLVBetweenMenuPoints_4, bInfos);
		
		setPadding(new Insets(10));
		getChildren().addAll(hBMenuPoints, sLHUnderNB);
		
		
		bStart.setOnMouseClicked(e -> {
			SceneManager.getInstance().switchScene(SceneNames.OVERVIEW);
		});
		
		bSendFile.setOnMouseClicked(e -> {
			SceneManager.getInstance().switchScene(SceneNames.SEND_RECEIVE_FILE);
		});
		
		bOpenURL.setOnMouseClicked(e -> {
			SceneManager.getInstance().switchScene(SceneNames.OPEN_URL);
		});
		
		bStartProgram.setOnMouseClicked(e -> {
			//TODO
			//SceneManager.getInstance().switchScene(SceneNames.START_PROGRAM);
		});
		
		bMakros.setOnMouseClicked(e -> {
			//TODO
			//SceneManager.getInstance().switchScene(SceneNames.MAKROS);
		});
		
		bInfos.setOnMouseClicked(e -> {
			SceneManager.getInstance().switchScene(SceneNames.INFORMATION);
		});
	}
}
