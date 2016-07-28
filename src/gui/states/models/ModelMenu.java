/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.states.models;

import entity.EntityManager;
import gui.BasedGame;
import gui.states.Game;
import input.Button;
import input.TextFieldArea;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Resources;
import map.MapManager;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

/**
 *
 * @author Kristof
 */
public class ModelMenu extends Observable {

    private final Model model;
    private final ThreadMapSelect threadMapSelected;

    private int xCordMapArea;
    private int yCordMapArea;
    private int xCordPlayerArea;
    private int yCordPlayerArea;
    private int leftSideButtonCord;
    private int topSideButtonCord;
    private int selectedMap = -1;
    private int selectedPlayer = -1;

    private Image background;

    private List maps;
    private TextFieldArea[] mapArea;
    private TextFieldArea[] playerArea;
    private boolean mapSelected = false;

    private Button play;

    private AngelCodeFont fontHeadline;
    private AngelCodeFont font;
    
    private TextFieldArea test;

    public ModelMenu(GameContainer container, Model model) {
        this.model = model;
        this.background = Resources.getImage("Main");
        this.maps = Arrays.asList(Resources.getAllMaps().toArray());
        this.fontHeadline = Resources.getFont("Comic Sans MS", "32 - bold italic");
        this.font = Resources.getFont("Comic Sans MS", "28 - bold");
        Image btnStart = Resources.getImage("btnStart");
        
        this.leftSideButtonCord = (container.getWidth() - btnStart.getWidth()) / 2;
        this.topSideButtonCord = (container.getHeight() - btnStart.getHeight()) / 2;
        this.xCordMapArea = this.leftSideButtonCord / 2 - 100;
        this.yCordMapArea = container.getHeight() / 2 - (45 + this.maps.size() * 28) / 2;
        
        this.play = new Button(container, btnStart, this.leftSideButtonCord, this.topSideButtonCord, "start");
        this.play.setMouseOverImage(Resources.getImage("btnStartOver"));
        this.play.setMouseDownImage(Resources.getImage("btnStartClick"));
        
        initMapArea(container);
        
        this.threadMapSelected = new ThreadMapSelect();
    }
    
    private void initMapArea(GameContainer container) {
        this.mapArea = new TextFieldArea[this.maps.size()];
        int width = 190;
        for (int i = 0; i < this.mapArea.length; i++) {
            this.mapArea[i] = new TextFieldArea(container, this.xCordMapArea, this.yCordMapArea + 35 + (28 * i), 200, 28, "mapSelect");
            this.mapArea[i].setText(this.maps.get(i).toString());
            this.mapArea[i].setFont(this.font);
            if (this.font.getWidth(this.maps.get(i).toString()) > width) {
                width = this.font.getWidth(this.maps.get(i).toString());
            }
        }
        if (width > 190) {
            width += 15;
            this.xCordMapArea = (this.leftSideButtonCord - width) / 2;
            for (TextFieldArea map : this.mapArea) {
                map.setX(this.xCordMapArea);
                map.setWidth(width);
            }
        }
    }
    
    public void playerSelected(int player) {
        if (this.selectedPlayer != -1 && this.selectedPlayer != player) {
            this.playerArea[this.selectedPlayer].setSelected(false);
            this.selectedPlayer = player;
        } else {
            this.playerArea[this.selectedPlayer].setSelected(true);
            this.selectedPlayer = player;
        }
        setChanged();
        notifyObservers(this);
    }
    
    public synchronized void mapSelected(String text) {
        this.threadMapSelected.setText(text);
        if (!this.threadMapSelected.isAlive()) {
            this.threadMapSelected.start();
        } else {
            this.threadMapSelected.restart();
        }
    }
    
    public void start() {
        if (this.selectedMap != -1) {
            EntityManager.loadEntitys(this.selectedPlayer + 1);
            this.model.setState(BasedGame.ID_GAME);
        }
        setChanged();
        notifyObservers(this);
    }
    
    public void fullScreen(GameContainer container) {
        this.leftSideButtonCord = (container.getWidth() - this.play.getWidth()) / 2;
        this.topSideButtonCord = (container.getHeight() - this.play.getHeight()) / 2;
        this.play.setX(this.leftSideButtonCord);
        this.play.setY(this.topSideButtonCord);
        this.xCordMapArea = (this.leftSideButtonCord - this.mapArea[0].getWidth())/2;
        this.yCordMapArea = container.getHeight() / 2 - (45 + this.maps.size() * this.mapArea[0].getHeight()) / 2;
        for (int i = 0; i < this.mapArea.length; i++) {
            this.mapArea[i].setX(this.xCordMapArea);
            this.mapArea[i].setY(this.yCordMapArea + 35 + (28 * i));
        }
        if (this.selectedMap != -1) {
            this.xCordPlayerArea = this.leftSideButtonCord + this.play.getWidth() + this.leftSideButtonCord / 2 - 100;
            this.yCordPlayerArea = container.getHeight() / 2 - (45 + MapManager.playerCount * 28) / 2;
            for (int i = 0; i < this.playerArea.length; i++) {
                this.playerArea[i].setX(this.xCordPlayerArea);
                this.playerArea[i].setY(this.yCordPlayerArea + 35 + (28 * i));
            }
        }
        setChanged();
        notifyObservers(this);
    }
    
    public void playerAreaLoad(GameContainer container) {
        
        if (this.mapSelected) {
            synchronized (this.threadMapSelected) {
                this.mapSelected = false;
                
                Game.loadMap((String) this.maps.get(this.selectedMap));
                
                this.playerArea = new TextFieldArea[MapManager.playerCount];
                this.xCordPlayerArea = this.leftSideButtonCord + this.play.getWidth() + this.leftSideButtonCord / 2 - 100;
                this.yCordPlayerArea = container.getHeight() / 2 - (45 + MapManager.playerCount * 28) / 2;
                for (int i = 0; i < this.playerArea.length; i++) {
                    this.playerArea[i] = new TextFieldArea(container, this.xCordPlayerArea, this.yCordPlayerArea + 35 + (28 * i), 200, 28, "playerSelect");
                    this.playerArea[i].setText((i + 1) + " Player");
                    this.playerArea[i].setFont(this.font);
                }
                this.playerArea[0].setSelected(true);
                this.selectedPlayer = 0;

                this.threadMapSelected.notify();
            }
        }
        
    }

    public int getxCordMapArea() {
        return this.xCordMapArea;
    }

    public void setxCordMapArea(int xCordMapArea) {
        this.xCordMapArea = xCordMapArea;
    }

    public int getyCordMapArea() {
        return this.yCordMapArea;
    }

    public void setyCordMapArea(int yCordMapArea) {
        this.yCordMapArea = yCordMapArea;
    }

    public int getxCordPlayerArea() {
        return this.xCordPlayerArea;
    }

    public void setxCordPlayerArea(int xCordPlayerArea) {
        this.xCordPlayerArea = xCordPlayerArea;
    }

    public int getyCordPlayerArea() {
        return this.yCordPlayerArea;
    }

    public void setyCordPlayerArea(int yCordPlayerArea) {
        this.yCordPlayerArea = yCordPlayerArea;
    }

    public int getLeftSideButtonCord() {
        return this.leftSideButtonCord;
    }

    public void setLeftSideButtonCord(int leftSideButtonCord) {
        this.leftSideButtonCord = leftSideButtonCord;
    }

    public int getTopSideButtonCord() {
        return this.topSideButtonCord;
    }

    public void setTopSideButtonCord(int topSideButtonCord) {
        this.topSideButtonCord = topSideButtonCord;
    }

    public int getSelectedMap() {
        return this.selectedMap;
    }

    public void setSelectedMap(int selectedMap) {
        this.selectedMap = selectedMap;
    }

    public int getSelectedPlayer() {
        return this.selectedPlayer;
    }

    public void setSelectedPlayer(int selectedPlayer) {
        this.selectedPlayer = selectedPlayer;
    }

    public Image getBackground() {
        return this.background;
    }

    public void setBackground(Image background) {
        this.background = background;
    }

    public List getMaps() {
        return this.maps;
    }

    public void setMaps(List maps) {
        this.maps = maps;
    }

    public TextFieldArea[] getMapArea() {
        return this.mapArea;
    }

    public void setMapArea(TextFieldArea[] mapArea) {
        this.mapArea = mapArea;
    }

    public TextFieldArea[] getPlayerArea() {
        return this.playerArea;
    }

    public void setPlayerArea(TextFieldArea[] playerArea) {
        this.playerArea = playerArea;
    }

    public boolean isMapSelected() {
        return this.mapSelected;
    }

    public void setMapSelected(boolean mapSelected) {
        this.mapSelected = mapSelected;
    }

    public Button getPlay() {
        return this.play;
    }

    public void setPlay(Button play) {
        this.play = play;
    }

    public AngelCodeFont getFontHeadline() {
        return this.fontHeadline;
    }

    public void setFontHeadline(AngelCodeFont fontHeadline) {
        this.fontHeadline = fontHeadline;
    }

    public AngelCodeFont getFont() {
        return this.font;
    }

    public void setFont(AngelCodeFont font) {
        this.font = font;
    }

    public class ThreadMapSelect extends Thread {

        private String text;

        @Override
        public void run() {
            synchronized (this) {
                try {
                    while (this.isAlive()) {                        
                        if (selectedMap != -1) {
                            mapArea[selectedMap].setSelected(false);
                        }
                        if (selectedMap != maps.indexOf(text)) {
                            selectedMap = maps.indexOf(text);
                            mapSelected = true;
                            wait();
                            setChanged();
                            notifyObservers(ModelMenu.this);
                        } else {
                            selectedMap = -1;
                            setChanged();
                            notifyObservers(ModelMenu.this);
                        }
                        wait();
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(ModelMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        private synchronized void restart() {
            this.notify();
        }

        public String getText() {
            return this.text;
        }

        public void setText(String text) {
            this.text = text;
        }

    }

    
    
}
