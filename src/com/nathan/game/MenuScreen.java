package com.nathan.game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.nathan.game.entity.items.potions.HealthPotion;
import com.nathan.game.entity.items.potions.ManaPotion;
import com.nathan.game.entity.mob.Player;
import com.nathan.game.graphics.Screen;
import com.nathan.game.graphics.ui.UIManager;
import com.nathan.game.input.Keyboard;
import com.nathan.game.input.Mouse;
import com.nathan.game.level.Level;
import com.nathan.game.sound.AudioPlayer;

public class MenuScreen extends Canvas implements Runnable {
	private static int width = 300;
	private static int height = 168;
	private static int scale = 3;
	public static String title = "Nathan's Game";
	
	private Thread thread;
	public JFrame frame;
	public JFrame charFrame;
	private Keyboard key;
	private Level level;
	private Player player;
	private boolean running = false;
	
	private static UIManager uiManager;
	
	private Screen screen;
	
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	public MenuScreen() {
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);
		screen = new Screen(width, height);
		uiManager = new UIManager();
		
		frame = new JFrame();
		
		JButton buttonStart = new JButton("New Game");
			buttonStart.setBorderPainted(false);
			buttonStart.setFocusPainted(false);
		JButton buttonGo = new JButton("Start Game");
			buttonGo.setBorderPainted(false);
			buttonGo.setFocusPainted(false);
		JButton buttonQuit = new JButton("Quit");
			buttonQuit.setBorderPainted(false);
			buttonQuit.setFocusPainted(false);
		JButton buttonLoad = new JButton("Load Previous Save");
			buttonLoad.setBorderPainted(false);
			buttonLoad.setFocusPainted(false);
		JButton buttonBack = new JButton("Back");
			buttonBack.setBorderPainted(false);
			buttonBack.setFocusPainted(false);

		JTextField nameField = new JTextField("Enter your character's name.", 10);

		nameField.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				nameField.setText("");
			}
		});

		nameField.setBounds(225, 300, 170, 50);

		buttonStart.setBounds(40, 300, 150, 50);
		buttonGo.setBounds(40, 300, 150, 50);
		buttonQuit.setBounds(505, 300, 90, 50);
		buttonLoad.setBounds(40, 225, 150, 50);
		buttonBack.setBounds(40, 225, 150, 50);

		JLabel gameName = new JLabel("Platform Game");
		gameName.setHorizontalTextPosition(JLabel.CENTER);
		gameName.setVerticalTextPosition(JLabel.CENTER);

		buttonStart.addActionListener(e -> {
			buttonLoad.setVisible(false);
			buttonStart.setVisible(false);
			buttonGo.setVisible(true);
			nameField.setVisible(true);
			buttonBack.setVisible(true);
		});

		buttonBack.addActionListener(e -> {
			buttonLoad.setVisible(true);
			buttonStart.setVisible(true);
			buttonGo.setVisible(false);
			nameField.setVisible(false);
			buttonBack.setVisible(false);
		});

		buttonGo.addActionListener(e -> {
			String charName = nameField.getText();

			if (!nameField.getText().equals("Enter your character's name.") && nameField.getText().length() <= 6 && nameField.getText().length() > 0) {
				MainClass game = new MainClass(charName);
				game.main(null, game);
				frame.dispose();
			}
		});

		buttonLoad.addActionListener(e -> {
			try {

				String filename = "save-details.txt";
				FileReader fr = new FileReader(filename);
				BufferedReader reader = new BufferedReader(fr);
				String line = reader.readLine();
				Scanner sc = null;
				String characterName = null;
				int characterLevel = 1;
				double characterXp = 0;
				int characterX = 21;
				int characterY = 68;

				while(line != null) {
					sc = new Scanner(line);
					characterName = sc.next();
					characterLevel = sc.nextInt();
					characterXp = sc.nextDouble();
					characterX = sc.nextInt();
					characterY = sc.nextInt();
					line = reader.readLine();
				}
				sc.close();

				/*String invfileName = "save-inv.txt";
				FileReader invfr = new FileReader(invfileName);
				BufferedReader invreader = new BufferedReader(invfr);
				String invline = invreader.readLine();
				Scanner invsc = null;*/

				String invfilename = "save-inv.txt";
				FileReader invfr = new FileReader(invfilename);
				BufferedReader invreader = new BufferedReader(invfr);
				String invline = invreader.readLine();
				String temp = null;
				Scanner invsc = null;
				ArrayList<String> invText = new ArrayList<>();

				while(invline != null) {
					System.out.println("Reading..");
					invsc = new Scanner(invline);
					invsc.useDelimiter(",");
					//invText.add(invsc.nextLine());
					temp = invsc.nextLine();
					invline = invreader.readLine();
				}
				if(invsc != null) {
					String[] tempArr = temp.split(",");
					for(int i = 0; i < tempArr.length; i++) {
						invText.add(tempArr[i]);
					}
					System.out.print(invText);
					invsc.close();
				}

				MainClass game = new MainClass(characterName, characterLevel, characterXp, characterX, characterY, invText);
				game.main(null, game);
				frame.dispose();
			} catch (IOException f) {
				System.out.println("Error reading from file.");
			}
		});
		buttonQuit.addActionListener(e -> {
			frame.dispose();
			System.exit(0);
		});
		try {
			frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("test.jpg")))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		frame.pack();
		frame.setVisible(true);
		frame.add(buttonStart);
		frame.add(buttonQuit);
		frame.add(nameField);
		frame.add(buttonGo);
		frame.add(buttonBack);
		frame.add(gameName);
		buttonBack.setVisible(false);
		buttonGo.setVisible(false);
		nameField.setVisible(false);
		frame.add(buttonLoad);
		key = new Keyboard();
		
		addKeyListener(key);
		
		Mouse mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);

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
				}
			}
		stop();
	}
	
	public void update() {
		key.update();
		uiManager.update();
	}
	
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();

		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		screen.clear();
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		try {
			Graphics g = bs.getDrawGraphics();
			g.setColor(new Color(0xffffff));
			g.fillRect(0,  0, getWidth(), getHeight());
			g.drawImage(image, 0, 0, width * scale, height * scale, null);
			uiManager.render(g);
			g.dispose();
			bs.show();
		} catch(IllegalStateException e) {

		}

	}
	
	public static UIManager getUIManager() {
		return uiManager;
	}
	
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		MenuScreen screen = new MenuScreen();
		screen.frame.setResizable(false);
		screen.frame.setTitle(MainClass.title);
		screen.frame.add(screen);
		screen.frame.pack();
		screen.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		screen.frame.setLocationRelativeTo(null);
		screen.frame.setVisible(true);

		screen.start();
		screen.requestFocus();
		//AudioPlayer music = new AudioPlayer("Fantasy");
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
}
