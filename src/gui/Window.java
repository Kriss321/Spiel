/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import main.Resources;
import main.Engine;
import gui.states.Game;
import gui.states.InGameMenu;
import gui.states.Menu;
import input.MyKeyboard;
import input.MyMouse;
import main.Config;
import main.Model;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Kristof
 */
public class Window extends StateBasedGame{
    
    public static int state = 0;
    public final static int ID_MENU = 0x00;
    public final static int ID_GAME = 0x01;
    public final static int ID_INGAMEMENU = 0x02;
    
    
    public static StateBasedGame stateBasedGame;
    
    public static Model model;

    public Window() {
        super("First Test");
        stateBasedGame = this;
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        long time = System.currentTimeMillis();
        Config.loadConfig();
        Resources.loadResources();
        Engine.loadEngine(container);
        MyKeyboard.loadMyKeyboard(container);
        MyMouse.loadMyMouse(container);
        
        Window.model = new Model(container);
        Menu menu = new Menu();
        Game game = new Game();
        InGameMenu inGameMenu = new InGameMenu();
        
        Window.model.addObserver(menu);
        Window.model.addObserver(game);
        Window.model.addObserver(inGameMenu);
        
        this.addState(menu);
        this.addState(game);
        this.addState(inGameMenu);

        System.out.println("Game: " + (System.currentTimeMillis() - time) + " ms");
    }
    
    public static void fullScreen(GameContainer container) {
        if (MyKeyboard.keyboard[Input.KEY_F11]) {
            MyKeyboard.keyboard[Input.KEY_F11] = false;
            Window.model.fullScreen(container);
        }
    }
    
    public static void setState(int state) {
        Window.model.setState(state);
    }
}
