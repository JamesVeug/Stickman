package com.wireframe.stickman;

import com.badlogic.gdx.math.Vector3;

public class Player extends Character{

	public Player(int x, int y, int z) {
		this(new Vector3(x,y,z));
	}

	public Player(Vector3 position) {
		super("player", position);
	}

	public void update(){
		super.update();
	}

	
}
