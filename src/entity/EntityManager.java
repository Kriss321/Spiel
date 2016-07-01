/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import map.MapManager;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Kristof
 */
public class EntityManager {
    
    public static Entity[] entitys;
    private Entity e;
    private Entity p;
    

    public EntityManager() {
        
    }
    
    public static void loadEntitys(int playerCount) {
        
        entitys = new Entity[playerCount];
        for (int i = 0; i < entitys.length; i++) {
            entitys[i] = new Player(MapManager.map, i);
        }
    }
    
    public void drawEntitys(Graphics g) {
        for (Entity entity : entitys) {
            entity.drawEntity(g);
        }
    }
    
    public void moveEntitys(int delta){
        for (Entity entity : entitys) {
            entity.move(delta);
        }
    }
}
