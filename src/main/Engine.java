/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Kristof
 */
public class Engine {
    
    private static int windowHeight;
    private static int windowWidth;
    
    public static void loadEngine(GameContainer container){
        container.setVSync(true);
        container.setShowFPS(false);
        container.setMultiSample(2);
        windowHeight = container.getHeight();
        windowWidth = container.getWidth();
    }
    
    public static void fullScreen(GameContainer container) {
        if (!container.isFullscreen()) {
            try {
                Main.container.setDisplayMode(container.getScreenWidth(), container.getScreenHeight(), true);
            } catch (SlickException ex) {
                System.err.println("Fullscreen not possible");
            }
        } else {
            try {
                Main.container.setDisplayMode(windowWidth, windowHeight, false);
            } catch (SlickException ex) {
                System.err.println("Fullscreen not possible");
            }
        }
    }
    
}
