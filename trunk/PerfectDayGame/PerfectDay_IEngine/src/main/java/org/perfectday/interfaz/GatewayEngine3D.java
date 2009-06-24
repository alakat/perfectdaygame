/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.interfaz;

import engine3D.Engine3D;
import java.util.List;
import org.perfectday.logicengine.model.battelfield.Field;
import org.perfectday.logicengine.model.minis.Mini;

/**
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public interface GatewayEngine3D {
    
    /**
     * Inicializa el motor 3D
     * @param army1 Cada elemento de la lista posee
     *     nombre del modelo
     *     id
     *     posición x,y
     * @param army2
     * @return
     */
    void init(List<Mini> army1,List<Mini> army2 );
    
    /**
     * Construir un metodo que obtena los fields por los que pasa el movimiento 
     * de ini a fin.
     * @param idMini
     * @param ini
     * @param end
     */
    void movedMini(Mini mini, Field ini, Field end);
    
    void atack(Mini mini ,boolean primaryAtack);
    
    void action(Mini mini);
    
    void death(Mini mini);
    
    void support(Mini mini);
    
    /**
     * Indica que mini ha sido selecionado para actuar y las casillas a las que 
     * puede mover
     * @param idMini
     * @param field
     * @param accesible
     */
    void activated(Mini mini,Field field, List<Field> accesible);
    
    /**
     * pinta las celdas accesibles en un combate
     * @param accesible
     */
    void showtargets(List<Field> accesible);
    
    /**
     * inidica un mini que ha sido seleccionado por alguna razon (pila de activación)
     * @param idMini
     */
    void selectedMini(Mini mini, Field field);
    
    //NECESITAMOS METODOS PARA HACER LAS ACCIONES CONTRARIAS A ACTIVAR Y SELECCIONAR.
    //Hay que indicarle al engine3D que tiene que quitar las marcas de los minis y las
    //celdas.    
    //añadido
    void deactivated(Mini mini, Field field, List<Field> accesible);
    //añadido
    void hidetargets(List<Field> accesible);
    //añadido
    void deselectedMini(Mini mini, Field field);
    
    public Engine3D getEngine3D();
    
    
    

}
