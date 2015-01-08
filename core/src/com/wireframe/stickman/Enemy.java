package com.wireframe.stickman;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Enemy extends AICharacter{

	public Enemy(float x, float y, float z, float w, float h) {
		this(new Vector3(x,y,z), new Vector2(w,h));
	}

	public Enemy(Vector3 position, Vector2 size) {
		super("enemy", position, size);
	}

	public void update(){
		super.update();
	}
}
