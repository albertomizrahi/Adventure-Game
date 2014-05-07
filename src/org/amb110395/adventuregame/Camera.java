package org.amb110395.adventuregame;

public class Camera {
	
	private double x;
	private double y;
	private int cameraWidth;
	private int cameraHeight;
	
	public Camera(double x, double y, int cameraWidth, int cameraHeight) {
		this.x = x;
		this.y = y;
		this.cameraWidth = cameraWidth;
		this.cameraHeight = cameraHeight;
	}
	
	public int getX() {
		return (int) x;
	}
	public void setX(double x) {
		this.x = x;
	}
	
	public int getY() {
		return (int) y;
	}
	public void setY(double y) {
		this.y = y;
	}
	
	public void update(Entity character, Map map) {
		x = character.getX() - cameraWidth/2.0;
		y = character.getY() - cameraHeight/2.0;
		
		if (x < 0)
			x = 0;
		else if (x + cameraWidth > map.getMapWidth())
			x = map.getMapWidth() - cameraWidth;
		
		if (y < 0)
			y = 0;
		else if(y + cameraHeight > map.getMapHeight())
			y = map.getMapHeight() - cameraHeight;
	}
	
}
