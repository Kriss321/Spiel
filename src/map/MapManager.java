/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

import entity.EntityManager;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Kristof
 */
public class MapManager {
    
    public static Map map;
    public static int playerCount;
    
    public void loadMap(String name) {
        try {
            map = new Map(name);
            playerCount = map.getStartPoitCount();
        } catch (SlickException ex) {
            System.err.println(ex);
        }
        
    }
    
    public void reloadMap() throws SlickException {
        if(map != null){
            map = new Map(map.getName());
        }
    }
    
    public void renderMap(int x, int y) {
        map.renderMap(x, y);
    }
    
    public void renderMap(int x, int y, int layer){
        map.renderMap(x, y, layer);
    }
    
    public static boolean collisionMap(float x, float y) {
        if (x >= 0 && x <= (map.getMap().getWidth() * map.getMap().getTileWidth()) && y >= 0 && y <= (map.getMap().getHeight() * map.getMap().getTileHeight())) {
            if (map.getMap().getTileId((int) (x / map.getMap().getTileWidth()), (int) (y / map.getMap().getTileHeight()), map.getMap().getLayerIndex("Solid")) != 0) {
                return true;
            }
        }

        return false;
    }

}
