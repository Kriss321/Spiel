/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package input;

import gui.BasedGame;
import org.newdawn.slick.Image;
import org.newdawn.slick.gui.GUIContext;

/**
 *
 * @author Kristof
 */
public class Button extends MyMouseOverArea {
    
    /** The function of the Button */
    protected String function = "";
    
    public Button(GUIContext container, Image image, int x, int y, String function) {
        super(container, image, x, y);
        this.function = function;
    }
    
    public Button(GUIContext container, Image image, int x, int y, int width, int height, String function) {
        super(container, image, x, y, width, height);
        this.function = function;
    }
    
    @Override
    public void mousePressed(int button, int mx, int my) {
        super.mousePressed(button, mx, my);
        if (this.isMouseOver()) {
            if(BasedGame.state == BasedGame.ID_MENU) {
                switch(function) {
                    case "start":
                        BasedGame.modelMenu.start();
                }
            }
        }
    }
    
}
