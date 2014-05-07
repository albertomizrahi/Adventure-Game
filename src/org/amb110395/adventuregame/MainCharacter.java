package org.amb110395.adventuregame;

import java.awt.Graphics;
import java.awt.Rectangle;

public class MainCharacter extends Entity{
	public static final int MOVE_SPEED = 150;
	
	private double distanceToMoveX;
	private double distanceToMoveY;
	
	private Map map;
	
	private Animation current;
	private Animation walkRight;	
	private Animation walkLeft;
	private Animation walkUp;
	private Animation walkDown;
	
	public MainCharacter(int x, int y, Map map) {
		super(x, y);
		this.map = map;
		super.updateTilePos();
		
		walkRight = new Animation(SpriteStore.get().loadSpritesFromSheet("sprites/character_sprites.png", 6, 6, 0, 2, Constants.TILE_SIZE), 250);
		walkLeft = new Animation(SpriteStore.get().loadSpritesFromSheet("sprites/character_sprites.png", 5, 5, 0, 2, Constants.TILE_SIZE), 250);
		walkUp = new Animation(SpriteStore.get().loadSpritesFromSheet("sprites/character_sprites.png", 7, 7, 0, 2, Constants.TILE_SIZE), 250);
		walkDown = new Animation(SpriteStore.get().loadSpritesFromSheet("sprites/character_sprites.png", 4, 4, 0, 2, Constants.TILE_SIZE), 250);

		setSprite(walkRight.getCurrentSprite());
	}
	
	public void move(long time) {
		attemptMove(time);
		super.moveBy(distanceToMoveX, distanceToMoveY);
		
		if (getDirection().equals(Direction.RIGHT))
			walkRight.update(time, distanceToMoveX);
		else if(getDirection().equals(Direction.LEFT))
			walkLeft.update(time, distanceToMoveX);
		else if(getDirection().equals(Direction.UP))
			walkUp.update(time, distanceToMoveY);
		else if(getDirection().equals(Direction.DOWN))
			walkDown.update(time, distanceToMoveY);
		
	}
	
	public void attemptMove(long time) {
		
		double distanceX = (time * getHorizontalVelocity()) / 1000;
		double distanceY = (time * getVerticalVelocity()) / 1000;
		double deltaX = 0;
		double deltaY = 0;
		
		if (getDirection().equals(Direction.RIGHT)) {
			double x = getX() + distanceX + getSprite().getWidth();
			double yTopCorner = getY();
			double yBotCorner = (getY() + getSprite().getHeight()-1);
			
			Tile topRight = map.getTileInCoord(x, yTopCorner-1);
			Tile botRight =  map.getTileInCoord(x,yBotCorner-1);
			
			
			if (!topRight.isPassable() || !botRight.isPassable()) {
				 deltaX = x - topRight.getX();
			}
		} 
		else if (getDirection().equals(Direction.LEFT)) {
			double x = getX() + distanceX;
			double yTopCorner = getY();
			double yBotCorner = (getY() + getSprite().getHeight()-1);
			
			Tile topLeft = map.getTileInCoord(x, yTopCorner);
			Tile botLeft =  map.getTileInCoord(x, yBotCorner);
			
			if (!topLeft.isPassable() || !botLeft.isPassable()) {
				 deltaX = x - (topLeft.getX() + map.getTileSize());
			}
		} 
		else if (getDirection().equals(Direction.UP)) {
			double y = getY() + distanceY-1;
			double xLeftCorner =  getX();
			double xRightCorner = (getX() + getSprite().getWidth()-1);
			
			Tile topLeft = map.getTileInCoord(xLeftCorner, y);
			Tile topRight =  map.getTileInCoord(xRightCorner, y);
			
			if (!topLeft.isPassable() || !topRight.isPassable()) {
				 deltaY = y - (topLeft.getY() + map.getTileSize());
			}
		} 
		else if (getDirection().equals(Direction.DOWN)) {
			double y = getY() + distanceY + getSprite().getHeight();
			double xLeftCorner =  getX();
			double xRightCorner = (getX() + getSprite().getWidth()-1);
			
			Tile botLeft = map.getTileInCoord(xLeftCorner, y);
			Tile botRight =  map.getTileInCoord(xRightCorner, y);
			
			if (!botLeft.isPassable() || !botRight.isPassable()) {
				 deltaY = y - botLeft.getY();
			}
		} 
		
		distanceToMoveX = distanceX - deltaX;
		distanceToMoveY = distanceY - deltaY;
		
	}
	
	
	public void moveBy(double deltaX, double deltaY) {
		if ((getHorizontalVelocity() < 0) && (getX() < 0 )) {
			return;
		}
		else if ((getHorizontalVelocity() > 0) && (getX() > map.getMapWidth() - getSprite().getWidth()-1)) {
			return;
		}
		
		if ((getVerticalVelocity() < 0) && (getY() < 1)) {
			return;
		}
		else if ((getVerticalVelocity() > 0) && (getY() > map.getMapHeight() - getSprite().getHeight()-2)) {
			return;
		}
		
		super.moveBy(deltaX, deltaY);
	}
	
//	public Rectangle getBounds() {
//		return new Rectangle((int)getX(),(int)getY(), current.getCurrentSprite().getWidth(), current.getCurrentSprite().getHeight());
//	}
	
	public boolean hasCollidedWith(Entity entity) {
		return getBounds().intersects(entity.getBounds());
	}
	
	public void collidedWith(Entity entity) {
		if (entity instanceof Enemy) {
			setX(getX() + getHorizontalVelocity()*-0.1);
			setY(getY() + getVerticalVelocity()*-0.1);
			
			getStats().updateHitpoints(-10);
		}
	}
	
	public void draw(Graphics g, double scrollX, double scrollY) {	
		if (getDirection().equals(Direction.RIGHT))
			current = walkRight;
		else if(getDirection().equals(Direction.LEFT))
			current = walkLeft;
		else if(getDirection().equals(Direction.UP))
			current = walkUp;
		else if(getDirection().equals(Direction.DOWN))
			current = walkDown;
		else 
			current = walkRight;
		
		
		 setSprite(current.getCurrentSprite());
		 super.draw(g, scrollX, scrollY);
	}

}
