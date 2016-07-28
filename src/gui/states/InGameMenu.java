/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.states;

import gui.Camera;
import gui.BasedGame;
import gui.states.models.Model;
import gui.states.models.ModelInGameMenu;
import input.Button;
import input.MyKeyboard;
import java.util.Observable;
import java.util.Observer;
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
    
    private final Model model;
    private final ModelInGameMenu modelInGameMenu;
    
    private int leftSidePauseCord;
    private int topSidePauseCord;
    
    private Image overlay;
    private Image pause;
    
    private Button menu;

    public InGameMenu(Model model, ModelInGameMenu modelInGameMenu) {
        this.model = model;
        this.modelInGameMenu = modelInGameMenu;
    }

    @Override
    public int getID() {
        return BasedGame.ID_INGAMEMENU;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        long time = System.currentTimeMillis();
        update(modelInGameMenu, modelInGameMenu);
        System.out.println("InitInGameMenu: " + (System.currentTimeMillis() - time) + " ms");
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.drawImage(BasedGame.modelGame.getBackground(), 0, 0);
        Camera.setScale(g);
        Camera.renderMap(g);
        Camera.renderEntity(g);
        Camera.removeScale(g);
        g.drawImage(overlay, 0, 0);
        g.drawImage(pause, leftSidePauseCord, topSidePauseCord);
        menu.render(container, g);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        changeState();
        this.model.fullScreen(container);
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof ModelInGameMenu) {
            leftSidePauseCord = modelInGameMenu.getLeftSidePauseCord();
            topSidePauseCord = modelInGameMenu.getTopSidePauseCord();
            overlay = modelInGameMenu.getOverlay();
            pause = modelInGameMenu.getPause();
            menu = modelInGameMenu.getMenu();
        }
    }

    public void changeState() {
        if (MyKeyboard.keyboard[Input.KEY_ESCAPE]) {
            MyKeyboard.keyboard[Input.KEY_ESCAPE] = false;
            model.setState(BasedGame.ID_GAME);
        }
    }
}
