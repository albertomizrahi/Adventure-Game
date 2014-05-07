package org.amb110395.adventuregame;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Entity {
	private double x;
	private double y;
	private double dx;
	private double dy;
	private Direction dir;
	private int tileX;
	private int tileY;
	private Sprite sprite;
	
	private Stats stats;
	
	public Entity(String ref, int x, int y) {
		this.sprite = SpriteStore.get().getSprite(ref);
		this.x = x;
		this.y = y;
		this.dir = Direction.STATIONARY;
		this.stats = new Stats();
	}
	
	public Entity(int x, int y) {
		this.x = x;
		this.y = y;
		this.dir = Direction.STATIONARY;
		this.stats = new Stats();
	}
	
	public Entity(String ref, int x, int y, Stats stats) {
		this.sprite = SpriteStore.get().getSprite(ref);
		this.x = x;
		this.y = y;
		this.dir = Direction.STATIONARY;
		this.stats = stats;
	}
	
	
	public void moveBy(double deltaX, double deltaY) {
		x += deltaX;
		y += deltaY;
		
		updateTilePos();
		
		//System.out.println(tileX + " , " + tileY);
	}
	
	public void move(long time) {
		x += (time * dx) / 1000;
		y += (time * dy) / 1000;
	}
	
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
