package org.amb110395.adventuregame;

import java.awt.Graphics;

public class Map {
	private Tile [][] map;
	private int tileSize;
	private int mapWidth;
	private int mapHeight;
	
	public Map(int tileSize, int verticalTiles, int horizontalTiles) {
		map = new Tile[verticalTiles][horizontalTiles];
		this.tileSize = tileSize;
		this.mapWidth = tileSize * horizontalTiles;
		this.mapHeight = tileSize * verticalTiles;
		
		System.out.println(mapWidth);
	}
	
	public Tile getTile(int x, int y) {
		return map[x][y];
	}
	public void setTile(String terrainRef, int x, int y, boolean isPassable) {
		map[x][y] = new Tile(terrainRef, y*tileSize, x*tileSize, isPassable);
	}
	
	/**
	 * Calculates the tile that contains the point (cordX,cordY).
	 * Note that (x,y) coordinates must be inverted in order to get the corresponding tile, 
	 * i.e., x coordinates are used to find the Y tile and y coordinates are used to find the X tile.
	 * This is done so that the corresponding tile position directly corresponds to the one in the 2d array
	 * 
	 * @param cordX
	 * @param cordY
	 * @return Corresponding tile
	 */
	public Tile getTileInCoord(double cordX, double cordY) {
		int tileX = (int) cordY / tileSize;
		int tileY = (int) cordX / tileSize;
		
		if (tileX >= map.length)
			tileX = map.length-1;
		else if (tileX < 0)
			tileX = 0;
		
		if (tileY >= map[0].length)
			tileY = map[0].length-1;
		else if (tileY < 0)
			tileY = 0;
		
		return map[tileX][tileY];
	}
	
	public int getTileSize() {
		return tileSize;
	}
	
	public void draw(Graphics g, double scrollX, double scrollY) {
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[0].length; y++) {
				Tile tile = map[x][y];
				tile.getTerrain().draw(g, tile.getX() - (int)scrollX, tile.getY() - (int)scrollY);
			}
		}
	}
	
	public int getMapWidth() {
		return mapWidth;
	}
	public int getMapHeight() {
		return mapHeight;
	}
}
