/*
 * WarRoom.java
 *
 * Created on 15 de julio de 2008, 18:37
 */

package org.perfectday.dashboard.gui;

import com.thoughtworks.xstream.XStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import org.apache.log4j.Logger;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.perfectday.dashboard.communication.GameBuilderCommunicator;
import org.perfectday.dashboard.communication.MessageChatListener;
import org.perfectday.communication.model.plugcommunication.PerfectDayMessage;
import org.perfectday.dashboard.communication.model.PerfectDayMessageFactory;
import org.perfectday.dashboard.exception.GameBuilderException;
import org.perfectday.gamebuilder.GameBuilder;
import org.perfectday.gamebuilder.GameBuilderFactory;
import org.perfectday.gamebuilder.model.BattleDescription;

/**
 *
 * @author  Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class WarRoom extends javax.swing.JPanel {

    private static final Logger logger = Logger.getLogger(WarRoom.class);
    private XMPPConnection connection;
    private Map<RosterEntry,Chat> chats;
    private Map<RosterEntry,ChatPanel> chatPannels;
    private List<RosterEntry> rosterEntries;
    private DashBoard dashBoard;
    /** Creates new form WarRoom */
    public WarRoom() {
        initComponents();
        chatPannels = new HashMap<RosterEntry,ChatPanel>();
        this.rosterEntries = new ArrayList<RosterEntry>();
    }

    
    
    private boolean exitsChat(String user) {
        int size = this.chatTabbedPane.getTabCount();
        for (int i = 0; i < size; i++) {
            if(this.chatTabbedPane.getTitleAt(i).equals(user))
                return true;            
        }
        return false;
    }

    private int indexTab(String user) {
        int size = this.chatTabbedPane.getTabCount();
        for (int i = 0; i < size; i++) {
            if(this.chatTabbedPane.getTitleAt(i).equals(user))
                return i;            
        }
        return -1;
    }

    
    
    public class ListRosterModel implements ListModel{

        private List<String> data;
       
       
        public ListRosterModel() {
            data = new ArrayList<String>();
        }        
        
        
        public int getSize() {
           return  data.size();
        }

        public Object getElementAt(int arg0) {
           return data.get(arg0);
        }
        
        public void addElement(String newData){
            this.data.add(newData);
        }

        public void addListDataListener(ListDataListener arg0) {
           
        }

        public void removeListDataListener(ListDataListener arg0) {
           
        }
        
    }


    public void incommingMessage(String user, String body) {
        RosterEntry rosterEntry = this.getRosterEntryByUser(user);
        if (this.chatPannels.containsKey(rosterEntry)){
            this.chatPannels.get(rosterEntry).printMessage(body,rosterEntry.getName());
        }else{
            ChatPanel cp = new ChatPanel(chats.get(rosterEntry));        
            this.chatTabbedPane.addTab(rosterEntry.getName(), cp);
            this.chatPannels.put(rosterEntry, cp);
            cp.printMessage(body, rosterEntry.getName());

        }
    }

    void addChat(String user) {
        throw new RuntimeException("M�todo en desuso");
//        this.chats.put(null,
//                this.connection.getChatManager().createChat(user, new MessageChatListener(this)));
//        this.rosters.add(user);
    }
    
    public void addChat(RosterEntry rosterEntry) {
        this.chats.put(rosterEntry,
                this.connection.getChatManager().createChat(rosterEntry.getUser(), new MessageChatListener(this)));
        this.rosterEntries.add(rosterEntry);
        this.rosters.add(rosterEntry.getName());
        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rightPanel = new javax.swing.JScrollPane();
        rosters = new java.awt.List();
        chatTabbedPane = new javax.swing.JTabbedPane();

        setLayout(new java.awt.BorderLayout());

        rosters.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rostersActionPerformed(evt);
            }
        });
        rightPanel.setViewportView(rosters);

        add(rightPanel, java.awt.BorderLayout.LINE_END);
        add(chatTabbedPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

private void rostersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rostersActionPerformed
    int index = this.rosters.getSelectedIndex();
    RosterEntry rosterEntry  = this.getRosterEntryByIndex(index);
    
    if (exitsChat(rosterEntry.getName())){
        this.dashBoard.getLAyuda().setText("EL usuario ya está creado");
    }else{
        ChatPanel cp = new ChatPanel(chats.get(rosterEntry));        
        this.chatTabbedPane.addTab(rosterEntry.getName(), cp);
        this.chatPannels.put(rosterEntry, cp);
    }
}//GEN-LAST:event_rostersActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane chatTabbedPane;
    private javax.swing.JScrollPane rightPanel;
    private java.awt.List rosters;
    // End of variables declaration//GEN-END:variables

    public JTabbedPane getChatTabbedPane() {
        return chatTabbedPane;
    }

    public void setChatTabbedPane(JTabbedPane chatTabbedPane) {
        this.chatTabbedPane = chatTabbedPane;
    }

    public Map getChats() {
        return chats;
    }

    public void setChats(Map chats) {
        this.chats = chats;
    }

    public XMPPConnection getConnection() {
        return connection;
    }

    public void setConnection(XMPPConnection connection) {
        this.connection = connection;
    }

    public JScrollPane getRightPanel() {
        return rightPanel;
    }

    public void setRightPanel(JScrollPane rightPanel) {
        this.rightPanel = rightPanel;
    }

    public DashBoard getDashBoard() {
        return dashBoard;
    }

    public void setDashBoard(DashBoard dashBoard) {
        this.dashBoard = dashBoard;
    }
    
    public void newBattelPetition(String from, String pdMessage) {       
        try {
            PerfectDayMessage pdm = (PerfectDayMessage) new XStream().fromXML(pdMessage);
            BattleDescription bd = (BattleDescription) new XStream().fromXML(pdm.getMessage());
            GameBuilder gb = GameBuilderFactory.getInstance().createGameBuilderClient(from);
            gb.setBattleDescription(bd);
            RosterEntry rosterEntry = this.getRosterEntryByUser(from);
            if (rosterEntry==null){
                JOptionPane.showMessageDialog(null,"Error interno. Roster no encontrado","Error", JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException("Roster no e");
            }
            gb.getCommunication().setChat(this.chats.get(rosterEntry));
            gb.move();
        } catch (GameBuilderException ex) {
            JOptionPane.showMessageDialog(this,ex.getMessage(), "PerfectDay. Informaci�n",JOptionPane.INFORMATION_MESSAGE);
        } catch (NoSuchMethodException ex) {
            logger.error("No Such Method Exception",ex);
            JOptionPane.showMessageDialog(null,"Error interno. Porfavor envie el bug a ....","Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalAccessException ex) {
            logger.error("Ilegal acceso",ex);
            JOptionPane.showMessageDialog(null,"Error interno. Porfavor envie el bug a ....","Error", JOptionPane.ERROR_MESSAGE);
        } catch (InvocationTargetException ex) {
            logger.error("Ilegal invocacion",ex);
            JOptionPane.showMessageDialog(null,"Error interno. Porfavor envie el bug a ....","Error", JOptionPane.ERROR_MESSAGE);
        }catch(Exception ex){
            logger.error("Error al parsear XML",ex);
            JOptionPane.showMessageDialog(null,"Error interno. Porfavor envie el bug a ....","Error", JOptionPane.ERROR_MESSAGE);
        }
            
    }

    
    
    public RosterEntry getRosterEntryByIndex(int i){
        return this.rosterEntries.get(i);
    }
    
    public RosterEntry getRosterEntryByUser(String user){
        for (RosterEntry rosterEntry : rosterEntries) {
            if(rosterEntry.getUser().equalsIgnoreCase(user)){
                return rosterEntry;
            }
        }
        return null;
    }
}
