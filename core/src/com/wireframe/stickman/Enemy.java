package com.wireframe.stickman;

import com.badlogic.gdx.math.Vector3;

public class Enemy extends Character{

	public Enemy(int x, int y, int z) {
		this(new Vector3(x,y,z));
	}

	public Enemy(Vector3 position) {
		super("enemy", position);
	}

	public void update(){
		super.update();
	}
	
	@Override
	public void takeDamage(int damage){
		super.takeDamage(damage);
		System.out.println("Health: " + getHealth());
	}
}
