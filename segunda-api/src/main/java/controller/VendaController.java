package controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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

import dao.VendaDAO;
import entidades.Cliente;
import entidades.Venda;
import erros.ClienteException;
import erros.VendaException;
import utils.Resposta;

@Path("/loja/vendas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VendaController {
	
	@Inject
	private VendaDAO vendaDAO;
	
	@GET
	@ManagedAsync
	public void testeFuncionando(@Suspended AsyncResponse response){		
		response.resume(Resposta.respostaSucesso("Servidor funcionando corretamente."));		
	}
	
	@GET
	@ManagedAsync
	@Path("/listar")
	public void listarTodasVendas(@Suspended AsyncResponse response){
		Response retorno = null;
		
		try {
			ArrayList<Venda> vendas = vendaDAO.listarVendas();
			retorno = Resposta.montarResposta(Status.OK, vendas);
		} catch (ClienteException | VendaException e) {
			retorno = Resposta.respostaErro(e.getMessage());
		}		
		response.resume(retorno);
	}
	
	@GET
	@ManagedAsync
	@Path("/find/{id}")
	public void procurarVenda(@PathParam("id") int id, @Suspended AsyncResponse response){
		Response retorno = null;
		List<Venda> vendas;
		
		try {
			vendas = vendaDAO.procurarVendasCliente(id);
			retorno = Resposta.montarResposta(Status.OK, vendas);			
		} catch (VendaException e) {
			retorno = Resposta.respostaErro(e.getMessage());
		}		
		response.resume(retorno);
	}
	
	@GET
	@ManagedAsync
	@Path("/find")
	public void procurarVendasCliente(@QueryParam("primeiroNome") String primeiroNome, @QueryParam("segundoNome") String segundoNome, @Suspended AsyncResponse response){
		Response retorno = null;
		List<Venda> vendas;
		
		try {
			vendas = vendaDAO.procurarVendasCliente(primeiroNome, segundoNome);
			retorno = Resposta.montarResposta(Status.OK, vendas);
		} catch (ClienteException | VendaException e) {
			retorno = Resposta.respostaErro(e.getMessage());
		}
		
		response.resume(retorno);	
	}
	
	@GET
	@ManagedAsync
	@Path("/total/{id}")
	public void calcularTotalVenda(@PathParam("id") int id, @Suspended AsyncResponse response){
		Response retorno = null;
		float totalVendas;
		
		try {
			totalVendas = vendaDAO.totalVendasCliente(id);
			String msg = "O total do custo de vendas do ID " + id  +  " � R$ " + totalVendas + " .";
			retorno = Resposta.respostaSucesso(msg);
			
			
		} catch (VendaException e) {
			retorno = Resposta.respostaErro(e.getMessage());
		}
		
		response.resume(retorno);
	}
	
	@GET
	@ManagedAsync
	@Path("/total")
	public void calcularTotalVendaCliente(@QueryParam("primeiroNome") String primeiroNome, @QueryParam("segundoNome") String segundoNome, @Suspended AsyncResponse response){
		Response retorno = null;
		float totalVendas;
		
		try {
			totalVendas = vendaDAO.totalVendasCliente(primeiroNome, segundoNome);
			String msg = "O total do custo de vendas do Cliente "+ primeiroNome + " " + segundoNome + " � R$ " + totalVendas + " .";
			retorno = Resposta.respostaSucesso(msg);
		} catch (ClienteException | VendaException e) {
			retorno = Resposta.respostaErro(e.getMessage());
		}
		
		response.resume(retorno);		
	}
	
	@POST
	@ManagedAsync
	@Path("/add")
	public void cadastrarNovaVenda(Venda novaVenda, @Suspended AsyncResponse response){
		Response retorno = null;
		
		if(novaVenda.getTotal() >= 0){
			try {
				vendaDAO.adicionarVenda(novaVenda);
				retorno = Resposta.respostaSucesso();
			} catch (VendaException | ClienteException e) {
				retorno = Resposta.respostaErro(e.getMessage());
			}
		}		
		response.resume(retorno);
	}
	
	@DELETE
	@ManagedAsync
	@Path("/delete/{id}")
	public void deletarVenda(@PathParam("id") int idVenda, @Suspended AsyncResponse response){
		Response retorno = null;
		
		try {
			vendaDAO.deletarVenda(idVenda);
			retorno = Resposta.respostaSucesso();
		} catch (VendaException e) {
			retorno = Resposta.respostaErro(e.getMessage());
		}
		
		response.resume(retorno);
	}
	
	@DELETE
	@ManagedAsync
	@Path("/delete")
	public void deletarVendaCliente(Cliente cliente, @Suspended AsyncResponse response){
		Response retorno = null;
		try {			
				if(StringUtils.isNotBlank(cliente.getPrimeiroNome()) && StringUtils.isNotBlank(cliente.getSegundoNome())){
					vendaDAO.deletarVendasCliente(cliente.getPrimeiroNome(), cliente.getSegundoNome());
					retorno = Resposta.respostaSucesso();
				}
				else if(cliente.getId() >= 0){
					vendaDAO.deletarVendasCliente(cliente.getId());
					retorno = Resposta.respostaSucesso();
				}			
				else{
					String msg = "Informa��es insuficientes para deletar vendas.";
					retorno = Resposta.respostaErro(msg);
				}
		}catch (VendaException | ClienteException e) {
			retorno = Resposta.respostaErro(e.getMessage());
		}		
		
		response.resume(retorno);
	}

}
