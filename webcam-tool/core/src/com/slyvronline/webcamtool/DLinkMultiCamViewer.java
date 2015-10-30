package com.slyvronline.webcamtool;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamDiscoveryEvent;
import com.github.sarxos.webcam.WebcamDiscoveryListener;
import com.github.sarxos.webcam.WebcamEvent;
import com.github.sarxos.webcam.WebcamListener;
import com.github.sarxos.webcam.WebcamMotionDetector;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamPicker;
import com.github.sarxos.webcam.WebcamResolution;
import com.github.sarxos.webcam.ds.ipcam.IpCamDeviceRegistry;
import com.github.sarxos.webcam.ds.ipcam.IpCamDriver;
import com.github.sarxos.webcam.ds.ipcam.IpCamMode;


/**
 * Proof of concept of how to handle webcam video stream from Java
 * 
 * @author Bartosz Firyn (SarXos)
 */
public class DLinkMultiCamViewer extends JFrame implements Runnable, WebcamListener, WindowListener, UncaughtExceptionHandler, ItemListener, WebcamDiscoveryListener {

	static {
		Webcam.setDriver(new IpCamDriver());
	}
	
	private static final long serialVersionUID = 1L;

	private Webcam webcam = null;
	private WebcamPanel panel = null;
	private WebcamPicker picker = null;
	private static WebcamMotionDetector detector;
	private boolean saveImages = true;

	@Override
	public void run() {

		Webcam.addDiscoveryListener(this);

		setTitle("Java Webcam Capture POC");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		addWindowListener(this);

		picker = new WebcamPicker();
		picker.addItemListener(this);

		webcam = picker.getSelectedWebcam();

		if (webcam == null) {
			System.out.println("No webcams found...");
			System.exit(1);
		}

		//webcam.setViewSize(WebcamResolution.VGA.getSize());
		webcam.addWebcamListener(DLinkMultiCamViewer.this);

		detector = new WebcamMotionDetector(webcam);
		detector.setInterval(500); // one check per 500 ms
		detector.setPixelThreshold(20);
		detector.start();
		
		panel = new WebcamPanel(webcam, false);
		panel.setFPSDisplayed(true);

		add(picker, BorderLayout.NORTH);
		add(panel, BorderLayout.CENTER);

		pack();
		setVisible(true);

		Thread t = new Thread() {

			@Override
			public void run() {
				panel.setPainter(new DLinkMotionPanel(webcam, saveImages, detector));
				panel.start();
				//detectMotion();
			}
		};
		t.setName("webcam-thread-start");
		t.setDaemon(true);
		t.setUncaughtExceptionHandler(this);
		t.start();
	}

	public static void main(String[] args) throws MalformedURLException {
		IpCamDeviceRegistry.register("DLink Cam1", "http://admin:samurai89@192.168.1.126:80/image.jpg", IpCamMode.PULL);
		IpCamDeviceRegistry.register("DLink Cam2", "http://admin:samurai89@192.168.1.127:80/image.jpg", IpCamMode.PULL);
		
		SwingUtilities.invokeLater(new DLinkMultiCamViewer());
	}

	@Override
	public void webcamOpen(WebcamEvent we) {
		System.out.println("webcam open");
	}

	@Override
	public void webcamClosed(WebcamEvent we) {
		System.out.println("webcam closed");
	}

	@Override
	public void webcamDisposed(WebcamEvent we) {
		System.out.println("webcam disposed");
	}

	@Override
	public void webcamImageObtained(WebcamEvent we) {
		// do nothing
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
		webcam.close();
	}

	@Override
	public void windowClosing(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		System.out.println("webcam viewer resumed");
		panel.resume();
	}

	@Override
	public void windowIconified(WindowEvent e) {
		System.out.println("webcam viewer paused");
		panel.pause();
	}

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		System.err.println(String.format("Exception in thread %s", t.getName()));
		e.printStackTrace();
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getItem() != webcam) {
			if (webcam != null) {

				panel.stop();

				remove(panel);

				webcam.removeWebcamListener(this);
				webcam.close();

				webcam = (Webcam) e.getItem();
				//webcam.setViewSize(WebcamResolution.VGA.getSize());
				webcam.addWebcamListener(this);

				System.out.println("selected " + webcam.getName());

				detector = new WebcamMotionDetector(webcam);
				detector.setInterval(500); // one check per 500 ms
				detector.setPixelThreshold(20);
				detector.start();
				
				panel = new WebcamPanel(webcam, false);
				panel.setFPSDisplayed(true);

				add(panel, BorderLayout.CENTER);
				pack();

				Thread t = new Thread() {

					@Override
					public void run() {
						panel.setPainter(new DLinkMotionPanel(webcam, saveImages, detector));
						panel.start();
					}
				};
				t.setName("webcam-thread");
				t.setDaemon(true);
				t.setUncaughtExceptionHandler(this);
				t.start();
			}
		}
	}

	@Override
	public void webcamFound(WebcamDiscoveryEvent event) {
		if (picker != null) {
			picker.addItem(event.getWebcam());
		}
	}

	@Override
	public void webcamGone(WebcamDiscoveryEvent event) {
		if (picker != null) {
			picker.removeItem(event.getWebcam());
		}
	}
}
