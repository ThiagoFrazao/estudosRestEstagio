package erros;

public class ItensException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3625421253067319635L;

	public ItensException(String msg){
		super(msg);
	}
	
	public ItensException(){
		super("Serviço requisitado esta indisponivel.");
	}
	
}
