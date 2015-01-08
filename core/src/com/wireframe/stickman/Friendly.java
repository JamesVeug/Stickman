package com.wireframe.stickman;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Friendly extends AICharacter{
	private Player player = null;

	public Friendly(float x, float y, float z, float x2, float y2) {
		this(new Vector3(x,y,z), new Vector2(x2,y2));
	}

	public Friendly(Vector3 position, Vector2 size) {
		super("friendly", position, size);
	}

	public void update(){
		
		// Move Character
		super.update();
		
		// Make sure we are alive
		if( !isAlive() ) return;
		
		// Only follow player if we don't have a target
		if( target != null ){
			return;
		}
		
		
		// Move towards player?
		if( player != null ){
			if( canSee(player) ){
				if( !playerIsNear(player) ) {
					followPlayer();
					return;
				}
				else if( moving() ) {
					stopMoving();
					return;
				}
			}
			else{
				player = null;
			}
		}
		else{
			// Where is the player?
			lookForPlayer();
			return;
		}
	}	

	private void lookForPlayer() {
		Player worldPlayer = World.getPlayer();
		
		// Check if the player is on the same Y
		if( (int)worldPlayer.getPosition().y != (int)getPosition().y ){
			return;
		}
		
		// Check if he's within 200 pixels on X
		if((int) worldPlayer.getPosition().x < (int)getPosition().x-200 &&
				(int)worldPlayer.getPosition().x > (int)getPosition().x+200){
			return;
		}
		
		
		// Can see the player
		player = worldPlayer;
	}

	private void followPlayer(){
		if( playerIsToTheLeft(player) ){
			if( movingRight() ) stopMoveingRight();
			if( !movingLeft() ) moveLeft();
		}
		else if( playerIsToTheRight(player) ){
			if( !movingRight() ) moveRight();
			if( movingLeft() ) stopMoveingLeft();
		}
	}

	private boolean playerIsNear(Player player){
		return getPosition().x + getWidth() >= player.getPosition().x &&
				getPosition().x <= player.getPosition().x + player.getWidth();
	}
	
	private boolean playerIsToTheRight(Player player){
		return player.getPosition().x > this.getPosition().x;
	}
	
	private boolean playerIsToTheLeft(Player player){
		return player.getPosition().x < this.getPosition().x;
	}
}
