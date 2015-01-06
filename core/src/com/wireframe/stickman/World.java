package com.wireframe.stickman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class World {
	private static Player player;
	
	private static List<GameObject> objects = new ArrayList<GameObject>();
	private static List<Ladder> ladders = new ArrayList<Ladder>();
	private static List<Tile> tiles = new ArrayList<Tile>();
	private static List<Character> friendlies = new ArrayList<Character>();
	private static List<Character> enemies = new ArrayList<Character>();
	private static List<FloatingText> floatingTexts = new ArrayList<FloatingText>();

	public static void addObject(GameObject obj) {
		objects.add(obj);
		
		if( obj instanceof Ladder ) ladders.add((Ladder) obj);
		else if( obj instanceof Tile ) tiles.add((Tile) obj);
		else if( obj instanceof Player ) player = (Player)obj;
		else if( obj instanceof Friendly ) friendlies.add((Friendly) obj);
		else if( obj instanceof Enemy) enemies.add((Enemy)obj);
		else if( obj instanceof FloatingText ) floatingTexts.add((FloatingText) obj);
	}

	public static void update() {
		
		// Update all objects
		for(int i = 0; i < objects.size(); i++ ){
			 objects.get(i).update();
		}
	}
	
	public static List<Tile> getTilesInArea(Rectangle area){
		List<Tile> overlaps = new ArrayList<Tile>();
		for( Tile obj : tiles ){
			if( area.overlaps(obj.getBounds()) ){
				overlaps.add(obj);
			}
		}
		return overlaps;
	}

	public static void draw(SpriteBatch batch) {

		// Sort the gameObjects
		Collections.sort(objects);
		
		for(int i = 0; i < objects.size(); i++ ){
			 objects.get(i).draw(batch);
		}
	}
	
	public static List<Ladder> getLadders(){
		return ladders;
	}
	
	public static List<Character> getFriendlies(){
		return friendlies;
	}
	
	public static List<Character> getEnemies(){
		return enemies;
	}

	public static Player getPlayer() {
		return player;
	}

	public static void initialize() {
		FileHandle selectedFile = StickmanResources.GetLocalFile("worlds/world.txt");			
		Scanner scan = new Scanner(selectedFile.read());
		while(scan.hasNext()){
			
			//brick 100 100 50 50
			String name = scan.next();
			Vector3 point = new Vector3(scan.nextInt(), scan.nextInt(), 0);
			point.y -= Gdx.graphics.getHeight();
			
			Vector2 size = new Vector2(scan.nextInt(), scan.nextInt());			
			GameObject loadedObject = getObjectFromData(name,point,size);
			assign3DPoint(loadedObject);
			System.out.println(loadedObject);
			addObject(loadedObject);		
		}			
		scan.close();
		
	}
	
	public static void assign3DPoint(GameObject o){
		if( o instanceof Tile ) o.getPosition().z = GameObject.Z_BACKGROUND;
		if( o instanceof WaterShore ) o.getPosition().z = GameObject.Z_BACKGROUNDOBJECT;
		if( o instanceof Water ) o.getPosition().z = GameObject.Z_BACKGROUND;
		if( o instanceof Character ) o.getPosition().z = GameObject.Z_OBJECTS;
	}
	
	public static GameObject getObjectFromData(String name, Vector3 point, Vector2 size){
		if( name.equalsIgnoreCase("brick") ) return new Brick(point,size);
		if( name.equalsIgnoreCase("player") ) return new Player(point);
		if( name.equalsIgnoreCase("enemy") ) return new Enemy(point);
		if( name.equalsIgnoreCase("friendly") ) return new Friendly(point);
		if( name.equalsIgnoreCase("spikes") ) return new Spikes(point,size);
		if( name.equalsIgnoreCase("water") ) return new Water(point,size);
		if( name.equalsIgnoreCase("watershore") ) return new WaterShore(point,size);
		if( name.equalsIgnoreCase("ladder") ) return new Ladder(point,(int) size.y);
		
		
		return null;
	}
	
	public static void createDefaultWorld(){
		addObject(new Player(100,50,GameObject.Z_OBJECTS));
		
		// Left Column
		for(int y = 0; y < 1000; y += 50){addObject(new Brick(0,y,GameObject.Z_BACKGROUND, 50,50));}
		
		// Second Floor
		for(int x = 50; x < 500; x+= 50){addObject(new Brick(x,200,GameObject.Z_BACKGROUND, 50,50));}
		for(int x = 600; x < 1000; x+= 50){addObject(new Brick(x,200,GameObject.Z_BACKGROUND,50,50));}
		
		// Bottom Floor
		for(int x = 50; x < 500; x+= 50){addObject(new Brick(x,0,GameObject.Z_BACKGROUND,50,50));}
		for(int x = 500; x < 600; x+= 50){addObject(new Spikes(x,0,GameObject.Z_BACKGROUND,50,50));}
		for(int x = 600; x < 1000; x+= 50){addObject(new Brick(x,0,GameObject.Z_BACKGROUND,50,50));}
		
		/** WATER LAGGY*/
		for(int x = -200; x < 1000; x += 50){
			addObject(new WaterShore(x,-25,GameObject.Z_BACKGROUNDOBJECT,50,50));
			
			if( x%50 == 0 ){
				addObject(new Water(x,-125,GameObject.Z_BACKGROUNDOBJECT,100,100));
			}
		}
		
		// Ladders
		addObject(new Ladder(215,100,GameObject.Z_BACKGROUNDOBJECT,150));
	}
}

