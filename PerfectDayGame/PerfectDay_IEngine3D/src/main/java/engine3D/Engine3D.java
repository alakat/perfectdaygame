package engine3D;

import com.threed.jpct.*;
import com.threed.jpct.util.*;

import java.io.*;

import java.awt.event.*;
import java.awt.image.*;

import java.util.Vector;

import java.awt.*;
import java.io.File;
import java.awt.Color;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.Timer;

public class Engine3D extends Thread {
	
	//--------------------------------------------------
    //-    CONSTANTES GLOBALES PUBLICAS DE LA CLASE    -
    //--------------------------------------------------

	//Sombras
	public static final int NO_SHADOWS = 0;
	public static final int STATIC_SHADOWS = 1;
	public static final int DYNAMIC_SHADOWS_LOW = 256;
	public static final int DYNAMIC_SHADOWS_MEDIUM = 512;
	public static final int DYNAMIC_SHADOWS_HIGH = 1024;
	
	//Modos del framebuffer
	public static final int SAMPLINGMODE_GL_AA_2X = 20;
	public static final int SAMPLINGMODE_GL_AA_4X = 40;
	public static final int SAMPLINGMODE_HARDWARE_ONLY = 0;
	public static final int SAMPLINGMODE_NORMAL = 0;
	public static final int SAMPLINGMODE_OGSS = 1;
	public static final int SAMPLINGMODE_OGSS_FAST = 3;
	public static final int SAMPLINGMODE_OGUS =	2;	
	//direcciones
	public static final int DIR_NORTE = 90;
	public static final int DIR_NORESTE = 45;
	public static final int DIR_ESTE = 0;
	public static final int DIR_SURESTE = 315;
	public static final int DIR_SUR = 270;
	public static final int DIR_SUROESTE = 225;
	public static final int DIR_OESTE = 180;
	public static final int DIR_NOROESTE = 135;	
        //direcciones en grados
	public static final int DEG_NORTE = 1;
	public static final int DEG_NORESTE = 2;
	public static final int DEG_ESTE = 3;
	public static final int DEG_SURESTE = 4;
	public static final int DEG_SUR = 5;
	public static final int DEG_SUROESTE = 6;
	public static final int DEG_OESTE = 7;
	public static final int DEG_NOROESTE = 8;
	//Tipos de animaciones
	public static final int ANIM_STAND = 0;
	public static final int ANIM_WALK = 1;
	public static final int ANIM_ATACK1 = 2;
	public static final int ANIM_ATACK2 = 3;
	public static final int ANIM_DEFEND = 4;
	public static final int ANIM_DEAD = 5;
	//Tipos de �rdenes.
	public static final int ACTION_MOVE_CHARACTER = 20;
	public static final int ACTION_ANIMATE_CHARACTER = 21;	
	//Loop en animacion para colas.
	public static final int MODE_NOT_LOOP = 0;
	public static final int MODE_NOT_LOOP_NOT_WAIT = 1;
	public static final int MODE_LOOP = 2;
	public static final int MODE_LOOP_NOT_WAIT = 3;
	//Animaciones
	public static final boolean LOOP = true;
	public static final boolean NOT_LOOP = false;
	//C�mara
	public static final float MAX_LOW_POSITION_CAMERA = 1.25f;
        //Objetos fijos.
        public static final String GRP_FIELD_MOV = "celda.GIF";
        public static final String GRP_MINI_SELECTED = "celda.GIF";        
        public static final String SIGNAL_MESH = "mSignal.3DS";
        //Otros
        public static final float GROUND_LEVEL = 0f;
        public static final float SPEED_RUN = 0.025f;
        public static final int SPEED_TURN = 10;
		
	//--------------------------------------------------
    //-    CONSTANTES GLOBALES PRIVADAS DE LA CLASE    -
    //--------------------------------------------------
	static final String objetoEmisor = "Engine3D";		//Para identificar quien env�a informaci�n al Log.
	static final String LOGFILENAME = "eLog.txt";		//Nombre del archivo log creado por el engine.
	static final Color colorWireFrame = Color.GREEN;	//Color del alambre en caso de renderizar en modo wireFrame
	static final int defaultFPS = 35;					//Valor de refresco por defecto si no se especifica ninguno.
	static final int minimumSleepValue = 5;				//Intervalo m�nimo del sleep dell thread antes de ponerlo a 100% de rendimiento.
	static final int defaultMaxPoly = 50000;			//M�xima cantidad de pol�gonos a renderizar por defecto.
	static final float animationSpeed = 0.01f;			//Velocidad de animaci�n de los modelos MD2.
	static final int maxAnimationSequences = 30;		//M�ximas secuencias de animaci�n en cada archivo MD2 a cargar.
    static final long microsegundo = 1000000;			//Valor fijo para facilidad de modificaci�n si hace falta.
    static final long milisegundo = 1000;				//Valor fijo para facilidad de modificaci�n si hace falta.
	
    //----------------------------------------
    //-    VARIABLES GLOBALES DE LA CLASE    -
    //----------------------------------------
    private World world;				//El mundo a renderizar por el engine.
    private TextureManager texMan;		//El gestor de texturas del engine.
    private FrameBuffer buffer;			//El frame buffer donde renderizar el mundo.
    private Canvas canvas;				//El canvas donde el frame buffer ser� pintado.
    private int fpsValue;				//Valor en FPS en que queremos que trabaje el engine3D.
    private long refreshTime; 			//Tiempo a esperar antes de pintar el siguiente frame.
    private boolean renderIt; 			//Indica si renderizamos o no las escens.
    private boolean closingEngine; 		//Valor de seguridad usando cuando se est� cerrando el engine.
    private boolean motor3DDestroyed; 	//Indica si liberamos el motor 3d y finalizamos la ejecuci�n de la hebra.
    private EngineLog eLog;				//Instancia log para informar sobre engine.
    private boolean wireframe; 			//Indicador de si se ha de renderizar en modo wireframe.
    private Texture numbers;			//Textura para los n�meros que pintan el framerate.
    private boolean showFPS = false;	//Variable que indica si se muestran los frames por pantalla y los n�mero de pol�gonos.
    private int triangles;				//Cantidad de tri�ngulos renderizados en el mundo en cada momento.
    private PaintListener paintListener;//Instancia PaintListener que se a�adir� el buffer para controlar el tiempo en generar renders del world.
    
    //Variables para sombras.
    protected int shadowMode;
    protected boolean usingDinamicShadows;
    protected boolean usingStaticShadows;
    //Variables para sombras din�micas.
    private Projector projector; 
    private ShadowHelper shadowHelper;  
    
    //Manejadores
    private HandlObj handlObj;  		//Manejador de objetos del mundo3D
    private HandlEngine handlEngine3D; 	//Manejador del engine3D, que har� de interfaz con el juego.
    private MouseListenerEngine3D mouseListener;	//Listener del rat�n dentro del engine 3D    
    private ObjectManager oManager;		//Manejador de almacenamiento de texturas y mallas.
    private AnimationStack animStack;	//Cola de animaciones.
    private DiskLoader diskLoader;		//Manejador de carga de archivos.
    //Ojo!! cambiar la posici�n inicial conlleva cambiar rotCamValue, que indica el �ngulo incial
    //entre la c�mara y el objetivo de la c�mara.
    protected float posCamX = 5;
    protected float posCamY = -7;
    protected float posCamZ = 0;
    protected float lookX = 5;
    protected float lookY = 0;
    protected float lookZ = 5;
    protected boolean camHasMoved;
    protected float rotCamValue = 270;
    //Valores m�ximos que admitir� la c�mara al desplazarse por el world.
    protected int maxXCamPos;
    protected int maxYCamPos;
    protected int maxZCamPos;    
    //Posici�n guardada del rat�n para realizar acci�n
    protected int mousePosX;
    protected int mousePosY;
    //Posici�n del rat�n en cada momento del juego (en constante cambio, para trabajar con una posici�n
    //concreta, salvalos estas en mousePosX o mousePosY.
    protected int mPosRX;
    protected int mPosRY;
    //Instancias para apuntar con el rat�n a un objeto del mapa.
    protected int poligonIDAimedFromMouse;
    protected Object3D object3DIDAimendFromMouse;
    protected String lastObjectAimed;
    //Instancias para el icono del rat�n.    
    private Object3D cursorMouse;
    private float offsettRot;

    //-----------------------
    //-    CONSTRUCTOR      -
    //-----------------------  
    public Engine3D(int width, int height, int FPS, int shadowsType, int frameBufferMode){
    	
        //Las variables de entrada indican el tama�o de la zona de dibujado.        
        //Creamos la hebra llamando al padre.
        super("hebraMotor3D");    
        //Guardamos el dato de FPS que queremos obtener.
        fpsValue = FPS;
        shadowMode = shadowsType;
        if ((shadowMode == Engine3D.DYNAMIC_SHADOWS_LOW)||(shadowMode == Engine3D.DYNAMIC_SHADOWS_MEDIUM)||(shadowMode == Engine3D.DYNAMIC_SHADOWS_HIGH))
        {
        	//Indicamos que estamos usando sombras din�micas.
        	usingDinamicShadows = true;
        }
        else if (shadowMode == Engine3D.STATIC_SHADOWS)
        {
        	//Indicamos que estamos usando sombras est�ticas
        	usingStaticShadows = true;
        }
        //Iniciamos el refresco a 0 y al empezar a funcionar el motor3D se estabilizar�
        //en el valor que convenga para alcanzar los FPS indicados por argumento.
        refreshTime = 0;        
        //Valores de inicio del objeto seleccionado por el rat�n
        poligonIDAimedFromMouse = -1;
        object3DIDAimendFromMouse = null;                
        //Iniciamos la configuraci�n del engine.
        initEngine3D();        
        //Creamos la instancia log para enviar por archivo informaci�n del engine.
        eLog = new EngineLog(LOGFILENAME);
        //Informamos.
        eLog.writeEngineLogWithEmisor(objetoEmisor,"Hebra del engine3D creada.");       
        //Creamos las instancias del mundo 3D
        world = new World();
        eLog.writeEngineLogWithEmisor(objetoEmisor,"Instancia WORLD del mundo virtual creada.");
        //Obtenemos la instancia del gestor de texturas.
        texMan = TextureManager.getInstance();
        eLog.writeEngineLogWithEmisor(objetoEmisor,"Gestor de texturas instanciado.");
        //A�adimos m�xima luz por defecto.
        world.setAmbientLight(255, 255, 255);        
        eLog.writeEngineLogWithEmisor(objetoEmisor,"Luz ambiental definida, valores m�ximos.");       
        //Creamos el FrameBuffer (Ojo con el orden de alto y ancho, debe corresponderse con el canvas
        buffer = new FrameBuffer(width, height, frameBufferMode);     
        //Informamos sobre el modo de renderizado.
        String modeFrameBuffer = getStringFromRenderMethod(frameBufferMode);
        eLog.writeEngineLogWithEmisor(objetoEmisor,"FrameBuffer creado y configurado para OPENGL en modo: "+modeFrameBuffer+".");
        //Creamos el objeto canvas para renderizar en OPENGL. 
        eLog.writeEngineLogWithEmisor(objetoEmisor,"Desactivada renderizaci�n por software.");
        buffer.disableRenderer(IRenderer.RENDERER_SOFTWARE);
        
        //Si usamos sombras din�micas preparamos los objetos que las usan
        if (usingDinamicShadows)
        {
        	projector = new Projector();
        	Config.glShadowZBias = 0.0f;
        	//Vemos la calidad de las sombras.
        	switch(shadowMode)
        	{
        		case (Engine3D.DYNAMIC_SHADOWS_LOW): 	shadowHelper = new ShadowHelper(world, buffer, projector, Engine3D.DYNAMIC_SHADOWS_LOW);
        												break;
        		case (Engine3D.DYNAMIC_SHADOWS_MEDIUM): shadowHelper = new ShadowHelper(world, buffer, projector, Engine3D.DYNAMIC_SHADOWS_MEDIUM);
        												break;
        		case (Engine3D.DYNAMIC_SHADOWS_HIGH): 	shadowHelper = new ShadowHelper(world, buffer, projector, Engine3D.DYNAMIC_SHADOWS_HIGH);
        												break;
        	}
    		shadowHelper.setFiltering(true);
    		shadowHelper.setAmbientLight(new Color(150,150,150));
    		eLog.writeEngineLogWithEmisor(objetoEmisor,"Sombras din�micas iniciadas.");
        }               
        eLog.writeEngineLogWithEmisor(objetoEmisor,"Canvas asociado al FrameBuffer creado.");
        canvas = buffer.enableGLCanvasRenderer();
        //Asociamos el Listener que nos calcular� las FPS con precisi�n para ajustar lso tiempos de espera del
        //bucle principal del engine3D.
        paintListener = new PaintListener();
        //Lo asociamos al buffer y lo activamos
        buffer.setPaintListener(paintListener);
        buffer.setPaintListenerState(true);
        //informamos
        eLog.writeEngineLogWithEmisor(objetoEmisor,"PaintListener creado, asociado a buffer y activado.");
        //Creamos el manejador de texturas y mallas.
        oManager = new ObjectManager(eLog);
        eLog.writeEngineLogWithEmisor(objetoEmisor,"Manejador de almacenamiento de texturas y mallas creado.");
        //Creamos el manejador de objetos para nuestro mundo virtual.       
        handlObj = new HandlObj(this,eLog);
        eLog.writeEngineLogWithEmisor(objetoEmisor,"Manejador de objetos del mundo virtual creado.");
        //Creamos el manejador del engine para trabajar desde la aplicaci�n us�ndo dicho
        handlEngine3D = new HandlEngine(this);  
        eLog.writeEngineLogWithEmisor(objetoEmisor,"Manjeador del engine3D creado.");
        //Creamos la cola de animaciones del engine.
        animStack = new AnimationStack();
        eLog.writeEngineLogWithEmisor(objetoEmisor,"Cola de animaciones y acciones del engine3D creado.");
        mouseListener = new MouseListenerEngine3D(this);
        eLog.writeEngineLogWithEmisor(objetoEmisor,"Listener de rat�n creado para el engine3D.");
        diskLoader = new DiskLoader(this);
        eLog.writeEngineLogWithEmisor(objetoEmisor,"Cargador de archivos del engine3D creado.");
        eLog.writeEngineLogWithEmisor(objetoEmisor,"Listos para arrancar hebra Engine3D."); 
    }
    //-------------------------------------------------------
    //-    METODO DE EJECUCION Y DESTRUCCION DE LA HEBRA    -
    //-------------------------------------------------------
    public void run(){
        
    	
    	//Indicamos que esta va a ser la hebra principal del engine3D (y del renderizador de JPCT)
    	World.setDefaultThread(Thread.currentThread());
    	//Optimizamos el acceso al buffer.
        buffer.optimizeBufferAccess();    	
        //Iniciamos las variables de control
    	renderIt = false;
        motor3DDestroyed = false;
        closingEngine = false;
        //Iniciamos la variable de cuenteo de tri�ngulos renderizados
        triangles = 0;        
        //Informamos v�a log        
        eLog.writeEngineLogWithEmisor(objetoEmisor,"EJECUTANDO HEBRA ENGINE3D (RUN).");
        //Posicionamos la c�mara virtual.
        world.getCamera().setPosition(posCamX, posCamY, posCamZ);
    	world.getCamera().lookAt(new SimpleVector(lookX, lookY, lookZ)); 
    	//Variable del personaje que est� ejecutando una acci�n en este momento.
    	//Nos servir� para ver cuando termina y poder ejecutar otra acci�n.
    	Character3D character = null;
    	
    	//Si tenemos activas las sombras din�micas configuramos el modo, luz ambienta y el proyector.
    	if (usingDinamicShadows)
    	{
    		shadowHelper.setCullingMode(false);
        	shadowHelper.setAmbientLight(new Color(150,150,150));
        	projector.setPosition(4.5f,-25,-18);
        	projector.lookAt(new SimpleVector(4.5f,0,5));  
            projector.setFOV(0.5f);
    		projector.setYFOV(0.5f);
    	}    				
    	//Bucle de renderizaci�n de la hebra. Solo renderizamos si se indica con "renderIt".
    	//Si el engine3D est� destruido (en caso de enviar �rden de finalizar su uso), no se renderiza nada
    	//para evitar errores.  	   	   	
        while (!motor3DDestroyed)
        {
            //Mientras la variable renderizar est� a true, renderizamos
            while (renderIt)
            {
            	//Vemos si se ha movido la c�mara
            	if (camHasMoved)
            	{
            		world.getCamera().setPosition(posCamX, posCamY, posCamZ);
            		world.getCamera().lookAt(new SimpleVector(lookX, lookY, lookZ));
            		camHasMoved = false;
            	}           	           	
            	//handlEngine3D.rotCamera(true, 0.5f);
            	
            	//Renderizamos la escena actual            	
                renderScene();  
                
        		//fijamos la posici�n del rat�n.
        	    mousePosX = mPosRX;
        	    mousePosY = mPosRY;
        	    //Obtenemos el nombre del objeto al que apunta el rat�n.
            	String lastObjectAimed = get3DObjectFromMouse(mousePosX,mousePosY);    
                //Rotamos el gr�fico del rat�n si se ha asignado uno
            	if (cursorMouse != null)
            	{
                    offsettRot = offsettRot + 0.1f;
                    cursorMouse.setRotationMatrix(new Matrix());
                    cursorMouse.rotateY(offsettRot);
            	}
                //Animamos aquellos objetos que necesiten ser animados entre frame y frame.
                handlObj.incrementCharacterAnimations(animationSpeed);
                handlObj.incrementAnimatedObjectAnimations(animationSpeed);
                
                
                //Vemos la cola de animaciones.
                character = actualizeAnimationActions(character);
                 
                
                //Obtenemos el tiempo que se tarda en renderizar una escena.
                long timeToRenderScene = paintListener.getTimeToDrawAImage();
                //Vemos lo que se tardar�a en renderizar las que se piden.
                long timeToRenderASecondOfScenes = timeToRenderScene * fpsValue;
                //Ojo, el valor est� en nanosegundos, lo pasamos a milisegundos en formato long.
                long totalRefreshTime = timeToRenderASecondOfScenes / microsegundo;
                //Tenemos el tiempo que hay que esperar para renderizar en un segundo fpsValue animaciones, pero
                //esa espera es para renderizarlas todas, no para cada una, as� que obtenemos la parte que hay que esperar
                //para renderizar todas las escenas pedidas en fpsValue en el tiempo suficiente para que entre todas duren 1 segundo.
                //SEGUIMOS TRABAJANDO EN MILISEGUNDOS:                
                refreshTime = ((milisegundo - totalRefreshTime)/fpsValue);               
                //Si vamos sobrados de recursos le quitamos algo de tiempo ya que en este c�lculo no hemos tenido en cuenta el tiempo que se
                //tarda en pasar la imagen del buffer al canvas y repintar el canvas.
                if (refreshTime >= minimumSleepValue)
                {	
                	//A�adimos velocidad si estamos justos de frames.
                	refreshTime = refreshTime - minimumSleepValue;
                }
                else
                {
                	//Esto lo ponemos por seguridad y por mejorar el rendimiento si est� a 2 o 3.
                	refreshTime = 0;
                }           
                //Esperamos un poco antes de volver a pintar la escena, as� no sobrecargamos
                //el microprocesador con esta hebra.
                try
                {
                    Thread.sleep(refreshTime);
                }
                catch (InterruptedException e) 
                {
                    System.out.println("Error en c�lculo y aplicaci�n del tiempo refresco en el engine3D : "+e.getMessage());
                    eLog.writeEngineLogWithEmisor(objetoEmisor, "Error durmiendo la hebra del engine3D.");
                }                
            }
        }
        //Saldremos del bucle cuando se indique que se ha destruido el engine3D.
        //Escribimos en el log la operaci�n.
        eLog.writeEngineLog("Engine3D cerrado.");
        //Cerramos el log del engine3D.
        eLog.closeEngineLog();
        //Al llegar a este punto salimos de la hebra.
    }
    public void destroyEngine3D(){
        
    	//Indicamos que se est� cerrando el motor para inhabilitar "setRenderizar" y que no se pueda intentar
    	//renderizar nada mientras se destruye el motor.    	
    	eLog.writeEngineLogWithEmisor(objetoEmisor, "Recogida petici�n de cerrar engine3D. PROCESADO.");
    	closingEngine = true;
    	//Indicamos que deje de renderizar.
    	renderIt = false;
    	//Liberamos el b�fer.
        if (buffer != null)
        {
            buffer.disableRenderer(buffer.getSamplingMode());
        	buffer.dispose();
        }
        //Indicamos que se ha destruido para cerrar la hebra pasa salir de la ejecuci�n.
        motor3DDestroyed = true;
     }   
    //-------------------------------------
    //-    METODOS PRIVADOS DEL ENGINE    -
    //-------------------------------------       
    private String getStringFromRenderMethod(int mode){
    	
    	//Este m�todo devuelve en formato de cadena de texto el modo de framebuffer pasado por variable.
    	String modeIntoString = "<< modo no identificado >>";
    	switch (mode)
    	{
    		case FrameBuffer.SAMPLINGMODE_GL_AA_2X:			modeIntoString = "SAMPLINGMODE_GL_AA_2X";break;	
    		case FrameBuffer.SAMPLINGMODE_GL_AA_4X:			modeIntoString = "SAMPLINGMODE_GL_AA_4X";break;	
    		case FrameBuffer.SAMPLINGMODE_HARDWARE_ONLY:	modeIntoString = "SAMPLINGMODE_HARDWARE_ONLY (NORMAL)";break;		
    		case FrameBuffer.SAMPLINGMODE_OGSS:				modeIntoString = "SAMPLINGMODE_OGSS";break;	
    		case FrameBuffer.SAMPLINGMODE_OGSS_FAST:		modeIntoString = "SAMPLINGMODE_OGSS_FAST";break;	
    		case FrameBuffer.SAMPLINGMODE_OGUS:				modeIntoString = "SAMPLINGMODE_OGUS";break;	    	
    	}
    	return modeIntoString;
    }
    private void initEngine3D(){
    	
    	//Iniciamos los valores del engine
    	
        //Indicamos m�x pol�gonos a representar, en caso de superar esta cifra, no ser�n renderizados los sobrantes.
        Config.maxPolysVisible = defaultMaxPoly;  
        //Indicamos que cargaremos como mucho las "maxAnimationSequences" de un archivo MD2. El resto
        //ser�n descartadas.
        Config.maxAnimationSubSequences = maxAnimationSequences;
        //Aplicamos un filtro trilineal para difuminar las im�genes con la
        //lejan�a y dar mejor aspecto.
        Config.glTrilinear = true;
        //distancia hasta la que se renderizan objetos.
        Config.farPlane = 4000;
        //Indicamos a JPCT que solo nos muestre los errores y avisos.
        Logger.setLogLevel(2);
        
        
    }
    private void renderScene(){
        
    	synchronized (world)
    	{
    		//En caso de renderizar vemos que est� todo correcto.
            if ((buffer != null)&&(world != null)&(canvas != null))
            {   
            	//Lo primero es tratar el tema de las sombras.
            	if (usingDinamicShadows)
            	{
            		//Las sombras son din�micas. Hay que renderizar de una determinada forma.
            		//Primero quitamos los signals del mapa para que no se almacena su sombra.
            		handlObj.hiddenSignalsToRender();
            		//Actualizamos en mapa de sombras.
            		shadowHelper.updateShadowMap();
            		//Limpiamos el buffer
            		buffer.clear(java.awt.Color.BLACK);
            		//Volvemos a colocar los signals para que se rendericen en la segunda pasada.
            		handlObj.visibleSignalsToRender();
                    //Dibujamos la escena con sombras din�micas (Excepto para los signals que los sacamos
            		//antes de hacer el mapa de sombras).
                    shadowHelper.drawScene();   		
            	}
            	else
            	{
            		//No hay sombras, renderizamos en forma normal.Primero limpiamos el buffer
            		buffer.clear(java.awt.Color.GRAY);
            		//Ahora renderizamos la escena en modo normal o wireframe, seg�n se haya pedido.
            		world.renderScene(buffer);
                    if (!wireframe)
                    {
                    	world.draw(buffer);
                    }
                    else
                    {
                    	world.drawWireframe(buffer, colorWireFrame);
                    }                                 
            	}
            	//Ahora sobre la escena ya pintada, pintamos la informaci�n de los fps si asi se pide.    
                if (showFPS)
                {
                  	 //Vemos si hay que mejorar el valor de refresco en funci�n de las
                  	 //FPS que estamos obteniendo.
                  	 //refreshTime = paintListener.recomendedRefreshTime(fpsValue, refreshTime);               	  
                  	 //Finalmente, mostramos los FPS y la cantidad de tri�ngulos renderizados en cada frame.
                  	 blitNumber(paintListener.getFPS(), 10, 10);
                  	 blitNumber(triangles, 10, 20);    
                  	 blitNumber(handlEngine3D.mouseCellPos[0], 10, 30); 
                  	 blitNumber(handlEngine3D.mouseCellPos[1],30, 30); 
                  	 blitNumber(this.poligonIDAimedFromMouse, 10,40);
                  	 if (this.object3DIDAimendFromMouse != null)
                  	 {
                      	 blitNumber(this.object3DIDAimendFromMouse.getID(), 10,50);
                  	 }
                }       
                //Finalmente, mostramos el buffer.
                buffer.update();            
                buffer.displayGLOnly();
                canvas.repaint();  
            }
    	}        
    }
    private String get3DObjectFromMouse(int mousePositionX, int mousePositionY){
    	
    	//OJO!!! Hay que hacer esta llamada despu�s de llamar a renderScene(), ya que necesita generar la escena
    	//para acceder a la lista de vertices visibles.    	
    	//OJO!!! SI modificamos el sampling del buffer hay que modificar esto.
    	String object3DName = null;
    	//Devolveremos el nombre en el mundo virtual del objecto seleccionado.
    	//Trazamos un rayo de cruce.
    	SimpleVector ray = Interact2D.reproject2D3D(world.getCamera(), buffer, mousePositionX, mousePositionY);
    	//Vemos donde se cruza el rayon con un pol�gono en el mundo 3D (No incluimos los que no se pueden seleccionar
    	//como por ejemplo las se�ales del rat�n.
    	int[] res = Interact2D.pickPolygon(world.getVisibilityList(), ray, Interact2D.EXCLUDE_NOT_SELECTABLE);
    	//Vemos si hemos cogido algo.
    	if (res!=null)
    	{    		
    	   //Obtenemos el objeto que se curza con el rayo el en mundo.
    	   Object3D pickedObj = world.getObject(Interact2D.getObjectID(res));    	   
    	   //Obtenemos el alias del objeto en el mundo.
    	   object3DName = handlObj.getNameInWorldFromObject(pickedObj);
    	   //Tambi�n guardaremos el id del pol�gono y del objeto para posicionar la se�al del rat�n
    	   //en el entorno 3D. (para actualizar los valores y saber d�nde apuntamos)    	   
    	   poligonIDAimedFromMouse = Interact2D.getPolygonID(res);
    	   object3DIDAimendFromMouse = pickedObj;
    	}  
    	else
    	{
    		//Indicamos que no apunta a nada
    		poligonIDAimedFromMouse = -1;
    		object3DIDAimendFromMouse = null;
    	}
    	//Devolvemos el resultado de la operaci�n.
    	return object3DName;
    }    
    private void blitNumber(int number, int x, int y) {
       
    	//Coge los n�meros pintados en bitmaps en la textura "numbers" y pinta el valor indicado en la posici�n
    	//de pantalla referida por los argumentos x e y.
    	if (numbers != null) 
    	{
    		//Convertimos el n�mero pasado por argumento de entrada en una cadena de texto.
    		String sNum = Integer.toString(number);
    		
    		for (int i=0; i<sNum.length(); i++)
    		{
    			//Cogemos cada n�mero en formato de char.
    			char cNum=sNum.charAt(i);
    			//Lo convertimso a valor num�rico.
    			int iNum=cNum-48;
    			//Lo pintamos en el buffer recortando dicho n�mero de su referente en la textura "numbers".
    			buffer.blit(numbers, iNum*5, 0, x, y, 5, 9, FrameBuffer.TRANSPARENT_BLITTING);
    			//Actualizamos el valor de X para poner los n�meros seguidos.
    			x+=5;
    		}
    	}
    }
    private Character3D actualizeAnimationActions(Character3D lastCharacterMoved){
    	
    	Character3D lastMoved = lastCharacterMoved;
    	//Se ha realizado ya alguna animaci�n sobre un personaje.
		//Vemos si ha terminado la animacion.
    	if ((lastCharacterMoved == null)||((!lastCharacterMoved.isDoingPrimaryAnimation())&&(!lastCharacterMoved.isMoving())))
    	{
    		//Comprobamos que haya m�s animaciones que hacer.    		
    		if (animStack.hasActions())
            {
    			if (lastCharacterMoved != null)
    			{
    				System.out.println(Boolean.toString(lastCharacterMoved.isDoingPrimaryAnimation())+"-"+Boolean.toString(lastCharacterMoved.isMoving()));
    			}
    			//Tenemos acciones que hacer, cogemos la primera.
            	LittleAction littleAction = animStack.getNewAnimationAction();
            	//Vemos qui�n la hace.
            	String nameInWorld = littleAction.nameObjectInWorld;
            	int typeAction = littleAction.action;
            	//Vemos el tipo de acci�n que es.
            	if (typeAction == ACTION_ANIMATE_CHARACTER)
            	{
            		//Entonces value1 y value2 indican caracter�sticas del movimiento.
            		if (handlObj.containsAnimatedCharacter(nameInWorld))
                	{
            			if (littleAction.value2 == MODE_LOOP)
                		{
                			handlObj.setCharacterAnimationSequence(nameInWorld, littleAction.value1, true);                 	
                		}
            			else if (littleAction.value2 == MODE_LOOP_NOT_WAIT)
            			{
            				handlObj.setCharacterAnimationSequenceNoWait(nameInWorld, littleAction.value1, true);
            			}
            			else if (littleAction.value2 == MODE_NOT_LOOP)
            			{
            				handlObj.setCharacterAnimationSequence(nameInWorld, littleAction.value1, false);
            			}   
            			else if (littleAction.value2 == MODE_NOT_LOOP_NOT_WAIT)
            			{
            				handlObj.setCharacterAnimationSequenceNoWait(nameInWorld, littleAction.value1, false);
            			}
            			//Indicamos que es el �ltimo que se ha animado.
            			lastMoved = handlObj.getAnimatedCharacterFromMap(littleAction.nameObjectInWorld);
                	}
                	else
                	{
                		eLog.writeEngineLogWithEmisor(objetoEmisor, "Se est� intentado asignar secuencias de animaci�n a un objeto inexistente: "+ nameInWorld);
                	}
            	}
            	else if (typeAction == ACTION_MOVE_CHARACTER)
            	{
            		//Entonces value1 y value2 y value3 son la direcci�n la velocidad de rotaci�n y la de movimiento
            		//Mueve un objeto animado a una cierta posici�n de destino con una cierta velocidad y una animaci�n concreta.
                	if (handlObj.containsAnimatedCharacter(nameInWorld))
                	{
                		//Primero obtenemos una referencia al objeto.
                		Character3D animObj = handlObj.getAnimatedCharacterFromMap(nameInWorld);
                		//Ya tenemos la referencia.       
                		//Si ya se est� moviendo, ignoramos la orden
                		if (!animObj.isMoving())
                		{
                			animObj.setNewDestiny(littleAction.value1, littleAction.value3, littleAction.value4);
                			//Indicamos la secuencia de animaci�n para el desplazamiento
                			//animObj.setAnimationSequence(seqAnimation, loop);
                		}
            			//Indicamos que es el �ltimo que se ha animado.
            			lastMoved = handlObj.getAnimatedCharacterFromMap(littleAction.nameObjectInWorld);
                	}
                	else
                	{
                		eLog.writeEngineLogWithEmisor(objetoEmisor, "Se est� intentado animar un personaje inexistente: "+ nameInWorld);
                	}
            	}
            	//Eliminamos la acci�n hecha.
            	animStack.removeFirstAnimationActionFromList();
            }
    	}
    	else
    	{
			System.out.println("ni entro");
    	}
    	return lastMoved;
    }
    //------------------------------
    //-    METODOS DE INSTANCIA    -
    //------------------------------
    public DiskLoader getDiskLoader(){
    	
    	return diskLoader;
    }
    public AnimationStack getAnimationStack(){
    	
    	return animStack;
    }
    public ShadowHelper getShadowHelper(){
    	
    	return shadowHelper;
    }
    public Canvas getCanvas(){
        
        //Devuelve una referencia al objeto canvas donde se dibuja el mundo.
        return canvas;
    }
    public World getWorld(){
    	
    	return world;
    }
    public TextureManager getTextureMan(){
    	
    	return texMan;
    }
    public HandlEngine getHandlEngine(){
    	
    	return handlEngine3D;
    }
    public EngineLog getEngineLog(){
    	
    	return eLog;
    }
    public HandlObj getHaldlObj(){
        
        return handlObj;
    }
    public ObjectManager getObjectManager(){
    	
    	return oManager;
    }
    public void setFPSFontsNumberTexture(Texture numTex){
    	
    	numbers = numTex;
    }
    public void setShowFPS(boolean show){
    	
    	showFPS = show;
    }
    public void setRenderizar(boolean valor){
      
    	//Chequeamos que no se est� cerrando el engine, ya que poner renderizar a true
    	//con el engine cerr�ndose puede ser falta.
    	if (!closingEngine)
    	{
    		//Asignamos el valor.
            renderIt = valor;            
    	}
    }
    public void setModeWireframe(boolean valor){
    	
    	wireframe = valor;
    }
    //--------------------------------------------------------------
    //-    METODOS DE INSTANCIA PARA ADD/REMOVE OBJETOS AL MUNDO   -
    //--------------------------------------------------------------
    public void putOnWorld(Object3D object3D){
    	
    	synchronized (world)
    	{
        	//Contamos los tri�ngulos a a�adir.
        	triangles = triangles + object3D.getMesh().getTriangleCount();
        	//Activamos el filtering para el objeto.
        	object3D.setFiltering(Object3D.FILTERING_DISABLED);
        	//Insertamos en el mundo virtual.
        	world.addObject(object3D);
    	}
    }
    public void removeFromWorld(Object3D object3D){
    	
    	synchronized (world)
    	{
    		triangles = triangles - object3D.getMesh().getTriangleCount();
    		world.removeObject(object3D);
    	}
    }
    //----------------------------------------------------
    //-    METODOS DE INSTANCIA PARA LA CAMARA VIRTUAL   -
    //----------------------------------------------------
    public void moveCameraInVectorDir(SimpleVector vectorNorm, float dist){
    	

    	//Vamos a ver si nos salimos del mapa (el look de la c�mara).
    	if ((vectorNorm.x + lookX <= maxXCamPos)&&(vectorNorm.x + lookX >= 0)&&(vectorNorm.z + lookZ <= maxZCamPos)&&(vectorNorm.z + lookZ >= 0))
    	{
    		//Como estamos dentro de los l�mites en los que se puede mover la c�mara, la movemos.
    		synchronized (world)
        	{
        		//Desplazamos.
            	Camera cam = world.getCamera();
            	cam.moveCamera(vectorNorm, dist);
            	//Calculo el desplazamiento hecho.
            	float desplInX = cam.getPosition().x - posCamX;
            	float desplInY = cam.getPosition().y - posCamY;
            	float desplInZ = cam.getPosition().z - posCamZ;
            	//Guardamos los resultados para seguir almacenando la posici�n de la c�mara
            	posCamX = cam.getPosition().x;
            	posCamY = cam.getPosition().y;
            	posCamZ = cam.getPosition().z;
            	//Tambi�n hay que mantener la distancia con el objetivo para movernos igual que "moveCamera".
            	lookX = lookX + desplInX;
            	lookY = lookY + desplInY;
            	lookZ = lookZ + desplInZ;
            	//No Indicamos que se ha movido la c�mara (No hace falta para moveCamera, pero s� para LookAt(SimpleVector)
                
            	//Indicamos que se ha movido la c�mara.
            	camHasMoved = true;
            	//cam.lookAt(new SimpleVector(lockX, lockY, lockZ));
        	}	
    	}    	
    }
    public void moveCamera(float px, float py, float pz){
    	
    	//Asociamos la nueva posici�n de la c�mara.
    	posCamX = px;
    	//es < ya que el eje Y es negativo
    	if (posCamY + py < -1)
    	{
        	posCamY = py;
    	}
    	posCamZ = pz;
    	//Indicamos que se ha movido la c�mara.
    	camHasMoved = true;
    }	
    public void displaceCamera(float dispX, float dispY, float dispZ){
    	
    	//Indicamos que desplazamos la c�mara desde su posici�n actual.
    	//Ojo, La recordar que el eje Y va al rev�s! 
    	//Tambi�n hay que ver si no hemos llegado a la altura m�xima o m�nima permitida por la c�mara.
    	if ((posCamY + dispY < MAX_LOW_POSITION_CAMERA)&&(posCamY + dispY >= maxYCamPos))
    	{
        	posCamY = posCamY + dispY;
    	}
    	posCamZ = posCamZ + dispZ;
    	posCamX = posCamX + dispX;   
    	//Indicamos que se ha movido la c�mara.
    	camHasMoved = true;
    }	
    public float getCamX(){
    	return posCamX;
    }
    public float getCamY(){
    	return posCamY;
    }
    public float getCamZ(){
    	return posCamZ;
    }
    public void rotCamera(boolean dir, float speed){
    	
    	if (dir)
    	{
        	rotCamValue = rotCamValue + speed;
    	}
    	else
    	{
        	rotCamValue = rotCamValue - speed;
    	}
    	float dx2 = (lookX - posCamX)*(lookX - posCamX);
    	float dz2 = (lookZ - posCamZ)*(lookZ - posCamZ);
    	float dist = (float)java.lang.Math.sqrt(dx2+dz2);
    	
    	float radioValue = dist;
    	
    	//float radioValue = radio;
    	//Calculamos la posici�n de la c�mara.
    	//Primero convertimos el �ngulo a radianes.
    	double rad = java.lang.Math.toRadians(rotCamValue);
    	double cosAngle = java.lang.Math.cos(rad);
    	double senAngle = java.lang.Math.sin(rad);
    	float y = (float)senAngle * radioValue;
    	float x = (float)cosAngle * radioValue;
    	//Calculo la distancia entre el objetivo y la c�mara.  	
    	moveCamera(x+lookX, posCamY, y+lookZ);
    	world.getCamera().lookAt(new SimpleVector(lookX,lookY,lookZ));
    	camHasMoved = true;
    }
    public float getRotCamValue(){
    	
    	return rotCamValue;
    }
    //-------------------------------------------------------
    //-    METODOS DE INSTANCIA PARA EL CURSOR DEL RATON    -
    //-------------------------------------------------------
    public Object3D getCursorMouse(){
    	
    	return cursorMouse;
    }
    public void setCursorMouse(String cMouse){
    	
    	cursorMouse = handlEngine3D.getSignalFromMap(cMouse);
        cursorMouse.setRotationMatrix(new Matrix());
        offsettRot = 0.0f;
    }

    public MouseListenerEngine3D getMouseListenerEngine3D(){
    	
    	return mouseListener;
    }
}