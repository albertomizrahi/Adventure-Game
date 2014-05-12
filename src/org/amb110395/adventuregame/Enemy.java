package org.amb110395.adventuregame;

public class Enemy extends Entity{
	
	public Enemy(String ref, int x, int y, Stats stats) {
		super(ref, x, y);
		super.updateTilePos();
	}

	public void collidedWith(Entity entity) {		
	}	

}
