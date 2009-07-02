/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.graphicsengine;

import java.util.List;
import org.perfectday.graphicsengine.exceptions.GraphicsEngineException;
import org.perfectday.logicengine.model.battelfield.BattelField;
import org.perfectday.logicengine.model.battelfield.Field;

/**
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public abstract class GatewayEngine {

    /**
     * Inicializa en el motor gráfico y en el GatewayEngine el campo de batalla
     * creado en BattelField
     * @param battelField
     * @throws org.perfectday.graphicsengine.exceptions.GraphicsEngineException
     */
    public abstract void initBattlefield(BattelField battelField) throws GraphicsEngineException;


       /**
        * Activa/desactiva el grid o rejilla
        * @param value
        */
        public abstract  void setGridVisible(boolean value);

        /**
         * Selecciona una posición para el cursor de indicación de unidad.
         * Traduce Field a una coordenada (x,y)
         * @param field
         */
        public abstract void setSelectedMarkOn(Field field);

        /**
         * Activa/Desactiva la visibilidad del cursor de unidad de selección.
         * @param value
         */
        public abstract void setSelectedMarkVisible(boolean value);

        /**
         * Activa/Desactiva la visibilidad del cursor de unidad de objetivo.
         * Traduce Field a una coordenada (x,y)
         * @param field
         */
        public abstract void setTargetdMarkOn(Field field);

        /**
         * Activa/Desactiva la visibilidad del cursor de unidad de selección.
         * @param value
         */
        public abstract void setTargetMarkVisible(boolean value);

        /**
         * Procedimiento para oscurecer una lista de casillas.
         * Lo traduce a un java.awt.Point[]
         * @param pointsList
         */
        public abstract void setInDarkPositionsOn(List<Field> list);

        /**
         * procedimiento para volver a encenderlas todas las casillas oscuras
         */
        public abstract void setInDarkPositionOff();
}
