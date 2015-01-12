package com.wireframe.stickman;


import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class StickmanGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private OrthographicCamera cam;
	
	public static int VIRTUAL_WIDTH;
	public static int VIRTUAL_HEIGHT;
    private static float ASPECT_RATIO;
    private Rectangle viewport;
    
	@Override
	public void create () {
		VIRTUAL_WIDTH = Gdx.graphics.getWidth();
		VIRTUAL_HEIGHT = Gdx.graphics.getHeight();
		ASPECT_RATIO = (float)VIRTUAL_WIDTH/(float)VIRTUAL_HEIGHT;
		
		batch = new SpriteBatch();
		cam = new OrthographicCamera(VIRTUAL_WIDTH/2, VIRTUAL_HEIGHT/2);
		cam.setToOrtho(true, VIRTUAL_WIDTH/2, VIRTUAL_HEIGHT/2);
		cam.update();
		
		// Import textures
		StickmanResources.initialize();
		
		// Create the world
		World.initialize();
		
		// Initialize everything for Android's controls
		if( Gdx.app.getType() != ApplicationType.Desktop ){
			AndroidGUI.initialize();
		}
	}
	
	@Override
	public void dispose(){
		batch.dispose();
		World.dispose();
	}

	@Override
	public void resize(int width, int height){
		 // calculate new viewport
        float aspectRatio = (float)width/(float)height;
        float scale = 1f;
        Vector2 crop = new Vector2(0f, 0f); 
        
        if(aspectRatio > ASPECT_RATIO)
        {
            scale = (float)height/(float)VIRTUAL_HEIGHT;
            crop.x = (width - VIRTUAL_WIDTH*scale)/2f;
        }
        else if(aspectRatio < ASPECT_RATIO)
        {
            scale = (float)width/(float)VIRTUAL_WIDTH;
            crop.y = (height - VIRTUAL_HEIGHT*scale)/2f;
        }
        else
        {
            scale = (float)width/(float)VIRTUAL_WIDTH;
        }

        float w = (float)VIRTUAL_WIDTH*scale;
        float h = (float)VIRTUAL_HEIGHT*scale;
        viewport = new Rectangle(crop.x, crop.y, w, h);
        
        // Initialize everything for Android's controls
        if( Gdx.app.getType() == ApplicationType.Android ){
        	AndroidGUI.updateButtons(w, h);
        }
	}
	
	@Override
	public void render () {
		// clear previous frame        
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Update the world
		World.update();
		
		// Update Camera
		Player player = World.getPlayer();
		cam.position.set(player.getX()+player.getWidth()/2, player.getY()+player.getHeight()/2, 0);
		//cam.viewportWidth = VIRTUAL_WIDTH/2;
		//cam.viewportHeight = VIRTUAL_HEIGHT/2;
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		
		 // set viewport
        Gdx.gl.glViewport((int) viewport.x, (int) viewport.y,
                          (int) viewport.width, (int) viewport.height);
		
		// Draw the world
		batch.begin();
		World.draw(batch, cam);
		
		// Draw the GUI
		if( Gdx.app.getType() != ApplicationType.Desktop ){
			AndroidGUI.draw(viewport);
			/*BitmapFont f = AndroidGUI.getFont();
			f.setColor(Color.RED);
			f.draw(batch, String.valueOf(GameRules.redKills), 0, 0);
			f.setColor(Color.BLUE);
			f.draw(batch, String.valueOf(GameRules.blueKills), 0, f.getCapHeight());*/
		}
		

		batch.end();
	}
}
