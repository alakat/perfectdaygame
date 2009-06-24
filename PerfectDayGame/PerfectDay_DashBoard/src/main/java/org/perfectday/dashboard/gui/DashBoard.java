/*
 * DashBoard.java
 *
 * Created on 15 de julio de 2008, 18:40
 */

package org.perfectday.dashboard.gui;

import java.util.HashMap;
import javax.swing.JDialog;
import javax.swing.JLabel;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.RosterEntry;
import org.perfectday.logicengine.core.Game;

/**
 *
 * @author  Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class DashBoard extends javax.swing.JFrame {

    
    /** Creates new form DashBoard */
    public DashBoard() {
        initComponents();
        LoginPanel loginPanel = new LoginPanel(this);
        JDialog dialog = new JDialog(this, "Login", true);
        loginPanel.setParent(dialog);        
        dialog.setContentPane(loginPanel);
        dialog.pack();
        dialog.setVisible(true);
        this.setTitle(this.getTitle()+"("+loginPanel.getUser()+")");
        this.getWarRoom1().setConnection(loginPanel.getConnection());
        this.getWarRoom1().setChats( new HashMap<String,Chat>());
        for (RosterEntry rosterEntry : loginPanel.getRoster().getEntries()) {
            this.getWarRoom1().addChat(rosterEntry.getUser());
        }
        warRoom1.repaint();
        warRoom1.setDashBoard(this);
       
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        warRoom1 = new org.perfectday.dashboard.gui.WarRoom();
        lAyuda = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PerfectDay");
        getContentPane().add(warRoom1, java.awt.BorderLayout.CENTER);

        lAyuda.setText("Ayuda en línea");
        getContentPane().add(lAyuda, java.awt.BorderLayout.PAGE_END);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-701)/2, (screenSize.height-488)/2, 701, 488);
    }// </editor-fold>//GEN-END:initComponents

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
            Game.getInstance_();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DashBoard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lAyuda;
    private org.perfectday.dashboard.gui.WarRoom warRoom1;
    // End of variables declaration//GEN-END:variables

    public JLabel getLAyuda() {
        return lAyuda;
    }

    public void setLAyuda(JLabel lAyuda) {
        this.lAyuda = lAyuda;
    }

    public WarRoom getWarRoom1() {
        return warRoom1;
    }

    public void setWarRoom1(WarRoom warRoom1) {
        this.warRoom1 = warRoom1;
    }

    
}
