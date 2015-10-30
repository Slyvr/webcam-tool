package com.slyvronline.webcamtool;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamMotionDetector;
import com.github.sarxos.webcam.WebcamPanel;

public class DLinkMotionPanel implements WebcamPanel.Painter{
	
	private boolean saveImages;
	private Webcam webcam;
	private WebcamMotionDetector detector;
	
	public DLinkMotionPanel(Webcam webcam2, boolean b, WebcamMotionDetector d) {
		webcam = webcam2;
		saveImages = b;
		detector = d;
	}

	public DLinkMotionPanel() {
		
	}

	@Override
	public void paintPanel(WebcamPanel panel, Graphics2D g2) {
		panel.getDefaultPainter().paintPanel(panel, g2);
	}

	@Override
	public void paintImage(WebcamPanel panel, BufferedImage image, Graphics2D g2) {
		double s = detector.getMotionArea();
		Point cog = detector.getMotionCog();

		Graphics2D g = image.createGraphics();
		g.setColor(Color.WHITE);
		g.drawString(String.format("Area: %.2f%%", s), 10, 20);

		if (detector.isMotion()) {
			if (saveImages){
				BufferedImage img = webcam.getImage();
				try {
					String fileName = "motion-detected_";
					fileName += System.currentTimeMillis()+".png";
					ImageIO.write(img, "PNG", new File(fileName));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			g.setStroke(new BasicStroke(2));
			g.setColor(Color.RED);
			g.drawOval(cog.x - 5, cog.y - 5, 10, 10);
		} else {
			g.setColor(Color.GREEN);
			g.drawRect(cog.x - 5, cog.y - 5, 10, 10);
		}

		g.dispose();

		panel.getDefaultPainter().paintImage(panel, image, g2);
	}

	public boolean isSaveImages() {
		return saveImages;
	}

	public void setSaveImages(boolean saveImages) {
		this.saveImages = saveImages;
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

	
}
