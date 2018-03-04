package model;

import java.security.SecureRandom;

public final class SecureNumberGenerator {
	
	private static final int MIN_SEC_NUMBER = 1000;
	private static final int MAX_SEC_NUMBER = 9999;
	
	private static int sNum = 0;
	
	public static int generateSecureNumber(){
		SecureRandom random = new SecureRandom();
		
		sNum = random.nextInt(MAX_SEC_NUMBER - MIN_SEC_NUMBER + 1) + MIN_SEC_NUMBER;
		
		return sNum;
	}
	
	public static int getSNum(){
		return sNum;
	}
	
}
