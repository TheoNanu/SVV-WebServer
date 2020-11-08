package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import exceptions.InvalidRequestException;
import server.RequestHandler;

public class RequestHandlerTest {
	
	private RequestHandler reqHandler = null;
	
	@Before
	public void setup() {
		this.reqHandler = new RequestHandler();
	}

	@Test(expected = InvalidRequestException.class)
	public void testHandleRequestPostRequest() throws InvalidRequestException {
		String request = "POST / HTTP/1.1";
		reqHandler.handleRequest(request);

	}
	
	@Test(expected = InvalidRequestException.class)
	public void testHandleRequestUpdateRequest() throws InvalidRequestException {
		String request = "UPDATE / HTTP/1.1";
		reqHandler.handleRequest(request);
	}
	
	@Test(expected = InvalidRequestException.class)
	public void testHandleRequestDeleteRequest() throws InvalidRequestException {
		String request = "DELETE / HTTP/1.1";
		reqHandler.handleRequest(request);

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
