/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.states;

import input.MyMouse;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Kristof
 */
public class Menu extends BasicGameState {
    
    private int x;
    private int y;
    
    private Image background;
    private Image btnStart;

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        background = new Image("res/Background.jpg");
        btnStart = new Image("res/btnStart.png");
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.drawImage(background, 0, 0);
        g.drawImage(btnStart, 300, 264);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        x = Mouse.getX();
        y = Mouse.getY();
        if((x >= 300) && (x <= 500) && (y >= 264) && (y <= 336)){
            if(MyMouse.mouse[Input.MOUSE_LEFT_BUTTON]){
                btnStart = new Image("res/btnStartClick.png");
            } else {
                btnStart = new Image("res/btnStartHover.png");
            }
        } else {
            btnStart = new Image("res/btnStart.png");
        }
    }
    
}
