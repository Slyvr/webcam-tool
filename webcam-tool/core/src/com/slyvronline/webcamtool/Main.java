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
		Webcam.setDriver(new IpCamDriver());
	}

	private static List<Webcam> webcams = null;
	private static WebcamMotionDetector detector;
	
	private static List<Texture> camTextures;
	
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
			IpCamDeviceRegistry.register("DLink Cam1", "http://admin:samurai89@192.168.1.126:80/image.jpg", IpCamMode.PULL);
			IpCamDeviceRegistry.register("DLink Cam2", "http://admin:samurai89@192.168.1.127:80/image.jpg", IpCamMode.PULL);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		webcams = Webcam.getWebcams();
		camTextures = new ArrayList<Texture>();
		
		for(Webcam webcam : webcams){
			webcam.open();
		}
	}

	@Override
	public void render () {
		camTextures = new ArrayList<Texture>();
		for(Webcam webcam : webcams){
			// get image
			BufferedImage image = webcam.getImage();
			/* Does not work with IP cameras
			ByteBuffer bytes = webcam.getImageBytes();
			Pixmap pixmap = new Pixmap(bytes.array(), 0, 0);
			*/
			
			// save image to PNG file
			String fileName = webcam.getName()+".png";
			try {
				ImageIO.write(image, "PNG", new File(fileName));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			camTextures.add(new Texture(new FileHandle(fileName)));
		}
		
		accum += Gdx.graphics.getDeltaTime();
		while(accum >= STEP) {
			accum -= STEP;
			Updater.update();
			Renderer.render();
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

	public static List<Webcam> getWebcams() {
		return webcams;
	}

	public static void setWebcams(List<Webcam> webcams) {
		Main.webcams = webcams;
	}

	public static WebcamMotionDetector getDetector() {
		return detector;
	}

	public static void setDetector(WebcamMotionDetector detector) {
		Main.detector = detector;
	}

	public static List<Texture> getCamTextures() {
		return camTextures;
	}

	public static void setCamTextures(List<Texture> camTextures) {
		Main.camTextures = camTextures;
	}
	
	
}
