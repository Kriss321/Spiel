/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.states.models;

import entity.Entity;
import entity.EntityManager;
import gui.BasedGame;
import input.Button;
import java.util.Observable;
import main.Resources;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

/**
 *
 * @author Kristof
 */
public class ModelInGameMenu extends Observable {
    
    private final Model model;
    
    private int leftSidePauseCord;
    private int topSidePauseCord;
    
    private Image overlay;
    private Image pause;
    
    private Button menu;
    private Button exit;
    private Button reload;

    public ModelInGameMenu(GameContainer container, Model model) {
        this.model = model;
        this.overlay = Resources.getImage("mapOverlay");
        this.pause = Resources.getImage("Pause");
        this.leftSidePauseCord = (container.getWidth() - this.pause.getWidth()) / 2;
        this.topSidePauseCord = 75;
        
        Image btnMenu = Resources.getImage("btnBack");
        this.menu = new Button(container, btnMenu, (container.getWidth() - btnMenu.getWidth()) / 2, 250, "back");
        this.menu.setMouseOverImage(Resources.getImage("btnBackOver"));
        this.menu.setMouseDownImage(Resources.getImage("btnBackClick"));

        Image btnReload = Resources.getImage("btnReload");
        this.reload = new Button(container, btnReload, (container.getWidth() - btnMenu.getWidth()) / 2, 310, "reload");
        this.reload.setMouseOverImage(Resources.getImage("btnReloadOver"));
        this.reload.setMouseDownImage(Resources.getImage("btnReloadClick"));

        Image btnExit = Resources.getImage("btnExit");
        this.exit = new Button(container, btnExit, (container.getWidth() - btnMenu.getWidth()) / 2, 370, "exit");
        this.exit.setMouseOverImage(Resources.getImage("btnExitOver"));
        this.exit.setMouseDownImage(Resources.getImage("btnExitClick"));
    }
    
    public void fullScreen(GameContainer container) {
        leftSidePauseCord = (container.getWidth() - pause.getWidth()) / 2;
        //topSidePauseCord = (container.getHeight()- pause.getHeight()) / 2;
        this.menu.setX((container.getWidth() - this.menu.getWidth()) / 2);
        setChanged();
        notifyObservers(this);
    }

    public void reload() {
        for (Entity entity : EntityManager.entitys) {
            entity.reset();
        }
        model.setState(BasedGame.ID_GAME);
    }

    public void exit() {
        model.setState(BasedGame.ID_MENU);
    }
    
    public void back() {
        model.setState(BasedGame.ID_GAME);
    }

    public int getLeftSidePauseCord() {
        return this.leftSidePauseCord;
    }

    public void setLeftSidePauseCord(int leftSidePauseCord) {
        this.leftSidePauseCord = leftSidePauseCord;
    }

    public int getTopSidePauseCord() {
        return this.topSidePauseCord;
    }

    public void setTopSidePauseCord(int topSidePauseCord) {
        this.topSidePauseCord = topSidePauseCord;
    }

    public Image getOverlay() {
        return this.overlay;
    }

    public void setOverlay(Image overlay) {
        this.overlay = overlay;
    }

    public Image getPause() {
        return this.pause;
    }

    public void setPause(Image pause) {
        this.pause = pause;
    }

    public Button getMenu() {
        return this.menu;
    }

    public void setMenu(Button menu) {
        this.menu = menu;
    }

    public Button getExit() {
        return exit;
    }

    public void setExit(Button exit) {
        this.exit = exit;
    }

    public Button getReload() {
        return reload;
    }

    public void setReload(Button reload) {
        this.reload = reload;
    }
    
}
