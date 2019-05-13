package com.velasco.ecommerceapi.services;

import org.springframework.mail.SimpleMailMessage;

import com.velasco.ecommerceapi.domain.Order;

public interface EmailService {

	void sendOrderConfirmationEmail(Order obj);
	
	void sendEmail(SimpleMailMessage msg);

}
