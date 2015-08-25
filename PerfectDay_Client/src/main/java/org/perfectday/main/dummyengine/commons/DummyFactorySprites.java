/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.perfectday.main.dummyengine.commons;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.imageio.ImageIO;
import org.apache.log4j.Logger;
import org.perfectday.logicengine.model.minis.MiniType;

/**
 *
 * @author Miguel (alakat@gmail.com)
 */
public class DummyFactorySprites {
    private Logger logger = Logger.getLogger(DummyFactorySprites.class);
    private Map<MiniType, SpriteSheet> spriteSheets;
    private static DummyFactorySprites instance;

    private DummyFactorySprites() {
        
        this.loadMap();
    }

    public static DummyFactorySprites getInstance() {
        if(instance==null)
            instance=new DummyFactorySprites();
        return instance;
    }

    /**
     * Comprueba la carga del mapa de sprites
     *
     * @return
     */
    private boolean isSpritesLoaded() {
        return this.spriteSheets != null;
    }

    /**
     * Carga, si no ha sido hecho ya, los spritessheets en el sistema
     */
    private void loadMap() {
        try {
            if (!this.isSpritesLoaded()) {
                this.spriteSheets=new HashMap<MiniType, SpriteSheet>();
                for (MiniType value : MiniType.values()) {
                    //TODO: Quitar
                    if(value==MiniType.SOLDIER)
                        loadMiniTypeSprites(value);                    
                }
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(),ex);
        }
    }

    /**
     * Carga la informaci?n de un tipo de Mini
     * @param minitype
     * @throws NumberFormatException
     * @throws IOException 
     */
    private void loadMiniTypeSprites(MiniType minitype) throws NumberFormatException, IOException {
        logger.info("Cargamos sprites de: "+minitype);
        InputStream is = DummyFactorySprites.class.getResourceAsStream("/assets/sprites/" + minitype.name().toLowerCase() + ".properties");
        //InputStream isImage = DummyFactorySprites.class.getResourceAsStream("/assets/sprites/" + minitype.name().toLowerCase() + ".png");
        URL url_ = DummyFactorySprites.class.getResource("/assets/sprites/" + minitype.name().toLowerCase() + ".png");
        BufferedImage bi = ImageIO.read(url_);
        
        Properties properties = new Properties();
        properties.load(is);
        SpriteSheet ss = new SpriteSheet();
        String animationsString = properties.getProperty("animations");
        String[] animations = animationsString.split(",");
        for (String animation : animations) {
            loadAnimation(properties, animation, bi, ss);
        }
        this.spriteSheets.put(minitype, ss);
    }

    /**
     * Se lee la informaci?n de una animaci?n del propperties de animaciones
     * @param properties
     * @param animation
     * @param bi
     * @param ss
     * @throws NumberFormatException 
     */
    private void loadAnimation(Properties properties, String animation, BufferedImage bi, SpriteSheet ss) throws NumberFormatException {
        int sprites = Integer.parseInt(properties.getProperty(
                "animations."+animation));
        String dim = properties.getProperty(
                "animations."+animation+".size");
        Dimension dimension = new Dimension(
                Integer.parseInt(dim.split(",")[0]),
                Integer.parseInt(dim.split(",")[1]));
        List<Sprite> spritesAnimations= new ArrayList<Sprite>();
        for (int i = 0; i < sprites; i++) {
            String p = properties.getProperty("animations."+animation+"."+i);
            Point point =new Point(
                    Integer.parseInt(p.split(",")[0]),
                    Integer.parseInt(p.split(",")[1]));
            
            Sprite sprite = new Sprite(bi, point, dimension);
            ss.addSprite(sprite);
            spritesAnimations.add(sprite);
        }
        ss.addAnimation(animation, spritesAnimations);
    }

    /**
     * Obtiene una spritesheet para un tipo de mini
     * @param miniType
     * @return 
     */
    public SpriteSheet getSpriteSheet(MiniType miniType){
        SpriteSheet ss =  this.spriteSheets.get(miniType);
        if(ss==null){
            ss = this.spriteSheets.get(this.spriteSheets.keySet().iterator().next());
            logger.debug("No hay sprite para:"+ miniType.name()+" Se carga por defecto");
        }
        return ss;
    }
    
    
    public AnimateSprite createAnimation(MiniType miniType,String animation){
        AnimateSprite as =null;
        SpriteSheet ss = this.getSpriteSheet(miniType);
        as = ss.createAnimation(animation);
        return as;
    }
}
