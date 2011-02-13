/*
 * DashBoard.java
 *
 * Created on 15 de julio de 2008, 18:40
 */

package org.perfectday.dashboard.gui;

import es.bitsonfire.perfectday.gui.dialog.SimpleHelpDialog;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.util.HashMap;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.packet.Presence;
import org.perfectday.dashboard.gui.dialog.InitialSplashPage;
import org.perfectday.dashboard.threads.DashBoardThreadGroup;

import java.awt.Point;

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
        dialog.setLocation(GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint());
        loginPanel.setParent(dialog);
        loginPanel.requestFocus();
        dialog.setContentPane(loginPanel);
        dialog.pack();
        dialog.setVisible(true);
        this.setTitle(this.getTitle()+"("+loginPanel.getUser()+")");
        this.getWarRoom1().setConnection(loginPanel.getConnection());
        this.getWarRoom1().setChats( new HashMap<String,Chat>());
        for (RosterEntry rosterEntry : loginPanel.getRoster().getEntries()) {
            Presence presence = loginPanel.getRoster().getPresence(rosterEntry.getUser());
            System.out.println(rosterEntry.getUser()+"-->"+presence.getType()+":"+presence.getStatus()  );
            this.getWarRoom1().addChat(rosterEntry.getUser());
            
        }
       
        
        InitialSplashPage page;
		try {
			page = new InitialSplashPage(this, false, DashBoardThreadGroup.helper.getInformation(1, 0, 0).getCompletText(), DashBoardThreadGroup.helper.getInformation(1, 0, 1).getCompletText());
			page.setVisible(true);
			page.setLocation(new Point(300, 300));
			synchronized (this) {
				this.wait(5000);
			}
			page.dispose();
		} catch (InterruptedException e) {
			JOptionPane.showMessageDialog(this, "No dispone de informaci�n de ayuda");
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
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jmHelp = new javax.swing.JMenu();
        miHelp = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PerfectDay");
        getContentPane().add(warRoom1, java.awt.BorderLayout.CENTER);

        lAyuda.setText("Ayuda en l�nea");
        getContentPane().add(lAyuda, java.awt.BorderLayout.PAGE_END);

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jmHelp.setText("Ayuda");

        miHelp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, 0));
        miHelp.setText("Ayuda");
        miHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miHelpActionPerformed(evt);
            }
        });
        jmHelp.add(miHelp);

        jMenuBar1.add(jmHelp);

        setJMenuBar(jMenuBar1);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-701)/2, (screenSize.height-488)/2, 701, 488);
    }// </editor-fold>//GEN-END:initComponents

    private void miHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miHelpActionPerformed

        SimpleHelpDialog dialog = new SimpleHelpDialog(this, false);
        dialog.setVisible(true);
    }//GEN-LAST:event_miHelpActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jmHelp;
    private javax.swing.JLabel lAyuda;
    private javax.swing.JMenuItem miHelp;
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