package engine3D;

import com.threed.jpct.*;

import java.util.Random;
import java.util.Vector;

public class TerrainObject3D{
		
	//----------------------------------------
    //-    CONSTANTES GLOBALES DE LA CLASE    -
    //----------------------------------------
	static Random random;
	static final String objetoEmisor = "TerrainObject";	
	static final int TEXTURE_BASE = 0;
	static final int TEXTURE_SECONDARY = 1;	
	static final int TILE_0_IN_CELL = 0;	//Los 4 tiles que forman una celda de terreno.
	static final int TILE_1_IN_CELL = 1;
	static final int TILE_2_IN_CELL = 2;
	static final int TILE_3_IN_CELL = 3;
	static final int MAX_TILES_IN_CELL = 4;
	//----------------------------------------
    //-    VARIABLES GLOBALES DE LA CLASE    -
    //----------------------------------------
	private Engine3D engine3D;		//Enlace al engine3D para obtener acceso a los manejadores.
	private Object3D terrain;		//Objeto del terreno a incluir en el mundo virtual
	private EngineLog eLog;			//Archivo de informaci�n de salida.
	private int maxPolyInObject3D;	//M�ximo n�mero de pol�gonos permitidos en el terreno.
	private Vector listCellInfo;	//Lista de informaci�n de cada celda del terreno (ID primer Poly,y pos x,y).
	private int maxX;				//Guardamos los valores m�ximos de X e Y para poder estimar el tama�o del terreno.
	private int maxY;
	private int[][] loadedMap;		//Enlace a la estructura del mapa actual representado.
	private TerrainTextureHelper texHelper;
	private Texture texGrid;		//Guardamos para poder acceder de forma r�pida a activarla y desactivarla.
	
	//-----------------------
    //-    CONSTRUCTORES    -
    //-----------------------
	public TerrainObject3D(int maxPol, Engine3D e3D, EngineLog engineLog){
		
		//Guardamos las referencias.
		maxPolyInObject3D = maxPol;
		engine3D = e3D;
		eLog = engineLog;
		//Creamos el texture helper.
		texHelper = new TerrainTextureHelper(this);
		//Ojo! el terreno no puede tener m�s de maxPol, as� que poner
		//un n�mero mayor al que estimemos que vamos a necesitar.
		terrain = new Object3D(maxPolyInObject3D);		
		//A un objeto3D con 0 pol�gonos no se le pueden a�adir v�rtices!! da error
		//de vertex out of bounds.
		//Iniciamos el n�mero de celdas en el terreno con espacio para 1 celda e incrementaci�n de 1 en 1. 
		listCellInfo = new Vector(1,1);
		//Creamos la instancia de random para obtener valores aleatorios.
		random = new Random();		
	}
	//---------------------------------------
    //-    METODOS DE INSTANCIA PRIVADOS    -
    //---------------------------------------
	public boolean generateNewTerrainMap(int[][] map, String[] terrainModels, String[] terrainTextures, String[] objectsInMap){
		
		//Valor que nos indica si nos hemos pasado en los pol�gonos.
		//OJO!! La �ltima textura de terrainTextures es el icono de celda.		
		boolean notExceeds = true;
		//El mapa m�nimo es de 2x2;
		if ((map.length > 1)&&(map[0].length > 1))
		{
			//Iniciamos		
			if (map != null)
			{
				//Guardamos los valores.
		    	maxX = map.length - 1;
		    	maxY = map[0].length - 1;
		    	loadedMap = map;
		    	//Creamos el mapa.
		        for (int x = 0; x < map.length;  x++)
		    	{
		    		for (int y = 0; y < map[0].length; y++)
		    		{
		    			if (map[x][y] == TerrainObject3D.TEXTURE_BASE)
		    			{
		    				insertNewTerrainCell(terrainModels[0], terrainTextures, TerrainObject3D.TEXTURE_BASE, x, y);
		    			}
		    			else if (map[x][y] == TerrainObject3D.TEXTURE_SECONDARY)
		    			{
		    				insertNewTerrainCell(terrainModels[1], terrainTextures, TerrainObject3D.TEXTURE_SECONDARY, x, y);
		    			}				
		    		}
		    	}
			}
		}
		//Devolvemos el resultado
		return notExceeds;
	}
	private boolean insertNewTerrainCell(String keyObject, String[] texture, int indexTexture, int posX, int posY){
    	
		//OJO!! La �ltima textura de terrainTextures es el icono de celda.
    	boolean notExceeds = true;
    	Object3D[] cellTerrain = new Object3D[MAX_TILES_IN_CELL];
    	//Vamos a usar el objeto TerrainTextureHelper para hacer transiciones entre texturas.
    	String[] tilesTails = texHelper.generateTextureTranslations(loadedMap, posX, posY);
    	//Esto nos devuelve las colas de los nombres de las texturas a a�adir al nombre de las
    	//texturas principales del terreno.
    	//Ahora vamos por los objetos.
    	//Cada celda son 4 TILES!
		//Sacamos las celdas indicadas.
    	for (int tile = 0; (tile < MAX_TILES_IN_CELL)&&(notExceeds); tile++)
    	{
    		Object3D copyMesh = engine3D.getObjectManager().getObject3DCopy(keyObject);
        	cellTerrain[tile] = copyMesh.cloneObject();
        	//Si no clonamos los mesh, sioguen compartiendo datos ambos objetos.
        	//No he encontrado mucha explicaci�n �?
        	cellTerrain[tile].setMesh(copyMesh.getMesh().cloneMesh(true));
        	//Preparamos el nombre de la textura.
        	String textureName = "";
        	//Si la textura es secundaria, se hace la transicion
        	if (indexTexture == TEXTURE_SECONDARY)
        	{
        		if (tilesTails[tile].equals("-1"))
        		{
        			//No hacemos transici�n, ponemos la secundaria, que es la que pasan.
        			textureName = texture[indexTexture];
        		}
        		else
        		{
            		//Hacemos la modificaci�n para transici�n de texturas.
                	//Convertimos el nombre de la textura seg�n el tile.
                	String texExt = texture[indexTexture].substring(texture[indexTexture].length()-4);
                	String texName = texture[indexTexture].substring(0,texture[indexTexture].length()-4);
                	//Generamos el nombre de la textura.
                	textureName = texName+tilesTails[tile]+texExt;
                	//Ya tengo la textura del tipo grass.jpg como   grass05.jpg      
        		}
        	}
        	else
        	{
        		//Ponemos la textura primaria, que es la que pasan.
        		textureName = texture[indexTexture];
        	}
        	//Ahora a�adimos la textura celda como una nueva capa.        	
        	int texID = engine3D.getTextureMan().getTextureID(textureName.toUpperCase());
        	TextureInfo texInfo = new TextureInfo(texID);
        	//Buscamos el texID de la textura de celda, para hacer grid.
        	//Este material lo marca el �ltimo nombre de textura del String[] texture
        	String nameGridTex = texture[texture.length-1];
        	int textureGridID = engine3D.getTextureMan().getTextureID(nameGridTex.toUpperCase()); 
        	texInfo.add(textureGridID, TextureInfo.MODE_ADD);
        	//Por defecto est� desactivada.
        	texGrid = engine3D.getTextureMan().getTextureByID(textureGridID);
        	if (texGrid != null)
        	{
        		//texGrid.setEnabled(true);
        	}
        	//Hacemos la llamada para a�adir la textura.
        	cellTerrain[tile].setTexture(texInfo);
    	}
        //Si no es null, la unimos
        if (cellTerrain != null)
        {        		
        	notExceeds = addNewTerrainCell(cellTerrain, posX, posY);     		
        }
        else
        {
        	eLog.writeEngineLogWithEmisor(objetoEmisor, "No se puede insertar en el terreno la celda con KEY "+keyObject+": su copia es NULL.");
        }
    	if (notExceeds)
    	{
    		eLog.writeEngineLogWithEmisor(objetoEmisor, "Insertada en el ObjectTerrain la celda con KEY "+keyObject+".");
    	}
    	return notExceeds;
	}
	private boolean addNewTerrainCell(Object3D[] objs, int posX, int posY){		
		
		boolean notExceeds = true;
		//Antes de unir nada hay que asegurarse que el nuevo objeto
		//no sobrepasa el tope de pol�gonos de este terreno, ya que si no
		//obtendr�amos un error. En caso de pasarse, devolvemos true.
		//Lo hacemos para cada objeto
		
		//Preparamos una variable para saber si es el primer pol�gono de una celda y por tanto almacenarlo.
		boolean isFirstPolygonInCell = true;
		//Iteramos
		for (int numObj = 0; (numObj < MAX_TILES_IN_CELL)&&(notExceeds); numObj++)
		{
			//Sacamos un objeto.
			Object3D obj = objs[numObj];
			//Lo unimos.
			int polysInCell = obj.getPolygonManager().getMaxPolygonID();
			int totalPoly = polysInCell + terrain.getPolygonManager().getMaxPolygonID();
			if (maxPolyInObject3D > totalPoly)
			{
				//Obtenemos la instancia PolygonManager del objeto pasado por argumento.
				PolygonManager polMan = obj.getPolygonManager();
				//Construimos una lista de los v�rtices que tiene. Para ello vemos cuantos pol�gonos tiene y
				//sacamos los 3 v�rtices que forman cada pol�gono.
				int maxPolyID = polMan.getMaxPolygonID();

				//Obtenemos todos los v�rtices de todos los pol�gonos.
				for (int poly = 0; poly < maxPolyID; poly++)
				{
					//Cada pol�gono tiene 3 v�rtices.
					SimpleVector vect1 = polMan.getTransformedVertex( poly, 0);
					SimpleVector vect2 = polMan.getTransformedVertex( poly, 1);
					SimpleVector vect3 = polMan.getTransformedVertex( poly, 2);
					//Cada v�rtice de cada pol�gono tiene asociado un par de valores U,V para la textura.
					SimpleVector uv1 = polMan.getTextureUV(poly , 0);
					SimpleVector uv2 = polMan.getTextureUV(poly , 1);
					SimpleVector uv3 = polMan.getTextureUV(poly , 2);
					//Cada pol�gono tiene una textura asociada, la optenemos.
					int texID = polMan.getPolygonTexture(poly);
					//Vemos que no haya un error en la textura.
					if (texID == -1)
					{
						texID = 0;
					}
					//Preparamos los offsets de los tiles.
	        		float offsetX = 0;
	        		float offsetY = 0;
	        		//Creamos la nueva celda de terreno con el objeto indicado y la posici�n indicada.
	        		//Como 1 una celda de terreno son 4 tiles, hay que hacer que encajen en cuadrado entre los valores X,Y.	        		
	        		switch (numObj)
	        		{
	        			case (TILE_0_IN_CELL): {offsetX = -0.25f; offsetY =  0.25f;} break;
	        			case (TILE_1_IN_CELL): {offsetX =  0.25f; offsetY =  0.25f;} break;
	        			case (TILE_2_IN_CELL): {offsetX = -0.25f; offsetY = -0.25f;} break;
	        			case (TILE_3_IN_CELL): {offsetX =  0.25f; offsetY =  -0.25f;} break;
	        		}
	        		//sumamos los offset a la posici�n de la celda.
					//posicionamos cada pol�gono en la zona indicada por argumento, ya que al cargarlos, estar�n en la posici�n 0,0.
					vect1.set(vect1.x+posX+offsetX,vect1.y, vect1.z+posY+offsetY);
					vect2.set(vect2.x+posX+offsetX,vect2.y, vect2.z+posY+offsetY);
					vect3.set(vect3.x+posX+offsetX,vect3.y, vect3.z+posY+offsetY);												
					//Lo unimos al objeto terrain (nos devuelve el ID del tri�ngulo).
					int idPol = terrain.addTriangle(vect1, uv1.x, uv1.y, vect2, uv2.x, uv2.y, vect3, uv3.x, uv3.y, texID);
					//Indicamos que se ha a�adido una celda m�s. Gardamos su posici�n X,Y y el ID del primer pol�gono unido
					//unido al objeto TerrainObject que la foma. M�s adelante usaremos esta info para saber a partir de un
					///ID de pol�gono en qu� celda est� posicionado el cursor.
					if (isFirstPolygonInCell)
					{
						//Guardo informaci�n sobre esa celda en una clase privada CellInfo.
						CellInfo cellInfo = new CellInfo(idPol,posX,posY);
						//La guardamos en el vector en la �ltima posici�n (para que est�n ordenadas).
						listCellInfo.add(cellInfo);
						//Indicamos que ya se ha almacenado la id y pos de esta celda
						isFirstPolygonInCell = false;										
					}
				}
			}
			else
			{
				//No unimos nada y devolvemos true.
				notExceeds = false;
			}
		}	
		return notExceeds;
	}
	//-------------------------------
    //-    METODOS DE INSTANCIA     -
    //-------------------------------	
	public int[] getCellInfo(int idPoly){
		
		//Se hace una consulta para ver a qu� celda (qu� posiciones) pertenece
		//el citado pol�gono. Se devuelve un array de dos elementos (la posici�n de la celda).
		int res[] = new int[2];
		//Buscamos en qu� celda est� ese id de pol�gono.
		boolean quit = false;		
		for (int i = 0; (i < listCellInfo.size())&&(!quit); i++)
		{
			//Saco una celda de informaci�n.
			CellInfo cellInfo = (CellInfo)listCellInfo.get(i);
			//Vemos si el ID pol�gono es mayor que el guardado de esta celda (que es el menor que contiene la celda).
			//Tambi�n entramos por aqu� si es la �ltima celda e IDPoly solo puede est�r aqu�.
			if ((cellInfo.idFirstPolyCell == idPoly)||(i == listCellInfo.size() - 1 ))
			{
				//Si es el mismo, ya tenemos la celda identificada.
				res[0] = cellInfo.posXCell;
				res[1] = cellInfo.posYCell;
				//Indicamos que se ha encontrado.
				quit = true;
			}
			else if (idPoly > cellInfo.idFirstPolyCell)
			{
				//En este caso hay que ver si es menor que la siguiente celda, ya que entonces
				//id poly pertenecer�a a esta celda.
				CellInfo nextCellInfo = (CellInfo)listCellInfo.get(i+1);
				//�es menor que la primera de la siguiente?
				if (idPoly < nextCellInfo.idFirstPolyCell)
				{
					//En este caso, tambi�n estamos en esta celda, guardamos los datos.
					res[0] = cellInfo.posXCell;
					res[1] = cellInfo.posYCell;
					//Indicamos que se ha encontrado.
					quit = true;
				}
			}
		}
		if (!quit)
		{
			//Si hemos salido por que no se ha encontrado, lo indicamos.
			res[0] = -1;
			res[1] = -1;
		}
		//Devolvemos.
		return res;
	}
	public int numCellsInTerrain(){
		
		//Devolvemos el n�mero de celdas en el terreno.
		return listCellInfo.size();
	}
	public Object3D getTerrainObject3D(){
		
		//Devolvemos el objeto3D terrain.
		return terrain;		
	}
	public Object3D getAndBuildTerrainObject3D(){
		
		//Triangulizamos m�s si es posible.
		terrain.createTriangleStrips(3);
		//Comprimimos la malla
		terrain.getMesh().compress();
    	//El terreno es est�tico, no se mueve, lo indicamos para mejorar el rendimiento.
    	terrain.enableLazyTransformations();
    	//Los construimos y lo dejamos listo para unir al mundo virtual
		terrain.build();
		return terrain;		
	}
	public boolean compareIDTerrains(Object3D obj){
		
		return (terrain.getID() == obj.getID());
	}
	public int[][] getMapStructure(){
		
		return loadedMap;
	}
	public int getMaxSizeY(){
		
		return maxY;
	}
	public int getMaxSizeX(){
		
		return maxX;
	}
	public void setGridMode(boolean value){
		
		if (texGrid != null)
		{
			texGrid.setEnabled(value);
		}
	}
	public boolean getGridMode(){
		
		boolean res = false;
		if (texGrid != null)
		{
			res = texGrid.isEnabled();
		}
		return res;
	}
	//************************************************************************************************
	//----------------------------------------
	//-			CLASE PRIVADA CELLINFO       -
	//----------------------------------------
	private class CellInfo{
		
		//Variables.
		private int posXCell;
		private int posYCell;
		private int idFirstPolyCell;
		
		//constructor
		public CellInfo(int id, int x, int y){
			
			idFirstPolyCell = id;
			posXCell = x;
			posYCell = y;			
		}		
	}
	//************************************************************************************************
}
