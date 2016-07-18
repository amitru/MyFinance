package com.amitru.mystockalert.util;

import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;

import com.amitru.mystockalert.beans.WatchListStock;

public class MailUtil {

	@Autowired
	String userName;
	
	@Autowired
	String password;
	
	
	public void sendEmail(List<WatchListStock> stockList) {

		if(stockList==null || stockList.size()==0) {
			System.out.println("Your Target Price not reached, No emails to sent... ");
			return;
		}
		
		List<String> recepientList = stockList.get(0).getEmailIdList();
		
		System.out.println("recepientList=" + recepientList);

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.starttls.required", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		props.put("mail.smtp.EnableSSL.enable","true");
		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
		
		
		props.put("mail.smtp.user", getUserName()); 
		props.put("mail.smtp.password",getPassword()); 
		props.put("mail.smtp.host", "smtp.gmail.com"); 
		props.put("mail.smtp.port", "25"); 
		props.put("mail.debug", "true"); 
		props.put("mail.smtp.auth", "true"); 
		props.put("mail.smtp.starttls.enable","true"); 
		props.put("mail.smtp.EnableSSL.enable","true");
		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
		props.setProperty("mail.smtp.socketFactory.fallback", "false"); 
		props.setProperty("mail.smtp.socketFactory.port", "465"); 
		
		
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(getUserName(), getPassword());
			}
		  });

		try {

			Message message = new MimeMessage(session);
			
			message.setFrom(new InternetAddress(getUserName()));
			
			//for(String recepientID: recepientList) 
				//message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(recepientID));
			
			
			InternetAddress[] recipientAddress = new InternetAddress[recepientList.size()];
			int counter = 0;
			for (String recipient : recepientList) {
			    recipientAddress[counter] = new InternetAddress(recipient.trim());
			    counter++;
			}
			message.setRecipients(Message.RecipientType.TO, recipientAddress);
			
			  MimeBodyPart textBodyPart = new MimeBodyPart();
		        textBodyPart.setText(prepareMessage(stockList));
			
			BodyPart messageBodyPart = new MimeBodyPart();
			
			Multipart multipart = new MimeMultipart();
			
			// Part two is attachment
	         messageBodyPart = new MimeBodyPart();
	         String filename = "d:\\MyStockAlerts.xls";
	         DataSource source = new FileDataSource(filename);
	         messageBodyPart.setDataHandler(new DataHandler(source));
	         messageBodyPart.setFileName(filename);
	         
	         multipart.addBodyPart(textBodyPart);
	         
	         multipart.addBodyPart(messageBodyPart);

	         // Send the complete message parts
	         message.setContent(multipart);
			
			//message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("amit.ruwali@gmail.com"));
			
			message.setSubject("TESTING::STOCK ALERT");
			
			//message.setText(prepareMessage(stockList));

			Transport.send(message);

			System.out.println("Message Sent to-" + recepientList);

		} catch (MessagingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	
	}
	
	private static String prepareMessage(List<WatchListStock> stockList) {
		
		StringBuffer myMessage = new StringBuffer();
		
		myMessage.append("\n Dear User, Attention ! The target set by you for below share(s)-is reached, buy it as early as possible-");
		for(WatchListStock myStock: stockList) {
			myMessage.append("\n ------------------------------------------------");
			myMessage.append("\n Script- " + myStock.getScriptCode() + " " + myStock.getScriptType());
			myMessage.append("\n Your Intrested Price=" + myStock.getIntrestedPriceBuy());
			myMessage.append("\n Current Market Price=" + myStock.getLivePrice());
			myMessage.append("\n ------------------------------------------------");
		}
		
		myMessage.append("\n Jai Sri Ganesh Maharaj Ki.....");
		
		myMessage.append("\n \n PLEASE NOTE: Based on Attached XLS, if you want to modify, please update the attached xls and reply in this email..");
		
		myMessage.append("\n \n Regards, \n Amit");
		
	    return myMessage.toString();
		
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
