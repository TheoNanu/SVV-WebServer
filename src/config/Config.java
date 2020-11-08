package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import exceptions.InvalidPathException;

public class Config {
	
	private String configFile = null;
	File file = null;
	private String rootDir = "";
	private String portNumber = "";
	private String maintenanceDir = "";

	// if the file doesn't exist create one
	public Config(String configFilePath) throws InvalidPathException{
		String invalidChars;
		String operatingSystem = System.getProperty("os.name");
		if (operatingSystem.startsWith("Windows")) {
		    invalidChars = "/*?\"<>|";
		} else if (operatingSystem.startsWith("Mac")) {
		    invalidChars = "/:";
		} else { // assume Unix/Linux
		    invalidChars = "/";
		}

		char[] chars = configFilePath.toCharArray();
		for (int i = 0; i < chars.length; i++) {
		    if ((invalidChars.indexOf(chars[i]) >= 0) // OS-invalid
		        || (chars[i] < '\u0020') // ctrls
		        || (chars[i] > '\u007e' && chars[i] < '\u00a0') // ctrls
		    ) {
		        throw new InvalidPathException();
		    }
		}
		if(configFilePath.contains("\\") == false)
			throw new InvalidPathException();
		
		this.configFile = configFilePath;
		this.file = new File(this.configFile);
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// if the file doesn't exist create one
	public void saveConfigFile(String newConfigFilePath) throws InvalidPathException{
		String invalidChars;
		String operatingSystem = System.getProperty("os.name");
		if (operatingSystem.startsWith("Windows")) {
		    invalidChars = "/*?\"<>|";
		} else if (operatingSystem.startsWith("Mac")) {
		    invalidChars = "/:";
		} else { // assume Unix/Linux
		    invalidChars = "/";
		}

		char[] chars = newConfigFilePath.toCharArray();
		for (int i = 0; i < chars.length; i++) {
		    if ((invalidChars.indexOf(chars[i]) >= 0) // OS-invalid
		        || (chars[i] < '\u0020') // ctrls
		        || (chars[i] > '\u007e' && chars[i] < '\u00a0') // ctrls
		    ) {
		        throw new InvalidPathException();
		    }
		}
		
		if(newConfigFilePath.contains("\\") == false)
			throw new InvalidPathException();
		
		this.configFile = newConfigFilePath;
		this.file = new File(this.configFile);
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getSetting(String key) {
		String value = null;
		try (InputStream input = new FileInputStream(this.configFile)) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            value = prop.getProperty(key);
            this.rootDir = prop.getProperty("rootDirectory");
            this.portNumber = prop.getProperty("port");
            this.maintenanceDir = prop.getProperty("maintenanceDirectory");
            
            input.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
		
		System.out.println("Value is " + value);
		return value;
	}
	
	public void setSetting(String key, String value) {
		try (InputStream input = new FileInputStream(this.configFile)) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            this.rootDir = prop.getProperty("rootDirectory");
            this.portNumber = prop.getProperty("port");
            this.maintenanceDir = prop.getProperty("maintenanceDirectory");
            
            input.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
		try (OutputStream output = new FileOutputStream(this.configFile)) {

            Properties prop = new Properties();
            
            // set the properties value
            prop.setProperty("rootDirectory", this.rootDir);
            prop.setProperty("port", this.portNumber);
            prop.setProperty("maintenanceDirectory", this.maintenanceDir);
            prop.setProperty(key, value);

            // save properties to project root folder
            prop.store(output, null);

            System.out.println(prop);
            
            output.close();

        } catch (IOException io) {
            io.printStackTrace();
        }
	}
	
	public String getConfigFileAbsolutePath() {
		return this.configFile;
	}
	
	public void closeFile()
	{
		
	}
}
