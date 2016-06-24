package com.mail;

import org.junit.Test;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by yuwt on 2016/6/21.
 */
public class MailTest {
	@Test
	public void test_send_mail() {
		// Recipient's email ID needs to be mentioned.
		String to = "yuwantao1109@gmail.com";

		// Sender's email ID needs to be mentioned
		String from = "yuwantao@readgo.cn";

		// Assuming you are sending email from localhost
		String host = "smtp.exmail.qq.com";

		String username = "yuwantao@readgo.cn";
		String password = "Rsrzrcj@7";
		// Get system properties
		Properties props = System.getProperties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", password);
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");

		// Get the default Session object.
		Session session = Session.getDefaultInstance(props,  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try{
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject("This is the Subject Line!");

			// Now set the actual message
			message.setText("This is actual message");

			// Send message
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, password);
			message.saveChanges();
			Transport.send(message);
			System.out.println("Sent message successfully....");
		}catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
