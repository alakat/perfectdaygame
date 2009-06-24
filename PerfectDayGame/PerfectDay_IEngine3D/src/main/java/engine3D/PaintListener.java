package engine3D;

import com.threed.jpct.*;

public class PaintListener implements IPaintListener{
	
	//----------------------------------------
    //-    VARIABLES GLOBALES DE LA CLASE    -
    //----------------------------------------
	private int imagesPerSecond;
	private int imagesCounter;
	private NanoTimer nTimer;	
	private long timeToDrawAImage;
	private long aCTimeToDrawAImage;
	
	//---------------------
    //-    CONSTRUCTOR    -
    //---------------------
	public PaintListener(){
		
		//Creamos un contador que se inicia solo.
		nTimer = new NanoTimer();
		//Iniciamos las variables.
		imagesPerSecond = 0;
		imagesCounter = 0;
	}
	
	//-------------------------------
    //-    METODOS DE INSTANCIA     -
    //-------------------------------
	public void startPainting(){
		
		 //Empezamos a renderizar una nueva imagen..
		aCTimeToDrawAImage = nTimer.getElapsedTime();
	}
	public void finishedPainting(){
		
		//Totamos el tiempo que se ha tardado el crear la imagen.
		timeToDrawAImage = nTimer.getElapsedTime() - aCTimeToDrawAImage;
		//Al finalizar incrementamos el contador de imágenes renderizadas
		imagesCounter++;
		//Vemos si ha pasado más de un cuarto de segundo (1.000.000.000nseg == 1 seg).		
		if (nTimer.getElapsedTime() >= 1000000000)
		{		
			//fijamos el número de frames renderizados en este segundo que acaba de finalizar.
			imagesPerSecond = imagesCounter + 1;
			//Reseteamos el número de imágenes contadas
			imagesCounter = 0;
			//Iniciamos el contador de tiempo.
			nTimer.reset();
		}
	}
	public long getTimeToDrawAImage(){
		
		return timeToDrawAImage;
	}
	public int getFPS(){
		
		//Devolvemos el número de frames renderizados por segundo.
		return (imagesPerSecond);
	}
//**********************************************************************************************************	
//--------------------------------------
//			CLASE PRIVADA NANOTIMER
//--------------------------------------
private class NanoTimer {
	
  private long nanos=0;

  public NanoTimer() {
  	
  	//Guardamos los nanosegundos que lleva el pc encendido.
	  nanos = System.nanoTime();
  }
  public void reset() {
  	
  	//Guardamos los nanosegundos que lleva el pc encendido.
	  nanos = System.nanoTime();
  }

  public long getElapsedTime() {
     
  	//Restamos el valor almacenado en "nanos" con el valor actual para obtener
  	//la cantidad de nanosegundos que han pasado desde que creamos el objeto 
	//(o lo reseteamos) hasta que hacemos esta llamada.
  	long cur = System.nanoTime();
  	return (cur - nanos);
  }
}
//--------------------------------------
//		Fin clase privada NANOTIMER
//--------------------------------------
}
