package com.wireframe.stickman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Ladder extends GameObject{	
	private Texture texture;
	private TextureRegion textureRegion;

	
	public Ladder(int x, int y, int z, int h) {
		this(new Vector3(x,y,z), h);
	}
	
	public Ladder(Vector3 position, int height) {
		super(position, new Vector2(50,height) );
		
		texture = StickmanResources.getImage("ladder.png");
		
		textureRegion = new TextureRegion(texture);
		textureRegion.setRegion(0,0,texture.getWidth(),height);
	}

	public void update(){
		
	}

	public void draw(SpriteBatch batch){
		batch.draw(textureRegion, getX(), getY(), getWidth(), getHeight());
	}

	@Override
	public void dispose() {
		texture.dispose();
	}
}
