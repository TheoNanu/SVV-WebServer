package server;

import exceptions.InvalidRequestException;

public class RequestHandler {
	
	private ResourceProvider prov = null;
	private String recvRequest = "";
	
	public RequestHandler(ResourceProvider resourceProvider) {
		this.prov = resourceProvider;
	}

	public void handleRequest(String request) throws InvalidRequestException{
		
	}
	
	public String getRecvRequest() {
		return this.recvRequest;
	}
}
