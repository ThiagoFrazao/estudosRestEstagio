package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.ws.rs.core.Response.Status;

import entidades.Produto;
import erros.DAOException;

@Dependent
public class ProdutoDAO {
	
	@Inject
	private ConexaoDAO dao;	
	
	public int adicionarProduto(Produto novoProduto) throws DAOException{
		
		String sql = "insert into PRODUTO (NOME,PRECO) values(?,?)";
		int retorno = -1;
		
		try {
			Connection con = dao.getConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			
			stmt.setString(1, novoProduto.getNome());
			stmt.setFloat(2, novoProduto.getPreco());
			
			retorno = stmt.executeUpdate();
			stmt.close();			
			con.close();
			
		} catch (SQLException e) {
			System.out.println("SQLException " + e.getMessage());
			e.printStackTrace();
			throw new DAOException();
		}
		
		return retorno;		
	}
	
	public int removerProduto(int id) throws DAOException{
		
		int retorno = -1;
		String sql = "delete from PRODUTO where id=?";
		
		try {
			Connection con = dao.getConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, id);
			
			retorno = stmt.executeUpdate();
			stmt.close();
			con.close();
			if(retorno == 0){
				String msg = "O ID informado nao corresponde a um produto";
				throw new DAOException(msg,Status.NOT_FOUND);
			}
		} catch (SQLException e) {
			System.out.println("SQLException " + e.getMessage());
			e.printStackTrace();
			throw new DAOException();
		}		
		return retorno;		
	}
	
	public int atualizarProduto(int id, String novoNome, float novoPreco) throws DAOException{
		
		int retorno = -1;
		String sql = "update PRODUTO set NOME=?, PRECO=? where ID=?";
		
		try {
			Connection con = dao.getConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, novoNome);
			stmt.setFloat(2, novoPreco);
			stmt.setInt(3, id);
			
			retorno = stmt.executeUpdate();
			stmt.close();
			con.close();
			if(retorno == 0){
				String msg = "O ID informado nao corresponde a um produto";
				throw new DAOException(msg,Status.NOT_FOUND);
			}			
		} catch (SQLException e) {
			System.out.println("SQLException " + e.getMessage());
			e.printStackTrace();
			throw new DAOException();
		}		
		return retorno;		
	}
	
	public ArrayList<Produto> procurarProdutoMaior(float preco) throws DAOException{
		ArrayList<Produto> retorno = new ArrayList<Produto>();
		Produto produto = null;
		String sql = "select * from PRODUTO where PRECO > ? order by PRECO";
		
		try {
			Connection con = dao.getConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setFloat(1, preco);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()){
				produto = new Produto();
				produto.setId(rs.getInt(1));
				produto.setNome(rs.getString(2));
				produto.setPreco(rs.getFloat(3));
				retorno.add(produto);
			}else{
				rs.close();
				stmt.close();
				con.close();				
				String msg = "Nao ha Produtos nessa faixa de preco.";
				throw new DAOException(msg,Status.NOT_FOUND);
			}
			
			while(rs.next()){				
				produto = new Produto();
				produto.setId(rs.getInt(1));
				produto.setNome(rs.getString(2));
				produto.setPreco(rs.getFloat(3));		
				retorno.add(produto);
			}
			rs.close();
			stmt.close();
			con.close();
			
		} catch (SQLException e) {			
			throw new DAOException();
		}
		
		return retorno;
	}
	
	public ArrayList<Produto> procurarProdutoMenor(float preco) throws DAOException{
		ArrayList<Produto> retorno = new ArrayList<Produto>();
		Produto produto = null;
		String sql = "select * from PRODUTO where PRECO < ? order by PRECO";
		
		try {
			Connection con = dao.getConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setFloat(1, preco);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()){
				produto = new Produto();
				produto.setId(rs.getInt(1));
				produto.setNome(rs.getString(2));
				produto.setPreco(rs.getFloat(3));
				retorno.add(produto);
			}else{
				rs.close();
				stmt.close();
				con.close();				
				String msg = "Nao ha Produtos nessa faixa de preco.";
				throw new DAOException(msg,Status.NOT_FOUND);
			}
			
			while(rs.next()){				
				produto = new Produto();
				produto.setId(rs.getInt(1));
				produto.setNome(rs.getString(2));
				produto.setPreco(rs.getFloat(3));		
				retorno.add(produto);
			}
			rs.close();
			stmt.close();
			con.close();
			
		} catch (SQLException e) {			
			throw new DAOException();
		}
		
		return retorno;
	}
	
	public ArrayList<Produto> procurarProdutoFaixa(float menor, float maior) throws DAOException{
		ArrayList<Produto> retorno = new ArrayList<Produto>();
		Produto produto = null;
		String sql = "select * from PRODUTO where PRECO < ? and PRECO > ? order by PRECO";
		
		try {
			Connection con = dao.getConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setFloat(1, menor);
			stmt.setFloat(2, maior);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()){
				produto = new Produto();
				produto.setId(rs.getInt(1));
				produto.setNome(rs.getString(2));
				produto.setPreco(rs.getFloat(3));
				retorno.add(produto);
			}else{
				rs.close();
				stmt.close();
				con.close();				
				String msg = "Nao ha Produtos nessa faixa de preco.";
				throw new DAOException(msg,Status.NOT_FOUND);
			}
			
			while(rs.next()){				
				produto = new Produto();
				produto.setId(rs.getInt(1));
				produto.setNome(rs.getString(2));
				produto.setPreco(rs.getFloat(3));		
				retorno.add(produto);
			}
			rs.close();
			stmt.close();
			con.close();
			
		} catch (SQLException e) {			
			throw new DAOException();
		}
		
		return retorno;
	}
	
	public Produto procurarProduto(int id) throws DAOException{
		
		Produto retorno = null;
		String sql = "select * from PRODUTO where ID=?";
		
		try {
			Connection con = dao.getConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			if(!rs.next()){
				rs.close();
				stmt.close();
				con.close();
				String msg = "O ID informado nao corresponde a um produto";
				throw new DAOException(msg,Status.NOT_FOUND);
			}
			
			retorno = new Produto(id);
			retorno.setNome(rs.getString(2));
			retorno.setPreco(rs.getFloat(3));
			
			rs.close();
			stmt.close();
			con.close();			
		} catch (SQLException e) {
			throw new DAOException();
		}		
		return retorno;		
	}

	public ArrayList<Produto> listarProdutos() throws DAOException{
		
		ArrayList<Produto> retorno = new ArrayList<Produto>();
		Produto produto;
		String sql = "select * from PRODUTO";
		
		try {
			Connection con = dao.getConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				produto = new Produto();
				produto.setId(rs.getInt(1));
				produto.setNome(rs.getString(2));
				produto.setPreco(rs.getFloat(3));
				
				retorno.add(produto);
			}
			rs.close();
			stmt.close();
			con.close();			
			
		} catch (SQLException e) {
			throw new DAOException();
		}			
		return retorno;		
	}
	
	public boolean verificarExistenciaProduto(int id) throws DAOException{
		
		boolean retorno = false;
		String sql = "select ID from PRODUTO where ID=?";
		
		try {
			Connection con = dao.getConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			retorno = rs.next();
			rs.close();
			stmt.close();
			con.close();
			
		} catch (SQLException e) {
			throw new DAOException();
		}		
		return retorno;		
	}
}
