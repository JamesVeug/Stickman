package com.wireframe.stickman;

import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public abstract class Character extends GameObject implements Health{
	public static final int TEAM_NEUTRAL = 0;
	public static final int TEAM_BLUE = 1;
	public static final int TEAM_RED = 2;
	
	public static final int CHARACTER_MAX_HEALTH = 100;
	public static final int CHARACTER_FADE_TIME = 2; // Time before dissappering
	public static final int CHARACTER_ATTACKDAMAGE_MAX = 15;
	public static final int CHARACTER_ATTACKDAMAGE_BASE = 1;
	public static final int CHARACTER_CRITICALCHANCE = 10; // 5/100
	public static final int CHARACTER_CRITICALINCREASE = 2; // Amount to increase by when critical hit is applied
	

	public static final int col = 3;
	public static final int row = 8;
	private boolean animating = true;
	private Animation animation;
	private Texture characterTexture;
	private TextureRegion[] frames;
	private TextureRegion currentImage;

	private float stateTime;
	private float fadeTime;
	

	
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
	
	private int teamNumber = TEAM_NEUTRAL;
	private Character targetToAttack;
	
	// Attacking
	protected Random random = new Random();
	private int criticalIncrease = CHARACTER_CRITICALINCREASE;
	private int criticalChance = CHARACTER_CRITICALCHANCE;
	private int maxAttackDamage = CHARACTER_ATTACKDAMAGE_MAX;
	private int baseAttackDamage = CHARACTER_ATTACKDAMAGE_BASE;
	private float lastAttack = 0;
	private float attackSpeed = 0.25f;
	private float attackRange = 30;
	
	// HEAlTH
	private int maxHealth = CHARACTER_MAX_HEALTH;
	private int health = maxHealth;

	public Character(String type, int x, int y, int z, int w, int h) {
		this(type, new Vector3(x,y,z), new Vector2(w,h));
	}

	public Character(String type, Vector3 position, Vector2 size) {
		super(position, size);
		initialize(type);
	}
	
	public void dispose(){
		//characterTexture.dispose();
		for(TextureRegion t : frames){
			//wWWt.getTexture().dispose();
		}
	}
	
	private void initialize(String type){		
		this.setTeamNumber(type);
		characterTexture = StickmanResources.getImage(type + "_animations.png");
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
	}

	public void update(){
		stateTime += Gdx.graphics.getDeltaTime();
		
		// Are we did?
		if( !isAlive() ){
			fadeTime += Gdx.graphics.getDeltaTime();
			if( fadeTime > CHARACTER_FADE_TIME ){
				World.removeCharacter(this);
			}
			animate();
			return;
		}
		
		
		// Check if we have a ladder
		if( ladder == null && ( movingUp || movingDown ) ){
			//if( World.getPlayer() == this )	System.out.println("==============YUP");
			
			Rectangle ladderBounds = getBounds();
			ladderBounds.setY(movingUp ? ladderBounds.y+1 : ladderBounds.y-1);
			
			List<Ladder> obs = World.getLadders();
			for(Ladder ladder : obs){
				if( ladder.getBounds().overlaps(ladderBounds) ){
					
					// Found a ladder to grab onto
					attachToLadder(ladder);
					break;
				}
			}
		}
		else if( ladder != null ){
			// Are we still on the ladder?
			if( !ladder.getBounds().overlaps(getBounds()) ){
				ladder = null;
			}
		}
		
		
		// Not on a ladder
		if( ladder == null ){
			
			// GRAVITY
			velocity.y += gravityStrength;
		}
		
		
		if( attacking ){
			if( lastAttack != 0f ){
				lastAttack += Gdx.graphics.getDeltaTime();
				if( lastAttack > attackSpeed ){
					attacking = false;	
					lastAttack = 0;
				}
			}
			else{
				// Perform attack
				performAttack();
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
	
	public void attachToLadder(Ladder ladder){
		this.ladder = ladder;
		velocity.set(0,0);
		
		float x = ladder.getX() + (ladder.getWidth()/2) - (getWidth()/2);
		moveTo(x,getY());
	}
	
	public void performAttack() {
		lastAttack = Gdx.graphics.getDeltaTime();
		if( targetToAttack != null ){
			int damage = random.nextInt(maxAttackDamage - baseAttackDamage) + baseAttackDamage;
			boolean criticalHit = random.nextInt(100) < getCriticalChance(); 
			damage *= criticalHit ? getCriticalIncrease() : 1;					
			targetToAttack.takeDamage(damage, criticalHit, this);
		}
	}

	private void animate(){
		if( !animating ){ return; }
		
		if( stateTime /animationSpeed >= col ){
			stateTime = 0;
		}
		
		if( !isAlive() ){
			
			// Death
			int index = facingRight ? 18 : 21;
			currentImage = animation.getKeyFrame(index*animationSpeed + stateTime);
			if( currentImage == animation.getKeyFrames()[index+2]){
				animating = false;
			}
		}
		else if( movingUp && ladder != null ){
			currentImage = animation.getKeyFrame(6*animationSpeed + stateTime);
		}
		else if( attacking ){
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
		moveBy(0,velocity.y);
	
		// Check for collision
		List<Tile> overLappingObjects = World.getTilesInArea(getBounds());
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
				moveBy(0,-velocity.y);
				velocity.y = 0;
				
			}
		}

		// Move BY X
		moveBy(velocity.x,0);
	
		// Check for collision
		overLappingObjects = World.getTilesInArea(getBounds());
		if( !overLappingObjects.isEmpty() ){
			moveBy(-velocity.x,0);
			velocity.x = 0;
		}
	}
	
	protected boolean canAttackTarget(GameObject target) {
		
		// See if they are alive
		if( target instanceof Health && !((Health)target).isAlive() ){
			return false;
		}
		
		// Must be on the same Y
		if( Math.abs((int)target.getY() - (int)getY()) > 10){
			return false;
		}
		
		// Must be within attacking range
		if( (int)target.getX() < (int)getX() ){
			// Enemy to the left
			
			// Not facing the enemy that is on the left
			if( facingRight() ){
				//System.out.println("NOT Facing correct direction");
				return false; 
			}
			
			// Within attacking range
			if( Vector2.len(target.getX() - getX(), 1) > getAttackRange() ){
				//System.out.println(Vector2.len(target.getX() - getX(), 1));
				return false;
			}
		}
		else{
			// Enemy to the right
			
			// Not facing the enemy that is on the right
			if( !facingRight() ){
				//System.out.println("NOT Facing correct direction");
				return false; 
			}
			
			// Within attacking range
			if( (int)target.getX() > (int)(getX()+getAttackRange()) ){
				//System.out.println(Vector2.len(target.getX() - getX(), 1));
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public void draw(SpriteBatch batch){
		batch.draw(currentImage, getX(), getY(), getWidth(), getHeight());
	}

	@Override
	public void takeDamage(int damage, boolean isACriticalHit, Character attacker) {
		int oldHealth = health;
		oldHealth = Math.max(0, Math.min(getMaxHealth(), health - damage));

		int difference = Math.abs(health - oldHealth);
		health = oldHealth;
		
		// Floating Text // TODO
		FloatingText text = new FloatingText(String.valueOf(difference), getX(), getY(), isACriticalHit ? Color.PURPLE : Color.RED);
		text.moveBy(0, -text.getHeight());
		World.addObject(text);
		
		if( !isAlive() ){
			stateTime = 0f;
		}
	}

	@Override
	public void heal(int health) {
		takeDamage(-health, false, null);
	}

	@Override
	public boolean isAlive() {
		return health > 0;
	}

	@Override
	public int getMaxHealth() {
		return maxHealth;
	}

	@Override
	public int getHealth() {
		return health;
	}
	
	@Override
	public void setMaxHealth(int max){
		maxHealth = max;
	}
	
	@Override
	public void setHealth(int health){
		this.health = health;
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

	public int getTeamNumber() {
		return teamNumber;
	}

	public void setTeamNumber(int teamNumber) {
		this.teamNumber = teamNumber;
	}

	private void setTeamNumber(String type) {
		if( type.equalsIgnoreCase("player") ) this.setTeamNumber(TEAM_BLUE);
		else if( type.equalsIgnoreCase("friendly") ) this.setTeamNumber(TEAM_BLUE);
		else if( type.equalsIgnoreCase("enemy") ) this.setTeamNumber(TEAM_RED);		
	}

	public float getFadeTime() {
		return fadeTime;
	}

	public void setFadeTime(float fadeTime) {
		this.fadeTime = fadeTime;
	}

	public int getMaxAttackDamage() {
		return maxAttackDamage;
	}

	public void setMaxAttackDamage(int maxAttackDamage) {
		this.maxAttackDamage = maxAttackDamage;
	}

	public int getBaseAttackDamage() {
		return baseAttackDamage;
	}

	public void setBaseAttackDamage(int baseAttackDamage) {
		this.baseAttackDamage = baseAttackDamage;
	}

	public int getCriticalChance() {
		return criticalChance;
	}

	public void setCriticalChance(int criticalChance) {
		this.criticalChance = criticalChance;
	}

	public int getCriticalIncrease() {
		return criticalIncrease;
	}

	public void setCriticalIncrease(int criticalIncrease) {
		this.criticalIncrease = criticalIncrease;
	}
}
