package engine3D;

import java.util.Hashtable;
import com.threed.jpct.*;

public class ObjectManager {
	
	//Se almacena un objeto3D cargado desde disco para no
	//tener que cargarlo cada vez que haya que renderizarlo.
	
	//IMPORTANTE!! TODOS LOS NOMBRES SE ALMACENAN EN MAYÚSCULAS!.
	
	static final String objetoEmisor = "ObjectManager";
	//----------------------------------------
    //-    VARIABLES GLOBALES DE LA CLASE    -
    //----------------------------------------	
	Hashtable oStore;	//Tabla de objectos cargados desde disco
	EngineLog eLog;		//Para guardar información.
	//-----------------------
    //-    CONSTRUCTORES    -
    //-----------------------
	public ObjectManager(EngineLog engineLog){
		
		//Creamos la tabla de objectos
		oStore = new Hashtable(1);
		//Asociamos el log de salida de información.
		eLog = engineLog;
	}
	//----------------------------------------------------
    //-    METODOS DE INSTANCIA (ELEMENTOS ESTATICOS)    -
    //----------------------------------------------------
	public boolean addObject3D(String keyName,String fileName, float factor){
				
		boolean res = false;
		Object3D newObject;
		//Carga y almacena un objecto si existe el arvchivo y si no está ya almacenado.
		//La key de almacenamiento es el String keyName.
		//La variable "factor" indica el factor de tamaño. Para no variar el tamaño, usar 1.
		//Solo admite objectos de formato 3DS y formados por una única pieza.

		Object3D[] objects = Loader.load3DS(fileName, factor);
		//Chequeamos y almacenamos.
	    newObject = objects[0];
	    //Ponemos a null el array para liberar (cuando JVM lo crea) los objetos no usados.
	    objects = null;   	   	
	    //Para evitar errores, siempre trabajaremos en mayúsculas.
	    String fileNameM = fileName.toUpperCase();
	    String keyNameM = keyName.toUpperCase();
    	//Si el objeto creado existe, lo almacenamos en la tabla hash.
    	if (newObject != null)
    	{
    		if (!isStoredObject3D(keyNameM))
    		{
    			oStore.put(keyNameM, newObject);
    			eLog.writeEngineLogWithEmisor(objetoEmisor,"Almacenado el objeto "+fileNameM+" con KEY "+keyNameM+" y factor de escala "+Float.toString(factor)+".");
    			//Indicamos que todo ha ido bien
    			res = true;
    		}
    		else
    		{
    			eLog.writeEngineLogWithEmisor(objetoEmisor,"No se ha podido almacenar el objeto "+fileNameM+" ya que la KEY "+keyNameM+" ya existe.");
    		}    		
    	}
    	else
    	{
    		eLog.writeEngineLogWithEmisor(objetoEmisor,"Error al cargar el objeto "+fileNameM+". La carga devuelve NULL.");
    	}
    	return res;
	}
	public boolean removeObject3D(String keyName){
		
		boolean res = false;
		//Primero hay que ver si existe.
		String keyNameM = keyName.toUpperCase();
		if (oStore.containsKey(keyNameM))
		{
			oStore.remove(keyNameM);
			eLog.writeEngineLogWithEmisor(objetoEmisor,"Se ha eliminado del almacén el objeto con KEY "+keyNameM+".");
			res = true;
		}
		else
		{
			eLog.writeEngineLogWithEmisor(objetoEmisor,"No se ha eliminado del almacén el objeto con KEY "+keyNameM+": La KEY no existe.");
		}		
		return res;
	}
	public Object3D getObject3DCopy(String keyName){
		
		Object3D copy = null;
		//Vemos si la KEY existe.
		String keyNameM = keyName.toUpperCase();
		if (isStoredObject3D(keyNameM))
		{
			//Sacamos el objeto3D
			Object3D obj = (Object3D)oStore.get(keyNameM);
			//Lo clonamos
			copy = obj.cloneObject();
		}
		else
		{
			eLog.writeEngineLogWithEmisor(objetoEmisor,"No se ha podido obtener una copia del objeto con KEY "+keyNameM+": La KEY no existe.");
		}
		return copy;
	}
	public boolean isStoredObject3D(String key){
		
		//Devuelve si un objeto con dicha key está ya almacenado.
		return (oStore.containsKey(key.toUpperCase()));
	}
	//---------------------------------------------------
    //-    METODOS DE INSTANCIA (ELEMENTOS ANIMADOS)    -
    //---------------------------------------------------
	public boolean addAnimatedObject3D(String keyName,String fileName, float factor){
		
		boolean res = false;
		//Carga y almacena un objecto si existe el arvchivo y si no está ya almacenado.
		//La key de almacenamiento es el String keyName.
		//La variable "factor" indica el factor de tamaño. Para no variar el tamaño, usar 1.
		//Solo admite objectos de formato MD2 y formados por una única pieza.

		Object3D newAnimatedObject = Loader.loadMD2(fileName, factor);
		//Chequeamos y almacenamos.  	   	
		String fileNameM = fileName.toUpperCase();
		String keyNameM = keyName.toUpperCase();
    	//Si el objeto creado existe, lo almacenamos en la tabla hash.
    	if (newAnimatedObject != null)
    	{
    		if (!isStoredObject3D(keyNameM))
    		{
    			oStore.put(keyNameM, newAnimatedObject);
    			eLog.writeEngineLogWithEmisor(objetoEmisor,"Almacenado el objeto animado "+fileNameM+" con KEY "+keyNameM+" y factor de escala "+Float.toString(factor)+".");
    			//Indicamos que todo ha ido bien
    			res = true;
    		}
    		else
    		{
    			eLog.writeEngineLogWithEmisor(objetoEmisor,"No se ha podido almacenar el objeto animado "+fileNameM+" ya que la KEY "+keyNameM+" ya existe.");
    		}    		
    	}
    	else
    	{
    		eLog.writeEngineLogWithEmisor(objetoEmisor,"Error al cargar el objeto animado "+fileNameM+". La carga devuelve NULL.");
    	}
    	return res;
	}
	public boolean removeAnimatedObject3D(String keyName){
		
		boolean res = false;
		//Primero hay que ver si existe.
		String keyNameM = keyName.toUpperCase();
		if (oStore.containsKey(keyNameM))
		{
			oStore.remove(keyNameM);
			eLog.writeEngineLogWithEmisor(objetoEmisor,"Se ha eliminado del almacén el objeto animado con KEY "+keyNameM+".");
			res = true;
		}
		else
		{
			eLog.writeEngineLogWithEmisor(objetoEmisor,"No se ha eliminado del almacén el objeto animado con KEY "+keyNameM+": La KEY no existe.");
		}		
		return res;
	}
	public Object3D getAnimatedObject3DCopy(String keyName){
		
		Object3D copy = null;
		//Vemos si la KEY existe.
		String keyNameM = keyName.toUpperCase();
		if (isStoredObject3D(keyNameM))
		{
			//Sacamos el objeto3D
			Object3D obj = (Object3D)oStore.get(keyNameM);
			//Lo clonamos
			copy = obj.cloneObject();
			//Informamos.
		}
		else
		{
			eLog.writeEngineLogWithEmisor(objetoEmisor,"No se ha podido obtener una copia del objeto animado con KEY "+keyNameM+": La KEY no existe.");
		}
		return copy;
	}
	public boolean isStoredAnimatedObject3D(String key){
		
		//Devuelve si un objeto con dicha key está ya almacenado.
		return (oStore.containsKey(key.toUpperCase()));
	}
	/*
	//---------------------------------------------------
    //-    METODOS DE INSTANCIA (PERSONAJES ANIMADOS)    -
    //---------------------------------------------------
	public boolean addAnimatedCharacter3D(String keyName,String fileName, float factor){
		
		boolean res = false;
		Character3D newAnimatedObject;
		//Carga y almacena un objecto si existe el arvchivo y si no está ya almacenado.
		//La key de almacenamiento es el String keyName.
		//La variable "factor" indica el factor de tamaño. Para no variar el tamaño, usar 1.
		//Solo admite objectos de formato MD2 y formados por una única pieza.

		Object3D newObject = Loader.loadMD2(fileName, factor);
		newAnimatedObject = new Character3D(newObject);
		//Chequeamos y almacenamos.  	   	
		String fileNameM = fileName.toUpperCase();
		String keyNameM = keyName.toUpperCase();
    	//Si el objeto creado existe, lo almacenamos en la tabla hash.
    	if (newAnimatedObject != null)
    	{
    		if (!isStoredObject3D(keyNameM))
    		{
    			oStore.put(keyNameM, newAnimatedObject);
    			eLog.writeEngineLogWithEmisor(objetoEmisor,"Almacenado el personaje animado "+fileNameM+" con KEY "+keyNameM+" y factor de escala "+Float.toString(factor)+".");
    			//Indicamos que todo ha ido bien
    			res = true;
    		}
    		else
    		{
    			eLog.writeEngineLogWithEmisor(objetoEmisor,"No se ha podido almacenar el personaje animado "+fileNameM+" ya que la KEY "+keyNameM+" ya existe.");
    		}    		
    	}
    	else
    	{
    		eLog.writeEngineLogWithEmisor(objetoEmisor,"Error al cargar el personaje animado "+fileNameM+". La carga devuelve NULL.");
    	}
    	return res;
	}
	public boolean removeAnimatedCharacter3D(String keyName){
		
		boolean res = false;
		//Primero hay que ver si existe.
		String keyNameM = keyName.toUpperCase();
		if (oStore.containsKey(keyNameM))
		{
			oStore.remove(keyNameM);
			eLog.writeEngineLogWithEmisor(objetoEmisor,"Se ha eliminado del almacén el objeto animado con KEY "+keyNameM+".");
			res = true;
		}
		else
		{
			eLog.writeEngineLogWithEmisor(objetoEmisor,"No se ha eliminado del almacén el objeto animado con KEY "+keyNameM+": La KEY no existe.");
		}		
		return res;
	}
	public Character3D getAnimatedCharacter3DCopy(String keyName){
		
		Character3D copy = null;
		//Vemos si la KEY existe.
		String keyNameM = keyName.toUpperCase();
		if (isStoredObject3D(keyNameM))
		{
			//Sacamos el objeto3D
			Character3D obj = (Character3D)oStore.get(keyNameM);
			//Lo clonamos
			copy = (Character3D)obj.cloneAnimatedObject();
			//Informamos.
		}
		else
		{
			eLog.writeEngineLogWithEmisor(objetoEmisor,"No se ha podido obtener una copia del objeto animado con KEY "+keyNameM+": La KEY no existe.");
		}
		return copy;
	}
	public boolean isStoredAnimatedCharacter3D(String key){
		
		//Devuelve si un objeto con dicha key está ya almacenado.
		return (oStore.containsKey(key.toUpperCase()));
	}
	*/
}
