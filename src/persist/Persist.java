package persist;

import java.io.File;

import config.Config;
import exceptions.InvalidDirectoryException;
import exceptions.InvalidPathException;
import exceptions.InvalidPortException;

public class Persist {
	
	private Config config;
	private int portNumber;
	private String rootDirectory;
	private String maintenanceDirectory;
	
	public Persist(Config config) {
		this.config = config;
	}

	public int getPortNumber() {
		String port = config.getSetting("port");
		int portAsInt = Integer.parseInt(port);
		return portAsInt;
	}
	
	public void setPortNumber(int portNumber) throws InvalidPortException{
		if(portNumber < 0)
			throw new InvalidPortException();
		else if(portNumber > 65535)
			throw new InvalidPortException();
		else if(portNumber == 0 || portNumber == 47 || portNumber == 51 || portNumber ==61 
				|| (portNumber >= 225 && portNumber <=241) || (portNumber >= 249 && portNumber <=255)
				 || portNumber == 994 || (portNumber >= 1011 && portNumber <=1020) || portNumber == 1023
				  || portNumber == 1024 || portNumber == 1027 || portNumber == 5351)
			throw new InvalidPortException();
		else
		{
			config.setSetting("port", String.valueOf(portNumber));
			this.portNumber = portNumber;
		}
	}
	
	public String getRootDir() {
		this.rootDirectory = config.getSetting("rootDirectory");
		return this.rootDirectory;
	}
	
	public void setRootDir(String rootDirPath) throws InvalidDirectoryException, InvalidPathException {
		
		boolean hasHtml = false;
		String invalidChars;
		String operatingSystem = System.getProperty("os.name");
		if (operatingSystem.startsWith("Windows")) {
		    invalidChars = "/*?\"<>|";
		} else if (operatingSystem.startsWith("Mac")) {
		    invalidChars = "/:";
		} else { // assume Unix/Linux
		    invalidChars = "/";
		}

		char[] chars = rootDirPath.toCharArray();
		for (int i = 0; i < chars.length; i++) {
		    if ((invalidChars.indexOf(chars[i]) >= 0) // OS-invalid
		        || (chars[i] < '\u0020') // ctrls
		        || (chars[i] > '\u007e' && chars[i] < '\u00a0') // ctrls
		    ) {
		        throw new InvalidPathException();
		    }
		}

		File[] files = new File(rootDirPath).listFiles();
		
		if(files == null)
			throw new InvalidDirectoryException();
		
		for(File file: files)
		{
			if(file.getName().endsWith(".html") || file.getName().endsWith(".htm"))
			{
				hasHtml = true;
			}
		}
		
		if(hasHtml == true)
		{
			this.rootDirectory = rootDirPath;
			config.setSetting("rootDirectory", rootDirPath);
		}
		else
			throw new InvalidDirectoryException();
	}
	
	public String getMaintenanceDir() {
		this.maintenanceDirectory = config.getSetting("maintenanceDirectory");
		return this.maintenanceDirectory;
	}
	
	public void setMaintenanceDir(String maintenanceDirPath) throws InvalidDirectoryException, InvalidPathException{
		boolean hasHtml = false;
		String invalidChars;
		String operatingSystem = System.getProperty("os.name");
		if (operatingSystem.startsWith("Windows")) {
		    invalidChars = "/*?\"<>|";
		} else if (operatingSystem.startsWith("Mac")) {
		    invalidChars = "/:";
		} else { // assume Unix/Linux
		    invalidChars = "/";
		}

		char[] chars = maintenanceDirPath.toCharArray();
		for (int i = 0; i < chars.length; i++) {
		    if ((invalidChars.indexOf(chars[i]) >= 0) // OS-invalid
		        || (chars[i] < '\u0020') // ctrls
		        || (chars[i] > '\u007e' && chars[i] < '\u00a0') // ctrls
		    ) {
		        throw new InvalidPathException();
		    }
		}

		File[] files = new File(maintenanceDirPath).listFiles();
		if(files == null)
			throw new InvalidDirectoryException();
		
		for(File file: files)
		{
			if(file.getName().endsWith(".html") || file.getName().endsWith(".htm"))
			{
				hasHtml = true;
			}
		}
		
		if(hasHtml == true)
		{
			this.maintenanceDirectory = maintenanceDirPath;
			config.setSetting("maintenanceDirectory", maintenanceDirPath);
		}
		else
			throw new InvalidDirectoryException();
	}
}
