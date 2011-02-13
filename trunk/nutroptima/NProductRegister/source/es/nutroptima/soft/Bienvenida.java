/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Bienvenida.java
 *
 * Created on 07-feb-2011, 22:21:47
 */

package es.nutroptima.soft;

import es.nutroptima.soft.model.Producto;
import es.nutroptima.soft.model.Usuario;
import es.nutroptima.soft.model.factories.UsuarioFactory;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import nproductregister.NProductRegisterView;
import org.jdesktop.application.Action;

/**
 *
 * @author Sergio Álvarez López <salvarez@nutroptima.es>
 */
public class Bienvenida extends javax.swing.JPanel {

    private NuevoProducto nuevoProducto;
    private NProductRegisterView view;
    private Usuario usuarioconectado;

    /** Creates new form Bienvenida */
    public Bienvenida() {
        initComponents();
    }

    public Bienvenida(NProductRegisterView aThis) {
        this.view = aThis;
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        passTF = new javax.swing.JTextField();
        listadolabel = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaProductos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        loginTF = new javax.swing.JTextField();
        bNuevoProducto = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        lMensajesEmergentes = new javax.swing.JLabel();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(nproductregister.NProductRegisterApp.class).getContext().getResourceMap(Bienvenida.class);
        setBackground(resourceMap.getColor("Form.background")); // NOI18N
        setName("Form"); // NOI18N

        passTF.setText(resourceMap.getString("passTF.text")); // NOI18N
        passTF.setName("passTF"); // NOI18N

        listadolabel.setText(resourceMap.getString("listadolabel.text")); // NOI18N
        listadolabel.setName("listadolabel"); // NOI18N

        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jScrollPane1.setName("jScrollPane1"); // NOI18N
        jScrollPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseClicked(evt);
            }
        });

        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nombre", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablaProductos.setName("tablaProductos"); // NOI18N
        tablaProductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tablaProductosKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(tablaProductos);

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        loginTF.setText(resourceMap.getString("loginTF.text")); // NOI18N
        loginTF.setName("loginTF"); // NOI18N

        bNuevoProducto.setText(resourceMap.getString("bNuevoProducto.text")); // NOI18N
        bNuevoProducto.setEnabled(false);
        bNuevoProducto.setName("bNuevoProducto"); // NOI18N
        bNuevoProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bNuevoProductoActionPerformed(evt);
            }
        });

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        lMensajesEmergentes.setName("lMensajesEmergentes"); // NOI18N

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(35, 35, 35)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(listadolabel)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(bNuevoProducto))
                    .add(layout.createSequentialGroup()
                        .add(jLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(loginTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 74, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(passTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 82, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jButton1)))
                .add(224, 224, 224))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 921, Short.MAX_VALUE)
                .addContainerGap())
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(lMensajesEmergentes, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 470, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(471, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(31, 31, 31)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(loginTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel2)
                    .add(passTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jButton1))
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(listadolabel)
                    .add(bNuevoProducto))
                .add(38, 38, 38)
                .add(lMensajesEmergentes, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 391, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void bNuevoProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bNuevoProductoActionPerformed
        //Limpiamos la información escrita en la interfaz
        nuevoProducto.resetView(null);
        view.getMainPanel().remove(this);
        view.getMainPanel().add(nuevoProducto, BorderLayout.NORTH);
        view.getMainPanel().updateUI();
        Logger.getLogger(this.getClass().getName()).info("Cambiando a vista de Nuevo Producto");
    }//GEN-LAST:event_bNuevoProductoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            // TODO add your handling code here:
            this.usuarioconectado = UsuarioFactory.getInstance().makeUsuario(this.loginTF.getText(), this.passTF.getText());
            this.tablaProductos.setModel(usuarioconectado);
            this.tablaProductos.updateUI();
            this.bNuevoProducto.setEnabled(true);
        } catch (Exception ex) {
            Logger.getLogger(Bienvenida.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Error, usuario y contraseña no validos");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tablaProductosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaProductosKeyTyped
        // TODO add your handling code here:
        System.out.println("EVENTOS!!"+evt.getKeyChar());
        if(evt.getKeyChar()== '\n'){
            int idx = this.tablaProductos.getSelectedRow();
            Producto p = this.usuarioconectado.getProductos().get(idx);
            try {
                p.save();
                lMensajesEmergentes.setText("Producto modificado");
                lMensajesEmergentes.setVisible(true);
                final JLabel  aux = lMensajesEmergentes;
                Thread t = new Thread(new Runnable() {
                    public void run() {
                        try {
                            System.out.println("Durmiendo");
                            Thread.sleep(1500);
                            lMensajesEmergentes.setText("");
                            System.out.println("Durmiendo");
                            Thread.sleep(100);
                            lMensajesEmergentes.setText("Producto modificado");
                            System.out.println("Durmiendo");
                            Thread.sleep(1500);
                            lMensajesEmergentes.setText("");
                            System.out.println("Durmiendo");
                            Thread.sleep(100);
                            lMensajesEmergentes.setText("Producto modificado");
                            System.out.println("Durmiendo");
                            Thread.sleep(1500);
                            lMensajesEmergentes.setText("");
                            System.out.println("Despieto");
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Bienvenida.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                t.start();
                
            } catch (Exception ex) {
                Logger.getLogger(Bienvenida.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showConfirmDialog(this, "Error ponte en contacto con la central de Nutroptima");
            }
        }
    }//GEN-LAST:event_tablaProductosKeyTyped

    private void jScrollPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseClicked
        // TODO add your handling code here:if
        if(this.usuarioconectado==null){
            //no hace nada
        }
        System.out.println("222click count"+evt.getClickCount());
        if(evt.getClickCount()==2){
            int idx = this.tablaProductos.getSelectedRow();
            Producto p = this.usuarioconectado.getProductos().get(idx);
            nuevoProducto.resetView(p);
            view.getMainPanel().remove(this);
            view.getMainPanel().add(nuevoProducto, BorderLayout.NORTH);
            view.getMainPanel().updateUI();
            Logger.getLogger(this.getClass().getName()).info("Cambiando a vista del Producto::"+p);
        }
    }//GEN-LAST:event_jScrollPane1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bNuevoProducto;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lMensajesEmergentes;
    private javax.swing.JLabel listadolabel;
    private javax.swing.JTextField loginTF;
    private javax.swing.JTextField passTF;
    private javax.swing.JTable tablaProductos;
    // End of variables declaration//GEN-END:variables

    @Action
    public void lanzador(){
    }

    public void setNuevoProducto(NuevoProducto nuevoProducto) {
        this.nuevoProducto = nuevoProducto;
    }

    public Usuario getUsuario(){
        return usuarioconectado;
    }

    
}
