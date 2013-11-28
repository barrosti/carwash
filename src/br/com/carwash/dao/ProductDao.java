/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.carwash.dao;

import br.com.carwash.objects.Product;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author alexandrebarros
 */
public class ProductDao extends GenericDao{
    
    private static final long serialVersionUID = 1L;
    
    private static ProductDao instance;
    
    public static final String NAME_TABLE = GenericDao.SCHEMA_BASE +".\"T_PRODUCTS\"";
    
    private ProductDao(){}
        
    public static ProductDao getInstance(){
        if(instance == null)
            instance = new ProductDao();
        return instance;
    }      
    
    public int addProduct(Product prod) throws SQLException {
        Connection conn = getConnection();
        prod.setId(getNextId(conn,"ID",NAME_TABLE));
        String query = "INSERT INTO "+ NAME_TABLE +" (\"ID\",\"NAME\",\"PRICE\",\"STOCK\") values (?,?,?,?)";
        executeCommand(conn,query, prod.getId(),prod.getName(),prod.getPrice(),prod.getStock());
        closeConnection(conn);
        return prod.getId();
    }

    public void removeProduct(int idProduct) throws SQLException {
        Connection conn = getConnection();
        executeCommand(conn,"DELETE FROM "+ NAME_TABLE +" WHERE \"ID\" = ?", idProduct);
        closeConnection(conn);
    }

    public void updateProduct(Product prod) throws SQLException {
        Connection conn = getConnection();
        String query = "UPDATE "+ NAME_TABLE +" SET \"NAME\"=?,\"PRICE\"=?,\"STOCK\"=? WHERE \"ID\" = ?";
        executeCommand(conn,query, prod.getName(),prod.getPrice(),prod.getStock(),prod.getId());
        closeConnection(conn);
    }

    public Product getProduct(int idProduct) throws SQLException {
        Connection conn = getConnection();
        ResultSet rs = executeQuery(conn,"SELECT * FROM "+ NAME_TABLE +" WHERE \"ID\" = ?", idProduct);
        Product prod = null;
        if (rs.next()) {
           prod = fillProduct(rs);
        }
        rs.close();
        closeConnection(conn);
        return prod;
    }

    public static Product fillProduct(ResultSet rs) throws SQLException {
        Product toReturn = new Product();
        toReturn.setId(rs.getInt("ID"));
        toReturn.setName(rs.getString("NAME"));
        toReturn.setPrice(rs.getFloat("PRICE"));
        toReturn.setStock(rs.getInt("STOCK"));
        return toReturn;
    }

    public List<Product> getAllProducts() throws SQLException {
        Connection conn = getConnection();
        List<Product> toReturn = new LinkedList<Product>();
        ResultSet rs = executeQuery(conn, "SELECT * FROM "+ NAME_TABLE );
        while (rs.next()) {
            toReturn.add(fillProduct(rs));
        }
        closeConnection(conn);
        return toReturn;
    }    
}
