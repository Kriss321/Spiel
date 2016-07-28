/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entity.Entity;
import entity.EntityManager;
import main.Main;
import map.MapManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Kristof
 */
public class Camera {
    
    public static int mapPosX = 0;
    public static int mapPosY = 0;
    
    private static float scale = 1;
    
    public static void setScale(Graphics g) {
        g.scale(scale, scale);
        //g.setAntiAlias(true);
    }
    
    public static void removeScale(Graphics g) {
        g.scale(1/scale , 1/scale);
    }
    
    public static void renderMap(Graphics g) {
        MapManager.renderMap(mapPosX, mapPosY, 0);
    }
    
    public static void renderEntity(Graphics g) {
        EntityManager.drawEntitys(g);
    }
    
    public static void calcScale(GameContainer container) {
        if (MapManager.map != null) {
            float scaleX = container.getWidth() / ((float) MapManager.map.getMap().getWidth() * MapManager.map.getMap().getTileWidth());
            float scaleY = container.getHeight() / ((float) MapManager.map.getMap().getHeight() * MapManager.map.getMap().getTileHeight());

            scaleX = Math.round(1000 * scaleX) / 1000f;
            scaleY = Math.round(1000 * scaleY) / 1000f;

            if (scaleX < 1 && scaleY < 1) {
                scale = 1;
            } else if (scaleX >= scaleY) {
                scale = scaleX;
            } else if (scaleX < scaleY) {
                scale = scaleY;
            }
        }
    }
    
    public static void calcMapPos(GameContainer container) {
        if (MapManager.map != null) {
            float maxX = 0;
            float minX = MapManager.map.getMap().getWidth() * MapManager.map.getMap().getTileWidth();
            float maxY = 0;
            float minY = MapManager.map.getMap().getHeight() * MapManager.map.getMap().getTileHeight();
            for (Entity entity : EntityManager.entitys) {
                maxX = Math.max(maxX, entity.getPosX() + entity.getWidth());
                minX = Math.min(minX, entity.getPosX());
                maxY = Math.max(maxY, entity.getPosY() + entity.getHeigth());
                minY = Math.min(minY, entity.getPosY());
            }
            float x = (maxX + minX) / 2;
            float y = (maxY + minY) / 2;

            if (x * scale < (container.getWidth() / 2)) {
                mapPosX = 0;
            } else if (x * scale > (MapManager.map.getMap().getWidth() * MapManager.map.getMap().getTileWidth() * scale - container.getWidth() / 2)) {
                mapPosX = (int) (MapManager.map.getMap().getWidth() * MapManager.map.getMap().getTileWidth() * scale - container.getWidth()) * -1;
            } else {
                mapPosX = Math.round((-x * scale + (container.getWidth() / 2)));
            }
            mapPosX = Math.round(mapPosX / scale);

            if (y < (container.getHeight() / 2)) {
                mapPosY = 0;
            } else if (y > (MapManager.map.getMap().getHeight() * MapManager.map.getMap().getTileHeight() * scale - container.getHeight() / 2)) {
                mapPosY = (int) (MapManager.map.getMap().getHeight() * MapManager.map.getMap().getTileHeight() * scale - container.getHeight()) * -1;
            } else {
                mapPosY = Math.round((-y + (container.getHeight() / 2)));
            }
            mapPosY = Math.round(mapPosY / scale);
        }
    }
    
    public static float getScale() {
        return scale;
    }
}
