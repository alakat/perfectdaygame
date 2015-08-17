/*
 * DummyGraphicsEngine.java
 *
 * Created on 8 de marzo de 2008, 11:42
 */

package org.perfectday.main.dummyengine;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import org.apache.log4j.Logger;
import org.perfectday.dashboard.threads.DashBoardThreadGroup;
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
import org.perfectday.main.dummyengine.model.ActivationStackPanel;
import org.perfectday.main.dummyengine.model.CombatInformationDialog;
import org.perfectday.main.dummyengine.model.JBattelField;
import org.perfectday.main.dummyengine.model.JMiniInfo;
import org.perfectday.error.Error;
import org.perfectday.logicengine.core.event.game.EndTurnEvent;
import org.perfectday.logicengine.core.event.manager.EventManager;
import org.perfectday.message.TextMessage.PDTextMessage;
import org.perfectday.threads.commands.kernell.DoActionCommand;
import org.perfectday.threads.commands.kernell.GoForAllCommand;
import org.perfectday.threads.commands.kernell.PutEventCommand;

/**
 *
 * @author  Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class DummyGraphicsEngine extends javax.swing.JFrame implements GraphicsEngine{
    
    //private BattelField battelField;

    private static Logger logger = Logger.getLogger(DummyGraphicsEngine.class);
    private UnitTime unitTime;
    private ActionMini actionMiniSelected;
    private Game game;
    /** Creates new form DummyGraphicsEngine */
    public DummyGraphicsEngine() {


        initComponents();     
        
        this.jMiniInfo1.setVisible(false);
        this.activationStackPanel1.setDummyGraphicsEngine(this);
        
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        this.setSize(new Dimension(600, 300));
        this.setVisible(true);
        this.pack();
        
        final int width = this.getWidth();
        final int height = this.getHeight();
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width / 2) - (width / 2);
        int y = (screenSize.height / 2) - (height / 2);
        this.setLocation(x, y);
    }




    @Override
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
            this.unitTime = LongUnitTimeFactory.getInstance().doNothing(game.getSelectedMini());
        }    
         //Se envia false por q no es un contra atacke y por tanto el contra 
        //ataque esta permitido permitido el contraatacke
        actionMini.setActionData(new Boolean(false));
        DashBoardThreadGroup.sendEventToKernell(new DoActionCommand(actionMini, targetMini, game.getSelectedMini(), unitTime));
        
    }

    public synchronized void turnOff() {     
        EndTurnEvent endTurnEvent = new EndTurnEvent(this.getUnitTime());
        endTurnEvent.setMini(game.getSelectedMini());
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
        bMover = new javax.swing.JButton();
        tabs = new javax.swing.JTabbedPane();
        jMiniInfo1 = new org.perfectday.main.dummyengine.model.JMiniInfo();
        pGame = new javax.swing.JPanel();
        jBattelField1 = new org.perfectday.main.dummyengine.model.JBattelField();
        jPanel1 = new javax.swing.JPanel();
        activationStackPanel1 = new org.perfectday.main.dummyengine.model.ActivationStackPanel();
        unitTimeComponent = new org.perfectday.main.dummyengine.DummyGraphicsUnitTimePanel();
        jMenuBar4 = new javax.swing.JMenuBar();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PerfectDay Suite de pruebas");
        setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
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

        bMover.setText("Mover");
        bMover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bMoverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pAccionesLayout = new javax.swing.GroupLayout(pAcciones);
        pAcciones.setLayout(pAccionesLayout);
        pAccionesLayout.setHorizontalGroup(
            pAccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pAccionesLayout.createSequentialGroup()
                .addComponent(bWait, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(bMover, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bAPorEllos, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(bAtacarPrimaria)
                .addComponent(bMover))
        );

        jMiniInfo1.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        tabs.addTab("Informaci?n de los minis", jMiniInfo1);

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
            .addGap(0, 0, Short.MAX_VALUE)
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
            .addGap(0, 337, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(activationStackPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(activationStackPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        unitTimeComponent.setBackground(javax.swing.UIManager.getDefaults().getColor("Panel.background"));

        javax.swing.GroupLayout unitTimeComponentLayout = new javax.swing.GroupLayout(unitTimeComponent);
        unitTimeComponent.setLayout(unitTimeComponentLayout);
        unitTimeComponentLayout.setHorizontalGroup(
            unitTimeComponentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        unitTimeComponentLayout.setVerticalGroup(
            unitTimeComponentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pGameLayout = new javax.swing.GroupLayout(pGame);
        pGame.setLayout(pGameLayout);
        pGameLayout.setHorizontalGroup(
            pGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pGameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(unitTimeComponent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBattelField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pGameLayout.setVerticalGroup(
            pGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pGameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pGameLayout.createSequentialGroup()
                        .addComponent(jBattelField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(pGameLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(unitTimeComponent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

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
                .addGap(18, 18, 18)
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bWaitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bWaitActionPerformed
        if(this.unitTime==null){
            this.unitTime = LongUnitTimeFactory.getInstance().doNothing(
                    game.getSelectedMini());
        }        
        EndTurnEvent endTurnEvent = new EndTurnEvent(unitTime, game.getSelectedMini());
        DashBoardThreadGroup.sendEventToKernell(new PutEventCommand(endTurnEvent));
    }//GEN-LAST:event_bWaitActionPerformed

    private void bAPorEllosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAPorEllosActionPerformed
        this.unitTimeComponent.confirmMiniMovement((LongUnitTime) game.getTurnTime());
        this.unitTimeComponent.repaint();
        DashBoardThreadGroup.sendEventToKernell(new GoForAllCommand(game.getSelectedMini()));
        
    }//GEN-LAST:event_bAPorEllosActionPerformed

    private void bAtacarPrimariaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAtacarPrimariaActionPerformed
        Field f = game.getBattelField().getField(
                game.getSelectedMini());
        //Adaptar a la distancÃƒÂ­a
        this.jBattelField1.getKeepAccess().clear();
        this.jBattelField1.getKeepAccess().addAll(
                ((ActionMini)game.getSelectedMini().getPrimaryAction())
                .getActionKeep().getFieldKeeped(f,game));
        this.actionMiniSelected = game.getSelectedMini().getPrimaryAction();
        this.jBattelField1.getMiniAccess().clear();
        this.jBattelField1.repaint();
        
        this.unitTimeComponent.confirmMiniMovement((LongUnitTime) game.getTurnTime());
        this.unitTimeComponent.selectedAction(LongUnitTimeFactory.getInstance().doCombat(game.getSelectedMini()));
        this.unitTimeComponent.repaint();
    }//GEN-LAST:event_bAtacarPrimariaActionPerformed

    private void bSecundarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSecundarioActionPerformed
                
        Field f = game.getBattelField().getField(
                game.getSelectedMini());
        //Adaptar a la distancÃƒÂ­a
        this.jBattelField1.getKeepAccess().clear();
        this.jBattelField1.getKeepAccess().addAll(
                ((ActionMini)game.getSelectedMini().getSecondaryAction())
                .getActionKeep().getFieldKeeped(f,game));
        this.actionMiniSelected = game.getSelectedMini().getSecondaryAction();
        this.jBattelField1.getMiniAccess().clear();
        this.jBattelField1.repaint();
        this.unitTimeComponent.confirmMiniMovement((LongUnitTime) game.getTurnTime());
        this.unitTimeComponent.selectedAction(LongUnitTimeFactory.getInstance().doCombat(game.getSelectedMini()));
        this.unitTimeComponent.repaint();
    }//GEN-LAST:event_bSecundarioActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this, "Perfectday versi?n pre Alfa 0.8.8\n Mas informaci?n en https://sourceforge.net/projects/perfectday o en http://game-perfectday.blogspot.com/ \n este juego es software libre y actualmente busca desarrolladores.\n Dise?ado y desarrollado por Miguel Angel L?pez Montellano (alakat@gmail.com)");
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_SPACE){
            if(this.unitTime==null){
                this.unitTime = LongUnitTimeFactory.getInstance().doNothing(
                    game.getSelectedMini());
            }        
            this.turnOff();
        }
    }//GEN-LAST:event_formKeyPressed

private void jMenuItem1ActionPerformed1(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed1
    String  as = game.getActivationStack().toString();
    JOptionPane.showMessageDialog(this,as,"Debug- Show ActivationStack",JOptionPane.INFORMATION_MESSAGE);
}//GEN-LAST:event_jMenuItem1ActionPerformed1

    private void bMoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bMoverActionPerformed
        // TODO add your handling code here:
        Mini mini = Game.getGame().getSelectedMini();
        this.jBattelField1.showAccesibleCeldForMove(mini);
        LongUnitTime lt = LongUnitTimeFactory.getInstance().doMovementAction(mini);
        this.unitTimeComponent.selectedAction(lt);
        this.jBattelField1.repaint();
    }//GEN-LAST:event_bMoverActionPerformed
    
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        final String[] argvs =args;
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new DummyGraphicsEngine();
//            }
//        });
//    }
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.perfectday.main.dummyengine.model.ActivationStackPanel activationStackPanel1;
    private javax.swing.JButton bAPorEllos;
    private javax.swing.JButton bAtacarPrimaria;
    private javax.swing.JButton bMover;
    private javax.swing.JButton bSecundario;
    private javax.swing.JButton bWait;
    private org.perfectday.main.dummyengine.model.JBattelField jBattelField1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar4;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem4;
    private org.perfectday.main.dummyengine.model.JMiniInfo jMiniInfo1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel pAcciones;
    private javax.swing.JPanel pGame;
    private javax.swing.JTabbedPane tabs;
    private org.perfectday.main.dummyengine.DummyGraphicsUnitTimePanel unitTimeComponent;
    // End of variables declaration//GEN-END:variables
    
  
    
    @Override
    public void addInfo(String s){
//        this.tfInfo.append(s);
//        this.tfInfo.append("\n");
//        this.scroll.getVerticalScrollBar().setValue(1000000);
    }

    /**
     * 
     * @param mini 
     */
    @Override
    public void activateMini(Mini mini) {
        jBattelField1.setSelectedMini(mini);
        this.unitTime=null;
        this.jMiniInfo1.setMini(mini);
        this.jBattelField1.getKeepAccess().clear();
        
        this.bAtacarPrimaria.setText(mini.getPrimaryAction().getName());
        this.bSecundario.setText(mini.getSecondaryAction().getName());
        this.jMiniInfo1.setVisible(true);
//        this.jActivationStack1.stackRefresh();
        
        this.activationStackPanel1.setAccident(
                game.getActivationStack().getStack());
        
        jBattelField1.repaint();
        this.activationStackPanel1.repaint();
        
        //ampliamos activamos el refresco del componente de gasto de UTs
        this.unitTimeComponent.newActivationMini(mini);
        this.unitTimeComponent.repaint();
    }

    public UnitTime getTurnCost() {
        logger.info("getTurn Cost Donde, no deber�a ser llamado!!!");
        UnitTime ut = new LongUnitTime(0l) ;
        ut.plus(this.unitTime);
        this.unitTime=null;
        this.jBattelField1.setSelectedMini(null);
        return ut;
    }
    
    public void setTurnCost(UnitTime ut){
        this.unitTime=ut;        
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
                game.getActivationStack().getStack());
        
        jBattelField1.repaint();
        this.activationStackPanel1.repaint();
        //Componente de gasto de unidades de tiempo
        this.unitTimeComponent.noMiniSelected();
        this.unitTimeComponent.repaint();
    }

    @Override
    public void setGame(Game game) {
        this.game=game;
        this.jBattelField1.setGame(game);
        this.jBattelField1.setBattelField(game.getBattelField());
        this.jBattelField1.setDummyGraphicsEngine(this);
    }

    public Game getGame() {
        return game;
    }


    public void addMiniAccess(Mini mini, List<Field> access){
        this.jBattelField1.setSelectedMiniNoSearchFieldsAccess(mini);
        this.jBattelField1.getMiniAccess().addAll(access);
        this.jBattelField1.repaint();
    }



    
    
    
}
