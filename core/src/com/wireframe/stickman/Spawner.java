package com.wireframe.stickman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
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
		System.out.println("CREATED");
		this.setPosition(point);
		this.setSize(size);
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
			spawned = new Enemy(position.x, position.y, position.z, size.x, size.y);
		}
		else if( type == SPAWNERTYPE_BLUE ){
			spawned = new Friendly(position.x, position.y, position.z, size.x, size.y);
		}
		
		// Add to the world
		if( spawned != null ){
			World.addObject(spawned);
		}
	}

	@Override
	public void draw(SpriteBatch batch) {
		// Don't draw this entity
	}

	@Override
	public Rectangle getBounds() {
		// Don't have bounds for this
		return null;
	}
}
