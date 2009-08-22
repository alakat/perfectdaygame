/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SimpleHelpDialog.java
 *
 * Created on 21-ago-2009, 3:26:20
 */
package es.bitsonfire.perfectday.gui.dialog;

import es.bitsonfire.perfectday.HelperDatabase;
import es.bitsonfire.perfectday.model.ActionHelpInformation;
import es.bitsonfire.perfectday.model.Context;
import es.bitsonfire.perfectday.model.Location;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class SimpleHelpDialog extends javax.swing.JDialog {

    /**
     * Tree Model para la representación de información de ayuda.
     */
    class LocationsTreeModel implements TreeModel {

        private HelperDatabase manager;

        public LocationsTreeModel() {
            manager = new HelperDatabase(new File("./src/main/resources/helper.db"));
        }

        @Override
        public void addTreeModelListener(TreeModelListener l) {
        }

        @Override
        public Object getChild(Object parent, int index) {
            if (parent instanceof Location) {
                Location location = (Location) parent;
                List<Context> list = new ArrayList(location.getContexts());
                return list.get(index);
            }
            if (parent instanceof Context) {
                Context context = (Context) parent;
                return context.getActions().get(index);
            }
            if (parent instanceof HelperDatabase) {
                List<Location> list = new ArrayList<Location>(manager.getDatabase().getLocations());
                return list.get(index);
            }
            return null;
        }

        @Override
        public int getChildCount(Object parent) {
            if (parent instanceof Location) {
                Location location = (Location) parent;
                return location.getContexts().size();
            }
            if (parent instanceof Context) {
                Context context = (Context) parent;
                return context.getActions().size();
            }
            if (parent instanceof HelperDatabase) {
                List<Location> list = new ArrayList<Location>(manager.getDatabase().getLocations());
                return list.size();
            }
            return 0;
        }

        @Override
        public int getIndexOfChild(Object parent, Object child) {
            if (parent instanceof Location) {
                Location location = (Location) parent;
                int i = 0;
                for (Context context : location.getContexts()) {
                    if (context.equals(child)) {
                        return i;
                    } else {
                        i++;
                    }
                }
                return 0;
            }
            if (parent instanceof Context) {
                Context context = (Context) parent;
                for (int i = 0; i < context.getActions().size(); i++) {
                    if (context.getActions().get(i).equals(child)) {
                        return i;
                    }
                }
                return 0;
            }
            if (parent instanceof HelperDatabase) {
                List<Location> list = new ArrayList<Location>(manager.getDatabase().getLocations());
                int i = 0;
                for (Location location : list) {
                    if (location.equals(child)) {
                        return i;
                    } else {
                        i++;
                    }
                }
            }
            return 0;
        }

        @Override
        public Object getRoot() {
            return manager;
        }

        @Override
        public boolean isLeaf(Object node) {
            return node instanceof ActionHelpInformation;
        }

        @Override
        public void removeTreeModelListener(TreeModelListener l) {
        }

        @Override
        public void valueForPathChanged(TreePath path, Object newValue) {
        }
    }

    /**
     * Resalta y permite diriguir las lecturas
     */
    class ShineLabelMouseListener implements MouseListener {

        private Component component;
        private ActionHelpInformation redirect;

        public ShineLabelMouseListener(Component component, ActionHelpInformation redirect) {
            this.component = component;
            this.redirect = redirect;
        }



        @Override
        public void mouseClicked(MouseEvent e) {
            if (redirect!=null) {
                paintActionHelper(redirect);
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            component.setForeground(Color.blue);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            component.setForeground(Color.black);
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }
    }
    private LocationsTreeModel treeModel = new LocationsTreeModel();

    /** Creates new form SimpleHelpDialog */
    public SimpleHelpDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
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

        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tree = new javax.swing.JTree();
        pInfor = new javax.swing.JPanel();
        ltitle = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        taInfo = new javax.swing.JTextArea();
        lPreview = new javax.swing.JLabel();
        lNext = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lInt1 = new javax.swing.JLabel();
        lInt2 = new javax.swing.JLabel();
        lInt3 = new javax.swing.JLabel();

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Enciclopedia de Perfectday aquí encontras toda la ayuda q necesitas");

        tree.setBorder(javax.swing.BorderFactory.createTitledBorder("Índice de ayuda"));
        tree.setModel(this.treeModel);
        tree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                treeMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tree);

        pInfor.setBorder(javax.swing.BorderFactory.createTitledBorder("Información"));

        ltitle.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        ltitle.setText("text");
        ltitle.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        taInfo.setBackground(this.getBackground());
        taInfo.setColumns(20);
        taInfo.setEditable(false);
        taInfo.setFont(new java.awt.Font("Lucida Console", 0, 13)); // NOI18N
        taInfo.setLineWrap(true);
        taInfo.setRows(5);
        taInfo.setWrapStyleWord(true);
        jScrollPane2.setViewportView(taInfo);

        lPreview.setText("jLabel2");

        lNext.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lNext.setText("jLabel2");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Información relacionada"));
        jPanel2.setLayout(new java.awt.GridLayout(1, 3));

        lInt1.setText("int1");
        jPanel2.add(lInt1);

        lInt2.setText("int2");
        jPanel2.add(lInt2);

        lInt3.setText("int3");
        jPanel2.add(lInt3);

        javax.swing.GroupLayout pInforLayout = new javax.swing.GroupLayout(pInfor);
        pInfor.setLayout(pInforLayout);
        pInforLayout.setHorizontalGroup(
            pInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pInforLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pInforLayout.createSequentialGroup()
                        .addGroup(pInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ltitle, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(pInforLayout.createSequentialGroup()
                        .addComponent(lPreview, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 392, Short.MAX_VALUE)
                        .addComponent(lNext, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))))
        );
        pInforLayout.setVerticalGroup(
            pInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pInforLayout.createSequentialGroup()
                .addComponent(ltitle, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lPreview)
                    .addComponent(lNext))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pInfor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
                    .addComponent(pInfor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void treeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_treeMouseClicked
        if (evt.getClickCount() == 2) {
            if (this.tree.getSelectionPath()!=null) {
                Object selected = this.tree.getSelectionPath().getLastPathComponent();
                if (selected instanceof ActionHelpInformation) {
                    ActionHelpInformation actionHelpInformation = (ActionHelpInformation) selected;
                    this.paintActionHelper(actionHelpInformation);
                }
            }
        }
}//GEN-LAST:event_treeMouseClicked

//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//
//            public void run() {
//                SimpleHelpDialog dialog = new SimpleHelpDialog(new javax.swing.JFrame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lInt1;
    private javax.swing.JLabel lInt2;
    private javax.swing.JLabel lInt3;
    private javax.swing.JLabel lNext;
    private javax.swing.JLabel lPreview;
    private javax.swing.JLabel ltitle;
    private javax.swing.JPanel pInfor;
    private javax.swing.JTextArea taInfo;
    private javax.swing.JTree tree;
    // End of variables declaration//GEN-END:variables

    private void paintActionHelper(ActionHelpInformation a) {
        this.ltitle.setText(a.getTitle());
        String cad = "Resumen: \n" + a.getResumen() + "\n\n Descripción completa: \n" + a.getCompletText();
        this.taInfo.setText(cad);
        String title = a.getPreview() == null ? "" : "<<"+a.getPreview().getTitle();
        this.lPreview.setText(title);
        title = a.getNext() == null ? "" : a.getNext().getTitle()+">>";
        this.lNext.setText(title);
        title = a.getInteresting().size() == 0 ? "" : a.getInteresting().get(0).getTitle();
        this.lInt1.setText(title);
        title = a.getInteresting().size() <= 1 ? "" : a.getInteresting().get(1).getTitle();
        this.lInt2.setText(title);
        title = a.getInteresting().size() <= 2 ? "" : a.getInteresting().get(2).getTitle();
        this.lInt3.setText(title);
        lPreview.addMouseListener(new ShineLabelMouseListener(lPreview,a.getPreview()));
        lNext.addMouseListener(new ShineLabelMouseListener(lNext,a.getNext()));
        lInt1.addMouseListener(new ShineLabelMouseListener(lInt1,a.getInteresting().size() == 0 ? null : a.getInteresting().get(0)));
        lInt2.addMouseListener(new ShineLabelMouseListener(lInt2,a.getInteresting().size() <= 1 ? null : a.getInteresting().get(1)));
        lInt3.addMouseListener(new ShineLabelMouseListener(lInt3,a.getInteresting().size() <= 2 ? null : a.getInteresting().get(2)));
        this.pInfor.repaint();
    }
}
