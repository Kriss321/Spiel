/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import gui.Window;
import input.TextFieldArea;
import java.util.Observable;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Kristof
 */
public class Model extends Observable {
    
    private final int windowHeight;
    private final int windowWidth;

    public Model(GameContainer container) {
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
    
    public void textFieldAreaAction(TextFieldArea textFieldArea, String text) {
        Object[] temp = new Object[2];
        if (text.endsWith(" Player")) {
            temp[0] = "playerSelect";
            temp[1] = Integer.parseInt(String.valueOf(text.charAt(0)))-1;
        } else {
            temp[0] = "mapSelect";
            temp[1] = text;
        }
        
        setChanged();
        notifyObservers(temp);
    }
    
    public void buttonAction() {
        setChanged();
        Object[] temp = new Object[2];
        temp[0] = "Start";
        notifyObservers(temp);
    }
}
