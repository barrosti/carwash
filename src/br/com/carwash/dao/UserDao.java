/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.carwash.dao;

import br.com.carwash.objects.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author alexandrebarros
 */
public class UserDao extends GenericDao{
    
    private static final long serialVersionUID = 1L;
    
    private static UserDao instance;
    
    public static final String NAME_TABLE = GenericDao.SCHEMA_BASE +".\"T_USERS\"";
    
    private UserDao(){}
        
    public static UserDao getInstance(){
        if(instance == null)
            instance = new UserDao();
        return instance;
    }
    
    public int addUser(User usr) throws SQLException {
        Connection conn = getConnection();
        usr.setId(getNextId(conn,"ID",NAME_TABLE));
        String query = "INSERT INTO "+ NAME_TABLE +" (\"ID\",\"NAME\",\"LOGIN\",\"PASSWORD\") values (?,?,?,?)";
        executeCommand(conn,query, usr.getId(), usr.getName(), usr.getLogin(), usr.getPassword());
        closeConnection(conn);
        return usr.getId();
    }
    
    public boolean isValidLogin(String user,String password) throws SQLException {
        boolean toReturn = false;
        Connection conn = getConnection();
        ResultSet rs = executeQuery(conn, "SELECT * FROM "+ NAME_TABLE +" WHERE \"LOGIN\" = ? AND \"PASSWORD\" = ?",user,password);
        if (rs.next()) {
            toReturn = true;
        }
        rs.close();
        closeConnection(conn);
        return toReturn;
    }       

    public void updateUser(User usr) throws SQLException {
        Connection conn = getConnection();
        String query = "UPDATE "+ NAME_TABLE +" SET \"NAME\"=?,\"LOGIN\"=?,\"PASSWORD\"=? WHERE \"ID\" = ?";
        executeCommand(conn,query, usr.getName(), usr.getLogin(), usr.getPassword(), usr.getId());
        closeConnection(conn);
    }

    public User getUser(int idUser) throws SQLException {
        Connection conn = getConnection();
        ResultSet rs = executeQuery(conn,"SELECT * FROM "+ NAME_TABLE +" WHERE \"ID\" = ?", idUser);
        User usr = null;
        if (rs.next()) {
            usr = fillUser(rs);
        }
        rs.close();
        closeConnection(conn);
        return usr;
    }

    public List<User> getAllUsers() throws SQLException {
        Connection conn = getConnection();
        ResultSet rs = executeQuery(conn,"SELECT * FROM "+ NAME_TABLE );
        List<User> toReturn = new LinkedList<User>();
        while (rs.next()) {
            toReturn.add(fillUser(rs));
        }
        rs.close();
        closeConnection(conn);
        return toReturn;
    }
    
    public void removeUser(int idUser) throws SQLException {
        Connection conn = getConnection();
        executeCommand(conn,"DELETE FROM "+ NAME_TABLE +" WHERE \"ID\" = ?", idUser);
        closeConnection(conn);
    }    

    public static User fillUser(ResultSet rs) throws SQLException {
        User toReturn = new User();
        toReturn.setId(rs.getInt("ID"));
        toReturn.setLogin(rs.getString("LOGIN"));
        toReturn.setName(rs.getString("NAME"));
        toReturn.setPassword(rs.getString("PASSWORD"));
        return toReturn;
    }    
}
