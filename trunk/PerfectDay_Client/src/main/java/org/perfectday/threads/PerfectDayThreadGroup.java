/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.threads;

/**
 * Identifica un grupo de hebras en perfectday.
 * Posee la informaci�n necesaria para realizar la comunicaci�n entre los
 * distintos grupos de hebras sin ocasionar interbloqueo entre las distintas
 * ejecuciones principales
 *  1� RedSocial
 *  2� Nucleo
 *  3� Motor gr�fico
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public abstract class PerfectDayThreadGroup extends ThreadGroup {

    private EventRunner eventRunner;
    private CommandRunner commandRunner;

    public PerfectDayThreadGroup(String name) {
        super(name);
        this.eventRunner = new EventRunner();
        this.commandRunner = new CommandRunner();
    }

    /**
     * Arranca el grupo
     */
    public void start(){
        Thread tEventRunner = new Thread(eventRunner);
        tEventRunner.start();
        Thread tCommandRunner = new Thread(commandRunner);
        tCommandRunner.start();
    }

    /**
     * Para el grupo
     */
    public void mystop(){
        eventRunner.stop();
        commandRunner.stop();
        this.stop();
    }



}
