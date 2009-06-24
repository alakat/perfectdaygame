package engine3D;

import java.util.Random;

import com.threed.jpct.*;

public class AnimatedObject3D extends Object3D implements IAnimation{
	
	
	private static Random num;	 //Para iniciar la animación por defecto por un número de frame aleatorio.
	//----------------------------------------
    //-    VARIABLES GLOBALES DE LA CLASE    -
    //----------------------------------------
	protected boolean animationIsOn;
	protected int numSeqStored;
	protected int defaultSeq;	
	protected int currentSeq;
	protected float currentValueInSeq;
	protected boolean loopAnimation;	
	protected boolean doingPrimaryAnimation;	//En caso de querer hacer una animación y que el engine3D no pueda
												//Mover más personajes hasta terminar, necesitaremos una variable de
												//control. Esta es.
	//-----------------------
    //-    CONSTRUCTORES    -
    //-----------------------
	public AnimatedObject3D(Object3D animObj){
		
		//Hacemos la llamada al padre.
		super(animObj);
		//Cgemos sus datos.
		numSeqStored = animObj.getAnimationSequence().getSequenceCount();
		//Indicamos que la secuencia actual y por defecto es 1 (con sec = 0 se realizan
		//todas las animaciones que contiene el modelo)
		currentSeq = 1;
		defaultSeq = 1;
		//Indicamos que el número de keyframe en la secuencia actual es aleatorio (van de 0 a 1, siendo 1 el último frame), para que
		//al cargar los objetos no vayan todos al compás y de más realismo.
		num = new Random();
		//Pedimos un valor entre 0 y 10 y lo guardamos como float.
		float secInit = (float)num.nextInt(100);
		//Lo dividimos entre 100 para obtener valores de entre 0.0 y 1.0  (ef : 0.45).
		currentValueInSeq = secInit/100;	
		//Por defecto empezamos con la animación por defecto corriendo
		animationIsOn = true;
		doingPrimaryAnimation = false;
	}
	//-----------------------------------------
    //-    METODOS DE INSTANCIA    -
    //-----------------------------------------
	public AnimatedObject3D cloneAnimatedObject(){
		
		//Devolvemos una copia de este objeto.
		AnimatedObject3D res;
		Object3D sup = super.cloneObject();
		sup.setMesh(sup.getMesh().cloneMesh(true));		
		//Creamos el nuevo objeto y le pasamos como argumento de creación "sup"
		res = new AnimatedObject3D(sup);
		//Igualamos los valores por defecto.
		res.currentSeq = this.currentSeq;
		res.currentValueInSeq = this.currentValueInSeq;
		res.defaultSeq = this.defaultSeq;
		res.numSeqStored = this.numSeqStored;
		//Lo devolvemos.
		return res;
	}	
	//-----------------------------------------
    //-    METODOS DEL INTERFACE IANIMATION    -
    //-----------------------------------------
	public int getNumSequencesStored(){

		//Devolvemos el número de secuencias de este objeto.
		return numSeqStored;
	}

	public boolean isAnimationRuning(){

		//Devolvemos si actualmente estamos animando o no
		return animationIsOn;
	}

	public void nextAnimationStep(float step){
		
		//Actualizamos el movimiento para mostrar en el siguiente frame
		//Primero vemos si la animación está parada o no.
		if (animationIsOn)
		{
			//Las animaciones dentro de una secuencia van de 0 a 1.
			if (currentValueInSeq + step <= 1)
			{
				/*
				//Ojo, a ver si nos pasamos al añadir el step
				if (currentValueInSeq + step > 1 )
				{										
					//No podemos seguir animando esa secuencia, así que la doy por finalizada y cargo la 
					//secuencia por defecto.
					setAnimationSequence(defaultSeq, false);
					//iniciamos la animacion
					currentValueInSeq = 0;
				}
				else
				{*/
					currentValueInSeq = currentValueInSeq + step;
					animate(currentValueInSeq, currentSeq);
				//}
			}
			else if (loopAnimation)
			{
				//Indicamos (por si no se ha hecho antes) que ya he terminado una animación.
				doingPrimaryAnimation = false;
				//Si llegamos al final de la animación pero estamos con loopAnimation == true, entonces
				//reiniciamos la misma secuencia de animación.
				currentValueInSeq = 0;
				animate(currentValueInSeq, currentSeq);	

				
			}
			else
			{
				//Indicamos (por si no se ha hecho antes) que ya he terminado una animación.
				doingPrimaryAnimation = false;
				//Hemos llegado al final de la animación actual, es decir, de la secuencia.
				//Cargamos la animación por defecto, que por defecto hace loop sola, así que ponemos loopAnimation=false;
				setAnimatinoSequenceWithoutPrimaryCheck(defaultSeq, false);
				//iniciamos la animacion
				currentValueInSeq = 0;

			}
		}		
	}
	public void setAnimationSequence(int value, boolean loop){
		
		//Indicamos la secuencia que queremos animar.
		if ((value < numSeqStored)&&(value >= 0))
		{
			currentSeq = value;
			//Evidentemente, la empezamos a animar desde el principio.
			currentValueInSeq = 0;
			loopAnimation = loop;
			//Indicamos que empezamos una nueva secuencia para poder controlar cuando termina.
			doingPrimaryAnimation = true;
		}	
	}
	public void setAnimatinoSequenceWithoutPrimaryCheck(int value, boolean loop){
		
		//Indicamos la secuencia que queremos animar.
		if ((value < numSeqStored)&&(value >= 0))
		{
			currentSeq = value;
			//Evidentemente, la empezamos a animar desde el principio.
			currentValueInSeq = 0;
			loopAnimation = loop;
		}
	}
	public void setDefaultAnimation(int value){
		
		//Ponemos la animación por defecto.
		defaultSeq = value;
	}
	public void setPlayAnimation(boolean value){
		
		//Indicamos si debemos animar o no según el argumento.
		animationIsOn = value;
	}
	public boolean isDoingPrimaryAnimation(){
		
		//Devolvemos el valor pedido
		return doingPrimaryAnimation;
	}
}
