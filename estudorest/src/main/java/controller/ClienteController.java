package controller;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.server.ManagedAsync;

import dao.ClienteDAO;
import entidades.Cliente;
import erros.DAOException;
import utils.Resposta;


@Path("/loja/cliente")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteController {
	
	@Inject
	private ClienteDAO clienteDAO;
	
	@GET
	@ManagedAsync
	@Path("/listar")
	public void listarTodosClientes(@Suspended AsyncResponse response){
		Response retorno = null;
		List<Cliente> clientes = null;
		try {
			clientes = clienteDAO.listarClientes();
			retorno = Resposta.montarResposta(Status.OK, clientes);
		} catch (DAOException e) {
			retorno = Resposta.montarResposta(e.getStatus(), e.getMessage());
		}		
		
		response.resume(retorno);
	}
	
	@GET
	@ManagedAsync
	@Path("/find/{id}")
	public void recuperarCliente(@PathParam("id") int id, @Suspended AsyncResponse response){
		Response retorno = null;
		try {
			Cliente cliente = clienteDAO.procurarCliente(id);
			retorno = Resposta.montarResposta(Status.OK, cliente);
		} catch (DAOException e) {
			retorno = Resposta.montarResposta(e.getStatus(), e.getMessage());
		}		
		response.resume(retorno);	
	}
	
	@GET
	@ManagedAsync
	@Path("/find")
	public void recuperarCliente1(@QueryParam("primeiroNome") String primeiroNome, @QueryParam("segundoNome") String segundoNome, @Suspended AsyncResponse response){
		Response retorno = null;
		try {			
			Cliente cliente = clienteDAO.procurarCliente(primeiroNome,segundoNome);
			retorno = Resposta.montarResposta(Status.OK, cliente);
		} catch (DAOException e) {
			retorno = Resposta.montarResposta(e.getStatus(), e.getMessage());
		}		
		response.resume(retorno);	
	}
	
	@POST
	@ManagedAsync
	@Path("/add")
	public void adicionarCliente(Cliente novoCliente, @Suspended AsyncResponse response){
		Response retorno = null;
		
		if(novoCliente.verificarValidade()){			
			if(!clienteDAO.verificarExistenciaCliente(novoCliente.getPrimeiroNome(), novoCliente.getSegundoNome())){
				try {
					clienteDAO.adicionarCliente(novoCliente);
				} catch (DAOException e) {
					retorno = Resposta.montarResposta(e.getStatus(), e.getMessage());
				}
				retorno = Resposta.montarRespostaSucesso();
			}else{
				String msg = "Cliente "+ novoCliente.getPrimeiroNome()+ " " + novoCliente.getSegundoNome() + " ja esta cadastrado.";
				retorno = Resposta.montarResposta(Status.CONFLICT, msg);
			}	
		}else{
			String msg = "Informacoes insuficientes para registrar cliente.";
			retorno = Resposta.montarResposta(Status.BAD_REQUEST,msg);
		}			
		response.resume(retorno);
	}
	
	@PUT
	@ManagedAsync
	@Path("/update")
	public void atualizarCliente(Cliente cliente, @Suspended AsyncResponse response){
		Response retorno = null;
		
		String primeiroNome = cliente.getPrimeiroNome();
		String segundoNome  = cliente.getSegundoNome();
		String novaRua = cliente.getEndereco();
		String novaCidade = cliente.getCidade();
		
		try {
			clienteDAO.atualizarCliente(primeiroNome, segundoNome, novaRua, novaCidade);
			retorno = Resposta.montarRespostaSucesso();
		} catch (DAOException e) {
			retorno = Resposta.montarResposta(e.getStatus(), e.getMessage());
		}
		
		response.resume(retorno);
	}
	
	@DELETE
	@ManagedAsync
	@Path("/delete")
	public void deletarCliente(Cliente cliente, @Suspended AsyncResponse response){
		Response retorno = null;
		
		try {
			if(StringUtils.isNotBlank(cliente.getPrimeiroNome()) && StringUtils.isNotBlank(cliente.getSegundoNome())){
				clienteDAO.deletarCliente(cliente.getPrimeiroNome(),cliente.getSegundoNome());
			}
			else if(Integer.valueOf(cliente.getId())!= null){
				clienteDAO.deletarCliente(cliente.getId());
			}
			else{
				String msg = "Informacoes insuficientes para encontrar Cliente.";
				retorno = Resposta.montarResposta(Status.BAD_REQUEST,msg);
			}			
			retorno = Resposta.montarRespostaSucesso();
		} catch (DAOException e) {
			retorno = Resposta.montarResposta(e.getStatus(), e.getMessage());
		}		
		response.resume(retorno);
	}
}
