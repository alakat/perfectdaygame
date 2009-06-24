package engine3D;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class EngineLog {
	
	//----------------------------------------
    //-    VARIABLES GLOBALES DE LA CLASE    -
    //----------------------------------------
	
	static private Calendar calendar;
	private BufferedWriter bw;
    //-----------------------
    //-    CONSTRUCTORES    -
    //-----------------------
	public EngineLog(String fileName){
		
		//Preparamos un objeto fichero para escribir en él.
		try
		{
			bw = new BufferedWriter(new FileWriter(fileName));
		    //Obtenemos del sistema una referencia al objeto calendar.
			calendar = Calendar.getInstance();
		}
		catch (IOException ioe)
		{
			System.out.println("Error iniciando archivo log del engine3D: "+ioe.getMessage());
		}
	}
    //------------------------------
    //-    METODOS DE INSTANCIA    -
    //------------------------------
	private String time(){
		
		String date = "*";
		//Obtienes los valores de tiempo actuales en un string.	
		date = date+standType(calendar.get(calendar.DAY_OF_MONTH));
		date = date+"-"+standType(calendar.get(calendar.MONTH));
		date = date+"-"+standType(calendar.get(calendar.YEAR));
		date = date+" ["+standType(calendar.get(calendar.HOUR_OF_DAY));
		date = date+":"+standType(calendar.get(calendar.MINUTE));
		date = date+":"+standType(calendar.get(calendar.SECOND))+"] ";			
		return date;
	}
	private String standType(int value){
		
		//Convierte valores del menores que 10 (ej: 9) al modo 0x (ejemplo 09) y los
		//devuelve como Strings.
		String add = "";
		if (value < 10)
		{
			add = "0";
		}
		return (add+Integer.toString(value));
	}
	public void writeEngineLogWithEmisor(String emisor, String text){
		
		//Añadimos los datos del emisor para saber el objeto que envía el mensaje.
		writeEngineLogAction(time()+"("+emisor+") -> "+text);
	}
	public void writeEngineLog(String text){
		
		writeEngineLogAction(time()+" -> "+text);
	}
	private void writeEngineLogAction(String text){
		
		//Intentamos abrirlo
		try
		{			
			bw.write(text);
			bw.newLine();
			bw.flush();
		}
		catch (IOException ioe)
		{
			System.out.println("Error escribiendo el archivo log del engine3D: "+ioe.getMessage());
			closeEngineLog();
		}
	}
	public void closeEngineLog(){
		
		try
		{			
			bw.flush();
			bw.close();
		}
		catch (IOException ioe)
		{
			System.out.println("Error cerrando log del engine3D: "+ioe.getMessage());
		}		
	}
}
