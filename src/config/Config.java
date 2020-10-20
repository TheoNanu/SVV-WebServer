package config;

import java.io.File;

import exceptions.InvalidPathException;

public class Config {
	
	private File configFile = null;

	// if the file doesn't exist create one
	public Config(String configFilePath) throws InvalidPathException{
		this.configFile = new File(configFilePath);
	}
	
	// if the file doesn't exist create one
	public void saveConfigFile(String newConfigFilePath) throws InvalidPathException{
		
	}
	
	public String getSetting(String key) {
		return null;
	}
	
	public void setSetting(String key, String value) {
		
	}
	
	public File getConfigFile() {
		return this.configFile;
	}
	
	public String getConfigFileAbsolutePath() {
		return this.configFile.getAbsolutePath();
	}
}
