/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.carwash.frames;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alexandrebarros
 */
public class JTableControl extends DefaultTableModel {

    private static final long serialVersionUID = 1L;
    private final List entitysToList;
    private final Class entityClassToList;
    private List<Method> fieldToData = new LinkedList<Method>();
    private final JTable tableToControl;

    public JTableControl(Class entityClassToList, List entitysToList,JTable tableToControl) {
        super();
        this.entitysToList = entitysToList;
        this.entityClassToList = entityClassToList;
        this.tableToControl = tableToControl;
        try {
            startAddTheColumns();
            startAddValues();
        } catch (Exception ex) {
            Logger.getLogger(JTableControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    private void startAddTheColumns() throws NoSuchMethodException, InstantiationException, IllegalAccessException {
        // Here is The Reference Class !
        // Reflection
        for (Field field : entityClassToList.getDeclaredFields()) {
            SwingColumns theAnnotation = field.getAnnotation(SwingColumns.class);
            if (theAnnotation != null) {
                addColumn(theAnnotation.description());
                String methodName = "get" + field.getName().toUpperCase().charAt(0) + field.getName().substring(1);
                fieldToData.add(entityClassToList.getDeclaredMethod(methodName));
            }
        }
    }

    private void startAddValues() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        // Manipulates Only the DATA fields

        for (Object entity : entitysToList) {
            List<Object> valuesToAdd = new LinkedList<Object>();

            for (Method method : fieldToData) {
                valuesToAdd.add(method.invoke(entity));
            }

            // Here we add the values in the MODEL !
            addRow(valuesToAdd.toArray());
        }
    }
}
