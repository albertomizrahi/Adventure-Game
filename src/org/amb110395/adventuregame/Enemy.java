package org.amb110395.adventuregame;

public class Enemy extends Entity{
	
	public Enemy(String ref, int x, int y, Map map) {
		super(ref, x, y);
		super.updateTilePos();
	}

	public void collidedWith(Entity entity) {		
	}	

}
