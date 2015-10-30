package com.slyvronline.mc.load;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.slyvronline.mc.objects.Img;
import com.slyvronline.webcamtool.Main;

public class LoadImgs {

	private static ArrayList<Img> imgs;
	
	public static void load(){
		imgs = new ArrayList<Img>();
		
		System.out.println("Loading Images Now!");
		FileHandle fh = Gdx.files.internal("data/imagesources.txt");
		
		loadImgsFromText(fh.readString());
		
		Main.getGlobal().setImgs(imgs);
	}
	
	public static void loadImgsFromText(String text){
		for(String path : text.split("\r\n")){
			String name = path.split(".png")[0];
			name = name.substring(name.lastIndexOf("/")+1, name.length());
			Texture tex = new Texture(Gdx.files.internal(path));
			imgs.add(new Img(tex,name));
		}
	}
}
