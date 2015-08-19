/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.perfectday.main.dummyengine.component;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.perfectday.logicengine.core.configuration.Configuration;
import org.perfectday.logicengine.model.minis.Mini;

/**
 *
 * @author Miguel (alakat@gmail.com)
 */
public class DummyAttributeMiniPanel extends javax.swing.JPanel {

    private Mini mini;
//    private static final int SIZE_X=100;
//    private static final int SIZE_Y=100;
//    private static final int PADDING_X=10;
//    private static final int PADDING_Y=10;
//    private static final int ICON_X=20;
//    private static final int ICON_Y=20;
//    private Icon atacIcon;
//    private Icon defIcon;
//    private Icon restIcon;
//    private Icon dmgIcon;
//    private Icon movIcon;
//    private Icon iniIcon;
    /**
     * Creates new form DummyAttributeMiniPanel
     */
    public DummyAttributeMiniPanel() {
        initComponents();
        //loadIcons();
        this.setMini(null);
    }

    public void setMini(Mini mini) {
        this.mini = mini;
        if(this.mini==null){
            this.atacNum.setText("");
            this.defNum.setText("");
            this.movNum.setText("");
            this.iniNum.setText("");
            this.restNum.setText("");
            this.dmgNum.setText("");
            this.primaryAction.setText("");
            this.secondaryAction.setText("");
            this.supportAction.setText("");
        }else{
            this.atacNum.setText(""+this.mini.getAtak());
            this.defNum.setText(""+this.mini.getDefense());
            this.movNum.setText(""+this.mini.getMoviment());
            this.iniNum.setText(""+this.mini.getIniciative());
            this.restNum.setText(""+this.mini.getResistance());
            this.dmgNum.setText(""+this.mini.getStrength());
            if(this.mini.getPrimaryAction()!=null){
                this.primaryAction.setText(
                    this.mini.getPrimaryAction().getName());
            }else{
                this.primaryAction.setText("");
            }
            
            if(this.mini.getSecondaryAction()!=null){
                this.secondaryAction.setText(
                    this.mini.getSecondaryAction().getName());
            }else{
                this.secondaryAction.setText("");
            }
            
            if(this.mini.getSupport()!=null){
                this.supportAction.setText(
                    this.mini.getSupport().getDescription());
            }else{
                this.supportAction.setText("");
            }
        }
        this.repaint();
    }
    
    /**
     * Carga los iconos, pendiente de un gestor de im?genes que no nos deje sin 
     * memoria
     */
    private void loadIcons(){
//        this.atacIcon = new ImageIcon(Configuration.getInstance().getURLIconByName("atacar_icon.png"));
//        this.defIcon = new ImageIcon(Configuration.getInstance().getURLIconByName("def_icon.png"));
//        this.dmgIcon = new ImageIcon(Configuration.getInstance().getURLIconByName("dmg_icon.png"));
//        this.iniIcon = new ImageIcon(Configuration.getInstance().getURLIconByName("ini_icon.png"));
//        this.movIcon = new ImageIcon(Configuration.getInstance().getURLIconByName("mov_icon.png"));
//        this.restIcon = new ImageIcon(Configuration.getInstance().getURLIconByName("rest_icon.png"));
//        
    }

    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        atacNum = new javax.swing.JLabel();
        dmgNum = new javax.swing.JLabel();
        movNum = new javax.swing.JLabel();
        iniNum = new javax.swing.JLabel();
        restNum = new javax.swing.JLabel();
        defNum = new javax.swing.JLabel();
        primaryAction = new javax.swing.JLabel();
        secondaryAction = new javax.swing.JLabel();
        supportAction = new javax.swing.JLabel();

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/dummy_icon/atacar_ico.png"))); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/dummy_icon/def_ico.png"))); // NOI18N

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/dummy_icon/rest_ico.png"))); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/dummy_icon/dmg_ico.png"))); // NOI18N

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/dummy_icon/mov_ico.png"))); // NOI18N

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/dummy_icon/ini_ico.png"))); // NOI18N

        atacNum.setText("1");

        dmgNum.setText("1");

        movNum.setText("1");

        iniNum.setText("1");

        restNum.setText("1");

        defNum.setText("1");

        primaryAction.setText("jLabel7");

        secondaryAction.setText("jLabel8");

        supportAction.setText("jLabel9");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(atacNum)
                    .addComponent(movNum)
                    .addComponent(dmgNum))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel5)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(defNum, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(restNum, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(iniNum, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(primaryAction)
                    .addComponent(secondaryAction)
                    .addComponent(supportAction))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(atacNum)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(dmgNum))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(movNum, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(defNum)
                                .addGap(14, 14, 14)
                                .addComponent(restNum))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(primaryAction))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(secondaryAction))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(iniNum))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(supportAction, javax.swing.GroupLayout.Alignment.TRAILING))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel atacNum;
    private javax.swing.JLabel defNum;
    private javax.swing.JLabel dmgNum;
    private javax.swing.JLabel iniNum;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel movNum;
    private javax.swing.JLabel primaryAction;
    private javax.swing.JLabel restNum;
    private javax.swing.JLabel secondaryAction;
    private javax.swing.JLabel supportAction;
    // End of variables declaration//GEN-END:variables

    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        
    }
}