/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

import java.io.BufferedReader;
import main.Resources;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author Kristof
 */
public class Map {
    
    private String mapName;
    private TiledMap map;
    private double gravity;
    
    public Map(String name) throws SlickException {
        mapName = name;
        map = Resources.loadMap(name);
        setGravity();
    }
    
    public void renderMap(int x, int y){
        map.render(x, y);
    }
    
    public void renderMap(int x, int y, int layer){
        map.render(x, y, layer);
    }
    
    public String getName(){
        return mapName;
    }
    
    public void setGravity(){
        try {
            InputStream temp = new FileInputStream(Resources.getMapPath(mapName));
            BufferedReader buffer = new BufferedReader(new InputStreamReader(temp));
            String map = buffer.lines().collect(Collectors.joining("\n"));
            
            Pattern properties = Pattern.compile("<[\\w =]+\"Gravity\"[\\w\\d.=\" ]+\\/>");
            Matcher matcher = properties.matcher(map);
            if (matcher.find()) {
                properties = Pattern.compile("[\\d.]+");
                matcher = properties.matcher(matcher.group());
                if (matcher.find()) {
                    gravity = Double.parseDouble(matcher.group());
                }
            } else {
                gravity = 9.81;
            }
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
