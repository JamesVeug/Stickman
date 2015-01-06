package com.wireframe.stickman;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Friendly extends Character{
	private Player player = null;
	private Character target = null;
	
	private float attackDelay = 0.5f; // Time between attacks
	private float lastAttack = 0f;

	public Friendly(int x, int y, int z) {
		this(new Vector3(x,y,z));
	}

	public Friendly(Vector3 position) {
		super("friendly", position);
	}

	public void update(){
		super.update();
		
		// Have a target?
		if( target == null ){
			searchForTarget();			
		}
		else if( canSee(target) ){
			if( canAttackTarget() ){
				stopMoving();
				setTargetToAttack(target);
				attackTarget();				
				return;
			}
			else{
				setTargetToAttack(null);
				moveTowardsTarget();
				return;
			}
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
		
		
		// Check delays
		if( lastAttack != 0f ){
			lastAttack += Gdx.graphics.getDeltaTime();
			if( lastAttack > attackDelay ){
				lastAttack = 0f;
			}
		}
	}
	
	/**
	 * Returns true if we had to change the direction that WE are facing
	 * @return
	 */
	private boolean faceTarget(){
		
		// To the right of us
		if( target.getPosition().x > getPosition().x && !facingRight() ){
			flipFacingDirection();
			return true;
		}
		
		// To the left of us
		if( target.getPosition().x < getPosition().x && facingRight() ){
			flipFacingDirection();
			return true;
		}
		
		
		return false;
	}
	
	private void moveTowardsTarget() {
		
		// Face the direction of the target
		if( faceTarget() ){
			return; 
		}
		
		// Move towards them
		if( target.getPosition().x > getPosition().x ){
			moveRight();
		}
		else if( target.getPosition().x < getPosition().x ){
			moveLeft();
		}
	}

	private boolean canAttackTarget() {
		
		// Must be on the same Y
		if( Math.abs((int)target.getPosition().y - (int)getPosition().y) > 10){
			return false;
		}
		
		// Must be within attacking range
		if( (int)target.getPosition().x < (int)getPosition().x ){
			// Enemy to the left
			
			// Not facing the enemy that is on the left
			if( facingRight() ){
				System.out.println("NOT Facing correct direction");
				return false; 
			}
			
			// Within attacking range
			if( Vector2.len(target.getPosition().x - getPosition().x, 1) > getAttackRange() ){
				System.out.println(Vector2.len(target.getPosition().x - getPosition().x, 1));
				return false;
			}
		}
		else{
			// Enemy to the right
			
			// Not facing the enemy that is on the right
			if( !facingRight() ){
				System.out.println("NOT Facing correct direction");
				return false; 
			}
			
			// Within attacking range
			if( (int)target.getPosition().x > (int)(getPosition().x+getAttackRange()) ){
				System.out.println(Vector2.len(target.getPosition().x - getPosition().x, 1));
				return false;
			}
		}
		
		return true;
	}

	private void attackTarget() {
		// Are we already attacking?
		if( isAttacking() ) return;
		
		// Attack Delay finished?
		if( lastAttack != 0f ) return;
			
		
		lastAttack += Gdx.graphics.getDeltaTime();
		attack();
	}

	private void searchForTarget() {
		List<Character> enemies = World.getEnemies();
		for(int i = 0; i < enemies.size(); i++){
			Character enemy = enemies.get(i);
			if( canSee(enemy) ){
				target = enemy;
				break;
			}
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
	
	private boolean canSee(Character character){
		// Check if the character is on the same Y
		if((int) character.getPosition().y < (int)getPosition().y-100 ||
				(int)character.getPosition().y > (int)getPosition().y+100){
			
			// Can't see target anymore
			target = null;
			return false;
		}
		
		// Check if he's within 200 pixels on X
		if((int) character.getPosition().x < (int)getPosition().x-200 ||
				(int)character.getPosition().x > (int)getPosition().x+200){
			
			// Can't see target anymore
			target = null;
						
			return false;
		}
		
		
		// Can see him
		return true;
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
