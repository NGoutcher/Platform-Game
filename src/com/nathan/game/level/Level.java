package com.nathan.game.level;

import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;

import com.nathan.game.MainClass;
import com.nathan.game.entity.Entity;
import com.nathan.game.entity.items.Item;
import com.nathan.game.entity.mob.Mob;
import com.nathan.game.entity.mob.NPC;
import com.nathan.game.entity.mob.Player;
import com.nathan.game.entity.particle.Particle;
import com.nathan.game.entity.projectile.Projectile;
import com.nathan.game.entity.spawner.ParticleSpawner;
import com.nathan.game.graphics.Screen;
import com.nathan.game.level.tile.Tile;
import com.nathan.game.util.Vector2i;

public class Level {

	protected int width, height;
	protected int[] tilesInt;
	protected int[] tiles;
	
	int[] rand = null;
	
	private List<Entity> entities = new ArrayList<Entity>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Particle> particles = new ArrayList<Particle>();	
	private List<Player> players = new ArrayList<Player>();
	private static String imagePath = "/levels/spawn.png";
	public ArrayList<NPC> npcs = new ArrayList<>();
	public int time = 0;
	
	private Comparator<Node> nodeSorter = new Comparator<Node>() {
		public int compare(Node n0, Node n1) {
			if(n1.fCost < n0.fCost) return +1;
			if(n1.fCost > n0.fCost) return -1;
			return 0;
		}
	};
	
	public static Level spawn = new SpawnLevel(imagePath);
	
	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		tilesInt = new int[ width*height];
		generateLevel();
	}
	
	public Level(String path) {
		loadLevel(path);
		generateLevel();
	}
	
	protected void generateLevel() {
		
	}
	
	
	protected void loadLevel(String path) {
		
	}
	
	public void update() {
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).update();
		}
		for(int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).update();
		}
		for(int i = 0; i < particles.size(); i++) {
			particles.get(i).update();
		}
		for(int i = 0; i < players.size(); i++) {
			players.get(i).update();
		}

		if(!checkNearNPC()) {
			getClientPlayer().setMsgPanelShownFalse();
		}

		remove();
	}
	
	private void remove() {
		for(int i = 0; i < entities.size(); i++) {
			if(entities.get(i).isRemoved()) entities.remove(i);
		}
		for(int i = 0; i < projectiles.size(); i++) {
			if(projectiles.get(i).isRemoved()) projectiles.remove(i);
		}
		for(int i = 0; i < particles.size(); i++) {
			if(particles.get(i).isRemoved()) particles.remove(i);
		}
		for(int i = 0; i < players.size(); i++) {
			if(players.get(i).isRemoved()) players.remove(i);
		}
	}
	
	public List<Projectile> getProjectiles() {
		return projectiles;
	}
	
	
	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);
		int x0 = (xScroll - 16)/16;
		int x1 = (xScroll + screen.width + 16) >> 4;
		int y0 = (yScroll - 16)/16;
		int y1 = (yScroll + screen.height + 16) >> 4;
		
		for(int y = y0; y < y1; y++) {
			for(int x = x0; x < x1; x++) {
				getTile(x, y).render(x, y, screen);
				
			}
		}
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).render(screen);
		}
		for(int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(screen);
		}
		for(int i = 0; i < particles.size(); i++) {
			particles.get(i).render(screen);
		}
		for(int i = 0; i < players.size(); i++) {
			players.get(i).render(screen);
		}
	}
	
	public void add(Entity e) {
		e.init(this);
		if (e instanceof Particle) {
			particles.add((Particle) e);
		} else if(e instanceof Projectile) {
			projectiles.add((Projectile) e);
		} else if (e instanceof Player) {
			players.add((Player) e);
		} else {
			entities.add(e);
			if(e.getClass().equals(NPC.class)) {
				npcs.add((NPC) e);
			}
		}
		
	}
	
	public void addProjectile(Projectile p) {
		p.init(this);
		projectiles.add(p);
	}
	
	public boolean tileCollision(int x, int y, int size, int xOffset, int yOffset) {
		boolean solid = false;
		for(int c = 0; c < 4; c++) {
			int xt = (x - c % 2 * size + xOffset) >> 4;
			int yt = (y - c / 2 * size + yOffset) >> 4;
			if(getTile(xt, yt).solid()) solid = true;
		}
		return solid;
	}
	
	public boolean mobCollision(int x, int y, int size, int xOffset, int yOffset, Mob from, double damage) {
		boolean solid = false;
		for(int i = 0; i < entities.size(); i++) {
			if(entities.get(i).getX() >= x - 16  && entities.get(i).getX() <= x + 16 && 
					entities.get(i).getY() >= y - 16  && entities.get(i).getY() <= y + 16 && 
					entities.get(i) instanceof Mob && !(entities.get(i).equals(from))) {
				if (!(entities.get(i) instanceof NPC) && from instanceof Player)
					System.out.println(((Mob) entities.get(i)).getHealth() + " " + damage);
				((Mob) (entities.get(i))).setHealthDamage(damage);
				this.add(new ParticleSpawner((int) entities.get(i).getX(), (int) entities.get(i).getY(), 10, 10, this, 3));


				if (from instanceof Player && ((Mob) (entities.get(i))).isDead()) {
					System.out.println("Yes" + ((Mob) entities.get(i)).getHealth());
					((Player) from).grantXp(((Mob) entities.get(i)).getXpReward());
					//((Player) from).setXpValLabel(((Player) from).getXp(), ((Player) from).getXp_required());
					int mobx = entities.get(i).getX();
					int moby = entities.get(i).getY();
					/*entities.get(i).respawnShooter(mobx, moby);	*/
				}
				solid = true;
			}
		}
		return solid;
	}
	
	public boolean playerCollision(int x, int y, int size, int xOffset, int yOffset, Mob from, double damage) {
		boolean solid = false;
		for(int i = 0; i < players.size(); i++) {
			if(players.get(i).getX() >= x - 16  && players.get(i).getX() <= x + 16 && 
					players.get(i).getY() >= y - 16  && players.get(i).getY() <= y + 16 && 
					!(players.get(i).equals(from))) {
				players.get(i).setHealthDamage(damage);
				this.add(new ParticleSpawner((int) players.get(i).getX(), (int) players.get(i).getY(), 10, 10, this, 3));
				solid = true;
			}
		}
		return solid;
	}
	
	public boolean playerItemCollision(int x, int y, int size, int xOffset, int yOffset, Item item) {
		boolean solid = false;
		for(int i = 0; i < players.size(); i++) {
				if (players.get(i).getX() >= x - 16 && players.get(i).getX() <= x + 16 &&
						players.get(i).getY() >= y - 16 && players.get(i).getY() <= y + 16) {
					if(!players.get(i).isInvFull() && players.get(i).getInputE() && !item.checkUnique(getClientPlayer())) {
						players.get(i).pickUpItem(item);
						solid = true;
					} else if(players.get(i).isInvFull() && players.get(i).getInputE()) {
						players.get(i).showSystemMessagePanel("System", "Inventory is full.");
					} else if(item.checkUnique(getClientPlayer()) && getClientPlayer().getInputE()) {
						getClientPlayer().showSystemMessagePanel("System", "You can only carry one of these items.");
					}
				}
		}
		return solid;
	}
	
	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) return Tile.voidTile;
		
		if(tiles[x + y * width] == Tile.col_spawn_grass1) return Tile.grass1;
		if(tiles[x + y * width] == Tile.col_spawn_grass2) return Tile.grass2;
		if(tiles[x + y * width] == Tile.col_spawn_grass3) return Tile.grass3;
		if(tiles[x + y * width] == Tile.col_spawn_grass4) return Tile.grass4;
		
		if(tiles[x + y * width] == Tile.col_spawn_gravel1) return Tile.spawn_gravel1;
		if(tiles[x + y * width] == Tile.col_spawn_gravel2) return Tile.spawn_gravel2;
		
		if(tiles[x + y * width] == Tile.col_spawn_well1) return Tile.spawn_well1;
		if(tiles[x + y * width] == Tile.col_spawn_well2) return Tile.spawn_well2;
		
		if(tiles[x + y * width] == Tile.col_wall1) return Tile.wall1;
		if(tiles[x + y * width] == Tile.col_wall2) return Tile.wall2;
		
		if(tiles[x + y * width] == Tile.col_fence_horiz) return Tile.fence_horiz;
		if(tiles[x + y * width] == Tile.col_fence_horizdownright) return Tile.fence_horizdownright;
		if(tiles[x + y * width] == Tile.col_fence_horizdownleft) return Tile.fence_horizdownleft;
		if(tiles[x + y * width] == Tile.col_fence_downleft) return Tile.fence_downleft;
		if(tiles[x + y * width] == Tile.col_fence_downright) return Tile.fence_downright;
		if(tiles[x + y * width] == Tile.col_fence_horizupleft) return Tile.fence_horizupleft;
		if(tiles[x + y * width] == Tile.col_fence_horizupright) return Tile.fence_horizupright;
		
		if(tiles[x + y * width] == Tile.col_floor1) return Tile.floor1;
		
		return Tile.voidTile;
	}
	
	public List<Player> getPlayer() {
		return players;
	}
	
	public Player getPlayerAt(int index) {
	return players.get(index);
	}
	
	public Player getClientPlayer() {
		return players.get(0);
	}

	public List<Node> findPath(Vector2i start, Vector2i goal) {
		List<Node> openList = new ArrayList<Node>();
		List<Node> closedList = new ArrayList<Node>();
		Node current = new Node(start, null, 0, getDistance(start, goal));
		openList.add(current);
		while(openList.size() > 0) {
			Collections.sort(openList, nodeSorter);
			current = openList.get(0);
			if(current.tile.equals(goal)) {
				List<Node> path = new ArrayList<Node>();
				while(current.parent != null) {
					path.add(current);
					current = current.parent;
				}
				openList.clear();
				closedList.clear();
				return path;
			}
			openList.remove(current);
			closedList.add(current);
			
			for(int i = 0; i < 9; i++) {
				if(i == 4) continue;
				int x = current.tile.getX();
				int y = current.tile.getY();
				int xi = (i % 3) - 1;
				int yi = (i / 3) - 1;
				Tile at = getTile(x + xi, y + yi);
				if(at == null) continue;
				if(at.solid()) continue;
				Vector2i a = new Vector2i(x + xi, y + yi);
				double gCost = current.gCost + (getDistance(current.tile, a) == 1 ? 1 : 0.95);
				double hCost = getDistance(a, goal);
				Node node = new Node(a, current, gCost, hCost);
				if(vecInList(closedList, a) && gCost >= node.gCost) continue;
				if(!vecInList(openList, a) || gCost < node.gCost) openList.add(node);
			}
		}
		closedList.clear();
		return null;
	}
	
	private boolean vecInList(List<Node> list, Vector2i vector) {
		for(Node n : list) {
			if(n.tile.equals(vector)) return true;
		}
		return false;
	}
	
	private double getDistance(Vector2i tile, Vector2i goal) {
		double dx = tile.getX() - goal.getX();
		double dy = tile.getY() - goal.getY();
		return Math.sqrt(dx*dx + dy*dy);
	}
	
	public List<Entity> getEntities(Entity e, int radius) {
		List<Entity> result = new ArrayList<Entity>();
		int ex = (int)e.getX();
		int ey = (int)e.getY();
		for(int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			if(entity.equals(e) || entity instanceof ParticleSpawner || entity instanceof Particle) continue;
			int x = (int) entity.getX();
			int y = (int) entity.getY();
			
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			double distance = Math.sqrt((dx * dx) + (dy * dy));
			if(distance <= radius) {
				result.add(entity);
			}
		}
		return result;
	}
	
	public List<Player> getPlayers(Entity e, int radius) {
		List<Player> result = new ArrayList<Player>();
		int ex = (int) e.getX();
		int ey = (int) e.getY();
		for(int i = 0; i < players.size(); i++) {
			Player player = players.get(i);
			int x = (int) player.getX();
			int y = (int) player.getY();
			
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			double distance = Math.sqrt((dx * dx) + (dy * dy));
			if(distance <= radius) {
				result.add(player);
			}
		}
		return result;
	}

	public int countItemsDropped() {
		int count = 0;
		for(int i = 0; i < entities.size(); i++) {
			if(entities.get(i) instanceof Item) {
				count++;
			}
		}
		return count;
	}

	public String getImagePath(){return imagePath;}

	public boolean checkNearNPC() {
		for(int i = 0; i < npcs.size(); i++) {
			if((getClientPlayer().getX() > npcs.get(i).getX() - 20 && getClientPlayer().getX() < npcs.get(i).getX() + 20) &&
					(getClientPlayer().getY() > npcs.get(i).getY() - 20 && getClientPlayer().getY() < npcs.get(i).getY() + 20)){
				return true;
			}
		}
		return false;
	}

	public int[] randomSpawn() {
		int[] xy = new int[2];
		Random rand = new Random();

		xy[0] = rand.nextInt(width);
		xy[1] = rand.nextInt(height);

		if(getTile(xy[0], xy[1]).solid() || getTile(xy[0] - 1, xy[1]).solid()) {
			return randomSpawn();
		}

		return xy;
	}
}
