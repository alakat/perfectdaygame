/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.main.laboratocGUI.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.core.event.manager.EventManager;
import org.perfectday.logicengine.core.event.manager.EventManagerThread;
import org.perfectday.logicengine.core.event.movement.MovedMiniEvent;
import org.perfectday.logicengine.model.battelfield.BattelField;
import org.perfectday.logicengine.model.battelfield.Field;
import org.perfectday.logicengine.model.battelfield.TypeField;
import org.perfectday.logicengine.model.command.combat.CombatResolutionCommand;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.minis.MiniLevel;
import org.perfectday.logicengine.movement.MasterMovement;
import org.perfectday.main.laboratocGUI.LaboratoryGUI;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class JBattelField extends JPanel {
    
    public static final Color Soldier       = new Color(0.3f,0.1f,0.1f);
    public static final Color Sargent       = new Color(0.4f,0.1f,0.1f);
    public static final Color Centurion     = new Color(0.5f,0.1f,0.1f);
    public static final Color Captain       = new Color(0.6f,0.1f,0.1f);
    public static final Color Hero          = new Color(0.7f,0.1f,0.1f);
    public static final Color Legendary_hero= new Color(0.8f,0.1f,0.1f);
    
    private BattelField battelField;
    private List<Field> miniAccess;
    private List<Field> keepAccess;
    private List<Field> keepSupporAccess;
    //Mini seleccionado para ver el apoyo
    private Mini supportSelectedMini;
    private Mini selectedMini;
    //Usado para pintar que mini es el seleccionado en la pila de activaciones.
    private Mini activationStackSelectedMini;
    private static final int SIZE_FIELD=30;   
    private static final int SIZA_SOLDIER=SIZE_FIELD-(SIZE_FIELD/5);
    private MessageVolante message;

    

   
    
    
    class MessageVolante{
        private int xScreen;
        private int yScreen;
        
        private String message;
        private int moved;

        public MessageVolante(int xScreen, int yScreen, String message) {
            this.xScreen = xScreen;
            this.yScreen = yScreen;
            this.message = message;
        }
        
        public void paint(Graphics g){
            int sizeMessage=message.length();
            g.drawRect(xScreen, yScreen, 32, sizeMessage);
            g.drawChars(message.toCharArray(),0, sizeMessage, xScreen, yScreen+24);
        }
    }
    
    
    public JBattelField() {
        miniAccess = new ArrayList<Field>();
        keepAccess = new ArrayList<Field>();
        keepSupporAccess = new ArrayList<Field>();
    }

    public BattelField getBattelField() {
        return battelField;
        
    }

    public void setBattelField(BattelField battelField) {
        this.battelField = battelField;
    }

    @Override
    public void paint(Graphics g) {
        
        if (this.battelField == null ){
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }else{
            Field[][] bf = this.battelField.getBattelfield();
            for(int i=0; i<bf.length;i++){
                for(int j=0;j<bf[i].length;j++){
                    if(this.miniAccess.contains(bf[i][j]))
                        printFieldAccesible(bf[i][j],g,i,j);                     
                    else
                        printField(g,bf[i][j],i,j);
                    if(this.keepAccess.contains(bf[i][j]))
                        printKeepField(g,bf[i][j],i,j);
                    if(this.supportSelectedMini!=null &&
                            (!this.keepSupporAccess.isEmpty()) &&
                            this.keepSupporAccess.contains(bf[i][j])){
                        paintArcSupportKeep(g, bf[i][j], i, j);
                    }
                    printOcupant(g,bf[i][j],i,j);
                }
                
            }
            
        }
        
        if (this.message!=null){
            message.paint(g);
        }
    }

    private Color getColor(TypeField typeField) {
        switch(typeField){
            case GRASS: return Color.GREEN; 
            case ROCK: return Color.GRAY;
            case TREE: return new Color(0.2f,0.9f,0.2f);
            default: return Color.BLACK;            
        }
    }

    private boolean inArray(int i, int j) {
        return (i>=0)&&(i<battelField.getHigth())&&(j>=0)&&(j<battelField.getWeidth());
    }

    private boolean isMiniSelected(int i, int j) {
       boolean inarray= inArray(i,j);
       if (inarray)
           return (battelField.getBattelfield()[i][j].getMiniOcupant()!=null);
       return false;
    }

    private void movedMiniMouseEvent(int j, int i) {
        //Movement mini
        Field targetField = battelField.getBattelfield()[i][j];
        if (this.miniAccess.contains(targetField)) {
            Field orig = this.battelField.getField(selectedMini);
            if (orig != null) {
                MovedMiniEvent miniEvent = 
                        new MovedMiniEvent(selectedMini, targetField);
                EventManager.getInstance().addEvent(miniEvent);
                synchronized(EventManagerThread.getEventManagerThread()){
                    EventManagerThread.getEventManagerThread().notifyAll();
                }
                //Antiguo modo de mover
//                orig.setMiniOcupant(null);
//                targetField.setMiniOcupant(selectedMini);
//                this.miniAccess.clear();
//                this.selectedMini = null;
//                UnitTime ut = LongUnitTimeFactory.getInstance().
//                        doMovementAction(Game.getInstance().getSelectedMini());
//                LaboratoryGUI.me.setTurnCost(ut);
//                LaboratoryGUI.me.fin_del_moviemiento();
            } else {
                LaboratoryGUI.me.addInfo("No field de origen");
            }
            this.repaint();
        }
    }

    private void paintMagicanSeal(Graphics g, int i, int j) {
        int pointx = (i*SIZE_FIELD)+(SIZE_FIELD/4);
        int pointy = (j*SIZE_FIELD)+(SIZE_FIELD/4);
        int cuarto = SIZE_FIELD/4;
        Color c = g.getColor();
        g.setColor(Color.BLACK);
        g.drawLine(pointx+cuarto, pointy, pointx, pointy+cuarto+cuarto);
        g.drawLine(pointx+cuarto, pointy, pointx+cuarto+cuarto, pointy+cuarto+cuarto);
        g.drawLine(pointx, pointy+cuarto, pointx+cuarto+cuarto, pointy+cuarto);
        g.drawLine(pointx, pointy+cuarto, pointx+cuarto+cuarto, pointy+cuarto+cuarto);
        g.drawLine(pointx, pointy+cuarto+cuarto, pointx+cuarto+cuarto, pointy+cuarto);
        g.setColor(c);
    }

    private void paintShield(Graphics g, int i, int j) {
        int pointx = (i*SIZE_FIELD) +(SIZE_FIELD/2);
        int pointy = (j*SIZE_FIELD) +(SIZE_FIELD/2);
        int [] x={(pointx),(pointx),(pointx+(SIZE_FIELD/4)),(pointx+(SIZE_FIELD/2)),(pointx+(SIZE_FIELD/2))};
        int [] y={(pointy),(pointy+(SIZE_FIELD/4)),(pointy+(SIZE_FIELD/2)),(pointy+(SIZE_FIELD/4)),(pointy)};
        Polygon p =new Polygon(x,y,x.length);
        g.fillPolygon(p);
        Color c = g.getColor();
        g.setColor(Color.BLACK);
        g.drawPolygon(p);
        g.setColor(c);
    }

    private void paintSoildier(int j, Graphics g, int i) {
        g.fillArc(i * SIZE_FIELD+2, j * SIZE_FIELD+2, SIZA_SOLDIER, SIZA_SOLDIER, 0, 360);
    }
    
    private void printAsessain(Graphics g, int i, int j) {
        g.fillArc(i * SIZE_FIELD+2, j * SIZE_FIELD+2, SIZA_SOLDIER, SIZA_SOLDIER, 0, 360);
        Color c  = g.getColor();
        g.setColor(Color.black);
        g.drawLine(i * SIZE_FIELD+((SIZA_SOLDIER/2)+5), j * SIZE_FIELD+((SIZA_SOLDIER/2)+5), i * SIZE_FIELD+10, j * SIZE_FIELD+5);
        g.drawLine(i * SIZE_FIELD+((SIZA_SOLDIER/2)+5), j * SIZE_FIELD+((SIZA_SOLDIER/2)+7), i * SIZE_FIELD+10, j * SIZE_FIELD+7);
    }

    private void paintTriangleKeep(Graphics g, Field field, int i, int j) {
        Field mField = this.battelField.getField(Game.getInstance().getSelectedMini());
        if(mField!=null){
            int xS = mField.getX()-i;
            int yS = mField.getY()-j;
            int x0,x1,x2,y0,y1,y2;
            this.printField(g, field, i, j);
            if(xS>0){
                //oeste

                x0 = (i+1)*SIZE_FIELD;
                y0 = (j)*SIZE_FIELD;
                x1 = (i)*SIZE_FIELD;
                y1 = (j*SIZE_FIELD)+(SIZE_FIELD/2);
                x2 = (i+1)*SIZE_FIELD;
                y2 = (j+1)*SIZE_FIELD;
            } else if(xS<0){
                //este
                x0 = i*SIZE_FIELD;
                y0 = (j*SIZE_FIELD);
                x1 = (i+1)*SIZE_FIELD;
                y1 = (j*SIZE_FIELD)+(SIZE_FIELD/2);
                x2=i*SIZE_FIELD;
                y2=(j+1)*SIZE_FIELD;
            }else if(yS>0){
                //norte

                x0 = (i)*SIZE_FIELD;
                y0 = (j+1)*SIZE_FIELD;
                x1 = ((i)*SIZE_FIELD)+(SIZE_FIELD/2);
                y1 = (j*SIZE_FIELD);
                x2 = ((i+1)*SIZE_FIELD);
                y2 = (j+1)*SIZE_FIELD;
            }else{
                //sur            
                x0 = (i)*SIZE_FIELD;
                y0 = (j)*SIZE_FIELD;
                x1 = ((i)*SIZE_FIELD)+(SIZE_FIELD/2);
                y1 = ((j+1)*SIZE_FIELD);
                x2 = ((i+1)*SIZE_FIELD);
                y2 = (j)*SIZE_FIELD;
            }
            int[] x= {x0,x1,x2};
            int[] y={y0,y1,y2};
            g.setColor(Color.yellow);
            g.drawPolygon(x,y, 3);
        }else{
            org.apache.log4j.Logger.getLogger(JBattelField.class)
                    .error("ERROR Al pintar el alcance mField ==null");
        }
    }

    private void paintArcSupportKeep(Graphics g, Field field, int i, int j) {
        Field mField = this.battelField.getField(this.supportSelectedMini);
        if(mField!=null){
            int xS = mField.getX()-i;
            int yS = mField.getY()-j;
            int x0,x1,x2,y0,y1,y2;
            this.printField(g, field, i, j);
            if(xS>0){
                //oeste

                x0 = (i+1)*SIZE_FIELD;
                y0 = (j)*SIZE_FIELD;
                x1 = (i)*SIZE_FIELD;
                y1 = (j*SIZE_FIELD)+(SIZE_FIELD/2);
                x2 = (i+1)*SIZE_FIELD;
                y2 = (j+1)*SIZE_FIELD;
            } else if(xS<0){
                //este
                x0 = i*SIZE_FIELD;
                y0 = (j*SIZE_FIELD);
                x1 = (i+1)*SIZE_FIELD;
                y1 = (j*SIZE_FIELD)+(SIZE_FIELD/2);
                x2=i*SIZE_FIELD;
                y2=(j+1)*SIZE_FIELD;
            }else if(yS>0){
                //norte

                x0 = (i)*SIZE_FIELD;
                y0 = (j+1)*SIZE_FIELD;
                x1 = ((i)*SIZE_FIELD)+(SIZE_FIELD/2);
                y1 = (j*SIZE_FIELD);
                x2 = ((i+1)*SIZE_FIELD);
                y2 = (j+1)*SIZE_FIELD;
            }else{
                //sur            
                x0 = (i)*SIZE_FIELD;
                y0 = (j)*SIZE_FIELD;
                x1 = ((i)*SIZE_FIELD)+(SIZE_FIELD/2);
                y1 = ((j+1)*SIZE_FIELD);
                x2 = ((i+1)*SIZE_FIELD);
                y2 = (j)*SIZE_FIELD;
            }
            int[] x= {x0,x1,x2};
            int[] y={y0,y1,y2};
            g.setColor(Color.PINK);            
            g.fillPolygon(x,y, 3);
        }else{
            org.apache.log4j.Logger.getLogger(JBattelField.class)
                    .error("ERROR Al pintar el alcance mField ==null");
        }
    }
    
    private void printField(Graphics g, Field field, int i, int j) {
        Color c=Color.BLACK;
        c = getColor(field.getTypeField());
        g.setColor(c);
        g.fillRect(i*SIZE_FIELD, j*SIZE_FIELD, SIZE_FIELD, SIZE_FIELD);
        g.setColor(Color.BLACK);
        g.drawRect(i*SIZE_FIELD, j*SIZE_FIELD, SIZE_FIELD, SIZE_FIELD);
    }

    @Override
    protected void processMouseMotionEvent(MouseEvent e) {
        int i = e.getX()/JBattelField.SIZE_FIELD;
        int j = e.getY()/JBattelField.SIZE_FIELD;
        if(isMiniSelected(i,j)){
                LaboratoryGUI.me.getJMiniInfo1().setVisible(true);
                LaboratoryGUI.me.getJMiniInfo1().setMini(
                        battelField.getBattelfield()[i][j].getMiniOcupant()
                        );
                LaboratoryGUI.me.getTabs().setSelectedIndex(0);
                //Pintamos quien es en la pila de acciones.
                LaboratoryGUI.me.getActivationStackPanel().setSelectedMini(
                        battelField.getBattelfield()[i][j].getMiniOcupant());
                //Cargamos el alcance de Apoyo
                if( battelField.getBattelfield()[i][j].getMiniOcupant().getSupport()!=null ){
                    keepSupporAccess.clear();
                   keepSupporAccess.addAll(
                            battelField.getBattelfield()[i][j]
                            .getMiniOcupant().getSupport().getSupportKeep()
                            .getFieldKeeped(battelField.getBattelfield()[i][j]));
                   this.supportSelectedMini=
                           battelField.getBattelfield()[i][j].getMiniOcupant();
                   this.repaint();
                }
                    
            }else{
                keepSupporAccess.clear();
                supportSelectedMini=null;
                this.repaint();
                LaboratoryGUI.me.getActivationStackPanel().setSelectedMini(null);
            }
    }

    
    @Override
    protected void processMouseEvent(MouseEvent e) {
        //super.processMouseEvent(e);
        
        int i = e.getX()/JBattelField.SIZE_FIELD;
        int j = e.getY()/JBattelField.SIZE_FIELD;
        if( e.getID() ==MouseEvent.MOUSE_CLICKED){
            if(this.keepAccess.isEmpty()){
                //Si no hay celdas de alcanze es un movimiento
//                System.out.println(" EVent D["+i+","+j+"]");
                if ( this.selectedMini!=null){
                    movedMiniMouseEvent(j, i);
                }
            }else{
                //Es un ataque
                if(isMiniSelected(i, j)){
                    LaboratoryGUI.me.setTargetMini(battelField.getBattelfield()[i][j].getMiniOcupant());              
                }
            }
        }else if(e.getID()==MouseEvent.MOUSE_MOVED){
            if(isMiniSelected(i,j)){
                LaboratoryGUI.me.getJMiniInfo1().setVisible(true);
                LaboratoryGUI.me.getJMiniInfo1().setMini(
                        battelField.getBattelfield()[i][j].getMiniOcupant()
                        );
               
            }
        }
         this.repaint();
    }

    private Color getMiniColor(MiniLevel miniLevel){
        switch(miniLevel){
            case SIMPLE_SOLDIER: return JBattelField.Soldier;
            case SARGENT:return JBattelField.Sargent;
            case CENTURION: return JBattelField.Centurion;
            case CAPTAIN: return JBattelField.Captain;
            case HERO: return JBattelField.Hero;
            case LEGENDARY_HERO:return JBattelField.Legendary_hero;
            default: return Color.black;
        }
    }

    private void printFieldAccesible(Field field, Graphics g, int i, int j) {
       Color c=Color.BLACK;
        c = getColor(field.getTypeField());
        g.setColor(c);
        g.fillRect(i*SIZE_FIELD, j*SIZE_FIELD, SIZE_FIELD, SIZE_FIELD);
        g.setColor(Color.RED);
        g.drawRect(i*SIZE_FIELD, j*SIZE_FIELD, SIZE_FIELD-1, SIZE_FIELD-1);
    }

    private void printKeepField(Graphics g, Field field,int i,int j) {
        Color c=Color.BLACK;
        c = getColor(field.getTypeField());
        g.setColor(c);
        g.fillRect(i*SIZE_FIELD, j*SIZE_FIELD, SIZE_FIELD, SIZE_FIELD);
        g.setColor(Color.RED);
        paintTriangleKeep(g,field,i,j);
    }

    private void printLaza(Graphics g, int i, int j) {
        int pointX = i*SIZE_FIELD;
        int[] x = {(pointX+(SIZE_FIELD/2)),(pointX+((3*SIZE_FIELD)/4)),(pointX+SIZE_FIELD)};
        int pointY = j*SIZE_FIELD;
        int[] y={(pointY+(SIZE_FIELD/2)),(pointY),(pointY+(SIZE_FIELD/2))};
        
        g.fillPolygon(new Polygon(x, y, 3));
        Color c = g.getColor();
        g.setColor(Color.BLACK);
        g.drawPolygon(new Polygon(x, y, 3));
        g.setColor(c);
    }
    
    private void printSoldier(Graphics g, int i, int j){
        paintSoildier(j, g, i);
    }
    private void printArcher(Graphics g, int i,int j){
        g.fillArc((i*SIZE_FIELD)-(SIZE_FIELD/4)+2, j*SIZE_FIELD+2,SIZA_SOLDIER,SIZA_SOLDIER, 0,90); 
        g.fillArc((i*SIZE_FIELD)-(SIZE_FIELD/4)+2, j*SIZE_FIELD+2,SIZA_SOLDIER,SIZA_SOLDIER, 0,-90); 
        g.fillRect((i*SIZE_FIELD), (j*SIZE_FIELD)+(SIZE_FIELD/4),
                SIZE_FIELD-(SIZE_FIELD/8), (SIZE_FIELD/16));
        Polygon p = puntaflecha (i,j);
        g.fillPolygon(p) ;
    }
    private void printLancer(Graphics g, int i,int j){
        paintSoildier(j,g,i); 
        printLaza(g,i,j);
    }
    private void printMagican(Graphics g, int i,int j){
        paintSoildier(j,g,i); 
        paintMagicanSeal(g,i,j);
    }
    private void printGoliath(Graphics g, int i,int j){
        paintSoildier(j,g,i); 
        paintShield(g,i,j);
    }
    private void printOcupant(Graphics g, Field field, int i, int j) {
        if(field.getMiniOcupant()!=null){
            if( this.selectedMini!=null && field.getMiniOcupant().equals(this.selectedMini))
                g.setColor(Color.orange);
            else
                g.setColor(getMiniColor(field.getMiniOcupant().getMiniLevel()));
            switch(field.getMiniOcupant().getMiniType()){
                case SOLDIER:printSoldier(g, i, j);break;
                case ARCHER: printArcher(g, i, j); break;
                case GOLIATH:printGoliath(g, i, j); break;
                case MAGICAN: printMagican(g, i, j); break;
                case LANCER: printLancer(g, i, j); break;
                case ASSASAIN: printAsessain(g,i,j); break;
            }
            //Pintamos bandera.
            Color c = (Color) Game.getInstance().getDintingibleObjectBand(field.getMiniOcupant());
            g.setColor(c);
            g.fillRect(i*SIZE_FIELD, j*SIZE_FIELD, 8, 3);
            //Si esta seleccionado en la pila de activación pintamos un punto 
            //amarillo en el interios
            if ( this.activationStackSelectedMini != null &&
                   field.getMiniOcupant()!=null &&
                   field.getMiniOcupant().equals(this.activationStackSelectedMini)){
                g.setColor(Color.yellow);
                g.fillArc(((i*SIZE_FIELD)-2)+(SIZE_FIELD/2), ((j*SIZE_FIELD)-2)+(SIZE_FIELD/2), 4, 4, 0, 360);
            }
        }
    }

    public List<Field> getMiniAccess() {
        return miniAccess;
    }

    public void setMiniAccess(List<Field> miniAccess) {
        this.miniAccess = miniAccess;
    }

    public Mini getSelectedMini() {
        return selectedMini;
    }

    public void setSelectedMini(Mini selectedMini) {
       this.miniAccess.clear();
        this.selectedMini = selectedMini;
        if(selectedMini!=null){
            long t0 =System.currentTimeMillis();
            List<Field> movementField =
                    MasterMovement.getInstance().getNearbyField( selectedMini,
                    battelField);
//            System.out.println("Tiempo busqueda:"+(System.currentTimeMillis()-t0));
            this.miniAccess.addAll(movementField);
        }
    }
    
    public void setSelectedMiniNoSearchFieldsAccess(Mini selectedMini){
        this.selectedMini = selectedMini;
    }

    public List<Field> getKeepAccess() {
        return keepAccess;
    }

    public void setKeepAccess(List<Field> keepAccess) {
        this.keepAccess = keepAccess;
    }

    private Polygon puntaflecha(int i, int j) {
        int[] x = {((i*SIZE_FIELD)+(SIZE_FIELD-(SIZE_FIELD/8))),
                    ((i*SIZE_FIELD)+(SIZE_FIELD-(SIZE_FIELD/8))),
                    ((i+1)*SIZE_FIELD)};
        int[] y ={((j*SIZE_FIELD)+(SIZE_FIELD/8)),((j*SIZE_FIELD)+((3*SIZE_FIELD)/8)),((j*SIZE_FIELD)+(SIZE_FIELD/4))};
        Polygon p = new Polygon(x, y, 3);
        return p;
    }
    
    /**
     * Pinta un cuadro de texto donde se informa del resultado del combate.
     * @param crc
     */
     public void showInfoCombatResolution(CombatResolutionCommand crc) {
//         Field f = this.battelField.getField(crc.getAtacker());
//         int xPos = f.getX()*SIZE_FIELD;
//         int yPos = f.getY()*SIZE_FIELD;
//        this.message = new MessageVolante(xPos,yPos,crc.toString());
    }

    public Mini getActivationStackSelectedMini() {
        return activationStackSelectedMini;
    }

    public void setActivationStackSelectedMini(Mini activationStackSelectedMini) {
        this.activationStackSelectedMini = activationStackSelectedMini;
    }
    
    

     
   
    
    

}
