/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.bitsonfire.perfectday.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *	Ayuda en una acción del juego. Las ayudas en las acciones pueden tener otras acciones relacionadas 
 * mediante siguinte, anterior, e interesante para ayudar al usuario
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class ActionHelpInformation implements Serializable {

	private int id;
	private String title;
	private String resumen;
	private String completText;
	private ActionHelpInformation next;
	private ActionHelpInformation preview;
	private List<ActionHelpInformation> interesting;
	/**
	 * @param id
	 * @param title
	 * @param resumen
	 * @param completText
	 */
	public ActionHelpInformation(int id, String title, String resumen, String completText) {
		super();
		this.id = id;
		this.title = title;
		this.resumen = resumen;
		this.completText = completText;
        interesting = new ArrayList<ActionHelpInformation>();
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
	 * @return the resumen
	 */
	public String getResumen() {
		return resumen;
	}
	/**
	 * @param resumen the resumen to set
	 */
	public void setResumen(String resumen) {
		this.resumen = resumen;
	}
	/**
	 * @return the completText
	 */
	public String getCompletText() {
		return completText;
	}
	/**
	 * @param completText the completText to set
	 */
	public void setCompletText(String completText) {
		this.completText = completText;
	}
	/**
	 * @return the next
	 */
	public ActionHelpInformation getNext() {
		return next;
	}
	/**
	 * @param next the next to set
	 */
	public void setNext(ActionHelpInformation next) {
		this.next = next;
	}
	/**
	 * @return the preview
	 */
	public ActionHelpInformation getPreview() {
		return preview;
	}
	/**
	 * @param preview the preview to set
	 */
	public void setPreview(ActionHelpInformation preview) {
		this.preview = preview;
	}

    /**
     *
     * @return
     */
    public List<ActionHelpInformation> getInteresting() {
        return interesting;
    }

    /**
     * 
     * @param interesting
     */
    public void setInteresting(List<ActionHelpInformation> interesting) {
        this.interesting = interesting;
    }


    @Override
    public String toString() {
        return this.getTitle();
    }


	
	
	
}
