package entidades;

public class Item {
	
	private Venda venda;
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
	
	public boolean validarItem(int modo){
		boolean retorno = false;
		if(modo == 1){
			// validar 2 campos
			retorno = this.getCusto() > 0 && this.getQuantidade() > 0;
		}else{
			// validar todos os campos
			retorno = this.getVenda() != null && this.getProduto() != null && this.getCusto() > 0 && 
					  this.getQuantidade() > 0 && this.getCodItem() > 0;
		}
		return retorno;
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
