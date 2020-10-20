package test;

import java.io.IOException;
import java.net.Socket;

import org.junit.After;
import org.junit.Before;


import server.HTTPServer;

public class ServerTest {
	
	HTTPServer server;
	Socket s;
	
	@Before
	public void setup() {
		s = new Socket();
		server = new HTTPServer(s);
	}


	@After
	public void tearDown() {
		server = null;
		try {
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
