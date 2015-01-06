package com.wireframe.stickman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class FloatingText extends GameObject{
	private String message;
	private float speed = -10f;
	

	public FloatingText(String message, float x, float y) {
		this.message = message;
		setPosition(new Vector3(x,y,GameObject.Z_FLOATINGTEXTS));
	}

	public void update(){
		getPosition().y = getPosition().y += speed;
	}

	@Override
	public void draw(SpriteBatch batch) {
		BitmapFont font = new BitmapFont(Gdx.files.internal("font.fnt"),Gdx.files.internal("font.png"),true);
		font.setColor(Color.RED);
		font.draw(batch, message, getPosition().x, getPosition().y);
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}
}
