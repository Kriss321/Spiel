/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package input;

import gui.Window;
import org.newdawn.slick.Image;
import org.newdawn.slick.gui.GUIContext;

/**
 *
 * @author Kristof
 */
public class Button extends MyMouseOverArea {
    
    public Button(GUIContext container, Image image, int x, int y) {
        super(container, image, x, y);
    }
    
    public Button(GUIContext container, Image image, int x, int y, int width, int height) {
        super(container, image, x, y, width, height);
    }
    
    @Override
    public void mousePressed(int button, int mx, int my) {
        super.mousePressed(button, mx, my);
        if (this.isMouseOver()) {
            Window.model.buttonAction();
        }
    }
    
}
