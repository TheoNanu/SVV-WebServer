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
	private Config config = null;
	private Persist persist = null;
	private RequestHandler req = null;
	
	private Socket socket;
	
	public HTTPServer(Socket s, Config c, Persist p) {
		this.socket = s;
		this.prov = new ResourceProvider();
		this.config = c;
		this.req = new RequestHandler();
		this.persist = p;
	}
	
	public static void main(String[] args) {
		
		try {
			Config config = null;
			try {
				config = new Config("C:\\Users\\theod\\config.properties");
			} catch (InvalidPathException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Persist p = new Persist(config);
			try {
				p.setPortNumber(8081);
			} catch (InvalidPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				p.setRootDir("C:\\Users\\theod\\eclipse-workspace\\WebServer\\src");
			} catch (InvalidDirectoryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidPathException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int port = p.getPortNumber();
			ServerSocket server = new ServerSocket(port);
			System.out.println("Server started. Listening for connections on  port " + port);
			
			while(true) {
				HTTPServer myServer = new HTTPServer(server.accept(), config, p);
				System.out.println("Connection received.");
				
				Thread thread = new Thread(myServer);
				thread.start();
			}
		}
		catch(IOException e){
			System.err.println("Server connection error: " + e.getMessage());
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
			
			try {
				this.req.handleRequest(input);
				if(fileRequested.endsWith("/"))
				{
					fileRequested += DEFAULT_FILE;
				}
				
				String absolutePath = this.config.getSetting("rootDirectory") + "\\" +  fileRequested;
				String pathFileNotFound = this.config.getSetting("rootDirectory") + "\\" +  FILE_NOT_FOUND;
				
				this.prov.sendRequestedFile(out, dataOut, absolutePath, pathFileNotFound);
			} catch (InvalidRequestException e) {
				// TODO Auto-generated catch block
				File file = new File(this.config.getSetting("rootDirectory") + "\\" +  METHOD_NOT_SUPPORTED);
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
}

