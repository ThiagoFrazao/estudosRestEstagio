package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.ws.rs.core.Response.Status;

import entidades.Cliente;
import entidades.Venda;
import erros.DAOException;

@Dependent
public class VendaDAO {
	
	@Inject
	private ConexaoDAO dao;	
	
	@Inject
	private ClienteDAO clientes;
	
	public int adicionarVenda(Venda novaVenda) throws DAOException{
		
		int retorno = -1;
		String sql = "insert into VENDA (CLIENTEID,TOTAL) values (?,?)";
		
		if(clientes.verificarExistenciaCliente(novaVenda.getId())){
			
			try {
				Connection con = dao.getConexao();
				PreparedStatement stmt = con.prepareStatement(sql);
				
				stmt.setInt(1, novaVenda.getCliente().getId());
				stmt.setFloat(2, novaVenda.getTotal());
				
				retorno = stmt.executeUpdate();				
				stmt.close();
				
			} catch (SQLException e) {
				throw new DAOException();
			}
		}else{
			String msg = "Toda Venda deve ter um Cliente cadastrado para ser realizada.";
			throw new DAOException(msg,Status.FORBIDDEN);
		}
		
		return retorno;
	}
	
	public int deletarVenda(int id) throws DAOException{
		int retorno = -1;
		String sql = "delete from VENDA where ID=?";
		
		try {
			Connection con = dao.getConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, id);
			
			retorno = stmt.executeUpdate();
			stmt.close();
			if(retorno == 0){
				String msg = "O ID informado nao corresponde a uma Venda.";
				throw new DAOException(msg,Status.NOT_FOUND);
			}
		} catch (SQLException e) {
			if(e.getSQLState().equals("23000")){
				String msg = "A Venda informada possui Itens cadastrados. Vendas com Itens nao podem ser deletadas.";
				throw new DAOException(msg,Status.FORBIDDEN);
			}else{
				throw new DAOException();	
			}			
		}		
		
		return retorno;
	}
	
	public int deletarVendasCliente(int idCliente) throws DAOException{
		int retorno = -1;
		String sql = "delete from VENDA where CLIENTEID=?";
		
		try {
			Connection con = dao.getConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, idCliente);
			
			retorno = stmt.executeUpdate();
			stmt.close();
			if(retorno == 0){
				String msg = "O Cliente informado nao possui Vendas cadastradas.";
				throw new DAOException(msg,Status.NOT_FOUND);				
			}
		} catch (SQLException e) {
			throw new DAOException();	
		}				
		return retorno;
	}
	
	public int deletarVendasCliente(String primeiroNome, String segundoNome) throws DAOException{
		int retorno = -1;
		String sql = "delete from VENDA where CLIENTEID=?";
		
		try {
			int idCliente = clientes.procurarCliente(primeiroNome, segundoNome).getId();
			Connection con = dao.getConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, idCliente);
			
			retorno = stmt.executeUpdate();
			stmt.close();
			if(retorno == 0){
				String msg = "O Cliente informado não possui Vendas cadastradas.";
				throw new DAOException(msg,Status.NOT_FOUND);
			}
		} catch (SQLException e) {
			if(e.getSQLState().equals("23000")){
				String msg = "As Vendas desse Cliente possuem Itens cadastrados. Vendas com Itens nao podem ser deletadas.";
				throw new DAOException(msg,Status.FORBIDDEN);
			}else{
				throw new DAOException();	
			}	
		}				
		return retorno;
	}
	
	public ArrayList<Venda> listarVendas() throws DAOException{
		Venda venda;
		Cliente cliente;
		float total;
		ArrayList<Venda> vendas = new ArrayList<Venda>();
		String sql = "select * from VENDA";
		
		try {
			Connection con = dao.getConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				venda = new Venda(rs.getInt(1));
				cliente = clientes.procurarCliente(rs.getInt(2));
				total = rs.getFloat(3);
				
				venda.setCliente(cliente);
				venda.setTotal(total);
				vendas.add(venda);
			}
			rs.close();
			stmt.close();
			con.close();
			
		} catch (SQLException e) {
			throw new DAOException();
		}		
		return vendas;	
	}
	
	
	public Venda procurarVenda(int id) throws DAOException{
		
		Venda retorno = null;
		Cliente cliente = null;
		String sql = "select * from VENDA where ID=?";
		
		try {
			Connection con = dao.getConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			

			
			if(!rs.next()){
				rs.close();
				stmt.close();
				String msg = "O ID informado não corresponde a uma Venda.";
				throw new DAOException(msg,Status.NOT_FOUND);
			}
			
			retorno = new Venda(id);
			cliente = this.clientes.procurarCliente(rs.getInt(2));
			
			retorno.setCliente(cliente);
			retorno.setTotal(rs.getFloat(3));
			
			rs.close();
			stmt.close();		
			con.close();
			
		} catch (SQLException e) {
			throw new DAOException();	
		} 
		
		return retorno;		
	}
	
	public ArrayList<Venda> procurarVendasCliente(int idCliente) throws DAOException{
		
		Venda venda = null;
		Cliente cliente = null;
		ArrayList<Venda> retorno = new ArrayList<Venda>();
		String sql = "select * from VENDA where CLIENTEID=?";
		
		try {
			Connection con = dao.getConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, idCliente);
			ResultSet rs = stmt.executeQuery();
			
			if(!rs.next()){
				rs.close();
				stmt.close();
				con.close();
				String msg = "O Cliente informado não possui Vendas cadastradas.";
				throw new DAOException(msg,Status.NOT_FOUND);
			}
			
			venda = new Venda(rs.getInt(1));
			cliente = this.clientes.procurarCliente(rs.getInt(2));
			
			venda.setCliente(cliente);
			venda.setTotal(rs.getFloat(3));
			retorno.add(venda);
			
			while(rs.next()){				
				venda = new Venda(rs.getInt(1));
				cliente = this.clientes.procurarCliente(rs.getInt(2));
				
				venda.setCliente(cliente);
				venda.setTotal(rs.getFloat(3));		
				retorno.add(venda);
			}
			
			rs.close();
			stmt.close();	
			con.close();			
		} catch (SQLException e) {
			throw new DAOException();	
		} 		
		return retorno;		
	}
	
	public ArrayList<Venda> procurarVendasCliente(String primeiroNome, String segundoNome) throws DAOException{
		
		Venda venda = null;
		Cliente cliente = null;
		ArrayList<Venda> retorno = new ArrayList<Venda>();
		String sql = "select * from VENDA where CLIENTEID=?";
		
		try {			
			cliente = clientes.procurarCliente(primeiroNome, segundoNome);
			int idCliente = cliente.getId();
			
			Connection con = dao.getConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, idCliente);
			ResultSet rs = stmt.executeQuery();
			
			if(!rs.next()){
				rs.close();
				stmt.close();
				con.close();
				String msg = "O Cliente informado não possui Vendas cadastradas.";
				throw new DAOException(msg,Status.NOT_FOUND);
			}
			
			venda = new Venda(rs.getInt(1));
			cliente = this.clientes.procurarCliente(rs.getInt(2));
			
			venda.setCliente(cliente);
			venda.setTotal(rs.getFloat(3));
			retorno.add(venda);
			
			while(rs.next()){				
				venda = new Venda(rs.getInt(1));
				cliente = this.clientes.procurarCliente(rs.getInt(2));
				
				venda.setCliente(cliente);
				venda.setTotal(rs.getFloat(3));		
				retorno.add(venda);
			}
			
			rs.close();
			stmt.close();	
			con.close();
			
		} catch (SQLException e) {
			throw new DAOException();	
		}	
		
		return retorno;		
	}
	
	public float totalVendasCliente(int idCliente) throws DAOException{
		float retorno = 0;
		String sql = "select TOTAL from VENDA where CLIENTEID=?";		
		
		try {
			Connection con = dao.getConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, idCliente);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()){
				retorno += rs.getFloat(1);
			}else{
				rs.close();
				stmt.close();
				con.close();
				String msg = "O Cliente informado não possui Vendas cadastradas.";
				throw new DAOException(msg,Status.NOT_FOUND);
			}
			
			while(rs.next()){
				retorno += rs.getFloat(1);
			}
			
			rs.close();
			stmt.close();	
			con.close();
			
		} catch (SQLException e) {
			throw new DAOException();	
		}
		
		
		return retorno;
	}
	
	public float totalVendasCliente(String primeiroNome, String segundoNome) throws DAOException{
		float retorno = 0;
		String sql = "select TOTAL from VENDA where CLIENTEID=?";		
		
		try {
			Cliente cliente = clientes.procurarCliente(primeiroNome, segundoNome);
			int idCliente = cliente.getId();
			
			Connection con = dao.getConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, idCliente);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()){
				retorno += rs.getFloat(1);
			}else{
				rs.close();
				stmt.close();
				con.close();
				String msg = "O Cliente informado não possui Vendas cadastradas.";
				throw new DAOException(msg,Status.NOT_FOUND);
			}
			
			while(rs.next()){
				retorno += rs.getFloat(1);
			}
			
			rs.close();
			stmt.close();
			con.close();
			
		} catch (SQLException e) {
			System.out.println("SQLException " + e.getMessage());
			e.printStackTrace();
			throw new DAOException();	
		}
		
		
		return retorno;
	}
	
	public int atualizarVenda(int id, float total) throws DAOException{
		int retorno = -1;
		String sql = "update PRODUTO set TOTAL=? where ID=?";
		
		try {
			Connection con = dao.getConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			
			stmt.setFloat(1, total);
			stmt.setInt(2, id);
			
			retorno = stmt.executeUpdate();
			stmt.close();
			con.close();
			
			if(retorno == 0){
				String msg = "O ID informado não corresponde a uma Venda.";
				throw new DAOException(msg,Status.NOT_FOUND);				
			}
		} catch (SQLException e) {
			throw new DAOException();			
		}		
		return retorno;
	}
	
	public boolean verificarExistencia(int id) throws DAOException{
		boolean retorno = false;
		String sql = "select ID from VENDA where ID=? ";
		
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
