package entidades;

import javax.validation.constraints.NotNull;

public class Item {
	
	@NotNull
	private Venda venda;
	@NotNull
	private Produto produto;
	private int codItem;
	private int quantidade;
	private int custo;
	
	public Item(){
		
	}
	
	public Item(Venda venda){
		this.venda = venda;
	}
	
	public Item(Venda venda, Produto produto, int item, int quantidade, int custo){
		this.venda   = venda;
		this.produto = produto;
		this.codItem = item;	
		this.custo   = custo;
		this.quantidade = quantidade;
	}
	
	// validar todos os campos
	public boolean validarItem(){			
		return this.getCusto() > 0 && this.getQuantidade() > 0 && this.getCodItem() > 0;
	}
	
	// validar 2 campos
	public boolean validarItem(int modo){
		
		return this.getCusto() > 0 && this.getQuantidade() > 0;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public int getCodItem() {
		return codItem;
	}

	public void setCodItem(int codItem) {
		this.codItem = codItem;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public int getCusto() {
		return custo;
	}

	public void setCusto(int custo) {
		this.custo = custo;
	}
	
	@Override
	public String toString(){		
		StringBuilder str = new StringBuilder();
		
		str.append("Identificador da Venda = " + this.getVenda().getId() + "\n");
		str.append("Produto = " + this.getProduto().getNome() + "\n");
		str.append("Item = " + this.getCodItem()+ "\n");
		str.append("Quantidade = " + this.getQuantidade() + "\n");
		str.append("Custo = " + this.getCusto());
		
		return str.toString();		
	}

}
