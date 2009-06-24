package engine3D;

public interface IAnimation {

    	//------------------------------------------
    	//-    METODOS ABSTRACTOS DEL INTERFACE    -
    	//------------------------------------------
	    
		//Para actualizar la animaci�n para el siguiente frame
		abstract void nextAnimationStep(float speed);
		//Para saber si la animaci�n est� en marcha.
		abstract boolean isAnimationRuning();
		//Para controlar la animaci�n (On, off)
		abstract void setPlayAnimation(boolean value);		
		//Pone una determinada animaci�n e indica si es en modo loop
		abstract void setAnimationSequence(int value, boolean loop);
		//Indica cu�l es la animaci�n por defecto.
		abstract void setDefaultAnimation(int value);
		//Devuelve el n�mero de secuencias animaciones del objeto.
		abstract int getNumSequencesStored();
		
		//El engine3D usar� estos m�todos para animar en cada frame el siguiente
		//estado de un objeto animado.
}
