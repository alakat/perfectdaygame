/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * HelperManagerGui.java
 *
 * Created on 18-ago-2009, 19:13:37
 */
package es.bitsonfire.perfectday.gui;

import es.bitsonfire.perfectday.model.ActionHelpInformation;
import es.bitsonfire.perfectday.model.Context;
import es.bitsonfire.perfectday.model.Location;
import java.io.FileNotFoundException;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListDataListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 *Aplicación de gestión de la base de datos de información de ayuda en perfectday
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class HelperManagerGui extends javax.swing.JFrame {

	/**
	 * Refereca la información de la pantalla de gestión de la información en Perfectday
	 */
    private void refreshGUI() {
        this.locationJList.setModel(new LocationListModel());
        this.contextJList.setModel(new ContextListModel());
        jTable1.setModel(new ActionHelperInformationModel());
        Context c = manager.getSelectedContext();
        if (this.jScrollPane2.getBorder() instanceof TitledBorder) {
            TitledBorder titledBorder = (TitledBorder) this.jScrollPane2.getBorder();
            if (c!=null) {
                titledBorder.setTitle("Acciones de: " + c.getTitle() +"("+c.getId()+")");
            }else{
                titledBorder.setTitle("Acciones");
            }
        }
        if (this.contextJList.getBorder() instanceof TitledBorder) {
            TitledBorder titledBorder = (TitledBorder) this.contextJList.getBorder();
            if (manager.getSelectedLocation()!=null) {
                titledBorder.setTitle("Contextos de: " + manager.getSelectedLocation().getTitle()+"("+manager.getSelectedLocation().getId()+")");
            }else{
               titledBorder.setTitle("Contextos");
            }
        }
        if(manager.getSelectedActionHelpInformation()!=null){
            ActionHelpInformation a = manager.getSelectedActionHelpInformation();
            this.ltitle.setText(a.getTitle());
            this.lComplet.setText(a.getCompletText());
            this.lResumen.setText(a.getResumen());
            this.SelectedAction.setVisible(true);
            String title = a.getPreview()==null?"":a.getPreview().getTitle();
            this.lpreview.setText("Anterior: "+title);
            title = a.getNext()==null?"":a.getNext().getTitle();
            this.lnext.setText("Siguiente: "+title);
            title = a.getInteresting().size()==0?"":a.getInteresting().get(0).getTitle();
            this.lInterest1.setText(title);
            title = a.getInteresting().size()<=1?"":a.getInteresting().get(1).getTitle();
            this.lInterest2.setText(title);
            title = a.getInteresting().size()<=2?"":a.getInteresting().get(2).getTitle();
            this.lInterest3.setText(title);
        }else{
            this.ltitle.setText("");
            this.lComplet.setText("");
            this.lResumen.setText("");
            
            this.SelectedAction.setVisible(false);
        }

        this.repaint();

    }

    class LocationListModel implements ListModel {

        @Override
        public int getSize() {
            return manager.getDatabase().getDatabase().getLocations().size();
        }

        @Override
        public Object getElementAt(int index) {
            Iterator<Location> i = manager.getDatabase().getDatabase().getLocations().iterator();
            for (int j = 0; j < index; j++) {
                i.next();
            }
            return i.next();
        }

        @Override
        public void addListDataListener(ListDataListener l) {
        }

        @Override
        public void removeListDataListener(ListDataListener l) {
        }
    }

    class ContextListModel implements ListModel {

        @Override
        public int getSize() {
            if (manager.getSelectedLocation() == null) {
                return 0;
            }
            return manager.getSelectedLocation().getContexts().size();
        }

        @Override
        public Object getElementAt(int index) {
            Iterator<Context> i = manager.getSelectedLocation().getContexts().iterator();
            for (int j = 0; j < index; j++) {
                i.next();
            }
            return i.next();
        }

        @Override
        public void addListDataListener(ListDataListener l) {
        }

        @Override
        public void removeListDataListener(ListDataListener l) {
        }
    }

    class ActionHelperInformationModel implements TableModel {

        private String[] COLUMNS_NAME = new String[]{"id", "título", "Resumen"};

        @Override
        public int getRowCount() {
            if (manager.getSelectedContext() == null) {
                return 0;
            }
            return manager.getSelectedContext().getActions().size();
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public String getColumnName(int columnIndex) {
            return COLUMNS_NAME[columnIndex];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return String.class;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return manager.getSelectedContext().getActions().get(rowIndex).getId();
                case 1:
                    return manager.getSelectedContext().getActions().get(rowIndex).getTitle();
                case 2:
                    return manager.getSelectedContext().getActions().get(rowIndex).getResumen();

            }
            return "";
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        }

        @Override
        public void addTableModelListener(TableModelListener l) {
        }

        @Override
        public void removeTableModelListener(TableModelListener l) {
        }
    }
    private static final String DATABASE_PATH = "src\\main\\resources\\helper.db";
    private ListModel locations = new LocationListModel();
    private ListModel contexts = new ContextListModel();
    private TableModel actions = new ActionHelperInformationModel();
    private HelperManager manager;

    /** Creates new form HelperManagerGui */
    public HelperManagerGui() {

        try {
            this.manager = new HelperManager(DATABASE_PATH);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Error [" + ex.getMessage() + "]", "Error al iniciar la base de datos", JOptionPane.ERROR_MESSAGE);
            int opc = JOptionPane.showConfirmDialog(this, "Desea crear una nueva base de datos");
            if (opc != JOptionPane.YES_OPTION) {
                this.dispose();
            }
            try {
                this.manager = new HelperManager(DATABASE_PATH, true);
            } catch (FileNotFoundException ex1) {
                ex1.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error inseperado", "Error al iniciar la base de datos", JOptionPane.ERROR_MESSAGE);
                dispose();
            }
        }
        initComponents();
        if (manager.getSelectedActionHelpInformation() == null) {
            this.SelectedAction.setVisible(false);
        }

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        locationPopMenu = new javax.swing.JPopupMenu();
        addLocation = new javax.swing.JMenuItem();
        removelocation = new javax.swing.JMenuItem();
        contextPopMenu = new javax.swing.JPopupMenu();
        addContext = new javax.swing.JMenuItem();
        addAction = new javax.swing.JMenuItem();
        removeContext = new javax.swing.JMenuItem();
        removeAction = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        locationJList = new javax.swing.JList();
        scrolljList = new javax.swing.JScrollPane();
        contextJList = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        SelectedAction = new javax.swing.JPanel();
        ltitle = new javax.swing.JLabel();
        lResumen = new javax.swing.JLabel();
        lpreview = new javax.swing.JLabel();
        lnext = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lInterest1 = new javax.swing.JLabel();
        lInterest2 = new javax.swing.JLabel();
        lInterest3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        lComplet = new javax.swing.JTextArea();

        addLocation.setText("Nueva Localización");
        addLocation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addLocationActionPerformed(evt);
            }
        });
        locationPopMenu.add(addLocation);

        removelocation.setText("Eliminar Localización");
        removelocation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removelocationActionPerformed(evt);
            }
        });
        locationPopMenu.add(removelocation);

        addContext.setText("Nuevo Contexto");
        addContext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addContextActionPerformed(evt);
            }
        });
        contextPopMenu.add(addContext);

        addAction.setText("Nueva Información de Accion\n");
        addAction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionActionPerformed(evt);
            }
        });
        contextPopMenu.add(addAction);

        removeContext.setText("Eliminar contexto");
        removeContext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeContextActionPerformed(evt);
            }
        });
        contextPopMenu.add(removeContext);

        removeAction.setText("Eliminar acción");
        removeAction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeActionActionPerformed(evt);
            }
        });
        contextPopMenu.add(removeAction);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Administrador de mensajes de información para Perfectday");
        setLocationByPlatform(true);
        setName("HelperManager"); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setAutoscrolls(true);
        jPanel1.setLayout(new java.awt.GridLayout(1, 0, 10, 50));

        locationJList.setBorder(javax.swing.BorderFactory.createTitledBorder("Localizaciones de información"));
        locationJList.setModel(this.locations);
        locationJList.setAutoscrolls(false);
        locationJList.setComponentPopupMenu(locationPopMenu);
        locationJList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                locationJListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(locationJList);
        locationJList.getAccessibleContext().setAccessibleParent(jPanel1);

        jPanel1.add(jScrollPane1);

        contextJList.setBorder(javax.swing.BorderFactory.createTitledBorder("Contextos dentro de la vista del usuario"));
        contextJList.setModel(this.contexts);
        contextJList.setComponentPopupMenu(contextPopMenu);
        contextJList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                contextJListMouseClicked(evt);
            }
        });
        scrolljList.setViewportView(contextJList);
        contextJList.getAccessibleContext().setAccessibleParent(jPanel1);

        jPanel1.add(scrolljList);

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("Actions"));

        jTable1.setModel(this.actions);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        SelectedAction.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        ltitle.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        lResumen.setBorder(javax.swing.BorderFactory.createTitledBorder("Resumen"));

        lpreview.setText("Anterior:");

        lnext.setText("Siguiente:");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Relacionados"));
        jPanel3.setLayout(new java.awt.GridLayout(1, 3));
        jPanel3.add(lInterest1);
        jPanel3.add(lInterest2);
        jPanel3.add(lInterest3);

        jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder("Información completa"));

        lComplet.setColumns(20);
        lComplet.setEditable(false);
        lComplet.setRows(5);
        jScrollPane3.setViewportView(lComplet);

        javax.swing.GroupLayout SelectedActionLayout = new javax.swing.GroupLayout(SelectedAction);
        SelectedAction.setLayout(SelectedActionLayout);
        SelectedActionLayout.setHorizontalGroup(
            SelectedActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SelectedActionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SelectedActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
                    .addComponent(lResumen, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
                    .addComponent(ltitle, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lpreview, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lnext, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        SelectedActionLayout.setVerticalGroup(
            SelectedActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SelectedActionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ltitle, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lResumen, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lpreview)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lnext)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 636, Short.MAX_VALUE)
                    .addComponent(SelectedAction, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 636, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SelectedAction, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void locationJListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_locationJListMouseClicked
        if (evt.getClickCount() == 2) {
            Location l = (Location) this.locationJList.getSelectedValue();
            this.manager.setSelectedLocation(l);
            refreshGUI();
        }
    }//GEN-LAST:event_locationJListMouseClicked

    private void addContextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addContextActionPerformed

        String title = JOptionPane.showInputDialog(this, "Introduce el nombre del nuevo contexto");
        if (title != null && !title.equals("")) {
            this.manager.addContext(new Context(0, title));
        }
        refreshGUI();
    }//GEN-LAST:event_addContextActionPerformed

    private void addActionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionActionPerformed

        ActionDialog dialog = new ActionDialog(this, true, manager);
        dialog.setVisible(true);
        this.manager.save();
        refreshGUI();
    }//GEN-LAST:event_addActionActionPerformed

    private void contextJListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_contextJListMouseClicked
        if (evt.getClickCount() == 2) {
            Context c = (Context) this.contextJList.getSelectedValue();
            this.manager.setSelectedContext(c);
            refreshGUI();
        }
    }//GEN-LAST:event_contextJListMouseClicked

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int i = jTable1.getSelectedRow();
        manager.setSelectedActionHelpInformation(manager.getSelectedContext().getActions().get(i));
        if(evt.getClickCount()==2){
             ActionDialog dialog = new ActionDialog(this, true, manager,manager.getSelectedContext().getActions().get(i));
            dialog.setVisible(true);
            this.manager.save();
        }
        refreshGUI();
    }//GEN-LAST:event_jTable1MouseClicked

    private void addLocationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addLocationActionPerformed

        String title = JOptionPane.showInputDialog(this, "Introduce el nombre de la nueva Localización");
        if (title != null && !title.equals("")) {
            Location l = new Location(0, title);
            this.manager.addLocation(l);
            this.manager.setSelectedLocation(l);
        }
        refreshGUI();
}//GEN-LAST:event_addLocationActionPerformed

    private void removelocationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removelocationActionPerformed
        int op=JOptionPane.showConfirmDialog(this, "Va a eliminad ["+manager.getSelectedLocation()+"] Los contextos y acciones de esta localización serán eliminados tambien.\n ¿Esta usted seguro?");
        if(op!= JOptionPane.YES_OPTION){
            return;
        }
        this.manager.removeLocation();
        manager.save();
        this.refreshGUI();
    }//GEN-LAST:event_removelocationActionPerformed

    private void removeContextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeContextActionPerformed
        int op=JOptionPane.showConfirmDialog(this, "Va a eliminar ["+manager.getSelectedContext()+"] Las acciones asocidas a este contexto serán eliminadas también.\n ¿Esta usted seguro?");
        if(op!= JOptionPane.YES_OPTION){
            return;
        }
        this.manager.removeContext();
        manager.save();
        this.refreshGUI();
    }//GEN-LAST:event_removeContextActionPerformed

    private void removeActionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeActionActionPerformed
        int op=JOptionPane.showConfirmDialog(this, "Va a eliminar ["+manager.getSelectedActionHelpInformation()+"].\n ¿Esta usted seguro?");
        if(op!= JOptionPane.YES_OPTION){
            return;
        }
        this.manager.removeActionHelperInformation();
        manager.save();
        this.refreshGUI();
    }//GEN-LAST:event_removeActionActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new HelperManagerGui().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel SelectedAction;
    private javax.swing.JMenuItem addAction;
    private javax.swing.JMenuItem addContext;
    private javax.swing.JMenuItem addLocation;
    private javax.swing.JList contextJList;
    private javax.swing.JPopupMenu contextPopMenu;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea lComplet;
    private javax.swing.JLabel lInterest1;
    private javax.swing.JLabel lInterest2;
    private javax.swing.JLabel lInterest3;
    private javax.swing.JLabel lResumen;
    private javax.swing.JLabel lnext;
    private javax.swing.JList locationJList;
    private javax.swing.JPopupMenu locationPopMenu;
    private javax.swing.JLabel lpreview;
    private javax.swing.JLabel ltitle;
    private javax.swing.JMenuItem removeAction;
    private javax.swing.JMenuItem removeContext;
    private javax.swing.JMenuItem removelocation;
    private javax.swing.JScrollPane scrolljList;
    // End of variables declaration//GEN-END:variables
}
