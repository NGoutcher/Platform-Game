package com.nathan.game.graphics.ui;

import java.awt.*;

import com.nathan.game.util.Vector2i;

public class UIComponent extends Component {

	public Vector2i position;
	protected Vector2i offset;
	public Color color;
	public Image img;
	
	public UIComponent(Vector2i position) {
		this.position = position;
		offset = new Vector2i();
	}
	
	void setOffset(Vector2i offset) {
		this.offset = offset;
	}
	
	public void update() {
		
	}
	
	public UIComponent setColor(int color) {
		this.color = new Color(color);
		return this;
	}

	public UIComponent setImage(Image img) {
		this.img = img;
		return this;
	}
	
	public void render(Graphics g) {
		
	}
}
