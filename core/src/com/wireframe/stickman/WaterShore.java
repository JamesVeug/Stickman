package com.wireframe.stickman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class WaterShore extends Tile{
	private float animationSpeed = 0.25f;
	private Animation animation;
	private TextureRegion[] frames;
	
	private static final int col = 16;
	private static final int row = 1;

	private float stateTime;
	private TextureRegion currentImage;
	
	public WaterShore(int x, int y, int z, int w, int h) {
		this(new Vector3(x,y,z), new Vector2(w,h));
	}
	
	public WaterShore(Vector3 position, Vector2 size) {
		super(position, size, StickmanResources.getImage("watershore.png"));
		TextureRegion[][] tmp = TextureRegion.split(getTexture(), getTexture().getWidth()/col, getTexture().getHeight()/row);
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
	}

	public void update(){
		stateTime+= Gdx.graphics.getDeltaTime();
		if( stateTime /animationSpeed >= col ){
			stateTime = 0;
		}
		
		currentImage = animation.getKeyFrame(stateTime);
	}
	
	@Override
	public void draw(SpriteBatch batch){
		batch.draw(currentImage, getX(), getY(), getWidth(), getHeight());
	}
}
