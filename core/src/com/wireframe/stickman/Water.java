package com.wireframe.stickman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Water extends Tile{
	private Vector2 size;
	
	private Rectangle bounds;

	private float animationSpeed = 0.25f;
	private Animation animation;
	private Texture playerTexture;
	private TextureRegion[] frames;
	
	private static final int col = 1;
	private static final int row = 1;

	private float stateTime;
	private TextureRegion currentImage;
	
	public Water(int x, int y, int z, int w, int h) {
		this(new Vector3(x,y,z), new Vector2(w,h));
	}
	
	public Water(Vector3 position, Vector2 size) {
		this.position = position;
		this.size = size;
		
		playerTexture = new Texture(Gdx.files.internal("water.png"));
		TextureRegion[][] tmp = TextureRegion.split(playerTexture, playerTexture.getWidth()/col, playerTexture.getHeight()/row);
		frames = new TextureRegion[col*row];
		
		int index = 0;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				frames[index++] = tmp[i][j];				
			}
		}

		stateTime = 0f;
		animation = new Animation(animationSpeed, frames);
		currentImage = animation.getKeyFrame(0);
		bounds = new Rectangle(position.x, position.y, currentImage.getRegionWidth(), currentImage.getRegionHeight());
	}

	public void update(){
		this.bounds.set(position.x, position.y, size.x, size.y);
		stateTime+= Gdx.graphics.getDeltaTime();
		if( stateTime /animationSpeed >= col ){
			stateTime = 0;
		}
		
		currentImage = animation.getKeyFrame(stateTime);
	}

	public void draw(SpriteBatch batch){
		batch.draw(currentImage, position.x, position.y, size.x, size.y);
	}

	/**
	 * @return the size
	 */
	public Vector2 getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(Vector2 size) {
		this.size = size;
	}

	/**
	 * @return the bounds
	 */
	public Rectangle getBounds() {
		return bounds;
	}

	/**
	 * @param bounds the bounds to set
	 */
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
}
