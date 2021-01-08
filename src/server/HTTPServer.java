package server;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

import config.Config;
import exceptions.InvalidDirectoryException;
import exceptions.InvalidPathException;
import exceptions.InvalidPortException;
import exceptions.InvalidRequestException;
import persist.Persist;

public class HTTPServer implements Runnable{
	
	static final String DEFAULT_FILE = "index.html";
	static final String FILE_NOT_FOUND = "404.html";
	static final String METHOD_NOT_SUPPORTED = "not_supported.html";
	private ResourceProvider prov = null;
	private static Config config = null;
	private static Persist persist = null;
	private RequestHandler req = null;
	private StateDriver stateDriver;
	private volatile static boolean running = false;
	
	private Socket socket;
	
	public static void initConfig() {
		try {
			config = new Config(".\\config.properties");
		} catch (InvalidPathException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		persist = new Persist(config);
	}
	
	public HTTPServer(Socket s, StateDriver sd) {
		socket = s;
		this.prov = new ResourceProvider();
		this.req = new RequestHandler();
		stateDriver = sd;
	
	}
	
	public static void startServer(StateDriver sd) {
		
		ServerSocket server = null;
		
		try {
			int port = persist.getPortNumber();
			server = new ServerSocket(port);
			System.out.println("Server started. Listening for connections on  port " + port);
			
			while(running) {
				HTTPServer myServer = new HTTPServer(server.accept(), sd);
				System.out.println("Connection received.");
				
				Thread thread = new Thread(myServer);
				thread.start();
			}
		}
		catch(IOException e){
			System.err.println("Server connection error: " + e.getMessage());
		}
		finally {
			System.out.println("Thread ending...");
			if(server != null)
				try {
					server.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			/*try {
				socket.close();
				System.out.println("I just closed the socket for no reason!!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
		
	}

	@Override
	public void run() {
		
		BufferedReader in = null;
		PrintWriter out = null;
		BufferedOutputStream dataOut = null;
		
		String fileRequested = null;
		
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
			dataOut = new BufferedOutputStream(socket.getOutputStream());
			
			String input = in.readLine();
			System.out.println(input);
			String method = "";
			
			if(input != null)
			{
				StringTokenizer parse = new StringTokenizer(input);
				method = parse.nextToken().toUpperCase();
				fileRequested = parse.nextToken().toLowerCase();
			}
			
			//System.out.println(HTTPServer.getState().toString());
			ServerState	s = this.stateDriver.getState();
			
			if(s == ServerState.RUNNING)
			{
				System.out.println("Running");
				try {
					this.req.handleRequest(input);
					if(fileRequested.endsWith("/"))
					{
						fileRequested += DEFAULT_FILE;
					}
					
					String absolutePath = HTTPServer.config.getSetting("rootDirectory") + "\\" +  fileRequested;
					String pathFileNotFound = HTTPServer.config.getSetting("rootDirectory") + "\\" +  FILE_NOT_FOUND;
					
					this.prov.sendRequestedFile(out, dataOut, absolutePath, pathFileNotFound);
				} catch (InvalidRequestException e) {
					// TODO Auto-generated catch block
					File file = new File(HTTPServer.config.getSetting("rootDirectory") + "\\" +  METHOD_NOT_SUPPORTED);
					int fileLength = (int)file.length();
					String contentType = "text/html";
					byte[] fileData = this.prov.readFileData(file, fileLength);
					
					out.println("HTTP/1.1 501 Not Implemented");
					out.println("Content-type: " + contentType);
					out.println("Content-length: " + fileLength);
					out.println();
					out.flush();
					
					dataOut.write(fileData, 0, fileLength);
					dataOut.flush();
				}
			}
			else if(s == ServerState.MAINTENANCE)
			{
				System.out.println("Maintenance");
				String absolutePath = HTTPServer.config.getSetting("maintenanceDirectory") + "\\maintenance.html";
				String pathFileNotFound = HTTPServer.config.getSetting("rootDirectory") + "\\" +  FILE_NOT_FOUND;
				this.prov.sendRequestedFile(out, dataOut, absolutePath, pathFileNotFound);
			}
			
		}
		catch(IOException ioe) {
			System.err.println("I\\O Exception");
			ioe.printStackTrace();
		}
		finally {
			try {
				in.close();
				out.close();
				dataOut.close();
				socket.close();
			}
			catch(Exception e) {
				System.err.println("Error while releasing the resources");
			}
		}
		
	}
	
	/*public static synchronized void changeToMaintenanceMode() {
		System.out.println("Switch to maintenance");
		state = ServerState.MAINTENANCE;
		System.out.println(state);
	}
	
	public static synchronized void changeToRunningState() {
			state = ServerState.RUNNING;
	}
	
	public static synchronized void changeToStoppedState() {
			state = ServerState.STOPPED;
	}
	
	public static synchronized ServerState getState() {
		return state;
	}*/
	
	public static String get(String property)
	{
		if(property.equals("port"))
			return Integer.toString(persist.getPortNumber());
		else if(property.equals("rootDirectory"))
			return persist.getRootDir();
		else if(property.equals("maintenanceDirectory"))
			return persist.getMaintenanceDir();
		else
			return "";
	}
	
	public static void set(String property, String value) throws NumberFormatException, InvalidPortException, InvalidDirectoryException, InvalidPathException 
	{
		if(property.equals("port"))
			
				persist.setPortNumber(Integer.parseInt(value));
			
		else if(property.equals("rootDirectory"))
			
				persist.setRootDir(value);
			
		else if(property.equals("maintenanceDirectory"))
			
				persist.setMaintenanceDir(value);
			
	}
	
	public static void terminate() {
		running = false;
	}
	
	public static void start() {
		running = true;
	}
	
	public static boolean isRunning() {
		return running;
	}
}

