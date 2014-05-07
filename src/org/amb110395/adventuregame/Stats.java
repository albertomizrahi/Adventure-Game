package org.amb110395.adventuregame;

public class Stats {
	
	private int level;
	private int experience;
	private int hitpoints;

	private int strength;
	private int archery;
	private int magic;
	
	public Stats() {
		level = 1;
		hitpoints = 100;
		experience = 0;
		strength = 0;
		archery = 0;
		magic = 0;
	}
	
	public Stats(int level, int hitpoints, int experience, int strength, int archery, int magic) {
		this.level = level;
		this.hitpoints = hitpoints;
		this.experience = experience;
		this.strength = strength;
		this.archery = archery;
		this.magic = magic;
	}
	
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public void increaseLevel() {
		level++;
	}

	public int getExperience() {
		return experience;
	}
	public void setExperience(int experience) {
		this.experience = experience;
	}
	public void increaseExperience(int increase) {
		experience += increase;
	}

	public int getHitpoints() {
		return hitpoints;
	}
	public void setHitpoints(int hitpoints) {
		this.hitpoints = hitpoints;
	}
	public void updateHitpoints(int change) {
		hitpoints += change;
	}

	public int getStrength() {
		return strength;
	}
	public void setStrength(int strength) {
		this.strength = strength;
	}
	public void updateStrengh(int change) {
		strength += change;
	}

	public int getArchery() {
		return archery;
	}
	public void setArchery(int archery) {
		this.archery = archery;
	}
	public void updateArchery(int change) {
		archery += change;
	}

	public int getMagic() {
		return magic;
	}
	public void setMagic(int magic) {
		this.magic = magic;
	}
	public void updateMagic(int change) {
		magic += change;
	}
	

}
