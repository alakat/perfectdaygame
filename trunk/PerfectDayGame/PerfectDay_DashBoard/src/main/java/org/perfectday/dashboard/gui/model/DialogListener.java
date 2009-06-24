/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.dashboard.gui.model;

/**
 *
 * @author Lobo
 */
public class DialogListener implements InterfaceDialogListener {

    public String capturarLoadingDialogEvent(LoadingDialogEvent evt) {
        return evt.getDescription();
    }

}
