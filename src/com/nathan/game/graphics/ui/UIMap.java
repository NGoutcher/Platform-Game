package com.nathan.game.graphics.ui;

import com.nathan.game.level.Level;
import com.nathan.game.util.Vector2i;

import java.awt.*;
import java.awt.image.ImageObserver;

public class UIMap extends UIPanel {

	private int x;
	private int y;
	private Image img;

	public UIMap(Vector2i position, Vector2i size) {
		super(position,size);
		img = Toolkit.getDefaultToolkit().getImage("res/levels/spawn.png").getScaledInstance(size.x,size.y, Image.SCALE_DEFAULT);
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(img, this.position.x, this.position.y, this);
	}

}
