package com.nathan.game.graphics.ui;

import com.nathan.game.util.Vector2i;

import java.awt.*;

public class UIImage extends UIPanel {
    public UIImage(Vector2i position, Vector2i size, String path) {
        super(position, size);
        img = Toolkit.getDefaultToolkit().getImage(path).getScaledInstance(size.x,size.y, Image.SCALE_DEFAULT);
    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(img, this.position.x, this.position.y, this);
    }
}
