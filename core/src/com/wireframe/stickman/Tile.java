package com.wireframe.stickman;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public abstract class Tile extends GameObject {
	private Texture texture;

	public Tile(Vector3 position, Vector2 size, Texture image) {
		super(position, size);
		this.texture = image;
	}
	
	/**
	 * @return the texture
	 */
	public Texture getTexture() {
		return texture;
	}

	/**
	 * @param texture the texture to set
	 */
	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public void update(){
		// Tiles do nothing
	}

	public void draw(SpriteBatch batch){
		batch.draw(texture, getX(), getY(), getWidth(), getHeight());
	}

	@Override
	public void dispose() {
		texture.dispose();
	}
}
