package server;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class HTTPServer implements Runnable{
	
	private String webRoot = null;
	static final String DEFAULT_FILE = "index.html";
	static final String FILE_NOT_FOUND = "404.html";
	static final String METHOD_NOT_SUPPORTED = "not_supported.html";
	
	static final int PORT = 8080;
	
	private Socket socket;
	
	public HTTPServer(Socket s) {
		this.socket = s;
		webRoot = System.getProperty("user.dir");
	}
	
	public static void main(String[] args) {
		
		try {
			ServerSocket server = new ServerSocket(PORT);
			System.out.println("Server started. Listening for connections.");
			
			while(true) {
				HTTPServer myServer = new HTTPServer(server.accept());
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
			
			if(!method.equals("GET"))
			{
				File file = new File(webRoot + METHOD_NOT_SUPPORTED);
				int fileLength = (int)file.length();
				String contentType = "text/html";
				byte[] fileData = readFileData(file, fileLength);
				
				out.println("HTTP/1.1 501 Not Implemented");
				out.println("Content-type: " + contentType);
				out.println("Content-length: " + fileLength);
				out.println();
				out.flush();
				
				dataOut.write(fileData, 0, fileLength);
				dataOut.flush();
			}
			else
			{
				if(fileRequested.endsWith("/"))
				{
					fileRequested += DEFAULT_FILE;
				}
				
				sendRequestedFile(out, dataOut, fileRequested);	
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
	
	public byte[] readFileData(File file, int fileLength) throws FileNotFoundException
	{
		FileInputStream fileIn = null;
		byte[] fileData = new byte[fileLength];
		
		try {
			fileIn = new FileInputStream(file);
			try {
				fileIn.read(fileData);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		finally {
			if(fileIn != null)
				try {
					fileIn.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		return fileData;
	}
	
	public void fileNotFound(PrintWriter out, OutputStream dataOut, String fileRequested) {
		File file = new File("C:\\Users\\theod\\eclipse-workspace\\WebServer\\src\\" + FILE_NOT_FOUND);
		int fileLength = (int)file.length();
		String content = "text/html";
		byte[] fileData = null;
		try {
			fileData = readFileData(file, fileLength);
		}catch(FileNotFoundException fnfe) {
			fileNotFound(out, dataOut, fileRequested);
		}
		
		out.println("HTTP/1.1 404 File Not Found");
		out.println("Content-type: " + content);
		out.println("Content-length: " + fileLength);
		out.println();
		out.flush();
		
		try {
			dataOut.write(fileData, 0, fileLength);
			dataOut.flush();
		} catch (IOException e1) {
			System.err.println("File not found!!!");
		}
	}
	
	public void sendDefaultPage(PrintWriter out, OutputStream dataOut) {
		File file = new File("C:\\Users\\theod\\eclipse-workspace\\WebServer\\src\\" + DEFAULT_FILE);
		int fileLength = (int)file.length();
		String content = "text/html";
		byte[] fileData = null;
		try {
			fileData = readFileData(file, fileLength);
		}catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
		
		out.println("HTTP/1.1 200 OK");
		out.println("Content-type: " + content);
		out.println("Content-length: " + fileLength);
		out.println();
		out.flush();
		
		try {
			dataOut.write(fileData, 0, fileLength);
			dataOut.flush();
		} catch (IOException e1) {
			System.err.println("File not found!!!");
		}
	}
	
	public void sendRequestedFile(PrintWriter out, OutputStream dataOut, String fileRequested) {
		File file = new File("C:\\Users\\theod\\eclipse-workspace\\WebServer\\src\\" + fileRequested);
		int fileLength = (int)file.length();
		String content = "text/html";
		byte[] fileData = null;
		try {
			fileData = readFileData(file, fileLength);
		}catch(FileNotFoundException fnfe) {
			fileNotFound(out, dataOut, fileRequested);
			return;
		}
		
		out.println("HTTP/1.1 200 OK");
		out.println("Content-type: " + content);
		out.println("Content-length: " + fileLength);
		out.println();
		out.flush();
		
		try {
			dataOut.write(fileData, 0, fileLength);
			dataOut.flush();
		} catch (IOException e1) {
			System.err.println("File not found!!!");
		}
	}
	
	public void changeRootDirectory(String newRootDir) {
		this.webRoot = newRootDir;
	}
	
	public String getCurrentRootDirectory() {
		return this.webRoot;
	}

}
