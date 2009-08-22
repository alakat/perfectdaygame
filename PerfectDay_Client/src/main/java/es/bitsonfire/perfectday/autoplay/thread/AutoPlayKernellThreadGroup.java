/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.bitsonfire.perfectday.autoplay.thread;

import es.bitsonfire.perfectday.autoplay.comunication.DummyPlugCommunication;
import org.perfectday.core.threads.KernellThreadGroup;
import org.perfectday.logicengine.core.Game;

/**
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class AutoPlayKernellThreadGroup extends KernellThreadGroup{

    public AutoPlayKernellThreadGroup(String name, Game game) {
        super(name, game, new DummyPlugCommunication());
    }

    
    @Override
    public boolean gameGo() {

        return true;
    }


}
