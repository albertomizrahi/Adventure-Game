package org.amb110395.adventuregame;

public class MainCharacter extends Entity{
	public static final int MOVE_SPEED = 150;
	
	private Map map;
	
	public MainCharacter(String ref, int x, int y, Map map) {
		super(ref, x, y);
		this.map = map;
	}
	
	public void move(long delta) {
//		if ((getHorizontalVelocity() < 0) && (getX() < 0 )) {
//			return;
//		}
//		else if ((getHorizontalVelocity() > 0) && (getX() > Constants.WINDOW_WIDTH - getSprite().getWidth()-1)) {
//			return;
//		}
//		
//		if ((getVerticalVelocity() < 0) && (getY() < 1)) {
//			return;
//		}
//		else if ((getVerticalVelocity() > 0) && (getY() > Constants.WINDOW_HEIGHT - getSprite().getHeight()-3)) {
//			return;
//		}
		
	
		super.move(delta);
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
		else if ((getVerticalVelocity() > 0) && (getY() > map.getMapHeight() - getSprite().getHeight()-3)) {
			return;
		}
		
		super.moveBy(deltaX, deltaY);
	}

}
