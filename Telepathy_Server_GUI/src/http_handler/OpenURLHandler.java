package http_handler;

import java.awt.Desktop;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import model.HttpPropertyNames;
import model.SecureNumberGenerator;

/**
 * OpenURLHandler manages the request for opening an URL on the local machine
 * @author Jean
 */
public class OpenURLHandler implements HttpHandler {
	
	private static final Logger LOG = Logger.getLogger(OpenURLHandler.class.getName());
	private int statusCode = HttpURLConnection.HTTP_INTERNAL_ERROR;

	@Override
	public void handle(HttpExchange htex) throws IOException {
		
		int secNum = -1;
		String response = "fail";
		String urlToOpen = "";
		
		try{
			if(htex.getRequestHeaders().get(HttpPropertyNames.SEC_NUM.toString()) != null){
				secNum = Integer.parseInt(htex.getRequestHeaders().get(HttpPropertyNames.SEC_NUM.toString()).get(0));
			}
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
		
		if(htex.getRequestHeaders().containsKey(HttpPropertyNames.URL_TO_OPEN.toString())){
			urlToOpen = htex.getRequestHeaders().get(HttpPropertyNames.URL_TO_OPEN.toString()).get(0);
		}
		
		//check secure number
		if (secNum == SecureNumberGenerator.getSNum() && !urlToOpen.equals("")) {
			
			//tell the browser to open url with http -> youtu.be wouldnt work otherwise, TODO support more than just http
			if(!urlToOpen.startsWith("http://") && !urlToOpen.startsWith("https://")){
				urlToOpen = "http://" + urlToOpen;
			}
			
			if (Desktop.isDesktopSupported()) {
				try {
					
					Desktop.getDesktop().browse(new URI(urlToOpen));
					response = "successfully opened Tab " + urlToOpen;
					LOG.log(Level.INFO, "Successfully opend Tab " + urlToOpen);
					statusCode = HttpURLConnection.HTTP_OK;
				} catch (URISyntaxException e) {
					LOG.log(Level.SEVERE, "Couldnt open URL!", e);
					response = "error opening Tab" + urlToOpen;
					statusCode = HttpURLConnection.HTTP_BAD_REQUEST;
					e.printStackTrace();
				}
			}else{
				LOG.log(Level.SEVERE, "Desktop is not supported!");
				//TODO Desktop not supported
			}
			
		}else{
			statusCode = HttpURLConnection.HTTP_UNAUTHORIZED;
			LOG.log(Level.WARNING, htex.getRemoteAddress() + " tried to open an URL but send the wrong secure number");
		}

		htex.sendResponseHeaders(statusCode, response.getBytes().length);
		OutputStream os = htex.getResponseBody();
		os.write(response.getBytes());
		os.close();

	}

}
