package org.amb110395.adventuregame;

public class Weapon extends Entity{


	public Weapon(String ref, int x, int y) {
		super(ref, x, y);
	}

	public void collidedWith(Entity entity) {
		if (entity instanceof Enemy) {
			entity.getStats().updateHitpoints(-10);
			System.out.println("Enemy's health: " + entity.getStats().getHitpoints());
		}
		
	}
	
	

}
