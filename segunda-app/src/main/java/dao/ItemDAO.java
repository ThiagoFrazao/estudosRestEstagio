package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import entidades.Item;
import entidades.Produto;
import entidades.Venda;
import erros.ItensException;
import erros.ProdutoException;
import erros.VendaException;

@Dependent
public class ItemDAO {
	
	@Inject
	private ConexaoDAO dao;	
	
	@Inject
	private VendaDAO vendas;
	
	@Inject
	private ProdutoDAO produtos;
	
	public int adicionarItem(Item novoItem) throws ItensException{
		int retorno = -1;
		String sql = "insert into ITENS (VENDAID,PRODUTOID,ITEM,QUANTIDADE,CUSTO) values (?,?,?,?,?)";
		
		if(this.vendas.verificarExistencia(novoItem.getVenda().getId()) && this.produtos.verificarExistenciaProduto(novoItem.getProduto().getId())){
			try {
				Connection con = dao.getConexao();
				PreparedStatement stmt = con.prepareStatement(sql);
				
				stmt.setInt(1, novoItem.getVenda().getId());
				stmt.setInt(2, novoItem.getProduto().getId());
				stmt.setInt(3, novoItem.getCodItem());
				stmt.setInt(4, novoItem.getQuantidade());
				stmt.setInt(5, novoItem.getCusto());
				
				retorno = stmt.executeUpdate();
				stmt.close();		
				con.close();
				
				
			} catch (SQLException e) {
				System.out.println("SQLException " + e.getMessage());
				e.printStackTrace();
				throw new ItensException();
			}
		}else{
			String msg = "Item n�o possui produto ou venda associados. Todo novo item deve ter um produto e venda associados.";
			throw new ItensException(msg);
		}
		
		
		return retorno;
	}
	
	public int removerVendaItens(int vendaID) throws ItensException{
		int retorno = -1;
		String sql = "delete from ITENS where VENDAID=?";
		
		try {
			Connection con = dao.getConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, vendaID);
			
			retorno = stmt.executeUpdate();
			stmt.close();
			con.close();
			if(retorno == 0){
				String msg = "N�o h� itens cadastrados na Venda informada.";
				throw new ItensException(msg);
			}			
		} catch (SQLException e) {
			System.out.println("SQLException " + e.getMessage());
			e.printStackTrace();
			throw new ItensException();
		}
		
		return retorno;
	}
	
	public int removerProdutosItens(int produtoID) throws ItensException{
		int retorno = -1;
		String sql = "delete from ITENS where PRODUTOID=?";
		
		try {
			Connection con = dao.getConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, produtoID);
			
			retorno = stmt.executeUpdate();
			stmt.close();
			con.close();
			if(retorno == 0){
				String msg = "N�o h� itens cadastrados na Venda informada.";
				throw new ItensException(msg);
			}
			
			
		} catch (SQLException e) {
			System.out.println("SQLException " + e.getMessage());
			e.printStackTrace();
			throw new ItensException();
		}
		
		return retorno;
	}
	
	public int removerItens(int codItem) throws ItensException{
		int retorno = -1;
		String sql = "delete from ITENS where ITEM=?";
		
		try {
			Connection con = dao.getConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, codItem);
			
			retorno = stmt.executeUpdate();
			stmt.close();
			con.close();
			if(retorno == 0){
				String msg = "N�o h� itens cadastrados na Venda informada.";
				throw new ItensException(msg);
			}
			
			
		} catch (SQLException e) {
			System.out.println("SQLException " + e.getMessage());
			e.printStackTrace();
			throw new ItensException();
		}
		
		return retorno;
	}
	
	public int atualizarItem(Venda venda, Produto produto, int item, int quantidade, int custo) throws ItensException{
		
		int retorno = -1;
		String sql = "update ITENS set PRODUTOID=?,ITEM=?,QUANTIDADE=?,CUSTO=? where VENDAID=?";
		
		try {
			Connection con = dao.getConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, produto.getId());
			stmt.setInt(2, item);
			stmt.setInt(3, quantidade);
			stmt.setInt(4, custo);
			stmt.setInt(5, venda.getId());
			
			retorno = stmt.executeUpdate();
			stmt.close();
			con.close();
			if(retorno == 0){
				String msg = "O ID informado n�o corresponde a um Item cadastrado.";
				throw new ItensException(msg);
			}
			
		} catch (SQLException e) {
			System.out.println("SQLException " + e.getMessage());
			e.printStackTrace();
			throw new ItensException();
		}
		
		return retorno;	
	}
	
	public int atualizarItem(Venda venda, int quantidade, int custo) throws ItensException{
		
		int retorno = -1;
		String sql = "update ITENS set QUANTIDADE=?,CUSTO=? where VENDAID=?";
		
		try {
			Connection con = dao.getConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, quantidade);
			stmt.setInt(2, custo);
			stmt.setInt(3, venda.getId());
			
			retorno = stmt.executeUpdate();
			stmt.close();
			con.close();
			if(retorno == 0){
				String msg = "O ID informado n�o corresponde a um Item cadastrado.";
				throw new ItensException(msg);
			}
			
		} catch (SQLException e) {
			System.out.println("SQLException " + e.getMessage());
			e.printStackTrace();
			throw new ItensException();
		}
		
		return retorno;	
	}
	
	public Item procurarItem(int codItem) throws VendaException, ProdutoException, ItensException{
		Item retorno = null;
		Venda venda;
		Produto produto;
		String sql = "select * from ITENS where ITEM=?";
		
		try {
			Connection con = dao.getConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, codItem);
			
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				retorno = new Item();
				venda = vendas.procurarVenda(rs.getInt(1));
				produto = produtos.procurarProduto(rs.getInt(2));
				
				retorno.setVenda(venda);
				retorno.setProduto(produto);
				retorno.setCodItem(rs.getInt(3));
				retorno.setQuantidade(rs.getInt(4));
				retorno.setCusto(rs.getInt(5));
				
				rs.close();
				stmt.close();
				con.close();
			}else{
				rs.close();
				stmt.close();
				con.close();
				String msg = "O ID informado n�o corresponde a um Item cadastrado.";
				throw new ItensException(msg);
			}
		} catch (SQLException e) {
			System.out.println("SQLException " + e.getMessage());
			e.printStackTrace();
			throw new ItensException();
		}
		
		return retorno;
	}
	
	public List<Item> procurarItensVenda(int idVenda) throws VendaException, ProdutoException, ItensException{
		List<Item> retorno = new ArrayList<Item>();
		Item item;
		Venda venda;
		Produto produto;
		String sql = "select * from ITENS where VENDAID=? order by VENDAID";
		
		
		try {
			Connection con = dao.getConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, idVenda);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()){
				item = new Item();
				venda = vendas.procurarVenda(rs.getInt(1));
				produto = produtos.procurarProduto(rs.getInt(2));
				
				item.setVenda(venda);
				item.setProduto(produto);
				item.setCodItem(rs.getInt(3));
				item.setQuantidade(rs.getInt(4));
				item.setCusto(rs.getInt(5));
				retorno.add(item);
			}else{
				rs.close();
				stmt.close();
				con.close();
				String msg = "A venda informada n�o possui Itens cadastrados.";
				throw new ItensException(msg);
			}
			
			while(rs.next()){
				item = new Item();
				venda = vendas.procurarVenda(rs.getInt(1));
				produto = produtos.procurarProduto(rs.getInt(2));
				
				item.setVenda(venda);
				item.setProduto(produto);
				item.setCodItem(rs.getInt(3));
				item.setQuantidade(rs.getInt(4));
				item.setCusto(rs.getInt(5));
				retorno.add(item);
			}
			rs.close();
			stmt.close();
			con.close();
			
		} catch (SQLException e) {
			System.out.println("SQLException " + e.getMessage());
			e.printStackTrace();
			throw new ItensException();
		}
		
		return retorno;
	}
	
	public List<Item> procurarItensProduto(int idProduto) throws VendaException, ProdutoException, ItensException{
		List<Item> retorno = new ArrayList<Item>();
		Item item;
		Venda venda;
		Produto produto;
		String sql = "select * from ITENS where PRODUTOID=? order by PRODUTOID";
		
		
		try {
			Connection con = dao.getConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, idProduto);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()){
				item = new Item();
				venda = vendas.procurarVenda(rs.getInt(1));
				produto = produtos.procurarProduto(rs.getInt(2));
				
				item.setVenda(venda);
				item.setProduto(produto);
				item.setCodItem(rs.getInt(3));
				item.setQuantidade(rs.getInt(4));
				item.setCusto(rs.getInt(5));
				retorno.add(item);
			}else{
				rs.close();
				stmt.close();
				con.close();
				String msg = "O produto informado n�o est� vinculado a Itens cadastrados.";
				throw new ItensException(msg);
			}
			
			while(rs.next()){
				item = new Item();
				venda = vendas.procurarVenda(rs.getInt(1));
				produto = produtos.procurarProduto(rs.getInt(2));
				
				item.setVenda(venda);
				item.setProduto(produto);
				item.setCodItem(rs.getInt(3));
				item.setQuantidade(rs.getInt(4));
				item.setCusto(rs.getInt(5));
				retorno.add(item);
			}
			rs.close();
			stmt.close();
			con.close();
			
		} catch (SQLException e) {
			System.out.println("SQLException " + e.getMessage());
			e.printStackTrace();
			throw new ItensException();
		}
		
		return retorno;
	} 
	
	public List<Item> listarTodosItens() throws VendaException, ProdutoException, ItensException{
		List<Item> retorno = new ArrayList<Item>();
		Item item;
		Venda venda;
		Produto produto;
		String sql = "select * from ITENS order by ITEM";
		
		
		try {			
			Connection con = dao.getConexao();			
			PreparedStatement stmt = con.prepareStatement(sql);			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				item = new Item();
				venda = vendas.procurarVenda(rs.getInt(1));
				produto = produtos.procurarProduto(rs.getInt(2));
				
				item.setVenda(venda);
				item.setProduto(produto);
				item.setCodItem(rs.getInt(3));
				item.setQuantidade(rs.getInt(4));
				item.setCusto(rs.getInt(5));
				retorno.add(item);
			}
			rs.close();
			stmt.close();
			con.close();
			
		} catch (SQLException e) {
			System.out.println("SQLException " + e.getMessage());
			e.printStackTrace();
			throw new ItensException();
		}
		
		return retorno;
	} 
	
/*	
	public static void main(String[] args) throws ItensException {
		
		ItemDAO teste = new ItemDAO();
		Venda venda = new Venda(200);
		Produto produto = new Produto(200);
		
		Item novoItem = new Item(venda,produto,10,10,10);
		teste.adicionarItem(novoItem);
	}
*/
}
