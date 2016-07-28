/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.states.models;

import gui.Camera;
import java.util.Observable;
import main.Resources;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

/**
 *
 * @author Kristof
 */
public class ModelGame extends Observable {

    private final Model model;

    private Image background;

    public ModelGame(GameContainer container, Model model) {
        this.model = model;
        this.background = Resources.getImage("Game");
    }
    
    public void fullScreen(GameContainer container) {
        Camera.calcScale(container);
    }
    
    public void changeState(GameContainer container) {
        Camera.calcScale(container);
    }

    public Image getBackground() {
        return this.background;
    }

    public void setBackground(Image background) {
        this.background = background;
    }
    
}
