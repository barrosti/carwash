/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.carwash.Util;

import java.util.List;

/**
 *
 * @author alexandrebarros
 */
public class Util {
    
    public static boolean isEmpty(String string){
        boolean isEmpty = false;
        if(string == null || string.trim().isEmpty() ){
            isEmpty = true;
        }
        return isEmpty; 
    }
    
   public static boolean isEmpty(List list){
        boolean isEmpty = false;
        if(list == null || list.isEmpty() ){
            isEmpty = true;
        }
        return isEmpty; 
    }
}
