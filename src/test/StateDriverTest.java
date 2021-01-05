package test;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.ServerState;
import server.StateDriver;

public class StateDriverTest {
	
	private StateDriver sd;
	
	@Before
	public void setup() {
		sd = new StateDriver();
	}

	@Test
	public void testRunning() {
		sd.setRunning();
		ServerState s = sd.getState();
		assertTrue(s.equals(ServerState.RUNNING));
	}
	
	@Test
	public void testMaintenance() {
		sd.setMaintenance();
		ServerState s = sd.getState();
		assertTrue(s.equals(ServerState.MAINTENANCE));
	}
	
	@Test
	public void testStopped() {
		sd.setStopped();
		ServerState s = sd.getState();
		assertTrue(s.equals(ServerState.STOPPED));
	}
	
	@After
	public void tearDown() {
		
	}
	
	

}
