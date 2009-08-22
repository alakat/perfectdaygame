/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.bitsonfire.perfectday.autoplay.comunication;

import org.perfectday.communication.model.plugcommunication.PlugCommunication;
import org.perfectday.message.model.Message;

/**
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class DummyPlugCommunication extends PlugCommunication{

    @Override
    public void exposeService() {
    }

    @Override
    public void connect(String destiny, String typeDestiny) {
    }

    @Override
    public void disconnect() {
    }

    @Override
    public void sendMessage(Object message) {
    }

    @Override
    public void startUp() {
    }

    @Override
    public Message receiveMessage() {
        return null;
    }

}
