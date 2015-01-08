package com.wireframe.stickman.editor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToggleButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.wireframe.stickman.World;


public class SelectionTile extends Button{

	public String fileName;
	public TextureRegion region;
	public TextureRegionDrawable drawable;
	private SelectionPanel selectionPanel;

	public SelectionTile(String fileName, TextureRegion region, SelectionPanel selectionPanel){
		this.selectionPanel = selectionPanel;
		this.fileName = fileName;
		this.region = region;
		this.drawable = new TextureRegionDrawable(region);
		
		// Skin
		TextButtonStyle style = new TextButtonStyle();
		style.up = drawable;
		this.setStyle(style);
		
		// Listener
		addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				actionPerformed();
				return true;
		 	}
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				//World.getPlayer().stopMoveingUp();
				return;
			}

		});
	}

	public void actionPerformed() {
		selectionPanel.notifyMappingPanel(this);
	}
	
	
}
