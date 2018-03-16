package unit_tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;

import model.ProgramSettings;

public class GetRequestSenderTest {
	
	private int responseCode = -1;
	
	public String sendGetRequestForResponse(String route, HashMap<String, String> properties) {

		StringBuilder result = new StringBuilder();
		URL url;
		
		try {
			
			url = new URL("http://localhost:" + String.valueOf(ProgramSettings.PORT) + route);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("GET");
			addProperties(conn, properties);
			
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			rd.close();
			
			responseCode = conn.getResponseCode();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result.toString();
	}
	
	public int getResponseCode(){
		return responseCode;
	}
	
	private void addProperties(HttpURLConnection conn, HashMap<String,String> props){
		
		for(Entry<String, String> entry : props.entrySet()){
			conn.addRequestProperty(entry.getKey(), entry.getValue());
		}
		
	}

}
