package engine3D;

import java.util.Random;

import com.threed.jpct.*;

public class Character3D extends AnimatedObject3D{
	
	private static final int minRotSpeed = 1;
	private static final float minMovSpeed = 0.01f;	
	//----------------------------------------
    //-    VARIABLES GLOBALES DE LA CLASE    -
    //----------------------------------------
	//private int currentSeq;
	//private float currentValueInSeq;
	//private int numSeqStored;
	//private int defaultSeq;	
	private boolean isMoving;
	//private boolean animationIsOn;
	private int direction;
	private SimpleVector destinyTranslation;
	private int aFacing;		//Angulo destino al que va girando la figura. (Grados)
	private int currAFacing;	//Angulo actual de la figura. (Grados)
	private SimpleVector rotationAxis;
	private int rotSpeed;
	//private boolean loopAnimation;
	private float speedInAxisX;
	private float speedInAxisZ;
	private float accMovInAxisX;
	private float accMovInAxisY;
	private float accMovInAxisZ;	
	//-----------------------
    //-    CONSTRUCTORES    -
    //-----------------------
	public Character3D(Object3D animObj){
		
		//Llamamos al constructor del padre
		super(animObj);
		//Almacenamos el número de secuencias animadas que contiene el modelo animObj
		//numSeqStored = getAnimationSequence().getSequenceCount();
		//Indicamos que la secuencia actual y por defecto es 1 (con sec = 0 se realizan
		//todas las animaciones que contiene el modelo)
		//currentSeq = 1;
		//defaultSeq = 1;
		//Indicamos que el número de keyframe en la secuencia actual es aleatorio (van de 0 a 1, siendo 1 el último frame), para que
		//al cargar los objetos no vayan todos al compás y de más realismo.
		//num = new Random();
		//Pedimos un valor entre 0 y 10 y lo guardamos como float.
		//float secInit = (float)num.nextInt(100);
		//Lo dividimos entre 100 para obtener valores de entre 0.0 y 1.0  (ef : 0.45).
		//currentValueInSeq = secInit/100;		
		currAFacing = 0;
		rotationAxis = new SimpleVector(0,1,0);
		destinyTranslation = new SimpleVector();
		isMoving = false;
		//Por defecto empezamos con la animación por defecto corriendo
		//animationIsOn = true;
	}
	//-------------------------------
    //-    METODOS DE INSTANCIA     -
    //-------------------------------	
	public void nextDisplacedStep(){
		
		//Para mover el objeto por el tablero. No tiene que ver con la animación.
		//SE HA HECHO ASÍ PORQUE COMPARANDO VALORES FLOAT Y DOUBLE EN UN ESPACIO TRIDIMENSIONAL FALLABA
		//MUCHO EN LAS ESTIMACIONES (pérdidas de precisión que resultaban catastróficas).
		boolean arrived = false;
		switch(direction)
		{
			case 1:	{	if (getTranslation().z >= destinyTranslation.z) 
						{arrived = true;}
						break; 			//Norte		
					}
			case 2:	{	if ((getTranslation().z >= destinyTranslation.z)&&(getTranslation().x >= destinyTranslation.x)) 
						{arrived = true;}
						break;			//Noreste
					}
			case 3:	{	if (getTranslation().x >= destinyTranslation.x) 
						{arrived = true;}
						break; 			//Este
					}
			case 4:	{	if ((getTranslation().z <= destinyTranslation.z)&&(getTranslation().x >= destinyTranslation.x)) 
						{arrived = true;}
						break;			//Sureste
					}
			case 5:	{	if (getTranslation().z <= destinyTranslation.z) 
						{arrived = true;}
						break; 			//Sur
					}
			case 6:	{	if ((getTranslation().z <= destinyTranslation.z)&&(getTranslation().x <= destinyTranslation.x)) 
						{arrived = true;}
						break;			//Suroeste
					}
			case 7:	{	if (getTranslation().x <= destinyTranslation.x) 
						{arrived = true;}
						break; 			//Oeste
					}
			case 8:	{	if ((getTranslation().z >= destinyTranslation.z)&&(getTranslation().x <= destinyTranslation.x)) 
						{arrived = true;}
						break;			//Noroeste
					}
		}
		if (arrived)
		{
			//Indicamos que finaliza aquí el movimiento
			isMoving = false;	
			//Fijamos en el destino final. (Para evitar imprecisiones y que quede la figura centrada)
			setTranslationMatrix(new Matrix());
			translate(destinyTranslation);
			//Fijamos la rotación final. (Para evitar imprecisiones y que quede la figura bien rotada)
			setRotationMatrix(new Matrix());
			//Convertimos en radianes
			float currAFacingRad = (float)java.lang.Math.toRadians(aFacing);
			//Rotamos
			rotateAxis(rotationAxis , currAFacingRad);
			//Dejamos asignados para no perder el valor exacto de a dónde esta mirando este objeto.
			currAFacing = aFacing;
			//Se pone la animación por defecto
			
			//Queda desfasado al usar el STACKANIMATION para controlar las animaciones.
			//setAnimationSequence(defaultSeq, true);
		}
		else
		{	
			//Vemos que no hayamos completado el giro
			if (aFacing != currAFacing)
			{
				//giramos un poco el objeto. (si aFacing == currAFancing, no rotamos, solo desplazamos)
				if (rotSpeed != 0)
				{
					//Copiamos currAFacing
					int lastCurrAFacing = currAFacing;
					//Chequeamos
					if (rotSpeed > 0)
					{
						//Giramos en el sentido de las manecillas del reloj.
						currAFacing = MathToy.getSumValueInDegrees(currAFacing, rotSpeed);					
					}
					else  //(rotSpeed < 0)
					{
						//Invertimos el valor de rotSpeed para que sea absoluto.
						int absRotSpeed = (-rotSpeed);
						//Giramos en el sentido de las manecillas del reloj. (getSubstarctValueInDegrees) 
						//solo funciona con valores positivos, por eso obtenemos absRotSpeed;
						currAFacing = MathToy.getSubtractValueInDegres(currAFacing, absRotSpeed);
					}
					//Vemos si nos hemos pasado el destino
					if (MathToy.valueInDegresBetweenTwoValues(aFacing, lastCurrAFacing, currAFacing))
					{
						//Colocamos el valor exacto.
						currAFacing = aFacing;
					}
				}
				//Rotamos
				setRotationMatrix(new Matrix());
				//Convertimos en radianes
				float currAFacingRad = (float)java.lang.Math.toRadians(currAFacing);
				rotateAxis(rotationAxis , currAFacingRad);
			}
			//Ahora actualizamos el desplazamiento.
			//Creamos una nueva matrix de translación para usar desplazamientos universales.
			setTranslationMatrix(new Matrix());
			//Actualizamos los acumuladores
			accMovInAxisX = accMovInAxisX + speedInAxisX;
			accMovInAxisZ = accMovInAxisZ + speedInAxisZ;
			translate(accMovInAxisX, accMovInAxisY, accMovInAxisZ);
		}
	}
	public void setNewDestiny(int directionM, int rotationSpeed, float movingSpeed){
		
		//Referenciamos el resto de valores.
		direction = directionM;
		if ((rotationSpeed < 0)||(rotationSpeed >= 360))
		{
			rotSpeed = minRotSpeed;
		}
		else
		{
			rotSpeed = rotationSpeed;
		}
		//Vemos que la velocidad de movimiento no sea menor que la mínima permitida (Evitamos que sea 0 por ejemplo).
		if (movingSpeed < minMovSpeed)
		{
			movingSpeed = minMovSpeed;
		}
		//Vemos cómo nos movemos según donde esté este objeto y la dirección a la que hay que moverlo
		int movX = 0;
		int movZ = 0;		
		if ((direction > 0)&&(direction < 9))
		{
			switch(direction)
			{
				case 1:	{	speedInAxisX = 0; 
							speedInAxisZ = movingSpeed;
							movZ = movZ+1;
							aFacing = 90;
							break; 			//Norte		
						}
				case 2:	{	speedInAxisX = movingSpeed;
							speedInAxisZ = movingSpeed; 	
							movZ = movZ+1; 
							movX = movX+1;
							aFacing = 45;
							break;			//Noreste
						}
				case 3:	{	speedInAxisX = movingSpeed;
							speedInAxisZ = 0;
							movX = movX+1;
							aFacing = 0;
							break;			//Este
						}
				case 4:	{	speedInAxisX = movingSpeed;
							speedInAxisZ = (-movingSpeed);
							movZ = movZ-1;
							movX = movX+1;
							aFacing = 315;
							break;		//Sureste
						}
				case 5:	{	speedInAxisX = 0; 
							speedInAxisZ = (-movingSpeed); 
							movZ = movZ-1; 
							aFacing = 270;
							break; 			//Sur
						}
				case 6:	{	speedInAxisX = (-movingSpeed);
							speedInAxisZ = (-movingSpeed);
							movZ = movZ-1;
							movX = movX-1;
							aFacing = 225;
							break;			//Suroeste
						}
				case 7:	{	speedInAxisX = (-movingSpeed);
							speedInAxisZ = 0;
							movX = movX-1;
							aFacing = 180;
							break;			//Oeste
						}
				case 8:	{	speedInAxisX = -(movingSpeed); 
							speedInAxisZ = movingSpeed; 
							movZ = movZ+1; 
							movX = movX-1;
							aFacing = 135;
							break;	//Noroeste
						}
			}		
			//Lo guardamos como destino.
			destinyTranslation.x = getTranslation().x + movX;
			destinyTranslation.y = getTranslation().y;
			destinyTranslation.z = getTranslation().z + movZ;
			//System.out.println("Angulo actual : "+Float.toString(this.currAFacing)+", a colocar "+Float.toString(this.aFacing));
			//Iniciamos las variables acumulativas.
			accMovInAxisX = getTranslation().x;
			accMovInAxisZ = getTranslation().z;
			accMovInAxisY = getTranslation().y;		
			//Calculamos el giro, la dirección a la que se debe hacer.
			//Para ello vemos en qué sentido hay que girar para hacer menos recorrido.
			int rotDir = MathToy.rotLikeClock(currAFacing, aFacing);
			if (rotDir != 0)
			{
				if (rotDir == 1)
				{
					//Giramos en sentido de las manecillas del reloj.
					//Obtenemos una relación entre la cantidad de ángulo a girar
					//y la velocidad necesaria, para que así ya se gire la figura 90º
					//o 180, tarde el mismo tiempo en ambos casos.
					int longRot = MathToy.getMinimumDegreesBetween(currAFacing, aFacing);
					//Indicamos que ese valor hay que recorrerlo en el tiempo indicado
					//por el argumento de entrada rotationSpeed.
					rotSpeed = (-(longRot/rotSpeed));
				}
				else //rotDir == 2
				{
					//Giramos en contra del sentido de las manecillas del reloj.
					////Giramos en sentido de las manecillas del reloj.
					//Obtenemos una relación entre la cantidad de ángulo a girar
					//y la velocidad necesaria, para que así ya se gire la figura 90º
					//o 180, tarde el mismo tiempo en ambos casos.
					int longRot = MathToy.getMinimumDegreesBetween(currAFacing, aFacing);
					//Indicamos que ese valor hay que recorrerlo en el tiempo indicado
					//por el argumento de entrada rotationSpeed.
					rotSpeed = longRot/rotSpeed;
				}
			}
			//Indicamos que hay nuevo desplazamiento
			isMoving = true;
		}
	}
	public boolean isMoving(){
		
		return isMoving;
	}
	public Character3D cloneAnimatedObject(){
		
		//Devolvemos una copia de este objeto.
		Character3D res;
		AnimatedObject3D sup = super.cloneAnimatedObject();
		sup.setMesh(sup.getMesh().cloneMesh(true));		
		//Creamos el nuevo objeto y le pasamos como argumento de creación "sup"
		res = new Character3D(sup);
		//Igualamos los valores por defecto.
		//res.currentSeq = this.currentSeq;
		//res.currentValueInSeq = this.currentValueInSeq;
		//res.defaultSeq = this.defaultSeq;
		//res.numSeqStored = this.numSeqStored;
		//Lo devolvemos.
		return res;
	}
	//Sobreescribimos este método del padre
	public void rotateAxis(SimpleVector axisRotation, int angleRotation){
		
		if ((angleRotation >= 0)&&(angleRotation < 360))
		{
			currAFacing = angleRotation;
		}
		else
		{
			currAFacing = 0;
		}
		//Pasamos el valor a radianes.
		float currAFacingRad = (float)java.lang.Math.toRadians(currAFacing);
		//Seguimos haciendo lo que el padre haría.
		super.rotateAxis(axisRotation, currAFacingRad);
	}
	//------------------------------------------------------
    //-    METODOS DE INSTANCIA  DEL INTERFACE IANIMATION   -
    //------------------------------------------------------
	public void nextAnimationStep(float step){
		
		//Actualizamos el movimiento para mostrar en el siguiente frame
		//Primero vemos si la animación está parada o no.
		super.nextAnimationStep(step);
		//Vemos si hay desplazamiento
		if (isMoving)
		{
			nextDisplacedStep();
		}
	}
}
