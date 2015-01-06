package com.wireframe.stickman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Ladder extends GameObject{
	private Vector2 size;
	
	private Texture texture;
	private TextureRegion textureRegion;
	private Rectangle bounds;

	
	public Ladder(int x, int y, int z, int h) {
		this(new Vector3(x,y,z), h);
	}
	
	public Ladder(Vector3 position, int height) {
		this.position = position;
		texture = new Texture(Gdx.files.internal("ladder.png"));
		
		this.size = new Vector2(texture.getWidth(), height);
		this.bounds = new Rectangle(position.x, position.y + size.y, size.x, size.y);
		
		textureRegion = new TextureRegion(texture);
		textureRegion.setRegion(0,0,texture.getWidth(),height);
	}

	public void update(){
		this.bounds.set(position.x, position.y, size.x, size.y);
		
	}

	public void draw(SpriteBatch batch){
		batch.draw(textureRegion, position.x, position.y, size.x, size.y);
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
