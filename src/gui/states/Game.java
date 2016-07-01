/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.states;

import entity.EntityManager;
import gui.Camera;
import main.Resources;
import input.MyKeyboard;
import map.MapManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Kristof
 */
public class Game extends BasicGameState {
    
    private static MapManager mapManager;
    private static EntityManager entityManager;
    
    private Image background;

    @Override
    public int getID() {
        return 1;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        long time = System.currentTimeMillis();
        background = Resources.getImage("Game");
        
        mapManager = new MapManager();
        entityManager = new EntityManager();
        System.out.println("InitGame: " + (System.currentTimeMillis() - time) + " ms");
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.drawImage(background, 0, 0);
        Camera.renderMap(mapManager, g);
        entityManager.drawEntitys(g);
        debug(container, g);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        entityManager.moveEntitys(delta);
        Camera.calcPos(container);
    }
    
    public static void loadMap(String name){
        mapManager.loadMap(name);
    }
    
    private void debug(GameContainer container, Graphics g){
        if (MyKeyboard.keyboard[Input.KEY_F3]) {
            g.drawString("FPS: " + container.getFPS(), 10, 10);
        }
    }
    
}
