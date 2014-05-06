package org.amb110395.adventuregame;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Tile {
	private int x;
	private int y;
	private Sprite terrain;
	private boolean isPassable;
	
	public Tile(String ref, int x, int y, boolean isPassable) {
		this.terrain = SpriteStore.get().getSprite(ref);
		this.x = x;
		this.y = y;
		this.isPassable = isPassable;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Sprite getTerrain() {
		return terrain;
	}
	public void setTerrain(Sprite terrain) {
		this.terrain = terrain;
	}
	
	public boolean isPassable() {
		return isPassable;
	}
	
	public Rectangle getBounds() {
		// TODO do not hard code tile size to 32
		return new Rectangle(x,y,32,32);
	}
	
	/**
	 * Draw this entity to the graphics context provided
	 * 
	 * @param g The graphics context on which to draw
	 */
	public void draw(Graphics g) {
		terrain.draw(g,(int) x,(int) y);
	}
}
