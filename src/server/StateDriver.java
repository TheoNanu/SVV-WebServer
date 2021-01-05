package server;

public class StateDriver {
	ServerState state = ServerState.STOPPED;
	
	public synchronized void setRunning() {
		state = ServerState.RUNNING;
	}
	
	public synchronized void setStopped() {
		state = ServerState.STOPPED;
	}
	
	public synchronized void setMaintenance() {
		state = ServerState.MAINTENANCE;
	}
	
	public synchronized ServerState getState() {
		return this.state;
	}
}
