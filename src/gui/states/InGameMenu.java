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
    
    private Image overlay;

    @Override
    public int getID() {
        return Window.ID_INGAMEMENU;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        long time = System.currentTimeMillis();
        overlay = Resources.getImage("mapOverlay");
        System.out.println("InitInGameMenu: " + (System.currentTimeMillis() - time) + " ms");
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.drawImage(Game.getBackground(), 0, 0);
        Camera.renderMap(g);
        Camera.renderEntity(g);
        g.drawImage(overlay, 0, 0);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        changeState();
    }

    @Override
    public void update(Observable o, Object arg) {
        
    }
    
    private void changeState() {
        if (MyKeyboard.keyboard[Input.KEY_ESCAPE]) {
            MyKeyboard.keyboard[Input.KEY_ESCAPE] = false;
            Window.setState(Window.ID_GAME);
        }
    }
    
}
