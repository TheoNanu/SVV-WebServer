package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import config.Config;
import exceptions.InvalidPathException;

public class ConfigTest {
	
	 private Config config = null;
	
	@Before
	public void setup() {
		String configFilePath = ".\\config.properties";
		try {
			config = new Config(configFilePath);
		} catch (InvalidPathException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}

	@Test(expected = InvalidPathException.class)
	public void testConfigConstructorInvalidPath() throws InvalidPathException {
		String configFilePath = ".\\<config.properties";
		config = new Config(configFilePath);

	}
	
	@Test(expected = InvalidPathException.class)
	public void testConfigConstructorInvalidCharsInPath() throws InvalidPathException {
		String configFilePath = ".\\src*\\config.properties";
		config = new Config(configFilePath);

	}
	
	@Test(expected = InvalidPathException.class)
	public void testConfigConstructorNoSlashesInPath() throws InvalidPathException {
		String configFilePath = "C:Users";
		config = new Config(configFilePath);
	}
	
	@Test
	public void testConfigConstructorFileDoesNotExist() {
		String configFilePath = ".\\src\\root\\config.properties";
		try {
			config = new Config(configFilePath);
		} catch (InvalidPathException e) {
			//e.printStackTrace();
		}
		assertEquals(configFilePath, config.getConfigFileAbsolutePath());
	}
	
	@Test
	public void testConfigConstructorValidPath() {
		String configFilePath = ".\\config.properties";
		try {
			config = new Config(configFilePath);
		} catch (InvalidPathException e) {
			//e.printStackTrace();
		}
		assertEquals(configFilePath, config.getConfigFileAbsolutePath());
	}
	
	@Test(expected = InvalidPathException.class)
	public void testSaveConfigFileInvalidPath() throws InvalidPathException {
		String newConfigFilePath = "C:\\Users\\theod?\\config.properties";
		config.saveConfigFile(newConfigFilePath);
	}
	
	@Test(expected = InvalidPathException.class)
	public void testSaveConfigFileInvalidCharsInPath() throws InvalidPathException {
		String newConfigFilePath = ".\\src?\\config.properties";
		config.saveConfigFile(newConfigFilePath);;
	}
	
	@Test(expected = InvalidPathException.class)
	public void testSaveConfigFileNoSlashesInPath() throws InvalidPathException {
		String newConfigFilePath = "C:config.properties";
		config.saveConfigFile(newConfigFilePath);;
	}
	
	@Test
	public void testSaveConfigFileFileDoesNotExist() {
		String newConfigFilePath = ".\\src\\maintenance\\config.properties";
		try {
			config.saveConfigFile(newConfigFilePath);;
		} catch (InvalidPathException e) {
			//e.printStackTrace();
		}
		assertEquals(newConfigFilePath, config.getConfigFileAbsolutePath());
	}
	
	@Test
	public void testSaveConfigFileValidPath() {
		String newConfigFilePath = ".\\config.properties";
		try {
			config.saveConfigFile(newConfigFilePath);;
		} catch (InvalidPathException e) {
			//e.printStackTrace();
		}
		assertEquals(newConfigFilePath, config.getConfigFileAbsolutePath());
	}
	
}
