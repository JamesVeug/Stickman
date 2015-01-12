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
	

	private Rectangle bounds;
	private Vector3 position;
	private Vector2 size;
	public abstract void dispose();
	public abstract void draw(SpriteBatch batch);
	public abstract void update();
	
	public GameObject(Vector3 position, Vector2 size){
		this.position = position.cpy();
		this.size = size.cpy();
		this.bounds = new Rectangle(position.x, position.y, size.x, size.y);		
	}

	public Vector3 getPosition() {
		return position.cpy();
	}

	public void setPosition(Vector3 position2) {
		this.position = position2.cpy();
	}
	
	public Vector2 getSize() {
		return size.cpy();
	}

	public void setSize(Vector2 size) {
		this.size = size.cpy();
	}

	public void setSize(float x, float y) {
		this.size = new Vector2(x,y);
	}

	public void moveBy(float x, float y) {
		position.x += x;
		position.y += y;
		bounds.setX(bounds.getX() + x);
		bounds.setY(bounds.getY() + y);
	}
	
	public void moveTo(float x, float y) {
		position.x = x;
		position.y = y;
		bounds.setX(x);
		bounds.setY(y);
	}
	
	public Rectangle getBounds(){
		return new Rectangle(bounds);
	}
	
	@Override
	public int compareTo(GameObject other){
		return (int) Math.signum(other.getZ()- getZ());
	}

	public float getX(){
		return position.x;
	}
	
	public float getY(){
		return position.y;
	}

	public float getZ() {
		return position.z;
	}
	
	public float getWidth(){
		return size.x;
	}
	
	public float getHeight(){
		return size.y;
	}
}
