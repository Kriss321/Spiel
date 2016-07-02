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
    
    public static void loadMyKeyboard(GameContainer container){
        container.getInput().addKeyListener(new MyKeyboard());
    }

    @Override
    public void keyPressed(int key, char c) {
        
        if (key == Input.KEY_F3) {
            keyboard[key] = !keyboard[key];
        } else {
            keyboard[key] = true;
        }
    }

    @Override
    public void keyReleased(int key, char c) {
        if (key != Input.KEY_F3) {
            keyboard[key] = false;
        }
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
