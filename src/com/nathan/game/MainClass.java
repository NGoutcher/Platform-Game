package com.nathan.game;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.ImageObserver;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.nathan.game.entity.items.potions.HealthPotion;
import com.nathan.game.entity.items.potions.ManaPotion;
import com.nathan.game.entity.items.weapons.Staff;
import com.nathan.game.entity.mob.Player;
import com.nathan.game.graphics.Screen;
import com.nathan.game.graphics.ui.UIManager;
import com.nathan.game.input.Keyboard;
import com.nathan.game.input.Mouse;
import com.nathan.game.level.Level;
import com.nathan.game.level.TileCoord;
import com.nathan.game.sound.AudioPlayer;

public class MainClass extends Canvas implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static int width = 300 - 80;
	private static int height = 168;
	private static int scale = 3;
	public static String title = "Nathan's Game";
	
	private Thread thread;
	private JFrame frame;
	private Keyboard key;
	private Level level;
	private static Player player;
	private boolean running = false;
	private static UIManager uiManager;
	
	private Screen screen;
	
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	public static void main(String[] args, MainClass game) {
		//MainClass game = new MainClass();
		game.frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				try {
					PrintWriter writer = new PrintWriter("save-details.txt", "UTF-8");
					writer.println(player.getName() + " " + player.getLevel() + " " + player.getXp() + " " + player.getX() / 16 + " " + player.getY() / 16);
					writer.close();

					PrintWriter invWriter = new PrintWriter(("save-inv.txt"), "UTF-8");
					for(int i = 0; i < player.inventory.size(); i++){
						if(i == player.inventory.size() - 1) {
							invWriter.print(player.inventory.get(i).getName());
						} else {
							invWriter.print(player.inventory.get(i).getName() + ",");
						}
					}
					invWriter.close();
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		});
		game.frame.setResizable(false);
		game.frame.setTitle(MainClass.title);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		
		game.start();
		game.requestFocus();
		AudioPlayer music = new AudioPlayer("Fantasy");
		/**MenuScreen menu = new MenuScreen();
		menu.frame.setResizable(false);
		menu.frame.setTitle(MainClass.title);
		menu.frame.add(menu);
		menu.frame.pack();
		menu.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu.frame.setLocationRelativeTo(null);
		menu.frame.setVisible(true);
		
		menu.start();
		menu.requestFocus(); */
		
	}
	
	public MainClass(String charName) {

		Dimension size = new Dimension(width * scale + 80 * 3, height * scale);
		setPreferredSize(size);
		screen = new Screen(width, height);
		uiManager = new UIManager();
		
		frame = new JFrame();
		key = new Keyboard();
		level = Level.spawn;
		TileCoord playerSpawn = new TileCoord(21, 68);
		player = new Player(charName, playerSpawn.x(), playerSpawn.y(), key);
		level.add(player);
		
		addKeyListener(key);
		
		Mouse mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		//drawMap(getGraphics());
	}

	public MainClass(String charName, int charLevel, double charXp, int charX, int charY, ArrayList<String> invText) {

		Dimension size = new Dimension(width * scale + 80 * 3, height * scale);
		setPreferredSize(size);
		screen = new Screen(width, height);
		uiManager = new UIManager();

		frame = new JFrame();
		key = new Keyboard();
		level = Level.spawn;
		TileCoord playerSpawn = new TileCoord(charX, charY);
		player = new Player(charName, charLevel, playerSpawn.x(), playerSpawn.y(), key);
		player.setXp(charXp);
		for(int i = 0; i < invText.size(); i++){
			findItem(invText.get(i));
		}
		level.add(player);
		addKeyListener(key);

		Mouse mouse = new Mouse();
		addMouseListener(mouse);

		addMouseMotionListener(mouse);
		//drawMap(getGraphics());

	}


	
	public static int getWindowWidth() {
		return width * scale;
	}
	
	public static int getWindowHeight() {
		return height * scale;
	}
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}
	
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
					updates++;
				delta--;
			}
			render();
			frames++; 
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle(title + " | " + updates + " UPS, " + frames + " FPS");
				updates = 0;
				frames = 0;
				if(player.getMana() <= 95) {
					player.setMana(player.getMana() + 5.00);
				}
				if(player.getHealth() <= 95) {
					player.setHealthRegen(5.00);
					if(player.getHealth() > 100) {
						double remainder = player.getHealth() - 100;
						player.setHealthRegen(player.getHealth() - remainder);
					}
					//if(player.getHealth() <= 99) {
						//player.setHealthRegen(1.00);
					//}
				}
				}
			}
		stop();
	}
	
	public void update() {
		key.update();
		level.update();
		uiManager.update();
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.clear();
		double xScroll = player.getX() - screen.width / 2;
		double yScroll = player.getY() - screen.height / 2;
		level.render((int) xScroll, (int) yScroll, screen);
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		Graphics g = bs.getDrawGraphics();
		g.fillRect(0,  0, getWidth(), getHeight());
		g.drawImage(image, 0, 0, width * scale, height * scale, null);
		uiManager.render(g);
		g.setColor(new Color(0xffffff));
		g.dispose();
		bs.show();
	}
	
	public void drawMana(Graphics G) {
		G.drawString("Mana: " + player.getMana(), (getWidth() / 2) - 27, (getHeight() / 2) + 75);
	}

	public void drawMap(Graphics graphics) {
		File file = new File("res/levels/level.png");

		BufferedImage image = null;

		try
		{
			image = ImageIO.read(file);
			System.out.println("fucked up");
		}
		catch (IOException e)
		{
			System.out.println("fucked up");
		}
		ImageObserver observer = null;
		graphics.drawImage(image, player.mapPanel.position.x, player.mapPanel.position.y, 50, 50, observer);
		uiManager.render(graphics);
	}
	
	public static UIManager getUIManager() {
		return uiManager;
	}

	public void findItem(String itemName) {
		if(itemName.equals("Health Potion")) {
			//player.inventory.add(new HealthPotion());
			player.pickUpItem(new HealthPotion());
		}

		if(itemName.equals("Mana Potion")) {
			player.pickUpItem(new ManaPotion());
		}

		if(itemName.equals("Weak Staff")) {
			player.pickUpItem(new Staff("Weak Staff", 2));
		}
	}
	
	
}
