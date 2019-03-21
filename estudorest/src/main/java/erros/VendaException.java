package erros;

public class VendaException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8790626020253026960L;

	public VendaException(String msg){
		super(msg);
	}
	
	public VendaException(){
		super("Serviço requisitado esta indisponivel.");
	}
	
}
