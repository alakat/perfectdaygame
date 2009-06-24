package mapTools;


import java.util.Vector;

/**
 *
 * @author Juan José Sánchez Álvarez
 *         juanjomalaga@gmail.com
 */
public class MapEntity {
    
    //Esta clase refleja los datos de un mapa.
    
    //Variables
    private int[][] logicMap;       //Valores lógicos del mapa, indica por ejemplo por donde puede moverse un personaje.
    private int[][] meshMap;        //Indica los modelos que van a formar la malla 3D
    private Vector surfaceLayerTex; //Indica las texturas añadidas al mesh (carreteras por ejemplo).
    private Vector objects3DLayer;  //Indica los adornos del mapa (árboles, casas, ect...)

    //Constructor.
    public MapEntity(int[][] lMap, int[][]mMap, Vector surTex, Vector obj3DL){
        
        //Crea la nueva entidad.
        logicMap = lMap;
        meshMap = mMap;
        surfaceLayerTex = surTex;
        objects3DLayer = obj3DL;
    }    
    //Métodos de instancia.
    public int[][] getLogicMap(){
        
        return logicMap;
    }
    public int[][] getMeshMap(){
        
        return meshMap;
    }
    public Vector getSurfaceLayerTex(){
        
        return this.surfaceLayerTex;
    }
    public Vector getObject3DLayer(){
        
        return this.objects3DLayer;
    }
}
