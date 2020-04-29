package com.nathan.game.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

	private boolean[] keys = new boolean[120];
	public boolean up, down, left, right, ui, h, m, menu, e, esc;
	
	public void update() {
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		h = keys[KeyEvent.VK_H];
		m = keys[KeyEvent.VK_M];
		ui = keys[KeyEvent.VK_U];
		menu = keys[KeyEvent.VK_ESCAPE];
		e = keys[KeyEvent.VK_E];
		esc = keys[KeyEvent.VK_ESCAPE];
	}
	
	public void keyPressed(KeyEvent e) {
		try {
			keys[e.getKeyCode()] = true;
		} catch(ArrayIndexOutOfBoundsException d) {
			//don't want output
		}
	}

	public void keyReleased(KeyEvent e) {
		try {
			keys[e.getKeyCode()] = false;
		} catch(ArrayIndexOutOfBoundsException d) {
			//don't want output
		}
	}

	public void keyTyped(KeyEvent e) {
		
	}
	
}
