package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import exceptions.InvalidDirectoryException;
import exceptions.InvalidPathException;
import exceptions.InvalidPortException;
import persist.Persist;

public class PersistTest {
	
	private Persist persist = null;
	
	@Before
	public void setup() {
		this.persist = new Persist();
	}

	@Test(expected = InvalidPortException.class)
	public void testSetPortNumberInvalidPortExceedLowBound() {
		try {
			persist.setPortNumber(-1);
		} catch (InvalidPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test(expected = InvalidPortException.class)
	public void testSetPortNumberInvalidPortExceedUpBound() {
		try {
			persist.setPortNumber(66000);
		} catch (InvalidPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test(expected = InvalidPortException.class)
	public void testSetPortNumberInvalidPortReservedPort() {
		try {
			persist.setPortNumber(80);
		} catch (InvalidPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSetPortNumberValidPort() {
		int portNumber = 8081;
		try {
			persist.setPortNumber(portNumber);
		} catch (InvalidPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(persist.getPortNumber(), portNumber);
	}
	
	@Test(expected = InvalidPathException.class)
	public void testSetRootDirInvalidPath() {
		String rootDirPath = "";
		try {
			persist.setRootDir(rootDirPath);
		} catch (InvalidDirectoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidPathException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test(expected = InvalidDirectoryException.class)
	public void testSetRootDirInvalidDirectoryException() {
		String rootDirPath = "";
		try {
			persist.setRootDir(rootDirPath);
		} catch (InvalidDirectoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidPathException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSetRootDirValidPathAndDir() {
		String rootDirPath = "";
		try {
			persist.setRootDir(rootDirPath);
		} catch (InvalidDirectoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidPathException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(persist.getRootDir(), rootDirPath);
	}
	
	@Test(expected = InvalidPathException.class)
	public void testSetMaintenanceDirInvalidPath() {
		String maintenanceDirPath = "";
		try {
			persist.setMaintenanceDir(maintenanceDirPath);
		} catch (InvalidDirectoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidPathException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test(expected = InvalidDirectoryException.class)
	public void testSetMaintenanceDirInvalidDirectoryException() {
		String maintenanceDirPath = "";
		try {
			persist.setMaintenanceDir(maintenanceDirPath);
		} catch (InvalidDirectoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidPathException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSetMaintenanceDirValidPathAndDir() {
		String maintenanceDirPath = "";
		try {
			persist.setRootDir(maintenanceDirPath);
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
