package org.amb110395.adventuregame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInputHandler extends KeyAdapter{
	private int keyPressed;
	private boolean leftPressed;
	private boolean rightPressed;
	private boolean downPressed;
	private boolean upPressed;
	private boolean spacebarPressed;
	
	public void keyPressed(KeyEvent e) {
		keyPressed = e.getKeyCode();
		
		switch (keyPressed) {
		case KeyEvent.VK_LEFT:
			leftPressed = true;
			break;
		case KeyEvent.VK_RIGHT:
			rightPressed = true;
			break;
		case KeyEvent.VK_UP:
			upPressed = true;
			break;
		case KeyEvent.VK_DOWN:
			downPressed = true;
			break;
		case KeyEvent.VK_SPACE:
			spacebarPressed = true;
			break;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		keyPressed = e.getKeyCode();
		
		switch (keyPressed) {
		case KeyEvent.VK_LEFT:
			leftPressed = false;
			break;
		case KeyEvent.VK_RIGHT:
			rightPressed = false;
			break;
		case KeyEvent.VK_UP:
			upPressed = false;
			break;
		case KeyEvent.VK_DOWN:
			downPressed = false;
			break;
		case KeyEvent.VK_SPACE:
			spacebarPressed = false;
			break;
		}
	}
	
	public boolean isLeftPressed() {
		return leftPressed;
	}
	public boolean isRightPressed() {
		return rightPressed;
	}
	public boolean isUpPressed() {
		return upPressed;
	}
	public boolean isDownPressed() {
		return downPressed;
	}
	public boolean isSpacebarPressed() {
		return spacebarPressed;
	}
	

}
