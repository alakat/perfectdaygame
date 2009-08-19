/**
 * 
 */
package es.bitsonfire.perfectday.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Base de datos de ayuda para usuarios. Serializada.
 * @author Miguel Angel López Montellano (alakat@gmail.com)
 *
 */
public class DataBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Set<Location> locations;

	/**
	 * 
	 */
	public DataBase() {
		super();
		this.locations = new HashSet<Location>();
	}

	/**
	 * @return the locations
	 */
	public Set<Location> getLocations() {
		return locations;
	}

	/**
	 * @param locations the locations to set
	 */
	public void setLocations(Set<Location> locations) {
		this.locations = locations;
	}

	/**
	 * 
	 * @param location
	 * @return
	 */
	public Location getLocation(int location) {
		for (Location l : locations) {
			if(l.getId()==location)
				return l;
		}
		throw new IndexOutOfBoundsException("Localización no encontrada");
	}

	
	
}
