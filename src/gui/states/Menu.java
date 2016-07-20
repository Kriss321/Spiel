/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.states;

import entity.EntityManager;
import main.Resources;
import gui.Window;
import input.Button;
import input.MyKeyboard;
import input.MyMouse;
import input.TextFieldArea;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import map.MapManager;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Kristof
 */
public class Menu extends BasicGameState implements Observer {
    
    private GameContainer container;
    
    private static int mouseX;
    private static int mouseY;
    private static int xMapList;
    private static int yMapList;
    private static int xPlayerList;
    private static int yPlayerList;
    private static int leftSideButton;
    private static int topSideButton;
    private static int selectedMap = -1;
    private static int selectedPlayer = -1;
    
    private static Image background;
    private static Image btnStart;
    
    private static List maps;
    private static TextFieldArea[] mapArea;
    private static TextFieldArea[] playerArea;
    private static boolean mapSelected = false;
    
    private static Button play;

    private AngelCodeFont fontHeadline;
    private AngelCodeFont font;
    
    TextFieldArea test;
    MouseOverArea test2;
    
    TextField abc;

    @Override
    public int getID() {
        return Window.ID_MENU;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        long time = System.currentTimeMillis();
        this.container = container;
        background = Resources.getImage("Main");
        btnStart = Resources.getImage("btnStart");
        maps = Arrays.asList(Resources.getAllMaps().toArray());
        
        leftSideButton = (container.getWidth() - btnStart.getWidth()) / 2;
        topSideButton = (container.getHeight() - btnStart.getHeight()) / 2;
        xMapList = leftSideButton/2-100;
        yMapList = container.getHeight()/2 - (45+maps.size()*28)/2;

        fontHeadline = Resources.getFont("Comic Sans MS", "32 - bold italic");
        font = Resources.getFont("Comic Sans MS", "28 - bold");
        
        mapArea = new TextFieldArea[maps.size()];
        int width = 190;
        for (int i = 0; i < mapArea.length; i++) {
            mapArea[i] = new TextFieldArea(container, xMapList, yMapList + 35 + (28 * i), 200, 28);
            mapArea[i].setText(maps.get(i).toString());
            mapArea[i].setFont(font);
            if (font.getWidth(maps.get(i).toString()) > width) {
                width = font.getWidth(maps.get(i).toString());
            }
        }
        if (width > 190) {
            width += 15;
            xMapList = (leftSideButton-width)/2;
            for (TextFieldArea mapArea1 : mapArea) {
                mapArea1.setX(xMapList);
                mapArea1.setWidth(width);
            }
        }
        
        play = new Button(container, btnStart, leftSideButton, topSideButton);
        play.setMouseOverImage(Resources.getImage("btnStartHover"));
        play.setMouseDownImage(Resources.getImage("btnStartClick"));
        
        System.out.println("InitMenu: " + (System.currentTimeMillis()-time) + " ms");
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.drawImage(background, 0, 0);
        //g.drawImage(btnStart, leftSideButton, topSideButton);
        play.render(container, g);
        
        debug(container, g);
        renderMapList(container, g);
        renderPlayerList(container, g);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        mouseX = Mouse.getX();
        mouseY = Math.abs(Mouse.getY()-container.getHeight());
        Window.fullScreen(container);
        mapSelect(container);
    }
 
    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof Object[]) {
            Object[] argument = (Object[])arg;
            if ((argument.length > 0) && (argument[0] instanceof String)) {
                String s = (String)argument[0];
                if (s.equalsIgnoreCase("fullscreen") && (argument.length == 2) && argument[1] instanceof GameContainer) {
                    
                    GameContainer container = (GameContainer)argument[1];
                    leftSideButton = (container.getWidth() - btnStart.getWidth()) / 2;
                    topSideButton = (container.getHeight() - btnStart.getHeight()) / 2;
                    xMapList = (leftSideButton - mapArea[0].getWidth())/2;
                    yMapList = container.getHeight() / 2 - (45 + maps.size() * mapArea[0].getHeight()) / 2;
                    for (int i = 0; i < mapArea.length; i++) {
                        mapArea[i].setX(xMapList);
                        mapArea[i].setY(yMapList + 35 + (28 * i));
                    }
                    if (selectedMap != -1) {
                        xPlayerList = leftSideButton + btnStart.getWidth() + leftSideButton / 2 - 100;
                        yPlayerList = container.getHeight() / 2 - (45 + MapManager.playerCount * 28) / 2;
                        for (int i = 0; i < playerArea.length; i++) {
                            playerArea[i].setX(xPlayerList);
                            playerArea[i].setY(yPlayerList + 35 + (28 * i));
                        }
                    }
                    
                } else if (s.equalsIgnoreCase("mapSelect") && (argument.length == 2) && argument[1] instanceof String) {
                    
                    String mapName = (String)argument[1];
                    selectedPlayer = -1;
                    if (selectedMap != -1) {
                        mapArea[selectedMap].setSelected(false);
                        
                    } 
                    if (selectedMap != maps.indexOf(mapName)) {
                        selectedMap = maps.indexOf(mapName);
                        mapSelected = true;
                    } else {
                        selectedMap = -1;
                    }
                    
                    
                } else if (s.equalsIgnoreCase("playerSelect") && (argument.length == 2) && argument[1] instanceof Integer) {
                    
                    int playercount = (int)argument[1];
                    if (selectedPlayer != -1 && selectedPlayer != playercount) {
                        playerArea[selectedPlayer].setSelected(false);
                        selectedPlayer = playercount;
                    } else {
                        playerArea[selectedPlayer].setSelected(true);
                        selectedPlayer = playercount;
                    }
                    
                } else if (s.equalsIgnoreCase("Start")) {
                    
                    if (selectedMap != -1) {
                        EntityManager.loadEntitys(selectedPlayer + 1);
                        Window.setState(Window.ID_GAME);
                    }
                    
                }
            }
        }
    }

    private void mapSelect(GameContainer container){
        if (mapSelected && selectedMap != -1) {
            mapSelected = false;
            Game.loadMap(selectedMap());
            playerArea = new TextFieldArea[MapManager.playerCount];
            xPlayerList = leftSideButton + btnStart.getWidth() + leftSideButton / 2 - 100;
            yPlayerList = container.getHeight() / 2 - (45 + MapManager.playerCount * 28) / 2;
            for (int i = 0; i < playerArea.length; i++) {
                playerArea[i] = new TextFieldArea(container, xPlayerList, yPlayerList + 35 + (28 * i), 200, 28);
                playerArea[i].setText((i + 1) + " Player");
                playerArea[i].setFont(font);
            }
            playerArea[0].setSelected(true);
            selectedPlayer = 0;
        }
    }
    
    public static String selectedMap(){
        return (String) maps.get(selectedMap);
    }
    
    private void debug(GameContainer container, Graphics g) {
        if (MyKeyboard.keyboard[Input.KEY_F3]) {
            g.drawString("FPS: " + container.getFPS(), 10, 10);
            g.drawString("Mouse x: " + String.valueOf(Mouse.getX()) + ", y: " + String.valueOf(Mouse.getY()), 10, 25);
            g.drawString("Map: " + String.valueOf(selectedMap), 10, 40);
        }
    }
    
    private void renderMapList(GameContainer container, Graphics g) {
        g.setColor(Color.lightGray);
        g.fillRoundRect(xMapList, yMapList, mapArea[0].getWidth(), 45 + maps.size() * mapArea[0].getHeight(), 3);
        fontHeadline.drawString(xMapList+50, yMapList+4, "Maps:", Color.orange);
        for (TextFieldArea textFieldArea : mapArea) {
            textFieldArea.render(container, g);
        }
    }
    
    private void renderPlayerList(GameContainer container, Graphics g) {
        if (selectedMap != -1 && selectedPlayer != -1) {
            g.setColor(Color.lightGray);
            g.fillRoundRect(xPlayerList, yPlayerList, 200, 45 + MapManager.playerCount * 28, 3);
            fontHeadline.drawString(xPlayerList+20, yPlayerList+5, "Spieleranzahl:", Color.orange);
            for (TextFieldArea textFieldArea : playerArea) {
                textFieldArea.render(container, g);
            }
        }
    }
    
}
