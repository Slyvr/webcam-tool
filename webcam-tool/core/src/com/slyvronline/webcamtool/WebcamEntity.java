package com.slyvronline.webcamtool;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamMotionDetector;
import com.slyvronline.mc.utils.Utils;

public class WebcamEntity {

	private String name;
	private Webcam webcam;
	private WebcamMotionDetector detector;
	private Texture texture;
	private BufferedImage image;
	private Rectangle pos;
	private int updateCounter;
	private int updateRate;
	
	public WebcamEntity(){
		updateCounter=0;
		updateRate = 60;
	}
	
	public void update(){
		if (updateCounter > updateRate){
			updateTexture();
			updateMotionDetect();
			updateCounter=0;
		}
		else{
			updateCounter++;
		}
	}
	
	public void updateTexture(){
		image = webcam.getImage();
		
		String fileName = "bin/data/webcams/"+webcam.getName()+".png";
		boolean newImage = false;
		FileHandle texFile = new FileHandle(fileName);
		try {
			ImageIO.write(image, "PNG", texFile.file());
			newImage = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (newImage) texture = new Texture(texFile);
	}
	
	public void updateMotionDetect(){
		double motionArea = detector.getMotionArea();
		if (detector.isMotion() && motionArea > 2){
			try {
				String fileName = "C:/Users/Matthew/Google Drive/private/webcamfeed/"+webcam.getName();
				fileName += "_"+System.currentTimeMillis()+".png";
				ImageIO.write(image, "PNG", new File(fileName));
				if (motionArea > 4) Utils.sendEmail(this.getName(), fileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void render(SpriteBatch batch){
		if (texture != null)
			batch.draw(texture, pos.getX(), pos.getY(), pos.getWidth(), pos.getHeight());
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Webcam getWebcam() {
		return webcam;
	}
	public void setWebcam(Webcam webcam) {
		this.webcam = webcam;
	}
	public WebcamMotionDetector getDetector() {
		return detector;
	}
	public void setDetector(WebcamMotionDetector detector) {
		this.detector = detector;
	}
	public Texture getTexture() {
		return texture;
	}
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	public Rectangle getPos() {
		return pos;
	}
	public void setPos(Rectangle pos) {
		this.pos = pos;
	}
	
	
}
