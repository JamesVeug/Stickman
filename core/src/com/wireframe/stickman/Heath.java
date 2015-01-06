package com.wireframe.stickman;

public interface Heath {
	public void takeDamage(int damage);
	public void heal(int health);
	public boolean isAlive();
	public int getMaxHealth();
	public int getHealth();
}
