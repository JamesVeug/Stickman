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

public abstract class Character extends GameObject implements Heath{	
	private static final int col = 3;
	private static final int row = 8;
	private static final int CHARACTER_MAX_HEALTH = 100;
	
	private Animation animation;
	private Texture characterTexture;
	private TextureRegion[] frames;

	private float stateTime;
	private TextureRegion currentImage;

	private Rectangle bounds;
	
	private float gravityStrength = 0.20f;
	private float animationSpeed = 0.125f;
	private float walkSpeed = 3;
	private float climbSpeed = 3;
	private float jumpSpeed = 6;
	
	private Vector2 velocity = new Vector2(0,0);
	private Ladder ladder;
	
	private boolean movingUp = false;
	private boolean movingLeft = false;
	private boolean movingRight = false;
	private boolean movingDown = false;
	private boolean attacking = false;
	private boolean facingRight = true;
	
	private Character targetToAttack;
	private int attackDamage = 10;
	private float lastAttack = 0;
	private float attackSpeed = 0.15f;
	private float attackRange = 30;
	private int health = CHARACTER_MAX_HEALTH;

	public Character(String type, int x, int y, int z) {
		this(type, new Vector3(x,y,z));
	}

	public Character(String type, Vector3 position) {
		this.setPosition(position);
		
		characterTexture = new Texture(Gdx.files.internal(type + "_animations.png"));
		TextureRegion[][] tmp = TextureRegion.split(characterTexture, characterTexture.getWidth()/col, characterTexture.getHeight()/row);
		frames = new TextureRegion[col*row];
		
		int index = 0;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				frames[index] = tmp[i][j];	
				frames[index++].flip(false, true);
			}
		}

		stateTime = 0f;
		animation = new Animation(animationSpeed, frames);
		currentImage = animation.getKeyFrame(6);
		bounds = new Rectangle(position.x, position.y, currentImage.getRegionWidth(), currentImage.getRegionHeight());
	}

	public void update(){
		stateTime += Gdx.graphics.getDeltaTime();
		//if( World.getPlayer() == this )	System.out.println("Ladder: " + ladder);
		
		// Check if we have a ladder
		if( ladder == null && ( movingUp || movingDown ) ){
			//if( World.getPlayer() == this )	System.out.println("==============YUP");
			
			Rectangle ladderBounds = movingUp ? new Rectangle(bounds.x,bounds.y+1,bounds.width,bounds.height) : 
									 			new Rectangle(bounds.x,bounds.y-1,bounds.width,bounds.height);
			List<Ladder> obs = World.getLadders();
			for(Ladder ladder : obs){
				if( ladder.getBounds().overlaps(ladderBounds) ){
					
					// Found a ladder to grab onto
					//if( World.getPlayer() == this )	System.out.println("==========FOUND NEW LADDER");
					this.ladder = ladder;
					velocity.x = 0;
					break;
				}
			}
		}
		else if( ladder != null ){
			// Are we still on the ladder?
			if( !ladder.getBounds().overlaps(bounds) ){
				ladder = null;
			}
		}
		
		
		// Not on a ladder
		if( ladder == null ){
			
			// GRAVITY
			velocity.y += gravityStrength;
		}
		
		
		if( attacking ){
			if( World.getPlayer() == this )	System.out.println("if attacking");
			if( lastAttack != 0f ){
				lastAttack += Gdx.graphics.getDeltaTime();
				//if( World.getPlayer() == this )	System.out.println(lastAttack + " " + (Gdx.graphics.getDeltaTime() + attackSpeed));
				if( lastAttack > attackSpeed ){
					attacking = false;	
					lastAttack = 0;
				}
			}
			else{
				// Perform attack
				lastAttack = Gdx.graphics.getDeltaTime();
				if( targetToAttack != null ){
					targetToAttack.takeDamage(attackDamage);
				}
				//if( World.getPlayer() == this )	System.out.println(lastAttack);
			}
		}
		
		
		// Move up/down
		if( ladder != null ){
			if(  velocity.y == 0 ){
				if( movingUp )velocity.y -= climbSpeed;
				if( movingDown )velocity.y += climbSpeed;
			}
			else{
				if( velocity.y < 0 && !movingUp )velocity.y += climbSpeed;
				if( velocity.y > 0 && !movingDown )velocity.y -= climbSpeed;
			}
		}
		
		
		
		// Move left/right
		if( velocity.x != 0 ){
			if( velocity.x < 0 && movingLeft == false ){
				if( ladder == null ){
					velocity.x += walkSpeed;
				}
			}
			else if( velocity.x > 0 && movingRight == false ){
				if( ladder == null ){
					velocity.x -= walkSpeed;
				}
			}
		}
		else if( ladder == null ){
			if( movingLeft ){
				velocity.x -= walkSpeed;
			}
			if( movingRight ){
				velocity.x += walkSpeed;
			}
		}
		
		// Move Character
		moveCharacter();
		
		// Animate character
		animate();
	}
	
	private void animate(){
		if( stateTime /animationSpeed >= col ){
			stateTime = 0;
		}
		
		if( movingUp && ladder != null ){
			currentImage = animation.getKeyFrame(6*animationSpeed + stateTime);
		}
		else if( attacking ){
			if( World.getPlayer() == this )	System.out.println("animate");
			int index = facingRight ? 12 : 15;
			currentImage = animation.getKeyFrame(index*animationSpeed + stateTime);			
		}
		else if( movingLeft ){			
			currentImage = animation.getKeyFrame(3*animationSpeed + stateTime);
		}
		else if( movingDown && ladder != null  ){
			currentImage = animation.getKeyFrame(6*animationSpeed + stateTime);
		}
		else if( movingRight ){
			currentImage = animation.getKeyFrame(stateTime);
		}
		else if( velocity.y > 0 ){
			// Falling
			currentImage = animation.getKeyFrame(9*animationSpeed + stateTime);
		}
		else{
			// IDLE
			currentImage = animation.getKeyFrame(6*animationSpeed + stateTime);
		}
	}
	
	private void moveCharacter(){
		
		// Move by Y
		position.y += velocity.y;
		//if( World.getPlayer() == this )	System.out.println(velocity.y);
		bounds.set(position.x, position.y, currentImage.getRegionWidth(), currentImage.getRegionHeight());
	
		// Check for collision
		List<Tile> overLappingObjects = World.getTilesInArea(bounds);
		if( !overLappingObjects.isEmpty() ){
			
			// Check if the object we are colliding with is colliding with the ladder
			boolean collided = true;
			if( ladder != null ){
				collided = false;
				for(Tile tile : overLappingObjects){
					if( !tile.getBounds().overlaps(ladder.getBounds()) ){
						collided = true;
					}
				}
			}
			
			// Double check collision
			if( collided ){
				//if( World.getPlayer() == this )	System.out.println("COLLIDED");
				if( velocity.y > 0 && ladder != null ) ladder = null;
				position.y -= velocity.y;
				velocity.y = 0;
				bounds.set(position.x, position.y, currentImage.getRegionWidth(), currentImage.getRegionHeight());
				
			}
		}
		
		position.x += velocity.x;
		bounds.set(position.x, position.y, currentImage.getRegionWidth(), currentImage.getRegionHeight());
	
		// Check for collision
		overLappingObjects = World.getTilesInArea(bounds);
		if( !overLappingObjects.isEmpty() ){
			position.x -= velocity.x;
			velocity.x = 0;
		}
		

		
		// Reset bounds
		bounds.set(position.x, position.y, currentImage.getRegionWidth(), currentImage.getRegionHeight());
	}
	
	@Override
	public void draw(SpriteBatch batch){
		batch.draw(currentImage, position.x, position.y);
	}

	@Override
	public void takeDamage(int damage) {
		int oldHealth = health;
		oldHealth = Math.max(0, Math.min(getMaxHealth(), health - damage));

		int difference = Math.abs(health - oldHealth);
		health = oldHealth;
		
		// Floating Text // TODO
		//FloatingText text = new FloatingText(String.valueOf(difference), getPosition().x, getPosition().y);
		//World.addObject(text);
	}

	@Override
	public void heal(int health) {
		takeDamage(-health);
	}

	@Override
	public boolean isAlive() {
		return health > 0;
	}

	@Override
	public int getMaxHealth() {
		return CHARACTER_MAX_HEALTH;
	}

	@Override
	public int getHealth() {
		return health;
	}
	
	/**
	 * @return the animation
	 */
	public Animation getAnimation() {
		return animation;
	}

	/**
	 * @param animation the animation to set
	 */
	public void setAnimation(Animation animation) {
		this.animation = animation;
	}

	/**
	 * @return the frames
	 */
	public TextureRegion[] getFrames() {
		return frames;
	}

	/**
	 * @param frames the frames to set
	 */
	public void setFrames(TextureRegion[] frames) {
		this.frames = frames;
	}

	/**
	 * @return the currentImage
	 */
	public TextureRegion getcurrentImage() {
		return currentImage;
	}
	

	/**
	 * @param currentImage the currentImage to set
	 */
	public void setcurrentImage(TextureRegion currentImage) {
		this.currentImage = currentImage;
	}

	/**
	 * @return the stateTime
	 */
	public float getStateTime() {
		return stateTime;
	}
	
	/**
	 * @param stateTime the stateTime to set
	 */
	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}

	/**
	 * @return the bounds
	 */
	public Rectangle getBounds() {
		return bounds;
	}

	/**
	 * @param bounds the bounds to set
	 */
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	
	public boolean movingLeft() {
		return movingLeft;
	}

	public boolean movingRight() {
		return movingRight;
	}

	public void moveUp() {
		movingUp = true;
	}
	
	public void stopMoveingUp() {
		movingUp = false;
	}

	public void moveLeft() {
		movingLeft = true;
		
		if( movingRight ) stopMoveingRight();
		facingRight = false;		
	}
	
	public void stopMoveingLeft() {
		movingLeft = false;
	}

	public void moveRight() {
		movingRight = true;
		
		if( movingLeft ) stopMoveingLeft();
		facingRight = true;
	}
	
	public void stopMoveingRight() {
		movingRight = false;
	}

	public void moveDown() {
		movingDown = true;
	}
	
	public void stopMoveingDown() {
		movingDown = false;
	}
	
	public boolean moving() {
		return movingLeft() || movingRight();
	}
	
	public boolean movingUp() {
		return movingUp;
	}

	public boolean movingDown() {
		return movingDown;
	}
	
	public void stopMoving() {
		if( movingRight() ) stopMoveingRight();
		if( movingLeft() ) stopMoveingLeft();
	}

	public int getWidth() {
		return currentImage.getRegionWidth();
	}
	
	public int getHeight() {
		return currentImage.getRegionHeight();
	}

	public void jump() {
		if( velocity.y == 0 ){
			velocity.y -= jumpSpeed;
			if ( ladder != null  ) ladder = null;
		}
	}

	public void attack() {
		attacking = true;
		//if( World.getPlayer() == this )	System.out.println("attacking");
		
	}
	
	public void stopAttacking() {
		attacking = false;
	}
	
	public boolean facingRight() {
		return facingRight;
	}
	
	public void flipFacingDirection(){
		facingRight = !facingRight;
	}
	
	public boolean isAttacking(){
		return attacking;
	}

	public float getAttackRange() {
		return attackRange;
	}

	public void setAttackRange(float attackRange) {
		this.attackRange = attackRange;
	}

	public float getAttackSpeed() {
		return attackSpeed;
	}

	public void setAttackSpeed(float attackSpeed) {
		this.attackSpeed = attackSpeed;
	}

	public Character getTargetToAttack() {
		return targetToAttack;
	}

	public void setTargetToAttack(Character targetToAttack) {
		this.targetToAttack = targetToAttack;
	}
}
