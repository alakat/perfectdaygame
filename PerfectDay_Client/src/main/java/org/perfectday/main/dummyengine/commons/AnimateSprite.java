/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.perfectday.main.dummyengine.commons;

import java.awt.Graphics2D;
import java.util.List;

/**
 *
 * @author Miguel (alakat@gmail.com)
 */
public class AnimateSprite {

    private Sprite[] sprites;
    private int spriteIndex;

    /**
     * 
     * @param sprites 
     */
    public AnimateSprite(List<Sprite> sprites) {
        this.spriteIndex = 0;
        this.sprites = new Sprite[sprites.size()];
        for (int i = 0; i < sprites.size(); i++) {
            this.sprites[i]=sprites.get(i);
        }
                
    }
    
    /**
     * Pinta la pieza de animaci?n que toca ahora
     * @param g 
     */
    public void paint(Graphics2D g){
        Sprite sprite = this.sprites[this.spriteIndex];
        sprite.paint(g);
        this.spriteIndex++;
        if(this.spriteIndex==this.sprites.length){
            this.spriteIndex=0;
        }
    }
    
    
    
    
}
