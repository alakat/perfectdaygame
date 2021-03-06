/*
 * ChatPanel.java
 *
 * Created on 16 de julio de 2008, 17:46
 */

package org.perfectday.dashboard.gui;

import es.bitsonfire.perfectday.gui.HelperKeyListener;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import org.apache.log4j.Logger;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;
import org.perfectday.dashboard.exception.GameBuilderException;
import org.perfectday.dashboard.threads.DashBoardThreadGroup;
import org.perfectday.gamebuilder.GameBuilder;
import org.perfectday.gamebuilder.GameBuilderFactory;
import org.perfectday.gamebuilder.model.BattleDescription;

/**
 *
 * @author  Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class ChatPanel extends javax.swing.JPanel {

    private Chat chat;
    private static final Logger logger = Logger.getLogger(ChatPanel.class);
    /** Creates new form ChatPanel */
    public ChatPanel(Chat c) {
        initComponents();
        this.taSendToText.addKeyListener(new HelperKeyListener(DashBoardThreadGroup.helper.getInformation(0, 0, 0)));
        this.chat = c;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        spChat = new javax.swing.JScrollPane();
        tzChat = new javax.swing.JTextPane();
        botonera = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        taSendToText = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        bEnviar = new javax.swing.JButton();
        bBatalla = new javax.swing.JButton();

        jMenuItem1.setText("jMenuItem1");
        jPopupMenu1.add(jMenuItem1);

        setLayout(new java.awt.BorderLayout());

        spChat.setAutoscrolls(true);

        tzChat.setEditable(false);
        spChat.setViewportView(tzChat);

        add(spChat, java.awt.BorderLayout.CENTER);

        botonera.setLayout(new java.awt.BorderLayout());

        taSendToText.setColumns(20);
        taSendToText.setRows(5);
        taSendToText.setMinimumSize(new java.awt.Dimension(600, 400));
        taSendToText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                taSendToTextKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(taSendToText);

        botonera.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new java.awt.GridLayout(2, 2, 30, 5));

        bEnviar.setText("Enviar");
        bEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEnviarActionPerformed(evt);
            }
        });
        jPanel1.add(bEnviar);

        bBatalla.setText("Batalla");
        bBatalla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bBatallaActionPerformed(evt);
            }
        });
        jPanel1.add(bBatalla);

        botonera.add(jPanel1, java.awt.BorderLayout.LINE_END);

        add(botonera, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

private void bEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEnviarActionPerformed
    this.printMessage(this.taSendToText.getText()+"\n","Yo");
    try {
        chat.sendMessage(this.taSendToText.getText()+"\n");
    } catch (XMPPException ex) {
        Logger.getLogger(ChatPanel.class).info("Error al mandar el mensaje "+this.taSendToText.getText());
    }
    this.taSendToText.setText("");
}//GEN-LAST:event_bEnviarActionPerformed

private void bBatallaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bBatallaActionPerformed
    if (Thread.currentThread().getThreadGroup() instanceof DashBoardThreadGroup) {
        DashBoardThreadGroup dashBoardThreadGroup = 
                (DashBoardThreadGroup) Thread.currentThread().getThreadGroup();
        if(dashBoardThreadGroup.inGame()){
            JOptionPane.showMessageDialog(this,
                    "Ya existe una partida en ejecuci�n",
                    "PerfectDay. Informaci�n",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
    }

    DescriptionBattleDialog dbd = new DescriptionBattleDialog(null, true);
    dbd.setVisible(true);
    BattleDescription bd = null;
    if(dbd.isAcepted()){        
            try {
                bd = dbd.getBattleDescription();
                Logger.getLogger(ChatPanel.class).info("Mission:" + bd.getMission());
                Logger.getLogger(ChatPanel.class).info("Public:" + bd.isBattlePublic());
                Logger.getLogger(ChatPanel.class).info("Point:" + bd.getPoint());
                GameBuilder bg = GameBuilderFactory.getInstance().createGameBuilderServer(chat.getParticipant());
                bg.setBattleDescription(bd);
                bg.getCommunication().setChat(chat);
                try {   
                    bg.move();
                } catch (IllegalAccessException ex) {
                    logger.error("Error de acceso ilegal",ex);
                    GameBuilderFactory.getInstance().delete(chat.getParticipant());
                    JOptionPane.showMessageDialog(this, "Error en las comunicaciones","Ha sido imposible iniciar el juego",JOptionPane.ERROR_MESSAGE);
                } catch (InvocationTargetException ex) {
                    logger.error("invocacion ilegal",ex);
                    GameBuilderFactory.getInstance().delete(chat.getParticipant());
                    JOptionPane.showMessageDialog(this, "Error en las comunicaciones","Ha sido imposible iniciar el juego",JOptionPane.ERROR_MESSAGE);
                }
            } catch (GameBuilderException ex) {
                JOptionPane.showMessageDialog(this,ex.getMessage(), "PerfectDay. Informaci�n",JOptionPane.INFORMATION_MESSAGE);
            } catch (NoSuchMethodException ex) {
                logger.error("Error al crear el constructor de partidas",ex);
                JOptionPane.showMessageDialog(this, "Error en las comunicaciones","Ha sido imposible iniciar el juego",JOptionPane.ERROR_MESSAGE);
            }
    }else{
        Logger.getLogger(ChatPanel.class).info("Batalla cancelada");
    }
}//GEN-LAST:event_bBatallaActionPerformed

private void taSendToTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_taSendToTextKeyPressed
// TODO add your handling code here:
    
    if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        evt.consume();//elimina el evento enter, así se evita que se quede el \n en el texto
        String message = this.taSendToText.getText();
        this.taSendToText.setText("");      
        this.printMessage(message+"\n","Yo");
        try {
            chat.sendMessage(message+"\n");
        } catch (XMPPException ex) {
             Logger.getLogger(ChatPanel.class).info("Error al mandar el mensaje "+message);
        }
       
   }
}//GEN-LAST:event_taSendToTextKeyPressed

public void printMessage(String message,String from){
    StyledDocument doc = this.tzChat.getStyledDocument();
   // message.replace('\n',' ');
    
    try {
      doc.insertString(doc.getLength(), from + ": " + message, doc.getStyle(from + ": " + message));
      
    } catch (BadLocationException ex) {
        Logger.getLogger(ChatPanel.class).error("Error en texto ",ex);
    }
    
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bBatalla;
    private javax.swing.JButton bEnviar;
    private javax.swing.JPanel botonera;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane spChat;
    private javax.swing.JTextArea taSendToText;
    private javax.swing.JTextPane tzChat;
    // End of variables declaration//GEN-END:variables

}
