package com.dataeconomy.datamigration.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil {

	public  String getFileName() throws IOException
	{
		return getClass().getClassLoader().getResource("db.properties").getFile();
	}
	public static void sendMail(String message,String email,String subject) throws Exception
	{
		MailUtil m = new MailUtil();
		InputStream input = new FileInputStream(m.getFileName());
		Properties prop = new Properties();
		prop.load(input);
		System.out.println("Email HOST***!"+prop.getProperty("SMTP_HOST"));
		Properties props = System.getProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.port", prop.getProperty("SMTP_PORT")); 
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);
		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(prop.getProperty("MAILFROM"),prop.getProperty("MAILFROM")));
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
		msg.setSubject(subject);
		msg.setContent(message,"text/html");
		msg.setHeader("X-SES-CONFIGURATION-SET", "ConfigSet");
		Transport transport = session.getTransport();
		try {
			new Thread(new Runnable() {

				public void run() {
					try {
						transport.connect(prop.getProperty("SMTP_HOST"), prop.getProperty("SMTP_USERNAME"), prop.getProperty("SMTP_PASSWORD"));
						transport.sendMessage(msg, msg.getAllRecipients());
						System.out.println("Email sent!");
					} catch (AddressException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println("MessagingException");
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println("MessagingException.");

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();

			//   System.out.println("Email sent.");
		} catch (Exception ex) {
			System.out.println("Failed to sent email.");
		}
		finally
		{
			transport.close();
		}

	}
	public static void sendUseralert(String username,String email,String password) throws Exception
	{
		// message contains HTML markups
		String message = "<html>";
		message += "<table align='center' cellspacing='30' width='100%' height='100%' style='background-color:#009688; font-family:Open Sans,Helvetica,Arial,sans-serif; font-size:14px; color:black; line-height:20px'>";
		message += "<tbody>";
		message += "<tr>";
		message += "<td align='center' style='vertical-align:top'>";
		message += "<table width='600' cellspacing='0' cellpadding='0' align='center' style='text-align:left; background-color:#88bd2d'>";
		message += "<tbody>";
		message += "<tr>";
		message += "<td height='5' style='height:5px'></td>";
		message += "</tr>";
		message += "<tr>";
		message += "<td>";
		message += "<table width='100%' cellspacing='0' cellpadding='25' style='background:#ffffff'>";
		message += "<tbody>";
		message += "<tr>";
		message += "Hi <b><u>"+username+" </u></b> Your Data Migration Tool user created successfully with username:"+username+""
				+ "  and password:"+password+" Please reset your password using resetpassword link.<b><u>" 
				+ " </u></b>  <b>Please login using: <a href='http://localhost:8080/DataMigration'>http://localhost:8080/DataMigration</a>  <u>";
		message += "</tr>";
		message += "</tbody>";
		message += "</table>";
		message += "</td>";
		message += "</tr>";
		message += "<tr>";
		message += "<td>";
		message += "<table width='100%' cellspacing='0' cellpadding='25' style='color:white; font-weight:600'>";
		message += "<tbody>";
		message += "<tr>";
		message += "<td>&nbsp;</td>";
		message += "<td style='text-align:right'>HelpDesk-Data Migration Management System-AEMS</td>";
		message += "</tr>";
		message += "</tbody>";
		message += "</table>";
		message += "</td>";
		message += "</tr>";
		message += "<tr>";
		message += "<td style='background-color:#009688; padding:10px; font-size:12px; color:#666666'>";
		message += "<table>";
		message += "</table>";
		message += "</td>";
		message += "</tr>";
		message += "</tbody>";
		message += "</table>";
		message += "</td>";
		message += "</tr>";
		message += "</tbody>";
		message += "</table>";
		message += "</html>";
		sendMail(message,email,"Data Migration Tool");
	}

}
