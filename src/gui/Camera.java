/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entity.EntityManager;
import input.MyKeyboard;
import map.MapManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

/**
 *
 * @author Kristof
 */
public class Camera {
    
    public static int mapPosX = 0;
    public static int mapPosY = 0;
    
    public Camera() {
        //EntityManager.entitys.length
    }
    
    public static void renderMap(MapManager mapManager, Graphics g) {
        mapManager.renderMap(mapPosX, mapPosY, 0);
        if (MyKeyboard.keyboard[Input.KEY_F3]) {
            g.drawString("Map: " + mapPosX + " - " + mapPosY, 100, 10);
        }
    }
    
    public static void calcPos(GameContainer container) {
        if (EntityManager.entitys[0].getPosX() < (container.getWidth()/2)) {
            mapPosX = 0;
        } else if (EntityManager.entitys[0].getPosX() > (MapManager.map.getMap().getWidth() * MapManager.map.getMap().getTileWidth() - container.getWidth()/2)) {
            mapPosX = (MapManager.map.getMap().getWidth() * MapManager.map.getMap().getTileWidth() - container.getWidth()) * -1;
        } else {
            mapPosX = Math.round((-EntityManager.entitys[0].getPosX() + (container.getWidth()/2)));
        }
        
        if (EntityManager.entitys[0].getPosY() < (container.getHeight()/2)) {
            mapPosY = 0;
        } else if (EntityManager.entitys[0].getPosY() > (MapManager.map.getMap().getHeight()* MapManager.map.getMap().getTileHeight()- container.getHeight()/2)) {
            mapPosY = (MapManager.map.getMap().getHeight()* MapManager.map.getMap().getTileHeight()- container.getHeight()) * -1;
        } else {
            mapPosY = Math.round((-EntityManager.entitys[0].getPosY() + (container.getHeight()/2)));
        }
    }
}
