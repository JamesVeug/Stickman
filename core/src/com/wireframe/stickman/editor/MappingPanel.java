package com.wireframe.stickman.editor;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class MappingPanel implements MouseListener, MouseMotionListener{
	
	private List<PlacedTile> placedTiles = new ArrayList<PlacedTile>();
	
	private TextureRegionDrawable currentTileImage = new TextureRegionDrawable();
	private String currentTileName = "brick";
	private Point mousePosition = new Point(0,0);
	private Dimension size = new Dimension(50,50);
	private boolean mouseOnScreen = false;

	public void draw(SpriteBatch batch){
		
		// Draw placed tiles
		for(int i = 0; i < placedTiles.size(); i++){
			PlacedTile tile = placedTiles.get(i); 
			batch.draw(tile.drawable.getRegion(), tile.point.x, tile.point.y, tile.size.width, tile.size.height);
		}
		
		// Draw Mouse's Image
		// Only if the mouse is on the panel
		if( Gdx.app.getType() == ApplicationType.Desktop && mouseOnScreen ){
			int x = mousePosition.x;
			int y = mousePosition.y;
			batch.draw(currentTileImage.getRegion(),x,y, size.width, size.height);
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		Point tempPosition = arg0.getPoint();		
		tempPosition.x -= tempPosition.x % 50;
		tempPosition.y -= tempPosition.y % 50;
		
		if( !tempPosition.equals(mousePosition) ){
			mousePosition = tempPosition;
		}		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		mouseOnScreen = true;
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		mouseOnScreen = false;
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		pressedOnScreen(arg0.getX(), arg0.getY());
	}
	
	public void pressedOnScreen(int x, int y){
		placedTiles.add(new PlacedTile(new Point(x,y), new Dimension(size.width, size.height), currentTileName, currentTileImage.getRegion()));		
	}
	
	public void update(){
		if( Gdx.input.isTouched() ){
				pressedOnScreen(Gdx.input.getDeltaX(), Gdx.input.getDeltaY());
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void changeSelectedTile(SelectionTile selection, Dimension size) {
		this.size = size;
		this.currentTileName = selection.fileName;
		this.currentTileImage = selection.drawable;
	}

	public List<PlacedTile> getPlacedTiles() {
		return placedTiles;
		
	}
	
	public void setPlacedTiles(List<PlacedTile> loadedMap) {
		this.placedTiles = loadedMap;
	}

	public void setTileDimension(int w, int h) {
		this.size.setSize(w, h);
	}
}
