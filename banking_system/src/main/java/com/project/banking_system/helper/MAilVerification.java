package com.project.banking_system.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.project.banking_system.dto.Customer;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
@Service
public class MAilVerification {
	@Autowired
	JavaMailSender mailsender;

	public void sendMail(Customer customer) {
		MimeMessage mimeMessage = mailsender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

		try {
			helper.setFrom("msiddaroodh@gmail.com");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			helper.setTo(customer.getEmail());
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			helper.setSubject("Mail Verification");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			helper.setText("Your otp for email verification " + customer.getOtp());
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mailsender.send(mimeMessage);
	}
}
