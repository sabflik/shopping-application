package nz.ac.auckland.shop.services;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;

@Path("/chat")
public class ChatResource {
	protected List<AsyncResponse> responses = new ArrayList<AsyncResponse>();

	@GET
	public void subscribe(@Suspended AsyncResponse response) {
		responses.add(response);
	}
	
	@POST
	@Consumes("text/plain")
	public void send(String message) {
		//Notify subscribers
		for(AsyncResponse response: responses) {
			response.resume(message);
		}
		responses.clear();
	}
}
