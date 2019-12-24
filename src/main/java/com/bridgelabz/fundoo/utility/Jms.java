package com.bridgelabz.fundoo.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class Jms {

	@Autowired
	JavaMailSender javamailsender;

	public void sendMail(String email, String token) {
		System.out.println("email"+email);
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom("tusharnayak1996@gmail.com");
		mail.setTo(email);
		mail.setText(token);
		mail.setSubject("Verification Token");
		System.out.println(mail.getFrom());
		System.out.println(mail.getSubject());
		System.out.println(mail.getTo());
			
		javamailsender.send(mail);
	
	}

}
