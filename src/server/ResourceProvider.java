package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class ResourceProvider {

	public byte[] readFileData(File file, int fileLength) throws FileNotFoundException{
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
	
	public void fileNotFound(PrintWriter out, OutputStream dataOut, String absolutePath) {
		File file = new File(absolutePath);
		int fileLength = (int)file.length();
		String content = "text/html";
		byte[] fileData = null;
		try {
			fileData = readFileData(file, fileLength);
		}catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
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
	
	public void sendDefaultPage(PrintWriter out, OutputStream dataOut, String defaultPath) {
		File file = new File(defaultPath);
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
	
	public void sendRequestedFile(PrintWriter out, OutputStream dataOut, String absolutePath, String pathFileNotFound) {
		File file = new File(absolutePath);
		int fileLength = (int)file.length();
		String content = "";
		if(absolutePath.endsWith("css"))
			content = "text/css";
		else
			content = "text/html";
		byte[] fileData = null;
		try {
			fileData = readFileData(file, fileLength);
		}catch(FileNotFoundException fnfe) {
			fileNotFound(out, dataOut, pathFileNotFound);
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
}
