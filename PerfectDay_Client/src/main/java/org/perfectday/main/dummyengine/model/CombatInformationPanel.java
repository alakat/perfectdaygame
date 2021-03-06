/*
 * CombatInformationPanel.java
 *
 * Created on 13 de abril de 2008, 14:50
 */

package org.perfectday.main.dummyengine.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author  Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class CombatInformationPanel extends javax.swing.JPanel {
    
    private Image backgroundImage;
    private List<String> info;
    /** Creates new form CombatInformationPanel */
    

    public CombatInformationPanel() {
        initComponents();
        info = new ArrayList<String>();                
    }
   public void setImage(String urlImg){
        this.backgroundImage = Toolkit.getDefaultToolkit().getImage(
                CombatInformationPanel.class.getClassLoader().
                getResource("assets/"+urlImg));
   }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    
    @Override
    public void paint(Graphics g){
        if(this.backgroundImage!=null)
            g.drawImage(backgroundImage, 0, 0, this);
        int x = 30;
        int y = 30;
        g.setColor(Color.BLACK);        
        for(String n: info){            
            g.drawString(n, x, y);
            y=y+15;
        }
    }
    
    
    public void setInfo(List<String> paper){
        for(String n: paper){
            if(n.length()>30){
                this.info.addAll(preparedNotice(n));
            }else{
                info.add(n);
            }
        }
        
    }
    
    private List<String> preparedNotice(String n){
        List<String> lAux = new ArrayList<String>();
        StringTokenizer sTok = new StringTokenizer(n," ");
        String cad="";
        while(sTok.hasMoreTokens()){
            String tok = sTok.nextToken();
            if((cad.length()+tok.length())<30)
                cad=cad+" "+tok;
            else{
                lAux.add(cad);
                cad="****"+tok;
            }
        }
        lAux.add(cad);
        return lAux;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
}
