package http_handler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import model.HttpPropertyNames;
import model.Management;
import model.SecureNumberGenerator;

/**
 * Checks if the secure number is right and sets the phone name
 * @author Jean
 */
public class AuthorizeStartHandler implements HttpHandler {

	private static final Logger LOG = Logger.getLogger(AuthorizeStartHandler.class.getName());
	private int statusCode = HttpURLConnection.HTTP_INTERNAL_ERROR;
	
	/**
	 * Handles the 'first' http request which should be triggered after the user scans the QR code
	 */
	@Override
	public void handle(HttpExchange htex) throws IOException {

		int secNum = -1;
		String response = "fail";
		String phoneName = "Mobile Device";

		try {
			secNum = Integer.parseInt(htex.getRequestHeaders().get(HttpPropertyNames.SEC_NUM.toString()).get(0));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		//set phone name 
		if(htex.getRequestHeaders().get(HttpPropertyNames.PHONE_NAME.toString()) != null){
			phoneName = htex.getRequestHeaders().get(HttpPropertyNames.PHONE_NAME.toString()).get(0);
		}
		
		if (secNum == SecureNumberGenerator.getSNum()) {
			LOG.log(Level.INFO, htex.getRemoteAddress().toString() + " logged on");
			response = "success";
			statusCode = HttpURLConnection.HTTP_OK;
		} else {
			LOG.log(Level.WARNING, htex.getRemoteAddress().toString() + " tried to log on with wrong security number");
			statusCode = HttpURLConnection.HTTP_UNAUTHORIZED;
		}

		htex.sendResponseHeaders(statusCode, response.getBytes().length);
		OutputStream os = htex.getResponseBody();
		os.write(response.getBytes());
		os.close();
		
		//changing the GUI while creating a response will result in a unexpected EOF
		if(secNum == SecureNumberGenerator.getSNum()){
			authorize(phoneName);
		}
	}
	
	/**
	 * Changing the GUI after response is send
	 * @param phoneName name of the mobile device
	 */
	private void authorize(String phoneName){
		Management.getInstance().setAuthorized(true);
		Management.getInstance().setPhoneName(phoneName);
	}
}
