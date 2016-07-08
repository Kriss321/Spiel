/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import gui.Window;
import java.util.Observable;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Kristof
 */
public class MyObservable extends Observable {
    
    private final int windowHeight;
    private final int windowWidth;

    public MyObservable(GameContainer container) {
        this.windowHeight = container.getHeight();
        this.windowWidth = container.getWidth();
    }

    public void fullScreen(GameContainer container) {
        if (!container.isFullscreen()) {
            try {
                Main.container.setDisplayMode(container.getScreenWidth(), container.getScreenHeight(), true);
            } catch (SlickException ex) {
                System.err.println("Fullscreen not possible");
            }
        } else {
            try {
                Main.container.setDisplayMode(windowWidth, windowHeight, false);
            } catch (SlickException ex) {
                System.err.println("Fullscreen not possible");
            }
        }
        setChanged();
        Object[] temp = new Object[2];
        temp[0] = "fullscreen";
        temp[1] = container;
        notifyObservers(temp);
    }
    
    public void setState(int state) {
        Window.stateBasedGame.enterState(state);
        Window.state = state;
        setChanged();
        Object[] temp = new Object[2];
        temp[0] = "changeState";
        temp[1] = state;
        notifyObservers(temp);
    }
}
