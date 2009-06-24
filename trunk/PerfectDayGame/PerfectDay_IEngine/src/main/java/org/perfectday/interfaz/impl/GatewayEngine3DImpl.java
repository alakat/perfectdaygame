/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.interfaz.impl;

import com.threed.jpct.SimpleVector;
import engine3D.AnimationStack;
import engine3D.DiskLoader;
import engine3D.Engine3D;
import java.util.List;
import org.apache.log4j.Logger;
import org.perfectday.interfaz.GatewayEngine3D;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.model.battelfield.Field;
import org.perfectday.logicengine.model.minis.Mini;

/**
 *
 * Implementación de la pasarela modelo-engine3D
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class GatewayEngine3DImpl implements GatewayEngine3D {

    private static final Logger logger = Logger.getLogger(GatewayEngine3DImpl.class);
    private Engine3D engine;
    
    //Incluyo dos animaciones más
    public static final int ANIMATION_IDLE = 0;
    public static final int ANIMATION_RUN = 6;
    
    public static final int ANIMATION_DEATH_MINI=1;
    public static final int ANIMATION_ACTACK_1 =2;
    public static final int ANIMATION_ACTACK_2 =3;
    public static final int ANIMATION_SUPPORT=4;
    public static final int ANIMATION_ACTION = 5;
    
    
    
    

    public GatewayEngine3DImpl() {
        
        //Puesto sin sombras hasta que finalize la implementación de static-sadows y se hable con los modeladores
        //de lo necesario para el estatic-shadow de cada mini
        this.engine = new Engine3D(640, 480, 35, Engine3D.NO_SHADOWS, Engine3D.SAMPLINGMODE_NORMAL);
        DiskLoader diskLoader = engine.getDiskLoader();
        //Cargamos los directorios de archivos.
        diskLoader.LoadTextureDirectory("textures");
        //cargamos el resto.
        diskLoader.LoadModelDirectory("models",1f);
        //Ejecutamos le hebra del engine3D.
        try
        {		
        	engine.setPriority(Thread.NORM_PRIORITY);
        	engine.start();
        	engine.setModeWireframe(false);       	
        }
        catch (StackOverflowError e)
        {
        	logger.error("StackOverflowError : "+e.getMessage(),e);
        	engine.getEngineLog().writeEngineLog("Salida anormal del engine3D por StackOverFlowError :"+e.getMessage());
        	engine.destroyEngine3D();
        	//Prohibidisimo : System.exit(0);
        }
        catch (OutOfMemoryError e)
        {
        	logger.error("OutOfMemoryError : "+e.getMessage(),e);
        	engine.getEngineLog().writeEngineLog("Salida anormal del engine3D por OutOfMemoryError :"+e.getMessage());
        	engine.destroyEngine3D();
        	//Prohibidisimo : System.exit(0);
        }
        catch (Exception e)
        {
        	logger.error("Salida anormal del engine3D : "+e.getMessage(),e);
        	engine.getEngineLog().writeEngineLog("Salida anormal del engine3D :"+e.getMessage());
        	engine.destroyEngine3D();
        	//Prohibidisimo : System.exit(0);
        }
    }
    
    @Override
    public void init(List<Mini> army1, List<Mini> army2) {        
        
        //Ponemos temporalmente la dirección a la que mira cada equipo hasta
        //ver como implementar correctamente "getOrientacion(Field f)"
        
        /* Vamos a cargar el mapa. */
        createTerrain();
         
        
        for (Mini mini : army2) {
            Field f = Game.getInstance().getBattelField().getField(mini);
            engine.getHandlEngine().insertNewAnimatedCharacterInWorld(
                    obtainModelByMini(mini),
                    obtainTextureByMini(mini),
                    ""+mini.getId(),
                    generatePosition(f),Engine3D.DEG_ESTE, generateAxisOrientation(f));
        }
        
        for (Mini mini : army1) {
            Field f = Game.getInstance().getBattelField().getField(mini);
//            engine.getHandlEngine().insertNewAnimatedCharacterInWorld(
//                    obtainModelByMini(mini),
//                    obtainTextureByMini(mini),
//                    ""+mini.getId(),
//                    generatePosition(f), Engine3D.DEG_OESTE, generateAxisOrientation(f));
        }
         this.engine.getHandlEngine().setRenderizar(true);
        
    }
    
    @Override
    public void action(Mini mini) {
        this.engine.getAnimationStack().addAnimationActionToStack(""+mini.getId(),ANIMATION_ACTION , Engine3D.MODE_NOT_LOOP);        
    }

    @Override
    public void atack(Mini mini,boolean primaryAtack) {
        if(primaryAtack)
            this.engine.getAnimationStack().addAnimationActionToStack(""+mini.getId(),ANIMATION_ACTACK_1 , Engine3D.MODE_NOT_LOOP);        
        else
            this.engine.getAnimationStack().addAnimationActionToStack(""+mini.getId(),ANIMATION_ACTACK_2 , Engine3D.MODE_NOT_LOOP);        
    }

    @Override
    public void death(Mini mini) {
        this.engine.getAnimationStack().addAnimationActionToStack(""+mini.getId(),ANIMATION_DEATH_MINI , Engine3D.MODE_NOT_LOOP);        
    }

    @Override
    public void movedMini(Mini mini, Field ini, Field end) {
        
        //Calculamos el camino.
        int [] directions = generateHalmitonRoad(ini,end);        
        //Ponemos al mini con la animación de correr en modo loop, pues debe durar durante todos los movimientos.        
        //Indicamos que no esperamos a finalizar la animación ya que hay que ejecutar el desplazamiento MIENTRAS realiza
        //dicha animación de correr.
        AnimationStack animSt = this.engine.getAnimationStack();
        animSt.addAnimationActionToStack(""+mini.getId(), ANIMATION_RUN, Engine3D.MODE_LOOP_NOT_WAIT);
        //Ahora realizamos los movimientos por el mundo.
        for (int i = 0; i < directions.length; i++)
        {
            //PRESUPONEMOS QUE DIRECTIONS SOLO CONTIENE VALORES VALIDOS!.
            //Ej  i == Engine3D.DIR_NORTE
            animSt.addMoveActionToStack(""+mini.getId(), i, Engine3D.SPEED_TURN, Engine3D.SPEED_RUN);
        }
        //Al terminar el desplazamiento, el mini tiene que dejar de mostrar la animación correr y mostrar IDLE.
        animSt.addAnimationActionToStack(""+mini.getId(), ANIMATION_IDLE, Engine3D.MODE_LOOP_NOT_WAIT);              
    }

    @Override
    public void selectedMini(Mini mini, Field field) {
        
        //Creamos una señal indicatorio en la posición celda en la que se encuentra.
        engine.getHandlEngine().insertNewSignal(Engine3D.SIGNAL_MESH, "selected-"+mini.getId(), Engine3D.GRP_MINI_SELECTED, true , field.getX(), field.getY());
    }   
    
    @Override
    public void deselectedMini(Mini mini, Field field) {
        
        //Eliminamos la señal con indetificativo "selectedMini".
        engine.getHandlEngine().removeSignal("selected-"+mini.getId());
    }
    
    @Override
    public void activated(Mini mini, Field field, List<Field> accesible) {
        
        //Se marca el mini como seleccionado.
        selectedMini(mini, field);
        //Marcamos las celdas de movimiento.
        showtargets(accesible);
    }
    
    @Override
    public void deactivated(Mini mini, Field field, List<Field> accesible) {
        
        //Desmararcamos el mini como seleccionado.
        deselectedMini(mini, field);
        //Desmarcamos las celdas de movimiento.
        hidetargets(accesible);
    }

    @Override
    public void showtargets(List<Field> accesible) {
        
        //Insertamos señales para cada una de las casillas field de la lista.
        for (int i = 0; i < accesible.size(); i++)
        {
            Field field = (Field)accesible.get(i);
            engine.getHandlEngine().insertNewSignal(Engine3D.SIGNAL_MESH, "targets"+Integer.toString(i), Engine3D.GRP_FIELD_MOV, true , field.getX(), field.getY());            
        }
        
    }
    
    @Override
    public void hidetargets(List<Field> accesible) {
        
        //Eliminamos señales para cada una de las casillas field de la lista.
        for (int i = 0; i < accesible.size(); i++)
        {
            Field field = (Field)accesible.get(i);
            engine.getHandlEngine().removeSignal("targets"+Integer.toString(i));            
        }
    }

    @Override
    public void support(Mini mini) {
        
        //Ponemos en la cola de animaciones del engine3D una animación del mini pasado por argumento.
        //Dicha animación está ahora mismo configurada para realizarse a la par que la siguiente que haya
        //en la pila, pues no esperamos que termine hacer la animación de "support" para que la pila realize otra 
        //animación (Modo NO_LOOP).
        this.engine.getAnimationStack().addAnimationActionToStack(""+mini.getId(),ANIMATION_SUPPORT , Engine3D.MODE_NOT_LOOP);        
    }

    private void createTerrain() {
        int [][] codeRepresentationMap = 
                new int[Game.getInstance().getBattelField().getBattelfield().length]
                [Game.getInstance().getBattelField().getBattelfield()[0].length];
          this.engine.getHandlEngine().createNewTerrain(50000);
        String[] terrainsObjs = {"ground.3ds","ground.3ds"};
        String[] terrainsTexs = {"grass.png","rock.png","cell.gif"};
        
        for (int i = 0; i < Game.getInstance().getBattelField().getBattelfield().length; i++) {
            Field[] fields = Game.getInstance().getBattelField().getBattelfield()[i];
            for (int j = 0; j < fields.length; j++) {
                Field field = fields[j];
                switch(field.getTypeField()){
                    case GRASS:codeRepresentationMap[i][j]=0;
                        break;
                    case  TREE:codeRepresentationMap[i][j]=0;
                        break;
                    case ROCK:codeRepresentationMap[i][j]=1;
                        break;
                }
            }
            
        }
        this.engine.getHandlEngine().generateNewTerrainMap(codeRepresentationMap, terrainsObjs, terrainsTexs, null);
        this.engine.getHandlEngine().insertTerrainToWorld("TerrenoPrincipal");
    }

    /**
     * Genera la orientación sobre Axis a partir de la posición que ocupa el mini
     * @param f
     * @return
     */
    private SimpleVector generateAxisOrientation(Field f) {
        //Dummy
        //Devolvemos una orientación de "altura" con el eje "Y", es correcto.
        //Rotaremos los objetos sobre el eje Y del entorno virtual.
        return new SimpleVector(0, 1,0);
    }

    private int[] generateHalmitonRoad(Field ini, Field end) {
        
        //¿no será camino de Dijkstra?
        
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Genera la orientación en el terreno a partir de la posición que ocupa el mini
     * @param f
     * @return
     */
    private int generateOrientation(Field f) {
        
        //Mumy 180
        //Para ver la orientación según el terreno de un field, habría que saber
        return 180;
    }

    /**
     * Genera la posiciónen el terreno a partir de la posición que ocupa el
     * el BattleField (Coordenadas discretas).
     * @param f
     * @return
     */
    private SimpleVector generatePosition(Field f) {
        //throw new UnsupportedOperationException("Not yet implemented");
        //Obtenemos los valores X e Y de f y los convertimos en un simpleVector.
        //Hay que tener en cuenta que la altura en el mundo virtual corresponde
        //a la inversa del eje Y, por lo que el eje Z de Field es el eje -Y del engine.
        logger.info("Vector de posición ("+f.getX()+","+( -(f.getZ()))+","+ f.getY()+")");
        return (new SimpleVector(f.getX(), -0.15f, f.getY()));
    }
    /**
     * Devuelve los valores X e Y que posee dicha instancia FIELD.
     * (Coordenadas discretas).
     * @param f
     * @return
     */
    private SimpleVector getXYFieldPosition(Field f) {

        return (new SimpleVector(f.getX(), this.engine.GROUND_LEVEL, f.getY()));
    }
    /**
     * Obtiene el nombre del fichero de modelado md2  a partir del tipo de mini
     * en la versión beta los modelos de los mini vendrán determinados por el tipo
     * un tipo soldado tendra un modelo em "models" que se llamara soldiers.md2
     * @param mini
     * @return
     */
    private String obtainModelByMini(Mini mini) {
        return mini.getMiniType().toString()+".md2";
    }

    /**
     * Se obtiene a partir del tipo y nivel del mini. y será un jpg.
     * @param mini
     * @return
     */
    private String obtainTextureByMini(Mini mini) {
        
        //
        //  ESTO HAY QUE MIRARLO, PUEDE SER MUCHAS COSAS, JPG, PNG, GIF, ect...
        //  SI HAY QUE USAR UN ÚNICO FORMATO, DEBE SER UNO QUE SOPORTE CANAL ALFA
        //  COMO POR EJEMPLO PNG. SI NO, ESTAMOS JODIDOS xD
        //  
        //return mini.getMiniType().toString()+"-"+mini.getMiniLevel().toString()+".jpg";
        return mini.getMiniType().toString()+".JPG";
    }

    @Override
    public Engine3D getEngine3D() {
        return this.engine; 
    }

    
}
