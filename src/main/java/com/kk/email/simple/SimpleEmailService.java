package com.kk.email.simple;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Helpful URL :
 * https://gist.github.com/ipingu/7681247
 * https://support.google.com/mail/?p=WantAuthError
 */
public class SimpleEmailService {

	public static void main(String[] args) {
		sendMail("your@email.com", "em4il_p4ssw0rd", "client@email.com", "Hello, I am message :P", "Hello, I am subject");
	}
	
	private static void sendMail(String from, String password, String to, String msg, String subject) {
		
		/**
		 * You can opt any one of these according to your usage.
		 * While trying anyone of them, don't forget to comment another one otherwise you will face compile time error.
		 */
		Properties props = viaSSL();
		//Properties props = viaTSL();
		
		//Get session
		Session session = Session.getDefaultInstance(props);
		
		try {
			//Compose message
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			message.setText(msg);
			
			//Send mail
			System.out.println("Sending mail in progress...");
			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com", from, password);
			/**
			 * These ways are throwing javax.mail.AuthenticationFailedException: failed to connect, no password specified?
			 * These ways doesn't work for me. So I tried transport.sendMessage() method which works fine.
			 * transport.send(message);
			 * Transport.send(message); 
			 */
			transport.sendMessage(message, message.getAllRecipients());
			System.out.println("Mail sent !!!");
			
 		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	private static Properties viaSSL() {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.auth", "true");
		
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.ssl", "true");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.port", "465");
		
		return props;
	}
	
	private static Properties viaTSL() {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.auth", "true");
		
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.tls", "true");
		
		return props;
	}
}
