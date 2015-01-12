package com.wireframe.stickman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class SmallButton extends GameObject implements Interactable{
	public static final int RED = 0;
	public static final int GREEN = 1;
	public static final int BLUE = 2;
	public static final int OFF = 3;
	
	public static final int MAXCOLOURS = 4;
	
	
	private int type;
	private TextureRegion[] frames;
	

	public SmallButton(Vector3 point, Vector2 size, int type){
		super(point, size);		
		this.setType(type);
		
		Texture characterTexture = StickmanResources.getImage("smallbutton.png");
		TextureRegion[][] tmp = TextureRegion.split(characterTexture, characterTexture.getWidth()/MAXCOLOURS, characterTexture.getHeight());
		frames = new TextureRegion[tmp[0].length];
		frames[0] = tmp[0][0];
		frames[1] = tmp[0][1];
		frames[2] = tmp[0][2];
		frames[3] = tmp[0][3];
	}
	
	public void setType(int type){
		if( type >= MAXCOLOURS ){
			throw new RuntimeException("UNKNOWN TYPE!!! " + type);
		}
		this.type = type;
	}
	
	@Override
	public void update(){
		if( type == OFF ) return;
		
		Player player = World.getPlayer();
		if( player.getBounds().overlaps(getBounds()) ){
			interact(player);
		}
	}
		

	@Override
	public void draw(SpriteBatch batch) {
		batch.draw(frames[type], getX(), getY(), getWidth(), getHeight());
	}

	@Override
	public void interact(GameObject triggerer) {
		World.getDoor(type).toggle();		
		setType(OFF);
	}

	@Override
	public void dispose() {
		for(TextureRegion t : frames){
			t.getTexture().dispose();
		}
	}
}
