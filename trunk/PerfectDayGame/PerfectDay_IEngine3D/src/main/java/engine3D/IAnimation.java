package engine3D;

public interface IAnimation {

    	//------------------------------------------
    	//-    METODOS ABSTRACTOS DEL INTERFACE    -
    	//------------------------------------------
	    
		//Para actualizar la animación para el siguiente frame
		abstract void nextAnimationStep(float speed);
		//Para saber si la animación está en marcha.
		abstract boolean isAnimationRuning();
		//Para controlar la animación (On, off)
		abstract void setPlayAnimation(boolean value);		
		//Pone una determinada animación e indica si es en modo loop
		abstract void setAnimationSequence(int value, boolean loop);
		//Indica cuál es la animación por defecto.
		abstract void setDefaultAnimation(int value);
		//Devuelve el número de secuencias animaciones del objeto.
		abstract int getNumSequencesStored();
		
		//El engine3D usará estos métodos para animar en cada frame el siguiente
		//estado de un objeto animado.
}
