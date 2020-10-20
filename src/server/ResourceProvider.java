package server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class ResourceProvider {

	public byte[] readFileData(File file, int fileLength) throws FileNotFoundException{
		return null;
	}
	
	public void fileNotFound(PrintWriter out, OutputStream dataOut) {
		
	}
	
	public void sendDefaultPage(PrintWriter out, OutputStream dataOut) {
		
	}
	
	public void sendRequestedFile(PrintWriter out, OutputStream dataOut, String fileRequested) {
		
	}
}
