package com.velasco.ecommerceapi.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.velasco.ecommerceapi.domain.Client;
import com.velasco.ecommerceapi.domain.Order;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override
	public void sendOrderConfirmationEmail(Order obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromOrder(obj);
		sendEmail(sm); //template method
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromOrder(Order obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getClient().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido confirmado! Código: " + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return sm;
	}
	
	protected String htmlFromTemplatePedido(Order obj) {
		Context context = new Context();
		context.setVariable("order", obj);
		return templateEngine.process("email/orderConfirmation", context);
	}
	
	@Override
	public void sendOrderConfirmationHtmlEmail(Order obj) {
		try {
			MimeMessage mm = prepareMimeMessagefromOrder(obj);
			sendHtmlEmail(mm); //template method
		} catch (MessagingException e) {
			sendOrderConfirmationEmail(obj);
		}
	}

	protected MimeMessage prepareMimeMessagefromOrder(Order obj) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(obj.getClient().getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("Pedido confirmado! Código: " + obj.getId());
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromTemplatePedido(obj), true);
		return mimeMessage;
	}
	
	@Override
	public void sendNewPasswordEmail(Client client, String newPass) {
		SimpleMailMessage sm = prepareNewPasswordEmail(client, newPass);
		sendEmail(sm); //template method
	}

	protected SimpleMailMessage prepareNewPasswordEmail(Client client, String newPass) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(client.getEmail());
		sm.setFrom(sender);
		sm.setSubject("Solicitação de Nova Senha");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("Nova senha: " + newPass);
		return sm;
	}

}
