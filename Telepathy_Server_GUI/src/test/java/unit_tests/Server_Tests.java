package unit_tests;


import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Random;

import org.junit.Test;

import model.HTTPRoutes;
import model.HttpPropertyNames;
import model.Management;
import model.ProgramSettings;
import model.SecureNumberGenerator;

public class Server_Tests {

	@Test
	public void serverStartedTest() {

		Management.getInstance().run();

		boolean isRunning = false;

		try (Socket s = new Socket("localhost", ProgramSettings.PORT)) {
			isRunning = true;
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		assertTrue(isRunning);

		Management.getInstance().resetManager();
	}

	@Test
	public void testSecureNumberGenerator() {

		int testNumber = 0;

		for (int i = 0; i < 20; i++) {
			testNumber = SecureNumberGenerator.generateSecureNumber();
			assertTrue((testNumber >= SecureNumberGenerator.getMinSecureNumber()) && (testNumber <= SecureNumberGenerator.getMaxSecureNumber()));
		}

	}

	@Test
	public void testOpenUrl() {

		GetRequestSenderTest grst = new GetRequestSenderTest();
		HashMap<String, String> props = new HashMap<String, String>();
		String result = null;
		int secNum = -1;

		Management.getInstance().run();
		secNum = SecureNumberGenerator.getSNum();

		props.put(HttpPropertyNames.SEC_NUM.toString(), String.valueOf(secNum));
		props.put(HttpPropertyNames.URL_TO_OPEN.toString(), "www.google.com");

		result = grst.sendGetRequestForResponse(HTTPRoutes.OPENURL.toString(), props);

		assertTrue((grst.getResponseCode() == HttpURLConnection.HTTP_OK) && (result.equals("successfully opened Tab http://www.google.com")));

		Management.getInstance().resetManager();
	}

	@Test
	public void testSendFile() throws UnsupportedEncodingException, FileNotFoundException, IOException, NoSuchAlgorithmException {

		final String PATH = System.getProperty("user.home") + "\\telepathyTest\\";
		final String TEST_FILE_NAME = "teleUnitTest";
		final String TEST_FILE_NAME_RECEIVED = "Received";
		final String HASH_NAME = "MD5";
		final int LENGTH_TEST_STRING = 200;
		
		PostRequestSenderTest prst = new PostRequestSenderTest();
		
		//write file which should be tested and create a hash of it
		File createFolder = new File(PATH);
		if(!createFolder.exists()){
			createFolder.mkdirs();
		}
		
		//create file to test
		File f = new File(PATH + TEST_FILE_NAME);
		f.createNewFile();
		
		//write random string into file
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(PATH + TEST_FILE_NAME), "utf-8"))) {
			writer.write(getRandomString(LENGTH_TEST_STRING));
		}
		
		//start server
		Management.getInstance().run();
		
		//set post properties
		HashMap<String,String> props = new HashMap<String,String>();
		
		props.put(HttpPropertyNames.SEC_NUM.toString(), String.valueOf(SecureNumberGenerator.getSNum()));
		props.put(HttpPropertyNames.FILE_NAME.toString(), TEST_FILE_NAME + TEST_FILE_NAME_RECEIVED);
		
		Management.getInstance().setDirectoryPathReceiveFile(PATH);
		
		//start post request for sending a file
		String result = prst.sendPostRequestForResult(HTTPRoutes.SEND_FILE.toString(), props, PATH + TEST_FILE_NAME);
		
		assertTrue(result.equals("success"));
		assertTrue(prst.getResponseCode() == HttpURLConnection.HTTP_OK);
		
		byte[] digestSourceFile = calculateHashOfFile(HASH_NAME, PATH + TEST_FILE_NAME);
		byte[] digestReceivedFile = calculateHashOfFile(HASH_NAME, PATH + TEST_FILE_NAME + TEST_FILE_NAME_RECEIVED);
		
		assertTrue(byteArraysSameContent(digestSourceFile, digestReceivedFile));
		
		Management.getInstance().resetManager();
	}

	@Test
	public void testAuthorize() {

		GetRequestSenderTest grst = new GetRequestSenderTest();
		HashMap<String, String> props = new HashMap<String, String>();
		String result = null;
		int secNum = -1;

		Management.getInstance().run();
		secNum = SecureNumberGenerator.getSNum();

		props.put(HttpPropertyNames.SEC_NUM.toString(), String.valueOf(secNum));
		props.put(HttpPropertyNames.PHONE_NAME.toString(), "Device Name");

		result = grst.sendGetRequestForResponse(HTTPRoutes.START.toString(), props);

		assertTrue((grst.getResponseCode() == HttpURLConnection.HTTP_OK) && (result.equals("success")));
		Management.getInstance().resetManager();
	}
	
	private String getRandomString(int length) {
        final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder sBuilder = new StringBuilder();
        
        Random rnd = new Random();
        
        while (sBuilder.length() < length) {
            int index = (int) (rnd.nextFloat() * CHARS.length());
            sBuilder.append(CHARS.charAt(index));
        }
        
        return sBuilder.toString();

    }
	
	private byte[] calculateHashOfFile(String hashName, String filePath){
		
		MessageDigest mdReceive = null;
		
		try{
			mdReceive = MessageDigest.getInstance(hashName);
			
			try(InputStream isReceive = Files.newInputStream(Paths.get(filePath)); DigestInputStream disReceive = new DigestInputStream(isReceive, mdReceive)){
				isReceive.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}catch(NoSuchAlgorithmException e){
			System.out.println("A hash function with the name" + hashName +" doesn't exist");
			e.printStackTrace();
		}
		
		return mdReceive.digest();
		
	}
	
	private boolean byteArraysSameContent(byte[] b1, byte[] b2){
		
		if(b1.length != b2.length){
			return false;
		}
		
		for(int i = 0; i < b1.length; i++){
			if(b1[i] != b2[i]){
				return false;
			}
		}
		
		return true;
	}
}
