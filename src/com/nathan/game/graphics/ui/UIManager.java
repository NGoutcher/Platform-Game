package com.nathan.game.graphics.ui;

import com.nathan.game.MainClass;
import com.sun.tools.javac.Main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class UIManager {

	private List<UIPanel> panels = new ArrayList<UIPanel>();
	public Graphics graphics;
	
	public UIManager() {
		
	}
	
	public void addPanel(UIPanel panel) {
		panels.add(panel);
	}

	public void removePanel(UIPanel panel) {
		for(int i = 0; i < panels.size(); i++) {
			if(panels.get(i).equals(panel)) {
				panels.remove(i);
			}
		}
	}
	
	public void update() {
		for(UIPanel panel : panels) {
			panel.update();
		}
	}
	
	public void render(Graphics g) {
		graphics = g;
		for(UIPanel panel : panels) {
			panel.render(graphics);
		}
	}
}
