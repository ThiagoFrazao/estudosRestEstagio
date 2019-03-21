package entidades;

import org.apache.commons.lang3.StringUtils;

public class Produto {
	
	private int id;
	private String nome;
	private float preco;
	
	public Produto(){
		
	}
	
	public Produto(int id){
		this.id = id;
	}
	
	public Produto(String nome, float preco){
		this.nome = nome;
		this.preco = preco;
	}
	
	public Produto(int id, String nome, float preco){
		this.id = id;
		this.nome = nome;
		this.preco = preco;
	}
	
	public boolean verificarValidade(){
		return StringUtils.isNotBlank(this.nome) && this.preco > 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public float getPreco() {
		return preco;
	}

	public void setPreco(float preco) {
		this.preco = preco;
	}
	
	@Override
	public String toString(){
		StringBuilder str = new StringBuilder();
		
		str.append("Nome  = " + this.getNome()  + "\n");
		str.append("Pre�o = " + this.getPreco() + "\n");
		
		return str.toString();
	}
	
}
