/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import org.newdawn.slick.GameContainer;

/**
 *
 * @author Kristof
 */
public class Engine {
    
    public Engine(GameContainer container){
        container.setVSync(true);
        container.setShowFPS(false);
    }
    
}
