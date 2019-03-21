package controller;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.ManagedAsync;

import utils.Resposta;

@ApplicationPath("/rest")
@Path("/loja")
public class RestApplication extends Application {	
	@GET
	@ManagedAsync
	public void testandoServidor(@Suspended AsyncResponse response){
		response.resume(Resposta.montarResposta(Status.OK, "Servidor funcionando corretamente"));
	}	
}