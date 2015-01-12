package com.wireframe.stickman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class World {
	private static Player player;
	
	private static List<GameObject> objects;
	private static List<Ladder> ladders;
	private static List<Tile> tiles;
	private static List<Character> friendlies;
	private static List<Character> enemies;
	private static List<FloatingText> floatingTexts;
	private static List<Spawner> spawners;
	private static List<Interactable> interactables;
	private static List<Door> doors;
	private static Texture DEFAULTBACKGROUND;

	public static void addObject(GameObject obj) {
		//if( obj instanceof Tile)
		objects.add(obj);
		
		if( obj instanceof Player ){
			player = (Player)obj;
			friendlies.add(player);
		}
		if( obj instanceof Ladder ) ladders.add((Ladder) obj);
		if( obj instanceof Tile ) tiles.add((Tile) obj);
		if( obj instanceof Friendly )friendlies.add((Friendly) obj);
		if( obj instanceof Enemy) enemies.add((Enemy)obj);
		if( obj instanceof FloatingText ) floatingTexts.add((FloatingText) obj);
		if( obj instanceof Spawner ) spawners.add((Spawner) obj);
		if( obj instanceof Interactable ) interactables.add((Interactable) obj);
		if( obj instanceof Door ) doors.add((Door) obj);
	}

	public static void update() {
		
		// Update all objects
		for(int i = 0; i < objects.size(); i++ ){
			 objects.get(i).update();
		}
	}
	
	public static void dispose(){
		for(GameObject o : objects){
			o.dispose();
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

	public static void draw(SpriteBatch batch, OrthographicCamera cam) {
		
		// Draw background
		drawBackground(batch, cam);

		// Sort the gameObjects
		Collections.sort(objects);
		
		for(int i = 0; i < objects.size(); i++ ){
			 objects.get(i).draw(batch);
		}
	}
	
	private static void drawBackground(SpriteBatch batch, OrthographicCamera cam){
		float x = World.getPlayer().getPosition().x - cam.viewportWidth/2;
		float y = World.getPlayer().getPosition().y - cam.viewportHeight/2;
		float w = World.getPlayer().getSize().x + cam.viewportWidth;
		float h = World.getPlayer().getSize().y + cam.viewportHeight;
		
		batch.draw(DEFAULTBACKGROUND, x, y, w, h);
		
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
	
	public static List<Interactable> getInteractables(){
		return interactables;
	}

	public static Player getPlayer() {
		return player;
	}

	public static void initialize() {
		objects = new ArrayList<GameObject>();
		ladders = new ArrayList<Ladder>();
		tiles = new ArrayList<Tile>();
		friendlies = new ArrayList<Character>();
		enemies = new ArrayList<Character>();
		floatingTexts = new ArrayList<FloatingText>();
		spawners = new ArrayList<Spawner>();
		interactables = new ArrayList<Interactable>();
		doors = new ArrayList<Door>();
		DEFAULTBACKGROUND = new Texture(StickmanResources.GetLocalFile("worlds/background_null.png"));
		
		
		FileHandle selectedFile = StickmanResources.GetLocalFile("worlds/world.txt");			
		Scanner scan = new Scanner(selectedFile.read());
		while(scan.hasNext()){
			
			//brick 100 100 50 50
			String name = scan.next();
			if( name.equals("background") ){
				processBackground(scan);
				continue;
			}
			Vector3 point = new Vector3(scan.nextInt(), scan.nextInt(), 0);
			point.y -= Gdx.graphics.getHeight();
			
			Vector2 size = new Vector2(scan.nextInt(), scan.nextInt());			
			GameObject loadedObject = getObjectFromData(name,point,size);
			assign3DPoint(loadedObject);
			addObject(loadedObject);		
		}			
		scan.close();
		
	}
	
	private static void processBackground(Scanner scan) {
		@SuppressWarnings("unused")
		String backgroundImage = scan.next();
		
		
	}

	public static void assign3DPoint(GameObject o){
		if( o instanceof Tile ) o.getPosition().z = GameObject.Z_BACKGROUND;
		if( o instanceof WaterShore ) o.getPosition().z = GameObject.Z_BACKGROUNDOBJECT;
		if( o instanceof Water ) o.getPosition().z = GameObject.Z_BACKGROUND;
		if( o instanceof Character ) o.getPosition().z = GameObject.Z_OBJECTS;
	}
	
	public static GameObject getObjectFromData(String name, Vector3 point, Vector2 size){
		if( name.equalsIgnoreCase("brick") ) return new Brick(point,size);
		if( name.equalsIgnoreCase("player") ) return new Player(point,size);
		if( name.equalsIgnoreCase("enemy") ) return new Enemy(point,size);
		if( name.equalsIgnoreCase("friendly") ) return new Friendly(point,size);
		if( name.equalsIgnoreCase("spikes") ) return new Spikes(point,size);
		if( name.equalsIgnoreCase("water") ) return new Water(point,size);
		if( name.equalsIgnoreCase("watershore") ) return new WaterShore(point,size);
		if( name.equalsIgnoreCase("ladder") ) return new Ladder(point,(int) size.y);
		if( name.equalsIgnoreCase("redspawner") ) return new Spawner(point,size,Spawner.SPAWNERTYPE_RED);
		if( name.equalsIgnoreCase("bluespawner") ) return new Spawner(point,size,Spawner.SPAWNERTYPE_BLUE);
		if( name.equalsIgnoreCase("redbutton") ) return new SmallButton(point,size,SmallButton.RED);
		if( name.equalsIgnoreCase("bluebutton") ) return new SmallButton(point,size,SmallButton.BLUE);
		if( name.equalsIgnoreCase("greenbutton") ) return new SmallButton(point,size,SmallButton.GREEN);
		if( name.equalsIgnoreCase("reddoor") ) return new Door(point,size,Door.RED);
		if( name.equalsIgnoreCase("bluedoor") ) return new Door(point,size,Door.BLUE);
		if( name.equalsIgnoreCase("greendoor") ) return new Door(point,size,Door.GREEN);
		
		
		throw new RuntimeException("UNKNOWN DATATYPE: '" + name + "'");
	}
	
	public static void createDefaultWorld(){
		addObject(new Player(100,50,GameObject.Z_OBJECTS,50,50));
		
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

	public static void removeCharacter(Character victim) {
		objects.remove(victim);
		//victim.dispose();
		if( victim instanceof Enemy ){
			enemies.remove(victim);
		}
		else if( victim instanceof Friendly ){
			friendlies.remove(victim);
		}
		else if( victim == player ){
			System.out.println("REMOVED PLAYER");
		}
		
	}
	
	public static void removeFloatingText(FloatingText text){
		floatingTexts.remove(text);
		objects.remove(text);
		//text.dispose();
	}

	public static Door getDoor(int type) {
		for(int i = 0; i < doors.size(); i++ ){
			Door door = doors.get(i);
			if( door.getType() == type ){
				return door;
			}
		}
		// TODO Auto-generated method stub
		return null;
	}

	public static List<Character> getCharactersInArea(Rectangle bounds) {
		// TODO Auto-generated method stub
		return null;
	}
}

