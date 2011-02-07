/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.nutroptima.soft.model;

/**
 *
 * @author Miguel Angel López Montellano <mlopez@nutroptima.es>
 */
public class Usuario {
    private int id;
    private String login;
    private String pass;

    public Usuario(int id, String login, String pass) {
        this.id = id;
        this.login = login;
        this.pass = pass;
    }

    public Usuario() {
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
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the pass
     */
    public String getPass() {
        return pass;
    }

    /**
     * @param pass the pass to set
     */
    public void setPass(String pass) {
        this.pass = pass;
    }
    
}
