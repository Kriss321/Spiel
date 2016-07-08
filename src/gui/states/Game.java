/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.states;

import entity.EntityManager;
import gui.Camera;
import gui.Window;
import main.Resources;
import input.MyKeyboard;
import java.util.Observable;
import java.util.Observer;
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
public class Game extends BasicGameState implements Observer {
    
    public static GameContainer container;
    
    private static Image background;

    @Override
    public int getID() {
        return Window.ID_GAME;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        long time = System.currentTimeMillis();
        Game.background = Resources.getImage("Game");
        Game.container = container;
        System.out.println("InitGame: " + (System.currentTimeMillis() - time) + " ms");
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.drawImage(background, 0, 0);
        Camera.setScale(g);
        Camera.renderMap(g);
        Camera.renderEntity(g);
        Camera.removeScale(g);
        debug(container, g);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        EntityManager.moveEntitys(delta);
        Camera.calcMapPos(container);
        Window.fullScreen(container);
        changeState();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof Object[]) {
            Object[] argument = (Object[])arg;
            if (argument[0] instanceof String) {
                String s = (String)argument[0];
                if (s.equalsIgnoreCase("fullscreen") && argument[1] instanceof GameContainer) {
                    GameContainer container = (GameContainer)argument[1];
                    if (Window.state == 1) {
                        Camera.calcScale(container);
                    }
                } else if (s.equalsIgnoreCase("changeState") && argument[1] instanceof Integer) {
                    int state = (int)argument[1];
                    if (Window.state == state) {
                        Camera.calcScale(container);
                    }
                }
                
            }
            
        }
    }

    public static void loadMap(String name){
        MapManager.loadMap(name);
    }
    
    private void debug(GameContainer container, Graphics g){
        if (MyKeyboard.keyboard[Input.KEY_F3]) {
            g.drawString("FPS: " + container.getFPS(), 10, 10);
            g.drawString("Map: " + Camera.mapPosX + " - " + Camera.mapPosY, 100, 10);
            EntityManager.debug(g);
        }
    }
    
    private void changeState() {
        if (MyKeyboard.keyboard[Input.KEY_ESCAPE]) {
            MyKeyboard.keyboard[Input.KEY_ESCAPE] = false;
            Window.setState(Window.ID_INGAMEMENU);
        }
    }
    
    public static Image getBackground() {
        return Game.background;
    }

}
