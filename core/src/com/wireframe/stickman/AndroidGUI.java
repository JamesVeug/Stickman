package com.wireframe.stickman;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class AndroidGUI {
	private static Stage stage;
	private static BitmapFont font;

	private static ArrayList<Button> movingmentButtons;
	private static ArrayList<Button> actionButtons;
	private static ArrayList<TextButtonStyle> buttonStyles; 	
	private static ArrayList<String> buttonStylesMappings;
	
	
	private static Skin skin;
	private static TextureAtlas buttonAtlas;
	
	public static void initialize(){
		stage = new Stage();
		font = new BitmapFont(false);
		buttonStyles = new ArrayList<TextButtonStyle>();
		buttonStylesMappings = new ArrayList<String>(){
			private static final long serialVersionUID = 6051847176794062001L;
		{
			add("up");
			add("left");
			add("right");
			add("down");
			add("circlebutton");
			add("circlebutton");
		}};



		
		
		
		skin = new Skin();
		buttonAtlas = new TextureAtlas("buttons/buttons.pack");
				
				
				
		skin.addRegions(buttonAtlas);
		font.setScale(3f);
		
		for(String string : buttonStylesMappings){
			TextButtonStyle style = new TextButtonStyle();
			style.up = skin.getDrawable(string);
			style.down = skin.getDrawable(string +"_pressed");
			style.font = font;
			buttonStyles.add(style);
		}
		
		movingmentButtons = new ArrayList<Button>(){
			private static final long serialVersionUID = -297482046234989211L;

		{
			add(new Button(buttonStyles.get(0))); // Up
			add(new Button(buttonStyles.get(1))); // Left
			add(new Button(buttonStyles.get(2))); // Right
			add(new Button(buttonStyles.get(3))); // Down
		}};
		actionButtons = new ArrayList<Button>(){
			private static final long serialVersionUID = -6045127395846488373L;

		{
			add(new TextButton("JUMP", buttonStyles.get(4))); // Jump
			add(new TextButton("ATTACK", buttonStyles.get(5))); // Attack
		}};
		
		Gdx.input.setInputProcessor(stage);

		
		// UP
		movingmentButtons.get(0).addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				World.getPlayer().moveUp();
				//Gdx.input.vibrate(50);
				return true;
		 	}
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				World.getPlayer().stopMoveingUp();
				return;
			}

		});
		
		// LEFT
		movingmentButtons.get(1).addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				World.getPlayer().moveLeft();
				//Gdx.input.vibrate(50);
				return true;
		 	}
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				World.getPlayer().stopMoveingLeft();
				return;
			}

		});
		
		
		// RIGHT
		movingmentButtons.get(2).addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				World.getPlayer().moveRight();
				//Gdx.input.vibrate(50);
				return true;
		 	}
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				World.getPlayer().stopMoveingRight();
				return;
			}

		});
		
		// DOWN
		movingmentButtons.get(3).addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				World.getPlayer().moveDown();
				//Gdx.input.vibrate(50);
				return true;
		 	}
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				World.getPlayer().stopMoveingDown();
				return;
			}

		});
		
		// Jump
		actionButtons.get(0).addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				//World.getPlayer().moveDown();
				return true;
		 	}
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				World.getPlayer().jump();
				//Gdx.input.vibrate(50);
				return;
			}

		});
		
		// Attack
		actionButtons.get(1).addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				World.getPlayer().attack();
				//Gdx.input.vibrate(50);
				return true;
		 	}
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				return;
			}

		});
	}
	
	public static void updateButtons(float viewWidth, float viewHeight){
		float width = (float) (viewHeight*0.15);
		float height = width;
		for(Button b : movingmentButtons){
			b.setSize(width, height);
			stage.addActor(b);
		}
		movingmentButtons.get(0).setPosition(width, height*2); // UP
		movingmentButtons.get(1).setPosition(0, height); // Left
		movingmentButtons.get(2).setPosition(width*2, height); // Right
		movingmentButtons.get(3).setPosition(width, 0); // Down

		
		/** ACTIONS BUTTONS */
		width *= 1.5;
		height *= 1.5;
		for(Button b : actionButtons){
			b.setSize(width, width);
			stage.addActor(b);
		}
		
		actionButtons.get(0).setPosition(viewWidth-(width*2), 0); // Jump
		actionButtons.get(1).setPosition(viewWidth-width, height); // Attack
	}
	
	public static void draw(Rectangle viewport){
		stage.getViewport().setScreenBounds((int)viewport.x, (int)viewport.y, (int)viewport.width, (int)viewport.height);
		stage.draw();
	}

	public static void resize(int width, int height) {
		updateButtons(width,height);
	}

	/**
	 * @return the font
	 */
	public static BitmapFont getFont() {
		return font;
	}
}
