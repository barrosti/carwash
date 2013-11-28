/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.carwash.dao;

import br.com.carwash.objects.Sell;
import br.com.carwash.objects.SellItem;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author alexandrebarros
 */
public class SellDao extends GenericDao{
    
    private static final long serialVersionUID = 1L;
    
    private static SellDao instance;
    
    public static final String NAME_TABLE_SELLS = GenericDao.SCHEMA_BASE +".\"T_SELLS\"";
    
    public static final String NAME_TABLE_SELLSITEM = GenericDao.SCHEMA_BASE +".\"T_SELLSITEM\"";
    
    private SellDao(){}
        
    public static SellDao getInstance(){
        if(instance == null)
            instance = new SellDao();
        return instance;
    }     
    
  public int addSell(Sell sell) throws SQLException{
      return addSell(sell, true);
  }  
    
  public int addSell(Sell sl, boolean cascade) throws SQLException {
        Connection conn = getConnection();
        sl.setId(getNextId(conn,"ID",NAME_TABLE_SELLS));
        String query = "INSERT INTO "+ NAME_TABLE_SELLS +" (\"ID\",\"ID_VENDOR\",\"DATEOFSALE\",\"TOTAL\") values (?,?,?,?)";
        
        float totalOfSale = 0f;
        for (SellItem item : sl.getItens()) {
            totalOfSale += item.getProduct().getPrice() * item.getQnt();
        }
        
        executeCommand(conn,query, sl.getId(), sl.getUser().getId(), sl.getDataOfSale(), totalOfSale);
        if (cascade) {
            
            for (SellItem item : sl.getItens()) {
                addSellItem(conn,item);
            }
        }
        closeConnection(conn);
        return sl.getId();
    }

    public int addSellItem(Connection conn,SellItem sli) throws SQLException {
        sli.setId(getNextId(conn,"ID",NAME_TABLE_SELLSITEM));
        String query = "INSERT INTO "+ NAME_TABLE_SELLSITEM +" (\"ID\",\"ID_PRODUCT\",\"QNT\",\"ID_SELL\") values (?,?,?,?)";
        executeCommand(conn,query, sli.getId(), sli.getProduct().getId(), sli.getQnt(), sli.getSell().getId());
        return sli.getId();
    }

    public void updateSell(Sell sell) throws SQLException{
        updateSell(sell, true);
    }
    
    public void updateSell(Sell sl, boolean cascade) throws SQLException {
        Connection conn = getConnection();
        String query = "UPDATE "+ NAME_TABLE_SELLS +" SET \"ID_VENDOR\"=?,\"TOTAL\"=? WHERE \"ID\" = ?";
        
        float totalOfSale = 0f;
        for (SellItem item : sl.getItens()) {
            totalOfSale += item.getProduct().getPrice() * item.getQnt();
        }
        executeCommand(conn,query, sl.getUser().getId(), totalOfSale, sl.getId());
        if (cascade) {
            for (SellItem item : sl.getItens()) {
                updateSellItem(conn,item);
            }
        }
        closeConnection(conn);
    }

    public void updateSellItem(Connection conn,SellItem sli) throws SQLException {
        String query = "UPDATE "+ NAME_TABLE_SELLSITEM +" SET \"ID_PRODUCT\" = ?,\"QNT\"=? WHERE \"ID\" = ?";
        executeCommand(conn,query, sli.getProduct().getId(), sli.getQnt(), sli.getId());
    }

    public void removeSell(int idSell) throws SQLException {
        Connection conn = getConnection();
        executeCommand(conn,"DELETE FROM "+ NAME_TABLE_SELLSITEM +" where \"ID_SELL\" = ?", idSell);
        executeCommand(conn,"DELETE FROM "+ NAME_TABLE_SELLS +" WHERE \"ID\" = ?", idSell);
        closeConnection(conn);
    }

    public void removeSellItem(int idSellItem) throws SQLException {
        Connection conn = getConnection();
        executeCommand(conn,"DELETE FROM "+ NAME_TABLE_SELLSITEM +" where \"ID\" = ?", idSellItem);
        closeConnection(conn);
    }

    public Sell getSell(int idSell) throws SQLException {
        Connection conn = getConnection();
        ResultSet rs = executeQuery(conn,"SELECT * FROM "+ NAME_TABLE_SELLS +" WHERE \"ID\" = ?", idSell);
        Sell sl = null;
        if (rs.next()) {
            sl = fillSell(rs, true);
        }
        rs.close();
        closeConnection(conn);
        return sl;
    }

    public List<Sell> getAllSells() throws SQLException {
        Connection conn = getConnection();
        ResultSet rs = executeQuery(conn,"SELECT * FROM "+ NAME_TABLE_SELLS );
        List<Sell> toReturn = new LinkedList<Sell>();
        while (rs.next()) {
            toReturn.add(fillSell(rs));
        }
        rs.close();
        closeConnection(conn);
        return toReturn;
    }
    
    public Sell fillSell(ResultSet rs) throws SQLException {
        return fillSell(rs, true);
    }

    public Sell fillSell(ResultSet rs, boolean populateItens) throws SQLException {
        Sell toReturn = new Sell();
        toReturn.setId(rs.getInt("ID"));
        toReturn.setUser(UserDao.getInstance().getUser(rs.getInt("ID_VENDOR")));
        toReturn.setDataOfSale(rs.getDate("DATEOFSALE"));
        toReturn.setTotal(rs.getFloat("TOTAL"));
        if (populateItens) {
            toReturn.setItens(getSellItens(toReturn));
        }
        return toReturn;
    }

    public SellItem getSellItem(int idSellItem) throws SQLException {
        Connection conn = getConnection();
        ResultSet rs = executeQuery(conn,"SELECT * FROM "+ NAME_TABLE_SELLSITEM +" where \"ID\" = ?", idSellItem);
        SellItem sli = null;
        if (rs.next()) {
            sli = fillSellItem(rs);
        }
        rs.close();
        closeConnection(conn);
        return sli;
    }

    public List<SellItem> getSellItens(Sell sell) throws SQLException {
        Connection conn = getConnection();
        List<SellItem> toReturn = new LinkedList<SellItem>();
        ResultSet rs = executeQuery(conn,"SELECT * from "+ NAME_TABLE_SELLSITEM +" where \"ID_SELL\" = ?", sell.getId());
        while (rs.next()) {
            toReturn.add(fillSellItem(rs, sell));
        }
        rs.close();
        closeConnection(conn);
        return toReturn;
    }

    public SellItem fillSellItem(ResultSet rs, Sell... sell) throws SQLException {
        SellItem toReturn = new SellItem();
        toReturn.setId(rs.getInt("ID"));
        toReturn.setQnt(rs.getInt("QNT"));
        toReturn.setProduct(ProductDao.getInstance().getProduct(rs.getInt("ID_PRODUCT")));
        if (sell != null && sell.length >0 ) {
            toReturn.setSell(sell[0]);
        } else {
            toReturn.setSell(getSell(rs.getInt("ID_SELL")));
        }
        return toReturn;
    }    
}
