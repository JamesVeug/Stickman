package com.wireframe.stickman;

import java.util.List;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Player extends Character{
	private boolean splashDamage = true;
	public static final int PLAYER_ATTACKDAMAGE_MAX = 16;
	public static final int PLAYER_ATTACKDAMAGE_BASE = 1;
	public static final int PLAYER_CRITICALCHANCE = 25; // 5/100
	public static final int PLAYER_CRITICALINCREASE = 2; // Amount to increase by when critical hit is applied
	public static final float PLAYER_ATTACKSPEED = 0.1f;

	public Player(int x, int y, int z, int w, int h) {
		this(new Vector3(x,y,z), new Vector2(w,h));
		
		 
	}

	public Player(Vector3 position, Vector2 size) {
		super("player", position, size);
		setMaxHealth(10000000);
		setHealth(10000000);
		
		setMaxAttackDamage(100);
		setBaseAttackDamage(25);
		
		setAttackSpeed(PLAYER_ATTACKSPEED);
		setCriticalIncrease(PLAYER_CRITICALINCREASE);
		setCriticalChance(PLAYER_CRITICALCHANCE);
		setMaxAttackDamage(PLAYER_ATTACKDAMAGE_MAX);
		setBaseAttackDamage(PLAYER_ATTACKDAMAGE_BASE);
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
				int damage = random.nextInt(getMaxAttackDamage() - getBaseAttackDamage()) + getBaseAttackDamage();
				boolean criticalHit = random.nextInt(100) <= getCriticalChance(); 
				damage *= criticalHit ? getCriticalIncrease() : 1;		
				target.takeDamage(damage, criticalHit, this);
				Gdx.input.vibrate(50);
				if( !splashDamage ){
					break;
				}
			}
		}
		
	}
	
	@Override
	public void takeDamage(int damage, boolean isACriticalHit, Character attacker){
		super.takeDamage(damage, isACriticalHit, attacker);
		Gdx.input.vibrate(50);
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
				Gdx.input.vibrate(100);
				break;
			}
		}
	}
}
