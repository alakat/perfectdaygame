/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.perfectday.main.dummyengine.commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 *
 * @author Miguel (alakat@gmail.com)
 */
public class SpriteSheet {
    
    private List<Sprite> sprites;
    private Map<String,List<Sprite>> animations;
    
    /**
     * 
     */
    public SpriteSheet() {
        this.sprites=new ArrayList<Sprite>();
        this.animations = new HashMap<String, List<Sprite>>();
        
    }
    
    /**
     * 
     * @param sprite 
     */
    public void addSprite(Sprite sprite){
        sprites.add(sprite);
    }
    
    /**
     * 
     * @param key
     * @param sprites 
     */
    public void addAnimation(String key, List<Sprite> sprites){
        this.animations.put(key, sprites);
    }

    /**
     * 
     * @return 
     */
    public List<Sprite> getSprites() {
        return sprites;
    }

    /**
     * 
     * @param animation
     * @return 
     */
    public AnimateSprite createAnimation(String animation) {
        AnimateSprite as = new AnimateSprite(this.animations.get(animation));
        return as;
    }

}