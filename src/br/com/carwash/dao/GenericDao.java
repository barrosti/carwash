/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.carwash.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alexandrebarros
 */
public class GenericDao implements Serializable{
    
    private static final long serialVersionUID = 1L;

    protected final static String SCHEMA_BASE = "\"CARWASH\"";
    
    /**
     * Open connection with database
     * @return conn 
     */
    public Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection cx = DriverManager.getConnection("jdbc:postgresql://localhost:5432/WASH", "postgres", "postgres");
            return cx;
        } catch (Exception ex) {
            Logger.getLogger(GenericDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
        
    /**
     * Close connection with database
     * @param conn
     */
    public void closeConnection(Connection conn){
        try {
            if(conn!=null)
                conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
         
    public Statement getStatement(Connection conn) throws SQLException {
        return conn.createStatement();
    }
  
    public PreparedStatement getStatement(Connection conn,String st) throws SQLException {
        return conn.prepareStatement(st);
    }    

    public ResultSet executeQuery(Connection conn,String query,Object... params) throws SQLException {
        PreparedStatement ps = getStatement(conn,query);
        for (int i = 0; i < params.length; i++) {
            ps.setObject(i+1, params[i]);
        }
        return ps.executeQuery();
    }

    public int executeCommand(Connection conn,String query,Object... params) throws SQLException {
        PreparedStatement ps = getStatement(conn,query);
        for (int i = 0; i < params.length; i++) {
            try {
                 ps.setObject(i+1, params[i]);
            } catch (Exception e) {
                Logger.getLogger(GenericDao.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        int result = ps.executeUpdate();
        ps.close();
        return result;
    }

    public Integer getNextId(Connection conn,String id,String tableName) throws SQLException {
        ResultSet rs = executeQuery(conn,"select MAX(\""+id.toUpperCase()+"\") from "+tableName.toUpperCase());
        rs.next();
        Object result = rs.getObject(1);
        if (result == null) {
            rs.close();
            return 1;
        } else {
            return ((Integer)result)+1;
        }
    }           
}
