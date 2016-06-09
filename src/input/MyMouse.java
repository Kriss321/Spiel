/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package input;

import gui.Window;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;

/**
 *
 * @author Kristof
 */
public class MyMouse implements MouseListener {
    
    public static boolean[] mouse = new boolean[5]; 
    
    private GameContainer container;

    public MyMouse(GameContainer container) {
        container.getInput().addMouseListener(this);
        this.container = container;
    }

    @Override
    public void mouseWheelMoved(int change) {
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        if(Window.state == 0){
            Window.game.enterState(1);
            Window.state = 1;
        }
        
    }

    @Override
    public void mousePressed(int button, int x, int y) {
        System.out.println("pressed");
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
