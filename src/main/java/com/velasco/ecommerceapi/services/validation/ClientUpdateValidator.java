package com.velasco.ecommerceapi.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.velasco.ecommerceapi.domain.Client;
import com.velasco.ecommerceapi.domain.enums.ClientType;
import com.velasco.ecommerceapi.dto.ClientDTO;
import com.velasco.ecommerceapi.repositories.ClientRepository;
import com.velasco.ecommerceapi.resources.handlers.FieldMessage;
import com.velasco.ecommerceapi.services.validation.utils.BR;

public class ClientUpdateValidator implements ConstraintValidator<ClientUpdate, ClientDTO> {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClientRepository repo;
	
	@Override 
	public void initialize(ClientUpdate ann) {
	}

	@Override
	public boolean isValid(ClientDTO objDto, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer id = Integer.parseInt(map.get("id"));
		
		List<FieldMessage> list = new ArrayList<>();
		
		Client aux = repo.findByEmail(objDto.getEmail());
		if(aux != null && !aux.getId().equals(id)) {
			list.add(new FieldMessage("email", "Email já existente"));
		}
		
		// inclua os testes aqui, inserindo erros na lista
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}