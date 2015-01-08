package com.wireframe.stickman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class SmallButton extends GameObject implements Interactable{
	public static final int OFF = 0;
	public static final int ON = 1;
	
	public int state;
	private TextureRegion[] frames;
	
	private final Rectangle bounds;

	public SmallButton(Vector3 point, Vector2 size, int state){
		setPosition(point);
		setSize(size);
		bounds = new Rectangle(point.x, point.y, size.x, size.y);
		
		this.state = state;
		
		Texture characterTexture = new Texture(Gdx.files.internal("smallbutton.png"));
		TextureRegion[][] tmp = TextureRegion.split(characterTexture, characterTexture.getWidth()/2, characterTexture.getHeight());
		frames = new TextureRegion[2];
		frames[0] = tmp[0][0];
		frames[1] = tmp[0][1];
		System.out.println(frames[0]);
		System.out.println(frames[1]);
	}
	
	@Override
	public void update(){
		
	}
		

	@Override
	public void draw(SpriteBatch batch) {
		TextureRegion image = state == ON ? frames[1] : frames[0];
		batch.draw(image, position.x, position.y, size.x, size.y);
	}

	@Override
	public Rectangle getBounds() {
		return bounds;
	}

	@Override
	public void interact(GameObject triggerer) {
		// Flip the state
		state = state == ON ? OFF : ON;
	}
}
