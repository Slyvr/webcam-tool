package com.slyvronline.mc.load;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.slyvronline.mc.objects.SoundFx;
import com.slyvronline.webcamtool.Main;

public class LoadSounds {

	public static void load(){
		ArrayList<SoundFx> sounds = new ArrayList<SoundFx>();
		
		
		Main.getGlobal().setSounds(sounds);
	}
}
