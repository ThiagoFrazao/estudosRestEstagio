package controller;

import java.util.ArrayList;

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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.ManagedAsync;

import dao.ProdutoDAO;
import entidades.Produto;
import erros.DAOException;
import utils.Resposta;

@Path("/produto")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoController {
	
	@Inject
	private ProdutoDAO produtoDAO;
	
	@Context
	private UriInfo info;
	
	@GET
	@ManagedAsync
	@Path("/find/{id}")
	public void procurarProdutoNome(@PathParam("id")int id, @Suspended AsyncResponse response){
		Response retorno = null;		
		try {
			Produto produtos = produtoDAO.procurarProduto(id);
			retorno = Resposta.montarResposta(Status.OK, produtos);
		} catch (DAOException e) {
			retorno = Resposta.montarResposta(e.getStatus(), e.getMessage());
		}
				
		response.resume(retorno);
	}
	
	@GET
	@ManagedAsync
	@Path("/find/maior/{preco}")
	public void procurarProdutoPrecoMaior(@PathParam("preco") float preco, @Suspended AsyncResponse response){
		Response retorno = null;
		try {
			ArrayList<Produto> produtos = produtoDAO.procurarProdutoMaior(preco);
			retorno = Resposta.montarResposta(Status.OK, produtos);
		} catch (DAOException e) {
			retorno = Resposta.montarResposta(e.getStatus(), e.getMessage());
		}
		response.resume(retorno);
	}
	
	@GET
	@ManagedAsync
	@Path("/find/menor/{preco}")
	public void procurarProdutoPrecoMenor(@PathParam("preco") float preco, @Suspended AsyncResponse response){
		Response retorno = null;
		try {
			ArrayList<Produto> produtos = produtoDAO.procurarProdutoMenor(preco);
			retorno = Resposta.montarResposta(Status.OK, produtos);
		} catch (DAOException e) {
			retorno = Resposta.montarResposta(e.getStatus(), e.getMessage());
		}
		response.resume(retorno);
	}
	
	@GET
	@ManagedAsync
	@Path("/find/faixa")
	public void procurarProdutoPrecoFaixa(@QueryParam("menor") float menor, @QueryParam("maior") float maior, @Suspended AsyncResponse response){
		Response retorno = null;
		try {
			ArrayList<Produto> produtos = produtoDAO.procurarProdutoFaixa(menor, maior);
			retorno = Resposta.montarResposta(Status.OK, produtos);
		} catch (DAOException e) {
			retorno = Resposta.montarResposta(e.getStatus(), e.getMessage());
		}
		response.resume(retorno);
	}
	
	@GET
	@ManagedAsync
	@Path("/listar")
	public void listarTodosProdutos(@Suspended AsyncResponse response){
		Response retorno = null;		
		try {
			ArrayList<Produto> produtos = produtoDAO.listarProdutos();
			retorno = Resposta.montarResposta(Status.OK, produtos);
		} catch (DAOException e) {
			retorno = Resposta.montarResposta(e.getStatus(), e.getMessage());
		}				
		response.resume(retorno);
	}
	
	@POST
	@ManagedAsync
	@Path("/add")
	public void adicionarProdutoNovo(Produto novoProduto, @Suspended AsyncResponse response){	
		Response retorno = null;		
		try {
			if(novoProduto.verificarValidade()){
				if(!produtoDAO.verificarExistenciaProduto(novoProduto.getNome())){
					produtoDAO.adicionarProduto(novoProduto);
					retorno = Resposta.montarRespostaCriacao();
				} else {
					retorno = Resposta.montarResposta(Status.CONFLICT, "O Produto" + novoProduto.getNome() + " ja esta cadastrado.");
				}				
			} else {
				retorno = Resposta.montarResposta(Status.BAD_GATEWAY, "Informacoes insuficientes para registrar Produto.");
			}
			
		} catch (DAOException e) {
			retorno = Resposta.montarResposta(e.getStatus(), e.getMessage());
		}		
		response.resume(retorno);		
	}
	
	@POST
	@ManagedAsync
	@Path("/update")
	public void atualizarProduto(Produto novoProduto, @Suspended AsyncResponse response){
		Response retorno = null;
		
		if(novoProduto.verificarValidade()){
			try {
				produtoDAO.atualizarProduto(novoProduto.getId(), novoProduto.getNome(), novoProduto.getPreco());
				retorno = Resposta.montarRespostaSucesso();
			} catch (DAOException e) {
				retorno = Resposta.montarResposta(e.getStatus(), e.getMessage());
			}
		}else{
			retorno = Resposta.montarResposta(Status.BAD_REQUEST, "Informacoes insuficientes para atualizar Produto.");
		}		
		response.resume(retorno);
	}
	
	@DELETE
	@ManagedAsync
	@Path("/delete/{id}")
	public void removerProduto(@PathParam("id") int id, @Suspended AsyncResponse response){
		Response retorno = null;		
		try {
			produtoDAO.removerProduto(id);
			retorno = Resposta.montarRespostaSucesso();
		} catch (DAOException e) {
			retorno = Resposta.montarResposta(e.getStatus(), e.getMessage());
		}		
		response.resume(retorno);
	}

}
