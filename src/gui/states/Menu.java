/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.states;

import main.Resources;
import gui.Window;
import input.MyKeyboard;
import input.MyMouse;
import java.io.FileNotFoundException;
import java.util.Set;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Kristof
 */
public class Menu extends BasicGameState {
    
    private static int mouseX;
    private static int mouseY;
    private static int posX;
    private static int posY;
    private static int leftSideButton;
    private static int topSideButton;
    private static int selectedMap = -1;
    
    private static Image background;
    private static Image btnStart;
    
    private static Object[] maps;
    private static Color[] mapSelect;
    
    TrueTypeFont fontMap;
    AngelCodeFont fontMapHeadline;
    AngelCodeFont fontMaps;

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        long time = System.currentTimeMillis();
        try {
            background = Resources.getImage("Main");
            btnStart = Resources.getImage("btnStart");
            leftSideButton = (container.getWidth() - btnStart.getWidth()) / 2;
            topSideButton = (container.getHeight() - btnStart.getHeight()) / 2;
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
        
        fontMapHeadline = Resources.getFont("Comic Sans MS", "32 - bold italic");
        fontMaps = Resources.getFont("Comic Sans MS", "28 - bold");
        Set<String> temp;
        maps = Resources.getAllMaps().toArray();
        mapSelect = new Color[maps.length];
        for (int i = 0; i < mapSelect.length; i++) {
            mapSelect[i] = Color.white;
        }
        
        System.out.println("initMenu: " + (System.currentTimeMillis()-time) + " ms");
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.drawImage(background, 0, 0);
        g.drawImage(btnStart, leftSideButton, topSideButton);
        if (MyKeyboard.keyboard[Input.KEY_F3]) {
            g.drawString("FPS: " + container.getFPS(), 10, 10);
            g.drawString("Mouse x: " + String.valueOf(mouseX) + ", y: " + String.valueOf(mouseY), 10, 25);
            g.drawString("Map: " + String.valueOf(selectedMap), 10, 40);
        }
        
        posX = 160;
        posY = container.getHeight()/2 - (45+maps.length*28)/2;
        g.setColor(Color.lightGray);
        g.fillRoundRect(posX, posY, 200, 45+maps.length*28, 3);
        fontMapHeadline.drawString(posX+50, posY+5, "Maps:", Color.orange);
        int y = posY+35;
        for (int i = 0; i < maps.length; i++) {
            String temp = maps[i].toString();
            fontMaps.drawString(posX+10, y, temp, mapSelect[i]);
            y += 28;
        }
        
        
        
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        mouseX = Mouse.getX();
        mouseY = Math.abs(Mouse.getY()-container.getHeight());
        
        try {
            if ((mouseX >= leftSideButton) && (mouseX <= (leftSideButton + btnStart.getWidth())) && (mouseY >= topSideButton) && (mouseY <= (topSideButton + btnStart.getHeight()))) {
                if (MyMouse.mouse[Input.MOUSE_LEFT_BUTTON]) {
                    btnStart = Resources.getImage("btnStartClick");
                } else {
                    btnStart = Resources.getImage("btnStartHover");
                }
            } else if ((mouseX >= posX) && (mouseX <= (posX + 200)) && (mouseY >= posY) && (mouseY <= (posY + 45 + maps.length * 28))) {
 
                for (int i = 0; i < maps.length; i++) {
                    if (mouseY >= posY + 38 + (i * 28) && mouseY <= posY + 38 + 28 + (i * 28)) {
                        if (selectedMap == i) {
                            mapSelect[i] = Color.cyan;
                        } else {
                            mapSelect[i] = Color.blue;
                        }
                    }
                }
                for (int i = 0; i < maps.length; i++) {
                    if (mouseY <= posY + 38 + (i * 28) || mouseY >= posY + 38 + 28 + (i * 28)) {
                        if (selectedMap == i) {
                            mapSelect[i] = Color.green;
                        } else {
                            mapSelect[i] = Color.white;
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
            }
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
        
    }
    
    public static void mousClicked(){
        if ((mouseX >= leftSideButton) && (mouseX <= (leftSideButton + btnStart.getWidth())) && (mouseY >= topSideButton) && (mouseY <= (topSideButton + btnStart.getHeight()))) {
            if (selectedMap != -1) {
                Game.loadMap(selectedMap());
                Window.game.enterState(1);
                Window.state = 1;
            }
        } else if ((mouseX >= posX) && (mouseX <= (posX + 200)) && (mouseY >= posY) && (mouseY <= (posY + 45 + maps.length * 28))) {
            for (int i = 0; i < maps.length; i++) {
                if (mouseY >= posY + 38 + (i * 28) && mouseY <= posY + 38 + 28 + (i * 28)) {
                    if (mapSelect[i] != Color.cyan) {
                        mapSelect[i] = Color.cyan;
                        if (selectedMap != -1) {
                            mapSelect[selectedMap] = Color.white;
                        }
                        selectedMap = i;
                    } else {
                        mapSelect[i] = Color.blue;
                        selectedMap = -1;
                    }
                }
            }
            

        }
    }
    
    public static String selectedMap(){
        return (String) maps[selectedMap];
    }
    
}
