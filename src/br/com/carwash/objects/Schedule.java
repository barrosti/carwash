/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.carwash.objects;

import br.com.carwash.frames.SwingColumns;
import java.sql.Date;
import java.util.Objects;

/**
 *
 * @author alexandrebarros
 */
public class Schedule implements java.io.Serializable{

    private static final long serialVersionUID = 1L;
    
    private Integer id;
    
    @SwingColumns(description="Car",colorOfBackgound="")
    private Car car;
    
    @SwingColumns(description="Date",colorOfBackgound="")
    private Date date;
    
    private float total;
    
    @SwingColumns(description="Type",colorOfBackgound="")
    private ScheduleType type;
    
    public Schedule(){
    }

    public Schedule(Integer id, Car car, Date date, float total, ScheduleType type) {
        this.id = id;
        this.car = car;
        this.date = date;
        this.total = total;
        this.type = type;
    }
    

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public ScheduleType getType() {
        return type;
    }

    public void setType(ScheduleType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Schedule other = (Schedule) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        return hash;
    }

}
