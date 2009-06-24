/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.brain;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.unittime.UnitTime;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public abstract class AbstractBrain implements Brain{

    protected AbstractGameBrain gameBrain;
    protected AbstractMotionBrain motionBrain;
    protected AbstractActionBrain actionBrain;

    public AbstractBrain( Properties p ) {
        try {
            String nameClassGameBrain = p.getProperty("GameBrain");
            String nameClassMotionBrain = p.getProperty("MotionBrain");
            String nameClassActionBrain = p.getProperty("ActionBrain");
            
            this.actionBrain =(AbstractActionBrain) 
                    Class.forName(nameClassActionBrain).getConstructor().newInstance();
            this.motionBrain = (AbstractMotionBrain) 
                    Class.forName(nameClassMotionBrain).getConstructor().newInstance();  
            this.gameBrain = (AbstractGameBrain)Class.forName(nameClassGameBrain)
                    .getConstructor(AbstractMotionBrain.class, AbstractActionBrain.class).
                    newInstance(this.motionBrain, this.actionBrain);
        } catch (InstantiationException ex) {
            org.apache.log4j.Logger.getLogger(AbstractBrain.class.getName()).
                    error(ex.getClass().getName());
           org.apache.log4j.Logger.getLogger(AbstractBrain.class.getName()).
                    error("Error en la inicialización de BRAIN",ex);
        } catch (IllegalAccessException ex) {
            org.apache.log4j.Logger.getLogger(AbstractBrain.class.getName()).
                    error(ex.getClass().getName());
            org.apache.log4j.Logger.getLogger(AbstractBrain.class.getName()).
                    error("Error en la inicialización de BRAIN",ex);
        } catch (IllegalArgumentException ex) {
            org.apache.log4j.Logger.getLogger(AbstractBrain.class.getName()).
                    error(ex.getClass().getName());
            org.apache.log4j.Logger.getLogger(AbstractBrain.class.getName()).
                    error("Error en la inicialización de BRAIN",ex);
        } catch (InvocationTargetException ex) {
            org.apache.log4j.Logger.getLogger(AbstractBrain.class.getName()).
                    error(ex.getClass().getName());
           org.apache.log4j.Logger.getLogger(AbstractBrain.class.getName()).
                    error("Error en la inicialización de BRAIN",ex);
        } catch (NoSuchMethodException ex) {
            org.apache.log4j.Logger.getLogger(AbstractBrain.class.getName()).
                    error(ex.getClass().getName());
            org.apache.log4j.Logger.getLogger(AbstractBrain.class.getName()).
                    error("Error en la inicialización de BRAIN",ex);
        } catch (SecurityException ex) {
            org.apache.log4j.Logger.getLogger(AbstractBrain.class.getName()).
                    error(ex.getClass().getName());
            org.apache.log4j.Logger.getLogger(AbstractBrain.class.getName()).
                    error("Error en la inicialización de BRAIN",ex);
        } catch (ClassNotFoundException ex) {
            org.apache.log4j.Logger.getLogger(AbstractBrain.class.getName()).
                    error(ex.getClass().getName());
            org.apache.log4j.Logger.getLogger(AbstractBrain.class.getName()).
                    error("Error en la inicialización de BRAIN",ex);
        }
    }
    
    public abstract Turn getTrun(Mini min,UnitTime ut);
    
}
