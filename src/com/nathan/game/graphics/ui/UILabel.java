package com.nathan.game.graphics.ui;

import java.awt.Color; 
import java.awt.Font;
import java.awt.Graphics;

import com.nathan.game.util.Vector2i;

public class UILabel extends UIComponent {

	public String text;
	private Font font;
	public boolean dropShadow = false;
	
	public UILabel(Vector2i position, String text) {
		super(position);
		font = new Font("Helvetica", Font.PLAIN, 30);
		this.text = text;
		color = new Color(0xff00ff);
	}

	public void setFont(Font font) {
		this.font = font;
		//return this;
	}
	
	public void clear() {
		text = "";
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void render(Graphics g) {
		if(dropShadow) {
		g.setColor(Color.BLACK);
		g.setFont(new Font(font.getName(), font.getStyle(), font.getSize() + 1));
		g.drawString(text, position.x + offset.x - 1, position.y + offset.y - 1);
		}
		g.setFont(font);
		g.setColor(color);
		g.drawString(text, position.x + offset.x, position.y + offset.y);
	}
}
