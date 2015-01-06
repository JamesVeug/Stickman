package com.wireframe.stickman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Brick extends Tile{
	private Vector2 size;
	
	private Texture texture;
	private Rectangle bounds;

	
	public Brick(int x, int y, int z, int w, int h) {
		this(new Vector3(x,y,z), new Vector2(w,h));
	}
	
	public Brick(Vector3 position, Vector2 size) {
		this.position = position;
		this.size = size;
		this.bounds = new Rectangle(position.x, position.y, size.x, size.y);
		
		texture = new Texture(Gdx.files.internal("brick.png"));
	}

	public void update(){
		this.bounds.set(position.x, position.y, size.x, size.y);
		
	}

	public void draw(SpriteBatch batch){
		batch.draw(texture, position.x, position.y, size.x, size.y);
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
