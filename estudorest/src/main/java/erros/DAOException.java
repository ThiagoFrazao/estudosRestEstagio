package erros;

import javax.ws.rs.core.Response.Status;

public class DAOException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1449233574783471528L;
	
	private Status status;

	public DAOException(String msg, Status status){
		super(msg);
		this.setStatus(status);
	}
	
	public DAOException(){
		super("Ocorreu um erro inesperado no servidor. Por favor tente novamente mais tarde.");
		this.setStatus(Status.INTERNAL_SERVER_ERROR);
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
