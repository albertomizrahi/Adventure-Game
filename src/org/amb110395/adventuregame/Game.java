package org.amb110395.adventuregame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Game extends Canvas {
	/* Boolean that determines whether game is running or not */
	private boolean gameRunning;
	/*  */
	private KeyInputHandler inputHandler;
	/* The strategy that allows us to use accelerate page flipping */
	private BufferStrategy strategy;
	/* Map that contains the 2d array of the tiles */
	private Map map;
	/* List of all the existing entities in the world */
	private List<Entity> entities;
	/* Object that represents the main character */
	private Entity mainCharacter;
	
	private Camera camera;
	

	public Game() {
		JFrame container = new JFrame("Adventure Game");
		container.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		JPanel panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(Constants.CAMERA_WIDTH,
				Constants.CAMERA_HEIGHT));
		panel.setLayout(null);

		this.setBounds(0, 0, Constants.CAMERA_WIDTH, Constants.CAMERA_HEIGHT);
		panel.add(this);

		// Tell AWT not to bother repainting our canvas since we're
		// going to do that our self in accelerated mode
		setIgnoreRepaint(true);

		// finally make the window visible
		container.pack();
		container.setResizable(false);
		container.setVisible(true);

		// request the focus so key events come to us
		requestFocus();

		// create the buffering strategy which will allow AWT
		// to manage our accelerated graphics
		createBufferStrategy(2);
		strategy = getBufferStrategy();

		inputHandler = new KeyInputHandler();
		addKeyListener(inputHandler);

		initMap();

		initEntities();

		gameRunning = true;
	}

	public void initMap() {

		int[][] intMap = { 
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 2, 0, 0, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 2, 0, 0, 0, 0, 0, 0, 1, 1, 0, 2, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, 
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 2, 0, 0, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 2, 0, 0, 0, 0, 0, 0, 1, 1, 0, 2, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };
		MapGenerator gen = new MapGenerator(intMap, Constants.TILE_SIZE);
		map = gen.generateMap();
	}

	public void initEntities() {
		entities = new ArrayList<Entity>();

		mainCharacter = new MainCharacter(Constants.MAIN_CHARACTER, Constants.CAMERA_WIDTH/2, Constants.CAMERA_HEIGHT/2, map);
		entities.add(mainCharacter);
		
		mainCharacter.setTileX((int) mainCharacter.getX() / map.getTileSize());
		mainCharacter.setTileY((int) mainCharacter.getY() / map.getTileSize());
		
		camera = new Camera(mainCharacter.getX() - Constants.CAMERA_WIDTH/2.0, 
				mainCharacter.getX() - Constants.CAMERA_HEIGHT/2.0,
				Constants.CAMERA_WIDTH,
				Constants.CAMERA_HEIGHT);
	}

	public void gameLoop() {
		long lastTime = System.currentTimeMillis();
		while (gameRunning) {
			long current = System.currentTimeMillis();
			long elapsed = current - lastTime;

			handleInput();
			update(elapsed);
			render();

			lastTime = current;
		}
	}

	public void handleInput() {

		mainCharacter.setHorizontalVelocity(0);
		mainCharacter.setVerticalVelocity(0);

		if (inputHandler.isLeftPressed()) {
			mainCharacter.setHorizontalVelocity(-MainCharacter.MOVE_SPEED);
			mainCharacter.setDirection(Direction.LEFT);
		} 
		else if (inputHandler.isRightPressed()) {
			mainCharacter.setHorizontalVelocity(MainCharacter.MOVE_SPEED);
			mainCharacter.setDirection(Direction.RIGHT);
		} 
		else if (inputHandler.isUpPressed()) {
			mainCharacter.setVerticalVelocity(-MainCharacter.MOVE_SPEED);
			mainCharacter.setDirection(Direction.UP);
		} 
		else if (inputHandler.isDownPressed()) {
			mainCharacter.setVerticalVelocity(MainCharacter.MOVE_SPEED);
			mainCharacter.setDirection(Direction.DOWN);
		}
		else {
			mainCharacter.setDirection(Direction.STATIONARY);
		}
		

	}

	
	public void update(long delta) {
		
		
		for (Entity entity : entities) {
			
			//entity.move(delta);
			
			double distanceX = (delta * entity.getHorizontalVelocity()) / 1000;
			double distanceY = (delta * entity.getVerticalVelocity()) / 1000;;
			
			if (entity.getDirection().equals(Direction.RIGHT)) {
				//double x = (entity.getX() + entity.getSprite().getWidth());
				double x = entity.getX() + distanceX + entity.getSprite().getWidth();
				double yTopCorner = entity.getY();
				double yBotCorner = (entity.getY() + entity.getSprite().getHeight()-1);
				
				Tile topRight = map.getTileInCoord(x, yTopCorner-1);
				Tile botRight =  map.getTileInCoord(x,yBotCorner-1);
				
				double deltaX = 0;
				if (!topRight.isPassable() || !botRight.isPassable()) {
					 deltaX = x - topRight.getX();
				}
								
				//if (deltaX > 0)
					entity.moveBy(distanceX-deltaX, 0);
			} 
			else if (entity.getDirection().equals(Direction.LEFT)) {
				double x = entity.getX() + distanceX;
				double yTopCorner = entity.getY();
				double yBotCorner = (entity.getY() + entity.getSprite().getHeight()-1);
				
				Tile topLeft = map.getTileInCoord(x, yTopCorner);
				Tile botLeft =  map.getTileInCoord(x, yBotCorner);
				
				double deltaX = 0;
				if (!topLeft.isPassable() || !botLeft.isPassable()) {
					 deltaX = x - (topLeft.getX() + map.getTileSize());
				}
								
				//if (deltaX < 0)
					entity.moveBy(distanceX - deltaX, 0);
			} 
			else if (entity.getDirection().equals(Direction.UP)) {
				//double y = entity.getY();
				double y = entity.getY() + distanceY-1;
				double xLeftCorner =  entity.getX();
				double xRightCorner = (entity.getX() + entity.getSprite().getWidth()-1);
				
				Tile topLeft = map.getTileInCoord(xLeftCorner, y);
				Tile topRight =  map.getTileInCoord(xRightCorner, y);
				
				double deltaY = 0;
				if (!topLeft.isPassable() || !topRight.isPassable()) {
					 deltaY = y - (topLeft.getY() + map.getTileSize());
				}
								
				//if (deltaY < 0)
					entity.moveBy(0,distanceY - deltaY);
			} 
			else if (entity.getDirection().equals(Direction.DOWN)) {
				//double y = (entity.getY() + entity.getSprite().getHeight());
				double y = entity.getY() + distanceY + entity.getSprite().getHeight();
				double xLeftCorner =  entity.getX();
				double xRightCorner = (entity.getX() + entity.getSprite().getWidth()-1);
				
				Tile botLeft = map.getTileInCoord(xLeftCorner, y);
				Tile botRight =  map.getTileInCoord(xRightCorner, y);
				
				double deltaY = 0;
				if (!botLeft.isPassable() || !botRight.isPassable()) {
					 deltaY = y - botLeft.getY();
				}
								
				//if (deltaY > 0)
					entity.moveBy(0,distanceY-deltaY);
			} 
			
			
			

//			// Top left corner
//			double newX = entity.getX() + (entity.getHorizontalVelocity()*delta)/1000;
//			double newY = entity.getY() + (entity.getVerticalVelocity()*delta)/1000;
//			int newTileX = 	(int) newY / map.getTileSize();
//			int newTileY = (int) newX / map.getTileSize();
//			//System.out.println((int) newY / map.getTileSize() + " , " + (int) newX / map.getTileSize());
//			Tile one = map.getTile(newTileX, newTileY);
//			
//			// Top right corner
//			newX = (entity.getX() + entity.getSprite().getWidth()) + (entity.getHorizontalVelocity()*delta)/1000;
//			newY = entity.getY() + (entity.getVerticalVelocity()*delta)/1000;
//			newTileX = 	(int) newY / map.getTileSize();
//			newTileY = (int) newX / map.getTileSize();
//			//System.out.println((int) newY / map.getTileSize() + " , " + (int) newX / map.getTileSize());
//			Tile two = map.getTile(newTileX, newTileY);
//			
//			// Bottom left corner
//			newX = entity.getX() + (entity.getHorizontalVelocity()*delta)/1000;
//			newY = (entity.getY() + entity.getSprite().getHeight()) + (entity.getVerticalVelocity()*delta)/1000;
//			newTileX = 	(int) newY / map.getTileSize();
//			newTileY = (int) newX / map.getTileSize();
//			//System.out.println((int) newY / map.getTileSize() + " , " + (int) newX / map.getTileSize());
//			Tile three = map.getTile(newTileX, newTileY);
//			
//			// Bottom right corner
//			newX = (entity.getX() + entity.getSprite().getWidth()) + (entity.getHorizontalVelocity() * delta)/ 1000;
//			newY = (entity.getY() + entity.getSprite().getHeight()) + (entity.getVerticalVelocity() * delta) / 1000;
//			newTileX = (int) newY / map.getTileSize();
//			newTileY = (int) newX / map.getTileSize();
//			// System.out.println((int) newY / map.getTileSize() + " , " + (int)
//			// newX / map.getTileSize());
//			Tile four = map.getTile(newTileX, newTileY);
//			double deltaX = newX - four.getX();
//			double deltaY = newY - four.getY();
//			
//			if (one.isPassable() && two.isPassable() && three.isPassable() && four.isPassable()) {
			//entity.move(delta);
			/*
			 * (x,y) coordinates must be inverted in order to get the corresponding tile, 
			 * i.e., x coordinates are used to find the Y tile and y coordinates are used to find the X tile.
			 * This is done so that the corresponding tile position directly corresponds to the one in the 2d array
			 * 
			 */
			entity.setTileX((int) entity.getY() / map.getTileSize());
			entity.setTileY((int) entity.getX() / map.getTileSize());
			//}
			
		}
		
		
		/*** Update camera coordinates ***/
		double cameraX = mainCharacter.getX() - Constants.CAMERA_WIDTH/2.0;
		double cameraY = mainCharacter.getY() - Constants.CAMERA_HEIGHT/2.0;
		
		if (cameraX < 0)
			cameraX = 0;
		else if (cameraX + Constants.CAMERA_WIDTH > map.getMapWidth())
			cameraX = map.getMapWidth() - Constants.CAMERA_WIDTH;
		
		if (cameraY < 0)
			cameraY = 0;
		else if(cameraY + Constants.CAMERA_HEIGHT > map.getMapHeight())
			cameraY = map.getMapHeight() - Constants.CAMERA_HEIGHT;
		
		camera.setX(cameraX);
		camera.setY(cameraY);

			
		
	}

	public void render() {
		// Get hold of a graphics context for the accelerated surface and blank
		// it out
		Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, Constants.CAMERA_WIDTH, Constants.CAMERA_HEIGHT);

		map.draw(g, camera.getX(), camera.getY());

		for (Entity entity : entities) {
			entity.draw(g, camera.getX(), camera.getY());
		}

		// We have completed drawing so clear up the graphics and flip the buffer over
		g.dispose();
		strategy.show();

		// Finally pause for a bit. Note: this should run us at about
		// 100 fps but on windows this might vary each loop due to
		// a bad implementation of timer
		try {
			Thread.sleep(10);
		} catch (Exception e) {
		}
	}

	public static void main(String[] args) {
		Game game = new Game();

		game.gameLoop();
	}

}
