package com.slyvronline.webcamtool.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.slyvronline.mc.utils.GameConstants;
import com.slyvronline.webcamtool.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = Main.TITLE;
		config.width = GameConstants.DEFAULT_WIDTH;
		config.height = GameConstants.DEFAULT_HEIGHT;
		new LwjglApplication(new Main(), config);
	}
}
