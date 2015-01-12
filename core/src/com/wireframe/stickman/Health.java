package com.wireframe.stickman;

public interface Health {
	public void takeDamage(int damage, boolean isACriticalHit, Character attacker);
	public void heal(int health);
	public boolean isAlive();
	public void setMaxHealth(int max);
	public void setHealth(int max);
	public int getMaxHealth();
	public int getHealth();
}
