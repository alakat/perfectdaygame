package engine3D;

public class MathToy {


	//---------------------
    //-    CONSTRUCTOR    -
    //---------------------
	public MathToy(){
		
	}
	//-------------------------------
    //-    METODOS DE INSTANCIA     -
    //-------------------------------
	public static int rotLikeClock(int angleSrc, int angleDst){
		
		//Valores de retorno:
		// 0 Si los ángulos son iguales.
		// 1 Si el camino corto está en sentido de las agujas del reloj.
		// 2 Si el camino corto está en sentido contrario a las agujas del reloj.
		//Siempre nos referimos al camino más corto a recorrer para ir del ángulo
		//origen al ángulo destino. Siempre se interpretan en grados (Max 360).		
		int res = 0;
		if (angleSrc != angleDst)
		{
			if (angleSrc < angleDst)
			{
				//¿En qué sentido está el camino largo?
				if (angleDst - angleSrc > 180)
				{
					res = 1;
				}
				else
				{
					res = 2;
				}
			}
			else	//Si (angleSrc > angleDst)
			{
				//¿En qué sentido está el camino largo?
				if (angleSrc - angleDst > 180)
				{
					res = 2;
				}
				else
				{
					res = 1;
				} 
			}
		}		
		//Devolvemos el valor.
		return res;
	}
	public static int getMinimumDegreesBetween(int angleSrc, int angleDst){
		
		//Devuelve el menos número de grados entre dos valores de ángulos.
		//ej:  entre 350 y 10 el valor es 20.
		int res = 0;
		if (angleSrc != angleDst)
		{
			if (angleSrc < angleDst)
			{
				//¿En qué sentido está el camino largo?
				if (angleDst - angleSrc > 180)
				{
					res = getSubtractValueInDegres(angleSrc, angleDst);
				}
				else
				{
					res = getSubtractValueInDegres(angleDst, angleSrc);
				}
			}
			else	//Si (angleSrc > angleDst)
			{
				//¿En qué sentido está el camino largo?
				if (angleSrc - angleDst > 180)
				{
					res = getSubtractValueInDegres(angleDst, angleSrc);
				}
				else
				{
					res = getSubtractValueInDegres(angleSrc, angleDst);
				} 
			}
		}		
		//Devolvemos el valor.
		return res;	
	}
	public static int getSumValueInDegrees(int angleBase, int angleToAdd){
		
		int res = angleBase + angleToAdd;
		if (res >= 360)
		{
			//Lo normalizamos.
			res = res - 360;
		}
		return res;
	}
	public static int getSubtractValueInDegres(int angleBase, int angleToSub){
		
		int res = angleBase - angleToSub;
		if (res < 0)
		{
			//Lo normalizamos.
			res = 360 - angleToSub + angleBase;
		}
		return res;
	}
	public static boolean valueInDegresBetweenTwoValues(int value, int sideA, int sideB){
		
		//Mira si un valor en grados está entre otros dos. Ejmpo " 50 entre 30 y 80 es cierto". "50 entre 60 y 90 es falso".
		boolean res = false;
		
		int minDegBetweenAB = getMinimumDegreesBetween(sideA, sideB);		
		int distValueToA = getMinimumDegreesBetween(value, sideA);
		int distValueToB = getMinimumDegreesBetween(value, sideB);
		//Ojo, no poner =<, ya que para el valor 180 de separación entre los 3 puntos, daría cierto!
		if ((distValueToA < minDegBetweenAB) && (distValueToB < minDegBetweenAB))
		{
			res = true;
		}						
		return res;
	}
	public static float degresToOnlyPositiveDegresBetween0and359(float deg){
		
		float res = deg;
		
		if (deg < 0)
		{
			res = 360 - deg;
			res = degresToOnlyPositiveDegresBetween0and359(res);
		}
		else if (deg > 360)
		{
			res = res - 360;
			//Por si hay más de una vuelta, ej: 750;
			res = degresToOnlyPositiveDegresBetween0and359(res);
		}	
		else if (deg == 360)
		{
			res = 0;
		}
		return res;
	}
}	
	