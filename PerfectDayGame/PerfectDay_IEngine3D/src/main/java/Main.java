import javax.swing.*;
import java.awt.*;
import com.threed.jpct.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.Random;

import engine3D.*;

public class Main extends JFrame implements KeyListener{
     
	String sep;  //para guardar el separador del sistema
	Engine3D myEngine3D;
	DiskLoader diskLoader;
	HandlEngine handlEngine;
	Canvas canvasM3D;	
	AnimationStack animStack;
	
    public Main(){
        
        //Creamos un JFrame para hacer las pruebas.
        super("PerfectDay Engine3D");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        //A�adimos el listener del teclado.
        this.addKeyListener(this);
        //Creamos el objeto engine3D
        myEngine3D = new Engine3D(640, 480, 35, Engine3D.STATIC_SHADOWS, Engine3D.SAMPLINGMODE_NORMAL);
        EngineLog eLog = myEngine3D.getEngineLog();
        handlEngine = myEngine3D.getHandlEngine();
        animStack = myEngine3D.getAnimationStack();
        diskLoader = myEngine3D.getDiskLoader();
        //Cargamos los directorios de archivos.
        diskLoader.LoadTextureDirectory("textures");
        //Cargamos algunos a parte para ponerle un scale distinto.
        diskLoader.LoadModelArchive("models","Tris.md2",0.005f);
        diskLoader.LoadModelArchive("models","stShadow.3ds",0.35f);
        //cargamos el resto.
        diskLoader.LoadModelDirectory("models",1f);



        //Ejecutamos la hebra.
        try
        {		
        	myEngine3D.setPriority(Thread.NORM_PRIORITY);
        	myEngine3D.start();
        	myEngine3D.setModeWireframe(false);       	
        }
        catch (StackOverflowError e)
        {
        	System.out.println("StackOverflowError : "+e.getMessage());
        	eLog.writeEngineLog("Salida anormal del engine3D por StackOverFlowError :"+e.getMessage());
        	myEngine3D.destroyEngine3D();
        	System.exit(0);
        }
        catch (OutOfMemoryError e)
        {
        	System.out.println("OutOfMemoryError : "+e.getMessage());
        	eLog.writeEngineLog("Salida anormal del engine3D por OutOfMemoryError :"+e.getMessage());
        	myEngine3D.destroyEngine3D();
        	System.exit(0);
        }
        catch (Exception e)
        {
        	System.out.println("Salida anormal del engine3D : "+e.getMessage());
        	eLog.writeEngineLog("Salida anormal del engine3D :"+e.getMessage());
        	myEngine3D.destroyEngine3D();
        	System.exit(0);
        }
        canvasM3D = myEngine3D.getCanvas();
        canvasM3D.setFocusable(false);
        this.add(canvasM3D);   
        this.pack();
        this.setVisible(true);
        //*****************************************************
        // 					ZONA  TEST
        //*****************************************************       
        //asignamos los fonts del fps
        handlEngine.setFpsFonts("numbers.jpg");
    	myEngine3D.setShowFPS(true); 
 
    	//int[][] manualMap = {{1,1,1,0,0,1},{1,0,1,0,0,0},{0,1,1,0,0,1},{1,0,0,1,0,1},{1,0,1,1,0,1},{0,0,1,0,1,1}};
        //A�adimos una celda al motor3D
        
    	int mx = 12;
        int my = 12;
        
        int[][] map = new int[mx][my];
        Random num = new Random();
        int valor;
        for (int y = 0; y < my; y++)
        {
        	for (int x = 0; x < mx; x++)
        	{
        		valor = num.nextInt();     		
        		if (((valor%2) == 0)||(valor%3 == 0)||(valor%5 == 0))
        		{
        			map[x][y] = 0;  
        		}
        		else
        		{
        			map[x][y] = 1;  
        		}       	
        	}
        }
        //segunda pasada.
        for (int y = 0; y < my; y++)
        {
        	for (int x = 0; x < mx; x++)
        	{
        		valor = num.nextInt();     		
        		if (map[x][y] == 1)
        		{        			
        			if ((x-1 >= 0)&&( x+1 <map.length)&&(y-1 >= 0)&&( y+1 <map[0].length))
        			{
        				map[x-1][y] = 1;  
        				map[x][y-1] = 1;
        				map[x-1][y-1] = 1;
        			}
        		}     	
        	}
        }
        handlEngine.createNewTerrain(50000);
        String[] terrainsObjs = {"ground.3ds","ground.3ds"};
        String[] terrainsTexs = {"grass.png","rock.png","cell.gif"};
        handlEngine.generateNewTerrainMap(map, terrainsObjs, terrainsTexs, null);
        handlEngine.insertTerrainToWorld("TerrenoPrincipal");
        
        //Test lectura mapa.
        /*
        int mapLoaded[][][] = diskLoader.LoadMapArchive("maps","map.txt","MapTraslator.txt");
        //Mostramos.
        if (mapLoaded != null)
        {
            diskLoader.printTranslateCode();
            myEngine3D.getHandlEngine().createNewTerrain(750000);
    		myEngine3D.getHandlEngine().generateNewTerrainMapTranslationList(mapLoaded, myEngine3D.getDiskLoader().getMapDataList(), myEngine3D.getDiskLoader().getMapDataLineDelimiter());
    		myEngine3D.getHandlEngine().insertTerrainToWorld("TerrenoPrincipal");
        }
        else
        {
        	System.out.println("No se ha cargado el mapa. Error en MAIN");
        }
        */
        
        
        //handlEngine.createNewStaticLight(5, -3, 5, 255, 255, 255, 1, 4);       
        //handlEngine.insertNewStaticModelInWorld("house1.3ds", "house1", new SimpleVector(5,0f,5), 0, new SimpleVector(0,0,0));
        //handlEngine.insertNewStaticModelInWorld("house1.3ds", "house2", new SimpleVector(2,0f,4), 90, new SimpleVector(0,1,0));
        //handlEngine.insertNewStaticModelInWorld("rocket.3ds", "rocket1", new SimpleVector(3,0.1f,3), 0, new SimpleVector(0,0,0));
        handlEngine.insertNewAnimatedCharacterInWorld("Tris.MD2", "tris.jpg", "ogro1", new SimpleVector(3,-0.15f,4), 180, new SimpleVector(0, 1,0));
        //Asociamos una sombra est�tica al modelo.
    	handlEngine.jointStaticShadow("stShadow.3ds", "ogro1", "stShadow.Png", 0.025f, 0.5f);	
        handlEngine.insertNewAnimatedCharacterInWorld("Tris.MD2", "tris.jpg", "ogro2", new SimpleVector(5,-0.15f,7), 180, new SimpleVector(0, 1,0));
        //Trooper tropa1 = new Trooper(myEngine3D, Trooper.NUM_SOLDIERS_2, Trooper.DIR_FACING_NORTE, "Tris.MD2", "tris.jpg", "ogro3", new SimpleVector(3,-0.15f,3));
        //Trooper tropa2 = new Trooper(myEngine3D, Trooper.NUM_SOLDIERS_3, Trooper.DIR_FACING_ESTE, "Tris.MD2", "tris.jpg", "ogro4", new SimpleVector(3,-0.15f,4));
        //Trooper tropa3 = new Trooper(myEngine3D, Trooper.NUM_SOLDIERS_5, Trooper.DIR_FACING_OESTE, "Tris.MD2", "tris.jpg", "ogro5", new SimpleVector(3,-0.15f,5));
        //Trooper tropa4 = new Trooper(myEngine3D, Trooper.NUM_SOLDIERS_6, Trooper.DIR_FACING_SUR, "Tris.MD2", "tris.jpg", "ogro6", new SimpleVector(3,-0.15f,6));

        
        //handlEngine.insertNewAnimatedModelInWorld("molino.MD2", "tris.jpg", "molino", new SimpleVector(7,0.0f,7), 180, new SimpleVector(0, 1,0));
        //Metemos una se�al para el cursor
        handlEngine.insertNewSignal("msignal.3DS", "cursor", "celda.gif", true , -200,-200);
        myEngine3D.setCursorMouse("cursor");
        //*****************************************************
        // 					FIN ZONA  TEST
        //*****************************************************       
        eLog.writeEngineLog("Renderizando");
        handlEngine.setRenderizar(true); 
        //Asociamos un listener del engine3D al canvas.
        myEngine3D.getMouseListenerEngine3D().setCanvasToListen(canvasM3D);       
        //lo activamos.
        myEngine3D.getMouseListenerEngine3D().setListening(true);
        //Asociamos una sombra est�tica al modelo.

        requestFocus();
    }
    public static void main(String[] args){
        
        new Main();
    }
    public void keyPressed(KeyEvent e){
    	
    	switch (e.getKeyCode()) {
    	case KeyEvent.VK_LEFT :testStackAnimationMov(Engine3D.DIR_NORTE);break;
    	case KeyEvent.VK_UP :   testStackAnimationMov(Engine3D.DIR_NORTE);break;
    	case KeyEvent.VK_DOWN : testStackAnimationMov(Engine3D.DIR_SUR);break;
    	case KeyEvent.VK_RIGHT : testStackAnimationMov(Engine3D.DIR_ESTE);break;
    	case KeyEvent.VK_NUMPAD8: testStackAnimationMov(Engine3D.DIR_NORTE);break;
    	case KeyEvent.VK_NUMPAD9: testStackAnimationMov(Engine3D.DIR_NORESTE);break;
    	case KeyEvent.VK_NUMPAD6: testStackAnimationMov(Engine3D.DIR_ESTE);break;
    	case KeyEvent.VK_NUMPAD3: testStackAnimationMov(Engine3D.DIR_SURESTE);break;
    	case KeyEvent.VK_NUMPAD2: testStackAnimationMov(Engine3D.DIR_SUR);break;
    	case KeyEvent.VK_NUMPAD1: testStackAnimationMov(Engine3D.DIR_SUROESTE);break;
    	case KeyEvent.VK_NUMPAD4: testStackAnimationMov(Engine3D.DIR_OESTE);break;
    	case KeyEvent.VK_NUMPAD7: testStackAnimationMov(Engine3D.DIR_NOROESTE);break;
    	} 
    }
    public void testStackAnimationMov(int dir){
    	
    	//Animamos
    	animStack.addAnimationActionToStack("ogro1", 2, Engine3D.MODE_LOOP_NOT_WAIT);
    	animStack.addMoveActionToStack("ogro1", dir, 10, 0.025f);
    	animStack.addAnimationActionToStack("ogro1", 1, Engine3D.MODE_LOOP_NOT_WAIT); 	
    }    
    public void keyReleased(KeyEvent e){ 
    	 
    }
    public void keyTyped(KeyEvent e) {
    		
	}
}