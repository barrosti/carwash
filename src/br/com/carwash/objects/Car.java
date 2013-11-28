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
public class Car implements java.io.Serializable{
    
    private static final long serialVersionUID = 1L;
  
    @SwingColumns(description="Id",colorOfBackgound="")
    private Integer id;
    
    @SwingColumns(description="Client",colorOfBackgound="")
    private Client client;
    
    @SwingColumns(description="Plaque",colorOfBackgound="")
    private String plaque;
    
    private CarType type;
    
    @SwingColumns(description="Mark",colorOfBackgound="")
    private String mark;
    
    @SwingColumns(description="Model",colorOfBackgound="")
    private String model;
    
    @SwingColumns(description="Year",colorOfBackgound="")
    private Integer year;    

    public Integer getId() {
        return id;
    }

    public Car(Integer id, Client client, String plaque, CarType type, String mark, String model, Integer year) {
        this.id = id;
        this.client = client;
        this.plaque = plaque;
        this.type = type;
        this.mark = mark;
        this.model = model;
        this.year = year;
    }
    
    public Car(){
        super();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getPlaque() {
        return plaque;
    }

    public void setPlaque(String plaque) {
        this.plaque = plaque;
    }

    public CarType getType() {
        return type;
    }

    public void setType(CarType type) {
        this.type = type;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Car{" + "mark=" + mark + ", model=" + model + ", year=" + year + '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Car other = (Car) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
        return hash;
    }
}
