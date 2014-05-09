package org.amb110395.adventuregame;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Entity {
	/* (x,y) position of the game's entity with respect to the entire map */
	private double x;
	private double y;
	/* Horizontal and vertical velocity of the entity */
	private double dx;
	private double dy;
	/* Direction the entity is facing to */
	private Direction dir;
	/* (x,y) tile coordinates of the entity in the 2d tile map */
	private int tileX;
	private int tileY;
	/* The current sprite of entity that will be drawn (This may change if animations are used)*/
	private Sprite sprite;
	/* Keep track of entity RPG stats */
	private Stats stats;
	
	public Entity(int x, int y) {
		this.x = x;
		this.y = y;
		this.dir = Direction.STATIONARY;
		this.stats = new Stats();
		
		// Determine the entity's tile position using its (x,y) position
		updateTilePos();
	}
	
	public Entity(String ref, int x, int y) {
		this(x,y);
		this.sprite = SpriteStore.get().getSprite(ref);
	}
	
	public Entity(String ref, int x, int y, Stats stats) {
		this(ref,x,y);
		this.stats = stats;
	}
	
	public void moveBy(double deltaX, double deltaY) {
		x += deltaX;
		y += deltaY;
		
		// Update the entity's (x,y) tile after it has moved 
		updateTilePos();
	}
	
	public void move(long time) {
		x += (time * dx) / 1000;
		y += (time * dy) / 1000;
	}
	
	/**
	 * Update the entity's (x,y) tile position according to its (x,y) position in the map.
	 * Note that (x,y) coordinates must be inverted in order to update the tile correctly, 
	 * i.e., x coordinates are used to find the Y tile and y coordinates are used to find the X tile.
	 * This is done so that the corresponding tile position directly corresponds to the one in the 2d array
	 */
	public void updateTilePos() {
		tileX = (int) y / Constants.TILE_SIZE;
		tileY = (int) x / Constants.TILE_SIZE;
	}
	
	public int getX() {
		return (int)x;
	}
	public void setX(double x) {
		this.x = x;
	}
	
	public int getY() {
		return (int)y;
	}
	public void setY(double y) {
		this.y = y;
	}
	
	public double getHorizontalVelocity() {
		return dx;
	}
	public void setHorizontalVelocity(int dx) {
		this.dx = dx;
	}
	
	public double getVerticalVelocity() {
		return dy;
	}
	public void setVerticalVelocity(int dy) {
		this.dy = dy;
	}
	
	public Direction getDirection() {
		return dir;
	}
	public void setDirection(Direction dir) {
		this.dir = dir;
	}
	
	public int getTileX() {
		return tileX;
	}
	public void setTileX(int tileX) {
		this.tileX = tileX;
	}
	
	public int getTileY() {
		return tileY;
	}
	public void setTileY(int tileY) {
		this.tileY = tileY;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	
	public Rectangle getBounds() {
		return new Rectangle((int)x,(int)y, getSprite().getWidth(), getSprite().getHeight());
	}
	public boolean hasCollidedWith(Entity entity) {
		return getBounds().intersects(entity.getBounds());
	}
	public abstract void collidedWith(Entity entity);
	
	
	public Stats getStats() {
		return stats;
	}

	
	public void draw(Graphics g, double scrollX, double scrollY) {
		sprite.draw(g, (int)(x - scrollX), (int)(y - scrollY));
	}
}
