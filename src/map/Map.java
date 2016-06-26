/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

import main.Resources;
import java.util.HashMap;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author Kristof
 */
public class Map {
    
    private String mapName;
    private TiledMap map;
    private float gravity;
    private java.util.Map<Integer, float[]> startPoints;
    
    public Map(String name) throws SlickException {
        mapName = name;
        map = Resources.loadMap(name);
        setGravity();
        findStartPoints();
        startPoints.size();
    }
    
    public void findStartPoints(){
        startPoints = new HashMap();
        int player = 0;
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                if(map.getTileId(x, y, 1) == 91){
                    float[] point = {x, y};
                    startPoints.put(player, point);
                    player++;
                }
            }
        }
    }
    
    public void renderMap(int x, int y) {
        map.render(x, y);
    }
    
    public void renderMap(int x, int y, int layer) {
        map.render(x, y, layer);
    }
    
    public String getName() {
        return mapName;
    }
    
    public TiledMap getMap() {
        return map;
    }
    
    public float[] getStartPoit(int id) {
        return startPoints.get(id);
    }
    
    public String getStartPoitCount() {
        return String.valueOf(startPoints.size());
    }
    
    public float setGravity(){
        return gravity = Float.parseFloat(map.getMapProperty("Gravity", "9.81"));
    }
}
