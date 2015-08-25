/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.perfectday.main.dummyengine.commons;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 *
 * @author Miguel (alakat@gmail.com)
 */
public class Sprite {
    
    BufferedImage image;
    Point spriteCood;
    Dimension spriteDim;

    public Sprite(BufferedImage image, Point spriteCood, Dimension spriteDim) {
        this.image = image;
        this.spriteCood = spriteCood;
        this.spriteDim = spriteDim;
    }

   /**
    * Pintamos su segmento del sprite
    * @param g 
    */
    public void paint(Graphics2D g){
        g.drawImage(image.getSubimage(spriteCood.x, spriteCood.y, 
                spriteDim.width, spriteDim.height), 0  , 0, null);
    }
    
}
