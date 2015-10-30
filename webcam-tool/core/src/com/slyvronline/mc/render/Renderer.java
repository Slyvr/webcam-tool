package com.slyvronline.mc.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.slyvronline.webcamtool.Main;

public class Renderer {

	public static void render(){
		SpriteBatch batch = Main.getGlobal().getBatch();
		
		
		
		Gdx.gl.glClearColor(0,0,0,0);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		Main.getGlobal().setStateTime(Main.getGlobal().getStateTime() + Gdx.graphics.getDeltaTime());
		
		batch.begin();
		

		if (Main.getGlobal().getBackMenu()!=null){
			batch.setProjectionMatrix(Main.getGlobal().getBackCam().combined);
			Main.getGlobal().getBackMenu().render(batch);
		}
		
		batch.setProjectionMatrix(Main.getGlobal().getCamera().combined);
		
		if (Main.getGlobal().getGame() != null){
			Main.getGlobal().getGame().render(batch);
		}

		
		batch.setProjectionMatrix(Main.getGlobal().getHudCam().combined);
		
		Main.getGlobal().getCurrentMenu().render(batch);
		
		int x=0;
		int y=0;
		for(Texture camTexture : Main.getCamTextures()){
			batch.draw(camTexture, x, y);
			x+=camTexture.getWidth();
			if (x >= Gdx.graphics.getWidth()-camTexture.getWidth()-50){
				x=0;
				y += camTexture.getHeight();
			}
		}
		/*
		Texture cam1 = new Texture(new FileHandle("DLink Cam1.png"));
		Texture cam2 = new Texture(new FileHandle("DLink Cam2.png"));
		
		batch.draw(cam1, 100, 100);
		
		batch.draw(cam2, 200 + cam1.getWidth(), 100);
		*/
		batch.end();
	}
}
