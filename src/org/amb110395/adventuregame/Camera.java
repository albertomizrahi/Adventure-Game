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
	
}
