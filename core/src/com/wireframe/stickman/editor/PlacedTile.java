package com.wireframe.stickman.editor;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.ImageIcon;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class PlacedTile {
	public Point point;
	public Dimension size;
	public TextureRegionDrawable drawable;
	public TextureRegion region;
	public String name;
	
	public PlacedTile(Point point, Dimension size, String name, TextureRegion region){
		this.name = name;
		this.point = point;
		this.size = size;
		this.region = region;
		this.drawable = new TextureRegionDrawable(region);
	}

	public String getSave() {		
		String save = name + " " + point.x + " " + point.y + " " + size.width + " " + size.height;
		return save;
	}
}
