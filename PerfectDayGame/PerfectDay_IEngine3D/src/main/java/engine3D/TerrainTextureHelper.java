package engine3D;

import com.threed.jpct.*;

public class TerrainTextureHelper {

	//Estructura de división de una celda en 4 sectores para estudiar sus adyacentes.
	//Cada sector indica los adyacentes que hay que consultar.	
	static final int SECTOR_NONE = -1;	
	static final int SECTOR_TYPE_0 = 0;
	static final int SECTOR_TYPE_1 = 1;
	static final int SECTOR_TYPE_2 = 2;
	static final int SECTOR_TYPE_3 = 3;
	static final int SECTOR_TYPE_4 = 4;
	static final int SECTOR_TYPE_5 = 5;
	static final int SECTOR_TYPE_6 = 6;
	static final int SECTOR_TYPE_7 = 7;
	//Estructura de una celda de cada tipo y los 4 sectores que la forman.
	//Indican {Esquina sup Izq, sup Der, inf izq e inf der.}
	static final int[] CELL_TYPE_0 = {SECTOR_TYPE_0,SECTOR_TYPE_4,SECTOR_NONE,SECTOR_TYPE_1};
	static final int[] CELL_TYPE_1 = {SECTOR_TYPE_7,SECTOR_TYPE_4,SECTOR_TYPE_3,SECTOR_TYPE_1};
	static final int[] CELL_TYPE_2 = {SECTOR_TYPE_7,SECTOR_TYPE_0,SECTOR_TYPE_3,SECTOR_NONE};
	static final int[] CELL_TYPE_3 = {SECTOR_TYPE_0,SECTOR_TYPE_4,SECTOR_TYPE_2,SECTOR_TYPE_5};
	static final int[] CELL_TYPE_4 = {SECTOR_TYPE_7,SECTOR_TYPE_4,SECTOR_TYPE_6,SECTOR_TYPE_5};
	static final int[] CELL_TYPE_5 = {SECTOR_TYPE_7,SECTOR_TYPE_0,SECTOR_TYPE_6,SECTOR_TYPE_2};
	static final int[] CELL_TYPE_6 = {SECTOR_NONE,SECTOR_TYPE_1,SECTOR_TYPE_2,SECTOR_TYPE_5};
	static final int[] CELL_TYPE_7 = {SECTOR_TYPE_3,SECTOR_TYPE_1,SECTOR_TYPE_6,SECTOR_TYPE_5};
	static final int[] CELL_TYPE_8 = {SECTOR_TYPE_3,SECTOR_NONE,SECTOR_TYPE_6,SECTOR_TYPE_2};
	
	static final int MAX_TILES_IN_CELL = 4;
	//----------------------------------------
    //-    VARIABLES GLOBALES DE LA CLASE    -
    //----------------------------------------
	private TerrainObject3D terrainObj3D;
	private int textureBase;
	private int textureSecondary;
	
	//-----------------------
	//-    CONSTRUCTORES    -
	//-----------------------
	public TerrainTextureHelper(TerrainObject3D terrainObject3D){
		
		//Obtenemos la estructura del mapa.
		terrainObj3D = terrainObject3D;			
		textureBase = terrainObject3D.TEXTURE_BASE;
		textureSecondary = terrainObject3D.TEXTURE_SECONDARY;
	}
		
	//-------------------------------
	//-    METODOS DE INSTANCIA     -
	//-------------------------------	
	public String[] generateTextureTranslations(int[][] map, int x, int y){
		
		String[] res = new String[MAX_TILES_IN_CELL];
		//A partir del terreno generamos la translación suave de texturas.
		//Esta llamada se hará cuando encontremos una textura secundaria en X,Y
		//Primero vemos que tipo de celda es.
		int[] sectors = getCellType(map, x, y);
		//Preparamos una variable para guardar el tipo de textura de dicho sector.
		int type = 0;
		//Estudiamos.		
		for (int t = 0; t < sectors.length; t++)
		{
			//Obtenemos el tipo de translación de textura
			type = getTileTextureSector(map, x, y, sectors[t], textureBase, textureSecondary);
			//Guardamos como string.
			res[t] = Integer.toString(type);			
		}
		//Ahora ya tenemos los terminales del nombre de cada textura
		//Devolvemos.
		return res;	
	}
	private int[] getCellType(int[][] map, int x, int y){
		
		int[] res = null;
		//Obtenemos el tipo de celda que hay en dicha posición.
		if (x == 0)
		{
			if (y == 0)
			{
				//Estamos en la esquina inferior izquierda.
				res = CELL_TYPE_0;
			}
			else if (y == map[0].length-1)
			{
				//Estamos en la esquina superior izquierda.
				res = CELL_TYPE_6;
			}
			else
			{
				//Estamos en otra parte de la primera columna (tope de la izquierda).
				res = CELL_TYPE_3;
			}
		}
		else  if (x == map.length-1)
		{
			if (y == 0)
			{
				//Estamos en la esquina inferior derecha.
				res = CELL_TYPE_2;
			}
			else if (y == map[0].length-1)
			{
				//Estamos en la esquina superior derecha.
				res = CELL_TYPE_8;
			}
			else
			{
				//Estamos en alguna celda pegada al tope de la derecha.
				res = CELL_TYPE_5;
			}			
		}
		else
		{
			//No estamos ni pegados a la derecha ni a la izquierda.
			if (y == 0)
			{
				//Estamos en la fila inferior derecha.
				res = CELL_TYPE_1;
			}
			else if (y == map[0].length-1)
			{
				//Estamos en la fila superior derecha.
				res = CELL_TYPE_7;
			}
			else
			{
				//Estamos en alguna celda central.
				res = CELL_TYPE_4;
			}		
		}
		return res;
	}
	private int getTileTextureSector(int[][] map, int x, int y, int sector, int texBase, int texSec){
		
		//Por defecto ponemos la secundaria, que es la que debe adaptarse.
		int res = SECTOR_NONE;
		//Estudiamos los adyacentes.
		switch(sector)
		{
			case (SECTOR_TYPE_0): 	if (map[x][y+1] == texBase) {res = 2; };break;
			case (SECTOR_TYPE_1): 	if (map[x+1][y] == texBase) {res = 3; };break;
			case (SECTOR_TYPE_2): 	if (map[x][y-1] == texBase) {res = 0; };break;
			case (SECTOR_TYPE_3): 	if (map[x-1][y] == texBase) {res = 1; };break;			
			case (SECTOR_TYPE_4):	{if (map[x][y+1] == map[x+1][y])
								 	{
										//Caso A o D
										if (map[x][y+1] == texBase)
										{
											//Caso A
											res = 11;
										}
										else
										{
											//caso D, hay que mirar la diagonal.
											if (map[x+1][y+1] == texBase)
											{
												res = 5;
											}
										}
								 	}
									else
									{
										//Caso B o C
										if (map[x][y+1] == texBase)
										{
											res = 2;
										}
										else
										{
											res = 3;
										}
									}
									};break;			
			case (SECTOR_TYPE_5): 	{if (map[x+1][y] == map[x][y-1])
									{
										//Caso A o D
										if (map[x+1][y] == texBase)
										{
											//Caso A
											res = 8;
										}
										else
										{
											//caso D, hay que mirar la diagonal.
											if (map[x+1][y-1] == texBase)
											{
												res = 6;
											}
										}
								 	}
									else
									{
										//Caso B o C
										if (map[x][y-1] == texBase)
										{
											res = 0;
										}
										else
										{
											res = 3;
										}
									}
									};break;
			case (SECTOR_TYPE_6):	{if (map[x-1][y] == map[x][y-1])
									{
										//Caso A o D
										if (map[x-1][y] == texBase)
										{
											//Caso A
											res = 9;
										}
										else
										{
											//caso D, hay que mirar la diagonal.
											if (map[x-1][y-1] == texBase)
											{
												res = 7;
											}
										}
								 	}
									else
									{
										//Caso B o C
										if (map[x][y-1] == texBase)
										{
											res = 0;
										}
										else
										{
											res = 1;
										}
									}
									};break;
			case (SECTOR_TYPE_7):	{if (map[x-1][y] == map[x][y+1])
									{
										//Caso A o D
										if (map[x-1][y] == texBase)
										{
											//Caso A
											res = 10;
										}
										else
										{
											//caso D, hay que mirar la diagonal.
											if (map[x-1][y+1] == texBase)
											{
												res = 4;
											}
										}
								 	}
									else
									{
										//Caso B o C
										if (map[x][y+1] == texBase)
										{
											res = 2;
										}
										else
										{
											res = 1;
										}
									}
									};break;			
		}		
		//Devolvemos.
		return res;
	}
}
