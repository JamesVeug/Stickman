package com.wireframe.stickman;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Door extends Tile implements Toggleable{

	public static final int MOVEDISTANCE = 1;
	public static final int UNLOCKED_LOCK = 0;
	public static final int RED = 1;
	public static final int BLUE = 2;
	public static final int GREEN = 3;
	public static Map<Integer, String> valueToString = new HashMap<Integer,String>(){
		private static final long serialVersionUID = -7407507921104760839L;
	{
		put(RED, "RED");
		put(BLUE, "BLUE");
		put(GREEN, "GREEN");
	}};
	

	private int type = UNLOCKED_LOCK;
	
	private Vector2 move = new Vector2(0,0); // Distance moved from the original position
	private boolean opening = false;

	public Door(Vector3 position, Vector2 size, int type){
		super(position, size, StickmanResources.getImage(valueToString.get(type).toLowerCase() +"door.png"));		
	}
	
	private void open(){
		if( move.y > -getHeight() ){
			move.y -= MOVEDISTANCE;
			moveBy(0,-MOVEDISTANCE);
			checkCollison();
		}		
	}

	private void close(){
		if( move.y < 0 ){
			move.y += MOVEDISTANCE;
			moveBy(0,MOVEDISTANCE);
			checkCollison();
		}		
	}
	
	private void checkCollison() {
		
		// Check Colliding with charcters
		/*List<Character> characters = World.getCharactersInArea(getBounds());
		if( !characters.isEmpty()) {
			for( Character c : characters){
				c.moveBy(0, opening ? -MOVEDISTANCE : MOVEDISTANCE);
			}
		}*/
	}

	@Override
	public void update() {
		if( opening ){
			open();
		}
		else{
			close();
		}
		
	}
	
	@Override
	public void toggle() {
		opening = !opening;
	}

	@Override
	public void draw(SpriteBatch batch) {
		batch.draw(getTexture(), getX(), getY(), getWidth(), getHeight());		
	}

	public int getType() {
		return type;
	}
}
