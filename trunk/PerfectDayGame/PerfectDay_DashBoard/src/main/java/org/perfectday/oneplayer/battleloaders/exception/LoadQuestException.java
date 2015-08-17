/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.perfectday.oneplayer.battleloaders.exception;

/**
 *
 * @author Miguel (alakat@gmail.com)
 */
public class LoadQuestException extends Exception {

    /**
     * Creates a new instance of <code>LoadQuestException</code> without detail
     * message.
     */
    public LoadQuestException() {
    }

    /**
     * Constructs an instance of <code>LoadQuestException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public LoadQuestException(String msg) {
        super(msg);
    }
}
