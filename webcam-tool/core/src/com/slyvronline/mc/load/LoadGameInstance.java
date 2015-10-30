package com.slyvronline.mc.load;

import com.slyvronline.mc.objects.GameInstance;
import com.slyvronline.webcamtool.Main;

public class LoadGameInstance {

	public static void load(String gamemode){
		if (gamemode.equals("sandbox")){
			GameInstance game = new GameInstance();
			game.setGamemode(gamemode);
			Main.getGlobal().setGame(game);
		}
	}
}
