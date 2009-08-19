/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.bitsonfire.perfectday.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *  Localización de la ayuda. Indentifica la pantalla donde está el usuario
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class Location implements Serializable{

	
    private int id;
    private String title;
    private Set<Context> contexts;
    
    
	/**
	 * 
	 */
	public Location() {
		super();
		this.contexts = new HashSet<Context>();
	}
	


	/**
	 * @param id
	 * @param title
	 */
	public Location(int id, String title) {
		super();
		this.id = id;
		this.title = title;
		this.contexts = new HashSet<Context>();
	}



	/**
	 * @param id
	 * @param title
	 * @param contexts
	 */
	public Location(int id, String title, Set<Context> contexts) {
		super();
		this.id = id;
		this.title = title;
		this.contexts = contexts;
	}


	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}


	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}


	/**
	 * @return the contexts
	 */
	public Set<Context> getContexts() {
		return contexts;
	}


	/**
	 * @param contexts the contexts to set
	 */
	public void setContexts(Set<Context> contexts) {
		this.contexts = contexts;
	}



	public Context getContext(int context) {
		for (Context c : this.contexts) {
			if(c.getId()==context)
				return c;
		}
		throw new IndexOutOfBoundsException("Context no exist in this location["+this.id+"]");
	}

    @Override
    public String toString() {
        return this.title;
    }



	
}
