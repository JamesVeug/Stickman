package com.wireframe.stickman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Spawner extends GameObject{
	
	public static final int SPAWNERTYPE_RED = 0;
	public static final int SPAWNERTYPE_BLUE = 1;
	public static final int spawnRate = 2;
	
	public float spawnDelay = 0f;
	
	// What this spawner is creating
	private int type;
	private Character spawned = null;
	
	public Spawner(Vector3 point, Vector2 size, int type){
		super(point, size);
		this.type = type;
	}
	
	public void update(){
		
		// Check if we already have a spawned unit
		if( spawned != null ){
			if( !spawned.isAlive() ){
				spawned = null;
			}
			else{
				return;
			}
		}
		
		// Try and spawn a new unit
		if( spawnDelay > spawnRate ){
			spawnDelay = 0f;
			spawn();
		}
		else{
			spawnDelay += Gdx.graphics.getDeltaTime();
		}
	}
	
	public void spawn(){
		if( type == SPAWNERTYPE_RED ){
			spawned = new Enemy(getX(), getY(), getZ(), getWidth(), getHeight());
		}
		else if( type == SPAWNERTYPE_BLUE ){
			spawned = new Friendly(getX(), getY(), getZ(), getWidth(), getHeight());
		}
		
		// Add to the world
		if( spawned != null ){
			World.addObject(spawned);
		}
	}

	@Override
	public void draw(SpriteBatch batch) {
	}

	@Override
	public void dispose() {
		
	}
}
