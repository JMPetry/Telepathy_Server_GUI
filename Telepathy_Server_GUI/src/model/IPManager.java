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

public class IPManager {

	private static Logger LOG = Logger.getLogger(IPManager.class.getName());

	public static String getIPWithPortAsString() {

		String ipWithPort = getOwnIpAddress().toString();
		
		ipWithPort += (":" + String.valueOf(ProgramSettings.PORT));
		
		//remove / before actual IP
		ipWithPort = ipWithPort.substring(1, ipWithPort.length());
		
		return ipWithPort;
	}

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

	private static String byteArrayToString(byte[] barr) {
		String s = "";

		for (int i = 0; i < barr.length; i++) {
			s += barr[i];
		}

		return s;
	}
}
