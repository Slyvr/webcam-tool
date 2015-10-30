package com.slyvronline.mc.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.slyvronline.webcamtool.Main;

public class Utils {
	
	public static double calcLineDistance(Rectangle pos1, Rectangle pos2){
		float diffX = pos1.getX()-pos2.getX();
		float diffY = pos1.getY()-pos2.getY();
		double distance = Math.sqrt((diffX*diffX)+(diffY*diffY));
		return distance;
	}
	
	public static Rectangle getGameMousePos(){
		Vector3 mousePos = Main.getGlobal().getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
		return new Rectangle(mousePos.x, mousePos.y,1,1);
	}
	
	public static Rectangle getMenuMousePos(){
		Vector3 mousePos = Main.getGlobal().getHudCam().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
		return new Rectangle(mousePos.x, mousePos.y,1,1);
	}
}
