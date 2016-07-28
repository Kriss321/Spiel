/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import gui.BasedGame;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Kristof
 */
public class Main {
    
    public static AppGameContainer container;
    public static BasedGame basedGame;
    
    /**
     * Entry point to our test
     *
     * @param argv The arguments to pass into the test
     */
    public static void main(String[] argv) {
        try {
            container = new AppGameContainer(basedGame = new BasedGame());
            container.setDisplayMode(1280, 768, false);
            container.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
    
    
    
}
