package server;

public class ServerWrapper implements Runnable{
	
	private StateDriver stateDriver;
	
	public ServerWrapper(StateDriver s) {
		this.stateDriver = s;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		HTTPServer.startServer(stateDriver);
	}

}
