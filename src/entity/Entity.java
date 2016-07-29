/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import org.newdawn.slick.Graphics;

/**
 *
 * @author Kristof
 */
public interface Entity {
    
    public void drawEntity(Graphics g);
    
    public void move(int delta);
    
    public void moveRight(int delta);
    
    public void moveLeft(int delta);
    
    public void Jump(int delta);
    
    public void gravity(int delta);
    
    public void slowDown(int delta);
    
    public boolean collisionEntity(float x, float y);
    
    public void reset();
    
    public float getPosX();
    
    public float getPosY();
    
    public int getWidth();
    
    public int getHeigth();
    
    public void debug(Graphics g);
    
}
