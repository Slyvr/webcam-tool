package com.slyvronline.mc.load;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.slyvronline.mc.objects.Ent;
import com.slyvronline.mc.objects.Menu;
import com.slyvronline.mc.objects.menus.*;
import com.slyvronline.webcamtool.Main;

public class LoadMenus {

	public static void load(){
		ArrayList<Menu> menus = new ArrayList<Menu>();
		
		menus.add(loadMainMenu());
		
		Main.getGlobal().setMenus(menus);
		Main.getGlobal().setCurrentMenu(menus.get(0));
	}
	
	public static Menu loadMainMenu(){
		MainMenu menu = new MainMenu();
		menu.setName("main");
		
		ArrayList<Ent> ents = new ArrayList<Ent>();
		/*
		Ent logo = new Ent();
		logo.setName("logo");
		logo.setImg(Main.getGlobal().getImgByName("Logo2_white"));
		logo.setPosBox(new Rectangle(80,
				Gdx.graphics.getHeight()-logo.getImg().getTex().getHeight()-100,
				logo.getImg().getTex().getWidth(),
				logo.getImg().getTex().getHeight()));
		ents.add(logo);
		*/
		menu.setEnts(ents);
		
		//Setup sub menus
		ArrayList<Menu> subMenus = new ArrayList<Menu>();
		
		menu.setSubMenus(subMenus);
		
		return menu;
	}
}
