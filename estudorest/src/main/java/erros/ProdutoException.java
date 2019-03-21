package erros;

public class ProdutoException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2032200460821699784L;
	

	public ProdutoException(String msg){
		super(msg);
	}
	
	public ProdutoException(){
		super("Serviço requisitado esta indisponivel.");
	}

}
