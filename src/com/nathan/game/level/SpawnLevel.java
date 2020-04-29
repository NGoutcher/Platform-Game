package com.nathan.game.level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import com.nathan.game.entity.mob.*;
import com.nathan.game.sound.AudioPlayer;

public class SpawnLevel extends Level{
	
	private AudioPlayer bgMusic;
	
	public SpawnLevel(String path) {
		super(path);
	} 
	
	protected void generateLevel() {
		for(int y = 0; y < 64; y++) {
			for (int x = 0; x < 64; x++) {
				getTile(x, y);
			}
		}
	}

	protected void loadLevel(String path) {
		try {
			BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
			int w = width = image.getWidth();
			int h = height = image.getHeight();
			tiles = new int[w * h];
			image.getRGB(0, 0, w, h, tiles, 0, w);
		} catch(IOException e) {
			System.out.println("Failed to load level.");
			e.printStackTrace();
		}
		for(int i = 0; i < 1; i++) {
			//add(new Star(17, 68));
			//add(new Shooter(17, 68, 1));

			//add 5 shooters
			for(int j = 0; j < 50; j++) {
				int[] xy = randomSpawn();
				add(new Shooter(xy[0], xy[1], 0));
			}

			//add(new Dummy(23, 68));
			add(new NPC(24, 68, "Priest Bob", "Hello there traveller.."));
			add(new NPC(30, 68, "Priest John", "Good day."));
		}
	}
}
