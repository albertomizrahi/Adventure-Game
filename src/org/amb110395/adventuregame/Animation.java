package org.amb110395.adventuregame;

public class Animation {

	/* The animation frames */
	private Sprite[] frames;
	/* The time since the last frame change took place */
	private long lastFrameChange;
	/* The frame duration in milliseconds, i.e. how long any given frame of animation lasts */
	private long frameDuration;
	/* The current frame of animation being displayed */
	private int frameNumber;
	
	/**
	 * Accepts a string array containing the references to each of the frames for this animation
	 * @param refs	References to the frames of the animation
	 * @param frameDuration	 The amount of time each frame lasts
	 */
	public Animation(String[] refs, long frameDuration) {
		frames = new Sprite[refs.length];

		for (int i = 0; i < frames.length; i++) {
			frames[i] = SpriteStore.get().getSprite(refs[i]);
		}
		
		this.frameDuration = frameDuration;
	}
	
	/**
	 * Accepts a Sprite array containing all of the frames for this animation
	 * @param refs	References to the frames of the animation
	 * @param frameDuration	 The amount of time each frame lasts
	 */
	public Animation(Sprite [] sprites, long frameDuration) {
		frames = sprites;
				
		this.frameDuration = frameDuration;
	}

	public void update(long time, double distance) {
		
		/* 
		 * If the distance that the player will travel is less than 1 pixel (e.g. he is colliding with an object) we
		 * set the frameNumber to 1, which should correspond to the sprite in a non-moving position
		 */
		if (Math.abs(distance) < 1) {
			frameNumber = 1;
			return;
		}
		
		// Keep track of the total time that has elapsed from the last time the frame changed
		lastFrameChange += time;

		// If the total time elapsed has exceeded the frame duration, the animation is set to the next frame
		if (lastFrameChange > frameDuration) {
			
			lastFrameChange = 0;
			frameNumber++;
			
			// If the last frame of the animation is reached, go back to the first
			if (frameNumber >= frames.length) {
				frameNumber = 0;
			}
		}
	}
	
	/**
	 * @return The current sprite that the animation is on.
	 */
	public Sprite getCurrentSprite() {
		return frames[frameNumber];
	}

}
