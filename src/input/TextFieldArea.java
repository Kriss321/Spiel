/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package input;

import gui.Window;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.GUIContext;

/**
 *
 * @author Kristof
 */
public class TextFieldArea extends MyMouseOverArea {
    /** The default state */
    private static final int NORMAL = 1;

    /** The mouse down state */
    private static final int MOUSE_DOWN = 2;

    /** The mouse over state */
    private static final int MOUSE_OVER = 3;
    
    /** The state of the area */
    private int state = NORMAL;
    
    /** The default font to use in the graphics context */
    private Font font = container.getDefaultFont();
    
    /** The text to write */
    protected String text = "";
    
    /** True if the TextField is selected */
    protected boolean selected = false;
    
    /** The current backgroundcolour being used */
    private Color currentBackgroundColor;
    
    /** The backgroundcolour used in normal state */
    private Color normalBackgroundColor = Color.lightGray;
    
    /** The backgroundcolour used in mouseOver state */
    private Color mouseOverBackgroundColor = Color.darkGray;
    
    /** The colour used in normal state */
    private Color normalColorSelected = Color.green;
    
    /** The colour used in mouseOver state */
    private Color mouseOverColorSelected = Color.green;

    /** The colour used in mouseDown state */
    private Color mouseDownColorSelected = Color.green;

    public TextFieldArea(GUIContext container, int x, int y, Font font, String text) {
        this(container, x, y, font.getWidth(text), font.getHeight(text));
        this.font = font;
        this.text = text;
    }
    
    public TextFieldArea(GUIContext container, int x, int y, int width, Font font, String text) {
        this(container, x, y, width, font.getHeight(text));
        this.font = font;
        this.text = text;
    }
    
    public TextFieldArea(GUIContext container, int x, int y, int width, int height) {
        super(container, null, x, y, width, height);
        //mouseOverColor = Color.blue;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setNormalBackgroundColor(Color normalBackgroundColor) {
        this.normalBackgroundColor = normalBackgroundColor;
    }

    public void setMouseOverBackgroundColor(Color mouseOverBackgroundColor) {
        this.mouseOverBackgroundColor = mouseOverBackgroundColor;
    }

    public void setNormalColorSelected(Color normalColorSelected) {
        this.normalColorSelected = normalColorSelected;
    }

    public void setMouseOverColorSelected(Color mouseOverColorSelected) {
        this.mouseOverColorSelected = mouseOverColorSelected;
    }

    public void setMouseDownColorSelected(Color mouseDownColorSelected) {
        this.mouseDownColorSelected = mouseDownColorSelected;
    }

    public Color getNormalBackgroundColor() {
        return normalBackgroundColor;
    }

    public Color getMouseOverBackgroundColor() {
        return mouseOverBackgroundColor;
    }

    public Color getNormalColorSelected() {
        return normalColorSelected;
    }

    public Color getMouseOverColorSelected() {
        return mouseOverColorSelected;
    }

    public Color getMouseDownColorSelected() {
        return mouseDownColorSelected;
    }

    public Color getNormalColor() {
        return normalColor;
    }

    public Color getMouseOverColor() {
        return mouseOverColor;
    }

    public Color getMouseDownColor() {
        return mouseDownColor;
    }

    @Override
    public void mousePressed(int button, int mx, int my) {
        super.mousePressed(button, mx, my);
        if (this.isMouseOver()) {
            this.selected = !this.selected;
            Window.model.textFieldAreaAction(this, text);
        }
    }

    @Override
    public void render(GUIContext container, Graphics g) {
        updateFont();
        g.setColor(currentBackgroundColor);
        g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        //g.drawRect(this.getX(), this.getY(), this.getWidth()-1, this.getHeight());
        font.drawString(this.getX()+10, this.getY()+0, text, currentColor);
    }

    private void updateFont() {
        if (!this.isMouseOver()) {
            if (selected) {
                currentColor = normalColorSelected;
            } else {
                currentColor = normalColor;
            }
            currentBackgroundColor = normalBackgroundColor;
            state = NORMAL;
            mouseUp = false;
        } else {
            if (mouseDown) {
                if ((state != MOUSE_DOWN) && (mouseUp)) {
                    if (mouseDownSound != null) {
                        mouseDownSound.play();
                    }
                    if (selected) {
                        currentColor = mouseDownColorSelected;
                    } else {
                        currentColor = mouseDownColor;
                    }
                    state = MOUSE_DOWN;

                    notifyListeners();
                    mouseUp = false;
                }

                return;
            } else {
                mouseUp = true;
                if (state != MOUSE_OVER) {
                    if (mouseOverSound != null) {
                        mouseOverSound.play();
                    }
                    if (selected) {
                        currentColor = mouseOverColorSelected;
                    } else {
                        currentColor = mouseOverColor;
                    }
                    currentBackgroundColor = mouseOverBackgroundColor;
                    state = MOUSE_OVER;
                }
            }

            mouseDown = false;
            state = NORMAL;
        }
    }
    
    public void setWidth(int width){
        this.area = new Rectangle(this.getX(),this.getY(),width, this.getHeight());
    }
    
    public void setHight(int height){
        this.area = new Rectangle(this.getX(),this.getY(),this.getWidth(), height);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
}
