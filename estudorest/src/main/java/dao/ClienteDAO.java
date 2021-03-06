package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.ws.rs.core.Response.Status;

import entidades.Cliente;
import erros.DAOException;

@Dependent
public class ClienteDAO {
	
	@Inject
	private ConexaoDAO dao;	
	
	public int adicionarCliente(Cliente novoCliente) throws DAOException{
		
		int retorno = -1;
		String sql = "insert into CLIENTE (PRIMNOME,SEGNOME,RUA,CIDADE) values (?,?,?,?)";
		
		try {
			Connection con = dao.getConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			
			stmt.setString(1, novoCliente.getPrimeiroNome());
			stmt.setString(2, novoCliente.getSegundoNome());
			stmt.setString(3, novoCliente.getEndereco());
			stmt.setString(4, novoCliente.getCidade());
			
			retorno = stmt.executeUpdate();
			if(retorno == 0){
				throw new SQLException();
			}			
			stmt.close();
			con.close();
			
		} catch (SQLException e) {
			throw new DAOException();			
		}		
		return retorno;
	}
	
	
	
	public Cliente procurarCliente(int id) throws DAOException{
		
		Cliente retorno = null;
		String sql = "select * from CLIENTE where ID=?";
		
		try {
			Connection con = dao.getConexao();
			PreparedStatement  stmt = con.prepareStatement(sql);
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			if(rs.next() == false){
				rs.close();
				stmt.close();
				con.close();
				String msg = "O ID informado nao correspende a um usuario.";
				throw new DAOException(msg,Status.NOT_FOUND);
			}
			
			retorno = new Cliente(id);
			retorno.setPrimeiroNome(rs.getString(2));
			retorno.setSegundoNome(rs.getString(3));
			retorno.setEndereco(rs.getString(4));
			retorno.setCidade(rs.getString(5));
			
			rs.close();
			stmt.close();
			con.close();
			
		} catch (SQLException e) {
			throw new DAOException();
		}		
		return retorno;	
	}
	
	public Cliente procurarCliente(String primeiroNome, String segundoNome) throws DAOException{
		
		Cliente retorno = null;
		String sql = "select * from CLIENTE where PRIMNOME=? and SEGNOME=?";
		
		try {
			Connection con = dao.getConexao();
			PreparedStatement  stmt = con.prepareStatement(sql);
			stmt.setString(1, primeiroNome);
			stmt.setString(2, segundoNome);
			
			ResultSet rs = stmt.executeQuery();
			if(rs.next() == false){
				rs.close();
				stmt.close();
				con.close();
				String msg = "O Nome informado nao correspende a um Cliente.";
				throw new DAOException(msg,Status.NOT_FOUND);
			}
			
			retorno = new Cliente(rs.getInt(1));
			retorno.setPrimeiroNome(rs.getString(2));
			retorno.setSegundoNome(rs.getString(3));
			retorno.setEndereco(rs.getString(4));
			retorno.setCidade(rs.getString(5));
			
			rs.close();
			stmt.close();
			con.close();
			
		} catch (SQLException e) {
			throw new DAOException();
		}		
		return retorno;	
	}
	
	public List<Cliente> listarClientes() throws DAOException{
		List<Cliente> retorno = new ArrayList<Cliente>();
		Cliente cliente;
		String sql = "select * from CLIENTE";
		
		try {
			Connection con = dao.getConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){				
				cliente = new Cliente(rs.getInt(1));
				cliente.setPrimeiroNome(rs.getString(2));
				cliente.setSegundoNome(rs.getString(3));
				cliente.setEndereco(rs.getString(4));
				cliente.setCidade(rs.getString(5));
				
				retorno.add(cliente);
			}
			rs.close();
			stmt.close();
			con.close();			
		} catch (SQLException e) {
			throw new DAOException();
		}		
		return retorno;
	}
	
	public int deletarCliente(int id) throws DAOException{
		int retorno = -1;
		String sql = "delete from CLIENTE where ID=?";
		
		try {
			Connection con = dao.getConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			
			stmt.setInt(1, id);
			
			retorno = stmt.executeUpdate();
			stmt.close();
			con.close();
			if(retorno == 0){						
				String msg = "O ID informado não correspende a um Cliente.";
				throw new DAOException(msg,Status.NOT_FOUND);				
			}
		} catch (SQLException e) {			
			if(e.getSQLState().equals("23000")){
				String msg = "Este Cliente tem uma venda cadastrada. Clientes com vendas cadastradas nao podem ser deletados.";
				throw new DAOException(msg,Status.FORBIDDEN);
			}else{
				e.printStackTrace();
				throw new DAOException();
			}			
		}		
		return retorno;
	}
	
	public int deletarCliente(String primeiroNome, String segundoNome) throws DAOException{
		int retorno = -1;
		String sql = "delete from CLIENTE where PRIMNOME=? and SEGNOME=?";
		
		try {
			Connection con = dao.getConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			
			stmt.setString(1, primeiroNome);
			stmt.setString(2, segundoNome);
			
			retorno = stmt.executeUpdate();
			stmt.close();
			con.close();
			if(retorno == 0){						
				String msg = "O Nome informado nao correspende a um Cliente.";
				throw new DAOException(msg,Status.NOT_FOUND);				
			}
		} catch (SQLException e) {
			if(e.getSQLState().equals("23000")){
				String msg = "Este Cliente tem uma Venda cadastrada. Clientes com Vendas cadastradas nao podem ser deletados.";
				throw new DAOException(msg,Status.FORBIDDEN);
			}
			else{
				throw new DAOException();
			}	
		}
		
		return retorno;
	}
	
	public int atualizarCliente(int id, String novaRua, String novaCidade) throws DAOException{
		
		int retorno = -1;
		String sql = "update CLIENTE set RUA=?, CIDADE=? where ID=?";
		
		try {
			Connection con = dao.getConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			
			stmt.setString(1, novaRua);
			stmt.setString(2, novaCidade);
			stmt.setInt(3, id);
			
			retorno = stmt.executeUpdate();
			stmt.close();
			con.close();
			if(retorno == 0){
				String msg = "O ID informado nao corresponde a um Cliente.";
				throw new DAOException(msg,Status.NOT_FOUND);
			}
		} catch (SQLException e) {
			throw new DAOException();
		}
		
		return retorno;
		
	}
	
public int atualizarCliente(String primeiroNome,String segundoNome, String novaRua, String novaCidade) throws DAOException{
		
		int retorno = -1;
		String sql = "update CLIENTE set RUA=?, CIDADE=? where PRIMNOME=? and SEGNOME=?";
		
		try {
			Connection con = dao.getConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			
			stmt.setString(1, novaRua);
			stmt.setString(2, novaCidade);
			stmt.setString(3, primeiroNome);
			stmt.setString(4, segundoNome);
			
			retorno = stmt.executeUpdate();
			stmt.close();
			con.close();
			if(retorno == 0){
				String msg = "O nome informado nao corresponde a um Cliente.";
				throw new DAOException(msg,Status.NOT_FOUND);
			}
		} catch (SQLException e) {
			throw new DAOException();
		}
		
		return retorno;
		
	}
	
	public boolean verificarExistenciaCliente(int id) throws DAOException{
		boolean retorno = false;
		String sql = "select ID from CLIENTE where ID=?";
		
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
	
	public boolean verificarExistenciaCliente(String primeiroNome, String segundoNome){
		boolean retorno = false;
		String sql = "select ID from CLIENTE where PRIMNOME=? and SEGNOME=?";
		
		try {
			Connection con = dao.getConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1,primeiroNome);
			stmt.setString(2,segundoNome);
			
			ResultSet rs = stmt.executeQuery();
			retorno = rs.next();
			
			rs.close();
			stmt.close();
			con.close();
			
		} catch (SQLException e) {
			System.out.println("SQLException \n" + e.getMessage());
			e.printStackTrace();
		}
		
		return retorno;
	}
}
