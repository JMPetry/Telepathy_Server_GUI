package http_handler;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import model.HttpPropertyNames;
import model.SecureNumberGenerator;

public class SendFileHandler implements HttpHandler {

	private static final String FILE_NAME_IN_HEADER = "Filename";
	private static final Logger LOG = Logger.getLogger(SendFileHandler.class.getName());
	private String filePath = System.getProperty("user.home") + "/Desktop";
	private int statusCode = HttpURLConnection.HTTP_INTERNAL_ERROR;

	@Override
	public void handle(HttpExchange htex) throws IOException {

		int bufferedByte = 0;
		int secNum = -1;
		String response = "fail";
		String fileName = "";
		OutputStream outStream = null;

		try {
			secNum = Integer.parseInt(htex.getRequestHeaders().get(HttpPropertyNames.SEC_NUM.toString()).get(0));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		if (secNum == SecureNumberGenerator.getSNum()) {
			
			try {
				List<String> fileNameFromReqHeader = htex.getRequestHeaders().get(FILE_NAME_IN_HEADER);
				fileName = fileNameFromReqHeader.get(0);

				InputStream in = new BufferedInputStream(htex.getRequestBody());

				File targetFile = new File(filePath + fileName);
				outStream = new FileOutputStream(targetFile);

				while ((bufferedByte = in.read()) != -1) {
					outStream.write(bufferedByte);
				}

				response = "success";
				statusCode = HttpURLConnection.HTTP_OK;
				LOG.log(Level.INFO, "Successfully received file " + FILE_NAME_IN_HEADER);

			} catch (NullPointerException e) {
				LOG.log(Level.SEVERE, "Message doesnt contain " + FILE_NAME_IN_HEADER + " while trying to send file!",
						e);
				e.printStackTrace();
			} catch (IOException i) {
				LOG.log(Level.SEVERE, "Couldnt write File!", i);
				i.printStackTrace();
			} finally {
				try {
					outStream.close();
				} catch (IOException a) {
					a.printStackTrace();
				}
			}
			
		}else{
			statusCode = HttpURLConnection.HTTP_FORBIDDEN;
			LOG.log(Level.WARNING, htex.getRemoteAddress() + " tried to open an URL but send the wrong secure number");
		}

		htex.sendResponseHeaders(statusCode, response.getBytes().length);
		OutputStream os = htex.getResponseBody();
		os.write(response.getBytes());
		os.close();

	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
