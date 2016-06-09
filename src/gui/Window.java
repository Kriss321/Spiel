/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import gui.states.Game;
import gui.states.Menu;
import input.MyKeyboard;
import input.MyMouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Kristof
 */
public class Window extends StateBasedGame{
    
    public static int state = 0;
    public static StateBasedGame game;

    public Window() {
        super("First Test");
        game = this;
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        new Engine(container);
        new MyKeyboard(container);
        new MyMouse(container);
        
        this.addState(new Menu());
        this.addState(new Game());
    }
    
}
