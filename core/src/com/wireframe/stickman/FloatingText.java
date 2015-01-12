package com.wireframe.stickman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class FloatingText extends GameObject{
	public static final int LIFE_LENGTH = 2;
	private static FileHandle fontFNT;
	private static FileHandle fontPNG;

	private Color color;
	private BitmapFont font;
	private String message;
	private float speed = -1f;
	private float lifetime;
	
	

	public FloatingText(String message, float x, float y, Color color) {
		super(new Vector3(x,y,GameObject.Z_FLOATINGTEXTS), new Vector2(0,0));
		this.message = message;
		this.color = color;
		
		// Statically make the font
		if( fontFNT == null ){
			fontFNT = Gdx.files.internal("font.fnt");
			fontPNG = Gdx.files.internal("font.png");
		}
		
		// Font object
		font = new BitmapFont(fontFNT,fontPNG,true);
		
		// Assign width/height
		TextBounds bounds = font.getBounds(message);
		setSize(bounds.width, bounds.height);
	}

	public void update(){
		moveBy(0, speed);
		
		// LIFE
		lifetime += Gdx.graphics.getDeltaTime();
		if( lifetime > LIFE_LENGTH ){
			World.removeFloatingText(this);
		}
	}

	@Override
	public void draw(SpriteBatch batch) {
		int scaler = (int) (Math.min(LIFE_LENGTH, lifetime) / LIFE_LENGTH*255f);		
		font.setColor(color.r, color.g, color.b, scaler);
		font.draw(batch, message, getPosition().x, getPosition().y);
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}
	
	public float getHeight(){
		return font.getXHeight();
	}

	public void setColor(Color color) {
		font.setColor(color);
	}

	@Override
	public void dispose() {
		font.dispose();
	}
}
