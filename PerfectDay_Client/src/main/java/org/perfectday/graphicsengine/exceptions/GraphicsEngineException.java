/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.graphicsengine.exceptions;

/**
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class GraphicsEngineException extends Exception {

    /**
     * Creates a new instance of <code>GraphicsEngineException</code> without detail message.
     */
    public GraphicsEngineException() {
    }


    /**
     * Constructs an instance of <code>GraphicsEngineException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public GraphicsEngineException(String msg) {
        super(msg);
    }
}
