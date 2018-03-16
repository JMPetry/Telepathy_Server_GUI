package model;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The IPManager gets the IP of the local computer and formats it to a IP with the correct port
 * @author Jean
 */
public class IPManager {

	private static Logger LOG = Logger.getLogger(IPManager.class.getName());
	
	/**
	 * Appends the port to the IP address of the local computer. This IP is used to send requests to later
	 * @return String of the IP and port to send requests to
	 */
	public static String getIPWithPortAsString() {

		String ipWithPort = getOwnIpAddress().toString();
		
		ipWithPort += (":" + String.valueOf(ProgramSettings.PORT));
		
		//remove / before actual IP
		ipWithPort = ipWithPort.substring(1, ipWithPort.length());
		
		return ipWithPort;
	}
	
	/**
	 * Tries to get the local IP and ignores IPs of VMs by looking at the MAC addresses of them
	 * @return InetAddress of the computer in the local network
	 */
	public static InetAddress getOwnIpAddress() {

		final String[] COMMON_VM_MAC_ADDRESSES = { "00-50-56", "00-0C-29", "00-05-69", "00-03-FF", "00-1C-42",
				"00-0F-4B", "00-16-3E", "08-00-27" };
		final String COMMON_LOCAL_IP_ADDR_START = "/192.168.";
		InetAddress ipAddr = null;
		final List<String> list = Arrays.asList(COMMON_VM_MAC_ADDRESSES);

		try {
			for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {

				if (networkInterface.getHardwareAddress() != null) {

					Enumeration<InetAddress> as = networkInterface.getInetAddresses();

					while (as.hasMoreElements()) {
						InetAddress inetAddr = as.nextElement();
						String s = byteArrayToString(networkInterface.getHardwareAddress());
						if (inetAddr.toString().startsWith(COMMON_LOCAL_IP_ADDR_START) && !list.contains(s) && s.contains("-")) {
							ipAddr = inetAddr;
						}
					}
				}
			}
		} catch (SocketException s) {
			LOG.log(Level.SEVERE, "Coudln't get IP", s);
		}

		return ipAddr;
	}
	
	/**
	 * Converts a byteArrayToString
	 * @param barr byte array which shall be converted
	 * @return String which contains the content of the byte array
	 */
	private static String byteArrayToString(byte[] barr) {
		String s = "";

		for (int i = 0; i < barr.length; i++) {
			s += barr[i];
		}

		return s;
	}
}
