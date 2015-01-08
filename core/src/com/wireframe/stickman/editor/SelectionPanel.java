package com.wireframe.stickman.editor;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.plaf.metal.MetalIconFactory.FolderIcon16;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.wireframe.stickman.StickmanResources;


public class SelectionPanel extends JPanel{

	private MappingPanel mappingPanel;
	private Dimension size = new Dimension(50,50);
	private HashMap<String,TextureRegionDrawable> imageNameToImage = new HashMap<String, TextureRegionDrawable>();
	private ArrayList<SelectionTile> buttons = new ArrayList<SelectionTile>();
	private static final List<String> directories = new ArrayList<String>(){{
		add("assets/tiles/");
		add("characters");
	}};
	
	public SelectionPanel(MappingPanel mappingPanel){
		
		this.mappingPanel = mappingPanel;
		setFocusable(true);
		
		int height = 0;
		for( String directory : directories ){
			System.out.println(directory);
			
			FileHandle folder = StickmanResources.GetInternalFile(directory);

			System.out.println(folder.path());
			System.out.println(folder.exists());
			FileHandle[] images = folder.list();
			System.out.println(images);
			
			for(FileHandle file : images){
				String fileName = file.name();
				fileName = fileName.substring(0, fileName.indexOf("."));
				
				SelectionTile tile = new SelectionTile(fileName, new TextureRegion(new Texture(file.path())), this);
				buttons.add(tile);
				
	
				height += tile.region.getRegionHeight();
				imageNameToImage.put(tile.fileName,  tile.drawable);
			}
			height += images.length*20;
		}
		setPreferredSize(new Dimension(80,height));
	}
	
	public void notifyMappingPanel(SelectionTile selection){
		mappingPanel.changeSelectedTile(selection, size);
	}
	
	public TextureRegionDrawable getImageFromName(String name){
		TextureRegionDrawable image = imageNameToImage.get(name);
		if( image == null ){
			throw new RuntimeException("Unknown name: " + name);
		}
		
		return image;
	}
}
