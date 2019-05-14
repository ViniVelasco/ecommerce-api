package com.velasco.ecommerceapi.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.velasco.ecommerceapi.domain.Client;
import com.velasco.ecommerceapi.repositories.ClientRepository;
import com.velasco.ecommerceapi.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {
	
	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private BCryptPasswordEncoder bCrypt;
	
	@Autowired
	private EmailService emailService;
	
	private Random rand = new Random();
	
	public void sendNewPassword(String email) {
		
		Client client = clientRepository.findByEmail(email);
	
		if(client == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		
		String newPass = newPassword();
		client.setPassword(bCrypt.encode(newPass));
	
		clientRepository.save(client);
		emailService.sendNewPasswordEmail(client, newPass);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i=0; i<10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3);
		if(opt == 0) { // generate digit
			return (char) (rand.nextInt(10) + 48);
		} else if(opt == 1) { //uppercase digit 
			return (char) (rand.nextInt(26) + 65);
		} else { //lowercase digit
			return (char) (rand.nextInt(26) + 97); //qtd + code first letter
		}
	}
}
