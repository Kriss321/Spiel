/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.states;

import gui.BasedGame;
import gui.states.models.Model;
import gui.states.models.ModelMenu;
import input.Button;
import input.MyKeyboard;
import input.TextFieldArea;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import map.MapManager;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
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
public class Menu extends BasicGameState implements Observer {
    
    private final Model model;
    private final ModelMenu modelMenu;
    
    public static GameContainer container;
    
    private int xCordMapArea;
    private int yCordMapArea;
    private int xCordPlayerArea;
    private int yCordPlayerArea;
    private int selectedMap = -1;
    private int selectedPlayer = -1;
    
    private Image background;
	
    private List maps;
    private TextFieldArea[] mapArea;
    private TextFieldArea[] playerArea;
    
    private Button play;

    private AngelCodeFont fontHeadline;
    
    public Menu(Model model, ModelMenu modelGame) {
        this.model = model;
        this.modelMenu = modelGame;
    }

    @Override
    public int getID() {
        return BasedGame.ID_MENU;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        long time = System.currentTimeMillis();
        update(this.modelMenu, this.modelMenu);

        System.out.println("InitMenu: " + (System.currentTimeMillis()-time) + " ms");
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.drawImage(this.background, 0, 0);
        this.play.render(container, g);
        renderMapList(container, g);
        renderPlayerList(container, g);
        
        debug(container, g);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        this.model.fullScreen(container);
        this.modelMenu.playerAreaLoad(container);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof ModelMenu) {
            this.background      = this.modelMenu.getBackground();
            this.play            = this.modelMenu.getPlay();
            
            this.maps            = this.modelMenu.getMaps();
            this.mapArea         = this.modelMenu.getMapArea();
            this.xCordMapArea    = this.modelMenu.getxCordMapArea();
            this.yCordMapArea    = this.modelMenu.getyCordMapArea();
                    
            this.playerArea      = this.modelMenu.getPlayerArea();
            this.xCordPlayerArea = this.modelMenu.getxCordPlayerArea();
            this.yCordPlayerArea = this.modelMenu.getyCordPlayerArea();
            
            this.selectedMap     = this.modelMenu.getSelectedMap();
            this.selectedPlayer  = this.modelMenu.getSelectedPlayer();
            
            this.fontHeadline    = this.modelMenu.getFontHeadline();
        }
    }

    private void debug(GameContainer container, Graphics g) {
        if (MyKeyboard.keyboard[Input.KEY_F3]) {
            g.drawString("FPS: " + container.getFPS(), 10, 10);
            g.drawString("Mouse x: " + String.valueOf(Mouse.getX()) + ", y: " + String.valueOf(Mouse.getY()), 10, 25);
            g.drawString("Map: " + String.valueOf(this.selectedMap), 10, 40);
        }
    }

    private void renderMapList(GameContainer container, Graphics g) {
        g.setColor(Color.lightGray);
        g.fillRoundRect(this.xCordMapArea, this.yCordMapArea, this.mapArea[0].getWidth(), 45 + this.maps.size() * this.mapArea[0].getHeight(), 3);
        this.fontHeadline.drawString(this.xCordMapArea + 50, this.yCordMapArea + 4, "Maps:", Color.orange);
        for (TextFieldArea textFieldArea : this.mapArea) {
            textFieldArea.render(container, g);
        }
    }

    private void renderPlayerList(GameContainer container, Graphics g) {
        if (this.selectedMap != -1 && this.selectedPlayer != -1) {
            g.setColor(Color.lightGray);
            g.fillRoundRect(this.xCordPlayerArea, this.yCordPlayerArea, 200, 45 + MapManager.playerCount * 28, 3);
            this.fontHeadline.drawString(this.xCordPlayerArea+20, this.yCordPlayerArea+5, "Spieleranzahl:", Color.orange);
            for (int i = 0; i < MapManager.playerCount; i++) {
                this.playerArea[i].render(container, g);
            }
        }
    }

}
