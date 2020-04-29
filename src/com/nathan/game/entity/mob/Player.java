package com.nathan.game.entity.mob;

import java.awt.Font;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.nathan.game.MainClass;
import com.nathan.game.entity.items.Item;
import com.nathan.game.entity.projectile.Projectile;
import com.nathan.game.entity.projectile.WizardProjectile;
import com.nathan.game.entity.spawner.ParticleSpawner;
import com.nathan.game.graphics.AnimatedSprite;
import com.nathan.game.graphics.Screen;
import com.nathan.game.graphics.Sprite;
import com.nathan.game.graphics.Spritesheet;
import com.nathan.game.graphics.ui.*;
import com.nathan.game.graphics.ui.UIManager;
import com.nathan.game.input.Keyboard;
import com.nathan.game.input.Mouse;
import com.nathan.game.sound.EffectPlayer;
import com.nathan.game.util.Vector2i;

import javax.swing.*;

public class Player extends Mob {
	
	private Keyboard input;
	private Sprite sprite;
	private int anim = 0;
	private boolean walking = false;

	private int invFullSize = 10;
	
	public double mana;
	public double xp;
	public double xp_required;
	
	public int playerLevel;
	
	private String name;
	private AnimatedSprite down = new AnimatedSprite(Spritesheet.goodMage_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(Spritesheet.goodMage_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(Spritesheet.goodMage_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(Spritesheet.goodMage_right, 32, 32, 3);
	
	private AnimatedSprite animSprite = null;
	
	private UIManager ui;
	
	private int fireRate = 0;
	int time = 0;
	public double totalHealth;
	
	public UIPanel panel = (UIPanel) new UIPanel(new Vector2i((300 - 80) * 3, 0), new Vector2i(80 * 3, 168 * 3)).setColor(0x4f4f4f);
	public UIPanel outline = (UIPanel) new UIPanel(new Vector2i((300 - 81) * 3, 0), new Vector2i(80 * 3, 168 * 3)).setColor(0x404040);
	
	public UIPanel invPanel = (UIPanel) new UIPanel(new Vector2i((300 - 76) * 3, 290), new Vector2i(72 * 3, 68 * 3)).setColor(0x2f2f2f);
	public UIPanel invOutline = (UIPanel) new UIPanel(new Vector2i((300 - 77) * 3, 287), new Vector2i(74 * 3, 70 * 3)).setColor(0x6f6f6f);

	//Each tab size x = 54, y = 30
	public UIPanel invTab = (UIPanel) new UIPanel(new Vector2i((300 - 76) * 3, 464), new Vector2i(54, 10*3)).setColor(0x6f6f6f);
	public UIPanel equipTab = (UIPanel) new UIPanel(new Vector2i((300 - 58) * 3, 464), new Vector2i(54, 10*3)).setColor(0x6f6f6f);
	public UIPanel spellsTab = (UIPanel) new UIPanel(new Vector2i((300 - 40) * 3, 464), new Vector2i(54, 10*3)).setColor(0x6f6f6f);
	public UIPanel statsTab = (UIPanel) new UIPanel(new Vector2i((300 - 22) * 3, 464), new Vector2i(54, 10*3)).setColor(0x6f6f6f);

	public UIPanel tabsOutline = (UIPanel) new UIPanel(new Vector2i((300 - 76) * 3, 461), new Vector2i(216, 10*3)).setColor(0x6f6f6f);

	private UILabel invTabText;

	public UIPanel mapPanel = (UIPanel) new UIPanel(new Vector2i((300 - 75) * 3, 10), new Vector2i(70 * 3, 53 * 3)).setColor(0x2f2f2f);
	public UIMap map = (UIMap) new UIMap(new Vector2i((300 - 75) * 3, 10), new Vector2i(70 * 3, 53 * 3));
	public UIPanel mapOutline = (UIPanel) new UIPanel(new Vector2i((300 - 76) * 3, 7), new Vector2i(72 * 3, 55 * 3)).setColor(0x6f6f6f);

	public UIPanel xpPanel = (UIPanel) new UIPanel(new Vector2i(0, 162 * 3), new Vector2i((300 - 80) * 3, 20 * 3)).setColor(0x4f4f4f);
	public UIPanel xpOutline = (UIPanel) new UIPanel(new Vector2i(0, 161 * 3), new Vector2i((300 - 80) * 3, 21 * 3)).setColor(0x404040);

	public UIPanel msgpanelOutline = (UIPanel) new UIPanel(new Vector2i((300-295) * 3, 370), new Vector2i(209 * 3, 42 * 3)).setColor(0x404040);
	public UIPanel msgpanel = (UIPanel) new UIPanel(new Vector2i((300-294) * 3, 372), new Vector2i(207 * 3, 40 * 3)).setColor(0xffffff);
	public boolean msgPanelShown = false;
	public UILabel text;
	public UILabel NPCNameLabel;
	public UILabel closeInstr;

	private UIProgressBar uiHealthBar;
	private UIProgressBar uiManaBar;
	private UIProgressBar uiXpBar;
	private UILabel xpValLabel;
	
	private UILabel playerLevelLabel;

	private List<UILabel> invLabels = new ArrayList<>();
	private List<UILabel> statsLabels = new ArrayList<>();
	private UILabel inventoryLabel;

	//private List<UILabel> messageLabels = new ArrayList<>();
	private int invTypedSize = 0;
	
	public List<Item> inventory = new ArrayList<Item>();

	boolean hpPotionCD = false;
	boolean mpPotionCD = false;

	boolean sysMsgPanelShown = false;

	JButton muteMusic = new JButton("Mute/Unmute Music");

	public int damageMultiplier = 1;

	public Player(String name, Keyboard input) {
		this.input = input;
		sprite = Sprite.goodMage_forward;
		animSprite = down;
		this.name = name;
		totalHealth = health;
	}
	
	public Player(String name, int x, int y, Keyboard input) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.spawn_x = x;
		this.spawn_y = y;
		this.input = input;

		totalHealth = health;

		playerLevel = 1;
		xp_required = (100 * playerLevel) / 2;
		
		sprite = Sprite.goodMage_forward;
		animSprite = down;
		fireRate = WizardProjectile.FIRE_RATE;
		
		ui = MainClass.getUIManager();
		ui.addPanel(outline);
		ui.addPanel(panel);
		ui.addPanel(invOutline);
		ui.addPanel(invPanel);
		generateInvTabs();
		ui.addPanel(mapOutline);
		ui.addPanel(map);
		//ui.addPanel(mapPanel);
		ui.addPanel(map);
		//ui.addPanel(xpOutline);
		//ui.addPanel(xpPanel);
		
		UILabel nameLabel = new UILabel(new Vector2i(10, 200), name + " - ");
		nameLabel.setColor(0xbbbbbb);
		nameLabel.setFont(new Font("Helvetica", Font.PLAIN, 24));
		nameLabel.dropShadow = false;
		panel.addComponent(nameLabel);
		
		playerLevelLabel = new UILabel(new Vector2i(nameLabel.position.x + 100, 200), "Level: " + playerLevel);
		playerLevelLabel.setColor(0xbbbbbb);
		playerLevelLabel.setFont(new Font("Helvetica", Font.PLAIN, 24));
		playerLevelLabel.dropShadow = false;
		panel.addComponent(playerLevelLabel);
		
		uiHealthBar = new UIProgressBar(new Vector2i(10, 210), new Vector2i(80 * 3 - 20, 20));
		uiHealthBar.setColor(0x6a6a6a);
		uiHealthBar.setForegroundColor(0xff3a3a);
		panel.addComponent(uiHealthBar);
		
		UILabel hpLabel = new UILabel(new Vector2i(uiHealthBar.position).add(new Vector2i(2, 18)), "HP");
		hpLabel.setColor(0xbbbbbb);
		hpLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
		hpLabel.dropShadow = false;
		panel.addComponent(hpLabel);
		
		health = 100;
		
		
		uiManaBar = new UIProgressBar(new Vector2i(10, 235), new Vector2i(80 * 3 - 20, 20));
		uiManaBar.setColor(0x6a6a6a);
		uiManaBar.setForegroundColor(0x0033cc);
		panel.addComponent(uiManaBar);
		
		UILabel mpLabel = new UILabel(new Vector2i(uiManaBar.position).add(new Vector2i(2, 18)), "MP");
		mpLabel.setColor(0xbbbbbb);
		mpLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
		mpLabel.dropShadow = false;
		panel.addComponent(mpLabel);
		mana = 100;
		
		uiXpBar = new UIProgressBar(new Vector2i(10, 260), new Vector2i(80 * 3 - 20, 20));
		uiXpBar.setColor(0x6a6a6a);
		uiXpBar.setForegroundColor(0x997a00);
		panel.addComponent(uiXpBar);
		
		UILabel xpLabel = new UILabel(new Vector2i(uiXpBar.position).add(new Vector2i(2, 18)), "XP");
		xpLabel.setColor(0xbbbbbb);
		xpLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
		xpLabel.dropShadow = false;
		panel.addComponent(xpLabel);
		xp = 0;

		xpValLabel = new UILabel(new Vector2i(uiXpBar.position).add(new Vector2i(175, 18)), ((xp/xp_required)*100)+ "%");
		xpValLabel.setColor(0xbbbbbb);
		xpValLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
		xpValLabel.dropShadow = false;
		panel.addComponent(xpValLabel);
	}

	public Player(String name, int playerLevel, int x, int y, Keyboard input) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.spawn_x = x;
		this.spawn_y = y;
		this.input = input;

		totalHealth = health;

		this.playerLevel = playerLevel;
		xp_required = (100 * playerLevel) / 2;

		sprite = Sprite.player_forward;
		animSprite = down;
		fireRate = WizardProjectile.FIRE_RATE;

		ui = MainClass.getUIManager();
		ui.addPanel(outline);
		ui.addPanel(panel);
		ui.addPanel(invOutline);
		ui.addPanel(invPanel);
		ui.addPanel(invTab);
		generateInvTabs();
		//ui.addPanel(mapPanel);
		ui.addPanel(map);
		//ui.addPanel(xpOutline);
		//ui.addPanel(xpPanel);

		UILabel nameLabel = new UILabel(new Vector2i(10, 200), name + " - ");
		nameLabel.setColor(0xbbbbbb);
		nameLabel.setFont(new Font("Helvetica", Font.PLAIN, 24));
		nameLabel.dropShadow = false;
		panel.addComponent(nameLabel);

		playerLevelLabel = new UILabel(new Vector2i(120, 200), "Level: " + playerLevel);
		playerLevelLabel.setColor(0xbbbbbb);
		playerLevelLabel.setFont(new Font("Helvetica", Font.PLAIN, 24));
		playerLevelLabel.dropShadow = false;
		panel.addComponent(playerLevelLabel);

		uiHealthBar = new UIProgressBar(new Vector2i(10, 210), new Vector2i(80 * 3 - 20, 20));
		uiHealthBar.setColor(0x6a6a6a);
		uiHealthBar.setForegroundColor(0xff3a3a);
		panel.addComponent(uiHealthBar);

		UILabel hpLabel = new UILabel(new Vector2i(uiHealthBar.position).add(new Vector2i(2, 18)), "HP");
		hpLabel.setColor(0xbbbbbb);
		hpLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
		hpLabel.dropShadow = false;
		panel.addComponent(hpLabel);

		health = 100;


		uiManaBar = new UIProgressBar(new Vector2i(10, 235), new Vector2i(80 * 3 - 20, 20));
		uiManaBar.setColor(0x6a6a6a);
		uiManaBar.setForegroundColor(0x0033cc);
		panel.addComponent(uiManaBar);

		UILabel mpLabel = new UILabel(new Vector2i(uiManaBar.position).add(new Vector2i(2, 18)), "MP");
		mpLabel.setColor(0xbbbbbb);
		mpLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
		mpLabel.dropShadow = false;
		panel.addComponent(mpLabel);
		mana = 100;

		uiXpBar = new UIProgressBar(new Vector2i(10, 260), new Vector2i(80 * 3 - 20, 20));
		uiXpBar.setColor(0x6a6a6a);
		uiXpBar.setForegroundColor(0x997a00);
		panel.addComponent(uiXpBar);

		UILabel xpLabel = new UILabel(new Vector2i(uiXpBar.position).add(new Vector2i(2, 18)), "XP");
		xpLabel.setColor(0xbbbbbb);
		xpLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
		xpLabel.dropShadow = false;
		panel.addComponent(xpLabel);
		xp = 0;

		xpValLabel = new UILabel(new Vector2i(uiXpBar.position).add(new Vector2i(175, 18)), ((xp/xp_required)*100)+ "%");
		xpValLabel.setColor(0xbbbbbb);
		xpValLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
		xpValLabel.dropShadow = false;
		panel.addComponent(xpValLabel);
	}
	
	public String getName() {
		return name;
	}

	public int getMana() {
		return (int) mana;
	}
	public void setMana(double mana) {
		this.mana = mana;
	}
	
	public double getXp() {
		return xp;
	}
	public void setXp(double xp) {
		this.xp = xp;
	}

	public void grantXp(double xp) {
		this.xp += xp;
	}

	public int getLevel() {return playerLevel; }
	public void setLevel(int Level) {
		this.playerLevel = Level;
	}

	public double getXp_required() {
		return xp_required;
	}

	public boolean solid() {
		return true;
	}
	public void update() {
		if(xp >= xp_required) {
			playerLevel += 1;
			if(xp > xp_required) {
				double xp_remainder = xp - xp_required;
				xp = xp_remainder;
			}
			xp_required = (50 * playerLevel);
			System.out.println(xp_required);
			EffectPlayer shoot = new EffectPlayer("levelup");
			level.add(new ParticleSpawner((int)x, (int)y, 100, 80, level, 2));
			playerLevelLabel.setText("Level: " + playerLevel);
			health += 20;
			totalHealth = health;
			//double xpRemainder = xp - 100;
			//xp = xpRemainder;
			//xpRemainder = 0;
		}

		if(health <= 0.0) {
			respawn();
		}
		time++;
		if (walking) animSprite.update();
		else animSprite.setFrame(0);
		if(fireRate > 0) fireRate--;
		double xa = 0, ya = 0;
		double speed = 1.0;
		if(anim < 7500) anim++; else anim = 0;
		if(input.up) {
			animSprite = up;
			ya-= speed;
		}
		if(input.down) {
			animSprite = down;
			ya+= speed;
		}
		if(input.left) {
			animSprite = right;
			xa-= speed;
		}
		if(input.right) {
			animSprite = left;
			xa+= speed;
		}
		if(input.h) {
			if (inventory.size() == 0) {
				System.out.println("Your inventory is empty.");
			} else {
				inventory.size();
				for (int i = 0; i < inventory.size(); i++) {
					if (inventory.get(i).getName().equals("Health Potion")) {
						if (!hpPotionCD) {
							this.health += 20;
							String tempName = inventory.get(i).getName();
							String tempDesc = inventory.get(i).getDescription();
							inventory.remove(i);
							int count = 0;
							for (int j = 0; j < inventory.size(); j++) {
								if (inventory.get(j).getName().equals("Health Potion"))
									count++;
							}

							if (count == 0) {
								for (int j = 0; j < invLabels.size(); j++) {
									if (invLabels.get(j).text.contains("Health Potion")) {
										invLabels.get(j).setText("");
										if (invLabels.size() > 1) {
											for (int k = j + 1; k < invLabels.size(); k++) {
												String tempText = invLabels.get(k).text;

												invLabels.get(k).setText("");
												invLabels.remove(k);

												invLabels.add(moveInvLabelUp(tempText));
												System.out.println(tempText);
												invPanel.addComponent(invLabels.get(invLabels.size() - 1));
											}
										}
										invLabels.remove(j);
									}
								}
							} else if (count == 1) {
								for (int j = 0; j < invLabels.size(); j++) {
									if (invLabels.get(j).text.contains("Health Potion")) {
										//invLabels.get(j).setText(inventory.get(i).getName() + " - " + inventory.get(i).getDescription());
										invLabels.get(j).setText(tempName + " - " + tempDesc);
										//System.out.println(inventory.get(i).getName() + " " + invLabels.get(j).text + "This");
									}
								}
							} else if (count > 1) {
								for (int j = 0; j < invLabels.size(); j++) {
									if (invLabels.get(j).text.contains("Health Potion")) {
										//invLabels.get(j).setText(inventory.get(i).getName() + " - " + inventory.get(i).getDescription() + " x" + count);
										invLabels.get(j).setText(tempName + " - " + tempDesc + " x" + count);
										System.out.println(inventory.get(i).getName() + " " + invLabels.get(j).text + "thIS");
									}
								}
							}
							hpPotionCD = true;
							break;
						} else {
							System.out.println("Your health potions are on cooldown.");
						}
					} else {
						System.out.println("You do not own any health potions.");
					}
				}
			}
		}

		if(input.m) {
			if (inventory.size() == 0) {
				System.out.println("Your inventory is empty.");
			} else {
				for (int i = 0; i < inventory.size(); i++) {
					if (inventory.get(i).getName().equals("Mana Potion")) {
						if (!mpPotionCD) {
							this.mana += 20;
							String tempName = inventory.get(i).getName();
							String tempDesc = inventory.get(i).getDescription();
							inventory.remove(i);
							int count = 0;
							for (int j = 0; j < inventory.size(); j++) {
								if (inventory.get(j).getName().equals("Mana Potion"))
									count++;
							}

							if (count == 0) {
								for (int j = 0; j < invLabels.size(); j++) {
									if (invLabels.get(j).text.contains("Mana Potion")) {
										invLabels.get(j).setText("");
										if (invLabels.size() > 1) {
											for (int k = j + 1; k < invLabels.size(); k++) {
												String tempText = invLabels.get(k).text;

												invLabels.get(k).setText("");
												invLabels.remove(k);

												invLabels.add(moveInvLabelUp(tempText));
												invPanel.addComponent(invLabels.get(invLabels.size() - 1));
											}
										}
										invLabels.remove(j);
									}
								}
							} else if (count == 1) {
								for (int j = 0; j < invLabels.size(); j++) {
									if (invLabels.get(j).text.contains("Mana Potion")) {
										//invLabels.get(j).setText(inventory.get(i).getName() + " - " + inventory.get(i).getDescription());
										invLabels.get(j).setText(tempName + " - " + tempDesc);
										System.out.println(inventory.get(i).getName() + " " + invLabels.get(j).text + "this");
									}
								}
							} else if (count > 1) {
								for (int j = 0; j < invLabels.size(); j++) {
									if (invLabels.get(j).text.contains("Mana Potion")) {
										//invLabels.get(j).setText(inventory.get(i).getName() + " - " + inventory.get(i).getDescription() + " x" + count);
										invLabels.get(j).setText(tempName + " - " + tempDesc + " x" + count);
										System.out.println(inventory.get(i).getName() + " " + invLabels.get(j).text + " Tsja");
									}
								}
							}
							mpPotionCD = true;
							break;
						} else {
							System.out.println("Your mana potions are on cooldown.");
						}
					} else {
						System.out.println("You do not own any mana potions.");
					}
				}
			}
		}

		if(input.esc && msgPanelShown)
			setMsgPanelShownFalse();

		if(input.esc && sysMsgPanelShown)
			setSystemMsgPanelShownFalse();

		updateTabs();
		if(xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
		
		if(time % 60 == 0) {
			if(mana <= 0.95) {
				mana += 0.5;
			}
		}

		if(time % 120 == 0 && hpPotionCD) {
			hpPotionCD = false;
		}

		if(time % 120 == 0 && mpPotionCD) {
			mpPotionCD = false;
		}
		clear();
		updateShooting();
		
		uiHealthBar.setProgress(health / 100);
		uiManaBar.setProgress(mana / 100);
		uiXpBar.setProgress((xp / xp_required));
		setXpValLabel(xp, xp_required);

		if(inventory.isEmpty() && !invLabels.isEmpty()) {
			for(int i = 0; i < invLabels.size(); i++){
				invLabels.get(i).setText("");
				invLabels.remove(i);
			}
		}

		//System.out.println(xp + "/" + xp_required);
	}
	
	private void respawn() {
		this.setX(spawn_x);
		this.setY(spawn_y);
		health = 100;
	}

	private void clear() {
		for(int i = 0; i < level.getProjectiles().size(); i++) {
			Projectile p = level.getProjectiles().get(i);
			if(p.isRemoved()) level.getProjectiles().remove(i);
		}
	}

	private void updateShooting() {
		if(Mouse.getButton() == 1 && Mouse.getX() < 657 && fireRate <= 0 && mana >= 10) {
			double dx = Mouse.getX() - MainClass.getWindowWidth() / 2;
			double dy = Mouse.getY() - MainClass.getWindowHeight() / 2;
			double dir = Math.atan2(dy, dx);
			if(dir <= 0 && dir >= -1.5 ) animSprite = up;
			if(dir <= -1.5 && dir <= 3) animSprite = up;
			if(dir <= 3 && dir >= 1.5) animSprite = down;
			if(dir <= 1.5 && dir >= 0) animSprite = down;
			shoot(x, y, dir, Mouse.getButton(), this, 20 * damageMultiplier);
			fireRate = WizardProjectile.FIRE_RATE;
			mana -= 10;

			//System.out.println("Mouse X: " + Mouse.getX() + ", Mouse Y: " + Mouse.getY());
		} 
		if(Mouse.getButton() == 3 && Mouse.getX() < 657 && fireRate <= 0 && mana >= 10) {
			double dx = Mouse.getX() - MainClass.getWindowWidth() / 2;
			double dy = Mouse.getY() - MainClass.getWindowHeight() / 2;
			double dir = Math.atan2(dy, dx);
			if(dir <= 0 && dir >= -1.5 ) animSprite = up;
			
			if(dir<= 1.5 && dir <= 3) animSprite = up;
			
			if(dir <= 3 && dir >= 1.5) animSprite = down;
			
			if(dir <= 1.5 && dir >= 0) animSprite = down;
			shoot(x, y, dir, Mouse.getButton(), this, 30 * damageMultiplier);
			fireRate = WizardProjectile.FIRE_RATE;
			mana -= 15;
		}
	}

	private void updateTabs() {
		//invTab
		if(!invTab.isActive() && Mouse.getButton() == 1 && Mouse.getX() > 672 && Mouse.getX() < 726 && Mouse.getY() > 464 && Mouse.getY() < 494) {
			invTab.setActive();
			renderInventoryLabels();
			if(equipTab.isActive()) {
				equipTab.setInactive();
			} else if(statsTab.isActive()) {
				hideStatsLabels();
				statsTab.setInactive();
			} else if(spellsTab.isActive()) {
				spellsTab.setInactive();
			}
		}

		if(!equipTab.isActive() && Mouse.getButton() == 1 && Mouse.getX() > 726 && Mouse.getX() < 780 && Mouse.getY() > 464 && Mouse.getY() < 494) {
			equipTab.setActive();
			if(invTab.isActive()) {
				hideInventoryLabels();
				invTab.setInactive();
			} else if(statsTab.isActive()) {
				hideStatsLabels();
				statsTab.setInactive();
			} else if(spellsTab.isActive()) {
				spellsTab.setInactive();
			}
		}

		if(!spellsTab.isActive() && Mouse.getButton() == 1 && Mouse.getX() > 780 && Mouse.getX() < 834 && Mouse.getY() > 464 && Mouse.getY() < 494) {
			spellsTab.setActive();
			if(invTab.isActive()) {
				hideInventoryLabels();
				invTab.setInactive();
			} else if(statsTab.isActive()) {
				hideStatsLabels();
				statsTab.setInactive();
			} else if(equipTab.isActive()) {
				equipTab.setInactive();
			}
		}

		if(!statsTab.isActive() && Mouse.getButton() == 1 && Mouse.getX() > 834 && Mouse.getX() < 888 && Mouse.getY() > 464 && Mouse.getY() < 494) {
			statsTab.setActive();
			createStatsLabels();
			if(invTab.isActive()) {
				hideInventoryLabels();
				invTab.setInactive();
			} else if(spellsTab.isActive()) {
				spellsTab.setInactive();
			} else if(equipTab.isActive()) {
				equipTab.setInactive();
			}
		}
	}

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob((int) (x - 16), (int) (y - 16), sprite);
	}
	
	public void pickUpItem(Item item) {
		if(inventory.size() >= invFullSize) {
			showSystemMessagePanel("System", "Inventory full.");
		} else if (!item.checkUnique(this)){
			inventory.add(item);
			/*if(item.getName().equals("Weak Staff")) {
				damageMultiplier += 1;
			}*/
			if(invTab.isActive()) {
				int count = 1;
				for (int i = 0; i < inventory.size() - 1; i++) {
					if (inventory.get(i).getName().equals(item.getName()))
						count++;
				}
				if (count > 1) {
					for (int i = 0; i < inventory.size() - 1; i++) {
						if (inventory.get(i).getName().equals(item.getName())) {

							for (int j = 0; j < invLabels.size(); j++) {
								if (invLabels.get(j).text.contains(item.getName())) {
									invLabels.get(j).setText(item.getName() + " - " + item.getDescription() + " x" + count);
								}
							}
							break;
						}
					}
				} else {
					invLabels.add(createInvLabel(item.getName() + " - " + item.getDescription()));
				}
			}
		}

		if(inventory.size() > 0) {
			for(int i = 0; i < invLabels.size(); i++) {
				invPanel.addComponent(invLabels.get(i));
			}
		}
	}

	public void renderInventoryLabels() {
		List<Item> tempInv = new ArrayList<>(inventory);
		inventory.clear();
		for(int i = 0; i < tempInv.size(); i++) {
			pickUpItem(tempInv.get(i));
		}
	}

	public void hideInventoryLabels() {
		for(int i = 0; i < invLabels.size(); i++) {
			invLabels.get(i).setText("");
		}

		invLabels.clear();
	}

	public UILabel createInvLabel(String itemName) {
		if(invLabels.isEmpty()) {
			inventoryLabel = new UILabel(new Vector2i(5, 15), itemName);
		} else {
			inventoryLabel = new UILabel(new Vector2i(5, invLabels.get(invLabels.size() - 1).position.getY() + 15), itemName);
		}
		inventoryLabel.setColor(0xbbbbbb);
		inventoryLabel.setFont(new Font("Helvetica", Font.PLAIN, 10));
		inventoryLabel.dropShadow = false;
		return inventoryLabel;
	}

	public void createStatsLabels() {
		UILabel maxInv = new UILabel(new Vector2i(5, 15), "Max Inventory Size: " + invFullSize);
		maxInv.setColor(0xbbbbbb);
		maxInv.setFont(new Font("Helvetica", Font.PLAIN, 10));
		maxInv.dropShadow = false;

		UILabel damgMult = new UILabel(new Vector2i(5, 30), "Damage Multiplier: " + getDamageMultiplier());
		damgMult.setColor(0xbbbbbb);
		damgMult.setFont(new Font("Helvetica", Font.PLAIN, 10));
		damgMult.dropShadow = false;

		statsLabels.add(maxInv);
		statsLabels.add(damgMult);
		for(int i = 0; i < statsLabels.size(); i++) {
			invPanel.addComponent(statsLabels.get(i));
		}
	}

	public void hideStatsLabels() {
		for(int i = 0; i < statsLabels.size(); i++) {
			statsLabels.get(i).setText("");
		}

		statsLabels.clear();
	}

	public UILabel moveInvLabelUp(String text) {
		if(invLabels.isEmpty()) {
			inventoryLabel = new UILabel(new Vector2i(5, 15), text);
		} else {
			inventoryLabel = new UILabel(new Vector2i(5, invLabels.get(invLabels.size() - 1).position.getY()), text);
		}
		inventoryLabel.setColor(0xbbbbbb);
		inventoryLabel.setFont(new Font("Helvetica", Font.PLAIN, 10));
		inventoryLabel.dropShadow = false;
		return inventoryLabel;
	}

	public boolean isInvFull() {
		if(!invLabels.isEmpty() && inventory.size() >= invFullSize) {
			return true;
		} else {
			return false;
		}
	}

	public void setXpValLabel(double xp, double xp_required) {
		double val = (xp/xp_required) * 100;
		xpValLabel.setText(BigDecimal.valueOf(val).setScale(0, RoundingMode.UP) + "%");
	}

	public boolean getInputE() {
		return input.e;
	}

	public void showMessagePanel(String NPCName, String message) {
		if(!msgPanelShown) {
			ui.addPanel(msgpanelOutline);
			ui.addPanel(msgpanel);

			NPCNameLabel = new UILabel(new Vector2i(40, 40), NPCName + " says:");
			NPCNameLabel.setColor(0x000000);
			NPCNameLabel.setFont(new Font("Helvetica", Font.BOLD, 18));
			NPCNameLabel.dropShadow = false;
			msgpanel.addComponent(NPCNameLabel);

			text = new UILabel(new Vector2i(40, 80), "'" + message + "'");
			text.setColor(0x000000);
			text.setFont(new Font("Helvetica", Font.PLAIN, 24));
			text.dropShadow = false;
			msgpanel.addComponent(text);

			closeInstr = new UILabel(new Vector2i(460, 110), "Press 'ESC' to close.");
			closeInstr.setColor(0x000000);
			closeInstr.setFont(new Font("Helvetica", Font.PLAIN, 16));
			closeInstr.dropShadow = false;
			msgpanel.addComponent(closeInstr);
			msgPanelShown = true;
		}
	}

	public void setMsgPanelShownFalse() {
		if(msgPanelShown) {
			msgPanelShown = false;
			NPCNameLabel.setText("");
			text.setText("");
			closeInstr.setText("");
			ui.removePanel(msgpanel);
			ui.removePanel(msgpanelOutline);
		}
	}

	public void showSystemMessagePanel(String NPCName, String message) {
		if(!sysMsgPanelShown) {
			ui.addPanel(msgpanelOutline);
			ui.addPanel(msgpanel);

			NPCNameLabel = new UILabel(new Vector2i(40, 40), NPCName + " says:");
			NPCNameLabel.setColor(0x000000);
			NPCNameLabel.setFont(new Font("Helvetica", Font.BOLD, 18));
			NPCNameLabel.dropShadow = false;
			msgpanel.addComponent(NPCNameLabel);

			text = new UILabel(new Vector2i(40, 80), "'" + message + "'");
			text.setColor(0x000000);
			text.setFont(new Font("Helvetica", Font.PLAIN, 24));
			text.dropShadow = false;
			msgpanel.addComponent(text);

			closeInstr = new UILabel(new Vector2i(460, 110), "Press 'ESC' to close.");
			closeInstr.setColor(0x000000);
			closeInstr.setFont(new Font("Helvetica", Font.PLAIN, 16));
			closeInstr.dropShadow = false;
			msgpanel.addComponent(closeInstr);
			sysMsgPanelShown = true;
		}
	}

	public void setSystemMsgPanelShownFalse() {
		if(sysMsgPanelShown) {
			sysMsgPanelShown = false;
			NPCNameLabel.setText("");
			text.setText("");
			closeInstr.setText("");
			ui.removePanel(msgpanel);
			ui.removePanel(msgpanelOutline);
		}
	}

	public boolean getMsgPanelShown() {
		return msgPanelShown;
	}

	public int getDamageMultiplier() {
		return damageMultiplier;
	}

	public void setDamageMultiplier(int damageMultiplier) {
		this.damageMultiplier = damageMultiplier;
	}

	public void generateInvTabs() {
		invTab.setLabel("Inventory");
		invTab.setActive();

		spellsTab.setLabel("Spells");
		spellsTab.setInactive();

		equipTab.setLabel("Equipped");
		equipTab.setInactive();

		statsTab.setLabel("Stats");
		statsTab.setInactive();

		ui.addPanel(tabsOutline);
		ui.addPanel(invTab);
		ui.addPanel(spellsTab);
		ui.addPanel(equipTab);
		ui.addPanel(statsTab);
	}

	public void renderInvSprites() {
		for(int i = 0; i < inventory.size(); i++) {
			UIImage invSprite = (UIImage) new UIImage(new Vector2i((300 - 75) * 3, 60), new Vector2i(70 * 3, 53 * 3), inventory.get(i).getIconPath());
			ui.addPanel(invSprite);
		}
	}
	
}
