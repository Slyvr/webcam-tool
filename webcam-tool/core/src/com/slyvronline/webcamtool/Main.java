package com.slyvronline.webcamtool;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamMotionDetector;
import com.github.sarxos.webcam.ds.ipcam.IpCamDeviceRegistry;
import com.github.sarxos.webcam.ds.ipcam.IpCamDriver;
import com.github.sarxos.webcam.ds.ipcam.IpCamMode;
import com.slyvronline.mc.load.LoadFonts;
import com.slyvronline.mc.load.LoadImgs;
import com.slyvronline.mc.load.LoadMenus;
import com.slyvronline.mc.load.LoadSounds;
import com.slyvronline.mc.objects.Global;
import com.slyvronline.mc.render.Renderer;
import com.slyvronline.mc.update.Updater;

public class Main extends ApplicationAdapter {
public static final String TITLE = "Webcam-Tool";
	
	static {
		Webcam.setDriver(new MyCompositeDriver());
	}

	private static List<WebcamEntity> webcams = null;
	
	private int maxHeight;
	private int x=0;
	private int y=0;
	
	public static final float STEP = 1/60f;
	private float accum;
	
	private static Global global;
	
	@Override
	public void create () {
		
		global = new Global();
		global.setDemoMode(true);
		LoadImgs.load();
		LoadFonts.load();
		LoadSounds.load();
		LoadMenus.load();
		
		try {
			String pw = new FileHandle("C:/Apps/pw.txt").readString();
			IpCamDeviceRegistry.register("DLink Cam1", "http://admin:"+pw+"@192.168.1.126:80/image.jpg", IpCamMode.PULL);
			IpCamDeviceRegistry.register("DLink Cam2", "http://admin:"+pw+"@192.168.1.127:80/image.jpg", IpCamMode.PULL);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		webcams = new ArrayList<WebcamEntity>();
		
		for(Webcam webcam : Webcam.getWebcams()){
			webcams.add(createNewWebcamEntity(webcam));
		}
	}
	
	public WebcamEntity createNewWebcamEntity(Webcam webcam){
		WebcamEntity we = new WebcamEntity();
		we.setWebcam(webcam);
		
		WebcamMotionDetector detector = new WebcamMotionDetector(webcam);
		detector.setInterval(500); // one check per 500 ms
		detector.setPixelThreshold(20);
		detector.start();
		we.setDetector(detector);
		
		we.setName(webcam.getName());
		
		webcam.open();
		
		BufferedImage image = webcam.getImage();
		we.setPos(new Rectangle(x,y,image.getWidth(),image.getHeight()));
		
		if (image.getHeight() > maxHeight) maxHeight = image.getHeight();
		x+=image.getWidth();
		if (x >= Gdx.graphics.getWidth()-image.getWidth()-50){
			x=0;
			y += maxHeight;
		}
		
		return we;
	}

	int camCheckCounter;
	
	@Override
	public void render () {
		accum += Gdx.graphics.getDeltaTime();
		while(accum >= STEP) {
			accum -= STEP;
			
			checkWebcamStatus();
			checkNewWebcams();
			
			for(WebcamEntity we : webcams){
				we.update();
			}
			
			Updater.update();
			Renderer.render();
		}
	}
	
	public void checkWebcamStatus(){
		for(int i = webcams.size()-1; i>=0; i--){
			Webcam webcam = webcams.get(i).getWebcam();
			if(!webcam.isOpen()){
				webcams.remove(i);
			}
		}
	}
	
	public void checkNewWebcams(){
		for(Webcam w : Webcam.getWebcams()){
			boolean found=false;
			for(WebcamEntity we : webcams){
				if (w.getName().equals(we.getWebcam().getName())){
					found=true;
				}
			}
			if (!found){
				webcams.add(createNewWebcamEntity(w));
			}
		}
	}

	public float getAccum() {
		return accum;
	}

	public void setAccum(float accum) {
		this.accum = accum;
	}

	public static Global getGlobal() {
		return global;
	}

	public static void setGlobal(Global global) {
		Main.global = global;
	}

	public static String getTitle() {
		return TITLE;
	}

	public static float getStep() {
		return STEP;
	}

	public static List<WebcamEntity> getWebcams() {
		return webcams;
	}

	public static void setWebcams(List<WebcamEntity> webcams) {
		Main.webcams = webcams;
	}

	public int getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(int maxHeight) {
		this.maxHeight = maxHeight;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
}
