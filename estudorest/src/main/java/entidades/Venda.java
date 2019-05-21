package entidades;

import javax.validation.constraints.NotNull;

public class Venda {
	
	private int id;
	@NotNull
	private float total;
	@NotNull
	private Cliente cliente;
	
	public Venda(){
		
	}
	
	public Venda(int id){
		this.id = id;
	}
	
	public Venda(float total, Cliente cliente){
		this.total = total;
		this.cliente = cliente;
	}
	
	public Venda(int id, float total, Cliente cliente){
		this.id = id;
		this.total = total;
		this.cliente = cliente;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	@Override
	public String toString(){
		
		StringBuilder str = new StringBuilder();
		
		str.append("Comprador = " + this.getCliente().getPrimeiroNome()+ " " + this.getCliente().getSegundoNome() + "\n");
		str.append("Total = " + this.getTotal());
		
		return str.toString();		
	}
	
	

}
