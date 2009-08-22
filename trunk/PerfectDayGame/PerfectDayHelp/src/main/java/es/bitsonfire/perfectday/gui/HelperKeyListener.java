/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.bitsonfire.perfectday.gui;

import es.bitsonfire.perfectday.model.ActionHelpInformation;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;

/**
 *
 * Esta clase se añade como listener de cualquier componente el cual, pulsando
 * CTRL+i muestre un diálogo con información sobre el ficho componente
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class HelperKeyListener implements KeyListener {

    private ActionHelpInformation information;

    /**
     *
     * @param information
     */
    public HelperKeyListener(ActionHelpInformation information) {
        this.information = information;
    }



    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("Evento typed!");
        if(e.isControlDown() && e.getKeyChar()=='i'){
            JOptionPane.showMessageDialog(null, information.getCompletText(), information.getTitle(), JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Evento keyPressed!");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("Evento keyReleased!");
    }


}
