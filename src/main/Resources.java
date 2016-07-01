/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author Kristof
 */
public class Resources{
    
    private static Map<String, Image> images;
    private static Map<String, String> maps;
    private static Map<String, Map<String, AngelCodeFont>> fonts;
    
    public static void loadResources(){
        images = new HashMap();
        maps = new HashMap();
        fonts = new HashMap();
        
        File dir = new File("./res/Maps");
        for (File file : dir.listFiles()) {
            if (!file.isHidden()) {
                String name = file.getName().substring(0, file.getName().lastIndexOf("."));
                maps.put(name, file.getAbsolutePath());
            }
        }
        
        dir = new File("./res/Backgrounds");
        for (File file : dir.listFiles()) {
            if (!file.isHidden()) {
                try {
                    String name = file.getName().substring(0, file.getName().lastIndexOf("."));
                    images.put(name, loadImage(file.getAbsolutePath()));
                } catch (SlickException ex) {
                    System.err.println(ex);
                }
            }
        }
        
        dir = new File("./res/Buttons");
        for (File file : dir.listFiles()) {
            if (!file.isHidden()) {
                try {
                    String name = file.getName().substring(0, file.getName().lastIndexOf("."));
                    images.put(name, loadImage(file.getAbsolutePath()));
                } catch (SlickException ex) {
                    System.err.println(ex);
                }
            }
        }
        
        dir = new File("./res/Entitys");
        for (File file : dir.listFiles()) {
            if (!file.isHidden()) {
                try {
                    String name = file.getName().substring(0, file.getName().lastIndexOf("."));
                    images.put(name, loadImage(file.getAbsolutePath()));
                } catch (SlickException ex) {
                    System.err.println(ex);
                }
            }
        }
        
        dir = new File("./res/Font");
        for (File file : dir.listFiles()) {
            if (!file.isHidden() && file.isDirectory()) {
                Map<String, AngelCodeFont> types = new HashMap();
                for (File font : file.listFiles()) {
                    if (!font.isHidden() && font.getName().endsWith(".png")) {
                        try {
                            String name = font.getName().substring(0, font.getName().lastIndexOf("."));
                            String typ = font.getName().substring(1, font.getName().lastIndexOf("]"));
                            if (font.getName().lastIndexOf("-") != -1) {
                                typ += " " + font.getName().substring(font.getName().lastIndexOf("-"), font.getName().lastIndexOf("."));
                            }
                            types.put(typ, loadFont(file.getAbsolutePath() + "/" + name));
                        } catch (SlickException ex) {
                            System.err.println(ex);
                        }
                    }
                }
                fonts.put(file.getName(), types);
            }
        }
        
    }
    
    private static Image loadImage(String path) throws SlickException {
        return new Image(path);
    }
    
    public static TiledMap loadMap(String name) throws SlickException {
        return new TiledMap(maps.get(name));
    }
    
    private static AngelCodeFont loadFont(String name) throws SlickException {
        return new AngelCodeFont(name+".fnt", new Image(name+".png"));
    }
    
    public static Image getImage(String image) {
        boolean found = false;
        for (String key : images.keySet()) {
            
            if(key.equalsIgnoreCase(image)){
                found = true;
                image = key;
            }
        }
        
        return images.get(image);
        
    }
    
    public static Collection getAllMaps(){
        return maps.keySet();
    }
    
    public static AngelCodeFont getFont(String font, String type){
        return fonts.get(font).get(type);
    }
    
    public static String getMapPath(String name){
        return maps.get(name);
    }
}
