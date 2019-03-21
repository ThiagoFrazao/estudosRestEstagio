package erros;

public class ClienteException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1449233574783471528L;

	public ClienteException(String msg){
		super(msg);
	}
	
	public ClienteException(){
		super("Serviço requisitado esta indisponivel.");
	}

}
