package com.wireframe.stickman;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Brick extends Tile{
	
	public Brick(int x, int y, int z, int w, int h) {
		this(new Vector3(x,y,z), new Vector2(w,h));
	}
	
	public Brick(Vector3 position, Vector2 size) {
		super(position, size, StickmanResources.getImage("brick.png"));
	}
}
