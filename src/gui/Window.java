/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import main.Resources;
import main.Engine;
import gui.states.Game;
import gui.states.Menu;
import input.MyKeyboard;
import input.MyMouse;
import main.Config;
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
        long time = System.currentTimeMillis();
        new Config();
        new Engine(container);
        new Resources();
        new MyKeyboard(container);
        new MyMouse(container);
        this.addState(new Menu());
        this.addState(new Game());
        System.out.println("Game: " + (System.currentTimeMillis() - time) + " ms");
    }
    
}
