package engine3D;

import com.threed.jpct.SimpleVector;

public class Trooper {

	//--------------------------------
    //-    CONSTANTES DE LA CLASE    -
	//--------------------------------
	public static final int NUM_SOLDIERS_1 = 1;
	public static final int NUM_SOLDIERS_2 = 2;
	public static final int NUM_SOLDIERS_3 = 3;
	public static final int NUM_SOLDIERS_4 = 4;
	public static final int NUM_SOLDIERS_5 = 5;
	public static final int NUM_SOLDIERS_6 = 6;
	
	public static final int DIR_FACING_NORTE = 90;
	public static final int DIR_FACING_SUR = 270;
	public static final int DIR_FACING_ESTE = 0;
	public static final int DIR_FACING_OESTE = 180;
	//----------------------------------------
    //-    VARIABLES GLOBALES DE LA CLASE    -
	//----------------------------------------
	private Engine3D engine3D;
	private int numSoldiers;
	private Character3D[] soldiers;
	private String trooperNameInWorld;
	//-----------------------
    //-    CONSTRUCTORES    -
    //-----------------------
	public Trooper(Engine3D engine3D, int numSoldiers, int aFacing, String key, String texture, String nameInWorld, SimpleVector position){
		
		//Guardamos referencias.
		this.engine3D = engine3D;
		trooperNameInWorld = nameInWorld;
		//En caso de no elegir uno de los disponibles, ponemos 1.
		switch (numSoldiers)
		{
			case (NUM_SOLDIERS_1):	numSoldiers = NUM_SOLDIERS_1 ;break;
			case (NUM_SOLDIERS_2):	numSoldiers = NUM_SOLDIERS_2 ;break;
			case (NUM_SOLDIERS_3):	numSoldiers = NUM_SOLDIERS_3 ;break;
			case (NUM_SOLDIERS_4):	numSoldiers = NUM_SOLDIERS_4 ;break;
			case (NUM_SOLDIERS_5):	numSoldiers = NUM_SOLDIERS_5 ;break;
			case (NUM_SOLDIERS_6):	numSoldiers = NUM_SOLDIERS_6 ;break;	
			default : numSoldiers = NUM_SOLDIERS_1; break;
		}
		//Preparamos una variable por si hay que invertir posiciones (ya que solo hemos defino 2 y son 4).
		boolean needInvert = false;
		//Variable para guardar el offset.
		float[] offset;
		//Obtenemos los offsets de las posiciones.
		if (aFacing == DIR_FACING_SUR)
		{
			offset = getPositions(numSoldiers, DIR_FACING_NORTE);
			needInvert = true;
		}
		else if (aFacing == DIR_FACING_OESTE)
		{
			offset = getPositions(numSoldiers, DIR_FACING_ESTE);
			needInvert = true;
		}
		else
		{
			offset = getPositions(numSoldiers, aFacing);
		}
		//Los creamos, recordar que ya tenemos 1 caracter.
		for (int i = 0; i < offset.length; i = i+2)
		{
			//Calculamos la posición con offset, corrigiendo el ángulo de la formación según para donde
			//indice la variable facing que está mirando la escuadra.
			SimpleVector pos;
			if (needInvert)
			{
				offset = getPositionWithCalculatedFacing(offset, aFacing);
			}
			//Tomamos la posición
			pos = new SimpleVector(position);
			//Calculamos
			pos.x = pos.x + offset[i];
			pos.z = pos.z + offset[i+1];
			engine3D.getHandlEngine().insertNewAnimatedCharacterInWorld(key, texture, trooperNameInWorld+Integer.toString(i), pos, aFacing, new SimpleVector(0,1,0));
		}
		//Hay que rotarlos y dejarlos mirando hacia donde nos indiquen.		
	}
	//---------------------------------------
    //-    METODOS DE INSTANCIA PRIVADOS    -
    //---------------------------------------
	private float[] getPositions(int nSoldiers, int facing){
		
		//Obtenemos el offset las posiciones de los soldados.
		//Cada posición es un par de valores int.
		float[] offset = new float[nSoldiers*2];
		switch (nSoldiers)
		{
		case (NUM_SOLDIERS_1):	{	offset[0] = 0; offset[1] = 0;};break;
		case (NUM_SOLDIERS_2):	{	offset = getNotSimetricalPosition(nSoldiers,facing);};break;
		case (NUM_SOLDIERS_3):	{	offset = getNotSimetricalPosition(nSoldiers,facing);};break;
		case (NUM_SOLDIERS_4):	{	offset[0] =  -0.25f; offset[1] = 0.25f;
									offset[2] =  0.25f; offset[3] =  0.25f;
									offset[4] = -0.25f; offset[5] = -0.25f;
									offset[6] =  0.25f; offset[7] = -0.25f;};break;
		case (NUM_SOLDIERS_5):	{	offset = getNotSimetricalPosition(nSoldiers,facing);};break;
		case (NUM_SOLDIERS_6):	{	offset = getNotSimetricalPosition(nSoldiers,facing);};break;	
		}
		//Devolvemos
		return offset;
	}
	private float[] getNotSimetricalPosition(int nSoldiers, int facing){
		
		//Obtenemos el offset las posiciones no simétricas de los soldados según facing.
		//Cada posición es un par de valores int.
		float[] offset = new float[nSoldiers*2];
		//Vemos facing.
		if (facing == DIR_FACING_NORTE)
		{
			switch (nSoldiers)
			{
				case (NUM_SOLDIERS_2):	{	offset[0] = -0.25f; offset[1] = 0;
											offset[2] =  0.25f; offset[3] = 0;};break;
				case (NUM_SOLDIERS_3):	{	offset[0] =  0.25f; offset[1] = 0.0f;
											offset[2] = -0.25f; offset[3] = 0.25f;
											offset[4] = -0.25f; offset[5] = -0.25f;};break;
				case (NUM_SOLDIERS_5):	{	offset[0] =  0.0f; offset[1] = 0.3f;
											offset[2] =  0.25f; offset[3] =  0.0f;
											offset[4] =  -0.25f; offset[5] = -0.f;
											offset[6] =  0.25f; offset[7] =   0.3f;
											offset[8] = -0.25f; offset[9] =  -0.3f;};break;
				case (NUM_SOLDIERS_6):	{	offset[0] =  0.25f; offset[1] = 0.25f;
											offset[2] =  -0.25f; offset[3] =  0.25f;
											offset[4] =  0.25f; offset[5] = -0.00f;
											offset[6] =  -0.25f; offset[7] = -0.00f;
											offset[8] =   0.25f; offset[9] = -0.25f;
											offset[10] = -0.25f; offset[11] = -0.25f;};break;	
			}
		}
		else if (facing == DIR_FACING_ESTE)
		{
			switch (nSoldiers)
			{
				case (NUM_SOLDIERS_2):	{	offset[0] =  0.0f; offset[1] = 0.25f;
											offset[2] =  0.0f; offset[3] = -0.25f;};break;
				case (NUM_SOLDIERS_3):	{	offset[0] =  0.0f; offset[1] = 0.25f;
											offset[2] = -0.25f; offset[3] = -0.25f;
											offset[4] =  0.25f; offset[5] = -0.25f;};break;
				case (NUM_SOLDIERS_5):	{	offset[0] =  0.3f; offset[1] = 0.00f;
											offset[2] =  0.00f; offset[3] =  0.25f;
											offset[4] =  0.00f; offset[5] = -0.25f;
											offset[6] = -0.3f; offset[7] =  0.25f;
											offset[8] = -0.3f; offset[9] = -0.25f;};break;
				case (NUM_SOLDIERS_6):	{	offset[0] =  0.3f; offset[1] = 0.25f;
											offset[2] =  0.3f; offset[3] = -0.25f;
											offset[4] =  0.0f; offset[5] =  0.25f;
											offset[6] =  0.0f; offset[7] = -0.25f;
											offset[8] =  -0.3f; offset[9] =  0.25f;
											offset[10] = -0.3f; offset[11] =-0.25f;};break;		
			}
		}
		//Devolvemos
		return offset;
	}
	private float[] getPositionWithCalculatedFacing(float[] offset, int dirFacing){
		
		if (dirFacing == DIR_FACING_SUR)
		{
			for (int i = 1; i < offset.length; i = i+2)
			{
				offset[i] = -offset[i];
			}			
		}
		else if (dirFacing == DIR_FACING_OESTE)
		{
			for (int i = 0; i < offset.length; i = i+2)
			{
				offset[i] = -offset[i];
			}			
		}
		return offset;
	}
	//---------------------------------------
    //-    METODOS DE INSTANCIA PUBLICOS    -
    //---------------------------------------
	public void setAnimationSequence(int sequence, boolean loop){
	
		for (int i = 0; i < numSoldiers; i++)
		{
			engine3D.getHaldlObj().setCharacterAnimationSequence((trooperNameInWorld.toUpperCase())+Integer.toString(i), sequence, loop);
		}
	}
	public void setAnimationSequenceNoWait(int sequence, boolean loop){
		
		for (int i = 0; i < numSoldiers; i++)
		{
			engine3D.getHaldlObj().setCharacterAnimationSequenceNoWait((trooperNameInWorld.toUpperCase())+Integer.toString(i), sequence, loop);
		}
	}
}
