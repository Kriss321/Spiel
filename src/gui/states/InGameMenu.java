/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.states;

import gui.Camera;
import gui.Window;
import input.MyKeyboard;
import java.util.Observable;
import java.util.Observer;
import main.Resources;
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
public class InGameMenu extends BasicGameState implements Observer {
    
    private static int leftSidePause;
    private static int topSidePause;
    
    private Image overlay;
    private static Image pause;

    @Override
    public int getID() {
        return Window.ID_INGAMEMENU;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        long time = System.currentTimeMillis();
        overlay = Resources.getImage("mapOverlay");
        pause = Resources.getImage("Pause");
        leftSidePause = (container.getWidth() - pause.getWidth()) / 2;
        topSidePause = 75;
        System.out.println("InitInGameMenu: " + (System.currentTimeMillis() - time) + " ms");
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.drawImage(Game.getBackground(), 0, 0);
        Camera.setScale(g);
        Camera.renderMap(g);
        Camera.renderEntity(g);
        Camera.removeScale(g);
        g.drawImage(overlay, 0, 0);
        g.drawImage(pause, leftSidePause, topSidePause);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        changeState();
        Window.fullScreen(container);
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof Object[]) {
            Object[] argument = (Object[])arg;
            if (argument[0] instanceof String) {
                String s = (String)argument[0];
                if (s.equalsIgnoreCase("fullscreen") && argument[1] instanceof GameContainer) {
                    GameContainer container = (GameContainer)argument[1];
                    leftSidePause = (container.getWidth() - pause.getWidth()) / 2;
                    //topSidePause = (container.getHeight()- pause.getHeight()) / 2;
                }
                
            }
            
        }
    }
    
    private void changeState() {
        if (MyKeyboard.keyboard[Input.KEY_ESCAPE]) {
            MyKeyboard.keyboard[Input.KEY_ESCAPE] = false;
            Window.setState(Window.ID_GAME);
        }
    }
    
}
