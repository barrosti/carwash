/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.carwash.objects;

import br.com.carwash.frames.SwingColumns;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author alexandrebarros
 */
public class Sell implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
   
    @SwingColumns(description="Id")
    private Integer id;
   
    @SwingColumns(description="User")
    private User user;
   
    @SwingColumns(description="Date of Sale")
    private Date dataOfSale;
    
    @SwingColumns(description="Total")
    private float total;
    
    private List<SellItem> itens = new LinkedList<SellItem>();

    public Sell(){
        super();
    }


    public Sell(Integer id, User user, Date dataOfSale, float total) {
        this.id = id;
        this.user = user;
        this.dataOfSale = dataOfSale;
        this.total = total;
    }

    
    public Date getDataOfSale() {
        return dataOfSale;
    }

    public void setDataOfSale(Date dataOfSale) {
        this.dataOfSale = dataOfSale;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sell other = (Sell) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.id);
        return hash;
    }

    public List<SellItem> getItens() {
        return itens;
    }

    public void setItens(List<SellItem> itens) {
        this.itens = itens;
    }

}
