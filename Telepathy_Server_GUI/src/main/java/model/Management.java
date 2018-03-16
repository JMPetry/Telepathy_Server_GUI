package model;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Holds the Server and is the 'Controller' between the GUI and the model, provides model infos to the GUI
 * @author Jean
 */
public class Management {
	
	private Server s;
	private static Management management;
	private BooleanProperty bPAuthorized = new SimpleBooleanProperty(false);
	private StringProperty sPPhoneName = new SimpleStringProperty("Mobile Device");
	
	private Management(){
		s = Server.getInstance(ProgramSettings.PORT);
	}
	
	/**
	 * Singleton structure
	 * @return only instance of the Management class
	 */
	public static Management getInstance(){
		if(management == null){
			management = new Management();
		}
		return management;
	}
	
	/**
	 * Starts the HTTP server
	 */
	public void run(){
		s.startHttpServer();
	}
	
	/**
	 * Gets the IP of the local machine and the security number which is appended and later merged into a QR code
	 * @return String of the IP plus the security code separated by a semicolon
	 */
	public String getQRCodeString(){
		return IPManager.getIPWithPortAsString() + ";" + String.valueOf(SecureNumberGenerator.generateSecureNumber());
	}
	
	/**
	 * Gets a property which indicates if a user has 'connected' to the http server 
	 * @return BooleanProperty which is set to true when a user successfully provides the correct security code
	 */
	public BooleanProperty getAuthorizedProperty(){
		return bPAuthorized;
	}
	
	/**
	 * Sets the authorized property for the GUI without creating problems with the dispatcher thread
	 * @param isAuthorized which indicates if the user is authorized or not
	 */
	public void setAuthorized(boolean isAuthorized){
		Platform.runLater(() -> bPAuthorized.set(isAuthorized));
	}
	
	/**
	 * Gets the phone name through a property to provide it to the GUI 
	 * @return StringProperty phone name
	 */
	public StringProperty getPhoneNameProperty(){
		return sPPhoneName;
	}
	
	/**
	 * Sets the phone name and changes the property to change the name in the GUI
	 * @param phoneName name of the phone which is connected
	 */
	public void setPhoneName(String phoneName){
		Platform.runLater(() -> sPPhoneName.set(phoneName));
	}
	
	/**
	 * Changes the path where a received file should be saved 
	 * @param filePath String of the directory path where the received file should be saved
	 */
	public void setDirectoryPathReceiveFile(String filePath){
		s.getSendFileHandler().setFilePath(filePath);
	}
	
	/**
	 * Closes the http server
	 */
	public void closeHttpServer(){
		s.closeHttpServer();
	}
	
	/**
	 * Resetting Management singleton for testing 
	 */
	public void resetManager(){
		s.closeHttpServer();
		management = null;
	}
}
