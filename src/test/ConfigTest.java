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
		String configFilePath = "";
		try {
			config = new Config(configFilePath);
		} catch (InvalidPathException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test(expected = InvalidPathException.class)
	public void testConfigConstructorInvalidPath() {
		String configFilePath = "";
		try {
			config = new Config(configFilePath);
		} catch (InvalidPathException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = InvalidPathException.class)
	public void testConfigConstructorInvalidCharsInPath() {
		String configFilePath = "C:/Users/prog_files*/config.properties";
		try {
			config = new Config(configFilePath);
		} catch (InvalidPathException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = InvalidPathException.class)
	public void testConfigConstructorNoSlashesInPath() {
		String configFilePath = "C:config.properties";
		try {
			config = new Config(configFilePath);
		} catch (InvalidPathException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testConfigConstructorFileDoesNotExist() {
		String configFilePath = "C:/Users/config.properties";
		try {
			config = new Config(configFilePath);
		} catch (InvalidPathException e) {
			e.printStackTrace();
		}
		assertEquals(configFilePath, config.getConfigFileAbsolutePath());
	}
	
	@Test
	public void testConfigConstructorValidPath() {
		String configFilePath = "C:/some/good/path";
		try {
			config = new Config(configFilePath);
		} catch (InvalidPathException e) {
			e.printStackTrace();
		}
		assertEquals(configFilePath, config.getConfigFile().getAbsolutePath());
	}
	
	@Test(expected = InvalidPathException.class)
	public void testSaveConfigFileInvalidPath() {
		String newConfigFilePath = "";
		try {
			config.saveConfigFile(newConfigFilePath);
		} catch (InvalidPathException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = InvalidPathException.class)
	public void testSaveConfigFileInvalidCharsInPath() {
		String newConfigFilePath = "C:/Users/prog_files*/config.properties";
		try {
			config.saveConfigFile(newConfigFilePath);;
		} catch (InvalidPathException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = InvalidPathException.class)
	public void testSaveConfigFileNoSlashesInPath() {
		String newConfigFilePath = "C:config.properties";
		try {
			config.saveConfigFile(newConfigFilePath);;
		} catch (InvalidPathException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSaveConfigFileFileDoesNotExist() {
		String newConfigFilePath = "C:/Users/config.properties";
		try {
			config.saveConfigFile(newConfigFilePath);;
		} catch (InvalidPathException e) {
			e.printStackTrace();
		}
		assertEquals(newConfigFilePath, config.getConfigFileAbsolutePath());
	}
	
	@Test
	public void testSaveConfigFileValidPath() {
		String newConfigFilePath = "C:/some/good/path";
		try {
			config.saveConfigFile(newConfigFilePath);;
		} catch (InvalidPathException e) {
			e.printStackTrace();
		}
		assertEquals(newConfigFilePath, config.getConfigFile().getAbsolutePath());
	}
	
}
