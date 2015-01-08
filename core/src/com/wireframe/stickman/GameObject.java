package com.wireframe.stickman;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public abstract class GameObject implements Comparable<GameObject>{
	
	public static final int Z_BACKGROUND = 0;
	public static final int Z_BACKGROUNDOBJECT = 1;
	public static final int Z_OBJECTS = 3;
	public static final int Z_FLOATINGTEXTS = 4;
	public static final int Z_FOREGROUND = 5;
	

	protected Vector3 position;
	protected Vector2 size;
	public abstract void draw(SpriteBatch batch);
	public abstract void update();
	public abstract Rectangle getBounds();

	public Vector3 getPosition() {
		return position;
	}

	public void setPosition(Vector3 position2) {
		this.position = position2;
	}
	
	public Vector2 getSize() {
		return size;
	}

	public void setSize(Vector2 size) {
		this.size = size;
	}
	
	@Override
	public int compareTo(GameObject other){
		return (int) Math.signum(position.z - other.getPosition().z);
	}

}
