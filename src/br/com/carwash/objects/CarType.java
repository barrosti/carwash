/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.carwash.objects;

/**
 *
 * @author alexandrebarros
 */
public enum CarType {
    
    COUPE("Coupe"),SEDAN("Sedan"),TRUCK("Truck"),SUV("Suv"),VAN("Van"),WAGON("Wagon");
    
    private String description;
    
    CarType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
