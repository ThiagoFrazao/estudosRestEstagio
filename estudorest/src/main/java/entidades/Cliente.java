package entidades;

import org.apache.commons.lang3.StringUtils;

public class Cliente {
	
	private int id;
	private String primeiroNome;
	private String segundoNome;
	private String endereco;
	private String cidade;
	
	public Cliente(){
		
	}
	
	public Cliente(int id){
		this.id = id;
	}
	
	public Cliente(String primeiroNome, String segundoNome, String endereco, String cidade ){
		this.primeiroNome = primeiroNome;
		this.segundoNome = segundoNome;
		this.endereco = endereco;
		this.cidade = cidade;
	}
	
	public boolean verificarValidade(){		
		return StringUtils.isNotBlank(this.primeiroNome) && StringUtils.isNotBlank(this.segundoNome) && 
			   StringUtils.isNotBlank(this.endereco) && StringUtils.isNotBlank(this.cidade);
	}

	public int getId() {
		return id;
	}
	
	public String getPrimeiroNome() {
		return primeiroNome;
	}

	public void setPrimeiroNome(String primeiroNome) {
		this.primeiroNome = primeiroNome;
	}

	public String getSegundoNome() {
		return segundoNome;
	}

	public void setSegundoNome(String segundoNome) {
		this.segundoNome = segundoNome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	@Override
	public String toString(){
		
		StringBuilder str = new StringBuilder();
		
		str.append("Nome = " + this.getPrimeiroNome() + " " + this.getSegundoNome() + "\n");
		str.append("Endereco = " + this.getEndereco() + "\n");
		str.append("Cidade = " + this.getCidade() + "\n");
		
		return str.toString();	
	}
	

}
