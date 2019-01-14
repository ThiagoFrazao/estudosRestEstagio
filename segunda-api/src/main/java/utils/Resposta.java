package utils;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class Resposta {
	
	private Status status;
	private String mensagem;

	
	public Resposta(){
		
	}
	
	public static Response montarResposta(Status st, Object conteudo){
		return Response.status(st).entity(conteudo).build();
	}
	
	public static Response respostaSucesso(){
		String mensagem = formatarMensagem("Operação efetuada com sucesso.");		
		return Response.status(Status.OK).entity(mensagem).build();		
	}
	
	public static Response respostaSucesso(String msg){	
		String mensagem = formatarMensagem(msg);
		return Response.status(Status.OK).entity(mensagem).build();		
	}
	
	public static Response respostaErro(){
		String mensagem = "Ocorreu um erro durante a requisição ou serviço requisitado não existe.";
		mensagem = formatarMensagem(mensagem);
		return Response.status(Status.BAD_REQUEST).entity(mensagem).build();
	}
	public static Response respostaErro(String msg){
		String mensagem = formatarMensagem(msg);
		return Response.status(Status.BAD_REQUEST).entity(mensagem).build();
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
