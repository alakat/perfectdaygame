/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.bitsonfire.perfectday.autoplay;

import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Esta clase almacena la toda la información  de una partida. Como empezo y que
 * eventos ocurrieron en la partida hasta que esta finalizo.
 * Además permite guardar y recuperar los datos al disco así como reproducir una
 * partida salvada.
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class GameBackUp {

    /**
     * Datos de la partida
     */
    private GameBackUpData data;
    /**
     * singleton instance
     */
    private static GameBackUp instance;

    private  GameBackUp() {
        newGame();
    }

    /**
     *
     * @return
     */
    public static GameBackUp getInstance(){
        if(instance == null){
            instance = new GameBackUp();
        }
        return instance;
    }

    /**
     * Crea un nuevo juego
     */
    public void newGame() {
        this.data = new GameBackUpData();
    }


    /**
     * Salva la partida.
     * @throws java.io.IOException
     */
   public boolean  saveGame() throws IOException{
       JFileChooser fileChooser = new JFileChooser();
       FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "Partidas guardadas en Perfect Day ", ".gpd");
       fileChooser.setFileFilter(filter);
       int opt = fileChooser.showSaveDialog(null);
       if(opt!= JFileChooser.APPROVE_OPTION )
           return false;
       File f  = fileChooser.getSelectedFile();
       this.data.save(f);
       return true;
   }

    public GameBackUpData getData() {
        return data;
    }

    public void setData(GameBackUpData data) {
        this.data = data;
    }
   
    /**
     * Permite al usuario seleccionar una partida guardad e iniciarla para su
     * reproducción. 
     * @return true si la partida inico, false en otro motivo
     */
    public boolean loadGame(){
        JFileChooser fileChooser = new JFileChooser();
       FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "Partidas guardadas en Perfect Day ", ".gpd");
       fileChooser.setFileFilter(filter);
       int opt = fileChooser.showSaveDialog(null);
       if(opt!= JFileChooser.APPROVE_OPTION )
           return false;
       File f  = fileChooser.getSelectedFile();
       gameGo(f);
       return true;
    }

    /**
     * Crea y ejecuta la partida guardad permitiendo su reproducción.
     * @param f
     */
    private void gameGo(File f) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
