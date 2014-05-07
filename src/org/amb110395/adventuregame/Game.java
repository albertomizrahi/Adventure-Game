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
	/* Handles the input from the user and sets the character's movement */
	private KeyInputHandler inputHandler;
	/* The strategy that allows us to use accelerate page flipping */
	private BufferStrategy strategy;
	/* Map that contains the 2d array of the tiles */
	private Map map;
	/* List of all the existing entities in the world */
	private List<Entity> entities;
	private List<Enemy> enemies;
	/* Object that represents the main character */
	private Entity mainCharacter;
	/* Represents the view of the world that the user can see */
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
		enemies = new ArrayList<Enemy>();

		mainCharacter = new MainCharacter(Constants.CAMERA_WIDTH/2, Constants.CAMERA_HEIGHT/2, map);
		entities.add(mainCharacter);

		Enemy enemy = new Enemy(Constants.ENEMY_1, 200, 200, map);
		entities.add(enemy);
		enemies.add(enemy);
		
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
//		else {
//			mainCharacter.setDirection(Direction.STATIONARY);
//		}
		

	}

	
	public void update(long time) {
		
		for (Entity entity : entities) {
			
			entity.move(time);

//			double distanceX = (delta * entity.getHorizontalVelocity()) / 1000;
//			double distanceY = (delta * entity.getVerticalVelocity()) / 1000;;
//			double deltaX = 0;
//			double deltaY = 0;
//			
//			if (entity.getDirection().equals(Direction.RIGHT)) {
//				double x = entity.getX() + distanceX + entity.getSprite().getWidth();
//				double yTopCorner = entity.getY();
//				double yBotCorner = (entity.getY() + entity.getSprite().getHeight()-1);
//				
//				Tile topRight = map.getTileInCoord(x, yTopCorner-1);
//				Tile botRight =  map.getTileInCoord(x,yBotCorner-1);
//				
//				
//				if (!topRight.isPassable() || !botRight.isPassable()) {
//					 deltaX = x - topRight.getX();
//				}
//			} 
//			else if (entity.getDirection().equals(Direction.LEFT)) {
//				double x = entity.getX() + distanceX;
//				double yTopCorner = entity.getY();
//				double yBotCorner = (entity.getY() + entity.getSprite().getHeight()-1);
//				
//				Tile topLeft = map.getTileInCoord(x, yTopCorner);
//				Tile botLeft =  map.getTileInCoord(x, yBotCorner);
//				
//				if (!topLeft.isPassable() || !botLeft.isPassable()) {
//					 deltaX = x - (topLeft.getX() + map.getTileSize());
//				}
//			} 
//			else if (entity.getDirection().equals(Direction.UP)) {
//				double y = entity.getY() + distanceY-1;
//				double xLeftCorner =  entity.getX();
//				double xRightCorner = (entity.getX() + entity.getSprite().getWidth()-1);
//				
//				Tile topLeft = map.getTileInCoord(xLeftCorner, y);
//				Tile topRight =  map.getTileInCoord(xRightCorner, y);
//				
//				if (!topLeft.isPassable() || !topRight.isPassable()) {
//					 deltaY = y - (topLeft.getY() + map.getTileSize());
//				}
//			} 
//			else if (entity.getDirection().equals(Direction.DOWN)) {
//				double y = entity.getY() + distanceY + entity.getSprite().getHeight();
//				double xLeftCorner =  entity.getX();
//				double xRightCorner = (entity.getX() + entity.getSprite().getWidth()-1);
//				
//				Tile botLeft = map.getTileInCoord(xLeftCorner, y);
//				Tile botRight =  map.getTileInCoord(xRightCorner, y);
//				
//				if (!botLeft.isPassable() || !botRight.isPassable()) {
//					 deltaY = y - botLeft.getY();
//				}
//			} 
//			
//			double dx = distanceX - deltaX;
//			double dy = distanceY - deltaY;
//			
//			entity.moveBy(dx,dy);
			
		}


		for (Entity enemy : enemies) {
			if (mainCharacter.hasCollidedWith(enemy)) {
				mainCharacter.collidedWith(enemy);
				enemy.collidedWith(mainCharacter);
			}
		}
		
		camera.update(mainCharacter, map);

		System.out.println(mainCharacter.getStats().getHitpoints());
			
		
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
