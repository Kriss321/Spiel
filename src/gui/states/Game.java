/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.states;

import entity.EntityManager;
import gui.Camera;
import gui.BasedGame;
import gui.states.models.Model;
import gui.states.models.ModelGame;
import input.MyKeyboard;
import java.util.Observable;
import java.util.Observer;
import map.MapManager;
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
public class Game extends BasicGameState implements Observer {
    
    private final Model model;
    private final ModelGame modelGame;
    
    private Image background;

    public Game(Model model, ModelGame modelGame) {
        this.model = model;
        this.modelGame = modelGame;
    }

    @Override
    public int getID() {
        return BasedGame.ID_GAME;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        long time = System.currentTimeMillis();
        update(this.modelGame, this.modelGame);
        System.out.println("InitGame: " + (System.currentTimeMillis() - time) + " ms");
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.drawImage(this.background, 0, 0);
        Camera.setScale(g);
        Camera.renderMap(g);
        Camera.renderEntity(g);
        Camera.removeScale(g);
        debug(container, g);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        Camera.calcMapPos(container);
        EntityManager.moveEntitys(delta);
        this.model.fullScreen(container);
        changeState();
        this.model.fps();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof ModelGame) {
            this.background = this.modelGame.getBackground();
        }
    }

    public static void loadMap(String name){
        MapManager.loadMap(name);
    }

    private void debug(GameContainer container, Graphics g){
        if (MyKeyboard.keyboard[Input.KEY_F3]) {
            g.drawString("FPS: " + container.getFPS(), 10, 10);
            g.drawString("Map: " + Camera.mapPosX + " - " + Camera.mapPosY, 100, 10);
            EntityManager.debug(g);
        }
    }

    private void changeState() {
        if (MyKeyboard.keyboard[Input.KEY_ESCAPE]) {
            MyKeyboard.keyboard[Input.KEY_ESCAPE] = false;
            this.model.setState(BasedGame.ID_INGAMEMENU);
        }
    }

}
