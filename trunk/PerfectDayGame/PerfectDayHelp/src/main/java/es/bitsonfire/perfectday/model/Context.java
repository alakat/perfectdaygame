/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.bitsonfire.perfectday.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * Representa un contexto de la pantalla del jugador, por ejemplo la zona
 * reservada a la información de resumen del juego
 * 
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class Context implements Serializable{

	private int id;
	private String title;
	private List<ActionHelpInformation> actions;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
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
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the actions
	 */
	public List<ActionHelpInformation> getActions() {
		return actions;
	}

	/**
	 * @param actions
	 *            the actions to set
	 */
	public void setActions(List<ActionHelpInformation> actions) {
		this.actions = actions;
	}

	/**
	 * @param id
	 * @param title
	 */
	public Context(int id, String title) {
		super();
		this.id = id;
		this.title = title;
		this.actions = new ArrayList<ActionHelpInformation>();
	}

	/**
	 * 
	 * @param action
	 * @return
	 */
	public ActionHelpInformation getAction(int action) {
		for (ActionHelpInformation ahi : this.actions) {
			if (ahi.getId() == action)
				return ahi;
		}
		throw new IndexOutOfBoundsException(
				"No existe está accion en este contexto[" + this.id + "]");
	}


    @Override
    public String toString() {
        return this.title;
    }
}
