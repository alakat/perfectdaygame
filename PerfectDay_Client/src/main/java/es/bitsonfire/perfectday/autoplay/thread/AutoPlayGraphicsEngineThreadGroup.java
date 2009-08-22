/**
 * 
 */
package es.bitsonfire.perfectday.autoplay.thread;

import org.perfectday.main.dummyengine.threads.GraphicsEngineThreadGroup;

/**
 * Grupo de hebras para el engine gráphico usado en las partidas reproducidas
 * 
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 * 
 */
public class AutoPlayGraphicsEngineThreadGroup extends
		GraphicsEngineThreadGroup {

	/**
	 * @param name
	 */
	public AutoPlayGraphicsEngineThreadGroup(String name) {
		super(name);
	}

}
