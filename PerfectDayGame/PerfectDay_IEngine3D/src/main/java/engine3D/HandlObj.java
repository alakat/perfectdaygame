package engine3D;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import com.threed.jpct.*;

public class HandlObj {

	//IMPORTANTE!! HAY QUE TRABAJAR CON LOS NOMBRES SIEMPRE EN MAYÚSCULAS.
	
	static final String objetoEmisor = "HandlObj";
	//----------------------------------------
    //-    VARIABLES GLOBALES DE LA CLASE    -
    //----------------------------------------
	private Object3D terrain = null; //Terreno en caso de haberlo.
	private Hashtable staticTableToys;	//HashTable con los distintos tipos de adornos del mapa de juego.
	private Hashtable animatedCharacterTableToys; //HashTable con los personajes animados del mapa de juego (personajes).
	private Hashtable animatedObjectTableToys; //HashTable con los objetos animados del mapa de juego (personajes).
	private Hashtable namesTableToys;	//Hastable con todos los nombres de objetos el mundo y su objeto3D relacioando.
	private Hashtable signalsTableToys; //HashTable con los objetos signal del mapa de juego.
	private EngineLog eLog;
	private Engine3D engine3D;
	//-----------------------
    //-    CONSTRUCTORES    -
    //-----------------------
    public HandlObj(Engine3D eng3D, EngineLog engineLog){
        
    	//Creamos la tabla de terrenos
    	//staticMapToys = new Hashtable(1);
    	//Asociamos el mundo virtual
    	//world = eng3D.world;
    	engine3D = eng3D;
    	eLog = engineLog;
    	//Iniciamos el almacén de objetos estáticos.
    	staticTableToys = new Hashtable(1);
    	//Iniciamos el almacén de objetos animados.
    	animatedCharacterTableToys = new Hashtable(1);
    	animatedObjectTableToys = new Hashtable(1);
    	//Iniciamos el almacén de señales.
    	signalsTableToys = new Hashtable(1);
    	//Iniciamos el almacén de nombres en mundo con objetos (animados o inanimados) e incluso el terreno.
    	namesTableToys = new Hashtable(1);
    }
    //-------------------------------
    //-    METODOS DE INSTANCIA     -
    //-------------------------------
    public String getNameInWorldFromObject(Object3D obj){
    	
    	//Devolvemos el nombre en el mundo de un objeto3D pasado por argumento.
    	String res = null;
    	if (namesTableToys.containsKey(obj))
    	{	    	
    	    //buscamos en la tabla de nombres el nombre del objeto.
    	    res = (String)namesTableToys.get(obj);
    	}
    	return res;
    }
    //--------------------------------------------
    //-    METODOS DE INSTANCIA PARA TERRENOS    -
    //--------------------------------------------
    public void insertTerrainToWorld(TerrainObject3D terrainObject, String nameInWorld){
    	
    	//Almacena el terreno de juego y lo mete en el mundo virtual
    	//Primero vemos si ya hay uno, pues hay que sacarlo.
    	if (terrain != null) 
    	{
    		//Eliminamos el antíguo
    		removeTerrainFromWorld();
    	}
    	//Asociamos el nuevo
    	terrain = terrainObject.getAndBuildTerrainObject3D();
    	//Le asociamos un nombre en el mundo.
    	namesTableToys.put(terrain, nameInWorld.toUpperCase());      	
    	//Si se están usando sombras dinámicas, metemos el nuevo terreno indicando que recive sombras.
    	if (engine3D.usingDinamicShadows)
    	{
        	engine3D.getShadowHelper().addReceiver(terrain);
    	}
    	//Metemos el objeto en el mundo virtual.
    	engine3D.putOnWorld(terrain);    	    
    	//Indicamos los valores para el movimiento tope de la cámara.
    	engine3D.maxXCamPos = terrainObject.getMaxSizeX();
    	engine3D.maxZCamPos = terrainObject.getMaxSizeY();
    	engine3D.maxYCamPos = -(engine3D.maxXCamPos+engine3D.maxYCamPos);
    }
    public void removeTerrainFromWorld(){
    	
    	//Borramos el nombre de la lista de objetos metidos en el mundo.
    	namesTableToys.remove(terrain);
    	//Eliminamos el antíguo
    	engine3D.removeFromWorld(terrain);
		terrain = null;
    }
    public Object3D getTerrain(){
    	
    	return terrain;
    }
    //-------------------------------------------------------------
    //-    METODOS DE INSTANCIA PARA OBJETOS ESTATICOS (TOYS)     -
    //-------------------------------------------------------------
    public void insertStaticModelToMap(Object3D object, String keyModel){
    	
	    //Construmos el objeto.
    	object.build();
    	//Lo metemos en la tabla de objetos estáticos.
	    staticTableToys.put(keyModel.toUpperCase(), object);
	    //Le asociamos un nombre en la tabla de nombres.
	    namesTableToys.put(object, keyModel.toUpperCase());
    	//Si se están usando sombras dinámicas, metemos el nuevo indicando que recive sombras y proyecta la suya propia.
    	if (engine3D.usingDinamicShadows)
    	{
    	    engine3D.getShadowHelper().addCaster(object);
    	}
	    //Metemos el objeto en el mundo configurando como actua con las sombras.
	    engine3D.putOnWorld(object);
    }
    public boolean containsStaticModel(String keyModel){
    	
    	return staticTableToys.containsKey(keyModel.toUpperCase());
    }
    public void removeStaticModelFromMap(String keyModel){
    	
    	//Si está, lo borramos.
    	if (staticTableToys.containsKey(keyModel.toUpperCase()))
    	{
    		//Eliminamos el objeto de la lista de objetos a renderizar.
    		Object3D obj = (Object3D)staticTableToys.remove(keyModel.toUpperCase());
    		//Eliminamos su alias de la lista de nombres de objetos del mundo.
    		namesTableToys.remove(obj);
    		//Lo eliminamos del mundo.
    		engine3D.removeFromWorld(obj);
    	}
    }  
    public Object3D getStaticModelFromMap(String keyModel){
    	
    	Object3D res = null;
    	//Si está, lo obtenemos
    	if (staticTableToys.containsKey(keyModel.toUpperCase()))
    	{
    		//Eliminamos el objeto de la lista de objetos a renderizar.
    		res = (Object3D)staticTableToys.remove(keyModel.toUpperCase());
    	}
    	return res;
    }
    //------------------------------------------------------------
    //-    METODOS DE INSTANCIA PARA OBJETOS ANIMADOS (TOYS)     -
    //------------------------------------------------------------
    public void insertAnimatedModelToMap(AnimatedObject3D animObject, String keyModel){
    	
	    //Lo construimos.    	
    	animObject.build();
    	//Le asociamos el nombre del mundo para ese objeto.    	
		namesTableToys.put(animObject,keyModel.toUpperCase());
		//Lo metemos en la tabla de objetos animados.
	    animatedObjectTableToys.put(keyModel.toUpperCase(), animObject);
	    //Si se usan sombras dinámicas,metemos el nuevo indicando que recive sombras y proyecta la suya propia.
    	if (engine3D.usingDinamicShadows)
    	{
    	    engine3D.getShadowHelper().addCaster(animObject);
    	}
	    //Metemos el objeto en el mundo.
	    engine3D.putOnWorld(animObject);
	    //Informamos.
		eLog.writeEngineLog("Añadido un objeto animado al mundo: "+ keyModel);
    }
    public AnimatedObject3D getAnimatedModelFromMap(String keyModel){
    	
    	return (AnimatedObject3D)animatedObjectTableToys.get(keyModel.toUpperCase());
    }
    public boolean containsAnimatedModel(String keyModel){
    	
    	return animatedObjectTableToys.containsKey(keyModel.toUpperCase());
    }
    public void removeAnimatedObjectFromMap(String keyModel){
    	
    	//Si existe lo eliminamos.
    	if (animatedObjectTableToys.containsKey(keyModel.toUpperCase()))
    	{    		
    		AnimatedObject3D obj = (AnimatedObject3D)animatedObjectTableToys.remove(keyModel.toUpperCase());
    		namesTableToys.remove(obj);
    		engine3D.removeFromWorld(obj);
    	}
    }
    public void incrementAnimatedObjectAnimations(float step){
    	
    	Enumeration e = animatedObjectTableToys.elements();
    	while (e.hasMoreElements())
    	{
    		AnimatedObject3D obj = (AnimatedObject3D)e.nextElement();
    		//animamos
    		obj.nextAnimationStep(step);
    	}
    }
    public void setObjectAnimationSequence(String keyModel,int seq, boolean loop){
    	
    	if (animatedObjectTableToys.containsKey(keyModel.toUpperCase()))
    	{
    		AnimatedObject3D obj = (AnimatedObject3D)animatedObjectTableToys.get(keyModel.toUpperCase());
    		obj.setAnimationSequence(seq, loop);
    	}
    }
    //------------------------------------------------------------
    //-    METODOS DE INSTANCIA PARA PERSONAJES ANIMADOS (TOYS)     -
    //------------------------------------------------------------
    public void insertAnimatedCharacterToMap(Character3D animObject, String keyModel){
    	
	    //Lo construimos.    	
    	animObject.build();
    	//Le asociamos el nombre del mundo para ese objeto.    	
		namesTableToys.put(animObject,keyModel.toUpperCase());
		//Lo metemos en la tabla de objetos animados.
	    animatedCharacterTableToys.put(keyModel.toUpperCase(), animObject);
	    //Si se usan sombras dinámicas,metemos el nuevo indicando que recive sombras y proyecta la suya propia.
    	if (engine3D.usingDinamicShadows)
    	{
    	    engine3D.getShadowHelper().addCaster(animObject);
    	}
	    //Metemos el objeto en el mundo.
	    engine3D.putOnWorld(animObject);
	    //Informamos.
		eLog.writeEngineLog("Añadido un objeto animado al mundo: "+ keyModel);
    }
    public Character3D getAnimatedCharacterFromMap(String keyModel){
    	
    	return (Character3D)animatedCharacterTableToys.get(keyModel.toUpperCase());
    }
    public boolean containsAnimatedCharacter(String keyModel){
    	
    	return animatedCharacterTableToys.containsKey(keyModel.toUpperCase());
    }
    public void removeAnimatedCharacterFromMap(String keyModel){
    	
    	//Si existe lo eliminamos.
    	if (animatedCharacterTableToys.containsKey(keyModel.toUpperCase()))
    	{    		
    		Character3D obj = (Character3D)animatedCharacterTableToys.remove(keyModel.toUpperCase());
    		namesTableToys.remove(obj);
    		engine3D.removeFromWorld(obj);
    	}
    }
    public void incrementCharacterAnimations(float step){
    	
    	Enumeration e = animatedCharacterTableToys.elements();
    	while (e.hasMoreElements())
    	{
    		Character3D obj = (Character3D)e.nextElement();
    		//animamos
    		obj.nextAnimationStep(step);
    	}
    }
    public void setCharacterAnimationSequence(String keyModel,int seq, boolean loop){
    	
    	if (animatedCharacterTableToys.containsKey(keyModel.toUpperCase())){
    		
    		Character3D obj = (Character3D)animatedCharacterTableToys.get(keyModel.toUpperCase());
    		obj.setAnimationSequence(seq, loop);
    	}
    }
    public void setCharacterAnimationSequenceNoWait(String keyModel,int seq, boolean loop){
    	
    	if (animatedCharacterTableToys.containsKey(keyModel.toUpperCase())){
    		
    		Character3D obj = (Character3D)animatedCharacterTableToys.get(keyModel.toUpperCase());
    		obj.setAnimatinoSequenceWithoutPrimaryCheck(seq, loop);
    	}
    }
    //--------------------------------------------
    //-    METODOS DE INSTANCIA PARA SEÑALES     -
    //--------------------------------------------
    public void insertSignalToMap(Object3D signal, String nameSignal, String signalTexture, boolean blending){
    	
    	//Le asociamos la textura indicada.
    	signal.setTexture(signalTexture.toUpperCase());   	
    	//¿usamos blending?
    	if (blending)
    	{
        	//Indicamos que se van a utilizar transparencias (alpha blending con el color negro absoluto).
        	signal.setTransparency(0);
    	}
	    //LAS SEÑALES NO SE PUEDEN SELECCIONAR!!!
	    signal.setSelectable(false);
    	//Construmos el objeto.
    	signal.build();
    	//Lo metemos en la tabla de objetos estáticos.
	    signalsTableToys.put(nameSignal.toUpperCase(), signal);
	    //Le asociamos un nombre en la tabla de nombres.
	    namesTableToys.put(signal, nameSignal.toUpperCase());
	    //Metemos el objeto en el mundo configurando como actua con las sombras.
	    engine3D.putOnWorld(signal);
    }
    public Object3D getSignalFromMap(String nameSignal){
    	
    	return (Object3D)signalsTableToys.get(nameSignal.toUpperCase());
    }
    public boolean containsSignal(String nameSignal){
    	
    	return signalsTableToys.containsKey(nameSignal.toUpperCase());
    }
    public void removeSignalFromMap(String nameSignal){
    	
    	//Si está, la borramos.
    	if (signalsTableToys.containsKey(nameSignal.toUpperCase()))
    	{
    		//Eliminamos el objeto de la lista de objetos a renderizar.
    		Object3D obj = (Object3D)signalsTableToys.remove(nameSignal.toUpperCase());
    		//Eliminamos su alias de la lista de nombres de objetos del mundo.
    		namesTableToys.remove(obj);
    		//Lo eliminamos del mundo.
    		engine3D.removeFromWorld(obj);
    	}
    }
    public void moveSignalToNewCellPosition(String nameSignal, int posX, int posY){
    	
    	if (containsSignal(nameSignal.toUpperCase()))
    	{
    		//Obtenemos el objeto3D.
    		Object3D signal = (Object3D)signalsTableToys.get(nameSignal.toUpperCase());
    		//Reseteamos su posición y rotación y lo posicionamos.
    		signal.setTranslationMatrix(new Matrix());
    		signal.translate(posX, 0.001f, posY);
    	}
    }
    public void hiddenSignalsToRender(){
		
		//Elimina las señales del mundo pero no del hastable, para así poder volver a meterlas después.
		//Se suele utilizar para quitar las señales antes de que shadowHelper renderice las sombras de los
		//objetos y añadirlas después para que aparezcan renderizadas sin sombra.
		
    	//Obtenemos la lista de señales almacenadas de world.
    	Set keys = signalsTableToys.keySet();
    	//Las saco todas del mundo.
    	Iterator iter = keys.iterator();
    	while (iter.hasNext())
    	{
    		String nameInWorld = (String)iter.next();
    		Object3D signal = (Object3D)signalsTableToys.get(nameInWorld);
    		engine3D.removeFromWorld(signal);
    		
    	}
	}
    public void visibleSignalsToRender(){
    	
    	//Obtenemos la lista de señales almacenadas de world.
    	Set keys = signalsTableToys.keySet();
    	//Las saco todas del mundo.
    	Iterator iter = keys.iterator();
    	while (iter.hasNext())
    	{
    		String nameInWorld = (String)iter.next();
    		Object3D signal = (Object3D)signalsTableToys.get(nameInWorld);
    		engine3D.putOnWorld(signal);    		
    	}
    }	
}
