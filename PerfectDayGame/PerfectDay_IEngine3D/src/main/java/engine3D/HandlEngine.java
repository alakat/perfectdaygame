package engine3D;

import java.awt.event.MouseEvent;

import com.threed.jpct.*;
import com.threed.jpct.util.*;

public class HandlEngine {
	
	static final String objetoEmisor = "HandlEngine";
	static float SIGNAL_HIGH = -0.015f;
	//----------------------------------------
    //-    VARIABLES GLOBALES DE LA CLASE    -
    //----------------------------------------
	private Engine3D engine3D;
	private TextureManager texMan;
	private EngineLog eLog;
	private ObjectManager oManager;
	private HandlObj handlObj;
	private TerrainObject3D terrainObj;
	
	protected int[] mouseCellPos;
	
    //-----------------------
    //-    CONSTRUCTORES    -
    //-----------------------
    public HandlEngine(Engine3D eng){
    	
    	//Guardamos las referencias a los objetos.
    	engine3D = eng;
    	//Obtenemos la referencia al gestor de texturas del engine3D.
    	texMan = engine3D.getTextureMan();
    	//Obtenemos la referencia a la instancia EngineLog.
    	eLog = engine3D.getEngineLog();
    	//Obtenemos la referencia de la instancia manjeador de objetos.
    	handlObj = engine3D.getHaldlObj();
    	//Creamos el almacen de objetos para cargarlos y almacenarlos, asociando la 
    	//instancia de salida de información.
    	oManager = engine3D.getObjectManager();   	
    	//Iniciamos la variable que contiene la casilla del mapa donde está el ratón apuntando.
    	mouseCellPos = new int[2];
    }
    //------------------------------
    //-    METODOS DE INSTANCIA    -
    //------------------------------
    public void setMouseEvent(MouseEvent mEvent){
    	
    	engine3D.mPosRX = mEvent.getX();
    	engine3D.mPosRY = mEvent.getY();
    }
    public void setRenderizar(boolean value){
    	
    	engine3D.setRenderizar(value);
    }
    public void setFpsFonts(String name){
    	
    	//Recordar que las texturas se guardan en mayúsculas
    	if (texMan.containsTexture(name.toUpperCase()))
    	{
    		Texture fonts = texMan.getTexture(name.toUpperCase());
    		engine3D.setFPSFontsNumberTexture(fonts);
    		eLog.writeEngineLog("Textura para números de FPS y polígonos CARGADAS");
    	}
    	else
    	{
    		eLog.writeEngineLog("Textura para números de FPS y polígonos NO CARGADAS "+name);
    	}
    }
    //---------------------
    //-      TERRENOS     -
    //---------------------
    public void createNewTerrain(int maxPolygons){
    	
    	//Crea un nuevo terreno de juego (mesh y textura)
    	//terrainObj = new TerrainObject3D();
    	terrainObj = new TerrainObject3D(maxPolygons, engine3D, eLog);    	
    	//Informamos
    	//eLog.writeEngineLogWithEmisor(objetoEmisor, "Construido nuevo TerrainObject de tamaño "+Integer.toString(maxX)+","+Integer.toString(maxY)+".");
    }    
    public boolean generateNewTerrainMap(int[][] map, String[] terrainModels, String[] terrainTextures, String[] objectsInMap){
    	
    	return terrainObj.generateNewTerrainMap(map, terrainModels, terrainTextures, objectsInMap);
    }
    public void insertTerrainToWorld(String nameInWorld){
    	
    	//Unimos el terreno al mundo virtual
    	handlObj.insertTerrainToWorld(terrainObj,nameInWorld);
        eLog.writeEngineLogWithEmisor(objetoEmisor, "El nuevo terreno de TerrainObject se ha unido al mundo virtual.");
    }
    public TerrainObject3D getTerrainObject3D(){
    	
    	return terrainObj;
    }
    public void setVisibleGrid(boolean value){
    	
    	terrainObj.setGridMode(value);
    }
    public boolean isVisibleGrid(){
    	
    	return terrainObj.getGridMode();
    }
    //-----------------------
    //-    OBJECT-MANAGER   -
    //-----------------------
    public boolean StoreObjectFromDisk(String fileName,String key, float scaleFactor){
    	
    	return oManager.addObject3D(key, fileName, scaleFactor);
    }
    public Object3D getObjectFromObjectManager(String key){
    	
    	return oManager.getObject3DCopy(key);
    }
    public boolean StoreAnimatedObjectFromDisk(String fileName,String key, float scaleFactor){
    	
    	return oManager.addAnimatedObject3D(key, fileName, scaleFactor);
    }
    public Object3D getAnimatedObjectFromObjectManager(String key){
    	
    	return oManager.getAnimatedObject3DCopy(key);
    }
    //------------------------
    //-    TEXTURE MANAGER   -
    //------------------------
    public void StoreTextureFromDisk(String directory, String name){
    	
    	if (!texMan.containsTexture(name))
    	{
    		Texture texture = new Texture(directory+name);
    		//Activamos clamping para que las ajuste a [0,1] en vez de repetirlas.
    		//MUY UTIL PARA EVITAR FALLOS HACIENDO MAPAS ESTILO TILES
    		texture.enableGLClamping();  
    		//Activamos el filtering
    		texture.setGLFiltering(true);
    		//OJO!!! 3ds guarda los nombres de texturas asociadas en mayúscula!
    		texMan.addTexture(name.toUpperCase(),texture);
    		eLog.writeEngineLogWithEmisor(objetoEmisor, "Añadida textura "+directory.toUpperCase()+name.toUpperCase()+" con NAME "+name.toUpperCase());
    	}
    }    
    //--------------
    //-    LUCES   -
    //--------------
    public void createNewStaticLight(float posX,float posY, float posZ, float red, float green, float blue, float attenuation, float discardDistance){
    	
    	Light newLight = new Light(engine3D.getWorld());
    	newLight.setPosition(new SimpleVector(posX,posY,posZ));
    	newLight.setAttenuation(attenuation);
    	newLight.setDiscardDistance(discardDistance);
    	newLight.enable();
    }    
    //--------------
    //-    CAMARA  -
    //--------------
    public void moveCamera(float posX, float posY, float posZ){
    	
    	engine3D.moveCamera(posX,posY,posZ);
    }
    //--------------------------
    //-    OBJETOS ESTATICOS   -
    //--------------------------
    public void insertNewStaticModelInWorld(String key, String name, SimpleVector position, float angleRotation, SimpleVector axisRotation){
    	
    	//Si no existe lo almacenamos.
    	if (!handlObj.containsStaticModel(name))
    	{
    		//Pasamos a mayúsculas.
    		key = key.toUpperCase();
    		name = name.toUpperCase();    
    		//Sacamos una mesh del model
    		Object3D copyMesh = oManager.getObject3DCopy(key);
        	Object3D newStaticObject = copyMesh.cloneObject();
        	//Si no clonamos los objetos, siguen compartiendo datos ambos objetos, hay que clonar los mesh.
        	newStaticObject.setMesh(copyMesh.getMesh().cloneMesh(true));        	
        	//compresión de la malla.
        	newStaticObject.getMesh().compress();
    		//Posicionamos el objeto.        	
        	newStaticObject.translate(0, 0, 0);
        	newStaticObject.translateMesh();
        	//Ahora lo desplazamos donde queramos.
        	newStaticObject.translate(position);
        	//Hacemos los cambios permanentes
        	//y lo giramos hacia donde queramos.
        	newStaticObject.rotateAxis(axisRotation, (float)java.lang.Math.toRadians(angleRotation));        	
        	//Ahora lo insertamos en el manejador de objetos, que trabaja con el objeto
        	//y el engine3D        	
    		handlObj.insertStaticModelToMap(newStaticObject, name);    		
    	}
    	else
    	{
    		eLog.writeEngineLogWithEmisor(objetoEmisor, "Ya existe en el mundo otro objeto con ese mismo nombre: "+ name);
    	}   	
    } 
    public void deleteStaticModelFromWorld(String key){
    	
    	handlObj.removeStaticModelFromMap(key);
    }
    public Object3D getStaticModelFromWorld(String key){
    	
    	return handlObj.getStaticModelFromMap(key);
    }
    //------------------------------------
    //-    OBJETOS ESTATICOS ANIMADOS    -
    //------------------------------------
    public void insertNewAnimatedModelInWorld(String key, String texture, String nameInWorld, SimpleVector position, int angleRotation, SimpleVector axisRotation){
    	
    	if (!handlObj.containsAnimatedModel(nameInWorld))
    	{
    		//Si no existe lo almacenamos.  		
    		Object3D copyMesh = oManager.getAnimatedObject3DCopy(key);
    		//Creamos el nuevo objeto animatedObject3D independiente de los demás.
    		
    		
    		AnimatedObject3D newAnimatedObject = new AnimatedObject3D(copyMesh);
    		newAnimatedObject.setMesh(newAnimatedObject.getMesh().cloneMesh(true));
    		//AnimatedObject3D newAnimatedObject = copyMesh.cloneAnimatedObject();
    		//Si no clonamos los objetos, siguen compartiendo datos ambos objetos. Hay que clonar los mesh
    		//compresión de la malla.
    		newAnimatedObject.getMesh().compress();
    		//Posicionamos el objeto.	
    		newAnimatedObject.translate(0, 0, 0);
    		newAnimatedObject.translateMesh();
    		//Ahora lo desplazamos donde queramos.
    		newAnimatedObject.translate(position);
    		//Hacemos el giro fijo para empezar a hacer giros desde esta posición.
    		newAnimatedObject.rotateAxis(axisRotation, angleRotation);
    		
    		//Añadimos textura. (No olvidar que se almacenan en mayúscula).
    		newAnimatedObject.setTexture(texture.toUpperCase());
    		//Lo insertamos en el mapa
    		handlObj.insertAnimatedModelToMap(newAnimatedObject, nameInWorld);
    	}
    	else
    	{
    		eLog.writeEngineLogWithEmisor(objetoEmisor, "Ya existe en el mundo otro objeto animado con ese mismo nombre: "+ key);
    	}
    } 
    public void deleteAnimatedModelFromWorld(String key){
    	
    	handlObj.removeAnimatedObjectFromMap(key);
    }    
    public void setAnimationSequenceInModelOnWorld(int sequence, String nameInWorld, boolean loop){
    	
    	if (handlObj.containsAnimatedModel(nameInWorld))
    	{
    		handlObj.setObjectAnimationSequence(nameInWorld, sequence, loop);
    	}
    	else
    	{
    		eLog.writeEngineLogWithEmisor(objetoEmisor, "Se está intentado asignar secuencias de animación a un objeto inexistente: "+ nameInWorld);
    	}
    }
    //-----------------------------
    //-    PERSONAJES ANIMADOS    -
    //-----------------------------
    public void insertNewAnimatedCharacterInWorld(String key, String texture, String nameInWorld, SimpleVector position, int angleRotation, SimpleVector axisRotation){
    	
    	if (!handlObj.containsAnimatedCharacter(nameInWorld))
    	{
    		//Si no existe lo almacenamos.  		
    		Object3D copyMesh = oManager.getAnimatedObject3DCopy(key);
    		//Creamos el nuevo objeto animatedObject3D independiente de los demás.
    		Character3D newAnimatedObject = new Character3D(copyMesh);
    		newAnimatedObject.setMesh(newAnimatedObject.getMesh().cloneMesh(true));
    		//AnimatedObject3D newAnimatedObject = copyMesh.cloneAnimatedObject();
    		//Si no clonamos los objetos, siguen compartiendo datos ambos objetos. Hay que clonar los mesh
    		//compresión de la malla.
    		newAnimatedObject.getMesh().compress();
    		//Posicionamos el objeto.	
    		newAnimatedObject.translate(0, 0, 0);
    		newAnimatedObject.translateMesh();
    		//Ahora lo desplazamos donde queramos.
    		newAnimatedObject.translate(position);
    		//Hacemos el giro fijo para empezar a hacer giros desde esta posición.
    		newAnimatedObject.rotateAxis(axisRotation, angleRotation);    		
    		//Añadimos textura. (No olvidar que se almacenan en mayúscula).
    		newAnimatedObject.setTexture(texture.toUpperCase());
		
    		//Lo insertamos en el mapa
    		handlObj.insertAnimatedCharacterToMap(newAnimatedObject, nameInWorld);
    	}
    	else
    	{
    		eLog.writeEngineLogWithEmisor(objetoEmisor, "Ya existe en el mundo otro objeto animado con ese mismo nombre: "+ key);
    	}
    }
    public void deleteAnimatedCharacterFromWorld(String key){
    	
    	handlObj.removeAnimatedCharacterFromMap(key);
    }    
    public void setAnimationSequenceInCharacterOnWorld(int sequence, String nameInWorld, boolean loop){
    	
    	if (handlObj.containsAnimatedCharacter(nameInWorld))
    	{
    		handlObj.setCharacterAnimationSequence(nameInWorld, sequence, loop);
    	}
    	else
    	{
    		eLog.writeEngineLogWithEmisor(objetoEmisor, "Se está intentado asignar secuencias de animación a un objeto inexistente: "+ nameInWorld);
    	}
    }
    public void moveAnimatedCharacterWithAnimation(String nameInWorld, int direction, float movingSpeed, int rotationSpeed, int seqAnimation, boolean loop){
    	
    	//Mueve un objeto animado a una cierta posición de destino con una cierta velocidad y una animación concreta.
    	if (handlObj.containsAnimatedCharacter(nameInWorld))
    	{
    		//Primero obtenemos una referencia al objeto.
    		Character3D animObj = handlObj.getAnimatedCharacterFromMap(nameInWorld);
    		//Ya tenemos la referencia.       
    		//Si ya se está moviendo, ignoramos la orden, para no provocar 
    		if (!animObj.isMoving())
    		{
    			animObj.setNewDestiny(direction, rotationSpeed, movingSpeed);
    			//Indicamos la secuencia de animación para el desplazamiento
    			animObj.setAnimationSequence(seqAnimation, loop);
    		}
    	}
    	else
    	{
    		eLog.writeEngineLogWithEmisor(objetoEmisor, "Se está intentado animar un personaje inexistente: "+ nameInWorld);
    	}
    }
    public Character3D getCharacter3DFromWorld(String key){
    	
    	return handlObj.getAnimatedCharacterFromMap(key);
    }
	//------------------------------------
	//-    OBJETOS SOMBRAS ESTÁTICAS     -
	//------------------------------------
    public void jointStaticShadow(String model, String nameCharacterToJoint, String shadowTexture, float altitude, float scale){
    	
		//Obtenemos el character al que asociamos la sombra.
		Character3D character = getCharacter3DFromWorld(nameCharacterToJoint);
		if (character != null)
		{
			//Añadimos una sombra estática. 
			insertNewStaticModelInWorld(model, nameCharacterToJoint+"shd", new SimpleVector(0,altitude,0), 0, new SimpleVector(0,0,0));
			Object3D shadow = getStaticModelFromWorld(nameCharacterToJoint+"shd".toUpperCase());
			//Escalamos.
			shadow.scale(scale);
	    	//Indicamos que se van a utilizar transparencias (alpha blending con el color negro absoluto).
			shadow.setTexture(shadowTexture.toUpperCase());
			shadow.setTransparency(0);
			//No son seleccionables.
			shadow.setSelectable(false);
			shadow.translate(0,0,0);
			shadow.translateMesh();
			//asociamos
			character.addChild(shadow); 
		}
    } 
	//--------------------------
	//-    OBJETOS SEÑALES     -
	//--------------------------
	public void insertNewSignal(String model, String nameSignal, String signalTexture, boolean blending, int posX, int posY){
		
		//Trabajos con las señales como si fueran objetos estáticos normales en lo que se refiere
		//a guardarlos y acceder a ellos.
		//Si no existe lo almacenamos.
    	if (!handlObj.containsSignal(nameSignal))
    	{
    		//Pasamos a mayúsculas.
    		nameSignal = nameSignal.toUpperCase();
    		model = model.toUpperCase();    
    		//Sacamos una mesh del model
    		Object3D copyMesh = oManager.getObject3DCopy(model);
        	Object3D newStaticObject = copyMesh.cloneObject();
        	//Si no clonamos los objetos, siguen compartiendo datos ambos objetos, hay que clonar los mesh.
        	newStaticObject.setMesh(copyMesh.getMesh().cloneMesh(true));        	
        	//compresión de la malla.
        	newStaticObject.getMesh().compress();
    		//Posicionamos el objeto.        	
        	newStaticObject.translate(0, -0.05f, 0);
        	newStaticObject.translateMesh();
        	//Ahora la ponemos fuera de la vista al iniciar).
        	newStaticObject.setVisibility(false);
        	//Ahora lo insertamos en el manejador de objetos, que trabaja con el objeto
        	//y el engine3D        	
    		handlObj.insertSignalToMap(newStaticObject, nameSignal, signalTexture, blending);    		
    	}
    	else
    	{
    		eLog.writeEngineLogWithEmisor(objetoEmisor, "Ya existe en el mundo otro objeto Signal con ese mismo nombre: "+ nameSignal);
    	}
	}
	public void removeSignal(String nameSignal){
		
		handlObj.removeSignalFromMap(nameSignal);
	}	
	public Object3D getSignalFromMap(String name){
		
		return handlObj.getSignalFromMap(name.toUpperCase());
	}
	public void moveSignalToNewPosition(String nameSignal, int posX, int posY){

		handlObj.moveSignalToNewCellPosition(nameSignal, posX, posY);
	}
	//------------------------------------------------------
	//-    OBJETOS SEÑAL DEL RATÓN Y COORDENADAS RATÓN     -
	//------------------------------------------------------
	public void refreshMouseSignalPosition(String nameSignal){
		
		
		//Pinta la señal del ratón en la celda indicada o en el objeto señalada.
		//Vemos si el objeto es válido.
		Object3D signal = getSignalFromMap(nameSignal);
		if (signal != null)
		{
			//Variable para guardar el desplazamiento.
			SimpleVector objTrans = null;
			//Obtengo los valores ID del polígono y el objeto apuntado por el ratón.
			int idPol = engine3D.poligonIDAimedFromMouse;
			Object3D obj = engine3D.object3DIDAimendFromMouse;
			//Nos aseguramos de que apunta a algoy que ese algo no sea la misma SIGNAL!.
			//(Si se detecta a sí misma se borra y pinta en cada frame debido al "signal.setVisible").
			if ((idPol != -1)&&(obj != null)&&(signal.getID() != obj.getID()))
			{		
				//Si es el terreno hay que poner la signal en las celdas.
				if (terrainObj.compareIDTerrains(obj))
				{
					//Obtenemos información de la celda en la que está el cursor
					//según el polígono al que está apuntando del objeto terrain.
					mouseCellPos = terrainObj.getCellInfo(idPol);
					//¿Es una sitación correcta?
					if ((mouseCellPos[0] != -1)&&(mouseCellPos[1] != -1))
			    	{
						//Es correcta, obtenemos la posición de la celda.
						objTrans = new SimpleVector();
						objTrans.x = mouseCellPos[0];
						objTrans.z = mouseCellPos[1];
					}
				}
				else
				{
					//No es un terreno, por lo que no hay que localizar su celda, es un
					//objeto y solo tenemos que poner la misma posición que este pero con
					//altura -0.05.
					objTrans = obj.getTranslation();
				}
				//Posicionamos la signal del ratón.
				signal.setTranslationMatrix(new Matrix());
				signal.translateMesh();
				signal.translate(objTrans.x, SIGNAL_HIGH, objTrans.z);
				//Si no está visible, la ponemos
				if (!signal.getVisibility())
				{
					signal.setVisibility(true);		
				}
			}
			else
			{
				//Entramos por aquí si no apuntamos a ningún objeto.
				//Escondemos el signal del ratón.
				signal.setVisibility(false);
			}
		}			
	}
	public int[] getMouseInCellCoord(){
		
		//Devolvemos las coordenadas en las que está el ratón.
		return mouseCellPos;
	}
	//--------------------
	//-   UTILIDADES     -
	//--------------------
	public void mergeTexturesLikeBlending(String textureBase, String textureAlpha){
		
		//Guarda el resultado en textureBase
		String texBase = textureBase.toUpperCase();
		String texAlpha = textureAlpha.toUpperCase();
		//Existen?
		if ((texMan.containsTexture(texBase))&&(texMan.containsTexture(texAlpha)))
		{
		    //Cogemos la información de la textura base
			TextureInfo texInfo = new TextureInfo(texMan.getTextureID(texBase));
		    //Le AÑADIMOS la información de la textura que hace de alpha
			texInfo.add(texMan.getTextureID(texAlpha),TextureInfo.MODE_ADD);
		}
		
	}
}
