package engine3D;

import java.util.Random;

import com.threed.jpct.*;

public class AnimatedObject3D extends Object3D implements IAnimation{
	
	
	private static Random num;	 //Para iniciar la animaci�n por defecto por un n�mero de frame aleatorio.
	//----------------------------------------
    //-    VARIABLES GLOBALES DE LA CLASE    -
    //----------------------------------------
	protected boolean animationIsOn;
	protected int numSeqStored;
	protected int defaultSeq;	
	protected int currentSeq;
	protected float currentValueInSeq;
	protected boolean loopAnimation;	
	protected boolean doingPrimaryAnimation;	//En caso de querer hacer una animaci�n y que el engine3D no pueda
												//Mover m�s personajes hasta terminar, necesitaremos una variable de
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
		//Indicamos que el n�mero de keyframe en la secuencia actual es aleatorio (van de 0 a 1, siendo 1 el �ltimo frame), para que
		//al cargar los objetos no vayan todos al comp�s y de m�s realismo.
		num = new Random();
		//Pedimos un valor entre 0 y 10 y lo guardamos como float.
		float secInit = (float)num.nextInt(100);
		//Lo dividimos entre 100 para obtener valores de entre 0.0 y 1.0  (ef : 0.45).
		currentValueInSeq = secInit/100;	
		//Por defecto empezamos con la animaci�n por defecto corriendo
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
		//Creamos el nuevo objeto y le pasamos como argumento de creaci�n "sup"
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

		//Devolvemos el n�mero de secuencias de este objeto.
		return numSeqStored;
	}

	public boolean isAnimationRuning(){

		//Devolvemos si actualmente estamos animando o no
		return animationIsOn;
	}

	public void nextAnimationStep(float step){
		
		//Actualizamos el movimiento para mostrar en el siguiente frame
		//Primero vemos si la animaci�n est� parada o no.
		if (animationIsOn)
		{
			//Las animaciones dentro de una secuencia van de 0 a 1.
			if (currentValueInSeq + step <= 1)
			{
				/*
				//Ojo, a ver si nos pasamos al a�adir el step
				if (currentValueInSeq + step > 1 )
				{										
					//No podemos seguir animando esa secuencia, as� que la doy por finalizada y cargo la 
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
				//Indicamos (por si no se ha hecho antes) que ya he terminado una animaci�n.
				doingPrimaryAnimation = false;
				//Si llegamos al final de la animaci�n pero estamos con loopAnimation == true, entonces
				//reiniciamos la misma secuencia de animaci�n.
				currentValueInSeq = 0;
				animate(currentValueInSeq, currentSeq);	

				
			}
			else
			{
				//Indicamos (por si no se ha hecho antes) que ya he terminado una animaci�n.
				doingPrimaryAnimation = false;
				//Hemos llegado al final de la animaci�n actual, es decir, de la secuencia.
				//Cargamos la animaci�n por defecto, que por defecto hace loop sola, as� que ponemos loopAnimation=false;
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
		
		//Ponemos la animaci�n por defecto.
		defaultSeq = value;
	}
	public void setPlayAnimation(boolean value){
		
		//Indicamos si debemos animar o no seg�n el argumento.
		animationIsOn = value;
	}
	public boolean isDoingPrimaryAnimation(){
		
		//Devolvemos el valor pedido
		return doingPrimaryAnimation;
	}
}
