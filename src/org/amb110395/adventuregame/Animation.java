package org.amb110395.adventuregame;

public class Animation {

	/** The animation frames */
	private Sprite[] frames;
	/** The time since the last frame change took place */
	private long lastFrameChange;
	/**
	 * The frame duration in milliseconds, i.e. how long any given frame of
	 * animation lasts
	 */
	private long frameDuration;
	/** The current frame of animation being displayed */
	private int frameNumber;

	private Sprite currentSprite;

	public Animation(String[] refs, long frameDuration) {
		frames = new Sprite[refs.length];

		for (int i = 0; i < frames.length; i++) {
			frames[i] = SpriteStore.get().getSprite(refs[i]);
		}

		currentSprite = frames[0];
		
		this.frameDuration = frameDuration;
	}
	
	public Animation(Sprite [] sprites, long frameDuration) {
		frames = sprites;
		
		currentSprite = frames[0];
		
		this.frameDuration = frameDuration;
	}

	public void update(long time, double distance) {
		
		if (distance == 0) {
			currentSprite = frames[1];
			return;
		}
		
		// since the move tells us how much time has passed
		// by we can use it to drive the animation, however
		// its the not the prettiest solution
		lastFrameChange += time;

		// if we need to change the frame, update the frame number
		// and flip over the sprite in use
		if (lastFrameChange > frameDuration) {
			// reset our frame change time counter
			lastFrameChange = 0;

			// update the frame
			frameNumber++;
			if (frameNumber >= frames.length) {
				frameNumber = 0;
			}

			currentSprite = frames[frameNumber];
		}
	}
	
	public Sprite getCurrentSprite() {
		return currentSprite;
	}

}
