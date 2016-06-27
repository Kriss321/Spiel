/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import input.MyKeyboard;
import main.Config;
import main.Resources;
import map.Map;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

/**
 *
 * @author Kristof
 */
public class Player implements Entity {

    private final float[] startPoint;
    private final int id;
    private final Map map;
    private final float gravity;
    private final int tileWidth, tileHeight;
    private final Image image;
    private final int height, width;
    private final int keyRight;
    private final int keyLeft;
    private final int keyJump;
    
    private float posX, posY;
    private float velX, velY;
    private boolean maxSpeed;
    private float down_speed;
    private boolean jump;
    private long jumpTime;
    private int jumpCount;
    private Entity collidedEntity;
    
    private float delta = 0;
    private int count = 0;
    
    
    public Player(Map map, int id) {
        this.map = map;
        this.id = id;
        this.gravity = map.getGravity();
        this.tileHeight = map.getMap().getTileHeight();
        this.tileWidth = map.getMap().getTileWidth();
        this.startPoint = map.getStartPoit(id);
        this.posX = this.startPoint[0] * this.tileWidth;
        this.posY = this.startPoint[1] * this.tileHeight;
        this.image = Resources.getImage("Player");
        this.height = this.image.getHeight();
        this.width = this.image.getWidth();
        this.keyRight = Config.getInt("p" + id + ".move.right");
        this.keyLeft = Config.getInt("p" + id + ".move.left");
        this.keyJump = Config.getInt("p" + id + ".move.jump");
        
        this.maxSpeed = false;
        this.collidedEntity = null;
        
    }
    
    public void drawEntity(Graphics g) {
        this.image.draw(this.posX, this.posY);
        
        if (MyKeyboard.keyboard[Input.KEY_F3]) {
            g.drawString("Player " + id + ":", 10+(id*400), 25);
            g.drawString("velX: " + String.valueOf(this.velX) + ", valY: " + String.valueOf(this.velY), 10+(id*400), 40);
            g.drawString("PosX: " + this.posX + " | PosY: " + this.posY, 10+(id*400), 55);
        }
        //g.drawLine(this.posX, this.posY-1, this.posX + width, this.posY-1);
        //g.drawLine(this.posX-1, this.posY, this.posX-1, this.posY + height);
        //g.drawLine(this.posX+width-1, this.posY+1, this.posX+width-1, this.posY + height-2);
    }
    
    public void move(int delta) {
        delta(delta);
        moveRight(delta);
        moveLeft(delta);
        slowDown(delta);
        this.posX += this.velX;
        gravity(delta);
        Jump(delta);
        this.posY += this.velY;
    }
    
    public void moveRight(int delta) {
        if (MyKeyboard.keyboard[this.keyRight] && !MyKeyboard.keyboard[this.keyLeft] && !(collisionWorld(this.posX + this.width, this.posY) || collisionWorld(this.posX + this.width, this.posY + this.height - 1))) {
            if (!this.maxSpeed && this.velX < (0.256f * delta)) {
                this.velX += 0.01f * delta;
            } else if (!this.maxSpeed) {
                this.maxSpeed = true;
            }
            if (this.maxSpeed) {
                this.velX = 0.256f * delta;
            }
        }

        if (collisionWorld(this.posX + width + this.velX, this.posY) || collisionWorld(this.posX + this.width + this.velX, this.posY + this.height - 1)) {
            if (this.posX % this.map.getMap().getTileWidth() != 0) {
                System.out.println("Right old: " + this.posX);
                this.posX = this.posX + width - this.posX % 32;
                System.out.println("Right new: " + this.posX);
            }
            this.velX = 0;
        } else if (collisionEntity(this.posX + width + this.velX, this.posY + 1) || collisionEntity(this.posX + this.width + this.velX, this.posY + this.height - 1)) {
            this.posX = this.collidedEntity.getPosX() - this.width;
            this.velX = 0;
        }
    }

    public void moveLeft(int delta) {
        if (MyKeyboard.keyboard[this.keyLeft] && !MyKeyboard.keyboard[this.keyRight] && !(collisionWorld(this.posX - 1, this.posY) || collisionWorld(this.posX - 1, this.posY + this.height - 1))) {
            if (!this.maxSpeed && this.velX > (-0.256f * delta)) {
                this.velX -= 0.01f * delta;
            } else if (!this.maxSpeed) {
                this.maxSpeed = true;
            }
            if (this.maxSpeed) {
                this.velX = -0.256f * delta;
            }
        }

        if (collisionWorld(this.posX + this.velX, this.posY) || collisionWorld(this.posX + this.velX, this.posY + this.height - 1)) {
            if (this.posX % this.map.getMap().getTileWidth() != 0) {
                System.out.println("Left old: " + this.posX);
                this.posX = this.posX - this.posX % 32;
                System.out.println("Left new: " + this.posX);
            }
            this.velX = 0;
        } else if (collisionEntity(this.posX + this.velX, this.posY + 1) || collisionEntity(this.posX + this.velX, this.posY + this.height - 1)) {
            this.posX = this.collidedEntity.getPosX() + this.collidedEntity.getWidth();
            this.velX = 0;
        }

    }
    
    public void Jump(int delta) {
        if (MyKeyboard.keyboard[this.keyJump]) {
            if (!this.jump && this.jumpCount <= 2) {
                this.jumpTime = System.currentTimeMillis();
                this.jump = true;
                this.jumpCount++;
            }

            if (this.jump && this.jumpCount == 1 && (this.jumpTime + 350) >= System.currentTimeMillis()) {
                this.velY = -5f;
            }

            if (this.jump && this.jumpCount == 2 && (this.jumpTime + 250) >= System.currentTimeMillis()) {
                this.velY = -4f;
            }

        } else if (this.jump) {
            this.jump = false;
        }

        if (collisionWorld(this.posX, this.posY + this.velY) || collisionWorld(this.posX + this.width - 0.01f, this.posY + this.velY)) {
            if (this.posY % this.map.getMap().getTileHeight() != 0) {
                this.posY = this.posY - this.posY % 32;
                System.out.println("Top: " + this.posY);
            }
            this.velY = 0;
        } else if (collisionEntity(this.posX + 1, this.posY + this.velY) || collisionEntity(this.posX + this.width - 0.01f, this.posY + this.velY)) {
            this.posY = this.collidedEntity.getPosY() + this.collidedEntity.getHeigth();
            this.velY = 0;
        }
    }
    
    public void gravity(int delta) {
        if (!collisionWorld(this.posX, this.posY + this.height) && !collisionWorld(this.posX + this.width - 0.01f, this.posY + this.height)) {
            if (this.velY < (this.down_speed * delta)) {
                this.velY += 0.02f * delta;
            }
            if (this.velY >= (this.down_speed * delta)) {
                this.velY = this.down_speed * delta;
            }
        }
        
        if (collisionWorld(this.posX, this.posY + this.height + this.velY) || collisionWorld(this.posX + this.width - 1, this.posY + this.height + this.velY)) {
            if (this.posY % this.map.getMap().getTileHeight() != 0) {
                this.posY = this.posY + height - this.posY % 32;
                System.out.println("------------------>" + this.posY);
                
            }
            this.jumpCount = 0;
            this.velY = 0;
        } else if (collisionEntity(this.posX, this.posY + this.height + this.velY) || collisionEntity(this.posX + this.width - 0.01f, this.posY + this.height + this.velY)) {
            this.posY = this.collidedEntity.getPosY() - this.height;
            this.velY = 0;
        }

    }
    
    public void slowDown(int delta) {
        //(!MyKeyboard.keyboard[this.keyLeft] && !MyKeyboard.keyboard[this.keyRight] || MyKeyboard.keyboard[this.keyLeft] && MyKeyboard.keyboard[this.keyRight])
        if (!(MyKeyboard.keyboard[this.keyLeft] ^ MyKeyboard.keyboard[this.keyRight])) {
            if (this.maxSpeed) {
                this.maxSpeed = false;
            }
            if (this.velX > 0.15f) {
                this.velX -= 0.01f * delta;
            } else if (this.velX < -0.15f) {
                this.velX += 0.01f * delta;
            } else {
                this.velX = 0;
            }
        }
    }
    
    public boolean collisionWorld(float x, float y) {
        if (x >= 0 && x <= (this.map.getMap().getWidth() * this.tileWidth) && y >= 0 && y <= (this.map.getMap().getHeight() * this.tileHeight)) {
            if (this.map.getMap().getTileId((int) (x / this.tileWidth), (int) (y / this.tileWidth), this.map.getMap().getLayerIndex("Solid")) != 0) {
                return true;
            }
        }

        return false;
    }
    
    public boolean collisionEntity(float x, float y) {
        for (Entity entity : EntityManager.entitys) {
            if (!entity.equals(this)) {
                if (x >= entity.getPosX() && x < (entity.getPosX() + entity.getWidth())) {
                    if (y >= entity.getPosY() && y < (entity.getPosY() + entity.getHeigth())) {
                        this.collidedEntity = entity;
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public void delta(int delta){
        if (this.count <= 300) {
            this.delta += delta;
            this.count++;
            this.down_speed = this.gravity / (this.delta / this.count);
        }
    }
    
    public float getPosX() {
        return this.posX;
    }
    
    public float getPosY() {
        return this.posY;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeigth() {
        return this.height;
    }
    
}
