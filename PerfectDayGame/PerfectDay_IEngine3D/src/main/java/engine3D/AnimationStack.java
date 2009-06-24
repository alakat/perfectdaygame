package engine3D;

import java.util.Vector;

public class AnimationStack {
	
    //----------------------------------------
    //-    VARIABLES GLOBALES DE LA CLASE    -
    //----------------------------------------
	private Vector stack;
    //-----------------------
    //-    CONSTRUCTOR      -
    //-----------------------  
	public AnimationStack(){
		
		//Creamos la pila.
		stack = new Vector(1,1);
	}	
    //------------------------------
    //-    METODOS DE INSTANCIA    -
    //------------------------------
	public void addAnimationActionToStack(String nameObjectInWorld, int value1, int value2){
		
		//Creamos una clase de información.
		LittleAction littleAction = new LittleAction(nameObjectInWorld, Engine3D.ACTION_ANIMATE_CHARACTER, value1, value2, 0, 0);
		//Metemos en el vector.
		System.out.println("Añadida Animacion");
		stack.add(littleAction);
		
	}
	public void addMoveActionToStack(String nameObjectInWorld, int value1, int value3, float value4){
		
		//Creamos una clase de información.
		LittleAction littleAction = new LittleAction(nameObjectInWorld, Engine3D.ACTION_MOVE_CHARACTER, value1, 0, value3, value4);
		//Metemos en el vector.
		System.out.println("Añadida movimiento");
		stack.add(littleAction);
	}
	public LittleAction getNewAnimationAction(){
		
		//Devolvemos el primer elemento. Aquí si sacamos la acción.
		System.out.println("Consultada accion");
		return (LittleAction)stack.firstElement();		
	}
	public void removeFirstAnimationActionFromList(){
		
		System.out.println("Sacada accion");
		stack.remove(0);	
	}
	public boolean hasActions(){
		
		//Indica se quedan acciones en la cola.
		return (!stack.isEmpty());
	}
}
