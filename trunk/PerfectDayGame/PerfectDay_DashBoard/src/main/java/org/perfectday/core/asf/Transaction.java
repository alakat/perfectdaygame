/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.core.asf;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.apache.log4j.Logger;

/**
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class Transaction {

    private Method method;
    private Object object;

    public Transaction(Method method, Object object) {
        this.method = method;
        this.object = object;
    }
    
    
    public void doTransaction() throws IllegalAccessException, InvocationTargetException{
        Logger.getLogger(Transaction.class).info("Ejecutamos el metodo:"+method.getName()
                );
        method.invoke(object);
    }
    
}
