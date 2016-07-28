/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.states.models;

import gui.BasedGame;
import input.MyKeyboard;
import main.Main;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Kristof
 */
public class Model {
    
    private final int windowHeight;
    private final int windowWidth;

    private ModelMenu modelMenu;
    private ModelGame modelGame;
    private ModelInGameMenu modelInGameMenu;

    public Model(GameContainer container) {
        this.windowHeight = container.getHeight();
        this.windowWidth = container.getWidth();
    }

    public void fullScreen(GameContainer container) {
        if (MyKeyboard.keyboard[Input.KEY_F11]) {
            MyKeyboard.keyboard[Input.KEY_F11] = false;
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
            
            this.modelMenu.fullScreen(container);
            this.modelGame.fullScreen(container);
            this.modelInGameMenu.fullScreen(container);
        }
    }
    
    public void setState(int state) {
        Main.basedGame.enterState(state);
        BasedGame.state = state;
        if (BasedGame.state == BasedGame.ID_GAME) {
            this.modelGame.changeState(Main.container);
        }
    }

    public void setModelMenu(ModelMenu modelMenu) {
        this.modelMenu = modelMenu;
    }

    public void setModelGame(ModelGame modelGame) {
        this.modelGame = modelGame;
    }

    public void setModelInGameMenu(ModelInGameMenu modelInGameMenu) {
        this.modelInGameMenu = modelInGameMenu;
    }
    
    public void fps() {
        if (MyKeyboard.keyboard[Input.KEY_J]) {
            Main.container.setTargetFrameRate(30);
        }
        if (MyKeyboard.keyboard[Input.KEY_N]) {
            Main.container.setTargetFrameRate(40);
        }
        if (MyKeyboard.keyboard[Input.KEY_M]) {
            Main.container.setTargetFrameRate(50);
        }
        if (MyKeyboard.keyboard[Input.KEY_K]) {
            Main.container.setTargetFrameRate(60);
        }
        if (MyKeyboard.keyboard[Input.KEY_L]) {
            Main.container.setTargetFrameRate(120);
        }
    }
}
