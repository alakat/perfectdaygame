/*
 * LaboratoryGUI.java
 *
 * Created on 8 de marzo de 2008, 11:42
 */

package org.perfectday.main.laboratocGUI;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.core.player.Player;
import org.perfectday.logicengine.model.battelfield.Field;
import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.command.combat.CombatResolutionCommand;
import org.perfectday.logicengine.model.unittime.UnitTime;
import org.perfectday.logicengine.model.unittime.LongUnitTime;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.minis.action.ActionMini;
import org.perfectday.logicengine.model.unittime.factories.LongUnitTimeFactory;
import org.perfectday.logicengine.movement.MasterMovement;
import org.perfectday.main.laboratocGUI.model.ActivationStackPanel;
import org.perfectday.main.laboratocGUI.model.CombatInformationDialog;
import org.perfectday.main.laboratocGUI.model.JBattelField;
import org.perfectday.main.laboratocGUI.model.JMiniInfo;
import org.perfectday.error.Error;
import org.perfectday.logicengine.core.event.game.EndTurnEvent;
import org.perfectday.logicengine.core.event.manager.EventManager;
import org.perfectday.message.TextMessage.PDTextMessage;

/**
 *
 * @author  Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class LaboratoryGUI extends javax.swing.JFrame implements PerfectDayGUI{
    
    //private BattelField battelField;
    private Game game;
    private UnitTime unitTime;
    public static LaboratoryGUI me;
    private Mini selectedMini;
    private ActionMini actionMiniSelected;
    /** Creates new form LaboratoryGUI */
    public LaboratoryGUI() {
        
        initComponents();        
       
        //this.battelField = new BattelField(w, h);
        LaboratoryGUI.me=this;
        this.jMiniInfo1.setVisible(false);
    }

    public boolean combatFinishWithPlayerWin(Player winer) {
        JOptionPane.showMessageDialog(
                this, "El jugador que gano es:"+winer.getName());
        return true;
    }

    public JMiniInfo getJMiniInfo1() {
        return jMiniInfo1;
    }
    
    public void fin_del_moviemiento(){
        //Cambiarlo por botonera.
        this.jBattelField1.repaint();                
    }

    public void setTargetMini(Mini targetMini) {       
        ActionMini actionMini = (ActionMini) this.actionMiniSelected;              
        this.actionMiniSelected = null;
        if (this.getUnitTime()== null) {
            this.unitTime = LongUnitTimeFactory.getInstance().doNothing(Game.getInstance().getSelectedMini());
        }    
         //Se envia false por q no es un contra atacke y por tanto el contra 
        //ataque esta permitido permitido el contraatacke
        actionMini.setActionData(new Boolean(false));
        actionMini.doAction(Game.getInstance().getSelectedMini(), targetMini);       
        this.turnOff();
    }

    public synchronized void turnOff() {     
        EndTurnEvent endTurnEvent = new EndTurnEvent(this.getUnitTime());
        endTurnEvent.setMini(Game.getInstance().getSelectedMini());
        EventManager.getInstance().addEvent(endTurnEvent);
        EventManager.getInstance().eventWaitTest();
       
    }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pAcciones = new javax.swing.JPanel();
        bWait = new javax.swing.JButton();
        bAPorEllos = new javax.swing.JButton();
        bAtacarPrimaria = new javax.swing.JButton();
        bSecundario = new javax.swing.JButton();
        tabs = new javax.swing.JTabbedPane();
        jMiniInfo1 = new org.perfectday.main.laboratocGUI.model.JMiniInfo();
        pGame = new javax.swing.JPanel();
        jBattelField1 = new org.perfectday.main.laboratocGUI.model.JBattelField();
        jPanel1 = new javax.swing.JPanel();
        activationStackPanel1 = new org.perfectday.main.laboratocGUI.model.ActivationStackPanel();
        jMenuBar3 = new javax.swing.JMenuBar();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuBar4 = new javax.swing.JMenuBar();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PerfectDay Suite de pruebas");
        setFont(new java.awt.Font("Comic Sans MS", 0, 12));
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        pAcciones.setBorder(javax.swing.BorderFactory.createTitledBorder("Acciones"));

        bWait.setText("Esperar");
        bWait.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bWaitActionPerformed(evt);
            }
        });

        bAPorEllos.setText("A Por Ellos!!");
        bAPorEllos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAPorEllosActionPerformed(evt);
            }
        });

        bAtacarPrimaria.setText("ataque_primario");
        bAtacarPrimaria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAtacarPrimariaActionPerformed(evt);
            }
        });

        bSecundario.setText("ataque_secundario");
        bSecundario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSecundarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pAccionesLayout = new javax.swing.GroupLayout(pAcciones);
        pAcciones.setLayout(pAccionesLayout);
        pAccionesLayout.setHorizontalGroup(
            pAccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pAccionesLayout.createSequentialGroup()
                .addComponent(bWait, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bAPorEllos, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bAtacarPrimaria, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bSecundario, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pAccionesLayout.setVerticalGroup(
            pAccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pAccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(bSecundario)
                .addComponent(bWait)
                .addComponent(bAPorEllos)
                .addComponent(bAtacarPrimaria))
        );

        jMiniInfo1.setFont(new java.awt.Font("Comic Sans MS", 0, 12));
        tabs.addTab("Información de los minis", jMiniInfo1);

        pGame.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jBattelField1.setBackground(new java.awt.Color(255, 255, 255));
        jBattelField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jBattelField1.setToolTipText("");
        jBattelField1.setAutoscrolls(true);

        javax.swing.GroupLayout jBattelField1Layout = new javax.swing.GroupLayout(jBattelField1);
        jBattelField1.setLayout(jBattelField1Layout);
        jBattelField1Layout.setHorizontalGroup(
            jBattelField1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 561, Short.MAX_VALUE)
        );
        jBattelField1Layout.setVerticalGroup(
            jBattelField1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 457, Short.MAX_VALUE)
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Turno"));

        javax.swing.GroupLayout activationStackPanel1Layout = new javax.swing.GroupLayout(activationStackPanel1);
        activationStackPanel1.setLayout(activationStackPanel1Layout);
        activationStackPanel1Layout.setHorizontalGroup(
            activationStackPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 219, Short.MAX_VALUE)
        );
        activationStackPanel1Layout.setVerticalGroup(
            activationStackPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 418, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(activationStackPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(activationStackPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pGameLayout = new javax.swing.GroupLayout(pGame);
        pGame.setLayout(pGameLayout);
        pGameLayout.setHorizontalGroup(
            pGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pGameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBattelField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pGameLayout.setVerticalGroup(
            pGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pGameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBattelField1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jMenu5.setText("PerfectDay");

        jMenuItem3.setText("About");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem3);

        jMenuBar3.add(jMenu5);

        jMenu6.setText("PerfectDay");

        jMenuItem4.setText("About");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem4);

        jMenuBar4.add(jMenu6);

        jMenu1.setText("Debug");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem1.setText("Show ActivationStack");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed1(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar4.add(jMenu1);

        setJMenuBar(jMenuBar4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pGame, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(tabs, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pAcciones, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pGame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pAcciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bWaitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bWaitActionPerformed
        if(this.unitTime==null){
            this.unitTime = LongUnitTimeFactory.getInstance().doNothing(
                    Game.getInstance().getSelectedMini());                   
        }        
        this.turnOff();
    }//GEN-LAST:event_bWaitActionPerformed

    private void bAPorEllosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAPorEllosActionPerformed
        double x = (new Random(System.currentTimeMillis())).nextDouble();
        if(game.getMasterAPorEllos().getNewProbability()>x){
//            this.tfInfo.append("A Por Ellos");
            List<Field> aporEllosField = MasterMovement.getInstance().
                    getAPorEllosField(game.getSelectedMini(), game.getBattelField());
            this.jBattelField1.setSelectedMiniNoSearchFieldsAccess(game.getSelectedMini());
            this.jBattelField1.getMiniAccess().addAll(aporEllosField);
            this.jBattelField1.repaint();
        }else{
            //Fail a por ellos
//            this.tfInfo.append("Falla el aporellos!!!");
            this.setTurnCost(
                    LongUnitTimeFactory.getInstance().
                    doMovementFailAPorEllos(game.getSelectedMini()));
            this.turnOff();
        }
    }//GEN-LAST:event_bAPorEllosActionPerformed

    private void bAtacarPrimariaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAtacarPrimariaActionPerformed
        Field f = Game.getInstance().getBattelField().getField(
                Game.getInstance().getSelectedMini());
        //Adaptar a la distancÃƒÂ­a
        this.jBattelField1.getKeepAccess().clear();
        this.jBattelField1.getKeepAccess().addAll(
                ((ActionMini)Game.getInstance().getSelectedMini().getPrimaryAction())
                .getActionKeep().getFieldKeeped(f));
        this.actionMiniSelected = Game.getInstance().getSelectedMini().getPrimaryAction();
        this.jBattelField1.getMiniAccess().clear();
        this.jBattelField1.repaint();
    }//GEN-LAST:event_bAtacarPrimariaActionPerformed

    private void bSecundarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSecundarioActionPerformed
                
        Field f = Game.getInstance().getBattelField().getField(
                Game.getInstance().getSelectedMini());
        //Adaptar a la distancÃƒÂ­a
        this.jBattelField1.getKeepAccess().clear();
        this.jBattelField1.getKeepAccess().addAll(
                ((ActionMini)Game.getInstance().getSelectedMini().getSecondaryAction())
                .getActionKeep().getFieldKeeped(f));
        this.actionMiniSelected = Game.getInstance().getSelectedMini().getSecondaryAction();
        this.jBattelField1.getMiniAccess().clear();
        this.jBattelField1.repaint();
    }//GEN-LAST:event_bSecundarioActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this, "Perfectday versiÃ³n pre Alfa 0.8.8\n Mas informaciÃ³n en https://sourceforge.net/projects/perfectday o en http://game-perfectday.blogspot.com/ \n este juego es software libre y actualmente busca desarrolladores.\n DiseÃƒÂ±ado y desarrollado por Miguel Angel LÃƒÂ³pez Montellano (alakat@gmail.com)");
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_SPACE){
            if(this.unitTime==null){
                this.unitTime = LongUnitTimeFactory.getInstance().doNothing(
                    Game.getInstance().getSelectedMini());                   
            }        
            this.turnOff();
        }
    }//GEN-LAST:event_formKeyPressed

private void jMenuItem1ActionPerformed1(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed1
    String  as = Game.getInstance().getActivationStack().toString();
    JOptionPane.showMessageDialog(this,as,"Debug- Show ActivationStack",JOptionPane.INFORMATION_MESSAGE);
}//GEN-LAST:event_jMenuItem1ActionPerformed1
    
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        final String[] argvs =args;
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new LaboratoryGUI();
//            }
//        });
//    }
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.perfectday.main.laboratocGUI.model.ActivationStackPanel activationStackPanel1;
    private javax.swing.JButton bAPorEllos;
    private javax.swing.JButton bAtacarPrimaria;
    private javax.swing.JButton bSecundario;
    private javax.swing.JButton bWait;
    private org.perfectday.main.laboratocGUI.model.JBattelField jBattelField1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JMenuBar jMenuBar4;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private org.perfectday.main.laboratocGUI.model.JMiniInfo jMiniInfo1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel pAcciones;
    private javax.swing.JPanel pGame;
    private javax.swing.JTabbedPane tabs;
    // End of variables declaration//GEN-END:variables
    
  
    
    public void addInfo(String s){
//        this.tfInfo.append(s);
//        this.tfInfo.append("\n");
//        this.scroll.getVerticalScrollBar().setValue(1000000);
    }

    public void activateMini(Mini mini) {
        jBattelField1.setSelectedMini(mini);
        
        this.jMiniInfo1.setMini(mini);
        this.jBattelField1.getKeepAccess().clear();
        
        this.bAtacarPrimaria.setText(mini.getPrimaryAction().getName());
        this.bSecundario.setText(mini.getSecondaryAction().getName());
        this.jMiniInfo1.setVisible(true);
//        this.jActivationStack1.stackRefresh();
        
        this.activationStackPanel1.setAccident(
                Game.getInstance().getActivationStack().getStack());
        
        jBattelField1.repaint();
        this.activationStackPanel1.repaint();
    }

    public UnitTime getTurnCost() {
        UnitTime ut = new LongUnitTime(0l) ;
        ut.plus(this.unitTime);
        this.unitTime=null;
        this.jBattelField1.setSelectedMini(null);
        return ut;
    }
    
    public void setTurnCost(UnitTime ut){
        this.unitTime=ut;        
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game=game;
        this.jBattelField1.setBattelField(game.getBattelField());
    }

    public UnitTime getUnitTime() {
        return unitTime;
    }

    public JTabbedPane getTabs() {
        return tabs;
    }

    /**
     * dummy method
     * @param commands
     */
    public void proccessCommand(List<Command> commands) {
        //NO sirve para nada dummy
        for(Command command:commands){
            if(command instanceof CombatResolutionCommand){
                CombatResolutionCommand crc = (CombatResolutionCommand) command;
                this.jBattelField1.showInfoCombatResolution(crc);
            }
        }
    }

    public void showInfoCombat(List<String> paper) {
        if(paper!=null && (!paper.isEmpty())){
            CombatInformationDialog cDialog = new CombatInformationDialog(this, true, "bg.jpeg", paper);
            cDialog.setVisible(true);
        }
    }

    public ActivationStackPanel getActivationStackPanel() {
        return activationStackPanel1;
    }

    public JBattelField getJBattelField1() {
        return jBattelField1;
    }

    public void showError(Error error) {
//        this.tfInfo.setText(this.tfInfo.getText()+"\n"+error.getName()+": "+error.getDescription());
        
    }

    public void redraw() {
//        this.jActivationStack1.repaint();
        this.jBattelField1.repaint();
        this.activationStackPanel1.repaint();
    }
    
    public void showTextMessage(PDTextMessage message){

    }
    public void showChatArea(){

        
    }

    @Override
    public void desactivateMini() {
        jBattelField1.setSelectedMini(null);
        
        
        this.jBattelField1.getKeepAccess().clear();
        
        this.jMiniInfo1.setVisible(true);
//        this.jActivationStack1.stackRefresh();
        
        this.activationStackPanel1.setAccident(
                Game.getInstance().getActivationStack().getStack());
        
        jBattelField1.repaint();
        this.activationStackPanel1.repaint();
    }
    
}
