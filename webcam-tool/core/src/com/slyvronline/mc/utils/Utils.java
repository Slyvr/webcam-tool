package com.slyvronline.mc.utils;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.slyvronline.webcamtool.Main;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

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
	
	public static void sendEmail(String camName, String imageFileName) throws AddressException, MessagingException{
		
		String pw = new FileHandle("C:/Apps/pw.txt").readString();
		// Step1
		System.out.println("\n 1st ===> setup Mail Server Properties..");
		Properties mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.port", "587");
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");
		System.out.println("Mail Server Properties have been setup successfully..");
 
		// Step2
		System.out.println("\n\n 2nd ===> get Mail Session..");
		Session getMailSession = Session.getDefaultInstance(mailServerProperties, null);
		Message generateMailMessage = new MimeMessage(getMailSession);
		generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("mschrum89@gmail.com"));
		//generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress("test2@crunchify.com"));
		generateMailMessage.setSubject("WebCamFeed_Motion Alert");
		
		BodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setText("Motion detected on camera -> "+camName);
		Multipart multipart = new MimeMultipart();
		messageBodyPart = new MimeBodyPart();
		DataSource source = new FileDataSource(imageFileName);
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(imageFileName);
		multipart.addBodyPart(messageBodyPart);
		
		generateMailMessage.setContent(multipart);
		System.out.println("Mail Session has been created successfully..");
 
		// Step3
		System.out.println("\n\n 3rd ===> Get Session and Send mail");
		Transport transport = getMailSession.getTransport("smtp");
 
		// Enter your correct gmail UserID and Password
		// if you have 2FA enabled then provide App Specific Password
		transport.connect("smtp.gmail.com", "mschrum89@gmail.com", pw);
		transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
		transport.close();
	}
}
