package engine3D;

import java.awt.Canvas;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import com.threed.jpct.Camera;
import com.threed.jpct.SimpleVector;

public class MouseListenerEngine3D implements MouseMotionListener, MouseListener, MouseWheelListener{
	
	//----------------------------------------
    //-    CONSTANTES GLOBALES DE LA CLASE   -
    //----------------------------------------
	private float DISPLACEMENT_SPEED = 0.15f;
	private float ZOOM_SPEED = 0.75f;
	private float ROTATION_SPEED = 2.0f;
	
	//----------------------------------------
    //-    VARIABLES GLOBALES DE LA CLASE    -
	//----------------------------------------
	private Engine3D myEngine3D;
	private boolean listening;
	private boolean mouseIntoScreen;
	private int lastX;
	private int lastY;
	private boolean button1MousePressed;
	private boolean button2MousePressed;
	private boolean button3MousePressed;
	
    //-----------------------
    //-    CONSTRUCTORES    -
    //-----------------------
	public MouseListenerEngine3D(Engine3D engine3d)
	{
		myEngine3D = engine3d;
	}
	
    //------------------------------
    //-    METODOS DE INSTANCIA    -
    //------------------------------
	public void setCanvasToListen(Canvas canvas){
		
		//Asociamos el listener al canvas pasado por argumento.
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		canvas.addMouseWheelListener(this);
		//Indicamos que estamos escuchando
		listening = true;
	}	
	public boolean isListening(){
		
		return listening;
	}
	public void setListening(boolean value){
		
		listening = value;
	}
	public void mouseDragged(MouseEvent mEvent) {
		
		if ((listening)&&(mouseIntoScreen))
		{						
			
			boolean rotDir = false;				//Dirección a rotar, false = Derecha, true = Izquierda.
			int x = mEvent.getX();
			int y = mEvent.getY();
			int dirMov = 0;			//Si == 0 nada, si 1 Este, 2 Oeste, 3 Norte, 4 Sur.

			//Estudiamos la dirección de movimiento.
			if (lastX > x)
			{
				//vamos a la izquierda.		
				dirMov = 2;
			}
			else if (lastX < x)
			{
				//Vamos a la derecha.
				dirMov = 1;
				rotDir = true;
			}
			else if (lastY > y)
			{
				//Vamos hacia arriba.
				dirMov = 3;
			}
			else if (lastY < y)
			{
				//Vamos hacia abajo.
				dirMov = 4;
			}
			//Actualizamos esta posición como última
			lastX = x;
			lastY = y;
			//Ahora estudiamos los casos.
			//Si está presionado el botón derecho sin el izquierdo a la vez, desplazamos.
			if ((!button1MousePressed)&&(button3MousePressed)&&(dirMov != 0))
			{								
				//Tomamos una referencia a la cámara del mundo.
				Camera camInWorld = myEngine3D.getWorld().getCamera();
				//Tomamos una referencia a donde está mirando.
				SimpleVector camMirando = camInWorld.getDirection();
				//El valor Y no lo cambiamos, no debe alterar su posición en ese eje.
				camMirando.y = 0.0f;
				//Estudiamos los casos.
				switch(dirMov)
				{
					case 1:	//Movemos hacia el este, hacia la derecha, hay que girar 90 grados camMirando
							camMirando.rotateY((float)java.lang.Math.PI/2.0f);
							myEngine3D.moveCameraInVectorDir(camMirando, DISPLACEMENT_SPEED);break;
					case 2: //Movemos hacia el oeste, hacia la izquierda, hay que girar 270 grados camMirando
							camMirando.rotateY((float)java.lang.Math.PI/-2.0f);
							myEngine3D.moveCameraInVectorDir(camMirando, DISPLACEMENT_SPEED);break;
							//Movemos hacia arriba, hacia el norte, pero no hacemos nada, ya apunta hacia esa dirección.
					case 3: camMirando.rotateY((float)java.lang.Math.PI);
							myEngine3D.moveCameraInVectorDir(camMirando, DISPLACEMENT_SPEED);break;							
							//Movemos hacia abajo, hacia el sur, hay que invertir el vector camMirando(rotar 180º).
					case 4: myEngine3D.moveCameraInVectorDir(camMirando, DISPLACEMENT_SPEED);break;
				}
				//Realizamos el deszaplamiento.
				
			}
			else if ((button1MousePressed)&&(button3MousePressed))
			{
				//Si tenemos presionados los botones derecho e izquierdo a la vez, rotamos sobre
				//el objetivo de lockAt en redondo.
				myEngine3D.rotCamera(rotDir, ROTATION_SPEED);
			}
			//Actualizamos el cursor igual que si lo hubiésemos movido (posición y tal)
			//Así el gráfico del cursor no se queda parado mientras hacemos drag
			myEngine3D.getHandlEngine().setMouseEvent(mEvent);		
			myEngine3D.getHandlEngine().refreshMouseSignalPosition("cursor");			
		}	
	}
	public void mouseMoved(MouseEvent mEvent) {
	
		if ((listening)&&(mouseIntoScreen))
		{
			myEngine3D.getHandlEngine().setMouseEvent(mEvent);		
			myEngine3D.getHandlEngine().refreshMouseSignalPosition("cursor");	
		}
	}
	public void mouseClicked(MouseEvent mEvent) {
		System.out.println("click");
	}
	public void mouseEntered(MouseEvent mEvent) {
	
		mouseIntoScreen = true;
		//Actualizamos los valores al entrar
		lastX = mEvent.getX();
		lastY = mEvent.getY();
	}
	public void mouseExited(MouseEvent mEvent) {
	
		mouseIntoScreen = false;
	}
	public void mousePressed(MouseEvent mEvent) {
		
		if (listening)
		{
			if (mEvent.getButton() == MouseEvent.BUTTON1)
			{
				this.button1MousePressed = true;
			}
			if (mEvent.getButton() == MouseEvent.BUTTON2)
			{
				this.button2MousePressed = true;
			}
			if (mEvent.getButton() == MouseEvent.BUTTON3)
			{
				this.button3MousePressed = true;
			}
		}
		//Para el drag. Actualizamos aunque no estemos escuchando
		lastX = mEvent.getX();
		lastY = mEvent.getY();
	}
	public void mouseReleased(MouseEvent mEvent) {
	 
		if (mEvent.getButton() == MouseEvent.BUTTON1)
		{
			this.button1MousePressed = false;
		}
		if (mEvent.getButton() == MouseEvent.BUTTON2)
		{
			this.button2MousePressed = false;
		}
		if (mEvent.getButton() == MouseEvent.BUTTON3)
		{
			this.button3MousePressed = false;
		}
	}
	public void mouseWheelMoved(MouseWheelEvent e) {
		
		if ((listening)&&(this.listening))
		{
			if (e.getWheelRotation() > 0)
			{
				myEngine3D.displaceCamera(0.0f, ZOOM_SPEED, 0.0f);
			}
			else
			{

				myEngine3D.displaceCamera(0.0f, -ZOOM_SPEED, 0.0f);
			}
		}
	}
}
