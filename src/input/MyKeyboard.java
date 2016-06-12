/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package input;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

/**
 *
 * @author Kristof
 */
public class MyKeyboard implements KeyListener {
    
    public static boolean[] keyboard = new boolean[250];
    
    private GameContainer container;
    
    public MyKeyboard(GameContainer container){
        container.getInput().addKeyListener(this);
        this.container = container;
    }

    @Override
    public void keyPressed(int key, char c) {
        keyboard[key] = !keyboard[key];
    }

    @Override
    public void keyReleased(int key, char c) {
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
