package com.wireframe.stickman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
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
		updateKeyPresses();
	}

	public void updateKeyPresses(){
		if( Gdx.app.getType() != ApplicationType.Desktop )return;
		
		
		// Moving Up
		if( !movingUp() ){
			if( Gdx.input.isKeyPressed(Keys.W) ){
				moveUp();				
			}
		}
		else{
			// Key Release
			if( !Gdx.input.isKeyPressed(Keys.W) ){
				stopMoveingUp();				
			}
		}
		
		// Moving Right
		if( !movingRight() ){
			if( Gdx.input.isKeyPressed(Keys.D) ){
				moveRight();				
			}
		}
		else{
			// Key Release
			if( !Gdx.input.isKeyPressed(Keys.D) ){
				stopMoveingRight();				
			}
		}
		
		// Moving Down
		if( !movingDown() ){
			if( Gdx.input.isKeyPressed(Keys.S) ){
				moveDown();				
			}
		}
		else{
			// Key Release
			if( !Gdx.input.isKeyPressed(Keys.S) ){
				stopMoveingDown();				
			}
		}
		
		// Moving Left
		if( !movingLeft() ){
			if( Gdx.input.isKeyPressed(Keys.A) ){
				moveLeft();				
			}
		}
		else{
			// Key Release
			if( !Gdx.input.isKeyPressed(Keys.A) ){
				stopMoveingLeft();				
			}
		}
		
		// Jump
		if( Gdx.input.isKeyJustPressed(Keys.SPACE) ){
			jump();				
		}
		
		// Attack
		if( Gdx.input.isKeyJustPressed(Keys.CONTROL_LEFT) ){
			attack();				
		}
	}
}
