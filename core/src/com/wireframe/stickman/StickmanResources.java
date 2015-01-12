package com.wireframe.stickman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class StickmanResources {
	static HashMap<String, Texture> textures;
	static HashMap<String, TextureAtlas> textureAtlass;
	
	static final List<String> ASSETSFOLDER = new ArrayList<String>(){
		private static final long serialVersionUID = -25920423641235983L;
	{
		// Characters
		add("images/characters/player_animations.png");
		add("images/characters/friendly_animations.png");
		add("images/characters/enemy_animations.png");
		
		// Tiles
		add("images/tiles/brick.png");
		add("images/tiles/block.png");
		add("images/tiles/spikes.png");
		add("images/tiles/water.png");
		add("images/tiles/watershore.png");
		
		// Entities
		add("images/entities/door.png");
		add("images/entities/bluedoor.png");
		add("images/entities/reddoor.png");
		add("images/entities/greendoor.png");
		add("images/entities/ladder.png");
		add("images/entities/redspawner.png");
		add("images/entities/bluespawner.png");
		add("images/entities/smallbutton.png");
		
		// GUI
		add("images/GUI/buttons.pack");
		add("images/GUI/buttons.png");
		add("images/GUI/healthbar.png");
		
	}};
	
	public static void initialize(){
		System.out.println("INITIALIZING IMAGES");
		textures = new HashMap<String, Texture>();
		textureAtlass = new HashMap<String, TextureAtlas>();
		
		// Import all iamges
		for(String path : ASSETSFOLDER){
			FileHandle file = GetInternalFile(path);
			if( file.extension().equals("png") ){
				textures.put(file.name(), new Texture(file));
			}
			else if( file.extension().equals("pack") ){
				textureAtlass.put(file.name(), new TextureAtlas(file));				
			}
		}
		
		
		System.out.println("FINISHED IMPORTING");
	}
	
	public static FileHandle GetLocalFile(String file){
		if( Gdx.app.getType() != ApplicationType.Desktop ) return Gdx.files.local("assets/" + file);
		else if( Gdx.app.getType() != ApplicationType.Android ) return Gdx.files.local(file);
		return null;
	}
	
	public static FileHandle GetInternalFile(String file){
		if( Gdx.app.getType() != ApplicationType.Desktop ) return Gdx.files.internal("./bin/assets" + file);
		else if( Gdx.app.getType() != ApplicationType.Android ) return Gdx.files.internal(file);
		return null;
	}

	public static Texture getImage(String string) {
		//System.out.println(string);
		return textures.get(string);
	}
}
