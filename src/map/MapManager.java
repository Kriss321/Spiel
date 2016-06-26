/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

import org.newdawn.slick.SlickException;

/**
 *
 * @author Kristof
 */
public class MapManager {
    
    public static Map map;
    
    public void loadMap(String name) {
        try {
            map = new Map(name);
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
    
}
