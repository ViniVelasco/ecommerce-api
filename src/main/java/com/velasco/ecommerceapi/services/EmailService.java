package com.velasco.ecommerceapi.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.velasco.ecommerceapi.domain.Client;
import com.velasco.ecommerceapi.domain.Order;

public interface EmailService {

	void sendOrderConfirmationEmail(Order obj);
	
	void sendEmail(SimpleMailMessage msg);
	
	void sendOrderConfirmationHtmlEmail(Order obj);
	
	void sendHtmlEmail(MimeMessage msg);

	void sendNewPasswordEmail(Client client, String newPass);

}
