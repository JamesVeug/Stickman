package com.wireframe.stickman;

import com.badlogic.gdx.math.Rectangle;

public interface Interactable {
	public void interact(GameObject triggerer);
	public Rectangle getBounds();
}
