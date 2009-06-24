/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.dashboard.gui.model;

import java.util.EventObject;
import org.perfectday.core.asf.FiniteAutomatonState;

/**
 *
 * @author Lobo
 */
public class LoadingDialogEvent extends EventObject {
    private String description;
    private String id;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    // Constructor parametrizado
   public LoadingDialogEvent( FiniteAutomatonState obj,String id ) {
        // Se le pasa el objeto como parametro a la superclase
        super( obj );
        // Se guarda el identificador del objeto
        this.id = id;
        }

    // Método para recuperar el identificador del objeto
    String getEventoID() {
        return( id );
        }

    

}
