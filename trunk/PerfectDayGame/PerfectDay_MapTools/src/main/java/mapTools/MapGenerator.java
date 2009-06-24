package mapTools;

import java.awt.Point;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author Juan José Sánchez Álvarez
 *         juanjomalaga@gmail.com
 */
public class MapGenerator {

    //Constantes.
    public final static String ESTATE_0_7 = "Nothing";
    public final static String ESTATE_1_7 = "Generating Variables.";
    public final static String ESTATE_2_7 = "Generating random logic map.";
    public final static String ESTATE_3_7 = "Generating mesh map.";
    public final static String ESTATE_4_7 = "Adapting logic map to mesh map.";
    public final static String ESTATE_5_7 = "Generating secondary textures.";
    public final static String ESTATE_6_7 = "Generating world objects.";
    public final static String ESTATE_7_7 = "Generating map instance.";
    
    public final static int LOGIC_BLOQUED = 1;
    public final static int LOGIC_NON_BLOQUED = 0;
    
    private final static int GROUND = 0;    
    private final static int MOUNTAIN = 1;
    private final static int PATH = 2;  
    private final static int WATER = 3;    
    private final static int BRIDGE = 4;  
    //Valores de escala aproximada para generar mapas aleatorios.
    private final static float MAX_MOUNTAIN_FACTOR = 0.50f;     //Valores máximos en porcentaje.
    private final static float MIN_MOUNTAIN_FACTOR = 0.10f;     //Valores mínimos en porcentaje.
    private final static float MAX_MOUNTAIN_GROUPS = 0.20f;     //Máximo grupos de montañas en un mapa.  
    private final static float MIN_MOUNTAIN_GROUPS = 0.10f;     //Mínimo grupos de montañas en un mapa. 
    private final static float CREATE_RIVERS = 0.30f;           //Probabilidad de que el mapa tenga un rio.
    private final static int TOTAL_PERCENT_INT = 100;           //Valores totales del 100%    
    private final static int MAX_DIFFUSE_MOUNTAIN_ZONE_AROUND_CENTER = 2;   //Para esparcir la montaña sobre su centro.El valor es inverso!
    private final static int MAX_PATHS_ARRAYS = 5;             //Máximos paths que se pueden generar, realmente es un random de 1 a max_paths_arrays.

    //Zona de jugadores.
    private final static int MIN_SIZE_HEIGHT_PLAYER_START_POSITION = 2;     //2 filas libres para posicionar jugadores en los extremos del mapa.    
    //Variables estáticas de la clase.
    private static Random random;
    
    //Estas instancias se encargan de generar y devolver mapas.
    private Vector[] paths;   //Para almacenar rutas de Points que no deben ser bloqueadas al insertar objetos.
    private String estate;  //Esta variable nos va a indicar qué se está haciendo ahroa mismo.
                            //Puede servir de ayuda para mostrar algún menú o barra de carga mientras
                            //Se genera un mapa.
    
    
    //Constructor
    public MapGenerator(){
        
        //Creamos una instancia de esta clase iniciando el estado de creación de mapas.
        estate = ESTATE_0_7;  
        //Creamos un objeto random.
        random = new Random();
    }
    //Métodos de instancia.
    public MapEntity generateNewMap(int sizeX, int sizeY){
       
        //Variables para generar el mapa.
        estate = ESTATE_1_7;
        int[][] logicMap = null;
        int[][] meshMap = null;
        Vector surfaceTextureLayer = null;
        Vector objects3DLayer = null;
        //Generamos sus valores del mapa lógico.
        estate = ESTATE_2_7;
        logicMap = this.generateLogicMap(sizeX, sizeY);
        //Generamos los valores del mapa de mesh a partir del mapa lógico.
        estate = ESTATE_3_7;
        meshMap = this.generateViableMeshMap(logicMap);
        //Como el mapa de mesh tiene unas condiciones a la hora de generarse,es posible que
        //haya que adaptar el mapa lógico al mesh generado. Así que lo hacemos.
        estate = ESTATE_4_7;
        logicMap = getAdaptedLogicMap(meshMap);
        //Generamos los valores de texturas secundarias.
        estate = ESTATE_5_7;
        surfaceTextureLayer = this.generateSurfaceTextureLayer();
        //Generamos los adornos del mapa modificando el mapa lógico si fuese necesario.
        estate = ESTATE_6_7;
        objects3DLayer = this.generateObjects3DLayer(logicMap);                        
        //Creamos la entidad
        estate = ESTATE_7_7;
        MapEntity newMap = new MapEntity(logicMap, meshMap, surfaceTextureLayer, objects3DLayer);
        //Liberamos las rutas creadas y que ya están en el mapa lógico.
        this.paths = null;
        estate = ESTATE_0_7;
        //Devolvemos
        return newMap;
    }
    public String getMapGenerationState(){
        
        //Devuelve el estado en que está el generador de mapas.
        return this.estate;
    }
    //Métodos privados de instancia.    
    private int[][] generateLogicMap(int sizeX, int sizeY){
        
        //Generamos variable.
        int[][] logicMap = new int[sizeX][sizeY];
        //Generamos el mapa aleatorio.
        //Obtenemos el porcentaje de montañas.
        int maxMountains = (int)((sizeX * sizeY * MAX_MOUNTAIN_FACTOR)); 
        int minMountains = (int)((sizeX * sizeY * MIN_MOUNTAIN_FACTOR));
        System.out.println("max y min "+ Integer.toString(maxMountains)+","+Integer.toString(minMountains));
        int randomValue = 0;
        while (randomValue < minMountains)
        {
            randomValue = random.nextInt(maxMountains);
            System.out.println("randomValue "+ Integer.toString(randomValue));
        }
        //Ya tenemos un valor de montañas usando MIN_MOUNTAIN_FACTOR y MAX_MOUNTAIN_FACTOR.
        //Ahora hay que distribuirlo por el mapa en forma de agrupaciones.
        logicMap = this.generateMountainsGroup(logicMap, randomValue);     
        //Chequeamos que existan rutas para los jugaadores.
        this.checkAvaiablePaths(logicMap);
        //Vemos si generamos rios (Se ve dentro si es necesario o si no. Aquí solo hacemos la llamada.        
        this.generateRivers(logicMap);   
        //Devolvemos.
        return logicMap;
    }
    private int[][] generateMountainsGroup(int[][] map, int mountainsValue){
        
       //Pasamos la variable para trabajar con ella.
       int mValue =  mountainsValue;
       //Creamos una variable para guardar los centros de grupos de montañas y luego usarlos para expandirlas.
       Vector centerM = new Vector();        
       //Vemos cuantos grupos hay que meter en el mapa. (Depende un poco del tamaño del mapa.)       
       int maxGroups = (int)((map.length*map[0].length*MAX_MOUNTAIN_GROUPS));
       int minGroups = (int)((map.length*map[0].length*MIN_MOUNTAIN_GROUPS));
       int groupsPercent = 0;
       int maxIter = 30;    //Máximos intentos para generar montañas, así si hay algún problema, evita que se
       int iter = 0;        //quede colgado este algorimo buscando montañas.
       while ((iter < maxIter)&&((groupsPercent <= 0)||(groupsPercent < minGroups)||(mValue <= groupsPercent)))
       {
           groupsPercent = random.nextInt(maxGroups);
           System.out.println("groups"+ Integer.toString(groupsPercent)); 
           iter++;
       }       
       if (mValue > groupsPercent)
       {
           //Generamos los centros de los grupos en posiciones aleatorias.
           for (int i = 0; i < groupsPercent; i++)
           {
               //Marcamos el inicio de una montaña.
               int randomX = random.nextInt(map.length-1);
               int randomY = random.nextInt(map[0].length-1);
               map[randomX][randomY] = this.MOUNTAIN;
               mValue = mValue - 1;
               System.out.println("mValue "+ Integer.toString(mValue));
               //Almacenamos el centro de dicha montaña.
               centerM.add(new Point(randomX,randomY));
           }
           //Ahora ya tenemos los inicios de montañas marcados de forma aleatoria en el mapa.
           //Solo queda repartir los valores mValue que quedan alrededor de los puntos de montañas
           //marcados en el mapa. Los sacamos de forma desordenada para aumentar la aleatoriedad.
           while (!centerM.isEmpty())
           {
               //Si quedan montañas por repartir
               if (mValue > 0)
               {
                    //Hacemos un poco más de mezcla.
                    int mix = random.nextInt(groupsPercent);
                    if ((mix == 0)||((mValue/mix) < 0))
                    {
                        //Para evitar error de división por cero.
                        mix = 1;
                    }
                    //Guardamos una variable para calcular un aleatorio del número de celdas montaña por cada centro
                    //de montañas.
                    int randMuntainsPerCenter = 0;
                    if (mValue/mix <= 0)
                    {
                        randMuntainsPerCenter = mValue;
                    }
                    else
                    {
                        randMuntainsPerCenter = mValue/mix;
                    }
                    //Vemos cuantas celdas ocupa este grupo de montaña
                    int numMountains = random.nextInt(randMuntainsPerCenter);
                    //restamos los valores que vamos a usar.
                    mValue = mValue - numMountains;
                    //los repartimos. Primero sacamos un centro de montaña aleatorio.
                    int randomCenter = random.nextInt(centerM.size());
                    Point centerMountain = (Point)centerM.remove(randomCenter);
                    int centerX = centerMountain.x;
                    int centerY =  centerMountain.y;
                    //Ya tenemos el centro del montaña, ahora repartimos los valores numMountais por dicha zona.
                    int areaX = numMountains/this.MAX_DIFFUSE_MOUNTAIN_ZONE_AROUND_CENTER;
                    int areaY = numMountains/this.MAX_DIFFUSE_MOUNTAIN_ZONE_AROUND_CENTER;
                    //Repartimos hasta gastarlas todas.
                    while (numMountains != 0)
                    {
                        for (int ax = -areaX; (ax < areaX)&&(numMountains != 0);  ax++)
                        {
                            for (int ay = -areaY; (ay < areaY)&&(numMountains != 0);  ay++)
                            {
                                //Vemos si estamos en los límites del mapa.
                                int posx = centerX + ax;
                                int posy = centerY + ay;

                                if ((posx >= 0)&&(posx < map.length)&&(posy >= 0)&&(posy < map[0].length))
                                {
                                    if (map[posx][posy] == this.GROUND)
                                    {
                                        map[posx][posy] = this.MOUNTAIN;
                                        numMountains = numMountains - 1;
                                        System.out.println("numMountain "+ Integer.toString(numMountains));
                                    }
                                }
                            }
                        }
                        //Incrementamos, así evitamos que nos queden más huecos libres y no se hayan puesto todos los numMountains,
                        //por lo que estaríamos en un bucle infinito.
                        areaX++;
                        areaY++;                    
                    }  
               }
           }
           //Al salir de aquí tendremos los centros de montañas generados y valores de montañas repartidos aleatoriamente
           //alrededor de estos núcleos. Vamos a hacer las montañas algo más densas eliminando huecos de 0 rodeados por patrones
           // de unos.
           for (int x = 0; x < map.length; x++)
           {
               for (int y = 0; y < map[0].length; y++)
               {
                   //Patrones para eliminar huecos de montañas.
                   if ((x-1 > 0)&&(x+1 < map.length))
                   {
                       if ((map[x-1][y] == this.MOUNTAIN)&&(map[x+1][y] == this.MOUNTAIN))
                       {
                           //forzamos al de enmedio  ser montaña.
                           map[x][y] = this.MOUNTAIN;
                       }
                   }
                   //Lo mismo para el eje Y
                   if ((y-1 > 0)&&(y+1 < map[0].length))
                   {
                       if ((map[x][y-1] == this.MOUNTAIN)&&(map[x][y+1] == this.MOUNTAIN))
                       {
                           //forzamos al de enmedio  ser montaña.
                           map[x][y] = this.MOUNTAIN;
                       }
                   }
               }
           }

           //Para mejorarlo,lo rotamos 90º, que es como menos ventaja hay para los jugadores
           //y hay más aleatoriedad. (Se debe a la forma de generar mapa, que hace que siempre haya más montañas hacia
           //el lado de un jugador.Así que intentaremos ponerlas en los laterales del mapa.
           //SOLO SE ACTIVA SI EL MAPA ES CUADRADO.
           if (map.length == map[0].length)
           {
               int[][] inverter = new int[map.length][map[0].length];
               for (int x = 0; x < map.length; x++)
               {
                   for (int y = 0; y < map[0].length; y++)
                   {
                       inverter[x][y] = map[map[0].length-1-y][map.length-1-x];
                   }
               }
               //reasignamos.
               map = inverter;
           }
           //Finalmente, para darle algo más de aletoriedad y evitar ventanas por parte de algún jugador de que los mapas
           //se generen de una forma que suela dar ventaja a la posición de uno u otro, vamos a echar a suerte si invertimos
           //el mapa lógico o lo dejamos como está.
           if (random.nextInt()%2 == 0)
           {
               //Si el número obtenido es par, lo invertimos.
               int[][] inverter = new int[map.length][map[0].length];
               for (int x = 0; x < map.length; x++)
               {
                   for (int y = 0; y < map[0].length; y++)
                   {
                       inverter[x][y] = map[map.length-1-x][map[0].length-1-y];
                   }
               }
               //reasignamos.
               map = inverter;
           }
       }
       
       return map;
       //Hemos terminado de generar las montañas aleatorias.
    }
    private void generateRivers(int[][] map){
        
        Vector river = new Vector();
        Vector bridgesInMap = new Vector();
        Vector watersInMap = new Vector();
        //Genera algún/os rios.
        //SOLO ADMITIMOS RIOS QUE NO VAYAN DE ZONA DE JUGADORES A ZONA DE JUGADORES
        //Primero vemos si hay que hacerlos.
        int probRandomRivers = random.nextInt(this.TOTAL_PERCENT_INT);
        int probCreateRivers = (int)(this.CREATE_RIVERS*this.TOTAL_PERCENT_INT);
        if (probRandomRivers <= probCreateRivers)
        {
            //Calculamos el punto de entrada del rio.
            int x = 0;
            while ((x < this.MIN_SIZE_HEIGHT_PLAYER_START_POSITION)||(x >= map.length-1-this.MIN_SIZE_HEIGHT_PLAYER_START_POSITION))
            {                    
                x = random.nextInt(map.length-1);
                System.out.println("REPITO X: "+Integer.toString(x));
            }            
            //Obtenemos un rio.
            System.out.print("RIVER ");
            //si los datos son correctos generamos el rio.
            for (int i = 0; i < map[0].length; i++)
            {
                //Añadimos una celda al rio.              
                river.add(new Point(x,i));  
                System.out.print(Integer.toString(x)+","+Integer.toString(i)+"-");
            }
            //Obtenemos todos los valores y los vamos conviertiendo en rio                
            while (!river.isEmpty())
            {
                Point p = (Point)river.remove(0);
                //Ojo, hay que tener en cuenta que si es un camino prefijado, no podemos quitarlo, se añade un puente.
                boolean find = false;
                for (int v = 0; (v < this.paths.length)&&(!find); v++)                
                {
                    Vector vector = this.paths[v];
                    if (vector.contains(p))
                    {
                        find = true;
                    }
                }
                if (find)
                {
                    //Es puente. Pero ojo, no añadimos dos puentes seguidos.en ese camino, ponemos agua y quitamos el camino
                    //prefijado, pues tenemos un puende adyacente por le que pasar.
                    if ((!bridgesInMap.contains(new Point(p.x,p.y-1)))&&(!bridgesInMap.contains(new Point(p.x,p.y+1))))
                    {
                        bridgesInMap.add(new Point(p.x,p.y));
                    }
                    else
                    {
                        watersInMap.add(new Point(p.x,p.y));
                    }
                }
                else
                {
                    //es celda rio.
                    watersInMap.add(new Point(p.x,p.y));
                }
            } 
            //Pasamos los datos al mapa, pues son válidos.               
            for (int v = 0; v < bridgesInMap.size(); v++)
            {
                Point bridge = (Point)bridgesInMap.get(v);
                map[bridge.x][bridge.y] = this.BRIDGE;
                //Si por arriba o por abajo tiene montaña, las convertimos en ground. Así nunca una montaña
                //bloquea un puente.
                if (map[bridge.x+1][bridge.y] == this.MOUNTAIN)
                {
                    map[bridge.x+1][bridge.y] = this.GROUND;
                }
                if (map[bridge.x-1][bridge.y] == this.MOUNTAIN)
                {
                    map[bridge.x-1][bridge.y] = this.GROUND;
                }    
            }
            for (int v = 0; v < watersInMap.size(); v++)
            {
                Point water = (Point)watersInMap.get(v);
                map[water.x][water.y] = this.WATER;                    
            }
        }                 
    }
    private void checkAvaiablePaths(int[][] map){
       
        //Chequea y crea rutas de movimiento para evitar que el mapa esté bloqueado por montañas aleatorias.
        //Lo primero es ver que las casillas donde salen los jugadores deben estar libres.
        for (int x = 0; x < this.MIN_SIZE_HEIGHT_PLAYER_START_POSITION; x++)
        {
            for (int y = 0; y < map[0].length; y++)
            {
                //Ojo con que el mapa sea más chico que estos valores.Hay que evitar producir un erro fuera de índice.
                if (x < map.length)
                {
                    //Forzamos a que sea ruta.
                    map[x][y] = this.PATH;
                }
            }
        }   
        //Hacemos lo mismo para el otro extremo (donde sale el segundo jugandor.
        for (int x = map.length-this.MIN_SIZE_HEIGHT_PLAYER_START_POSITION; x < map.length; x++)
        {
            for (int y = 0; y < map[0].length; y++)
            {
                //Ojo con que el mapa sea más chico que estos valores.Hay que evitar producir un erro fuera de índice.
                if (x < map.length)
                {
                    //Forzamos a que sea ground.
                    map[x][y] = this.PATH;
                }
            }
        }    
        //Ahora ya tenemos libres las posiciones en la que empiezan los dos jugadores.
        //Lo siguiente es ver que existen caminos que permitan llegar de una de las posicione de salida de un jugador a
        //Las posiciones de salidas del otro, para así asegurarnos de que pueden llegar el uno al otro para luchar. 
        int maxPaths = 0;
        //Como nunca debe ser 0, ya que si no el mapa podría estar bloqueado para los jugadores, hacemos un while.
        while (maxPaths == 0)
        {
           maxPaths = random.nextInt(this.MAX_PATHS_ARRAYS);
        }
        //Ahora que tenemos un número de rutas a generar, generamos sus valores.
        this.paths = new Vector[maxPaths];
        for (int p = 0; p < this.paths.length; p++)
        {
            //Generamos y almacenamos una ruta.
            this.paths[p] = this.generatePath(map);          
        }
    }
    private Vector generatePath(int[][] map){
        
        //Genera una ruta entre una posición de salira y una de llegada.
        Vector v = new Vector(0);
        //Generamos una ruta libre que atraviese el mapa.
        //Las rutas van desde la posición del jugador A a la posición del jugador B.        
        int startY = random.nextInt(map[0].length);
        int endY = random.nextInt(map[0].length);   
        System.out.println("START Y END "+Integer.toString(startY)+","+Integer.toString(endY));
        //Generamos la ruta.
        //Primero guardamos los desplazamientos en Y que hay que hacer durante la ruta.
        int desplY = endY - startY;
        //Vemos en cuantas líneas rectas hay que partir el camino.
        int sections = 1;
        //Es importante saber el signo, ya que indica la diagonal del camino y evita el error fuera de índice.
        int signum = 1;
        if (desplY != 0)
        {
            //Si es negativo, pasamos a positivo.
            if (desplY < 0)
            {
                sections = -desplY;
                signum = -1;
            }
            else
            {
                sections = desplY;
            }
        }
        //Vemos cuantas puntos rectas tiene una sección.
        int pointsInSection = map.length/sections;
        //OJO!! si map.length/sections no es una división con resto 0, tendremos un camino incompleto a no tener
        //puntos entre seccion, si no que se pierden con el resto.
        //HAY QUE GUARDAR EL RESTO PARA AÑADIRLO AL FINAL DEL CAMINO COMPLENTANDO ESTE.
        int endWay = map.length % sections;        
        //Generamos los puntos del camino.
        System.out.println("Secciones , PISEC y resto "+Integer.toString(sections)+","+Integer.toString(pointsInSection)+","+Integer.toString(endWay));
        //Hay que cuidar si los valores son positivos o negativos (siempre ambos tiene el mismo signo).
        if (sections < 0)
        {
            //los ponemos positivos.
            sections = - sections;
            pointsInSection = - pointsInSection;
        }
        //Vamos a ir guardando el último punto añadido y a partir de él añadiremos el resto del camino que falta.
        Point lastPoint = null;
        //Iteramos.                
        for (int sec = 0; sec < sections; sec++)
        {
            for (int pisec = 0; pisec < pointsInSection; pisec++)
            {
                //Guardamos los valores
                Point point = new Point((pointsInSection*sec)+pisec,startY+sec*signum);
                v.add(point);
                //guardamos.
                lastPoint = point;
                System.out.println("Añadido punto "+Integer.toString(point.x)+","+Integer.toString(point.y));
                //Forzamos a que sean pasables.
                map[point.x][point.y] = this.PATH;
            }
        }
        //Añadimos la parte del camino que falta, aunque ya no vaya en la misma dirección que la original.
        for (int r = 0; r < endWay; r++)
        {
             //Guardamos los valores
             Point point = new Point(lastPoint.x+1,lastPoint.y);
             v.add(point);
             //guardamos.
             lastPoint = point;
             System.out.println("Añadido punto "+Integer.toString(point.x)+","+Integer.toString(point.y));
             //Forzamos a que sean pasables.
             map[point.x][point.y] = this.PATH;
        }        
        //Finalmente, vamos solapar los cambios de dirección.
        //Así guardaremos estructura B en vez de la A, que es la que nos interesa (solapadas).
        //A:        22           B:     22
        //            22                 222
        Vector vUnion = new Vector();
        for (int i = 0; i < v.size(); i++)
        {
            //vemos si existe el punto en índice del vector i+1.
            if (i+1 < v.size())
            {
                //Sacamos los dos seguidos.
                Point a = (Point)v.get(i);
                Point b = (Point)v.get(i+1);
                //Vemos si forman escalón, es decir, tienen distintos valores Y.
                if (a.y != b.y)
                {
                    //Añadimos un punto que los una con la estructura que queremos.
                    Point p = new Point(a.x,b.y);
                    //Lo metemos en el vector.
                    vUnion.add(p);
                    //Lo añadimos en el mapa.
                    map[p.x][p.y] = this.PATH;
                }
            }           
        }
        //unimos el vector (No se puede hacer dentro del bucle anterior!)
        for (int i = 0; i < vUnion.size(); i++)
        {
            Point p = (Point)vUnion.get(i);
            v.add(p);
        }                
        //devolvemos.
        return v;
    }
    private int[][] generateViableMeshMap(int[][] logicMap){
        
        //Generamos variable.
        int[][] meshMap = new int[logicMap.length][logicMap[0].length];        
        //OJO!!!! LA CREACIÓN DE MESH NO VA A ACORRESPONDER AL 100& CON EL MAPA LÓGICO. PARA ADAPTARLO, SE OPTA POR NO PODER
        //METER LOS MESH QUE NO SE PUEDAN, O EN SU DEFECTO, ELIMINAR VALORES DEL MAPA LÓGICO PARA ADAPTAR EL MESH. NUNCA SE
        //AÑADIRÁN VALORES AL MAPA LÓGICO PARA ADAPTAR EL MESH YA QUE INVALIDAMOS EL CHEQUEO DE RUTAS. ADEMÁS, SI SE HACE EL
        //CHEQUEO DE RUTAS DESPUÉS DE GENERAR EL MESHMAP, AL HACER MODIFICACIONES NOS CARGAMOS OTRA VEZ EL MESHMAP.
        //PODEMOS ELIMINAR AQUÍ VALORES 1 DEL MAPA LÓGICO PARA ENCAJAR EL MESH, PERO NUNCA AÑADIRLOS.
        
        //Primero traspasamos el mapaLógico y luego lo transformaremos.
        for (int x = 0; x < logicMap.length; x++)
        {
            for (int y = 0; y < logicMap[0].length; y++)
            {
                if (logicMap[x][y] == this.MOUNTAIN)
                {
                    meshMap[x][y] = this.MOUNTAIN;
                }
                else if (logicMap[x][y] == this.WATER)
                {
                    meshMap[x][y] = this.WATER;
                }
                else if (logicMap[x][y] == this.BRIDGE)
                {
                    meshMap[x][y] = this.BRIDGE;
                }
                else
                {
                    meshMap[x][y] = this.GROUND;
                }        
            }
        }        
        //Devolvemos.
        return meshMap;
    }   
    private int[][] getAdaptedLogicMap(int[][] meshMap){
        
        //Un mapa lógico solo tiene 2 valores (por ahora):
        // valor 0: Indica que se puede pasar por la celda.
        // valro 1: Indica que la celda está bloqueada y no se puede mover a través de ella.        
        int[][] logicMap = new int[meshMap.length][meshMap[0].length];
        //Generamos un mapa lógico aleatorio. Usamos el tamaño del mapa para ver la cantidad de montañas a poner.
        for (int i = 0; i < meshMap.length; i++)
        {
            for (int j = 0; j < meshMap[0].length; j++)
            {
                if ((meshMap[i][j] == this.MOUNTAIN)||(meshMap[i][j] == this.WATER))
                {
                    logicMap[i][j] = this.LOGIC_BLOQUED;
                }
            }
        }
        //Devolvemos
        return logicMap;
    }
    private Vector generateSurfaceTextureLayer(){
        
        //Generamos vector.
        Vector surfaces = new Vector();
        //A IMPLEMENTAR
        
        
        //Devolvemos.
        return surfaces;
    } 
    private Vector generateObjects3DLayer(int[][] logicMap){
        
        //Generamos vector
        Vector objects3D = new Vector();
        //Usaremos el argumento logicMap para indicar en el mapa lógico qué objetos3D de adornos del mapa
        //pueden bloquear la celda y cuales no.(Pe: Un puende puede hacer que una celda donde hay un rio sea pasable, en
        //cambio, una casa puede hacer que una celda libre ya no sea pasable. Todo esto debe quedar registrado en el mapa
        //lógico).
        
        //A IMPLEMENTAR
        
        //Devolvemos
        return objects3D;
    }
}
