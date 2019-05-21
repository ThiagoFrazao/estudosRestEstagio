package controller;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.ManagedAsync;

import utils.Resposta;

@ApplicationPath("/rest/loja")
@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class RestApplication extends Application {	
	
	@GET
	@ManagedAsync
	public void testandoServidor(@Suspended AsyncResponse response, @Context UriInfo uriInfo){
		
		response.resume(Resposta.montarResposta(Status.OK, "Servidor funcionando corretamente no caminho: " + uriInfo.getBaseUri().toString()));
	}	
}