/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.core.asf;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.perfectday.core.asf.exception.AutomatonException;

/**
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class State {
    private static final Logger logger = Logger.getLogger(State.class);
    private String name;
    private List<State> movements;
    private List<Transaction> transactions;

    public State(String name, List<State> movements, List<Transaction> transactions) throws AutomatonException {
        this.name = name;
        this.movements = movements;
        this.transactions = transactions;
        if(this.movements.size() != this.transactions.size())
            throw new AutomatonException("No son validas las listas de estados y transacciones, distinto tamaño");
    }

    public State(String name) {
        this.name = name;
        this.movements = new ArrayList<State>();
        this.transactions = new ArrayList<Transaction>();
    }
    
    

    public List<State> getMovements() {
        return movements;
    }

    public void setMovements(List<State> movements) {
        this.movements = movements;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
    
    public void move(State state) throws IllegalAccessException, InvocationTargetException{
        logger.info("Move to :"+state.getName());
        boolean find = false;
        int idx = 0;
        for(; idx<this.movements.size();idx++){
            if(this.movements.get(idx).getName().equals(state.getName())){
                find = true;                
                break;
            }
        }
        if(find){
            logger.info("Se ejecuta la transacción"+idx);
            this.transactions.get(idx).doTransaction();            
        }else{
            logger.warn("NO transaction finding");  
        }
    }
    
    

}
