package com.wireframe.stickman;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GUI {
	@SuppressWarnings("unused")
	private static Stage stage;
	private static BitmapFont font;	
	
	
	public static void initialize(){
		font.setScale(3f);
		stage = new Stage();
		font = new BitmapFont(false);
	}		
}
