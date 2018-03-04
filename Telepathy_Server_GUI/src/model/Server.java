package model;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpServer;

import http_handler.AuthorizeStartHandler;
import http_handler.OpenURLHandler;
import http_handler.SendFileHandler;

public class Server {

	private static Server s;
	private int port;
	private static final Logger log = Logger.getLogger(Server.class.getName());
	private SendFileHandler sfh = new SendFileHandler();
	private HttpServer server;

	private Server(int port) {
		this.port = port;
	}

	public static Server getInstance(int port) {

		if (s == null) {
			s = new Server(port);
		}
		return s;
	}

	// only access if getInstance(int port) was called before
	public static Server getInstance() {
		return s;
	}

	public void startHttpServer(){
		
		try{
			server = HttpServer.create(new InetSocketAddress(port), 0);
			server.createContext(HTTPRoutes.OPENURL.toString(), new OpenURLHandler());
			server.createContext(HTTPRoutes.SEND_FILE.toString(), sfh);
			server.createContext(HTTPRoutes.START.toString(), new AuthorizeStartHandler());
			server.setExecutor(null);
			server.start();
			log.log(Level.INFO, "Server started on port " + port);
		}catch(IOException e){
			log.log(Level.SEVERE, "Could not create Server on Port " + port, e);
			e.printStackTrace();
		}
		
	}
	
	public SendFileHandler getSendFileHandler(){
		return sfh;
	}
	
	public void closeHttpServer(){
		server.stop(0);
	}

}
