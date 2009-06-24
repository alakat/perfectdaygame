package engine3D;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.StringTokenizer;

public class DiskLoader {

	//Objeto para cargar distintos archivos desde disco.
	static final int MAX_LAYERS_IN_MAP = 3;
	
	//----------------------------------------
    //-    VARIABLES GLOBALES DE LA CLASE    -
	//----------------------------------------
	private char sep;
	private HandlEngine handlEngine;
	
	//-----------------------
    //-    CONSTRUCTORES    -
    //-----------------------
	public DiskLoader(Engine3D engine3D){
				
		//Obtenemos las referencias.
		handlEngine = engine3D.getHandlEngine();
		//Obtenemos el separador del sistema.
		sep = File.separatorChar;
	}
	//-----------------------------
    //-   METODOS DE INSTANCIA    -
    //-----------------------------
	public void LoadTextureDirectory(String dir){
		
        //Cargamos todas las texturas del directorio.
        File dirTex = new File(dir);
        if (dirTex != null)
        {
        	//Obtenemos la lista de archivos.
        	String[] files = dirTex.list();
        	for (int i=0; i<files.length; i++) 
            {
               String name = files[i];
               handlEngine.StoreTextureFromDisk(dir+sep, name);
            } 
        }
	}
	public void LoadModelDirectory(String dir, float scale){
		
        //Cargamos todos los modelos 3ds o MD2 del directorio.
        File dirMod = new File(dir);
        if (dirMod != null)
        {
        	String[] files = dirMod.list();
            for (int i=0; i<files.length; i++) 
            {
               String nameModel = files[i];
               if (nameModel.toUpperCase().endsWith(".3DS"))
               {
            	   handlEngine.StoreObjectFromDisk(dir+sep+nameModel, nameModel, scale);        	   
               }
               else if (nameModel.toUpperCase().endsWith(".MD2"))
               {
            	   handlEngine.StoreAnimatedObjectFromDisk(dir+sep+nameModel, nameModel, scale);
               }
            } 
        }	
	}
	public void LoadModelArchive(String dir, String nameModel, float scale){
		
        //Cargamos todos los modelos 3ds o MD2 del directorio.
       if (nameModel.toUpperCase().endsWith(".3DS"))
       {
          	   handlEngine.StoreObjectFromDisk(dir+sep+nameModel, nameModel, scale); 	   
       }
       else if (nameModel.toUpperCase().endsWith(".MD2"))
       {
         	   handlEngine.StoreAnimatedObjectFromDisk(dir+sep+nameModel, nameModel, scale); 
       }
	}
	public int[][][] LoadMapArchive(String dir, String name){
		
		//Los mapas tienen 3 capas por defecto (terreno, texturas adorno y objetos de adorno.)
		int[][][] map = null;
		int[] mapSize;
		try
		{
			//Leemos el mapa.
			File archive = new File(dir+sep+name);
			if (archive.exists())
			{
				//Analizamos el mapa.
				 BufferedReader in = new BufferedReader(new FileReader(archive));
				 //obtenemos el tamaño del mapa.
				 mapSize = readMapDimensions(in);
				 //vemos si todo ha ido bien.
				 if ((mapSize[0] != 0)&&(mapSize[1] != 0))
				 {
					 //Creamos el objeto map con esas medidas
					 map = new int[mapSize[0]][mapSize[1]][MAX_LAYERS_IN_MAP];
					 //Vamos a leer las capas y a rellenar el mapa.
					 for (int layer = 0; layer < MAX_LAYERS_IN_MAP; layer++)
					 {
						//leemos una capa.
						map = readMapLayer(in, layer, map);
					 }					 
				 }
				 else
				 {
					 map = new int[0][0][0];
				 }
				 //Cerramos.
				 in.close();
			}
			else
			{
				map = new int[0][0][0];
				System.out.println("ERROR AL ABRIR EL ARCHIVO DE MAPA, El archivo no existe.");
			}				
	        
		}
		catch (FileNotFoundException e)
		{
			System.out.println("ERROR AL ABRIR EL ARCHIVO DE MAPA, No es encuentra. -> "+e.toString());
		}
		catch (IOException e)
		{
			System.out.println("ERROR AL ABRIR EL ARCHIVO DE MAPA. -> "+e.toString());
		}
		//devolvemos
        return map;
	}
	private int[] readMapDimensions(BufferedReader in){
		
		int[] dim = new int[2];
		StringTokenizer tokenizer;
		String token;
		//Buscamos las dimensiones del mapa de la primera capa o layer, todas tendrán el mismo tamaño.
		try
		{
			String line = in.readLine();
			if (line != null)
			{
					tokenizer = new StringTokenizer(line);
					//Obtenemos los elementos. (SE LEE PRIMERO LA Y Y LUEGO LA X, es el formato de mappy)
					//Cogemos hasta el primer corchete.
					token = tokenizer.nextToken("[");
					//No nos interesa, cogemos el valor hasta el siguiente corchete.
					token = tokenizer.nextToken("]");
					//Elimimanos el primer corchete de la cadena.
					token = token.substring(1);
					//Ya tenemos uno de los valores.
					dim[1] = Integer.parseInt(token);				
					//Repetimos la operación.
					token = tokenizer.nextToken("[");
					//No nos interesa, cogemos el valor hasta el siguiente corchete.
					token = tokenizer.nextToken("]");
					//Elimimanos el primer corchete de la cadena.
					token = token.substring(1);
					dim[0] = Integer.parseInt(token);
			}	
		}
		catch (FileNotFoundException e)
		{
			System.out.println("ERROR AL ABRIR EL ARCHIVO DE MAPA, No es encuentra. -> "+e.toString());
		}
		catch (IOException e)
		{
			System.out.println("ERROR AL ABRIR EL ARCHIVO DE MAPA. -> "+e.toString());
		}
		catch (Exception e)
		{
			System.out.println("Fallo leyendo archivo de mapa. Seguramente mal formato. -> "+e.toString());
		}
		//devolvemos
		return dim;
	}
	private int[][][] readMapLayer(BufferedReader in, int layer, int[][][] map){
		

		int[][][] res = map;
		StringTokenizer tokenizer;
		String token;
		String line;
		//Iniciamos el estudio.
		try
		{
			for( int y = 0; y < map[0].length; y++)
			{
				//Vemos el contenido.
				line = in.readLine();
				if (line != null)
				{
					tokenizer = new StringTokenizer(line, "]}, {[;");
					//Obtenemos los elementos.
					for( int x = 0; x < map.length; x++)
					{
						 token = tokenizer.nextToken();
						 res[x][y][layer] = Integer.parseInt(token);
					} 
				}
			}	
			//leemos 3 cadenas más para dejarlo en el siguiente layer por si esta no es la última capa.
			if (layer < MAX_LAYERS_IN_MAP-1)
			{
				line = in.readLine();	//Equivale al fin del array del layer.			
				line = in.readLine();	//Salta el espacio en blanco de la siguiente línea
				line = in.readLine();	//Salta el tamaño del layer, que es el mismo para todo el mapa.
			}
		}
		catch (FileNotFoundException e)
		{
			System.out.println("ERROR AL ABRIR EL ARCHIVO DE MAPA, No es encuentra. -> "+e.toString());
		}
		catch (IOException e)
		{
			System.out.println("ERROR AL ABRIR EL ARCHIVO DE MAPA. -> "+e.toString());
		}
		catch (Exception e)
		{
			System.out.println("Fallo leyendo archivo de mapa. Seguramente mal formato. -> "+e.toString());
		}
		return res;
	}
}
