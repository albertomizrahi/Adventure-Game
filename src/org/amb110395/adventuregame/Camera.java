package org.amb110395.adventuregame;

public class Camera {
	
	/* X coordinate of the top-left corner of the camera */
	private double x;
	/* Y coordinate of the top-left corner of the camera */
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
	
	/**
	 * Update the camera coordinates while making sure that if the player reaches one of the ends of the map,
	 * the camera doesn't cross the map limit.
	 * 
	 * @param character The main player. Its position determines how the camera moves
	 * @param map	The map object used to get the map's width and height
	 */
	public void update(Entity player, Map map) {
		/*
		 * Since the player is positioned in the middle of the screen, we use its position coordinates along with half the
		 * camera's width and height to determine the top-left coordinate of the camera
		 */
		x = player.getX() - cameraWidth/2.0;
		y = player.getY() - cameraHeight/2.0;
		
		/* Make sure that the camera doesn't overstep the map's size from the left or right */
		if (x < 0)
			x = 0;
		else if (x + cameraWidth > map.getMapWidth())
			x = map.getMapWidth() - cameraWidth;
		
		/* Make sure that the camera doesn't overstep the map's size from above or below */
		if (y < 0)
			y = 0;
		else if(y + cameraHeight > map.getMapHeight())
			y = map.getMapHeight() - cameraHeight;
	}
	
}
