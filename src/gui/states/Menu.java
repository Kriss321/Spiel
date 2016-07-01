/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.states;

import entity.EntityManager;
import main.Resources;
import gui.Window;
import input.MyKeyboard;
import input.MyMouse;
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
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Kristof
 */
public class Menu extends BasicGameState {
    
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
    
    private static Object[] maps;
    private static Color[] mapSelect;
    private static Color[] playerSelect;
    
    AngelCodeFont fontHeadline;
    AngelCodeFont font;

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        long time = System.currentTimeMillis();
        background = Resources.getImage("Main");
        btnStart = Resources.getImage("btnStart");
        leftSideButton = (container.getWidth() - btnStart.getWidth()) / 2;
        topSideButton = (container.getHeight() - btnStart.getHeight()) / 2;

        fontHeadline = Resources.getFont("Comic Sans MS", "32 - bold italic");
        font = Resources.getFont("Comic Sans MS", "28 - bold");
        Set<String> temp;
        maps = Resources.getAllMaps().toArray();
        mapSelect = new Color[maps.length];
        for (int i = 0; i < mapSelect.length; i++) {
            mapSelect[i] = Color.white;
        }
        
        System.out.println("InitMenu: " + (System.currentTimeMillis()-time) + " ms");
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.drawImage(background, 0, 0);
        g.drawImage(btnStart, leftSideButton, topSideButton);

        debug(container, g);
        renderMapList(container, g);
        renderPlayerCountList(container, g);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        mouseX = Mouse.getX();
        mouseY = Math.abs(Mouse.getY()-container.getHeight());
        mouseEvent();
    }
    
    public static void mouseClicked(){
        if ((mouseX >= leftSideButton) && (mouseX <= (leftSideButton + btnStart.getWidth())) && (mouseY >= topSideButton) && (mouseY <= (topSideButton + btnStart.getHeight()))) {
            if (selectedMap != -1) {
                EntityManager.loadEntitys(selectedPlayer+1);
                Window.game.enterState(1);
                Window.state = 1;
            }
        } else if ((mouseX >= xMapList) && (mouseX <= (xMapList + 200)) && (mouseY >= yMapList) && (mouseY <= (yMapList + 45 + maps.length * 28))) {
            for (int i = 0; i < maps.length; i++) {
                if (mouseY >= yMapList + 38 + (i * 28) && mouseY <= yMapList + 38 + 28 + (i * 28)) {
                    if (mapSelect[i] != Color.cyan) {
                        mapSelect[i] = Color.cyan;
                        if (selectedMap != -1) {
                            mapSelect[selectedMap] = Color.white;
                        }
                        selectedMap = i;
                        Game.loadMap(selectedMap());
                        playerSelect = new Color[MapManager.playerCount];
                        playerSelect[0] = Color.green;
                        selectedPlayer = 0;
                        for (int x = 1; x < playerSelect.length; x++) {
                            playerSelect[x] = Color.white;
                        }
                    } else {
                        mapSelect[i] = Color.blue;
                        selectedMap = -1;
                    }
                }
            }

        } else if (selectedMap != -1 && (mouseX >= xPlayerList) && (mouseX <= (xPlayerList + 200)) && (mouseY >= yPlayerList) && (mouseY <= (yPlayerList + 45 + MapManager.playerCount * 28))) {
            for (int i = 0; i < MapManager.playerCount; i++) {
                if (mouseY >= yPlayerList + 38 + (i * 28) && mouseY <= yPlayerList + 38 + 28 + (i * 28)) {
                    if (selectedPlayer != i) {
                        playerSelect[i] = Color.cyan;
                        if (selectedPlayer != -1) {
                            playerSelect[selectedPlayer] = Color.white;
                        }
                        selectedPlayer = i;
                    }
                }
            }
        }
    }
    
    public static String selectedMap(){
        return (String) maps[selectedMap];
    }
    
    private void mouseEvent(){
        if ((mouseX >= leftSideButton) && (mouseX <= (leftSideButton + btnStart.getWidth())) && (mouseY >= topSideButton) && (mouseY <= (topSideButton + btnStart.getHeight()))) {
            if (MyMouse.mouse[Input.MOUSE_LEFT_BUTTON]) {
                btnStart = Resources.getImage("btnStartClick");
            } else {
                btnStart = Resources.getImage("btnStartHover");
            }
        } else if ((mouseX >= xMapList) && (mouseX <= (xMapList + 200)) && (mouseY >= yMapList) && (mouseY <= (yMapList + 45 + maps.length * 28))) {

            for (int i = 0; i < maps.length; i++) {
                if (mouseY >= yMapList + 38 + (i * 28) && mouseY <= yMapList + 38 + 28 + (i * 28)) {
                    if (selectedMap == i) {
                        mapSelect[i] = Color.cyan;
                    } else {
                        mapSelect[i] = Color.blue;
                    }
                }
            }
            for (int i = 0; i < maps.length; i++) {
                if (mouseY <= yMapList + 38 + (i * 28) || mouseY >= yMapList + 38 + 28 + (i * 28)) {
                    if (selectedMap == i) {
                        mapSelect[i] = Color.green;
                    } else {
                        mapSelect[i] = Color.white;
                    }
                }
            }

        } else if (selectedMap != -1 && (mouseX >= xPlayerList) && (mouseX <= (xPlayerList + 200)) && (mouseY >= yPlayerList) && (mouseY <= (yPlayerList + 45 + MapManager.playerCount * 28))) {

            for (int i = 0; i < MapManager.playerCount; i++) {
                if (mouseY >= yPlayerList + 38 + (i * 28) && mouseY <= yPlayerList + 38 + 28 + (i * 28)) {
                    if (selectedPlayer == i) {
                        playerSelect[i] = Color.cyan;
                    } else {
                        playerSelect[i] = Color.blue;
                    }
                }
            }
            for (int i = 0; i < MapManager.playerCount; i++) {
                if (mouseY <= yPlayerList + 38 + (i * 28) || mouseY >= yPlayerList + 38 + 28 + (i * 28)) {
                    if (selectedPlayer == i) {
                        playerSelect[i] = Color.green;
                    } else {
                        playerSelect[i] = Color.white;
                    }
                }
            }

        } else {
            btnStart = Resources.getImage("btnStart");
            for (int i = 0; i < maps.length; i++) {
                if (selectedMap == i) {
                    mapSelect[i] = Color.green;
                } else {
                    mapSelect[i] = Color.white;
                }
            }
            if (selectedMap != -1) {
                for (int i = 0; i < MapManager.playerCount; i++) {
                    if (selectedPlayer == i) {
                        playerSelect[i] = Color.green;
                    } else {
                        playerSelect[i] = Color.white;
                    }
                }
            }
        }
    }
    
    private void debug(GameContainer container, Graphics g) {
        if (MyKeyboard.keyboard[Input.KEY_F3]) {
            g.drawString("FPS: " + container.getFPS(), 10, 10);
            g.drawString("Mouse x: " + String.valueOf(mouseX) + ", y: " + String.valueOf(mouseY), 10, 25);
            g.drawString("Map: " + String.valueOf(selectedMap), 10, 40);
        }
    }
    
    private void renderMapList(GameContainer container, Graphics g) {
        xMapList = leftSideButton/2-100;
        yMapList = container.getHeight()/2 - (45+maps.length*28)/2;
        g.setColor(Color.lightGray);
        g.fillRoundRect(xMapList, yMapList, 200, 45+maps.length*28, 3);
        fontHeadline.drawString(xMapList+50, yMapList+5, "Maps:", Color.orange);
        int y = yMapList+35;
        for (int i = 0; i < maps.length; i++) {
            String temp = maps[i].toString();
            font.drawString(xMapList+10, y, temp, mapSelect[i]);
            y += 28;
        }
    }
    
    private void renderPlayerCountList(GameContainer container, Graphics g) {
        if (selectedMap != -1) {
            xPlayerList = leftSideButton + btnStart.getWidth() + leftSideButton / 2 - 100;
            yPlayerList = container.getHeight() / 2 - (45 + MapManager.playerCount * 28) / 2;
            g.setColor(Color.lightGray);
            g.fillRoundRect(xPlayerList, yPlayerList, 200, 45 + MapManager.playerCount * 28, 3);
            fontHeadline.drawString(xPlayerList+20, yPlayerList+5, "Spieleranzahl:", Color.orange);
            int y = yPlayerList+35;
            for (int i = 0; i < MapManager.playerCount; i++) {
                font.drawString(xPlayerList+10, y, (i+1) + " Player", playerSelect[i]);
                y += 28;
            }
        }
    }
}
