package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import exceptions.InvalidRequestException;
import server.RequestHandler;
import server.ResourceProvider;

public class RequestHandlerTest {
	
	private RequestHandler reqHandler = null;
	private ResourceProvider prov = null;
	
	@Before
	public void setup() {
		this.prov = new ResourceProvider();
		this.reqHandler = new RequestHandler(this.prov);
	}

	@Test(expected = InvalidRequestException.class)
	public void testHandleRequestPostRequest() {
		String request = "POST / HTTP/1.1";
		try {
			reqHandler.handleRequest(request);
		} catch (InvalidRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test(expected = InvalidRequestException.class)
	public void testHandleRequestUpdateRequest() {
		String request = "UPDATE / HTTP/1.1";
		try {
			reqHandler.handleRequest(request);
		} catch (InvalidRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test(expected = InvalidRequestException.class)
	public void testHandleRequestDeleteRequest() {
		String request = "DELETE / HTTP/1.1";
		try {
			reqHandler.handleRequest(request);
		} catch (InvalidRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testHandleRequestValidRequest() {
		String request = "GET / HTTP/1.1";
		try {
			reqHandler.handleRequest(request);
		} catch (InvalidRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(request, reqHandler.getRecvRequest());
	}

}
