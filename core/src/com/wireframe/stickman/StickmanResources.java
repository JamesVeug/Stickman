package com.wireframe.stickman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.files.FileHandle;

public class StickmanResources {
	public static FileHandle GetLocalFile(String file){
		if( Gdx.app.getType() != ApplicationType.Desktop ) return Gdx.files.local("assets/" + file);
		else if( Gdx.app.getType() != ApplicationType.Android ) return Gdx.files.local(file);
		return null;
	}
}
