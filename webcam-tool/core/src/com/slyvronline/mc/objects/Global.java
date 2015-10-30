package com.slyvronline.mc.objects;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.slyvronline.mc.utils.GameConstants;

/**
 * This class is the main source for everything to be loaded and where resources are pulled out.
 * Call Global.getImgByName(name) to retrieve any image you need, for instance.
 * 
 * This class also contains some basic variables to determine game defaults and setup information
 * 
 * The current variables keep track of what to be currently displaying or playing in the game.
 * @author Matt Schrum - slyvr89
 * @date 10/25/2013
 */
public class Global {

	private ArrayList<Img> imgs;
	private ArrayList<SoundFx> sounds;
	private ArrayList<Font> fonts;
	private ArrayList<Menu> menus;
	
	private Menu backMenu;
	private Menu currentMenu;
	private GameInstance game;
	private GameOptions options;
	
	private OrthographicCamera backCam;
	private OrthographicCamera camera;
	private OrthographicCamera hudCam;
	private SpriteBatch batch;
	private boolean demoMode;
	
	private float stateTime;
	
	public Global(){
		stateTime = 0f;
		batch = new SpriteBatch();
		options = new GameOptions();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, GameConstants.DEFAULT_WIDTH, GameConstants.DEFAULT_HEIGHT);
		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false, GameConstants.DEFAULT_WIDTH, GameConstants.DEFAULT_HEIGHT);
		backCam = new OrthographicCamera();
		backCam.setToOrtho(false, GameConstants.DEFAULT_WIDTH, GameConstants.DEFAULT_HEIGHT);
		this.demoMode = false;
	}
	
	public Img getImgByName(String name){
		for(Img img : imgs)
			if (name.equals(img.getName())) return img;
		return null;
	}
	public Font getFontByName(String name){
		for(Font font : fonts)
			if (font.getName().equals(name)) return font;
		return null;
	}
	public Menu getMenuByName(String name){
		for(Menu menu : menus)
			if (name.equals(menu.getName())) return menu;
		return null;
	}
	
	public ArrayList<Img> getImgs() {
		return imgs;
	}
	public void setImgs(ArrayList<Img> imgs) {
		this.imgs = imgs;
	}
	public ArrayList<SoundFx> getSounds() {
		return sounds;
	}
	public SoundFx getSoundByName(String name){
		for(SoundFx sound : sounds)
			if (sound.getName().equals(name)) return sound;
		return null;
	}
	public void setSounds(ArrayList<SoundFx> sounds) {
		this.sounds = sounds;
	}
	public ArrayList<Font> getFonts() {
		return fonts;
	}
	public void setFonts(ArrayList<Font> fonts) {
		this.fonts = fonts;
	}
	public ArrayList<Menu> getMenus() {
		return menus;
	}
	public void setMenus(ArrayList<Menu> menus) {
		this.menus = menus;
	}
	public OrthographicCamera getCamera() {
		return camera;
	}
	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}
	public SpriteBatch getBatch() {
		return batch;
	}
	public void setBatch(SpriteBatch batch) {
		this.batch = batch;
	}
	public Menu getBackMenu() {
		return backMenu;
	}
	public void setBackMenu(Menu backMenu) {
		this.backMenu = backMenu;
	}
	public OrthographicCamera getHudCam() {
		return hudCam;
	}
	public void setHudCam(OrthographicCamera hudCam) {
		this.hudCam = hudCam;
	}
	public OrthographicCamera getBackCam() {
		return backCam;
	}
	public void setBackCam(OrthographicCamera backCam) {
		this.backCam = backCam;
	}
	public Menu getCurrentMenu() {
		return currentMenu;
	}
	public void setCurrentMenu(Menu currentMenu) {
		this.currentMenu = currentMenu;
	}
	public GameInstance getGame(){
		return game;
	}
	public void setGame(GameInstance game){
		this.game=game;
	}
	public GameOptions getOptions(){
		return options;
	}
	public void setOptions(GameOptions options){
		this.options=options;
	}
	public boolean isDemoMode() {
		return demoMode;
	}
	public void setDemoMode(boolean demoMode) {
		this.demoMode = demoMode;
	}
	public float getStateTime() {
		return stateTime;
	}
	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}
}
