/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.dashboard.gui.model;

import java.util.EventListener;

/**
 *
 * @author Lobo
 */
interface InterfaceDialogListener extends EventListener{
    public String capturarLoadingDialogEvent( LoadingDialogEvent evt );

}
