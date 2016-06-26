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
    
    protected static Entity[] entitys;
    private Entity e;
    private Entity p;
    

    public EntityManager() {
        
    }
    
    public void loadEntitys() {
        entitys = new Entity[2];
        for (int i = 0; i < 2; i++) {
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
