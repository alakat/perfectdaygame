/**
 * 
 */
package es.bitsonfire.perfectday.gui;

import java.io.File;
import java.io.FileNotFoundException;

import es.bitsonfire.perfectday.HelperDatabase;
import es.bitsonfire.perfectday.model.ActionHelpInformation;
import es.bitsonfire.perfectday.model.Context;
import es.bitsonfire.perfectday.model.Location;

/**
 * Clase para la administración directa de la base de datos de información de Perferctday. 
 * Interfaz de uso de una GUI.
 * @author Miguel Angel López Montellano (alakat@gmail.com)
 *
 */
public class HelperManager {
	
	private File databaseFile;
	private Location selectedLocation;
	private Context selectedContext;
	private ActionHelpInformation selectedActionHelpInformation;
	private boolean readMode;
	private HelperDatabase database;
	
	/**
	 * @throws FileNotFoundException 
	 * 
	 */
	public HelperManager(String path) throws FileNotFoundException {
		this.databaseFile = new File(path);
		if(!this.databaseFile.exists()){
			throw new FileNotFoundException("HelperInformationNotFound");
		}
        this.database = new HelperDatabase(this.databaseFile);
		refresh();
	}

    /**
	 * @throws FileNotFoundException
	 *
	 */
	public HelperManager(String path, boolean write) throws FileNotFoundException {
		this.databaseFile = new File(path);
        this.database = new HelperDatabase(this.databaseFile);
		refresh();
        if(write)
            this.save();
	}


	/**
	 * @return the databaseFile
	 */
	public File getDatabaseFile() {
		return databaseFile;
	}

	/**
	 * @param databaseFile the databaseFile to set
	 */
	public void setDatabaseFile(File databaseFile) {
		this.databaseFile = databaseFile;
	}

	/**
	 * @return the selectedLocation
	 */
	public Location getSelectedLocation() {
		return selectedLocation;
	}

	/**
	 * @param selectedLocation the selectedLocation to set
	 */
	public void setSelectedLocation(Location selectedLocation) {
		this.selectedLocation = selectedLocation;
        if(!selectedLocation.getContexts().isEmpty()){
            this.setSelectedContext(selectedLocation.getContexts().iterator().next());
        }else{
            this.selectedContext=null;
            this.selectedActionHelpInformation=null;
        }
	}

	/**
	 * @return the selectedContext
	 */
	public Context getSelectedContext() {
		return selectedContext;
	}

	/**
	 * @param selectedContext the selectedContext to set
	 */
	public void setSelectedContext(Context selectedContext) {
		this.selectedContext = selectedContext;
        if(!selectedContext.getActions().isEmpty()){
            this.setSelectedActionHelpInformation(selectedContext.getActions().get(0));
        }else{
            this.selectedActionHelpInformation=null;
        }
	}

	/**
	 * @return the selectedActionHelpInformation
	 */
	public ActionHelpInformation getSelectedActionHelpInformation() {
		return selectedActionHelpInformation;
	}

	/**
	 * @param selectedActionHelpInformation the selectedActionHelpInformation to set
	 */
	public void setSelectedActionHelpInformation(
			ActionHelpInformation selectedActionHelpInformation) {
		this.selectedActionHelpInformation = selectedActionHelpInformation;
	}

	/**
	 * @return the readMode
	 */
	public boolean isReadMode() {
		return readMode;
	}

	/**
	 * @param readMode the readMode to set
	 */
	public void setReadMode(boolean readMode) {
		this.readMode = readMode;
	}

	/**
	 * @return the database
	 */
	public HelperDatabase getDatabase() {
		return database;
	}

	/**
	 * @param database the database to set
	 */
	public void setDatabase(HelperDatabase database) {
		this.database = database;
	}
	
	/**
	 * Salva la base de datos
	 * @return
	 */
	public boolean save(){
		this.database.writeInformation(databaseFile);
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean refresh(){
		
		this.selectedLocation = null;
		this.selectedContext = null;
		this.selectedActionHelpInformation = null;
		if(!database.getDatabase().getLocations().isEmpty()){
			this.selectedLocation = database.getDatabase().getLocations().iterator().next();
		}
		if(this.selectedLocation!=null && !this.selectedLocation.getContexts().isEmpty())
			this.selectedContext = this.selectedLocation.getContexts().iterator().next();
		if(this.selectedContext!=null && !this.selectedContext.getActions().isEmpty()){
			this.selectedActionHelpInformation = this.selectedContext.getActions().iterator().next();
		}
		return true;			
	}
	
	/**
	 * Añade una localización a la base de datos del sistema
	 * @param location
	 */
	public void addLocation(Location location){
		this.selectedLocation = location;
		this.database.addLocation(location);
		save();
	}
	
	/**
	 * Añade un contexto al sistema
	 * @param c
	 */
	public void addContext(Context c){
		this.selectedContext=c;
        this.database.addContext(this.selectedLocation.getId(), c);
		save();
	}
	
	public void addActionHelpInformation(ActionHelpInformation inf){
		this.database.addActionHelpInformation(selectedLocation.getId(), selectedContext.getId(), inf);
		this.selectedActionHelpInformation=inf;
		save();
	}
	
	/**
	 * elimina la localización seleccionada
	 */
	public void removeLocation(){
		this.database.getDatabase().getLocations().remove(selectedLocation);
		this.refresh();
	}
	
	/**
	 * Elimina el contexto seleccionado
	 */
	public void removeContext(){
		this.selectedLocation.getContexts().remove(selectedContext);
		this.selectedContext = null;
		this.selectedActionHelpInformation = null;
		if(this.selectedLocation!=null && !this.selectedLocation.getContexts().isEmpty())
			this.selectedContext = this.selectedLocation.getContexts().iterator().next();
		if(this.selectedContext!=null && !this.selectedContext.getActions().isEmpty()){
			this.selectedActionHelpInformation = this.selectedContext.getActions().iterator().next();
		}
	}
	
	/**
	 * Elimina la información de la acción.
	 */
	public void removeActionHelperInformation(){
		this.selectedContext.getActions().remove(this.selectedActionHelpInformation);
		this.selectedActionHelpInformation = null;
		if(this.selectedContext!=null && !this.selectedContext.getActions().isEmpty()){
			this.selectedActionHelpInformation = this.selectedContext.getActions().iterator().next();
		}
	}
}
