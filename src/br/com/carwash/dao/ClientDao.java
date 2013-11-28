/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.carwash.dao;

import br.com.carwash.objects.Client;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author alexandrebarros
 */
public class ClientDao extends GenericDao{
    
    private static final long serialVersionUID = 1L;
    
    private static ClientDao instance;
    
    public static final String NAME_TABLE = GenericDao.SCHEMA_BASE +".\"T_CLIENTS\"";
    
    private ClientDao(){}
        
    public static ClientDao getInstance(){
        if(instance == null)
            instance = new ClientDao();
        return instance;
    }    
    
    public int addClient(Client client) throws SQLException {
        Connection conn = getConnection();
        client.setId(getNextId(conn,"ID",NAME_TABLE));
        String query = "INSERT INTO "+ NAME_TABLE +" (\"ID\",\"NAME\",\"ADDRESS\",\"TELEPHONE\",\"EMAIL\") VALUES (?,?,?,?,?)";
        executeCommand(conn,query, client.getId(),client.getName(),client.getAddress(),client.getTelephone(),client.getEmail());
        closeConnection(conn);
        return client.getId();
    }

    public void removeClient(int idClient) throws SQLException {
        Connection conn = getConnection();
        executeCommand(conn,"DELETE FROM "+ NAME_TABLE +" WHERE \"ID\" = ?", idClient);
        closeConnection(conn);
    }

    public void updateClient(Client client) throws SQLException {
        Connection conn = getConnection();
        String query = "UPDATE "+ NAME_TABLE +" SET \"NAME\" = ?, \"ADDRESS\" = ?, \"TELEPHONE\" = ?, \"EMAIL\" = ? WHERE \"ID\" = ?";
        executeCommand(conn,query, client.getName(),client.getAddress(),client.getTelephone(),client.getEmail(),client.getId());
        closeConnection(conn);
    }

    public Client getClient(int idClient) throws SQLException {
        Connection conn = getConnection();
        ResultSet rs = executeQuery(conn,"SELECT * FROM "+ NAME_TABLE +" WHERE \"ID\" = ?", idClient);
        Client client = null;
        if (rs.next()) {
            client = fillClient(rs);
        }
        rs.close();
        closeConnection(conn);
        return client;
    }

    public List<Client> getAllClients() throws SQLException {
        Connection conn = getConnection();
        ResultSet rs = executeQuery(conn,"SELECT * FROM "+ NAME_TABLE );
        List<Client> toReturn = new LinkedList<Client>();
        while (rs.next()) {
            toReturn.add(fillClient(rs));
        }
        rs.close();
        closeConnection(conn);
        return toReturn;
    }

    public static Client fillClient(ResultSet rs) throws SQLException {
        Client toReturn = new Client();
        toReturn.setId(rs.getInt("ID"));
        toReturn.setName(rs.getString("NAME"));
        toReturn.setAddress(rs.getString("ADDRESS"));
        toReturn.setTelephone(rs.getString("TELEPHONE"));
        toReturn.setEmail(rs.getString("EMAIL"));
        return toReturn;
    }

    public List<Client> getClientsByName(String name) throws SQLException {
        Connection conn = getConnection();
        List<Client> toReturn = new LinkedList<Client>();
        ResultSet rs = executeQuery(conn,"SELECT * FROM "+ NAME_TABLE +" WHERE \"NAME\" LIKE ?", "%"+name+"%");
        while (rs.next()) {
            toReturn.add(fillClient(rs));
        }
        rs.close();
        closeConnection(conn);
        return toReturn;
    }    
}
