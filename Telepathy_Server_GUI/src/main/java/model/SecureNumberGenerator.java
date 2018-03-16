package model;

import java.security.SecureRandom;

/**
 * Generates a secure number which is used to authenticate the user later
 * @author Jean
 */
public final class SecureNumberGenerator {
	
	private static final int MIN_SEC_NUMBER = 1000;
	private static final int MAX_SEC_NUMBER = 9999;
	
	private static int sNum = 0;
	
	/**
	 * Generates a secure number between MIN_SEC_NUMBER and MAX_SEC_NUMBER
	 * @return int secure number
	 */
	public static int generateSecureNumber(){
		SecureRandom random = new SecureRandom();
		
		sNum = random.nextInt(MAX_SEC_NUMBER - MIN_SEC_NUMBER + 1) + MIN_SEC_NUMBER;
		
		return sNum;
	}
	
	public static int getSNum(){
		return sNum;
	}
	
	/**
	 * Needed for testing, returns minimal secure number
	 * @return min secure number
	 */
	public static int getMinSecureNumber(){
		return MIN_SEC_NUMBER;
	}
	
	/**
	 * Needed for testing, returns maximal secure number
	 * @return max secure number
	 */
	public static int getMaxSecureNumber(){
		return MAX_SEC_NUMBER;
	}
}
