package com.wireframe.stickman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class FloatingText extends GameObject{
	public static final int LIFE_LENGTH = 2;
	private static FileHandle fontFNT;
	private static FileHandle fontPNG;

	private BitmapFont font;
	private String message;
	private float speed = -1f;
	private float lifetime;
	
	

	public FloatingText(String message, float x, float y) {
		this.message = message;
		setPosition(new Vector3(x,y,GameObject.Z_FLOATINGTEXTS));
		
		// Statically make the font
		if( fontFNT == null ){
			fontFNT = Gdx.files.internal("font.fnt");
			fontPNG = Gdx.files.internal("font.png");
		}
		
		// Font object
		font = new BitmapFont(fontFNT,fontPNG,true);
	}

	public void update(){
		getPosition().y = getPosition().y += speed;
		
		// LIFE
		lifetime += Gdx.graphics.getDeltaTime();
		if( lifetime > LIFE_LENGTH ){
			World.removeFloatingText(this);
		}
	}

	@Override
	public void draw(SpriteBatch batch) {
		int scaler = (int) (Math.min(LIFE_LENGTH, lifetime) / LIFE_LENGTH*255f);		
		font.setColor(1, 0, 0, scaler);
		font.draw(batch, message, getPosition().x, getPosition().y);
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}
	
	public float getHeight(){
		return font.getXHeight();
	}
}
