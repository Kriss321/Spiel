/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package input;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;

/**
 *
 * @author Kristof
 */
public class MyMouse implements MouseListener {
    
    public static boolean[] mouse = new boolean[5];

    public static void loadMyMouse(GameContainer container) {
        container.getInput().addMouseListener(new MyMouse());
    }

    @Override
    public void mouseWheelMoved(int change) {
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        
    }

    @Override
    public void mousePressed(int button, int x, int y) {
        mouse[button] = true;
    }

    @Override
    public void mouseReleased(int button, int x, int y) {
        mouse[button] = false;
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
    }

    @Override
    public void setInput(Input input) {
    }

    @Override
    public boolean isAcceptingInput() {
        return true;
    }

    @Override
    public void inputEnded() {
    }

    @Override
    public void inputStarted() {
    }
    
}
