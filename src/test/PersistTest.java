package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import config.Config;
import exceptions.InvalidDirectoryException;
import exceptions.InvalidPathException;
import exceptions.InvalidPortException;
import persist.Persist;

public class PersistTest {
	
	private Persist persist = null;
	private Config config;
	
	@Before
	public void setup() {
		try {
			this.config = new Config(".\\config.properties");
		} catch (InvalidPathException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.persist = new Persist(config);
	}

	@Test(expected = InvalidPortException.class)
	public void testSetPortNumberInvalidPortExceedLowBound() throws InvalidPortException {
		persist.setPortNumber(-1);

	}
	
	@Test(expected = InvalidPortException.class)
	public void testSetPortNumberInvalidPortExceedUpBound() throws InvalidPortException {
		persist.setPortNumber(66000);
	}
	
	@Test(expected = InvalidPortException.class)
	public void testSetPortNumberInvalidPortReservedPort() throws InvalidPortException {
		persist.setPortNumber(47);
	}
	
	@Test
	public void testSetPortNumberValidPort() throws InvalidPortException{
		int portNumber = 8081;
		persist.setPortNumber(portNumber);
		assertEquals(persist.getPortNumber(), portNumber);
	}
	
	@Test(expected = InvalidPathException.class)
	public void testSetRootDirInvalidPath() throws InvalidDirectoryException, InvalidPathException {
		String rootDirPath = "C:\\Users*";
		persist.setRootDir(rootDirPath);
	}
	
	@Test(expected = InvalidDirectoryException.class)
	public void testSetRootDirInvalidDirectoryException() throws InvalidDirectoryException, InvalidPathException {
		String rootDirPath = "C:\\Users";
		persist.setRootDir(rootDirPath);
	
	}
	
	@Test
	public void testSetRootDirValidPathAndDir() {
		String rootDirPath = ".\\src\\root";
		try {
			persist.setRootDir(rootDirPath);
		} catch (InvalidDirectoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidPathException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(rootDirPath, persist.getRootDir());
	}
	
	@Test(expected = InvalidPathException.class)
	public void testSetMaintenanceDirInvalidPath() throws InvalidDirectoryException, InvalidPathException {
		String maintenanceDirPath = "C:\\Users*";
		persist.setMaintenanceDir(maintenanceDirPath);
	}
	
	@Test(expected = InvalidDirectoryException.class)
	public void testSetMaintenanceDirInvalidDirectoryException() throws InvalidDirectoryException, InvalidPathException {
		String maintenanceDirPath = "C:\\Users";
		persist.setMaintenanceDir(maintenanceDirPath);

	}
	
	@Test
	public void testSetMaintenanceDirValidPathAndDir() {
		String maintenanceDirPath = ".\\src\\maintenance";
		try {
			persist.setMaintenanceDir(maintenanceDirPath);
		} catch (InvalidDirectoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidPathException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(persist.getMaintenanceDir(), maintenanceDirPath);
	}
}
