package com.wireframe.stickman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class AICharacter extends Character{
	protected Character target = null;
	
	protected float attackDelay = 0.5f; // Time between attacks
	protected float lastAttack = 0f;

	public AICharacter(String type, float x, float y, float z, float w, float h) {
		this(type, new Vector3(x,y,z), new Vector2(w,h));
	}

	public AICharacter(String type, Vector3 position, Vector2 size) {
		super(type, position, size);
	}

	public void update(){
		
		// Move Character
		super.update();

		// Make sure we are alive
		if( !isAlive() ) return;
		
		// Check delays
		if( lastAttack != 0f ){
			lastAttack += Gdx.graphics.getDeltaTime();
			if( lastAttack > attackDelay ){
				lastAttack = 0f;
			}
		}
		
		// Have a target?
		if( target == null ){
			searchForTarget();			
		}
		else if( !target.isAlive() ){
			target = null;
		}
		else if( canSee(target) ){
			if( canAttackTarget(target) ){
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
	}
	
	/**
	 * Returns true if we had to change the direction that WE are facing
	 * @return
	 */
	protected boolean faceTarget(){
		
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
	
	protected void moveTowardsTarget() {
		
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

	protected void attackTarget() {
		// Are we already attacking?
		if( isAttacking() ) return;
		
		// Attack Delay finished?
		if( lastAttack != 0f ) return;
			
		
		lastAttack += Gdx.graphics.getDeltaTime();
		attack();
	}

	protected void searchForTarget() {
		List<Character> enemies = null;
		if( getTeamNumber() == Character.TEAM_BLUE ) enemies = World.getEnemies();
		if( getTeamNumber() == Character.TEAM_RED ) enemies = World.getFriendlies();
		
		// Get all enemies in sight
		ArrayList<Character> nearbyEnemies = new ArrayList<Character>();
		for(int i = 0; i < enemies.size(); i++){
			Character enemy = enemies.get(i);
			if( canSee(enemy) && enemy.getTeamNumber() != this.getTeamNumber() ){				
				nearbyEnemies.add(enemy);
			}
		}
		
		// Find closest enemy
		if( !nearbyEnemies.isEmpty() ){
			Collections.sort(nearbyEnemies, new targetSearchPrioritiser(this));
			
			/*if ( this instanceof Enemy ){
				System.out.println("ENEMIES");
				for( Character c : nearbyEnemies ){
					System.out.println(c.getClass().getSimpleName() + " " + c.getPosition());
				}
			}*/
			target = nearbyEnemies.get(0);
		}
	}
	
	protected boolean canSee(Character character){
		
		// Not alive? Who cares
		if( !character.isAlive() ){
			return false;
		}
		
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
	
	private class targetSearchPrioritiser implements Comparator<Character>{
		private Character searcher;

		public targetSearchPrioritiser(AICharacter aiCharacter) {
			this.searcher = aiCharacter;
		}

		@Override
		public int compare(Character o1, Character o2) {
			
			// Distance between them
			float distance1 = o1.getPosition().dst(searcher.position);
			float distance2 = o2.getPosition().dst(searcher.position);
			return (int) Math.signum(distance1 - distance2);
		}
		
	}
}
