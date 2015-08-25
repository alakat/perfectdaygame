/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.perfectday.main.dummyengine.component;

import java.awt.Graphics;
import java.awt.Graphics2D;
import org.perfectday.logicengine.model.minis.MiniType;
import org.perfectday.main.dummyengine.commons.AnimateSprite;
import org.perfectday.main.dummyengine.commons.DummyFactorySprites;
import org.perfectday.main.dummyengine.threads.DummySpriteControlerRunner;

/**
 *
 * @author Miguel (alakat@gmail.com)
 */
public class DummySpriteTestPanel extends javax.swing.JPanel {

    
    private AnimateSprite sprite;
    
    
    /**
     * Creates new form DummySpriteTestPanel
     */
    public DummySpriteTestPanel() {
        initComponents();
        //this.runTest();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        this.testAnimation(g);
    }
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(204, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 138, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 72, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    public void runTest() {
        
        this.sprite = DummyFactorySprites.getInstance().createAnimation(MiniType.SOLDIER, "active_wait");
        DummySpriteControlerRunner runner = new DummySpriteControlerRunner();
        runner.addComponent(this);
        Thread t = new Thread(runner);
        t.start();
    }

    private void testAnimation(Graphics g) {
       Graphics g1 = g.create(10, 10, 35, 35);
       if(this.sprite!=null)
           this.sprite.paint((Graphics2D) g1);
    }
}