package org.amb110395.adventuregame;

public class MapGenerator {
	
	private int [][] intMap;
	private int tileSize;
	
	public MapGenerator(int [][] intMap, int tileSize) {
		this.intMap = intMap;
		this.tileSize = tileSize;
	}
	
	public Map generateMap() {
		int verticalTiles = intMap.length;
		int horizontalTiles = intMap[0].length;
		/* In order to calculate the tile size, we assume that:
		 *  1) # of vertical tiles = # of horizontal tiles
		 *  2) The number of vertical tiles divides the window width evenly
		 */
		
		Map map = new Map(tileSize, verticalTiles, horizontalTiles);
		
		for (int x = 0; x < verticalTiles; x++) {
			for (int y = 0; y < horizontalTiles; y++) {
				int value = intMap[x][y];
				String imgRef = "";
				boolean isPassable = true;
				switch (value) {
				case 0:
					imgRef = Constants.TERRAIN_PLAIN;
					break;
				case 1:
					imgRef = Constants.TERRAIN_GRASS;
					break;
				case 2:
					imgRef = Constants.TERRAIN_WATER;
					isPassable = false;
					break;
				default:
					imgRef = "sprites/ship.gif";
					break;
				}
				
				map.setTile(imgRef, x, y, isPassable);
			}
		}
		
		return map;
	}

}
