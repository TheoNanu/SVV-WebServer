package server;

import java.util.StringTokenizer;

import exceptions.InvalidRequestException;

public class RequestHandler {
	
	private String recvRequest = "";
	
	public RequestHandler() {
	}

	public void handleRequest(String request) throws InvalidRequestException{
		String method = "";
		
		if(request != null)
		{
			StringTokenizer parse = new StringTokenizer(request);
			method = parse.nextToken().toUpperCase();
		}
		
		if(!method.equals("GET"))
		{
			throw new InvalidRequestException();
		}
		else
		{
			this.recvRequest = request;
		}
	}
	
	public String getRecvRequest() {
		return this.recvRequest;
	}
}
