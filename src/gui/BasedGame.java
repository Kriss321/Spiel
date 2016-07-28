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
import gui.states.models.*;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Kristof
 */
public class BasedGame extends StateBasedGame {
    
    public static int state = 0;
    public final static int ID_MENU = 0x00;
    public final static int ID_GAME = 0x01;
    public final static int ID_INGAMEMENU = 0x02;
    
    public static Model model;
    public static ModelMenu modelMenu;
    public static ModelGame modelGame;
    public static ModelInGameMenu modelInGameMenu;

    public BasedGame() {
        super("First Test");
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        long time = System.currentTimeMillis();
        Config.loadConfig();
        Resources.loadResources();
        Engine.loadEngine(container);
        MyKeyboard.loadMyKeyboard(container);
        MyMouse.loadMyMouse(container);
        
        BasedGame.model = new Model(container);
        BasedGame.modelMenu = new ModelMenu(container, BasedGame.model);
        BasedGame.modelGame = new ModelGame(container, BasedGame.model);
        BasedGame.modelInGameMenu = new ModelInGameMenu(container, BasedGame.model);
        
        BasedGame.model.setModelMenu(modelMenu);
        BasedGame.model.setModelGame(modelGame);
        BasedGame.model.setModelInGameMenu(modelInGameMenu);
        
        Menu menu = new Menu(model, modelMenu);
        Game game = new Game(model, modelGame);
        InGameMenu inGameMenu = new InGameMenu(model, modelInGameMenu);
        
        BasedGame.modelMenu.addObserver(menu);
        BasedGame.modelGame.addObserver(game);
        BasedGame.modelInGameMenu.addObserver(inGameMenu);
        
        this.addState(menu);
        this.addState(game);
        this.addState(inGameMenu);

        System.out.println("Game: " + (System.currentTimeMillis() - time) + " ms");
    }

}
