package com.velasco.ecommerceapi.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.velasco.ecommerceapi.domain.Client;
import com.velasco.ecommerceapi.services.validation.ClientUpdate;

@ClientUpdate
public class ClientDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotEmpty(message="Preenchimento obrigatório")
	@Length(min=5, max=120, message="O tamanho deve ser entre 5 e 120 caracteres")
	private String name;
	
	@NotEmpty
	@Email(message="Email inválido")
	private String email;
	
	public ClientDTO() {
		
	}
	
	public ClientDTO(Client obj) {
		name = obj.getName();
		email = obj.getEmail();
		id = obj.getId();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
