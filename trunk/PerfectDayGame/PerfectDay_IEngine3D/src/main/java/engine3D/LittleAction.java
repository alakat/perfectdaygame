package engine3D;

public class LittleAction {

	//Variables
	protected String nameObjectInWorld;
	protected int action;
	protected int value1;
	protected int value2;
	protected int value3;
	protected float value4;
	
	//Cosntructor
	public LittleAction(String nameObjectInWorld, int action, int value1, int value2, int value3, float value4){
	
		//Tomamos los valores.
		this.nameObjectInWorld = nameObjectInWorld;
		this.action = action;
		this.value1 = value1;
		this.value2 = value2;
		this.value3 = value3;
		this.value4 = value4;
	}	
}
