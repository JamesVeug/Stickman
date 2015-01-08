package com.wireframe.stickman.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.wireframe.stickman.StickmanGame;
import com.wireframe.stickman.editor.MapMaker;

public class EditorLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1024;
		config.height = 720;
		new LwjglApplication(new MapMaker(), config);
	}
}
