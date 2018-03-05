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

public class AuthorizeStartHandler implements HttpHandler {

	private static final Logger LOG = Logger.getLogger(AuthorizeStartHandler.class.getName());
	private int statusCode = HttpURLConnection.HTTP_INTERNAL_ERROR;

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
		
		//send phonename
		if(htex.getRequestHeaders().get(HttpPropertyNames.PHONE_NAME.toString()) != null){
			phoneName = htex.getRequestHeaders().get(HttpPropertyNames.PHONE_NAME.toString()).get(0);
			Management.getInstance().setPhoneName(phoneName);
		}

		if (secNum == SecureNumberGenerator.getSNum()) {
			
			Management.getInstance().setAuthorized(true);
			
			LOG.log(Level.INFO, htex.getRemoteAddress().toString() + " logged on");
			response = "success";
			statusCode = HttpURLConnection.HTTP_OK;
		} else {
			LOG.log(Level.WARNING, htex.getRemoteAddress().toString() + " tried to log on with wrong security number");
		}

		htex.sendResponseHeaders(statusCode, response.getBytes().length);
		OutputStream os = htex.getResponseBody();
		os.write(response.getBytes());
		os.close();

	}
}
