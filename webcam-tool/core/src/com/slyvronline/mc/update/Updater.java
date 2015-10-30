package com.slyvronline.mc.update;

import java.util.ArrayList;

import com.slyvronline.webcamtool.Main;

public class Updater {

	public static void update(){

		Main.getGlobal().getCurrentMenu().update();
		if (Main.getGlobal().getGame() != null && Main.getGlobal().getCurrentMenu().getCurrentSubMenu() == null)
			Main.getGlobal().getGame().update();
		
		if (Main.getGlobal().getBackMenu()!=null)
			Main.getGlobal().getBackMenu().update();
		
		Main.getGlobal().getCamera().update();
		Main.getGlobal().getHudCam().update();
		Main.getGlobal().getBackCam().update();
	}
}
