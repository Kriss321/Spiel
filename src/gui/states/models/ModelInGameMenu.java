/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.states.models;

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

    public ModelInGameMenu(GameContainer container, Model model) {
        this.model = model;
        this.overlay = Resources.getImage("mapOverlay");
        this.pause = Resources.getImage("Pause");
        this.leftSidePauseCord = (container.getWidth() - this.pause.getWidth()) / 2;
        this.topSidePauseCord = 75;
        
        Image btnMenu = Resources.getImage("btnMainMenu");
        this.menu = new Button(container, btnMenu, (container.getWidth() - btnMenu.getWidth()) / 2, 250, "mainMenu");
        this.menu.setMouseOverImage(Resources.getImage("btnMainMenuOver"));
    }
    
    public void fullScreen(GameContainer container) {
        leftSidePauseCord = (container.getWidth() - pause.getWidth()) / 2;
        //topSidePauseCord = (container.getHeight()- pause.getHeight()) / 2;
        this.menu.setX((container.getWidth() - this.menu.getWidth()) / 2);
        setChanged();
        notifyObservers(this);
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
    
}
