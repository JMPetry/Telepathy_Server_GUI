package model;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Management {
	
	private Server s;
	private static Management management;
	private BooleanProperty bPAuthorized = new SimpleBooleanProperty(false);
	private StringProperty sPPhoneName = new SimpleStringProperty("Mobile Device");
	
	private Management(){
		s = Server.getInstance(ProgramSettings.PORT);
	}
	
	public static Management getInstance(){
		if(management == null){
			management = new Management();
		}
		return management;
	}
	
	public void run(){
		s.startHttpServer();
	}
	
	public String getQRCodeString(){
		return IPManager.getIPWithPortAsString() + ";" + String.valueOf(SecureNumberGenerator.generateSecureNumber());
	}
	
	public BooleanProperty getAuthorizedProperty(){
		return bPAuthorized;
	}
	
	public void setAuthorized(boolean isAuthorized){
		Platform.runLater(() -> bPAuthorized.set(isAuthorized));
	}
	
	public StringProperty getPhoneNameProperty(){
		return sPPhoneName;
	}
	
	public void setPhoneName(String phoneName){
		Platform.runLater(() -> sPPhoneName.set(phoneName));
	}
	
	public void setDirectoryPathReceiveFile(String filePath){
		s.getSendFileHandler().setFilePath(filePath);
	}
	
	public void closeHttpServer(){
		s.closeHttpServer();
	}
}
