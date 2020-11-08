package test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.junit.Before;
import org.junit.Test;

import config.Config;
import exceptions.InvalidPathException;
import server.ResourceProvider;

public class ResourceProviderTest {
	
	private ResourceProvider prov = null;
	private Config config = null;
	
	@Before
	public void setup() {
		this.prov = new ResourceProvider();
		try {
			this.config = new Config("C:\\Users\\theod\\config.properties");
		} catch (InvalidPathException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test(expected = FileNotFoundException.class)
	public void testReadFileDataInexistentFile() throws FileNotFoundException{
		File file = new File(" ");
		int fileLength = (int)file.length();
		prov.readFileData(file, fileLength);
	}
	
	@Test
	public void testContentReadFromFile() {
		File file = new File("C:\\Users\\theod\\eclipse-workspace\\WebServer\\src\\test.txt");
		int fileLength = (int)file.length();
		byte expected[] = "This is a test file.".getBytes();
		try {
			byte[] read = prov.readFileData(file, fileLength);
			assertArrayEquals(expected, read);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test(expected=NullPointerException.class)
	public void testReadFileDataNullFileReference() {
		File file = null;
		int fileLength = (int)file.length();
		try {
			byte read[] = prov.readFileData(file, fileLength);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected=NullPointerException.class)
	public void testReadFileDataPrintWriterNullReference() {
		String fileNotFound = "C:\\Users\\theod\\eclipse-workspace\\WebServer\\src\\404.html";
		PrintWriter out = null;
		OutputStream dataOut = null;
		OutputStream otp = new ByteArrayOutputStream();
		dataOut = new BufferedOutputStream(otp);
		prov.fileNotFound(out, dataOut, fileNotFound);
	}
	
	@Test(expected=NullPointerException.class)
	public void  testReadFileDataOutputStreamNullReference() {
		String fileNotFound = "C:\\Users\\theod\\eclipse-workspace\\WebServer\\src\\404.html";
		OutputStream otp = new ByteArrayOutputStream();
		PrintWriter out = new PrintWriter(otp);
		prov.fileNotFound(out, null, fileNotFound);
	}
	
	@Test
	public void testContentOfDefaultFile() {
		File file = new File("C:\\Users\\theod\\eclipse-workspace\\WebServer\\src\\index.html");
		int fileLength = (int)file.length();
		byte[] fileContent = new byte[fileLength];
		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
			try {
				fileInput.read(fileContent);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(fileInput != null) {
				try {
					fileInput.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		OutputStream otp1 = new ByteArrayOutputStream();
		OutputStream otp2 = new ByteArrayOutputStream();
		OutputStream dataOut = new BufferedOutputStream(otp1);
		PrintWriter out = new PrintWriter(otp2);
		prov.sendDefaultPage(out, dataOut);
		String dataAsString = new String(fileContent);
		assertTrue(dataAsString.equals(otp1.toString()));
	}
	
	@Test
	public void testContentOfRequestedFile() {
		String reqFile = "C:\\Users\\theod\\eclipse-workspace\\WebServer\\src\\test.txt";
		String fileNotFound = "C:\\Users\\theod\\eclipse-workspace\\WebServer\\src\\404.html";
		File file = new File(reqFile);
		int fileLength = (int)file.length();
		byte[] fileContent = new byte[fileLength];
		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
			try {
				fileInput.read(fileContent);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(fileInput != null) {
				try {
					fileInput.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		OutputStream otp1 = new ByteArrayOutputStream();
		OutputStream otp2 = new ByteArrayOutputStream();
		OutputStream dataOut = new BufferedOutputStream(otp1);
		PrintWriter out = new PrintWriter(otp2);
		prov.sendRequestedFile(out, dataOut, "C:\\Users\\theod\\eclipse-workspace\\WebServer\\src\\test.txt", fileNotFound);
		String dataAsString = new String(fileContent);
		assertTrue(dataAsString.equals(otp1.toString()));
	}
	
	@Test
	public void testContentOfErrorFile() {
		String reqFile = "C:\\Users\\theod\\eclipse-workspace\\WebServer\\src\\404.html";
		File file = new File(reqFile);
		int fileLength = (int)file.length();
		byte[] fileContent = new byte[fileLength];
		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
			try {
				fileInput.read(fileContent);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(fileInput != null) {
				try {
					fileInput.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		OutputStream otp1 = new ByteArrayOutputStream();
		OutputStream otp2 = new ByteArrayOutputStream();
		OutputStream dataOut = new BufferedOutputStream(otp1);
		PrintWriter out = new PrintWriter(otp2);
		prov.fileNotFound(out, dataOut, reqFile);
		String dataAsString = new String(fileContent);
		assertTrue(dataAsString.equals(otp1.toString()));
	}

}
