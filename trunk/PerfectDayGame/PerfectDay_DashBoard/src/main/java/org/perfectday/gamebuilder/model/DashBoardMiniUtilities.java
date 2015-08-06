/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.gamebuilder.model;

import java.util.List;
import org.apache.log4j.Logger;
import org.perfectday.dashboard.exception.GameBuilderException;
import org.perfectday.gamebuilder.GameBuilder;
import org.perfectday.gamebuilder.GameBuilderClient;
import org.perfectday.gamebuilder.GameBuilderServer;
import org.perfectday.logicengine.model.battelfield.BattelField;
import org.perfectday.logicengine.model.battelfield.Field;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.minis.MiniFactory;

/**
 * Clase NO ORIENTADA A OBJETOS de utiliades para la inicialización de la partida
 * a través de internet
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class DashBoardMiniUtilities {

    private static final  Logger logger = Logger.getLogger(DashBoardMiniUtilities.class);
    public static Mini buildMini(MiniDescription miniDescription) throws GameBuilderException{
        try {
            logger.info("Se construye el un:" + miniDescription.getMini());
            Mini mini = MiniFactory.getInstance().createMini(miniDescription.getMini(), miniDescription.getLevel());
            logger.info("Construido!!:" + mini.toString());
            return mini;
        } catch (Exception ex) {
            logger.error("El mini no se ha podido construir, error en factoría de minis");
            throw new GameBuilderException("Error en factoria de minis");
        }
       
    }

    /**
     * Muestra el dialog para la selección del despliege de las unidades.
     * @param aThis
     */
    public static void showDeployDialog(GameBuilderClient aThis) {
         
         DashBoardMiniUtilities.dummyDeployClient(aThis.getTrueClientArmy(),aThis.getBattlefield());
    }

    /**
     * Muestra el dialog para la selección del despliege de las unidades.
     * @param aThis
     */
    public static void showDeployDialog(GameBuilder aThis) {
         
         DashBoardMiniUtilities.dummyDeployOnePlayer(aThis);
    }


    /**
     * Sincroniza dos  battleField para poner de acerudo las colocaciones.
     * @param battlefield
     * @param receivedBattleField
     * @param trueClientArmy
     */
    public static void sinchronizedBattleField(BattelField battlefield, BattelField receivedBattleField, List<Mini> trueClientArmy) {
        for (Mini mini : trueClientArmy) {
            Field dest = receivedBattleField.getField(mini);
            battlefield.getBattelfield()[dest.getX()][dest.getY()].setMiniOcupant(mini);
        }
    }

    private static void dummyDeployClient(List<Mini> trueClientArmy, BattelField battelField) {
        int iazul=battelField.getHigth()-2;
        int jazul=2;
        for(Mini mini: trueClientArmy){
            logger.info("Mini:"+mini.toString());
            battelField.getBattelfield()[iazul][jazul].setMiniOcupant(mini);            
            jazul++;
            if(jazul>=battelField.getBattelfield()[0].length){
                jazul=2;
                iazul++;
            }
        }    
    }

    private static void dummyDeployServer(List<Mini> trueServerArmy, BattelField battelField) {
        int irojo=2;
        int jrojo=2;
        for(Mini mini: trueServerArmy){
            logger.info("Mini:"+mini.toString());
            battelField.getBattelfield()[irojo][jrojo].setMiniOcupant(mini);            
            jrojo++;
            if(jrojo>battelField.getBattelfield()[0].length){
                jrojo=2;
                irojo--;
            }
        }                
    }

    private static void dummyDeployOnePlayer(GameBuilder gameBuilder) {
        DashBoardMiniUtilities.dummyDeployServer(gameBuilder.getTrueServerArmy(), gameBuilder.getBattlefield());
        DashBoardMiniUtilities.dummyDeployClient(gameBuilder.getTrueClientArmy(), gameBuilder.getBattlefield());
    }

     
    
   
    
    
}
