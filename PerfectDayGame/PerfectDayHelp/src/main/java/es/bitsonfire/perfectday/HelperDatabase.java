/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.bitsonfire.perfectday;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


import es.bitsonfire.perfectday.model.ActionHelpInformation;
import es.bitsonfire.perfectday.model.Context;
import es.bitsonfire.perfectday.model.DataBase;
import es.bitsonfire.perfectday.model.Location;
import java.util.Iterator;

/**
 *  Esta clase contiene todos los datos de ayuda para Perfectday
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class HelperDatabase {

	private DataBase database;
	private boolean newer;
	
	public HelperDatabase() {
		database = new DataBase();
	}
	
	public HelperDatabase(File f){
		if(!f.exists()){
			newer = true;
			database = new DataBase();
		}else{
			ObjectInputStream ois;
			try {
				ois = new ObjectInputStream(new FileInputStream(f));
				database = (DataBase) ois.readObject();
				ois.close();
			} catch (FileNotFoundException e) {
				throw new RuntimeException("Fichero no encontrado");
			} catch (IOException e) {
				throw new RuntimeException("Error en lectura:"+e.getMessage(),e);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Error en casteo:"+e.getMessage(),e);
			}				
		}
	}

	/**
	 * @return the database
	 */
	public DataBase getDatabase() {
		return database;
	}

	/**
	 * @param database the database to set
	 */
	public void setDatabase(DataBase database) {
		this.database = database;
	}

	/**
	 * @return the newer
	 */
	public boolean isNewer() {
		return newer;
	}

	/**
	 * @param newer the newer to set
	 */
	public void setNewer(boolean newer) {
		this.newer = newer;
	}
	
	
	public void writeInformation(File f){
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(database);
			oos.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Fichero no encontrado");
		} catch (IOException e) {
            e.printStackTrace();
			throw new RuntimeException("Error en escritura:"+e.getMessage(),e);
		} 		
	}
	
	/**
	 * 
	 * @param location
	 * @param context
	 * @param action
	 * @return
	 */
	public ActionHelpInformation getInformation(int location, int context, int action){
		return database.getLocation(location).getContext(context).getAction(action);
	}
	/**
	 * 
	 * @param location
	 * @param context
	 * @param action
	 */
	public void addActionHelpInformation( int location, int context,ActionHelpInformation action){
        Context c = this.database.getLocation(location).getContext(context);
		action.setId(getActionID(c));
        c.getActions().add(action);
	}
	/**
	 * 
	 * @param l
	 */
	public void addLocation(Location l){
        l.setId(this.getLocationID());
		this.database.getLocations().add(l);
	}
	/**
	 * 
	 * @param location
	 * @param c
	 */
	public void addContext(int location, Context c){
		Location l = this.database.getLocation(location);
        c.setId(this.getContextID(l));
		l.getContexts().add(c);
	}

    /**
     *
     * @return
     */
    private int getLocationID(){
        return database.getNextLocationID();
    }

    /**
     * 
     * @param l
     * @return
     */
    private int getContextID(Location l){
        int max = -1;
        if(l.getContexts().isEmpty()){
            return 0;
        }else{
            Iterator<Context> i = l.getContexts().iterator();
            Context c=null;
            while (i.hasNext()) {
                c = i.next();
                if(c.getId()>max){
                    max = c.getId();
                }

            }
            return max+1;
        }
    }

    private int getActionID(Context c){
        if(c.getActions().isEmpty()){
            return 0;
        }else{
            int max = -1;
            Iterator<ActionHelpInformation> i =  c.getActions().iterator();
            ActionHelpInformation a=null;
            while (i.hasNext()) {
                a = i.next();
                if(a.getId()>max){
                    max = a.getId();
                }

            }
            return max+1;
        }
    }

    @Override
    public String toString() {
        return "Enciclopedia de Perfect Day";
    }

}
