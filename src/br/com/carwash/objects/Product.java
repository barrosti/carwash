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
public class Product implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @SwingColumns(description="Id",colorOfBackgound="")
    private Integer id;
    
    @SwingColumns(description="Name",colorOfBackgound="")
    private String name;
    
    @SwingColumns(description="Price",colorOfBackgound="")
    private float price;
    
    @SwingColumns(description="# in Stock",colorOfBackgound="")
    private int stock;
    
    public Product(){
        super();
    }

    public Product(Integer id, String name, float price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public String toString() {
        return getName();
    }

}
