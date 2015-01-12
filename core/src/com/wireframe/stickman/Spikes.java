package com.wireframe.stickman;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Spikes extends Tile{
	
	public Spikes(int x, int y, int z, int w, int h) {
		this(new Vector3(x,y,z), new Vector2(w,h));
	}
	
	public Spikes(Vector3 position, Vector2 size) {
		super(position, size, StickmanResources.getImage("spikes.png"));
	}
}
