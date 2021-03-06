/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.perfectday.main.dummyengine.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author Miguel (alakat@gmail.com)
 */
public class DummyDegradateYellowBar extends javax.swing.JPanel {

    private double percentBar;
    private int number;
    private Color background;
    private Color barColor;
    private Color borderBarColor;
    private Color stringColor;
    private static final int BORDER_SIZE=4;
    public static final int BAR_SIZE_X = 130;
    
    /**
     * Creates new form DummyDegradateYellowBar
     */
    public DummyDegradateYellowBar() {
        initComponents();
        this.percentBar = 1;
//        this.setBackground(new Color(0f, 0f, 0f, 0f));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setPreferredSize(new java.awt.Dimension(130, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 130, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    public void setPercent(double p){
        this.percentBar=p;
        this.repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(this.borderBarColor);
        g2d.drawRoundRect(0, 0, BAR_SIZE_X, 30, 4, 4);
        g2d.setColor(this.background);
        g2d.fillRoundRect(0+BORDER_SIZE, 0+BORDER_SIZE, 130-BORDER_SIZE, 30-BORDER_SIZE,4,4);
        int x = (int) (BAR_SIZE_X* (1-percentBar));
        g2d.setColor(this.barColor);
        g2d.fillRoundRect(x+BORDER_SIZE, 0+BORDER_SIZE, 130-BORDER_SIZE, 30-BORDER_SIZE,4,4);    
        
        g2d.setColor(this.stringColor);
        g2d.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        g2d.drawString(""+number, x+25, 12);
    }

    public void setNumber(int number) {
        this.number = number;
        this.repaint();
    }
    
    
    
    
    
}
