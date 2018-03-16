package unit_tests;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map.Entry;

import model.ProgramSettings;

public class PostRequestSenderTest {
	
	private int responseCode = -1;
	
	public String sendPostRequestForResult(String route, HashMap<String, String> properties, String filePath) {

		StringBuffer response = null;

		try {

			byte[] bytearray = Files.readAllBytes(Paths.get(filePath));
			URL url = new URL("http://localhost:" + String.valueOf(ProgramSettings.PORT) + route);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=12315");
			addProperties(conn, properties);

			conn.setDoOutput(true);

			// req
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.write(bytearray);
			wr.flush();
			wr.close();
			
			//resp
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			responseCode = conn.getResponseCode();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return response.toString();

	}
	
	public int getResponseCode(){
		return responseCode;
	}
	
	private void addProperties(HttpURLConnection conn, HashMap<String, String> props) {
		for (Entry<String, String> entry : props.entrySet()) {
			conn.addRequestProperty(entry.getKey(), entry.getValue());
		}
	}
	
}
