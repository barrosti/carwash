/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.carwash.objects;

/**
 *
 * @author alexandrebarros
 */
public enum ScheduleType {
    
    FULLWASH("Full Wash"),OUTWASH("Wash Out of Car only"), INTERNALWASH("Wash internal of car wash only");

    private String description;
    
    ScheduleType(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
