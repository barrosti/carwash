/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.carwash.objects;

import br.com.carwash.frames.SwingColumns;
import java.util.Objects;

/**
 *
 * @author alexandrebarros
 */
public class SellItem implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    
    private Integer id;
    
    @SwingColumns(description="Product")
    private Product product;
    
    private Sell sell;
   
    @SwingColumns(description="Qnt")
    private int qnt;
   
    @SwingColumns(description="Total")
    private float totalOfItem;
    
    public SellItem(){
        super();
    }


    public SellItem(Integer id, Product product, Sell sell, int qnt) {
        this.id = id;
        this.product = product;
        this.sell = sell;
        this.qnt = qnt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQnt() {
        return qnt;
    }

    public void setQnt(int qnt) {
        this.qnt = qnt;
    }

    public Sell getSell() {
        return sell;
    }

    public void setSell(Sell sell) {
        this.sell = sell;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SellItem other = (SellItem) obj;
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

    public float getTotalOfItem() {
        if (getProduct() != null) {
            return getProduct().getPrice()*getQnt();
        }
        return totalOfItem;
    }

    public void setTotalOfItem(float totalOfItem) {
        this.totalOfItem = totalOfItem;
    }
    
}
