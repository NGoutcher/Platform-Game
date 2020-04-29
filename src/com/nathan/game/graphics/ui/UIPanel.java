package com.nathan.game.graphics.ui;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

import com.nathan.game.entity.mob.Player;
import com.nathan.game.util.Vector2i;

public class UIPanel extends UIComponent {

	private List<UIComponent> components = new ArrayList<UIComponent>();
	public Vector2i size;
	private Graphics graphics;
	private boolean activeVal = false;
	
	public UIPanel(Vector2i position, Vector2i size) {
		super(position);
		this.position = position;
		this.size = size;
		color = new Color(0xcacaca);
	}
	
	public void addComponent(UIComponent component) {
		components.add(component);
	}

	public void removeComponent(int i) {
		components.remove(i);
	}

	public void removeComponent(UIComponent component) {
		components.remove(component);
	}

	public boolean contains(UIComponent component) {
		boolean found = false;
		for(int i = 0; i < components.size(); i++) {
			if(components.get(i).equals(component)){
				found = true;
			}
		}
		return found;
	}
	public void update() {
		for(UIComponent component : components) {
			component.setOffset(position);
			component.update();
		}
	}
	
	public void clear() {
		components.clear();
	}
	
	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(position.x, position.y, size.x, size.y);
		for(UIComponent component : components) {
			component.render(g);
		}
	}

	public Graphics getGraphics() {
		return graphics;
	}

	public void setLabel(String text) {
		UILabel label = new UILabel(new Vector2i(8, 20), text);
		label.setColor(0xbbbbbb);
		label.setFont(new Font("Helvetica", Font.PLAIN, 10));
		label.dropShadow = false;
		this.addComponent(label);
	}

	public void setActive() {
		activeVal = true;
		this.setColor(0x2f2f2f);
	}

	public void setInactive() {
		activeVal = false;
		this.setColor(0x6f6f6f);
	}

	public boolean isActive() {
		return activeVal;
	}
}
