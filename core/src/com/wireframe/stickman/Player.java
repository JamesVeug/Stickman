package com.wireframe.stickman;

import java.util.List;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Player extends Character{
	private boolean splashDamage = true;

	public Player(int x, int y, int z, int w, int h) {
		this(new Vector3(x,y,z), new Vector2(w,h));
	}

	public Player(Vector3 position, Vector2 size) {
		super("player", position, size);
		setMaxHealth(10000000);
		setHealth(10000000);
		
		setMaxAttackDamage(100);
		setBaseAttackDamage(25);
	}

	public void update(){
		super.update();
		updateKeyPresses();
	}
	
	@Override
	public void performAttack(){
		super.performAttack();
		
		List<Character> enemies = World.getEnemies();
		for(int i = 0; i < enemies.size(); i++){
			Character target = enemies.get(i);
			if( canAttackTarget(target) ){
				
				target.takeDamage(random.nextInt(getMaxAttackDamage() - getBaseAttackDamage()) + getBaseAttackDamage(), this);
				if( !splashDamage ){
					break;
				}
			}
		}
		
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
		
		// Interact
		if( Gdx.input.isKeyJustPressed(Keys.ENTER) ){
			System.out.println("ATTEMPING");
			attemptInteraction();				
		}
	}
	
	public void attemptInteraction(){
		List<Interactable> inters = World.getInteractables();
		for( Interactable i : inters ){
			if( i.getBounds().overlaps(getBounds()) ){
				System.out.println("INTERACTING");
				i.interact(this);
				break;
			}
		}
	}
}
