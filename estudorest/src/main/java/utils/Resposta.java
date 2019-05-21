package utils;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class Resposta {
	
	private Status status;
	private String mensagem;

	
	public Resposta(){
		
	}
	
	public static Response montarResposta(Status st, Object conteudo){
		if(conteudo.getClass().equals(String.class)){
			String msg = formatarMensagem((String) conteudo);
			return Response.status(st).entity(msg).build();
		} else {
			return Response.status(st).entity(conteudo).build();
		}		
	}
	
	public static Response montarRespostaSucesso(){
		String mensagem = formatarMensagem("Operação efetuada com sucesso.");		
		return Response.status(Status.OK).entity(mensagem).build();		
	}
	
	public static Response montarRespostaCriacao() {
		String msg = formatarMensagem("Recurso criado com sucesso.");
		return Response.status(Status.CREATED).entity(msg).build();
	}
	
	public static Response montarRespostaCriacao(String uriObjeto) {
		String msg = formatarMensagem("Recurso criado com sucesso.");
		return Response.status(Status.CREATED).header("Link", uriObjeto).entity(msg).build();
	}
	
	
	private static String formatarMensagem(String msg){		
		String resposta = "{ \"resposta\": \"" + msg + "\" }";		
		return resposta;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	


}
